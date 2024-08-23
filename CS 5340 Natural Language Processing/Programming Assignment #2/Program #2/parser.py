import sys
import copy

class node:
    def __init__(self, currentNode=None):
        self.currentNode = currentNode
        self.nextNode = None
        self.currentWordNumber = None
        self.dependencyDef = None

# apply the transition parsing algorithm.
def simulateMode(file):
    # print("Hello this is working")
    wordHolderBuddy = []
    sentenceQueue = []
    line = file.readline()

    # Putting root at the top of the sentence queue (last in line)
    root = node("ROOT")
    root.currentWordNumber = 0

    wordHolderBuddy.append(root)

    # Getting words from sentence inside a queue to be used.
    for word in line.split():
        if(word != "SENTENCE:"):
            sentenceQueue.append(node(word))
        # printing to output/terminal.
        print(word, end =" ")
    print()

    # Printing "OPERATORS".
    line = file.readline()
    print(line[0:(len(line) - 1)])
    line = file.readline()

    operatorQueue = []

    # printing operators to output/terminal.
    while line:
        print(line[0:(len(line) - 1)], end =" ")
        operatorQueue.append(line[0:(len(line) - 1)])
        line = file.readline()
    print('\n')

    # To get last dependency.
    operatorQueue.append("end dummy")

    # Begin parsing.
    print("PARSING NOW")

    currentWord = sentenceQueue.pop(0)
    currentOp = operatorQueue.pop(0)
    dependencyParse = []
    wordCounter = 1

    while operatorQueue:
        if(currentOp == "Shift"):

            print("Shifting " + currentWord.currentNode + "(" + str(wordCounter) + ")")

            currentWord.currentWordNumber = wordCounter
            wordHolderBuddy.append(currentWord)
            if(sentenceQueue):
                currentWord = sentenceQueue.pop(0)
            wordCounter += 1

        elif("RightArc" in currentOp):
            child = wordHolderBuddy.pop()
            parent = wordHolderBuddy.pop()

            print(currentOp + " to produce: " + parent.currentNode + "(" + str(parent.currentWordNumber) + ")" + " -- " + currentOp[9:(len(currentOp))] + " --> " + child.currentNode + "(" + str(child.currentWordNumber) + ")")

            parent.nextNode = child
            parent.dependencyDef = currentOp[9:(len(currentOp))]
            wordHolderBuddy.append(parent)

            nodeCopy = copy.copy(parent)
            dependencyParse.append(nodeCopy)
        
        elif("LeftArc" in currentOp):
            parent = wordHolderBuddy.pop()
            child = wordHolderBuddy.pop()

            print(currentOp + " to produce: " + parent.currentNode + "(" + str(parent.currentWordNumber) + ")" + " -- " + currentOp[8:(len(currentOp))] + " --> " + child.currentNode + "(" + str(child.currentWordNumber) + ")")

            parent.nextNode = child
            parent.dependencyDef = currentOp[8:(len(currentOp))]
            wordHolderBuddy.append(parent)

            nodeCopy = copy.copy(parent)
            dependencyParse.append(nodeCopy)

        if(operatorQueue):
            currentOp = operatorQueue.pop(0)

    print()

    print("FINAL DEPENDENCY PARSE")
        
    for parentWord in dependencyParse:
        print(parentWord.currentNode + "(" + str(parentWord.currentWordNumber) + ")" + " -- " + parentWord.dependencyDef + " --> " + parentWord.nextNode.currentNode + "(" + str(parentWord.nextNode.currentWordNumber) + ")")
    
    print()


def genopsMode(opFile, goldFile):
    words = []
    wordsPlusRoot = []
    wordsPlusRoot.append("ROOT")
    dependency = []
    label = []
    stack = []
    relations = []

    print("SENTENCE:", end =" ")
    # filling in words, dependency, and labels
    with goldFile as goldFileLoop:
        for line in goldFileLoop:
            if not ("#" in line):
                lineList = line.split()
                words.append(lineList[1])
                wordsPlusRoot.append(lineList[1])
                dependency.append(lineList[2])
                label.append(lineList[3])

                print(lineList[1], end =" ")

    print()
    
    # Creating all nodes and adding it to "goldenParse".
    goldParseNodes = []

    # A list that just puts the parents and children of the goldenParse in order.
    goldParseParents = []
    # goldParseChildren is the same is words except that it will never be modified while words will.
    goldParseChildren = []

    # numDependencies is the number of dependencies a certain node has.
    numDependencies = [0] * (len(words)+1)

    i = 0
    while i < len(words):
        if dependency[i] == '0':
            wordNode = node("ROOT")
            wordNode.currentWordNumber = 0
            numDependencies[0] += 1
        else:
            wordNode = node(words[int(dependency[i]) - 1])
            wordNode.currentWordNumber = words.index(words[int(dependency[i]) - 1]) + 1
            numDependencies[int(dependency[i])] += 1
        
        wordNode.nextNode = words[i]
        
        wordNode.dependencyDef = label[i]

        goldParseNodes.append(wordNode)
        goldParseParents.append(wordNode.currentNode)
        goldParseChildren.append(wordNode.nextNode)
        i+=1

    # I guess I can print the golden parse in order for the first print...
    print("GOLD DEPENDENCIES")

    for goldNode in goldParseNodes:
        print(goldNode.currentNode + "(" + str(goldNode.currentWordNumber) + ")" + " -- " + goldNode.dependencyDef + " --> " + goldNode.nextNode + "(" + str(words.index(goldNode.nextNode) + 1) + ")")

    print()
    print("GENERATING PARSING OPERATORS")

    finalOpSeqList = []

    #Push Root onto stack first
    stack.append("ROOT")

    while True:

        relationFound = False

        if len(stack) == 1 and len(words) == 0:
            break
        elif len(stack) == 1 and len(words) != 0:
            word = words.pop(0)
            stack.insert(0,word)
            print("Shift")
            finalOpSeqList.append("Shift")
            continue

        word1 = stack.pop(0)
        word2 = stack.pop(0)

        # Try LeftArc
        counter = 0
        for parent in goldParseParents:
            if word1 == parent:
                if goldParseChildren[counter] == word2:
                    # perform left arc
                    relations.append(word1 + " " + word2)
                    stack.insert(0,word1)
                    print("LeftArc_" + label[counter] + " to produce: " + word1 + "(" + str(wordsPlusRoot.index(word1)) + ")" + " -- " + label[counter] + " --> " + word2 + "(" + str(wordsPlusRoot.index(word2)) + ")")
                    finalOpSeqList.append("LeftArc_" + label[counter])
                    relationFound = True
                    break
            counter += 1

        if relationFound:
            continue

        # Try RightArc
        counter = 0
        for parent in goldParseParents:
            if word2 == parent:
                if goldParseChildren[counter] == word1:
                    # AND if all dependents of the word at the top of the stack have already been assigned.

                    dependencyCount = 0
                    for relation in relations:
                        if relation.split()[0] == word1:
                            dependencyCount += 1
                    
                    if not (dependencyCount == numDependencies[wordsPlusRoot.index(word1)]):
                        break

                    # perform right arc
                    relations.append(word2 + " " + word1)
                    stack.insert(0,word2)
                    print("RightArc_" + label[counter] + " to produce: " + word2 + "(" + str(wordsPlusRoot.index(word2)) + ")" + " -- " + label[counter] + " --> " + word1 + "(" + str(wordsPlusRoot.index(word1)) + ")")
                    finalOpSeqList.append("RightArc_" + label[counter])
                    relationFound = True
                    break
            counter += 1

        if relationFound:
            continue

        # Shift
        stack.insert(0, word2)
        stack.insert(0, word1)
        moveWord = words.pop(0)
        stack.insert(0, moveWord)
        print("Shift")
        finalOpSeqList.append("Shift")


    print()
    print("FINAL OPERATOR SEQUENCE")

    for operator in finalOpSeqList:
        print(operator)
    
    print()

# for testing simulateMode (comment out later)
# file = open("./data-files/sentops1.txt", 'r')
# simulateMode(file)
# file.close

# for testing genopsMode (comment out later)
# opFile = open("./data-files/operators.txt", 'r')
# goldFile = open("./data-files/gold1.txt", 'r')
# genopsMode(opFile, goldFile)
# opFile.close
# goldFile.close

# python3 parser.py -simulate ./data-files/sentops1.txt
# python3 parser.py -genops ./data-files/operators.txt ./data-files/gold1.txt

mode = sys.argv[1]

if(mode == "-simulate"):
    file = open(sys.argv[2], 'r')
    simulateMode(file)
    file.close

elif(mode == "-genops"):
    opFile = open(sys.argv[2], 'r')
    goldFile = open(sys.argv[3], 'r')
    genopsMode(opFile, goldFile)
    opFile.close
    goldFile.close