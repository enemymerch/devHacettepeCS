import os
import re
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
import numpy as np



class Dataset:
    TrainDatasetPaths = ["C:\\Users\\MCan\\PycharmProjects\\BBM406Assignment2\\MRDataset\\train\\neg", "C:\\Users\\MCan\\PycharmProjects\\BBM406Assignment2\\MRDataset\\train\\pos"]
    TestDatasetPaths = ["C:\\Users\\MCan\\PycharmProjects\\BBM406Assignment2\\MRDataset\\test\\neg", "C:\\Users\\MCan\\PycharmProjects\\BBM406Assignment2\\MRDataset\\test\\pos"]

    TrainCorpus = []
    TrainDataLabels = []


    TestCorpus = []
    TestDataLabels = []

    def __init__(self):
        # Train Data Creating corpus and label arrays
        #NEGATIVE - TRAIN
        for filename in os.listdir(self.TrainDatasetPaths[0]):
            if filename.endswith(".txt"):
                file = open(self.TrainDatasetPaths[0] + "\\" + filename, encoding="utf8")
                #Append to Corpus
                self.TrainCorpus.append(file.read())
                #Append to Labels
                self.TrainDataLabels.append(0)

                file.close()



        # Train Data Creating corpus and label arrays
        #POSITIVE - TRAIN
        for filename in os.listdir(self.TrainDatasetPaths[1]):
            if filename.endswith(".txt"):
                file = open(self.TrainDatasetPaths[1] + "\\" + filename, encoding="utf8")
                #Append to Corpus
                self.TrainCorpus.append(file.read())
                #Append to Labels
                self.TrainDataLabels.append(int(1))


        # Test Data Creating corpus and label arrays
        #NEGATIVE - TEST
        for filename in os.listdir(self.TrainDatasetPaths[0]):
            if filename.endswith(".txt"):
                file = open(self.TrainDatasetPaths[0] + "\\" + filename, encoding="utf8")
                #Append to Corpus
                self.TestCorpus.append(file.read())
                #Append to Labels
                self.TestDataLabels.append(0)

                file.close()

        # Test Data Creating corpus and label arrays
        #POSITIVE - TEST
        for filename in os.listdir(self.TrainDatasetPaths[1]):
            if filename.endswith(".txt"):
                file = open(self.TrainDatasetPaths[1] + "\\" + filename, encoding="utf8")
                #Append to Corpus
                self.TestCorpus.append(file.read())
                #Append to Labels
                self.TestDataLabels.append(1)

                file.close()

