# CS3505 A3: Makefile for making TrieTest executable.
# @file Makefile
# @author Harry Kim
# @version 1.0 9/24/21

# Removed -lz on lines 12, 15, and 18 for autograder.

CC = g++
CFLAGS = -Wall -std=c++11

TrieTest : Trie.o TrieTest.o
	$(CC) $(CFLAGS) -o TrieTest Trie.o TrieTest.o

Trie.o : Trie.cpp Trie.h
	$(CC) $(CFLAGS) -c Trie.cpp

TrieTest.o : TrieTest.cpp Trie.h
	$(CC) $(CFLAGS) -c TrieTest.cpp

# add a make clean and make test option
clean:
	rm -f TrieTest *.o

test:
	./TrieTest "wordsList.txt" "queryList.txt"

# Used for compiling code to debug.
c:
	g++ -std=c++11 -g -o TrieTest Trie.cpp TrieTest.cpp
	./TrieTest