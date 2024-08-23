# CS3505 A2: Makefile for making spiralPDF executable.
# @file Makefile
# @author Harry Kim
# @version 1.0 9/13/21

# Removed -lz from line 13, 16, 19, and 22 for autograder.

CC = g++
CFLAGS = -Wall -std=c++11
LIBHARU = ./libharu

spiralPDF : HaruPDF.o Spiral.o spiralPDF.o
	$(CC) $(CFLAGS) -o spiralPDF HaruPDF.o Spiral.o spiralPDF.o -L$(LIBHARU)/src -I$(LIBHARU)/include -lhpdfs -lz

HaruPDF.o : HaruPDF.cpp HaruPDF.h
	$(CC) $(CFLAGS) -c HaruPDF.cpp -L$(LIBHARU)/src -I$(LIBHARU)/include -lhpdfs -lz

Spiral.o : Spiral.cpp Spiral.h
	$(CC) $(CFLAGS) -c Spiral.cpp -lhpdfs -lz

spiralPDF.o : spiralPDF.cpp HaruPDF.h Spiral.h
	$(CC) $(CFLAGS) -c spiralPDF.cpp -L$(LIBHARU)/src -I$(LIBHARU)/include -lhpdfs -lz

# add a make clean and make test option
clean:
	rm -f spiralPDF *.o libSpiralPDF.a

test:
	./spiralPDF "Honestly I am so tired. I should really get some sleep. Getting a late start on assignments because of other assignments really sucks."

smalltest:
	./spiralPDF "wee woo wee woo wee woo wee woo"

longtest:
	./spiralPDF "Hey haha look at me spiraling out into oblivion. Look at me now, dad! You can't call me a failiure anymore. I bet you can't read the rest of what my text says without being a cheater by looking in the code. You looked, didn't you? You cheater. I'm disappointed in you. Shame. Shame on thee!"