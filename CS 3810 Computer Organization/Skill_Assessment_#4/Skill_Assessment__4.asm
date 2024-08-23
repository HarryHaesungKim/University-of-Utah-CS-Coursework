##############################################################################
# Skill Assessment #4 
# 
# This program (when complete) will sort an array of numbers and print them
# out.  Your task is to hand-compile the code for the function that sorts
# the numbers.  You MUST follow the code outline - do not rearrange or
# optimize the algorithm.  Just convert each line of the algorithm
# into a small group of assembly statements.
#
# Several test cases are given below - feel free to adjust the code in 
# 'main' to test one of the other arrays.
#
# P.S.  I've always loved assembly language coding for breaking tasks into
# small, simpler steps.  It was great fun to write the solution, and I hope
# you find a bit of elegance or joy in your coding.  -- PJ
##############################################################################

# Data memory below this point.				
	.data
	
TestArray1:  # 3 elements
	.word 68, 4, 19	
	
TestArray2:  # 9 elements
	.word 9, 3, 7, 5, 1, 2, 4, 6, 8
	
TestArray3:  # 20 elements
        .word 20, 41, 38, 15, 96, 23, 13, 92, 91, 25, 17, 14, 40, 79, 51, 11, 15, 63, 64, 81 
        
Message1:
	.asciiz "*** Program has started. ***\n" 	
	
Message2:
	.asciiz "Unsorted data: " 	
	
Message3:
	.asciiz "Sorted data: " 	
	
Message4:
     	.asciiz "The number 42 from the stack, printed twice: "	
	
Message5:
	.asciiz "*** Program has ended. ***\n"
	
	
# Students should place additional asciiz data (and labels) below this line.

Prompt1:
	.asciiz "Entering sort: "
	
Prompt2:
	.asciiz "Returning from sort: "
	
newline:
	.asciiz "\n"
			
# Program memory below this point.				
	.text
	
Main:
	# Caution - Remember - You can change the code in Main for testing but
	# we will replace this code with our own code for official tests.
	# Do NOT do anything here that your sort function will depend on.

	# Before doing anything else, print a message to the screen.
	#   Normally you wouldn't do this, but for testing it is nice to know
	#   that your program is running.
	
	la   $a0, Message1
	li   $v0, 4         # Print string syscall code
	syscall

	# Give our 'main' function a stack frame.  We won't use it, but we
	#   make sure to have one.  For testing, I'll create a frame large
	#   enough to hold ten words, and I'll put a 42 in the first position
	#   just for fun.  Also, because I use $s0 and $s1 below, I'll preserve them,
	#   and I'll also preserve the old frame pointer (whatever it was).
	
	li   $t0, 42        # I am doing this just for testing, no good reason.
	
	addi $sp, $sp, -40  # Create my stack frame.  (Really, just keep track of a new, smaller address.)
	sw   $fp, 12($sp)   # Preserve $fp by storing it in our stack frame.
	sw   $s1,  8($sp)   # Preserve $s1 by storing it in our stack frame.
	sw   $s0,  4($sp)   # Preserve $s0 by storing it in our stack frame.
	sw   $t0,  0($sp)   # Put a 42 in our frame (just to show you it can be done).
	addi $fp, $sp, 36   # The frame pointer address is now the address of 
	                    #   the top word (the tenth word, word 9) of our stack frame).
	                    
	# Set up for testing:
	#   $s0 - contains the base address of an array.
	#   $s1 - contains the length of an array.
	
	la   $s0, TestArray2
	li   $s1, 9
	  
	# Print out the unsorted array.
	
	la   $a0, Message2
	li   $v0, 4         # Print string syscall code
	syscall

        move $t0, $s0       # Copy the array base into $t0
        move $t1, $s1       # Copy the array length into $t1
                            #   $t1 represents the number of values we need to print        
Loop1Top:
        beq  $t1, $zero, Loop1End # If we have zero items left to print, exit the loop.
        
        lw   $a0, 0($t0)    # Load an integer from the array
        li   $v0, 1         # Print integer syscall code
	syscall
	
        li   $a0, 32        # ASCII code for a space character
	li   $v0, 11        # Print character syscall code
	syscall
	
	addi $t1, $t1, -1   # We now have one less item to print.
	addi $t0, $t0, 4    # Advance the address to the address of the next item.
	j    Loop1Top       # Go back and loop again.

Loop1End:

        li   $a0, 10        # ASCII code for a newline character
	li   $v0, 11        # Print character syscall code
	syscall
	
	# Next, go sort the array.  (Students will need to write this function
	#   or else the code will stop working at this point.  Students should not
	#   change the way this function is called.  Students should also not add
	#   code to "Main" to preserve anything else on the stack.)
	
	move $a0, $s0       # Copy the array address into the first parameter.
	li   $a1, 0         # Set the low index to 0 (the index of the first array element)
	addi $a2, $s1, -1   # Set the high index to length-1 (the index of the last array element)
	jal  Sort           # Sort the array from positions lowIndex to highIndex (inclusive)

	# Print out the sorted array.
	
	la   $a0, Message3
	li   $v0, 4         # Print string syscall code
	syscall

        move $t0, $s0       # Copy the array base into $t0
        move $t1, $s1       # Copy the array length into $t1
                            #   $t1 represents the number of values we need to print        
Loop2Top:
        beq  $t1, $zero, Loop2End # If we have zero items left to print, exit the loop.
        
        lw   $a0, 0($t0)    # Load an integer from the array
        li   $v0, 1         # Print integer syscall code
	syscall
	
        li   $a0, 32        # ASCII code for a space character
	li   $v0, 11        # Print character syscall code
	syscall
	
	addi $t1, $t1, -1   # We now have one less item to print.
	addi $t0, $t0, 4    # Advance the address to the address of the next item.
	j    Loop2Top       # Go back and loop again.

Loop2End:

        li   $a0, 10        # ASCII code for a newline character
	li   $v0, 11        # Print character syscall code
	syscall
	
	# I stored a number 42 into the stack earlier.  Make sure it is still there, and
	#   use both the stack pointer and the frame pointer to access it.
	#   (This way, we make sure that the student has correctly restored the stack
	#   and that the student has not accidentally overwritten our data.)
	
	la   $a0, Message4
	li   $v0, 4         # Print string syscall code
	syscall
	
	lw   $a0, 0($sp)    # Load the 42 from our frame, use the stack pointer base address.
	li   $v0, 1         # Print integer syscall code
	syscall
	
	li   $a0, 32        # ASCII code for a space character
	li   $v0, 11        # Print character syscall code
	syscall
	
	lw   $a0, -36($fp)  # Load the 42 from our frame, use the frame pointer base address.
	li   $v0, 1         # Print integer syscall code
	syscall
	
	li   $a0, 10        # ASCII code for a newline character
	li   $v0, 11        # Print character syscall code
	syscall
	
	# Our 'Main' function is done.  Restore saved values from the stack frame and
	#   'remove' the stack frame.  We just adjust the $sp address, but this
	#   is how we keep track of stack frames, so the frame is 'removed'.  Since we
	#   grew the stack by 40 bytes above, we'll shrink it by 40 here.  (Remember,
	#   the stack 'grows' down, lower memory addresses = larger stack.)
	#
	# Important:  Don't 'remove' the frame until we have restored the data from it.
	
	lw   $fp, 12($sp)   # Restore $fp by loading it from our stack frame.
	lw   $s1,  8($sp)   # Restore $s1 by loading it from our stack frame.
	lw   $s0,  4($sp)   # Restore $s0 by loading it from our stack frame.
	addi $sp, $sp, 40   # Remove my stack frame.  (Really, just keep track of a new, larger address.)
	                    
	# Now that the program has finished, print a message to the screen.
	#   Normally you wouldn't do this, but for testing it is nice to know
	#   that your program has completed normally.
	
	la   $a0, Message5
	li   $v0, 4         # Print string syscall code
	syscall
	
	# Exit gracefully.

	li   $v0, 10         # Terminate program syscall code
	syscall
        # Simulator will now stop executing statements.

##############################################################################

# Student code for the sort function should go here.  I deleted my solution
#   code below, but I left my comments behind.  (Some comments I replaced with ???
#   because they gave away exactly what to do.)

# void sort (int[] data, int lowIndex, int highIndex)
Sort:

	# ???
        addi $sp, $sp, -16      # Save the saved registers and the return
        sw   $ra, 0($sp)        #  address on the stack so they can
        sw   $fp, 4($sp)        #  be restored when recursion ends.
        sw   $s4, 8($sp)
        sw   $s5, 12($sp)
        addi $fp, $sp, 16

	# (Move arguments into their registers)
	move $s3, $a0 # data: Array address, it arrives in $a0 but your code will copy $a0 to $s3 and use $s3 instead.
	move $s4, $a1 # lowIndex: integer, it arrives in $a1 but your code will copy $a1 to $s4 and use $s4 instead.
	move $s5, $a2 # highIndex: integer, it arrives in $a2 but your code will copy $a2 to $s5 and use $s5 instead.
	
	# print ("Entering sort: ")
	la $a0, Prompt1   # Put the address of the string into $a0
	li $v0, 4        # 4 -- Syscall for print string
	syscall
        
        # print (lowIndex)
	li    $v0, 1
	la    $a0, ($s4)
	syscall
	
        # print (" ")
	li    $v0, 11
	li    $a0, 32
	syscall
	
        # print (highIndex)
	li    $v0, 1
	la    $a0, ($s5)
	syscall
	
        # print ("\n")
	li    $v0, 4
	la    $a0, newline
	syscall
	
        # if (lowIndex  >=  highIndex)
	sge  $t0, $s4, $s5     # checks if lowIndex >= highIndex (1 if true, 0 if false)
	beq  $t0, $zero, EndOfIfStatement1 # if lowIndex >= highIndex is equal to zero (false), goes to EndOfIfStatement.
        
	#     print ("Returning from sort: ")
	la $a0, Prompt2   # Put the address of the string into $a0
	li $v0, 4        # 4 -- Syscall for print string
	syscall
	
        #     print (lowIndex)
        li    $v0, 1
	la    $a0, ($s4)
	syscall
	
        #     print (" ")
        li    $v0, 11
	li    $a0, 32
	syscall
	
        #     print (highIndex)
	li    $v0, 1
	la    $a0, ($s5)
	syscall
	
        #     print ("\n")
	li    $v0, 4
	la    $a0, newline
	syscall
	
	# ???
        lw   $ra, 0($sp)        # Restore the saved registers and return address, restore
        lw   $fp, 4($sp)        #  the stack pointer, then return to the caller.
        lw   $s4, 8($sp)
        lw   $s5, 12($sp)
        addi $sp, $sp, 16
	
        #     return
        j Return
        
EndOfIfStatement1:
        
    	# centerValue = data[highIndex]
    	move $t1, $s3 # Put address of data into $t1.
    	move $t0, $s5 # Put the index ($s5 = highadress) into $t0.
    	add $t0, $t0, $t0
    	add $t0, $t0, $t0 # mulitplying by 4.
    	add $t0, $t0, $t1 # combine the two components of the address
	lw $t1, 0($t0) # get the value from the array cell
    	move $t9, $t1 # centerValue: $t9 integer
    	
    	# centerIndex = lowIndex
    	move $t8, $s4 # centerIndex: $t8 integer

    	# tempIndex   = lowIndex
    	move $t6, $s4 #tempIndex $t6 integer
    	
    	# while (tempIndex < highIndex)
BeginningOfSortWhileLoop1:

    	#    tempValue = data[tempIndex]
    	move $t1, $s3 # Put address of data into $t1.
    	move $t0, $t6 # Put the index ($t6 = tempIndex) into $t0.
    	add $t0, $t0, $t0
    	add $t0, $t0, $t0 # mulitplying by 4.
    	add $t0, $t0, $t1 # combine the two components of the address
	lw $t1, 0($t0) # get the value from the array cell
    	move $t7, $t1 # tempValue: $t7 integer
    	
   	#    if (tempValue < centerValue)
	slt  $t2, $t7, $t9 # checks if tempValue < centerValue (1 if true, 0 if false)
	beq  $t2,$zero,EndOfIfStatement2 # if lowIndex < highIndex is not equal to zero (false), goes to EndOfIfStatement1
	
   	#        data[tempIndex]   = data[centerIndex]
    	move $t3, $s3 # Put address of data into $t3.
    	move $t2, $t8 # Put the index ($t8 = centerIndex) into $t0.
    	add $t2, $t2, $t2
    	add $t2, $t2, $t2 # mulitplying by 4.
    	add $t2, $t2, $t3 # combine the two components of the address
	lw $t3, 0($t2) # get the value from the array cell
	sw $t3, 0($t0) # Store contents of data[centerIndex] to data[tempIndex]
	
   	#        data[centerIndex] = tempValue 
   	sw $t7, 0($t2) # Store contents of tempValue to data[centerIndex]
    	
   	#        centerIndex       = centerIndex + 1
   	addi $t8, $t8, 1

EndOfIfStatement2:
   	
   	#    tempIndex = tempIndex + 1
   	addi $t6, $t6, 1
   	
        # While loop ends. while (tempIndex < highIndex)
	slt  $t0, $t6, $s5     # checks if tempIndex < highIndex (1 if true, 0 if false)
	bne  $t0, $zero, BeginningOfSortWhileLoop1 # if tempIndex < highIndex is not equal to one (true), goes to EndOfIfStatement1
   	
	move $a0, $s3
	move $a1, $s4 # lowIndex: integer, it arrives in $a1 but your code will copy $a1 to $s4 and use $s4 instead.
	subi $t0, $t8, 1 # centerIndex - 1
	move $a2, $t0 # centerIndex: $t8 integer
	
   	# ???
   	add  $sp, $sp, -16       # Expand stack to make room for another variable.
   	sw   $ra, 0($sp)        # Preserve return address (it may get overwritten by the procedure call)
   	sw   $s5, 4($sp)        # Preserve highIndex (it may get overwritten by the procedure call)
   	sw   $t8, 8($sp)        # Preserve centerIndex (it may get overwritten by the procedure call)
   	sw   $t9, 12($sp)        # Preserve centerValue (it may get overwritten by the procedure call)
	
	# sort (data, lowIndex, centerIndex-1)
	jal Sort
	
	# ???
	lw   $ra, 0($sp)
   	lw   $s5, 4($sp)        # Restore highIndex (it may get overwritten by the procedure call)
   	lw   $t8, 8($sp)        # Restore centerIndex (it may get overwritten by the procedure call)
   	lw   $t9, 12($sp)        # Restore centerValue (it may get overwritten by the procedure call)
	add  $sp, $sp, 16	# Undo previous stack expansion

   	# data[highIndex]
   	move $t1, $s3 # Put address of data into $t1.
    	move $t0, $s5 # Put the index ($s5 = highIndex) into $t0.
    	add $t0, $t0, $t0
    	add $t0, $t0, $t0 # mulitplying by 4.
    	add $t0, $t0, $t1 # combine the two components of the address
    	
    	# data[centerIndex]
    	move $t3, $s3 # Put address of data into $t3.
    	move $t2, $t8 # Put the index ($t8 = centerIndex) into $t2.
    	add $t2, $t2, $t2
    	add $t2, $t2, $t2 # mulitplying by 4.
    	add $t2, $t2, $t3 # combine the two components of the address
    	
    	lw $t4, 0($t2) # loading contents of data[centerIndex] to $t4
    	
	# data[highIndex] = data[centerIndex]
    	sw $t4, 0($t0)
   	
        # data[centerIndex] = centerValue
        sw $t9, 0($t2)
        
	move $a0, $s3
	addi $t0, $t8, 1
	move $a1, $t0 # centerIndex: $t8: integer
	move $a2, $s5 # highIndex: $s5: integer, it arrives in $a2 but your code will copy $a2 to $s5 and use $s5 instead
   	
	# sort (data, centerIndex+1, highIndex)
	jal Sort
        
	# print ("Returning from sort: ")
	la $a0, Prompt2   # Put the address of the string into $a0
	li $v0, 4        # 4 -- Syscall for print string
	syscall
        
        #     print (lowIndex)
        li    $v0, 1
	la    $a0, ($s4)
	syscall
	
        #     print (" ")
        li    $v0, 11
	li    $a0, 32
	syscall
	
        #     print (highIndex)
	li    $v0, 1
	la    $a0, ($s5)
	syscall
	
        #     print ("\n")
	li    $v0, 4
	la    $a0, newline
	syscall
	
	# ???
        lw   $ra, 0($sp)        # Restore the saved registers and return address, restore
        lw   $fp, 4($sp)        #  the stack pointer, then return to the caller.
        lw   $s4, 8($sp)
        lw   $s5, 12($sp)
        addi $sp, $sp, 16

Return:
        
    	# return
    	jr $ra