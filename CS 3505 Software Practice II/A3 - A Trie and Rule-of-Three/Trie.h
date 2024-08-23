/**
 * CS3505 A3: Header file for Trie.cpp.
 * @file Trie.h
 * @author Harry Kim
 * @version 1.0 9/24/21
 */

#ifndef TRIE
#define TRIE

#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Trie
{
private:
    Trie *letArray[26] = {nullptr};
    string currentLetter;
    bool endOfWord;

    // Private helper function.
    Trie *findPrefix(Trie &inputTrie, string prefix);
    void buildWords(vector<string> &words, string prefix);

public:
    Trie();
    Trie(char letter);

    // Rule of 3:
    ~Trie();
    Trie(const Trie &other);
    Trie &operator=(Trie other);

    // Public functions.
    void addAWord(string input);
    bool isAWord(string input);
    vector<string> allWordsStartingWithPrefix(string input);
};

#endif