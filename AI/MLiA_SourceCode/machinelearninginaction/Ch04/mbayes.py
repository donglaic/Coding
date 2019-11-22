from numpy import *

def loadDataSet():
	postingList=[['my', 'dog', 'has', 'flea', 'problems', 'help', 'please'],
                 ['maybe', 'not', 'take', 'him', 'to', 'dog', 'park', 'stupid'],
                 ['my', 'dalmation', 'is', 'so', 'cute', 'I', 'love', 'him'],
                 ['stop', 'posting', 'stupid', 'worthless', 'garbage'],
                 ['mr', 'licks', 'ate', 'my', 'steak', 'how', 'to', 'stop', 'him'],
                 ['quit', 'buying', 'worthless', 'dog', 'food', 'stupid']]
	classVec = [ 0,1,0,1,0,1 ]
	# 0: 侮辱文字 1: 正常言论
	return postingList, classVec

def createVocabList(dataSet):
	vocabSet = set([])
	for document in dataSet:
		vocabSet = vocabSet | set(document)
	return list(vocabSet)

def setOfWords2Vec(vocabList,inputSet):
	returnVec = [0] * len(vocabList)
	for word in inputSet:
		if word in vocabList:
			returnVec[vocabList.index(word)] = 1
		else:
			print("the word: ", word, " is not in my Vocabulary!")
	return returnVec

def bagOfWords2Vec(vocabList,inputSet):
	returnVec = [0] * len(vocabList)
	for word in inputSet:
		if word in vocabList:
			returnVec[vocabList.index(word)] += 1
		else:
			print("the word: ", word, " is not in my Vocabulary!")
	return returnVec

def trainNB0(trainMatrix,trainCategory):
	numWords = len(trainMatrix[0])
	pAbusive = sum(trainCategory) / float( len(trainCategory))
	p0Num = ones(numWords)
	p1Num = ones(numWords)
	for i in range(len(trainMatrix)):
		if trainCategory[i] == 1:
			p1Num += trainMatrix[i]
		else:
			p0Num += trainMatrix[i]
	p1Vect = log( p1Num / sum(p1Num)	+ 2)
	p0Vect = log( p0Num / sum(p0Num)	+ 2)
	return p0Vect,p1Vect,pAbusive

def classifyNB(vec2Classify, p0Vec, p1Vec, pClass1):
	if sum(vec2Classify * p1Vec) + log(pClass1) > sum(vec2Classify * p0Vec) + log(1 - pClass1):
		return 1
	else:
		return 0
	
def testingNB():
	listOPosts,listClasses = loadDataSet()
	myVocabList = createVocabList(listOPosts)
	trainMat = [ setOfWords2Vec(myVocabList,postinDoc) for postinDoc in listOPosts ]
	p0V, p1V, pAb = trainNB0( trainMat, listClasses)
	testEntry = ['love','my','dalmation']
	print(testEntry, "classified as: ", classifyNB( setOfWords2Vec(myVocabList,testEntry), p0V, p1V, pAb))
	testEntry = ['stupid','garbage']
	print(testEntry, "classified as: ", classifyNB( setOfWords2Vec(myVocabList,testEntry), p0V, p1V, pAb))
