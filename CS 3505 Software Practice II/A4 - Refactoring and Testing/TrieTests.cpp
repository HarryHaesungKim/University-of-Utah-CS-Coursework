/**
 * CS3505 A4: Tests Trie.cpp using googletests.
 * @file TrieTests.cpp
 * @author Harry Kim & Braden Morfin
 * @version 1.0 10/1/21
 */

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include "gtest/gtest.h"
#include "Trie.h"

TEST(TrieTests, BasicCases)
{
    // Adding words to Trie.
    Trie myBrandNewTrie = Trie();
    myBrandNewTrie.addAWord("hello");
    myBrandNewTrie.addAWord("health");
    myBrandNewTrie.addAWord("heaven");
    myBrandNewTrie.addAWord("hound");
    myBrandNewTrie.addAWord("water");
    myBrandNewTrie.addAWord("wazzup");

    bool judgement = myBrandNewTrie.isAWord("water");
    EXPECT_TRUE(judgement);

    bool judgement2 = myBrandNewTrie.isAWord("hello");
    EXPECT_TRUE(judgement2);

    bool judgement4 = myBrandNewTrie.isAWord("helli");
    EXPECT_FALSE(judgement4);

    bool judgement5 = myBrandNewTrie.isAWord("health");
    EXPECT_TRUE(judgement5);

    bool judgement6 = myBrandNewTrie.isAWord("");
    EXPECT_FALSE(judgement6);

    vector<string> wordsWithPrefix = myBrandNewTrie.allWordsStartingWithPrefix("he");
    EXPECT_EQ(wordsWithPrefix.size(), 3);

    EXPECT_EQ(wordsWithPrefix[0], "health");
    EXPECT_EQ(wordsWithPrefix[1], "heaven");
    EXPECT_EQ(wordsWithPrefix[2], "hello");
}

TEST(TrieTests, EdgeCases)
{
    Trie myBrandNewTrie = Trie();
    myBrandNewTrie.addAWord("he");
    myBrandNewTrie.addAWord("hello");
    myBrandNewTrie.addAWord("health");
    myBrandNewTrie.addAWord("heaven");
    myBrandNewTrie.addAWord("hound");
    myBrandNewTrie.addAWord("water");
    myBrandNewTrie.addAWord("wazzup");

    bool judgement = myBrandNewTrie.isAWord("");
    EXPECT_FALSE(judgement);

    vector<string> wordsWithPrefix = myBrandNewTrie.allWordsStartingWithPrefix("zebra");
    EXPECT_EQ(wordsWithPrefix.size(), 0);

    vector<string> wordsWithPrefix2 = myBrandNewTrie.allWordsStartingWithPrefix("he");
    EXPECT_EQ(wordsWithPrefix2.size(), 4);

    bool judgement3 = myBrandNewTrie.isAWord("hell");
    EXPECT_FALSE(judgement3);
}

TEST(TriTests, CopyAndAssignmentBehavior)
{
    // Adding words to Trie.
    Trie myBrandNewTrie = Trie();
    myBrandNewTrie.addAWord("hello");
    myBrandNewTrie.addAWord("health");
    myBrandNewTrie.addAWord("heaven");
    myBrandNewTrie.addAWord("hound");
    myBrandNewTrie.addAWord("water");
    myBrandNewTrie.addAWord("wazzup");

    // Copy constructor tests
    Trie newTrie = Trie(myBrandNewTrie);

    bool judgement = newTrie.isAWord("water");
    EXPECT_TRUE(judgement);

    bool judgement2 = newTrie.isAWord("hello");
    EXPECT_TRUE(judgement2);

    bool judgement3 = newTrie.isAWord("helli");
    EXPECT_FALSE(judgement3);

    bool judgement4 = newTrie.isAWord("health");
    EXPECT_TRUE(judgement4);

    bool judgement5 = newTrie.isAWord("");
    EXPECT_FALSE(judgement5);

    vector<string> wordsWithPrefix = newTrie.allWordsStartingWithPrefix("he");
    EXPECT_EQ(wordsWithPrefix.size(), 3);

    EXPECT_EQ(wordsWithPrefix[0], "health");
    EXPECT_EQ(wordsWithPrefix[1], "heaven");
    EXPECT_EQ(wordsWithPrefix[2], "hello");

    // Assignment operator tests

    Trie anotherNewTrie;
    anotherNewTrie = myBrandNewTrie;

    bool judgement6 = anotherNewTrie.isAWord("water");
    EXPECT_TRUE(judgement6);

    bool judgement7 = anotherNewTrie.isAWord("hello");
    EXPECT_TRUE(judgement7);

    bool judgement8 = anotherNewTrie.isAWord("helli");
    EXPECT_FALSE(judgement8);

    bool judgement9 = anotherNewTrie.isAWord("health");
    EXPECT_TRUE(judgement9);

    bool judgement10 = anotherNewTrie.isAWord("");
    EXPECT_FALSE(judgement10);

    vector<string> wordsWithPrefix2 = anotherNewTrie.allWordsStartingWithPrefix("he");
    EXPECT_EQ(wordsWithPrefix2.size(), 3);

    EXPECT_EQ(wordsWithPrefix2[0], "health");
    EXPECT_EQ(wordsWithPrefix2[1], "heaven");
    EXPECT_EQ(wordsWithPrefix2[2], "hello");
}