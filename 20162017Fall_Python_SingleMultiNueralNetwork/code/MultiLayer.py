import mnist_loader
import numpy as np
import NeuralNetwork as nn


# to reshape the data according to the batchSize
def GetXY(batchSize, Data, inputSize, outputSize):
    X = np.zeros((batchSize,inputSize))
    Y = np.zeros((batchSize, outputSize))
    if batchSize == len(Data):
        for i in range(batchSize):
            X[i] = Data[i][0].transpose()
            Y[i] = Data[i][1].transpose()

    X = X.transpose()
    Y = Y.transpose()
    trainData = []
    trainData.append(X)
    trainData.append(Y)
    return  trainData

# to reshape the data according to the batchSize
def GetX(batchSize, Data, inputSize):
    X = np.zeros((batchSize,inputSize))
    if batchSize == len(Data):
        for i in range(batchSize):
            X[i] = Data[i][0].transpose()

    X = X.transpose()
    return  X

# For training the Network
def Train(network, X, Y, max_iter):

    for i in range(max_iter):
        network.ForwardPropagation(X)
        network.BackPropagation(Y)
        network.UpdateWeights()



list1 = []
list1.append(30)




TrainingDataSet, ValidationDataSet, TestDataSet = mnist_loader.load_data_wrapper()
"""len.s"""
TrainingDataSetLen = len(TrainingDataSet)
TrainingDataInputsLen = len(TrainingDataSet[0][0])
TrainingDataLabelLen = len(TrainingDataSet[0][1])



learning_rate = 0.2
max_iter = 1
batch_size = 1

myNetwork = nn.NeuralNetwork(1,list1,10,784, learning_rate)

#Training
for i in range(int(TrainingDataSetLen/batch_size)):

    lower_bound = i*batch_size
    upper_bound = (i+1)*batch_size
    trainData = GetXY(batch_size, TrainingDataSet[lower_bound:upper_bound], TrainingDataInputsLen, TrainingDataLabelLen)
    Train(myNetwork, trainData[0], trainData[1], max_iter)
    print(i*batch_size)
count = 0


##Evalution of the test data
for i in range(int(len(TestDataSet)/batch_size)):

    lower_bound = i*batch_size
    upper_bound = (i+1)*batch_size
    testInput = GetX(batch_size, TestDataSet[lower_bound:upper_bound], TrainingDataInputsLen)
    myNetwork.ForwardPropagation(testInput)
    Output = (myNetwork.Output).transpose()

    for j in range(batch_size):
        t = np.argmax(Output[j])
        if TestDataSet[lower_bound+j][1] == t:
            count = count +1

acc = (count / len(TestDataSet)) *100

print("Our accuracy is :", acc)


