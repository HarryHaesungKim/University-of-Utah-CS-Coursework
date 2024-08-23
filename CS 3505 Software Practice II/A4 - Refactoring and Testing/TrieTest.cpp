/**
 * CS3505 A3: Runs Trie code for testing and debugging.
 * @file TrieTest.cpp
 * @author Harry Kim
 * @version 1.0 9/24/21
 */

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include "Trie.h"

int main()
{
     Trie myBrandNewTrie = Trie();
     myBrandNewTrie.addAWord("he");
     myBrandNewTrie.addAWord("hello");
     myBrandNewTrie.addAWord("health");
     myBrandNewTrie.addAWord("heaven");
     myBrandNewTrie.addAWord("hound");
     myBrandNewTrie.addAWord("water");
     myBrandNewTrie.addAWord("wazzup");
     cout << endl;

     cout << "--------------------isAWord tests-----------------------" << endl;
     bool judgement = myBrandNewTrie.isAWord("water");
     cout << "Judgement 1 is (should be true 1): " << judgement << endl
          << endl;

     bool judgement2 = myBrandNewTrie.isAWord("hello");
     cout << "Judgement 2 is (should be true 1): " << judgement2 << endl
          << endl;

     bool judgement3 = myBrandNewTrie.isAWord("hell");
     cout << "Judgement 3 is (should be false 0): " << judgement3 << endl
          << endl;

     bool judgement4 = myBrandNewTrie.isAWord("helli");
     cout << "Judgement 4 is (should be false 0): " << judgement4 << endl
          << endl;

     bool judgement6 = myBrandNewTrie.isAWord("health");
     cout << "Judgement 6 is (should be true 1): " << judgement6 << endl
          << endl;

     bool judgement5 = myBrandNewTrie.isAWord("");
     cout << "Judgement 5 is (should be false 0): " << judgement5 << endl
          << endl;

     cout << endl;
     cout << "--------------------allWordsStartingWithPrefix tests-----------------------" << endl;

     vector<string> hWords = myBrandNewTrie.allWordsStartingWithPrefix("h");
     cout << "The vector elements are: ";
     for (unsigned int i = 0; i < hWords.size(); i++)
          cout << hWords.at(i) << ' ';
     cout << endl;

     vector<string> words = myBrandNewTrie.allWordsStartingWithPrefix("he");
     cout << "The vector elements are (prefix: he): ";
     for (unsigned int i = 0; i < words.size(); i++)
          cout << words.at(i) << ' ';
     cout << endl;

     vector<string> words2 = myBrandNewTrie.allWordsStartingWithPrefix("hi");
     cout << "The vector elements are (prefix: hi): ";
     for (unsigned int i = 0; i < words2.size(); i++)
          cout << words2.at(i) << ' ';
     cout << endl;

     vector<string> emptyWords = myBrandNewTrie.allWordsStartingWithPrefix("");
     cout << "The vector elements are: ";
     for (unsigned int i = 0; i < emptyWords.size(); i++)
          cout << emptyWords.at(i) << ' ';
     cout << endl;

     vector<string> wWords = myBrandNewTrie.allWordsStartingWithPrefix("w");
     cout << "The vector elements are: ";
     for (unsigned int i = 0; i < wWords.size(); i++)
          cout << wWords.at(i) << ' ';
     cout << endl;

     vector<string> notfound = myBrandNewTrie.allWordsStartingWithPrefix("zebra");
     cout << "The vector elements are: ";
     for (unsigned int i = 0; i < notfound.size(); i++)
          cout << notfound.at(i) << ' ';
     cout << endl;

     return 0;
}

// /**
//  * CS3505 A2: Runs spiral code and makes a pdf with text as spiral.
//  * @file TrieTest.cpp
//  * @author Harry Kim
//  * @version 1.0 9/24/21
//  */

// #include <iostream>
// #include <fstream>
// #include <string>
// #include <vector>
// #include "Trie.h"

// // two command-line arguments, each one a filename. The first is a file of words, each on their own line,
// // with the words all lowercase and only made up of characters a-z. The second is a file of queries, each
// // word on its own line and also of acceptable characters.
// int main(int argc, char **argv)
// {
//      vector<string> wordList;
//      vector<string> queriesList;

//      // If there's not enough arguments, program doesn't even try to run.
//      if (argc < 3)
//      {
//           std::cout << "Insufficient argument count." << std::endl;
//           return 0;
//      }

//      else
//      {
//           // Loading words and queries into vectors.
//           string line;
//           ifstream myfile(argv[1]);
//           if (myfile.is_open())
//           {
//                while (getline(myfile, line))
//                {
//                     wordList.push_back(line);
//                }
//                myfile.close();
//           }

//           else
//                cout << "Unable to open file";

//           ifstream myfile2(argv[2]);
//           if (myfile2.is_open())
//           {
//                while (getline(myfile2, line))
//                {
//                     queriesList.push_back(line);
//                }
//                myfile2.close();
//           }

//           else
//                cout << "Unable to open file";

//           // Adding all words from wordsList to a Trie.
//           Trie testTrie = Trie();
//           for (string word : wordList)
//           {
//                testTrie.addAWord(word);
//           }

//           // Performing tests with newly aquired words. Yay.
//           // Do these later (they should be pretty easy to do if everything is implemented correctly. Ugh).

//           // If the word is found, output "word is found", where word is the actual tested word. If the word is not found, output
//           // "word is not found, did you mean:" followed by a list of words in the dict that start with word as a prefix, one alternate
//           // per line, with the listing indented 3 spaces. If no alternatives are found, instead report "no alternatives found" in place
//           // of the alternate words, also indented 3 spaces and on its own line.

//           for (string query : queriesList)
//           {
//                if (testTrie.isAWord(query))
//                {
//                     cout << query << " is found";
//                     cout << endl;
//                }
//                else
//                {
//                     vector<string> alternatives = testTrie.allWordsStartingWithPrefix(query);
//                     cout << query << " is not found, did you mean:" << endl;
//                     if (!alternatives.empty())
//                     {
//                          for (unsigned int i = 0; i < alternatives.size(); i++)
//                               cout << ' ' << ' ' << ' ' << alternatives.at(i) << endl;
//                     }
//                     else
//                     {
//                          cout << ' ' << ' ' << ' ' << "no alternatives are found";
//                          cout << endl;
//                     }
//                }
//           }
//      }
//      return 0;
// }