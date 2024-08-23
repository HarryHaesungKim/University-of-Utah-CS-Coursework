/**
 * CS3505 A4: Header file for Trie.cpp.
 * @file Trie.h
 * @author Harry Kim & Braden Morfin
 * @version 1.0 10/1/21
 */

#ifndef TRIE
#define TRIE

#include <iostream>
#include <string>
#include <vector>
#include <map>

using namespace std;

class Trie
{
private:
    map<char, Trie> children;
    bool endOfWord;

    // Private helper functions.
    Trie findPrefix(string prefix);
    void buildWordsAndAddToVector(vector<string> &words, string prefix);

public:
    Trie();
    Trie(const Trie &other);
    Trie &operator=(Trie other);

    // Public functions.
    void addAWord(string input);
    bool isAWord(string input);
    vector<string> allWordsStartingWithPrefix(string input);
};

#endif