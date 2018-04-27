import mnist_loader
import numpy as np


def sigmoid(z):
    t = 1.0/(1.0 + np.exp(-1*z))
    return t

def sigmoid_prime(z):
    t = sigmoid(z)*(1-sigmoid(z))
    return t
def tanh(z):
    return (np.exp(z)- np.exp(-1*z) / np.exp(z) + np.exp(-1*z))
def tanh_prime(z):
    return 1.0 - tanh(z)**2

def tanh(z):
    t1 = np.exp(z)-np.exp((-1)*z)
    t2 = np.exp(z)+np.exp((-1)*z)
    return t1/t2

def CalculateOut(W, X):
    Z = np.dot(W, X)
    return sigmoid(Z)

def CalculateZ(W,X):
    return np.dot(W, X)

def EvaluateGradient(Target, Out, Z, X):
    sp = sigmoid_prime(Z)
    t = ((Out-Target)*sp)
    return np.dot(t, X.transpose())

def Train(W, X, Target, max_iter, learning_rate=0.001):
    for i in range(max_iter):
        weigths_grad = EvaluateGradient(Target, CalculateOut(W, X), CalculateZ(W, X), X) # calculate grad_desc with loss func.
        T = W - (learning_rate * weigths_grad) # weight update
        W = T
    return W

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

TrainingDataSet, ValidationDataSet, TestDataSet = mnist_loader.load_data_wrapper()
"""len.s"""
TrainingDataSetLen = len(TrainingDataSet)
TrainingDataInputsLen = len(TrainingDataSet[0][0])
TrainingDataLabelLen = len(TrainingDataSet[0][1])

"""Training ınputs shape 50,000x784"""
X = np.random.rand(TrainingDataSetLen ,TrainingDataInputsLen)
"""Training labels shape 50,000x10"""
Y = np.random.rand(TrainingDataSetLen, TrainingDataLabelLen)

"""now we have our arrays for calculations"""
for i in range(TrainingDataSetLen):
    X[i] = TrainingDataSet[i][0].transpose()
    Y[i] = TrainingDataSet[i][1].transpose()



"""Sigle layer network
multi class classifier !
output of the network will be a 1x10 vector !
every x 1x784 -->784 tane input var
bunlar output layer'ında 10 tane nöron olacak
bu 10 nöron'un her biri bir digit 'i gösteriyor."""


"""here we go """
""" created weight array contains 10 vectors of 1x784"""
OutputNeuronNumber = 10



W = np.zeros((OutputNeuronNumber, TrainingDataInputsLen),dtype=float)

Y_Est = np.zeros((TrainingDataSetLen,OutputNeuronNumber), dtype=float)

learning_rate = 0.0001
max_iter = 200
batch_size = 100 # batch size should be 5k
# gonna train the  network for evert trainnig exp



for i in range(int(TrainingDataSetLen/batch_size)):
    # train specific training data
    #W = Train(W, TrainingDataSet[i][0], TrainingDataSet[i][1], max_iter, learning_rate)
    #print(i)
    lower_bound = i*batch_size
    upper_bound = (i+1)*batch_size
    a = TrainingDataSet[lower_bound:upper_bound]
    trainData = GetXY(batch_size,a, TrainingDataInputsLen, TrainingDataLabelLen)
    W = Train(W, trainData[0], trainData[1], max_iter, learning_rate)
    #if T.all() == W.all():
    #    print("wwww")
    print(i*batch_size)

"""Gonna calculate accuracy"""
count = 0
for i in range(len(TestDataSet)):
    out = CalculateOut(W,TestDataSet[i][0])
    label = np.argmax(out)
    if label == TestDataSet[i][1]:
        count = count +1

acc = (count/len(TestDataSet))*100
print("Our  acc is :", acc)