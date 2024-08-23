/*
 * Author: Daniel Kopta
 * Updated by: Erin Parker
 * CS 4400, University of Utah
 *
 * Simulator handout
 * A simple x86-like processor simulator.
 * Read in a binary file that encodes instructions to execute.
 * Simulate a processor by executing instructions one at a time and appropriately
 * updating register and memory contents.
 *
 * Some code and pseudo code has been provided as a starting point.
 *
 * Completed by: Harry Kim
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include "instruction.h"

// Forward declarations for helper functions
unsigned int get_file_size(int file_descriptor);
unsigned int *load_file(int file_descriptor, unsigned int size);
instruction_t *decode_instructions(unsigned int *bytes, unsigned int num_instructions);
unsigned int execute_instruction(unsigned int program_counter, instruction_t *instructions,
                                 int *registers, unsigned char *memory);
void print_instructions(instruction_t *instructions, unsigned int num_instructions);
void error_exit(const char *message);

// 17 registers
#define NUM_REGS 17
// 1024-byte stack
#define STACK_SIZE 1024

int main(int argc, char **argv)
{
  // Make sure we have enough arguments
  if (argc < 2)
    error_exit("must provide an argument specifying a binary file to execute");

  // Open the binary file
  int file_descriptor = open(argv[1], O_RDONLY);
  if (file_descriptor == -1)
    error_exit("unable to open input file");

  // Get the size of the file
  unsigned int file_size = get_file_size(file_descriptor);
  // Make sure the file size is a multiple of 4 bytes
  // since machine code instructions are 4 bytes each
  if (file_size % 4 != 0)
    error_exit("invalid input file");

  // Load the file into memory
  // We use an unsigned int array to represent the raw bytes
  // We could use any 4-byte integer type
  unsigned int *instruction_bytes = load_file(file_descriptor, file_size);
  close(file_descriptor);

  unsigned int num_instructions = file_size / 4;

  /****************************************/
  /**** Begin code to modify/implement ****/
  /****************************************/

  // Allocate and decode instructions (left for you to fill in)
  instruction_t *instructions = decode_instructions(instruction_bytes, num_instructions);

  // Optionally print the decoded instructions for debugging
  // Will not work until you implement decode_instructions
  // Do not call this function in your submitted final version
  // print_instructions(instructions, num_instructions);

  // Once you have completed Part 1 (decoding instructions), uncomment the below block

  // Allocate and initialize registers
  int *registers = (int *)malloc(sizeof(int) * 17);
  // TODO: initialize register values
  registers[0] = 0;
  registers[1] = 0;
  registers[2] = 0;
  registers[3] = 0;
  registers[4] = 0;
  registers[5] = 0;
  registers[6] = 1024;
  registers[7] = 0;
  registers[8] = 0;
  registers[9] = 0;
  registers[10] = 0;
  registers[11] = 0;
  registers[12] = 0;
  registers[13] = 0;
  registers[14] = 0;
  registers[15] = 0;
  registers[16] = 0;

  // Stack memory is byte-addressed, so it must be a 1-byte type
  // TODO allocate the stack memory. Do not assign to NULL.
  unsigned char *memory = (unsigned char *)malloc(sizeof(char) * 1024);

  for (int i = 0; i < 1024; i++)
  {
    memory[i] = 0;
  }

  // Run the simulation
  unsigned int program_counter = 0;

  // program_counter is a byte address, so we must multiply num_instructions by 4
  // to get the address past the last instruction
  while (program_counter != num_instructions * 4)
  {
    program_counter = execute_instruction(program_counter, instructions, registers, memory);
  }

  return 0;
}

/*
 * Decodes the array of raw instruction bytes into an array of instruction_t
 * Each raw instruction is encoded as a 4-byte unsigned int
 */
instruction_t *decode_instructions(unsigned int *bytes, unsigned int num_instructions)
{
  // TODO: Don't return NULL

  instruction_t *retval = (instruction_t *)malloc(num_instructions * sizeof(instruction_t));

  /*
  int i;
  for(i = ...){
    retval[i] = (fill in fields based on raw bits);
  */

  unsigned int opcodePos = 0xf8000000;
  int firstRegisterPos = 0x07c00000;
  int secondRegisterPos = 0x003e0000;
  int immediatePos = 0x0000ffff;

  int i;

  for (i = 0; i < num_instructions; i++)
  {

    unsigned int theByte = bytes[i];

    // Need to adjust to get proper number.

    retval[i].opcode = (theByte & opcodePos) >> 27;
    retval[i].first_register = (theByte & firstRegisterPos) >> 22;
    retval[i].second_register = (theByte & secondRegisterPos) >> 17;
    retval[i].immediate = (theByte & immediatePos) >> 0;
  }

  return retval;
}

/*
 * Executes a single instruction and returns the next program counter
 */
unsigned int execute_instruction(unsigned int program_counter, instruction_t *instructions, int *registers, unsigned char *memory)
{
  // program_counter is a byte address, but instructions are 4 bytes each
  // divide by 4 to get the index into the instructions array
  instruction_t instr = instructions[program_counter / 4];

  _Bool jump = 0;

  switch (instr.opcode)
  {
  case subl:
    registers[instr.first_register] = registers[instr.first_register] - instr.immediate;
    break;
  case addl_reg_reg:
    registers[instr.second_register] = registers[instr.first_register] + registers[instr.second_register];
    break;
  case addl_imm_reg:
    registers[instr.first_register] = registers[instr.first_register] + instr.immediate;
    break;
  case imull:
    registers[instr.second_register] = registers[instr.first_register] * registers[instr.second_register];
    break;
  case shrl:
    registers[instr.first_register] = (unsigned int)registers[instr.first_register] >> 1;
    break;
  case movl_reg_reg:
    registers[instr.second_register] = registers[instr.first_register];
    break;
  case movl_deref_reg:
    // Piecing back together the int from memory.

    // registers[instr.second_register] = ((memory[registers[instr.first_register] + 0] << 24) >> (instr.immediate * 8)) + ((memory[registers[instr.first_register] + 1] << 16) >> (instr.immediate * 8)) + ((memory[registers[instr.first_register] + 2] << 8) >> (instr.immediate * 8)) + ((memory[registers[instr.first_register] + 3] << 0) >> (instr.immediate * 8));
    registers[instr.second_register] = (memory[registers[instr.first_register] + instr.immediate + 0] << 24) + (memory[registers[instr.first_register] + instr.immediate + 1] << 16) + (memory[registers[instr.first_register] + instr.immediate + 2] << 8) + (memory[registers[instr.first_register] + instr.immediate + 3] << 0);


    break;
  case movl_reg_deref:
    // How to store int into a char array? piece by piece.

    // memory[registers[instr.second_register] + 0] = (int)(((registers[instr.first_register] >> 24) << (instr.immediate * 8)) & 0xFF);
    // memory[registers[instr.second_register] + 1] = (int)(((registers[instr.first_register] >> 16) << (instr.immediate * 8)) & 0xFF);
    // memory[registers[instr.second_register] + 2] = (int)(((registers[instr.first_register] >> 8) << (instr.immediate * 8)) & 0xFF);
    // memory[registers[instr.second_register] + 3] = (int)(((registers[instr.first_register] >> 0) << (instr.immediate * 8)) & 0xFF);

    memory[registers[instr.second_register] + instr.immediate + 0] = (int)((registers[instr.first_register] >> 24) & 0xFF);
    memory[registers[instr.second_register] + instr.immediate + 1] = (int)((registers[instr.first_register] >> 16) & 0xFF);
    memory[registers[instr.second_register] + instr.immediate + 2] = (int)((registers[instr.first_register] >> 8) & 0xFF);
    memory[registers[instr.second_register] + instr.immediate + 3] = (int)((registers[instr.first_register] >> 0) & 0xFF);

    // memory[registers[instr.second_register + instr.immediate] + 0] = (int)((registers[instr.first_register] >> 24) & 0xFF);
    // memory[registers[instr.second_register + instr.immediate] + 1] = (int)((registers[instr.first_register] >> 16) & 0xFF);
    // memory[registers[instr.second_register + instr.immediate] + 2] = (int)((registers[instr.first_register] >> 8) & 0xFF);
    // memory[registers[instr.second_register + instr.immediate] + 3] = (int)((registers[instr.first_register] >> 0) & 0xFF);

    break;
  case movl_imm_reg:
    registers[instr.first_register] = instr.immediate;
    break;

  case cmpl:

    // resetting eflags for new cmpl
    registers[16] = 0;

    int solution = registers[instr.second_register] - registers[instr.first_register];

    // If reg2 is positive, reg1 is negative, and solution is negative.
    if ((registers[instr.second_register] >= 0) && (registers[instr.first_register] < 0))
    {
      if (solution < 0)
      {
        // OF
        registers[16] = registers[16] | 0x00000800;
      }
    }
    // If reg2 is negative, reg1 is positive, and solution is positive.
    else if ((registers[instr.second_register] < 0) && (registers[instr.first_register] >= 0))
    {
      if (solution >= 0)
      {
        // OF
        registers[16] = registers[16] | 0x00000800;
      }
    }

    // If reg2 - reg1 = 0 ZF.
    if (solution == 0)
    {
      registers[16] = registers[16] | 0x00000040;
    }

    // If unsigned reg2 - unsigned reg1 < 0. Basically if unsigned reg1 > unsigned reg2, it will result in a negative number and will result in overflow.
    if ((unsigned int)registers[instr.first_register] > (unsigned int)registers[instr.second_register])
    {
      // CF
      registers[16] = registers[16] | 0x00000001;
    }

    // If most significant bit is 1 SF.
    if ((solution & 0x80000000) == 0x80000000)
    {
      registers[16] = registers[16] | 0x00000080;
    }
    break;

  case je:
    //
    if ((registers[16] & 0x00000040) == 0x00000040)
    {
      jump = 1;
      // set program counter to the target register.
    }

    break;
  case jl:
    //
    if (((registers[16] & 0x00000080) == 0x00000080) ^ ((registers[16] & 0x00000800) == 0x00000800))
    {
      jump = 1;
      // set program counter to target register.
    }

    break;
  case jle:
    //
    if ((((registers[16] & 0x00000080) == 0x00000080) ^ ((registers[16] & 0x00000800) == 0x00000800)) || ((registers[16] & 0x00000040) == 0x00000040))
    {
      jump = 1;
      // set program counter to target register.
    }

    break;
  case jge:
    //
    if (!((registers[16] & 0x00000080) == 0x00000080) ^ ((registers[16] & 0x00000800) == 0x00000800))
    {
      jump = 1;
      // set program counter to target register.
    }

    break;
  case jbe:
    //
    if (((registers[16] & 0x00000001) == 0x00000001) || ((registers[16] & 0x00000040) == 0x00000040))
    {
      jump = 1;
      // set program counter to target register.
    }

    break;
  case jmp:
    jump = 1;
    // Set program counter to target register.
    break;
  case call:
    //
    registers[6] = registers[6] - 4;

    memory[registers[6] + 0] = (int)(((program_counter + 4) >> 24) & 0xFF);
    memory[registers[6] + 1] = (int)(((program_counter + 4) >> 16) & 0xFF);
    memory[registers[6] + 2] = (int)(((program_counter + 4) >> 8) & 0xFF);
    memory[registers[6] + 3] = (int)(((program_counter + 4) >> 0) & 0xFF);

    return program_counter + 4 + instr.immediate;

    break;
  case ret:
    //
    if (registers[6] == 1024)
    {
      exit(0); // or whatever exit simulation is.
    }
    else
    {

      program_counter = ((memory[registers[6] + 0] << 24) + (memory[registers[6] + 1] << 16) + (memory[registers[6] + 2] << 8) + (memory[registers[6] + 3] << 0));

      registers[6] = registers[6] + 4;

      return program_counter;
    }
    break;
  case pushl:
    //
    registers[6] = registers[6] - 4;
    // memory[registers[6]] = registers[instr.first_register];

    memory[registers[6] + 0] = (int)((registers[instr.first_register] >> 24) & 0xFF);
    memory[registers[6] + 1] = (int)((registers[instr.first_register] >> 16) & 0xFF);
    memory[registers[6] + 2] = (int)((registers[instr.first_register] >> 8) & 0xFF);
    memory[registers[6] + 3] = (int)((registers[instr.first_register] >> 0) & 0xFF);

    break;

  case popl:
    //
    // registers[instr.first_register] = memory[registers[6]];

    registers[instr.first_register] = ((memory[registers[6] + 0] << 24) + (memory[registers[6] + 1] << 16) + (memory[registers[6] + 2] << 8) + (memory[registers[6] + 3] << 0));

    registers[6] = registers[6] + 4;
    break;
  case printr:
    printf("%d (0x%x)\n", registers[instr.first_register], registers[instr.first_register]);
    break;
  case readr:
    scanf("%d", &(registers[instr.first_register]));
    break;

    // TODO: Implement remaining instructions
  }

  // TODO: Do not always return program_counter + 4
  //       Some instructions jump elsewhere

  // program_counter + 4 represents the subsequent instruction
  if (jump)
  {
    program_counter = program_counter + 4 + instr.immediate;
  }
  else
  {
    program_counter += 4;
  }

  return program_counter;
}

/*********************************************/
/****  DO NOT MODIFY THE FUNCTIONS BELOW  ****/
/*********************************************/

/*
 * Returns the file size in bytes of the file referred to by the given descriptor
 */
unsigned int get_file_size(int file_descriptor)
{
  struct stat file_stat;
  fstat(file_descriptor, &file_stat);
  return file_stat.st_size;
}

/*
 * Loads the raw bytes of a file into an array of 4-byte units
 */
unsigned int *load_file(int file_descriptor, unsigned int size)
{
  unsigned int *raw_instruction_bytes = (unsigned int *)malloc(size);
  if (raw_instruction_bytes == NULL)
    error_exit("unable to allocate memory for instruction bytes (something went really wrong)");

  int num_read = read(file_descriptor, raw_instruction_bytes, size);

  if (num_read != size)
    error_exit("unable to read file (something went really wrong)");

  return raw_instruction_bytes;
}

/*
 * Prints the opcode, register IDs, and immediate of every instruction,
 * assuming they have been decoded into the instructions array
 */
void print_instructions(instruction_t *instructions, unsigned int num_instructions)
{
  printf("instructions: \n");
  unsigned int i;
  for (i = 0; i < num_instructions; i++)
  {
    printf("op: %d, reg1: %d, reg2: %d, imm: %d\n",
           instructions[i].opcode,
           instructions[i].first_register,
           instructions[i].second_register,
           instructions[i].immediate);
  }
  printf("--------------\n");
}

/*
 * Prints an error and then exits the program with status 1
 */
void error_exit(const char *message)
{
  printf("Error: %s\n", message);
  exit(1);
}