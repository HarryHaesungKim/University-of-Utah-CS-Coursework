# dictionary of lists to store train vocabulary

import sys
import os
import re
import math

def distsim(trainPath, testPath, stopWordsPath, k):

    train = open(trainPath, 'r')
    test = open(testPath, 'r')
    stopWords = open(stopWordsPath, 'r')
    k = int(k)
    
    # file name with extension
    file_name = os.path.basename(testPath) + '.distsim'
    solutionFile = open(file_name, "w")

    stopWordsSet = set(line.strip() for line in stopWords)
    vocabOccurDic = dict()
    senseInventory = set()
    senseAndContext = dict()
    numTrainSentences = 0

    for line in train:

        numTrainSentences += 1

        senseAndSentence = line.split('\t')

        # senseAndSentence[0] is the "GOLDSENSE:<goldsense>" part and senseAndSentence[1] is the sentence with the occurance in it.
        # find the occurance.

        goldSense = (senseAndSentence[0])[10: len(senseAndSentence[0])]

        senseInventory.add(goldSense)

        sentenceList = senseAndSentence[1].lower().split()

        # getting index of occurence word
        matching = [s for s in sentenceList if "<occurrence>" in s]
        occurrence = matching[0]
        occurIndex = sentenceList.index(occurrence)

        # get surrounding words out of the sentence and put it in the vocabulary set.
        # Clear it of punctuation and uppercase letters.
        # Make sure that it is not a stop word.
        # Make sure it has an occurance of more than one.

        # making sure we don't go out of bounds.
        kRight = k
        kLeft = k

        if (occurIndex + k) > (len(sentenceList) - 1):
            kRight = len(sentenceList) - 1 - occurIndex

        if (occurIndex - k) < 0:
            kLeft = occurIndex

        contextForSenseAndContext = []

        # adding words to vocab.
        for i in range(kRight):
            if sentenceList[occurIndex + (i + 1)] in vocabOccurDic.keys():
                vocabOccurDic[sentenceList[occurIndex + (i + 1)]] = vocabOccurDic[sentenceList[occurIndex + (i + 1)]] + 1
            else:
                entry = {sentenceList[occurIndex + (i + 1)] : 1}
                vocabOccurDic.update(entry)
            contextForSenseAndContext.append(sentenceList[occurIndex + (i + 1)])

        for i in range(kLeft):
            if sentenceList[occurIndex - (i + 1)] in vocabOccurDic.keys():
                vocabOccurDic[sentenceList[occurIndex - (i + 1)]] = vocabOccurDic[sentenceList[occurIndex - (i + 1)]] + 1
            else:
                entry = {sentenceList[occurIndex - (i + 1)] : 1}
                vocabOccurDic.update(entry)
            contextForSenseAndContext.append(sentenceList[occurIndex - (i + 1)])

        # Adding or updating for senseAndContext
        if goldSense in senseAndContext.keys():
                senseAndContext[goldSense] = senseAndContext[goldSense] + contextForSenseAndContext
        else:
            entry = {goldSense : contextForSenseAndContext}
            senseAndContext.update(entry)

    # removing entries that only have an occurance of 1.
    vocabOccurDic = {key:val for key, val in vocabOccurDic.items() if val != 1}

    V = list(vocabOccurDic.keys())

    # making sure words contain at least some letters. Single characters like '(' will be removed.
    goodWords = []
    for w in V:
        if re.search('[a-zA-Z]', w):
            goodWords.append(w)
    V = goodWords

    # getting rid of stop words
    V = [i for i in V if i not in stopWordsSet]

    # Congradulations. You now have a complete vocab list (V) and all senses in a set. What now?
    # For each sense, we need a "signature vector". We need context surrounding occurance word (even if it is a stop word or has no alphabet characters) for each sense.
    # Dictionary of lists?
    # With all context words for each sense, we can fill signature vector and assign it to their cooresponding sense.

    for sense, contextList in senseAndContext.items():
        signatureVector = [0] * len(V)

        for word in contextList:
            if word in V:
                signatureVector[V.index(word)] += 1

        senseAndContext[sense] = signatureVector

    # Changing value name to keep track of things easier.
    senseAndSignatureVector = senseAndContext

    # Congradulations. You now have a complete vocab list (V), all senses in a set, AND a signature vector for each sense. What now? We move on to testing.
    # Now create context vectors! Same as sense vectors except no gold sense, or anything to do with senses really, and use the test file.

    contextAndContext = dict()
    numTestSentences = 0
    sentenceNum = 0

    for line in test:

        numTestSentences += 1

        # making list of each line in
        sentenceList = line.lower().split()

        # getting index of occurence word
        matching = [s for s in sentenceList if "<occurrence>" in s]
        occurrence = matching[0]
        occurIndex = sentenceList.index(occurrence)

        # making sure we don't go out of bounds.
        kRight = k
        kLeft = k

        if (occurIndex + k) > (len(sentenceList) - 1):
            kRight = len(sentenceList) - 1 - occurIndex

        if (occurIndex - k) < 0:
            kLeft = occurIndex

        contextForContextAndContext = []

        for i in range(kRight):
            contextForContextAndContext.append(sentenceList[occurIndex + (i + 1)])

        for i in range(kLeft):
            contextForContextAndContext.append(sentenceList[occurIndex - (i + 1)])

        # Only need to keep building up since we only have one thing: the target word.
        entry = {sentenceNum : contextForContextAndContext}
        contextAndContext.update(entry)

        signatureVector = [0] * len(V)

        for word in contextAndContext[sentenceNum]:
            if word in V:
                signatureVector[V.index(word)] += 1

        contextAndContext[sentenceNum] = signatureVector

        sentenceNum += 1

    testSentenceAndContextVector = contextAndContext


    # For testing and reference. Comment out later.
    # print("Number of Training Sentences = " + str(numTrainSentences))
    # print("Number of Test Sentences = " + str(numTestSentences))
    # print("Number of Gold Senses = " + str(len(senseAndSignatureVector.keys())))
    # print("Vocabulary Size = " + str(len(V)))
    solutionFile.write("Number of Training Sentences = " + str(numTrainSentences) + '\n')
    solutionFile.write("Number of Test Sentences = " + str(numTestSentences) + '\n')
    solutionFile.write("Number of Gold Senses = " + str(len(senseAndSignatureVector.keys())) + '\n')
    solutionFile.write("Vocabulary Size = " + str(len(V)) + '\n')


    # We now have all signature vectors and a context vector. Now compute cosine similarity.
    # X is context vector
    # Y is signature vectors
    cosXY = 0.0

    for testSentence, X in testSentenceAndContextVector.items():
        
        cosAndSense = dict()

        for currSense, Y in senseAndSignatureVector.items():
            numerator = sum([a*b for a,b in zip(X,Y)])

            denominator =  math.sqrt(sum([a*b for a,b in zip(X,X)])) * math.sqrt(sum([a*b for a,b in zip(Y,Y)]))

            if denominator == 0:
                cosXY = 0
            else:
                cosXY = numerator / denominator

            if cosXY in cosAndSense.keys():
                cosAndSense[cosXY] = cosAndSense[cosXY] + " " + currSense
            else:
                entry = {cosXY : currSense}
                cosAndSense.update(entry)

        finalLine = ""

        for i in sorted(cosAndSense.keys(),reverse=True):
            listOfWords = sorted(cosAndSense[i].split())
            for words in listOfWords:
                finalLine = finalLine + words + "(" + str(round(i, 2)) + ") "

        finalLine = finalLine.rstrip()

        # print(finalLine)
        # now print to file instead of terminal
        solutionFile.write(finalLine + '\n')

    # print("Do cool stuff")

    train.close
    test.close
    stopWords.close
    solutionFile.close

# python3 distsim.py ./data/train.txt ./data/test.txt ./data/stopwords.txt 10

# For testing. Comment out later.
# distsim("./data/train.txt", "./data/test.txt", "./data/stopwords.txt", 10)

distsim(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4])