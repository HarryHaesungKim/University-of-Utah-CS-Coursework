import sys
import os
import re


def lesk(testPath, definitionsPath, stopWordsPath):

    test = open(testPath, 'r')
    definitions = open(definitionsPath, 'r')
    stopWords = open(stopWordsPath, 'r')

    # listOfSenses = []
    # listOfOverlapNumbers = []

    # file name with extension
    file_name = os.path.basename(testPath) + '.lesk'

    solutionFile = open(file_name, "w")

    stopWordsSet = set(line.strip() for line in stopWords)


    for testSentence in test:

        senseKeyOverlapNumValDic = dict()

        # bestSense = ""
        maxOverlap = 0
        context = testSentence.lower().split()

        # making sure words contain at least some letters. Single characters like '(' will be removed.
        goodWords = []
        for w in context:
            if re.search('[a-zA-Z]', w):
                goodWords.append(w)
        context = goodWords

        # copyContext = copy()

        # for word in context:  # iterating on a copy since removing will mess things up
        #     if word in stopWordsSet:
        #         context.remove(word)

        context = [i for i in context if i not in stopWordsSet]

        for line in definitions:
            definitionsSet = line.split('\t')

            sense = definitionsSet[0]

            # definitionsSet[1] = re.sub(r'[^\w\s]', '', definitionsSet[1]).lower()
            # definitionsSet[2] = re.sub(r'[^\w\s]', '', definitionsSet[2]).lower()

            definitionsSet[1] = definitionsSet[1].lower()
            definitionsSet[2] = definitionsSet[2].lower()

            currentDef = definitionsSet[1].split()
            currentExample = definitionsSet[2].split()

            # check if any input is just a character.
            goodWords = []
            for w in currentDef:
                if re.search('[a-zA-Z]', w):
                    goodWords.append(w)
            currentDef = goodWords

            goodWords = []
            for w in currentExample:
                if re.search('[a-zA-Z]', w):
                    goodWords.append(w)
            currentExample = goodWords

            # getting rid of new line character at the end of the last word.
            currentExample[len(currentExample) - 1] = currentExample[len(currentExample) - 1].replace("\n", "" )

            signature = currentDef + currentExample

            signature = [i for i in signature if i not in stopWordsSet]

            # overlapList = list(set(context) & set(signature))
            overlap = len(list(set(context) & set(signature)))

            # listOfSenses.append(sense)
            # listOfOverlapNumbers.append(overlap)

            # senseKeyOverlapNumValDic.update(sense, overlap)

            if overlap in senseKeyOverlapNumValDic.keys():
                senseKeyOverlapNumValDic[overlap] = senseKeyOverlapNumValDic[overlap] + " " + sense
            else:
                entry = {overlap : sense}
                senseKeyOverlapNumValDic.update(entry)

            if overlap > maxOverlap:
                maxOverlap = overlap
                # bestSense = sense

        definitions.seek(0)

        # senseAndNum = bestSense + "(" + str(maxOverlap) + ")"

        # Plan: keep two lists of both senses and cooresponding overlap numbers and sort them?
        # New plan: it would be faster to just remove best from list after each time it is done.
        # New new plan: Keep dictionary with overlap number as keys. Append sense to value string and then split into lists then sort. No need to loop or complicated sorting.

        finalLine = ""

        for i in sorted(senseKeyOverlapNumValDic.keys(),reverse=True):
            listOfWords = sorted(senseKeyOverlapNumValDic[i].split())
            for words in listOfWords:
                finalLine = finalLine + words + "(" + str(i) + ") "

        finalLine = finalLine.rstrip()

        # print(finalLine)
        # now print to file instead of terminal
        solutionFile.write(finalLine + '\n')

    test.close
    definitions.close
    stopWords.close
    solutionFile.close



# python3 lesk.py ./data/test.txt ./data/definitions.txt ./data/stopwords.txt

# for testing (comment out later):
# lesk("./data/test.txt", "./data/definitions.txt", "./data/stopwords.txt")

# run lesk algorithm
lesk(sys.argv[1], sys.argv[2], sys.argv[3])