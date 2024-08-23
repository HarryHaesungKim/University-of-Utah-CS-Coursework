/**
 * CS3505 A2: Runs spiral code and makes a pdf with text as spiral.
 * @file TrieTest.cpp
 * @author Harry Kim
 * @version 1.0 9/24/21
 */

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include "Trie.h"

// two command-line arguments, each one a filename. The first is a file of words, each on their own line,
// with the words all lowercase and only made up of characters a-z. The second is a file of queries, each
// word on its own line and also of acceptable characters.
int main(int argc, char **argv)
{
     vector<string> wordList;
     vector<string> queriesList;

     // If there's not enough arguments, program doesn't even try to run.
     if (argc < 3)
     {
          std::cout << "Insufficient argument count." << std::endl;
          return 0;
     }

     else
     {
          // Loading words and queries into vectors.
          string line;
          ifstream myfile(argv[1]);
          if (myfile.is_open())
          {
               while (getline(myfile, line))
               {
                    wordList.push_back(line);
               }
               myfile.close();
          }

          else
               cout << "Unable to open file";

          ifstream myfile2(argv[2]);
          if (myfile2.is_open())
          {
               while (getline(myfile2, line))
               {
                    queriesList.push_back(line);
               }
               myfile2.close();
          }

          else
               cout << "Unable to open file";

          // Adding all words from wordsList to a Trie.
          Trie testTrie = Trie();
          for (string word : wordList)
          {
               testTrie.addAWord(word);
          }

          // Performing tests with newly aquired words. Yay.
          // Do these later (they should be pretty easy to do if everything is implemented correctly. Ugh).

          // If the word is found, output "word is found", where word is the actual tested word. If the word is not found, output
          // "word is not found, did you mean:" followed by a list of words in the dict that start with word as a prefix, one alternate
          // per line, with the listing indented 3 spaces. If no alternatives are found, instead report "no alternatives found" in place
          // of the alternate words, also indented 3 spaces and on its own line.

          for (string query : queriesList)
          {
               if (testTrie.isAWord(query))
               {
                    cout << query << " is found";
                    cout << endl;
               }
               else
               {
                    vector<string> alternatives = testTrie.allWordsStartingWithPrefix(query);
                    cout << query << " is not found, did you mean:" << endl;
                    if (!alternatives.empty())
                    {
                         for (unsigned int i = 0; i < alternatives.size(); i++)
                              cout << ' ' << ' ' << ' ' << alternatives.at(i) << endl;
                    }
                    else
                    {
                         cout << ' ' << ' ' << ' ' << "no alternatives are found";
                         cout << endl;
                    }
               }
          }
     }
     return 0;
}