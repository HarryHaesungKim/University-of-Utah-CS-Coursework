/*******************************************
 * Solutions for the CS:APP Performance Lab
 ********************************************/

#include <stdio.h>
#include <stdlib.h>
#include "defs.h"

/*
 * Please fill in the following student struct
 */
student_t student = {
    "Harry Kim",         /* Full name */
    "u1226472@utah.edu", /* Email address */
};

/***************
 * COMPLEX KERNEL
 ***************/

/******************************************************
 * Your different versions of the complex kernel go here
 ******************************************************/

/*
 * naive_complex - The naive baseline version of complex
 */
char naive_complex_descr[] = "naive_complex: Naive baseline implementation";
void naive_complex(int dim, pixel *src, pixel *dest)
{
  int i, j;

  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++)
    {

      dest[RIDX(dim - j - 1, dim - i - 1, dim)].red = ((int)src[RIDX(i, j, dim)].red +
                                                       (int)src[RIDX(i, j, dim)].green +
                                                       (int)src[RIDX(i, j, dim)].blue) /
                                                      3;

      dest[RIDX(dim - j - 1, dim - i - 1, dim)].green = ((int)src[RIDX(i, j, dim)].red +
                                                         (int)src[RIDX(i, j, dim)].green +
                                                         (int)src[RIDX(i, j, dim)].blue) /
                                                        3;

      dest[RIDX(dim - j - 1, dim - i - 1, dim)].blue = ((int)src[RIDX(i, j, dim)].red +
                                                        (int)src[RIDX(i, j, dim)].green +
                                                        (int)src[RIDX(i, j, dim)].blue) /
                                                       3;
    }
}

/*
 * complex - Your current working version of complex
 * IMPORTANT: This is the version you will be graded on
 */
char complex_descr[] = "complex: Current working version";
void complex(int dim, pixel *src, pixel *dest)
{
  // Shift instead of multiply or divide
  // Strength reduction (take addition outside of the loops). Subexpressions.
  // Replace sequence of products with additions
  // Loop unrolling

  // naive_complex(dim, src, dest);

  int i, j;

  for (i = 0; i < dim; i += 4)
    for (j = 0; j < dim; j += 4)
    {
      // j + 0
      // i + 0
      int row = dim - j - 1;
      int col = dim - i - 1;
      int transformPix = ((int)src[RIDX(i, j, dim)].red + (int)src[RIDX(i, j, dim)].green + (int)src[RIDX(i, j, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 1
      col = dim - (i + 1) - 1;
      transformPix = ((int)src[RIDX(i + 1, j, dim)].red + (int)src[RIDX(i + 1, j, dim)].green + (int)src[RIDX(i + 1, j, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 2
      col = dim - (i + 2) - 1;
      transformPix = ((int)src[RIDX(i + 2, j, dim)].red + (int)src[RIDX(i + 2, j, dim)].green + (int)src[RIDX(i + 2, j, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 3
      col = dim - (i + 3) - 1;
      transformPix = ((int)src[RIDX(i + 3, j, dim)].red + (int)src[RIDX(i + 3, j, dim)].green + (int)src[RIDX(i + 3, j, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;

      // j + 1
      // i + 0
      row = dim - (j + 1) - 1;
      col = dim - i - 1;
      transformPix = ((int)src[RIDX(i, j + 1, dim)].red + (int)src[RIDX(i, j + 1, dim)].green + (int)src[RIDX(i, j + 1, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 1
      col = dim - (i + 1) - 1;
      transformPix = ((int)src[RIDX(i + 1, j + 1, dim)].red + (int)src[RIDX(i + 1, j + 1, dim)].green + (int)src[RIDX(i + 1, j + 1, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 2
      col = dim - (i + 2) - 1;
      transformPix = ((int)src[RIDX(i + 2, j + 1, dim)].red + (int)src[RIDX(i + 2, j + 1, dim)].green + (int)src[RIDX(i + 2, j + 1, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 3
      col = dim - (i + 3) - 1;
      transformPix = ((int)src[RIDX(i + 3, j + 1, dim)].red + (int)src[RIDX(i + 3, j + 1, dim)].green + (int)src[RIDX(i + 3, j + 1, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;

      // j + 2
      // i + 0
      row = dim - (j + 2) - 1;
      col = dim - i - 1;
      transformPix = ((int)src[RIDX(i, j + 2, dim)].red + (int)src[RIDX(i, j + 2, dim)].green + (int)src[RIDX(i, j + 2, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 1
      col = dim - (i + 1) - 1;
      transformPix = ((int)src[RIDX(i + 1, j + 2, dim)].red + (int)src[RIDX(i + 1, j + 2, dim)].green + (int)src[RIDX(i + 1, j + 2, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 2
      col = dim - (i + 2) - 1;
      transformPix = ((int)src[RIDX(i + 2, j + 2, dim)].red + (int)src[RIDX(i + 2, j + 2, dim)].green + (int)src[RIDX(i + 2, j + 2, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 3
      col = dim - (i + 3) - 1;
      transformPix = ((int)src[RIDX(i + 3, j + 2, dim)].red + (int)src[RIDX(i + 3, j + 2, dim)].green + (int)src[RIDX(i + 3, j + 2, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;

      // j + 3
      // i + 0
      row = dim - (j + 3) - 1;
      col = dim - i - 1;
      transformPix = ((int)src[RIDX(i, j + 3, dim)].red + (int)src[RIDX(i, j + 3, dim)].green + (int)src[RIDX(i, j + 3, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 1
      col = dim - (i + 1) - 1;
      transformPix = ((int)src[RIDX(i + 1, j + 3, dim)].red + (int)src[RIDX(i + 1, j + 3, dim)].green + (int)src[RIDX(i + 1, j + 3, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 2
      col = dim - (i + 2) - 1;
      transformPix = ((int)src[RIDX(i + 2, j + 3, dim)].red + (int)src[RIDX(i + 2, j + 3, dim)].green + (int)src[RIDX(i + 2, j + 3, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
      // i + 3
      col = dim - (i + 3) - 1;
      transformPix = ((int)src[RIDX(i + 3, j + 3, dim)].red + (int)src[RIDX(i + 3, j + 3, dim)].green + (int)src[RIDX(i + 3, j + 3, dim)].blue) / 3;
      dest[RIDX(row, col, dim)].red = transformPix;
      dest[RIDX(row, col, dim)].green = transformPix;
      dest[RIDX(row, col, dim)].blue = transformPix;
    }
}

/*********************************************************************
 * register_complex_functions - Register all of your different versions
 *     of the complex kernel with the driver by calling the
 *     add_complex_function() for each test function. When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_complex_functions()
{
  add_complex_function(&complex, complex_descr);
  add_complex_function(&naive_complex, naive_complex_descr);
}

/***************
 * MOTION KERNEL
 **************/

/***************************************************************
 * Various helper functions for the motion kernel
 * You may modify these or add new ones any way you like.
 **************************************************************/

/*
 * weighted_combo - Returns new pixel value at (i,j)
 */
static pixel weighted_combo(int dim, int i, int j, pixel *src)
{
  int ii, jj;
  pixel current_pixel;

  int red, green, blue;
  red = green = blue = 0;

  int num_neighbors = 0;
  for (ii = 0; ii < 3; ii++)
    for (jj = 0; jj < 3; jj++)
      if ((i + ii < dim) && (j + jj < dim))
      {
        num_neighbors++;
        red += (int)src[RIDX(i + ii, j + jj, dim)].red;
        green += (int)src[RIDX(i + ii, j + jj, dim)].green;
        blue += (int)src[RIDX(i + ii, j + jj, dim)].blue;
      }

  current_pixel.red = (unsigned short)(red / num_neighbors);
  current_pixel.green = (unsigned short)(green / num_neighbors);
  current_pixel.blue = (unsigned short)(blue / num_neighbors);

  return current_pixel;
}

/*
 * inner_weighted_combo - Returns new pixel value at (i,j) faster than weighted_combo
 */
static pixel inner_weighted_combo(int dim, int i, int j, pixel *src)
{
  pixel current_pixel;
  int red, green, blue;
  red = green = blue = 0;

  // i + 0
  // j + 0
  int ridxNum = RIDX(i, j, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;
  // j + 1
  ridxNum = RIDX(i, j + 1, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;
  // j + 2
  ridxNum = RIDX(i, j + 2, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;

  // i + 1
  // j + 0
  ridxNum = RIDX(i + 1, j, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;
  // j + 1
  ridxNum = RIDX(i + 1, j + 1, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;
  // j + 2
  ridxNum = RIDX(i + 1, j + 2, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;

  // i + 2
  // j + 0
  ridxNum = RIDX(i + 2, j, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;
  // j + 1
  ridxNum = RIDX(i + 2, j + 1, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;
  // j + 2
  ridxNum = RIDX(i + 2, j + 2, dim);
  red += (int)src[ridxNum].red;
  green += (int)src[ridxNum].green;
  blue += (int)src[ridxNum].blue;

  current_pixel.red = (unsigned short)(red / 9);
  current_pixel.green = (unsigned short)(green / 9);
  current_pixel.blue = (unsigned short)(blue / 9);

  return current_pixel;
}

/******************************************************
 * Your different versions of the motion kernel go here
 ******************************************************/

/*
 * naive_motion - The naive baseline version of motion
 */
char naive_motion_descr[] = "naive_motion: Naive baseline implementation";
void naive_motion(int dim, pixel *src, pixel *dst)
{
  int i, j;

  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++)
      dst[RIDX(i, j, dim)] = weighted_combo(dim, i, j, src);
}

/*
 * motion - Your current working version of motion.
 * IMPORTANT: This is the version you will be graded on
 */
char motion_descr[] = "motion: Current working version";
void motion(int dim, pixel *src, pixel *dst)
{

  int i, j;

  for (i = 0; i < dim - 2; i++)
    for (j = 0; j < dim - 2; j++)
      dst[RIDX(i, j, dim)] = inner_weighted_combo(dim, i, j, src);

  // left boarder
  i = dim - 2;
  for (j = 0; j < dim - 2; j++){
    
    pixel current_pixel;

    pixel next_pixel;

    int red, green, blue;
    red = green = blue = 0;

    int red_next, green_next, blue_next;
    red_next = green_next = blue_next = 0;

    // i + 0
    // j + 0
    int ridxNum = RIDX(i, j, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    // j + 1
    ridxNum = RIDX(i, j + 1, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    // j + 2
    ridxNum = RIDX(i, j + 2, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;

    // the last row of pixels will only have these. So do them at the same time.
    // i + 1
    // j + 0
    ridxNum = RIDX(i + 1, j, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    red_next += (int)src[ridxNum].red;
    green_next += (int)src[ridxNum].green;
    blue_next += (int)src[ridxNum].blue;
    // j + 1
    ridxNum = RIDX(i + 1, j + 1, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    red_next += (int)src[ridxNum].red;
    green_next += (int)src[ridxNum].green;
    blue_next += (int)src[ridxNum].blue;
    // j + 2
    ridxNum = RIDX(i + 1, j + 2, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    red_next += (int)src[ridxNum].red;
    green_next += (int)src[ridxNum].green;
    blue_next += (int)src[ridxNum].blue;

    // Only 6 neighbors
    current_pixel.red = (unsigned short)(red / 6);
    current_pixel.green = (unsigned short)(green / 6);
    current_pixel.blue = (unsigned short)(blue / 6);

    // Only 3 neighbors
    next_pixel.red = (unsigned short)(red_next / 3);
    next_pixel.green = (unsigned short)(green_next / 3);
    next_pixel.blue = (unsigned short)(blue_next / 3);
  
    dst[RIDX(i, j, dim)] = current_pixel;

    dst[RIDX(i + 1, j, dim)] = next_pixel;
  }

  // bottom boarder
  j = dim - 2;
  for (i = 0; i < dim - 2; i++){

    pixel current_pixel;

    pixel next_pixel;

    int red, green, blue;
    red = green = blue = 0;

    int red_next, green_next, blue_next;
    red_next = green_next = blue_next = 0;

    // i + 0
    // j + 0
    int ridxNum = RIDX(i, j, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    // j + 1
    ridxNum = RIDX(i, j + 1, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    red_next += (int)src[ridxNum].red;
    green_next += (int)src[ridxNum].green;
    blue_next += (int)src[ridxNum].blue;

    // i + 1
    // j + 0
    ridxNum = RIDX(i + 1, j, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    // j + 1
    ridxNum = RIDX(i + 1, j + 1, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    red_next += (int)src[ridxNum].red;
    green_next += (int)src[ridxNum].green;
    blue_next += (int)src[ridxNum].blue;

    // i + 2
    // j + 0
    ridxNum = RIDX(i + 2, j, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    // j + 1
    ridxNum = RIDX(i + 2, j + 1, dim);
    red += (int)src[ridxNum].red;
    green += (int)src[ridxNum].green;
    blue += (int)src[ridxNum].blue;
    red_next += (int)src[ridxNum].red;
    green_next += (int)src[ridxNum].green;
    blue_next += (int)src[ridxNum].blue;

    // Only 6 neighbors
    current_pixel.red = (unsigned short)(red / 6);
    current_pixel.green = (unsigned short)(green / 6);
    current_pixel.blue = (unsigned short)(blue / 6);

    // Only 3 neighbors
    next_pixel.red = (unsigned short)(red_next / 3);
    next_pixel.green = (unsigned short)(green_next / 3);
    next_pixel.blue = (unsigned short)(blue_next / 3);

    dst[RIDX(i, j, dim)] = current_pixel;
    dst[RIDX(i, j + 1, dim)] = next_pixel;
  }


  // // last 2 by 2 square on bottom left boarder
  i = dim - 2;
  j = dim - 2;
  
  pixel top_left_pixel;
  pixel top_right_pixel;
  pixel bottom_left_pixel;
  pixel bottom_right_pixel;

  int red_tl, green_tl, blue_tl;
  red_tl = green_tl = blue_tl = 0;

  int red_tr, green_tr, blue_tr;
  red_tr = green_tr = blue_tr = 0;

  int red_bl, green_bl, blue_bl;
  red_bl = green_bl = blue_bl = 0;

  int red_br, green_br, blue_br;
  red_br = green_br = blue_br = 0;

  // i = row, j = col

  // i + 0
  // j + 0
  int ridxNum = RIDX(i, j, dim);
  red_tl += (int)src[ridxNum].red;
  green_tl += (int)src[ridxNum].green;
  blue_tl += (int)src[ridxNum].blue;
  // j + 1
  ridxNum = RIDX(i, j + 1, dim);
  red_tl += (int)src[ridxNum].red;
  green_tl += (int)src[ridxNum].green;
  blue_tl += (int)src[ridxNum].blue;
  red_tr += (int)src[ridxNum].red;
  green_tr += (int)src[ridxNum].green;
  blue_tr += (int)src[ridxNum].blue;

  // i + 1
  // j + 0
  ridxNum = RIDX(i + 1, j, dim);
  red_tl += (int)src[ridxNum].red;
  green_tl += (int)src[ridxNum].green;
  blue_tl += (int)src[ridxNum].blue;
  red_bl += (int)src[ridxNum].red;
  green_bl += (int)src[ridxNum].green;
  blue_bl += (int)src[ridxNum].blue;

  // j + 1
  ridxNum = RIDX(i + 1, j + 1, dim);
  red_tl += (int)src[ridxNum].red;
  green_tl += (int)src[ridxNum].green;
  blue_tl += (int)src[ridxNum].blue;
  red_tr += (int)src[ridxNum].red;
  green_tr += (int)src[ridxNum].green;
  blue_tr += (int)src[ridxNum].blue;
  red_bl += (int)src[ridxNum].red;
  green_bl += (int)src[ridxNum].green;
  blue_bl += (int)src[ridxNum].blue;
  red_br += (int)src[ridxNum].red;
  green_br += (int)src[ridxNum].green;
  blue_br += (int)src[ridxNum].blue;


  // Only 4 neighbors
  top_left_pixel.red = (unsigned short)(red_tl / 4);
  top_left_pixel.green = (unsigned short)(green_tl / 4);
  top_left_pixel.blue = (unsigned short)(blue_tl / 4);

  // Only 2 neighbors
  top_right_pixel.red = (unsigned short)(red_tr / 2);
  top_right_pixel.green = (unsigned short)(green_tr / 2);
  top_right_pixel.blue = (unsigned short)(blue_tr / 2);

  // Only 2 neighbors
  bottom_left_pixel.red = (unsigned short)(red_bl / 2);
  bottom_left_pixel.green = (unsigned short)(green_bl / 2);
  bottom_left_pixel.blue = (unsigned short)(blue_bl / 2);

  // Only 1 neighbor
  bottom_right_pixel.red = (unsigned short)(red_br);
  bottom_right_pixel.green = (unsigned short)(green_br);
  bottom_right_pixel.blue = (unsigned short)(blue_br);

  dst[RIDX(i, j, dim)] = top_left_pixel;
  dst[RIDX(i, j + 1, dim)] = top_right_pixel;
  dst[RIDX(i + 1, j, dim)] = bottom_left_pixel;
  dst[RIDX(i + 1, j + 1, dim)] = bottom_right_pixel;
}

/*********************************************************************
 * register_motion_functions - Register all of your different versions
 *     of the motion kernel with the driver by calling the
 *     add_motion_function() for each test function.  When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_motion_functions()
{
  add_motion_function(&motion, motion_descr);
  add_motion_function(&naive_motion, naive_motion_descr);
}
