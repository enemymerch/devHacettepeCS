from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
import numpy as np
from sklearn.feature_extraction.text import HashingVectorizer
import DatasetClass
import math as mt



def accuracy(realLabels, estimatedLabel):
    counter = 0
    length =len(realLabels)
    for i in range(length):
        if realLabels[i] == estimatedLabel[i]:
            counter = counter + 1

    return (counter/length)*100


def NB(TestVectors, negLabeledWordProbs, posLabeledWordProbs):
    p1 = 0
    p2 = 0
    prior = mt.log1p(0.5)
    EstimatedLabels = []
    for i in TestVectors:
        testVector = i.toarray()[0]
        likelihood = 0.0

        likelihood = np.dot(testVector, negLabeledWordProbs)

        p1 = likelihood + prior
        likelihood = np.dot(testVector, posLabeledWordProbs)

        p2 = likelihood + prior

        if p1 > p2:
            EstimatedLabels.append(0)
        else:
            EstimatedLabels.append(1)

    return EstimatedLabels

MRDataset = DatasetClass.Dataset()

cV = CountVectorizer(analyzer='word',ngram_range=(1, 1),min_df=0.1 , max_df=0.4)
Transformer = TfidfTransformer(smooth_idf=True, use_idf=True)



print("Here we go !")

processType = 1
#processType == 1 for Normal
#processType != 1 for Tf-idf


TrainVectors = (cV.fit_transform(MRDataset.TrainCorpus))
TrainVectors_Tf =Transformer.fit_transform(TrainVectors)

EstimatedLabels = []

if processType == 1:
    TrainVectors = TrainVectors.toarray()

    vocablaryLength = len(cV.get_feature_names())
    print("Vocablary size :" , vocablaryLength)


    negLabeledWordCount = TrainVectors[0:12500].sum(axis=0)
    TotalNegWordCount = negLabeledWordCount.sum()

    negLabeledWordProbs = np.zeros(vocablaryLength, dtype=float)

    negLabeledWordProbs = np.log1p((negLabeledWordCount+1)/(float(vocablaryLength +TotalNegWordCount)))

    posLabeledWordCount = TrainVectors[12500:25000].sum(axis=0)
    TotalPosWordCount = posLabeledWordCount.sum()

    posLabeledWordProbs = np.zeros(vocablaryLength, dtype=float)
    posLabeledWordProbs = np.log1p((posLabeledWordCount+1)/(float(vocablaryLength +TotalPosWordCount)))


    TestVectors = cV.transform(MRDataset.TestCorpus)

    EstimatedLabels = NB(TestVectors, negLabeledWordProbs, posLabeledWordProbs)

else:
    TrainVectors_Tf = TrainVectors_Tf.toarray()

    vocablaryLength = len(cV.get_feature_names())
    print("Vocablary size :" , vocablaryLength)

    ## NEGATIVE CLASS
    NegLabeledWordFreq = TrainVectors_Tf[0:12500].sum(axis=0)
    TotalNegLabeledWordFreq = NegLabeledWordFreq.sum()

    NegLabeledWordProbs = np.zeros(vocablaryLength, dtype=float)

    NegLabeledWordProbs = (NegLabeledWordFreq+1)/(float(vocablaryLength +TotalNegLabeledWordFreq))


    ## POSITIVE CLASS
    PosLabeledWordFreq = TrainVectors_Tf[12500:25000].sum(axis=0)
    TotalPosLabeledWordFreq = PosLabeledWordFreq.sum()

    PosLabeledWordProbs = np.zeros(vocablaryLength, dtype=float)
    PosLabeledWordProbs = (PosLabeledWordFreq+1)/(float(vocablaryLength +TotalPosLabeledWordFreq))


    TestVectors = cV.transform(MRDataset.TestCorpus)
    TestVectors_Tf = Transformer.transform(TestVectors)

    EstimatedLabels = NB(TestVectors_Tf, NegLabeledWordProbs, PosLabeledWordProbs)


acc = accuracy(MRDataset.TestDataLabels, EstimatedLabels)

print(acc)

