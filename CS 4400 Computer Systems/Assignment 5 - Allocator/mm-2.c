/*
 * mm.c
 *
 * For this implementation, I used an explicit free list to traverse the heap and allocate on free blocks.
 * My implimentation maps a given adjusted size of a page and allocates the given memory. The pointers of
 * the free list are stored within the payload blocks for easy access.
 * 
 * I also unmap pages when necessary. It looks at the previous block header and next block header. If the
 * previous block size is 16 (prologue block is the only block with that size), and the next block size
 * is 0 but is marked as allocated, the block of memory gets unmapped.
 * 
 * Adjusting the explicit free list was pretty easy since it was a LIFO structure. When a node needed to
 * get added, it only needed to go on top of the "stack". Removing nodes was a bit harder, but still easy
 * since all that needed to be done was to make sure that its neighbors bypassed the node by stitching
 * them together.
 * 
 * My total score isn't as efficient as I wanted it to be, but it's still pretty good. It has a hard time
 * determining an optimal size to work with and keep around. That's my best guess anyway.
 *
 * Harry Kim (u1226472)
 * 11/29/22
 * 
 */
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>
#include <string.h>

#include "mm.h"
#include "memlib.h"

// typedef for header and footer
typedef size_t block_header;
typedef size_t block_footer;

// Struct for node linked list
typedef struct list_node
{
  struct list_node *prev;
  struct list_node *next;
} list_node;

/* always use 16-byte alignment */
#define ALIGNMENT 16

/* rounds up to the nearest multiple of ALIGNMENT */
#define ALIGN(size) (((size) + (ALIGNMENT - 1)) & ~(ALIGNMENT - 1))

/* rounds up to the nearest multiple of mem_pagesize() */
#define PAGE_ALIGN(size) (((size) + (mem_pagesize() - 1)) & ~(mem_pagesize() - 1))

// This assumes you have a struct or typedef called "block_header" and "block_footer"
#define OVERHEAD (sizeof(block_header) + sizeof(block_footer))

// Given a payload pointer, get the header or footer pointer
#define HDRP(bp) ((char *)(bp) - sizeof(block_header))
#define FTRP(bp) ((char *)(bp) + GET_SIZE(HDRP(bp)) - OVERHEAD)

// Given a payload pointer, get the next or previous payload pointer
#define NEXT_BLKP(bp) ((char *)(bp) + GET_SIZE(HDRP(bp)))
#define PREV_BLKP(bp) ((char *)(bp)-GET_SIZE((char *)(bp)-OVERHEAD))

#define MAX(x, y) ((x) > (y) ? (x) : (y))

// ******These macros assume you are using a size_t for headers and footers ******

// Given a pointer to a header, get or set its value
#define GET(p) (*(size_t *)(p))
#define PUT(p, val) (*(size_t *)(p) = (val))

// Combine a size and alloc bit
#define PACK(size, alloc) ((size) | (alloc))

// Given a header pointer, get the alloc or size
#define GET_ALLOC(p) (GET(p) & 0x1)
#define GET_SIZE(p) (GET(p) & ~0xF)

// ******Recommended helper functions******

// Declaring helper functions
static void set_allocated(void *b, size_t size);
static void extend(size_t s);
static void *coalesce(void *bp);
static void heap_check();
static void adjustFreeList(void *bp, int addOrDelete);

// Global variables
void *current_avail = NULL;
int current_avail_size = 0;
void *first_bp;
int multiplier;

// Keep a list of free blocks. One to the other. LIFO order by inserting newly freed blocks at the beginning of the list.
static list_node *free_block;

/*
 * mm_init - initialize the malloc package.
 */
int mm_init(void)
{
  current_avail = NULL;
  current_avail_size = 0;
  free_block = NULL;
  // first_bp = NULL;
  multiplier = 1;
  // mm_malloc(0); // Never freed
  return 0;
}

/*
 * mm_malloc - Allocate a block by using bytes from current_avail,
 *     grabbing a new page if necessary.
 */
void *mm_malloc(size_t size)
{
  // Explicit free list implementation.

  // Initialization.
  int newsize = ALIGN(MAX(size, sizeof(list_node)) + OVERHEAD);
  list_node *p = free_block;

  while (1)
  {
    // Reached (or already at) bottom of LIFO linked list.
    if (p == NULL)
    {
      // Extend
      extend(newsize);
      void *newbp = NEXT_BLKP(current_avail + 16);
      // free list gets added to in set_allocated.
      set_allocated(newbp, newsize);
      // heap_check();
      return newbp;
    }
    // Found a good space
    if ((GET_SIZE(HDRP(p))) >= newsize)
    {
      // free list gets added to in set_allocated.
      set_allocated(p, newsize);
      // heap_check();
      return (void *)p;
    }
    // Move on the the next free block.
    else
    {
      p = p->prev;
    }
  }
}

/*
 * mm_free - Frees a block and unmaps when necessary.
 */
void mm_free(void *ptr)
{
  PUT(HDRP(ptr), PACK(GET_SIZE(HDRP(ptr)), 0));
  PUT(FTRP(ptr), PACK(GET_SIZE(HDRP(ptr)), 0));

  // Add newly freed block to explicit free list
  // coalesce should already take care of that.
  void *cptr = coalesce(ptr);

  // unmap unused pages
  // I don't know how to do that... yet.

  // 3. you can check if first_bp is unallocated and if it is you can check if the next block after is the terminating block since it must be the terminating if there's only a free_block in the page.
  // If the block size is a multiple of 4096 - 32, the header of the block size + 8 is
  // If prev block size is 16 and if next block is allocated and size 0, unmap that sucker.
  if(GET_SIZE(HDRP(PREV_BLKP(ptr))) == 16 && GET_SIZE(HDRP(NEXT_BLKP(ptr))) == 0 && GET_ALLOC(HDRP(NEXT_BLKP(ptr))) == 1){
  // If ptr size is a multiple of 4096
  // if((GET_SIZE(HDRP(ptr))) % 4096 == 0 && GET_SIZE(HDRP(NEXT_BLKP(ptr))) == 0 && GET_ALLOC(HDRP(NEXT_BLKP(ptr))) == 1){
    // printf("I am unmapping! %p\n", ptr);
    adjustFreeList(ptr, 0);
    mem_unmap(ptr - 32, GET_SIZE(HDRP(ptr)) + 32);
  }


  // heap_check();
}

/* Set a block to allocated
 * Update block headers/footers as needed
 * Update free list if applicable
 * Split block if applicable
 */
static void set_allocated(void *p, size_t size)
{
  // Assuming we are only allocating on a free block with adequate size, delete the node and add a new one at bp + size.
  // Only adding to the extra size of the unallocated space should be applicable here.

  size_t extra_size = GET_SIZE(HDRP(p)) - size;
  int result = (int)(GET_SIZE(HDRP(p)) - size);
  size_t legacy_p_size = GET_SIZE(HDRP(p));

  // For testing. Comment out later.
  // printf("Pointer size: %ld\n", GET_SIZE(HDRP(p)));
  // printf("Size: %ld\n", size);
  // printf("Extra size: %ld\n", extra_size);
  // printf("Result: %d\n", result);

  PUT(HDRP(p), PACK(size, 1));
  PUT(FTRP(p), PACK(size, 1));

  // Since p is allocated, previously being in the free list, p needs to be removed.
  adjustFreeList(p, 0);

  // If splitting would create a useful block.
  if (result >= (int)(ALIGN(1 + OVERHEAD)))
  {
    PUT(HDRP(NEXT_BLKP(p)), PACK(extra_size, 0));
    PUT(FTRP(NEXT_BLKP(p)), PACK(extra_size, 0));

    // For testing. Comment out later.
    // printf("prev bp: %p\n", PREV_BLKP(p));
    // printf("curr bp: %p\n", p);
    // printf("next bp: %p\n", NEXT_BLKP(p));

    // Add unallocated block pointer to free list
    adjustFreeList(NEXT_BLKP(p), 1);
  }

  else
  {
    // printf("else working %p\n", p);
    PUT(HDRP(p), PACK(legacy_p_size, 1));
    PUT(FTRP(p), PACK(legacy_p_size, 1));
    // printf("if working done %p\n", p);
  }
}

/* Request more memory by calling mem_map
 * Initialize the new chunk of memory as applicable
 * Update free list if applicable
 */
static void extend(size_t s)
{
  current_avail_size = PAGE_ALIGN(s * 10 * multiplier);
  current_avail = mem_map(current_avail_size);
  void *bp = current_avail + 16;

  // Overhead is already acounted for with newsize.

  // bp points to the current payload.
  // PUT(HDRP(bp), PACK(s, 1));
  // PUT(FTRP(bp), PACK(s, 1));
  // NEED TO PUT 16 IN FIRST BLOCK. NOT "s"!!!
  PUT(HDRP(bp), PACK(16, 1));
  PUT(FTRP(bp), PACK(16, 1));

  // printf("Size in p: %ld\n", GET_SIZE(HDRP(bp)));

  // NEXT_BLKP points to the next payload pointer. -8 for padding, -16 for first never freed block, and -8 for last header. Total: -32.
  PUT(HDRP(NEXT_BLKP(bp)), PACK(current_avail_size - 32, 0));
  PUT(FTRP(NEXT_BLKP(bp)), PACK(current_avail_size - 32, 0));

  // Need to set very end as size 0 and allocated.
  PUT(HDRP(current_avail + current_avail_size), PACK(0, 1));

  // printf("Current bp: %p\n", bp);
  // printf("Next bp: %p\n", NEXT_BLKP(bp));

  // multiplier += 1;

  if (multiplier < 80)
  {
    multiplier += 1;
  }
  

  // Add NEXT_BLKP(bp) to explicit free list.
  adjustFreeList(NEXT_BLKP(bp), 1);

  // // No freed blocks at this point.
  // if (free_block == NULL)
  // {
  //   // Last in freed block becomes the new block pointer.
  //   printf("working, %p\n", free_block);
  //   free_block = (list_node *)NEXT_BLKP(bp);
  //   free_block->prev = NULL;
  //   free_block->next = NULL;
  // }

  // // If there is a freed block:
  // else
  // {
  //   // NEXT_BLKP(bp) becomes the new free_block.
  //   ((list_node *)NEXT_BLKP(bp))->next = NULL;
  //   ((list_node *)NEXT_BLKP(bp))->prev = free_block;
  //   free_block->next = ((list_node *)NEXT_BLKP(bp));
  //   free_block = ((list_node *)NEXT_BLKP(bp));
  // }
}

/* Coalesce a free block if applicable
 * Returns pointer to new coalesced block
 */
static void *coalesce(void *bp)
{
  // printf("I am coalescing! %p\n", bp);

  size_t prev_alloc = GET_ALLOC(HDRP(PREV_BLKP(bp)));
  size_t next_alloc = GET_ALLOC(HDRP(NEXT_BLKP(bp)));
  size_t size = GET_SIZE(HDRP(bp));

  // For testing. Delete later.
  // printf("prev alloc: %ld\n", prev_alloc);
  // printf("prev size: %ld\n", GET_SIZE(HDRP(PREV_BLKP(bp))));
  // printf("next alloc: %ld\n", next_alloc);
  // printf("next size: %ld\n", GET_SIZE(HDRP(NEXT_BLKP(bp))));

  // Case 1: freed block is between two allocated blocks
  if (prev_alloc && next_alloc)
  {
    // A new free block has been opened up.
    // add bp to explicit free list linked list.
    // bp becomes the new free_block.
    // ((list_node *)bp)->next = NULL;
    // ((list_node *)bp)->prev = free_block;
    // free_block->next = ((list_node *)bp);
    // free_block = ((list_node *)bp);
    adjustFreeList(bp, 1);

    return bp;
  }

  // Case 2: Freed block has an unallocated block to the right of it.
  else if (prev_alloc && !next_alloc)
  {
    size += GET_SIZE(HDRP(NEXT_BLKP(bp)));

    // delete NEXT_BLKP(bp) from explicit free list linked list.
    // Needs to happen before header and footer are set. Otherwise, NEXT_BLKP(bp) will point to the very end of the space.
    adjustFreeList(NEXT_BLKP(bp), 0);

    PUT(HDRP(bp), PACK(size, 0));
    PUT(FTRP(bp), PACK(size, 0));

    // add bp to explicit free list linked list.
    // bp becomes the new free_block.
    adjustFreeList(bp, 1);
  }

  // Case 3: Freed block has an unallocated block to the left of it.
  else if (!prev_alloc && next_alloc)
  {
    size += GET_SIZE(HDRP(PREV_BLKP(bp)));

    PUT(FTRP(bp), PACK(size, 0));
    PUT(HDRP(PREV_BLKP(bp)), PACK(size, 0));
    

    bp = PREV_BLKP(bp);

    // Don't have to adjust list since there should already be a free block node in list of previous block.
  }

  // Case 4: Freed block is between two unallocated blocks.
  else if (!prev_alloc && !next_alloc)
  {
    size += (GET_SIZE(HDRP(PREV_BLKP(bp))) + GET_SIZE(HDRP(NEXT_BLKP(bp))));

    // Already free block node in list of previous block. Next block node needs to be deleted.
    adjustFreeList(NEXT_BLKP(bp), 0);

    PUT(HDRP(PREV_BLKP(bp)), PACK(size, 0));
    PUT(FTRP(NEXT_BLKP(bp)), PACK(size, 0));
    // bp = PREV_BLKP(bp);

    // May want to move block pointer after adjusting list with it.
    bp = PREV_BLKP(bp);
  }

  return bp;
}

/**
 * A crappy heap checker, but it does what I need it to do well enough.
 * Put before mm_malloc and mm_free return.
 */
static void heap_check()
{
  // void *current_node = first_bp;
  // while (GET_SIZE(HDRP(current_node)) != 0)
  // {
  //   if (GET_SIZE(HDRP(current_node)) < 0)
  //   {
  //     printf("NEGATIVE SIZE AT POINTER %p: %ld\n", current_node, GET_SIZE(current_node));
  //   }
  //   current_node = NEXT_BLKP(current_node);
  // }
  // printf("Good job. %p\n", current_node);

  list_node *current_lp = free_block;
  while (current_lp->prev != NULL)
  {
    if(GET_ALLOC((void*)current_lp) != 0){
      printf("BLOCK IN FREE LIST IS ALLOCATED: %p\n", (void*)current_lp);
    }
    current_lp = current_lp->prev;
  }
}

/* Makes adjustments to the nodes of the explicit free list if need be.
 * 1 to add and 0 to delete a node.
 */
static void adjustFreeList(void *bp, int addOrDelete)
{
  // Add node to free list.
  if (addOrDelete)
  {

    // No freed blocks at this point.
    if (free_block == NULL)
    {

      // Last in freed block becomes the new block pointer.
      free_block = (list_node *)bp;
      free_block->prev = NULL;
      free_block->next = NULL;
    }
    else if (free_block != NULL)
    {
      // bp becomes the new free_block.
      ((list_node *)bp)->next = NULL;
      ((list_node *)bp)->prev = free_block;
      free_block->next = ((list_node *)bp);
      free_block = ((list_node *)bp);
    }
  }

  // delete node from free list.
  else
  {

    // If bp is at the end (top) of the lifo linked list:
    if (((list_node *)bp) == free_block)
    {
      // free_block is the only one in the list.
      if (free_block->prev == NULL)
      {
        free_block->next = NULL;
        free_block = NULL;
      }
      else
      {
        free_block = free_block->prev;
        free_block->next = NULL;
      }
    }
    // bp is the beginning (bottom) of lifo linked list:
    else if (((list_node *)bp)->prev == NULL)
    {
      ((list_node *)bp)->next->prev = NULL;
    }

    // bp is literally anywhere else. Somewhere in the middle of it all.
    else
    {
      
      list_node* curr = ((list_node *)bp);
      list_node* prevBp = curr->prev;
      list_node* nextBp = curr->next;

      prevBp->next = nextBp;
      nextBp->prev = prevBp;

    }
  }
}