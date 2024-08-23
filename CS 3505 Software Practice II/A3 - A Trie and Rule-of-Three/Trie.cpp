/**
 * CS3505 A3: Represents a Trie data structure using letters.
 * @file Trie.cpp
 * @author Harry Kim
 * @version 1.0 9/24/21
 */

#include <iostream>
#include <string>
#include <vector>
#include "Trie.h"

using namespace std;

Trie::Trie()
{
    currentLetter = "root";
    endOfWord = false;
}

Trie::Trie(char letter)
{
    currentLetter = letter;
    endOfWord = false;
}

Trie::~Trie()
{
    for (Trie *pointer : letArray)
    {
        delete pointer;
    }
}

Trie::Trie(const Trie &other)
{
    currentLetter = other.currentLetter;
    endOfWord = other.endOfWord;
    int i = 0;
    for (Trie *pointer : other.letArray)
    {
        if (pointer)
        {
            letArray[i] = new Trie(*other.letArray[i]);
        }
        else
        {
            letArray[i] = nullptr;
        }
        i++;
    }
}

Trie &Trie::operator=(Trie other)
{
    swap(currentLetter, other.currentLetter);
    // iterate through all letArray elements then swap.
    for (int i = 0; i < 26; i++)
    {
        swap(letArray[i], other.letArray[i]);
    }
    swap(endOfWord, other.endOfWord);
    return *this;
}

/**
 * This fuction adds a word to the Trie.
 * 
 * @param input The word to be added.
 */
void Trie::addAWord(string input)
{

    if (letArray[int(input[0]) - 'a'])
    {
        // If the letters of the input are exhausted, end function.
        if (input.empty())
        {
            endOfWord = true;
            return;
        }

        // Recursion
        // Making input shorter (removes first letter) since a trie object at the corresponding number already exists.
        letArray[int(input[0]) - 'a']->addAWord(input.erase(0, 1));
    }
    else
    {
        // If the letters of the input are exhausted, end function.
        if (input.empty())
        {
            endOfWord = true;
            return;
        }

        // Creating a new trie object at the root's corresponding number.
        Trie *newTrie = new Trie(input[0]);
        letArray[int(input[0]) - 'a'] = newTrie;

        // Making input shorter (removes first letter).
        // Recursion.
        newTrie->addAWord(input.erase(0, 1));
    }
}

/**
 * This fuction searches for a word to the Trie.
 * 
 * @param input The word to be searched.
 */
bool Trie::isAWord(string input)
{
    if (letArray[int(input[0]) - 'a'] && !input.empty())
    {
        Trie &newTrie = *letArray[int(input[0]) - 'a'];
        return newTrie.isAWord(input.erase(0, 1));
    }
    else
    {
        if (endOfWord)
        {
            return true;
        }
        return false;
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
    
    // Need newTrie so that the current trie doesn't get messed up when trying to find the prefix.
    Trie newTrie = Trie(*this);
    findPrefix(newTrie, input);
    // Making sure an empty input is still valid.
    if(newTrie.currentLetter == "root" && !input.empty()){
        newTrie.currentLetter = "noWordsExistWithThisInput";
    }
    newTrie.buildWords(words, input);
    return words;
}

/**
 * This helper function iterates through the trie to get in posiiton to start building words using the prefix.
 * 
 * @param inputTrie A reference to the trie that will be iterated through.
 * @param prefix The prefix.
 */
Trie *Trie::findPrefix(Trie &inputTrie, string prefix)
{
    for (Trie *pointer : letArray)
    {
        if (pointer)
        {
            if (prefix.length() == 1 && pointer->currentLetter[0] == prefix[0])
            {
                inputTrie = *pointer;
                break;
            }
            if (pointer->currentLetter[0] == prefix[0])
            {
                return pointer->findPrefix(inputTrie, prefix.erase(0, 1));
            }
        }
    }
    return this;
}

/**
 * This fuction builds the words and adds them to the words vector.
 * 
 * @param words The vector to hold the completed words.
 * @param prefix The prefix used for the first part of the word.
 */
void Trie::buildWords(vector<string> &words, string prefix)
{
    string wordWithoutPrefix;
    if(currentLetter == "noWordsExistWithThisInput"){
        return;
    }
    for (Trie *pointer : letArray)
    {
        if (pointer)
        {
            if (!pointer->endOfWord)
            {
                prefix.push_back(pointer->currentLetter[0]);
                pointer->buildWords(words, prefix);
                prefix.pop_back();
            }
            else
            {
                prefix.push_back(pointer->currentLetter[0]);
                words.push_back(prefix);
                prefix.pop_back();
            }
        }
    }
    // If it went through all the letters and prefix is a word, add in the prefix;
    if(words.empty() && Trie(*this).isAWord(prefix)){
        words.push_back(prefix);
        return;
    }
}