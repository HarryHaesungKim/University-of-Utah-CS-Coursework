/**
 * CS3505 A4: Represents a Trie data structure using letters.
 * @file Trie.cpp
 * @author Harry Kim & Braden Morfin
 * @version 1.0 10/1/21
 */

#include <iostream>
#include <string>
#include <vector>
#include "Trie.h"

using namespace std;
/**
 * Default contructor for a Trie object.
 */
Trie::Trie()
{
    endOfWord = false;
}

/**
 * Contructs a Trie object using a another Trie object.
 * 
 * @param other other trie used to construct this trie.
 */
Trie::Trie(const Trie &other)
{
    endOfWord = other.endOfWord;
    children = other.children;
}

/**
 * Overloaded assignment operator that assigns this Tries data to other Tries data.
 * 
 * @param other other trie to assign this trie to.
 */
Trie &Trie::operator=(Trie other)
{
    swap(endOfWord, other.endOfWord);
    swap(children, other.children);
    return *this;
}

/**
 * This fuction adds a word to the Trie.
 * 
 * @param input The word to be added.
 */
void Trie::addAWord(string input)
{
    char currentCharacter = input[0];
    if (input.empty())
    {
        endOfWord = true;
        return;
    }
    if (children.count(currentCharacter) == 0)
    {
        Trie newBranch = Trie();
        children[currentCharacter] = newBranch;
    }
    children[currentCharacter].addAWord(input.erase(0, 1));
}

/**
 * This fuction searches for a word in the Trie.
 * 
 * @param input The word to be searched.
 */
bool Trie::isAWord(string input)
{
    if (children.count(input[0]) != 0 && !input.empty())
    {
        Trie &newTrie = children[input[0]];
        return newTrie.isAWord(input.erase(0, 1));
    }
    else
    {
        return endOfWord;
    }
}

/**
 * This fuction returns all words starting with a given prefix.
 * 
 * @param input The prefix.
 */
vector<string> Trie::allWordsStartingWithPrefix(string input)
{
    vector<string> words = vector<string>();
    Trie saveRoot = Trie(*this);
    *this = findPrefix(input);
    if (endOfWord)
    {
        words.push_back(input);
    }
    buildWordsAndAddToVector(words, input);
    *this = saveRoot;
    return words;
}

/**
 * This helper function iterates through the trie to get in posiiton to start building words using the prefix.
 * 
 * @param prefix The prefix.
 */
Trie Trie::findPrefix(string prefix)
{
    unsigned int counter = 0;
    for (auto &child : children)
    {
        if (prefix.length() == 1 && child.first == prefix[0])
        {
            *this = child.second;
            counter++;
            break;
        }
        if (child.first == prefix[0])
        {
            counter++;
            return child.second.findPrefix(prefix.erase(0, 1));
        }
    }
    if (counter == prefix.length())
    {
        return *this;
    }
    else
    {
        Trie emptyTrie = Trie();
        return emptyTrie;
    }
}

/**
 * This fuction builds the words and adds them to the words vector.
 * 
 * @param words The vector to hold the completed words.
 * @param prefix The prefix used for the first part of the word.
 */
void Trie::buildWordsAndAddToVector(vector<string> &words, string prefix)
{
    for (auto &child : children)
    {
        prefix.push_back(child.first);
        if (child.second.endOfWord)
        {
            words.push_back(prefix);
        }
        child.second.buildWordsAndAddToVector(words, prefix);
        prefix.pop_back();
    }
}