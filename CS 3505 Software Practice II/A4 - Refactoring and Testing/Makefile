# CS3505 A4: Makefile for making the TrieTests executable.
# @file Makefile
# @author Harry Kim & Braden Morfin
# @version 1.0 10/1/21

CC = g++
CFLAGS = -Wall -std=c++11
GOOGLETEST ?= ./googletest

TrieTests : Trie.o TrieTests.o
	$(CC) $(CFLAGS) -o TrieTests Trie.o TrieTests.o -L$(GOOGLETEST)/lib -I$(GOOGLETEST)/googletest/include -lgtest -lgtest_main -lpthread

Trie.o : Trie.cpp Trie.h
	$(CC) $(CFLAGS) -c Trie.cpp

TrieTests.o : TrieTests.cpp Trie.h
	$(CC) $(CFLAGS) -c TrieTests.cpp -L$(GOOGLETEST)/lib -I$(GOOGLETEST)/googletest/include -lgtest -lgtest_main -lpthread

# Added a make clean and make test option.
clean:
	rm -f TrieTests *.o

test:
	./TrieTests.exe