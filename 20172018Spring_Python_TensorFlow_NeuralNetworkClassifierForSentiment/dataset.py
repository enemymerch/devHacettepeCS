# -*- coding: utf-8 -*-
"""
Created on Thu Apr 26 17:46:30 2018

@author: Can
"""


import numpy as np
from random import shuffle



class Dataset:
    def __init__(self, positivesDirectory="positives.txt", negativesDirectory="negatives.txt", vectorsDirectory="vectors.txt", trainingDataPercentage=80, batchSize=5):
        self.vectorLength = 200
        self.trainingDataPercentage = trainingDataPercentage
        self.batchSize = batchSize
        self.nextBatchIndex = 0
        
        #init directories
        self.positivesDirectory = positivesDirectory;
        self.negativesDirectory = negativesDirectory;
        self.vectorsDirectory = vectorsDirectory;
        
        #reading positives
        with open(self.positivesDirectory)as f:
            positiveRawData = f.readlines()    
        self.positiveRawData = [line.strip() for line in positiveRawData]
        self.positiveDataLength = len(self.positiveRawData)
        # reading negatives
        with open(self.negativesDirectory)as f:
            negativeRawData = f.readlines()
        self.negativeRawData = [line.strip() for line in negativeRawData]
        self.negativeDataLength = len(self.negativeRawData)

        #reading vectors for words
        with open(self.vectorsDirectory)as f:
            vectorData = f.readlines()    
        self.word2vec = {};
        for line in vectorData:
            tokens = line.split(":")
            vector = [];
            for number in (tokens[1].split(" ")):
                vector.append(float(number.strip()))
            self.word2vec[tokens[0].strip()] = np.asarray(vector)

    # train and test
    def createDatasets(self):
        # going to sufffle negative and positive raw datas togather --> raw, not vector but sentence
        self.rawData = (self.positiveRawData + self.negativeRawData)
        shuffle(self.rawData)

        # going to create the labels for self.rawData and labels are goning to be one_hot=true
        self.rawDataLength = len(self.rawData)
        self.labels = np.zeros((self.rawDataLength, 2))
        for i in range(self.rawDataLength):
            sentence = self.rawData[i]
            if sentence in self.positiveRawData:
                self.labels[i,0] = 1 # sentence is positive
            else:
                self.labels[i,1] = 1 # sentence is negative
 

        # now going to create vectors for corresponding rawData
        self.data = np.ones((self.rawDataLength, self.vectorLength), dtype=float)
        for i in range(self.rawDataLength):
            sentence = self.rawData[i]
            sentenceVector = np.zeros(self.vectorLength, dtype=float)
            for word in (sentence.split(" ")):
                if word in self.word2vec.keys():
                    sentenceVector += self.word2vec[word.strip()]
            self.data[i,:] = sentenceVector

        self.dataLength = len(self.data)
        # init TrainingSet and TestSet
        self.initTrainingSet()
        self.initTestSet()

    def initTrainingSet(self):
        self.trainingDatasetLength = int(self.rawDataLength*(self.trainingDataPercentage/100))
        startIndex = 0
        endIndex = startIndex + self.trainingDatasetLength 
        self.trainingData = self.data[startIndex:endIndex,:]
        self.trainingLabels = self.labels[startIndex:endIndex,:]
    def initTestSet(self):
        startIndex = self.trainingDatasetLength
        self.testData = self.data[startIndex:,:]
        self.testLabels = self.labels[startIndex:,:]


    def nextBatch(self):
        if (self.nextBatchIndex + self.batchSize) > self.trainingDatasetLength:
            return None, None
        else:
            self.nextBatchIndex = self.nextBatchIndex + self.batchSize
            return self.trainingData[self.nextBatchIndex:self.nextBatchIndex+self.batchSize,:], self.trainingLabels[self.nextBatchIndex:self.nextBatchIndex+self.batchSize,:]

    def resetBatch(self):
        self.nextBatchIndex = 0