from mailbox import linesep
from sre_constants import NEGATE
import sys
from tkinter import Label
from unittest.case import DIFF_OMITTED

import os

import string

import csv

import re

train_file = open(sys.argv[1])
train_fileName = os.path.splitext(os.path.basename(sys.argv[1]))[0] + "_features"
test_file = open(sys.argv[2])
test_fileName = os.path.splitext(os.path.basename(sys.argv[2]))[0] + "_features"
lexicon_file = open(sys.argv[3])

#For testing and debugging:
# train_file = open('./testing-files/test-train.txt')
# train_fileName = os.path.splitext(os.path.basename('./testing-files/test-train.txt'))[0]
# lexicon_file = open('./testing-files/test-lexicon.txt')

LABEL = 0
DIFF = 0
EXCL = 0
NEG = 0
NGTNEG = 0
NGTPOS = 0
POS = 0
W1 = "UNK"
W2 = "UNK"
W3 = "UNK"
W4 = "UNK"
W5 = "UNK"

lineNumber = 0
count = -1
negationWords = ["no","not","never","isn't","wasn't","won't"]
previousWord = ""
previousPreviousWord = ""
negationActive = False
negationCounter = 0

lexiconSet = set(line.strip() for line in lexicon_file)

# When a negation word is identified, activate negationActive and start counting negationActiveCounter to 2 then reset.
# Maybe we can just read the next two words, do our calculations, then jump back to the line.

headers = ["LABEL","DIFF","EXCL","NEG","NGTNEG","NGTPOS","POS","W1","W2","W3","W4","W5"]

# Write a train file csv
with open(train_fileName + ".csv", "w", newline='') as myfile:
    filewriter = csv.writer(myfile, quotechar='|')
    filewriter.writerow(headers)

    while True:

        eachLine = train_file.readline()

        if not eachLine:
            break

        # For testing:
        # count += 1
        # if count == 20:
        #     exit()

        if not eachLine.strip():
            DIFF = POS - NEG
            # For Testing:
            printString = "" + str(LABEL) + "," + str(DIFF) + "," + str(EXCL) + "," + str(NEG) + "," + str(NGTNEG) + "," + str(NGTPOS) + "," + str(POS) + "," + W1 + "," + W2 + "," + W3 + "," + W4 + "," + W5
            #print(printString)

            myList = printString.split(",")

            filewriter.writerow(myList)

            # Calculate DIFF (POS - NEG), reset all variables, write to csv file or whatever it's called, and continue.
            LABEL = 0
            DIFF = 0
            EXCL = 0
            NEG = 0
            NGTNEG = 0
            NGTPOS = 0
            POS = 0
            W1 = "UNK"
            W2 = "UNK"
            W3 = "UNK"
            W4 = "UNK"
            W5 = "UNK"

            lineNumber = 0

            previousWord = ""
            previousPreviousWord = ""

            continue

        # Need to add a case for negation words.

        insertWord = eachLine.strip()

        if any(c.isalpha() for c in eachLine):

            if "," in insertWord:
                insertWord = "\"" + insertWord + "\""
            
            if "!" in insertWord:
                EXCL += 1

            #Negation word detected
            if insertWord in negationWords:
                negationActive = True
            
            else:
                lexicon_file.seek(0, 0)
                for line in lexicon_file:
                    if not line or line == "\n":
                        break
                    if insertWord == line.split()[0]:
                        posOrNeg = line.split()[1]
                        if("POS" in posOrNeg):
                            POS += 1
                        elif("NEG" in posOrNeg):
                            NEG += 1
                        lexicon_file.seek(0, 0)
                        break
                lexicon_file.seek(0, 0)
        
        else:
            # Get LABEL
            if "0" in eachLine and (not previousWord):
                LABEL = 0
                lineNumber += 1
                continue
            if "1" in eachLine and (not previousWord):
                LABEL = 1
                lineNumber += 1
                continue
            # Get EXCL
            if "!" in eachLine:
                EXCL += 1
            # Incase of commas,
            elif "," in eachLine:
                if lineNumber <= 5:
                    insertWord = "\",\""
            elif "\"" in eachLine:
                if lineNumber <= 5:
                    #insertWord = "\'"+insertWord+"\'"
                    insertWord = "\"\"\"\""

        if negationActive:
            negationCounter += 1
            # do cool stuff
            # save the position
            pos = train_file.tell()
            
            # get next two words
            nextWord = train_file.readline().strip()
            nextnextWord = train_file.readline().strip()

            # go back to original position
            train_file.seek(pos)

            # look for NGTPOS and NGTNEG

            addToNGTPOS = 0
            addToNGTNEG = 0

            # looking for
            lexicon_file.seek(0, 0)
            for line in lexicon_file:
                if not line or line == "\n":
                    break

                if previousWord == line.split()[0]:
                    posOrNeg = line.split()[1]
                    if("POS" in posOrNeg):
                        addToNGTPOS = 1
                    elif("NEG" in posOrNeg):
                        addToNGTNEG = 1
                    lexicon_file.seek(0, 0)
                    break
            lexicon_file.seek(0, 0)

            for line in lexicon_file:
                if not line or line == "\n":
                    break

                if previousPreviousWord == line.split()[0]:
                    posOrNeg = line.split()[1]
                    if("POS" in posOrNeg):
                        addToNGTPOS = 1
                    elif("NEG" in posOrNeg):
                        addToNGTNEG = 1
                    lexicon_file.seek(0, 0)
                    break
            lexicon_file.seek(0, 0)

            for line in lexicon_file:
                if not line or line == "\n":
                    break
                
                if nextWord == line.split()[0]:
                    posOrNeg = line.split()[1]
                    if("POS" in posOrNeg):
                        addToNGTPOS = 1
                    elif("NEG" in posOrNeg):
                        addToNGTNEG = 1
                    lexicon_file.seek(0, 0)
                    break
            lexicon_file.seek(0, 0)

            for line in lexicon_file:
                if not line or line == "\n":
                    break

                if nextnextWord == line.split()[0]:
                    posOrNeg = line.split()[1]
                    if("POS" in posOrNeg):
                        addToNGTPOS = 1
                    elif("NEG" in posOrNeg):
                        addToNGTNEG = 1
                    lexicon_file.seek(0, 0)
                    break
            lexicon_file.seek(0, 0)

            NGTPOS += addToNGTPOS
            NGTNEG += addToNGTNEG

            negationActive = False

        
        if lineNumber == 1:
            W1 = insertWord
        elif lineNumber == 2:
            W2 = insertWord
        elif lineNumber == 3:
            W3 = insertWord
        elif lineNumber == 4:
            W4 = insertWord
        elif lineNumber == 5:
            W5 = insertWord
        
        # previous words are good to go.
        if lineNumber >= 0:
            previousPreviousWord = previousWord
            previousWord = insertWord

        lineNumber += 1
        print("training working...")

    if NGTPOS > negationCounter:
        NGTPOS = negationCounter

    if NGTNEG > negationCounter:
        NGTNEG = negationCounter

    if NGTPOS > POS:
        NGTPOS = POS

    if NGTNEG > NEG:
        NGTNEG = NEG

    DIFF = POS - NEG

    # For Testing:
    printString = "" + str(LABEL) + "," + str(DIFF) + "," + str(EXCL) + "," + str(NEG) + "," + str(NGTNEG) + "," + str(NGTPOS) + "," + str(POS) + "," + W1 + "," + W2 + "," + W3 + "," + W4 + "," + W5
    #print(printString)

    myList = printString.split(",")

    filewriter.writerow(myList)

    # Calculate DIFF (POS - NEG), reset all variables, write to csv file or whatever it's called, and continue.
    LABEL = 0
    DIFF = 0
    EXCL = 0
    NEG = 0
    NGTNEG = 0
    NGTPOS = 0
    POS = 0
    W1 = "UNK"
    W2 = "UNK"
    W3 = "UNK"
    W4 = "UNK"
    W5 = "UNK"

    lineNumber = 0

    previousWord = ""
    previousPreviousWord = ""

train_file.close()

# Write a test file csv
with open(test_fileName + ".csv", "w", newline='') as myfile:
    filewriter = csv.writer(myfile, quotechar='|')
    filewriter.writerow(headers)

    while True:

        eachLine = test_file.readline()

        if not eachLine:
            break

        # For testing:
        # count += 1
        # if count == 20:
        #     exit()

        if not eachLine.strip():
            DIFF = POS - NEG
            # For Testing:
            printString = "" + str(LABEL) + "," + str(DIFF) + "," + str(EXCL) + "," + str(NEG) + "," + str(NGTNEG) + "," + str(NGTPOS) + "," + str(POS) + "," + W1 + "," + W2 + "," + W3 + "," + W4 + "," + W5
            #print(printString)

            myList = printString.split(",")

            filewriter.writerow(myList)

            # Calculate DIFF (POS - NEG), reset all variables, write to csv file or whatever it's called, and continue.
            LABEL = 0
            DIFF = 0
            EXCL = 0
            NEG = 0
            NGTNEG = 0
            NGTPOS = 0
            POS = 0
            W1 = "UNK"
            W2 = "UNK"
            W3 = "UNK"
            W4 = "UNK"
            W5 = "UNK"

            lineNumber = 0

            previousWord = ""
            previousPreviousWord = ""

            continue

        # Need to add a case for negation words.

        insertWord = eachLine.strip()

        if any(c.isalpha() for c in eachLine):

            if "," in insertWord:
                insertWord = "\"" + insertWord + "\""
            
            if "!" in insertWord:
                EXCL += 1

            #Negation word detected
            if insertWord in negationWords:
                negationActive = True
            
            else:
                lexicon_file.seek(0, 0)
                for line in lexicon_file:
                    if not line or line == "\n":
                        break
                    if insertWord == line.split()[0]:
                        posOrNeg = line.split()[1]
                        if("POS" in posOrNeg):
                            POS += 1
                        elif("NEG" in posOrNeg):
                            NEG += 1
                        lexicon_file.seek(0, 0)
                        break
                lexicon_file.seek(0, 0)
        
        else:
            # Get LABEL
            if "0" in eachLine and (not previousWord):
                LABEL = 0
                lineNumber += 1
                continue
            if "1" in eachLine and (not previousWord):
                LABEL = 1
                lineNumber += 1
                continue
            # Get EXCL
            if "!" in eachLine:
                EXCL += 1
            # Incase of commas,
            elif "," in eachLine:
                if lineNumber <= 5:
                    insertWord = "\",\""
            elif "\"" in eachLine:
                if lineNumber <= 5:
                    #insertWord = "\'"+insertWord+"\'"
                    insertWord = "\"\"\"\""

        if negationActive:
            negationCounter += 1
            # do cool stuff
            # save the position
            pos = test_file.tell()
            
            # get next two words
            nextWord = test_file.readline().strip()
            nextnextWord = test_file.readline().strip()

            # go back to original position
            test_file.seek(pos)

            # look for NGTPOS and NGTNEG

            addToNGTPOS = 0
            addToNGTNEG = 0

            # looking for
            lexicon_file.seek(0, 0)
            for line in lexicon_file:
                if not line or line == "\n":
                    break

                if previousWord == line.split()[0]:
                    posOrNeg = line.split()[1]
                    if("POS" in posOrNeg):
                        addToNGTPOS = 1
                    elif("NEG" in posOrNeg):
                        addToNGTNEG = 1
                    lexicon_file.seek(0, 0)
                    break
            lexicon_file.seek(0, 0)

            for line in lexicon_file:
                if not line or line == "\n":
                    break

                if previousPreviousWord == line.split()[0]:
                    posOrNeg = line.split()[1]
                    if("POS" in posOrNeg):
                        addToNGTPOS = 1
                    elif("NEG" in posOrNeg):
                        addToNGTNEG = 1
                    lexicon_file.seek(0, 0)
                    break
            lexicon_file.seek(0, 0)

            for line in lexicon_file:
                if not line or line == "\n":
                    break
                
                if nextWord == line.split()[0]:
                    posOrNeg = line.split()[1]
                    if("POS" in posOrNeg):
                        addToNGTPOS = 1
                    elif("NEG" in posOrNeg):
                        addToNGTNEG = 1
                    lexicon_file.seek(0, 0)
                    break
            lexicon_file.seek(0, 0)

            for line in lexicon_file:
                if not line or line == "\n":
                    break

                if nextnextWord == line.split()[0]:
                    posOrNeg = line.split()[1]
                    if("POS" in posOrNeg):
                        addToNGTPOS = 1
                    elif("NEG" in posOrNeg):
                        addToNGTNEG = 1
                    lexicon_file.seek(0, 0)
                    break
            lexicon_file.seek(0, 0)

            NGTPOS += addToNGTPOS
            NGTNEG += addToNGTNEG

            negationActive = False

        
        if lineNumber == 1:
            W1 = insertWord
        elif lineNumber == 2:
            W2 = insertWord
        elif lineNumber == 3:
            W3 = insertWord
        elif lineNumber == 4:
            W4 = insertWord
        elif lineNumber == 5:
            W5 = insertWord
        
        # previous words are good to go.
        if lineNumber >= 0:
            previousPreviousWord = previousWord
            previousWord = insertWord

        lineNumber += 1
        print("test working...")

    if NGTPOS > negationCounter:
        NGTPOS = negationCounter

    if NGTNEG > negationCounter:
        NGTNEG = negationCounter

    if NGTPOS > POS:
        NGTPOS = POS

    if NGTNEG > NEG:
        NGTNEG = NEG

    DIFF = POS - NEG

    # For Testing:
    printString = "" + str(LABEL) + "," + str(DIFF) + "," + str(EXCL) + "," + str(NEG) + "," + str(NGTNEG) + "," + str(NGTPOS) + "," + str(POS) + "," + W1 + "," + W2 + "," + W3 + "," + W4 + "," + W5
    #print(printString)

    myList = printString.split(",")

    filewriter.writerow(myList)

    # Calculate DIFF (POS - NEG), reset all variables, write to csv file or whatever it's called, and continue.
    LABEL = 0
    DIFF = 0
    EXCL = 0
    NEG = 0
    NGTNEG = 0
    NGTPOS = 0
    POS = 0
    W1 = "UNK"
    W2 = "UNK"
    W3 = "UNK"
    W4 = "UNK"
    W5 = "UNK"

    lineNumber = 0

    previousWord = ""
    previousPreviousWord = ""

test_file.close()

lexicon_file.close()