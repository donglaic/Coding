from numpy import *
import operator
from os import listdir

def createDataSet():
	group = array([[1.0,1.1],[1.0,1.0],[0,0],[0,0.1]])
	labels = ['A','A','B','B']
	return group, labels

def classify0(inX,dataSet,labels,k):
	'''
	inX: 代分类的输入变量
	dataSet: 训练样本集
	labels: 标签向量
	k: 最近邻数
	'''
	# 距离计算
	dataSetSize = dataSet.shape[0]
	diffMat = tile(inX,(dataSetSize,1)) - dataSet
	sqDiffMat = diffMat**2
	sqDistance = sqDiffMat.sum(axis=1)
	distance = sqDistance**0.5
	# 选择距离最小的k个点
	sortedDistIndicies = distance.argsort()
	# 找到k个点出现频次最高的分类
	classCount={}
	for i in range(k):
		voteIlabel = labels[sortedDistIndicies[i]]
		classCount[voteIlabel] = classCount.get(voteIlabel,0) + 1
	sortedClassCount = sorted(classCount.items(),key=operator.itemgetter(1),reverse=True)
	return sortedClassCount[0][0]

def file2matrix(filename):
	'''
	filename: 样本文件名
	'''
	fr = open(filename)
	arrayOLines = fr.readlines()
	numberOfLines = len(arrayOLines)
	returnMat = zeros((numberOfLines,3))
	classLabelVector = []
	index = 0
	for line in arrayOLines:
		listFromLine = line.strip().split('\t')
		returnMat[index,:] = listFromLine[0:3]
		classLabelVector.append(int(listFromLine[-1]))
		index += 1
	return returnMat,classLabelVector
	
def autoNorm(dataSet):
	'''
	归一化
	dataSet: 数据集
	'''
	minVals = dataSet.min(0)
	maxVals = dataSet.max(0)
	ranges = maxVals - minVals
	#normDataSet = zeros(shape(dataSet))
	m = dataSet.shape[0]
	normDataSet = dataSet - tile(minVals, (m,1))
	normDataSet = normDataSet / tile(ranges, (m,1))
	return normDataSet, ranges, minVals
	
def datingClassTest():
	'''
	测试算法
	'''
	hoRatio = 0.10
	datingDataMat,datingLabels = file2matrix('datingTestSet2.txt')
	normMat,ranges,minVals = autoNorm(datingDataMat)
	m = normMat.shape[0]
	numTestVecs = int(m * hoRatio)
	errorCount = 0
	for i in range(numTestVecs):
		classifierResult = classify0(normMat[i,:],normMat[numTestVecs:m,:],datingLabels[numTestVecs:m],3)
		print("the classifier came back with: {}, the real answer is: {}".format(classifierResult, datingLabels[i]))
		if classifierResult != datingLabels[i]:
			errorCount += 1
	print("the total error rate is: {}".format(errorCount/float(numTestVecs)))

def classifyPerson():
	'''
	使用算法，分类约会人
	'''
	resultList = ['not at all','in samll doses','in large doses']
	percentTats = float(input("percentage of time spent playing vido games?"))
	ffMiles = float(input("frequent flier miles earned per year?"))
	iceCream = float(input("liters of ice cream consumed per year?"))
	datingDataMat,datingLabels = file2matrix('datingTestSet2.txt')
	normMat,ranges,minVals = autoNorm(datingDataMat)
	inArr = array([ffMiles,percentTats,iceCream])
	classifierResult = classify0((inArr - minVals) / ranges, normMat, datingLabels, 3)
	print("You will probably like the person: ", resultList[classifierResult - 1])

def img2Vector(filename):
	returnVect = zeros((1,1024))
	fr = open(filename)
	for i in range(32):
		lineStr = fr.readline()
		for j in range(32):
			returnVect[0,32*i+j] = int(lineStr[j])
	return returnVect

def handwritingClassTest():
	'''
	手写识别测试
	'''
	# 准备数据
	# 训练数据
	hwLabels = []
	trainingFileList = listdir('digits/trainingDigits')
	m = len(trainingFileList)
	trainingMat = zeros((m,1024))
	for i in range(m):
		fileNameStr = trainingFileList[i]
		classNumStr = int(fileNameStr.split('.')[0].split('_')[0])
		hwLabels.append(classNumStr)
		trainingMat[i,:] = img2Vector('digits/trainingDigits/{}'.format(fileNameStr))
	# 测试数据
	testFileList = listdir('digits/testDigits')
	errorCount = 0.0
	mTest = len(testFileList)
	for i in range(mTest):
		fileNameStr = testFileList[i]
		classNumStr = int(fileNameStr.split('.')[0].split('_')[0])
		vectorUnderTest = img2Vector('digits/testDigits/{}'.format(fileNameStr))
		classifierResult = classify0(vectorUnderTest, trainingMat, hwLabels, 3)
		print("the classifier came back with: {}, the real answer is: {}".format(classifierResult, classNumStr))
		if classifierResult != classNumStr:
			errorCount += 1
	print("\nthe total number of errors is: {}".format(errorCount))
	print("\nthe total error rate is: {}".format(errorCount/float(mTest)))


		
