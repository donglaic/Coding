from math import log

def calcShannonEnt(dataSet):
	'''
	计算熵
	'''
	numEntries = len(dataSet)
	# 计算每个分类的数目
	labelCounts = {}
	for featVec in dataSet:
		currentLabel = featVec[-1]
		if currentLabel not in labelCounts.keys():
			labelCounts[currentLabel] = 0
		labelCounts[currentLabel] += 1
	shannonEnt = 0.0
	# 计算熵
	for key in labelCounts:
		prob = float(labelCounts[key])/numEntries
		shannonEnt -= prob * log(prob,2)
	return shannonEnt
	
def createDataSet():
	dataSet = [
		[1,1,'yes'],
		[1,1,'yes'],
		[1,0,'no'],
		[0,1,'no'],
		[0,1,'no']
		]
	labels = [ 'no surfacing', 'flippers']
	return dataSet, labels

def splitDataSet(dataSet,axis,value):
	'''
	划分数据集
	'''
	retDataSet = []
	for featVec in dataSet:
		if featVec[axis] == value:
			reducedFeatVec = featVec[:axis]
			reducedFeatVec.extend(featVec[axis+1:])
			retDataSet.append(reducedFeatVec)
	return retDataSet

def chooseBestFeatureToSplit(dataSet):
	'''
	选择最好的数据集划分方式
	'''
	numFeatures = len(dataSet[0])- 1
	baseEntropy = calcShannonEnt(dataSet)
	print("baseEntropy: ",baseEntropy)
	bestInfoGain = 0.0
	bestFeature = -1
	for i in range(numFeatures):
		print("feature: ",i)
		featSet = set([ example[i] for example in dataSet])
		newEntropy = 0.0
		for value in featSet:
			subDataSet = splitDataSet(dataSet,i,value)
			prob = len(subDataSet) / float(len(dataSet))
			newEntropy += prob * calcShannonEnt(subDataSet)
		infoGain = baseEntropy - newEntropy
		print("infoGain: ",infoGain)
		if infoGain > bestInfoGain:
			bestInfoGain = infoGain
			bestFeature = i
	print("bestFeature: ",bestFeature)
	return bestFeature

def majorityCnt(classList):
	'''
	返回出现次数最多的分类
	'''
	classCount = {}
	for vote in classList:
		classCount[vote] = classCount.get(vote,0) + 1
	sortedClassCount = sorted(classCount.items(),key=operator.itemgetter(1),reverse=True)
	return sortedClassCount[0][0]

def createTree(dataSet,labels):
	classList = [example[-1] for example in dataSet]
	# 如果类别完全一致，则结束分类
	if len(set(classList)) == 1:
		print("only one class: ",classList[0])
		return classList[0]
	# 如果所与特征都遍历完成了，则返回出现次数最多的分类
	if len(dataSet[0]) == 1:
		print("all properity used, majorityCnt: ",majorityCnt(classList))
		return majorityCnt(classList)
	# 继续进行分类
	bestFeat = chooseBestFeatureToSplit(dataSet)
	bestFeatLabel = labels[bestFeat]
	myTree = {bestFeatLabel:{}}
	del(labels[bestFeat])
	featValues = set([example[bestFeat] for example in dataSet])
	for value in featValues:
		subLabels = labels[:]
		myTree[bestFeatLabel][value] = createTree(splitDataSet(dataSet,bestFeat,value), subLabels)
	return myTree

def classify(inputTree,featLabels,testVec):
	'''
	使用算法
	'''
	firstStr = list(inputTree.keys())[0]
	secondDict = inputTree[firstStr]
	featIndex = featLabels.index(firstStr)
	for key in secondDict.keys():
		if testVec[featIndex] == key:
			if type(secondDict[key]).__name__=='dict':
				classLabel = classify(secondDict[key],featLabels,testVec)
			else:
				classLabel = secondDict[key]
			break
	return classLabel

def storeTree(inputTree,filename):
	import pickle
	fw = open(filename,'wb')
	pickle.dump(inputTree,fw)
	fw.close()

def grabTree(filename):
	import pickle
	fr = open(filename,'rb')
	return pickle.load(fr) 
