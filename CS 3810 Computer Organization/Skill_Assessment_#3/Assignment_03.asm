# Harry Kim
# u1226472
# Skill Assessment #3

	.data
	
newline:

	.asciiz "\n"

Array:	

	.word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1

	.text

main:

	li    $s2, 16 # Counter for how many lines of Pascal's triangle should be printed.

loopCompute:

	jal PrintNonZeroNums
	
	jal function1
	
	# Printing new line character
	li    $v0, 4
	la    $a0, newline
	syscall
	
	sub   $s2, $s2, 1 # Decrement counter.
	
	bne   $s2, $zero, loopCompute
	
	li    $v0, 10 # Syscall code for graceful exit
	syscall

# This function calculates Pascal's triange and saves it to the array.
function1:

	li    $s0, 16 # A counter for the elements in the array.
	la    $t1, Array
            
startFunction1:	
            
	# Increment Integer1 and print
	lw    $t0, ($t1)
	lw    $t2, 4($t1)
	li    $v0, 1
	add   $t0, $t0, $t2

	sw    $t0, ($t1)
	addi  $t1, $t1, 4
            
	# Decrement and set counter
	sub   $s0, $s0, 1
	li    $v0, 1    
	move  $a0, $s0
	
	bne   $s0, $zero, startFunction1
            
endFunction1:
	
	jr    $ra

# This function prints all non-zero numbers.
PrintNonZeroNums:

	li    $s0, 16 # A counter for the elements in the array.
	la    $t1, Array

startLoop1:

	# Increment Array and print
	lw    $t0, ($t1)
	bne   $t0, $zero, PrintNumber # Skipping numbers that are zero.
	sw    $t0, ($t1)
	addi  $t1, $t1, 4
	
	# Decrement counter.
	sub   $s0, $s0, 1
	bne   $s0, $zero, startLoop1

endLoop1:

	jr    $ra

# This helper function prints the non-zero numbers and a space.	
PrintNumber:

	li    $v0, 1
	move  $a0, $t0
	syscall
	
	# Print space
	li    $v0, 11
	li    $a0, 32
	syscall
	
	# Decrement counter.
	sub   $s0, $s0, 1
	
	# Next position in memory for array.
	sw    $t0, ($t1)
	addi  $t1, $t1, 4
	
	bne   $s0, $zero, startLoop1
	j endLoop1
