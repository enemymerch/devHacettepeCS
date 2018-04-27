import mnist_loader
import numpy as np


def sigmoid(Z):
    return 1.0 / (1.0 + np.exp(-1 * Z))
def sigmoid_prime(Z):
    return sigmoid(Z) * (1.0 - sigmoid(Z))
def tanh(z):
    return (np.exp(z)- np.exp(-1*z) / np.exp(z) + np.exp(-1*z))
def tanh_prime(z):
    return 1.0 - tanh(z)**2
def CalculateZ(W,X):
    return np.dot(W, X)

def CalculateOut(W, X):
    return sigmoid(CalculateZ(W, X))


class NeuralNetwork:

    def __init__(self, HiddenLayerNumber, HiddenLayerNeuronNumbers, OutputLayerNeuronNumber,
                 InputLayerNeuronNumber, LearningRate):

        self.LearningRate = LearningRate

        """Hidden Layer Numbers, type = integer"""
        self.HiddenLayerNumber = HiddenLayerNumber
        """Hidden Layer Neuron Numbers, type = list , len = self.HiddenLayerNumbers """
        self.HiddenLayerNeuronNumbers = HiddenLayerNeuronNumbers

        """Output Layer Neuron Number , also means output number """
        self.OutputLayerNeuronNumber = OutputLayerNeuronNumber

        """Layer Number, +1 for output layer"""
        self.TotalLayerNumber = self.HiddenLayerNumber + 1

        """ Input Number"""
        self.InputLayerNeuronNumber = InputLayerNeuronNumber


        """Init input vectors """
        self.InputVectors = []
        self.LayersInputNumbers = []

        for i in range(self.TotalLayerNumber):
            if i == 0:
                """Input for first Hidden Layer So !"""
                self.InputVectors.append(np.zeros((self.InputLayerNeuronNumber, 1), dtype=float))
                self.LayersInputNumbers.append(self.InputLayerNeuronNumber)
            else:
                self.InputVectors.append(np.zeros((self.HiddenLayerNeuronNumbers[i - 1], 1),dtype=float))
                self.LayersInputNumbers.append(self.HiddenLayerNeuronNumbers[i - 1])


        """Now We're going to initialize the weight vectors"""

        """ WeightVectorNumber, every vector represents a layer  """
        self.WeightVectorNumber = self.TotalLayerNumber

        """We're going to create a list for WeightVectors because every weightVector is going to has a different shape"""
        self.WeightVectors = []

        for i in range(self.WeightVectorNumber):
            if i == self.TotalLayerNumber-1:
                self.WeightVectors.append(np.zeros((self.OutputLayerNeuronNumber, self.LayersInputNumbers[i]), dtype=float))
            else:
                self.WeightVectors.append(np.zeros((self.HiddenLayerNeuronNumbers[i], self.LayersInputNumbers[i]),dtype=float))


        #We're gonna create a list for error's for every Layer
        self.Error = []
        for i in range(self.TotalLayerNumber):
            self.Error.append(0.0) # İnitialize every cost to 0.0 (float)
        self.Error.append(0.0)

    def ForwardPropagation(self, X):
        # We're going to set Input's of every layer according to the X ınput(first input)

        # iteration through every layer
        for i in range(self.TotalLayerNumber):
            if i == 0:#if the layer is the first hidden layer
                self.InputVectors[0] = X
                # FirstLayer's input is  the trainData's Features
            else:
                self.InputVectors[i] = CalculateOut(self.WeightVectors[i-1],self.InputVectors[i-1])
                # Input of i.layer is (i-1 layer's out)

        # Now every X(Input) for every layer is set !
        #In the end , we're going to calculate the OUTPUT
        OutputLayerIndex = self.TotalLayerNumber-1
        self.Output = CalculateOut(self.WeightVectors[OutputLayerIndex], self.InputVectors[OutputLayerIndex])

    def BackPropagation(self, Y_target):
        # In this method, going to calculate error's for every layers

        OutputLayerIndex = self.TotalLayerNumber-1
        i = self.TotalLayerNumber
        #iteration start from the TAIL to the HEAD
        while i > -1:
            if i == OutputLayerIndex+1:# if this layer is the E layer !
                self.Error[i] = (self.Output - Y_target)
            elif i == OutputLayerIndex:
                self.Error[i] = self.Error[i+1]*sigmoid_prime(CalculateZ(self.WeightVectors[i], self.InputVectors[i]))
            else:
                t = (np.dot(self.Error[i+1].transpose(),self.WeightVectors[i+1])).transpose()
                self.Error[i] = t * sigmoid_prime(CalculateZ(self.WeightVectors[i], self.InputVectors[i]))
            i = i -1
        #Now we setted Error's of every layer according to the output !
    def UpdateWeights(self):
        for i in range(self.TotalLayerNumber):
            t = (np.dot(self.Error[i], self.InputVectors[i].transpose()))
            self.WeightVectors[i] = self.WeightVectors[i] - (self.LearningRate*t/1)
    def UpdateWeight(self, LayerIndex):
        t = (np.dot(self.Error[LayerIndex], self.InputVectors[LayerIndex].transpose()))
        self.WeightVectors[LayerIndex] = self.WeightVectors[LayerIndex] - (self.LearningRate*t)

    def toString(self):
        print("Total Layer Numbers: ", self.TotalLayerNumber)
        print("Hidden Layer Numbers: ", self.HiddenLayerNumber)
        print("Hidden Layer Neuron Numbers: ", self.HiddenLayerNeuronNumbers)
        print("Output Layer Neuron Numbers: ", self.OutputLayerNeuronNumber)
        print("\nHere goes our layers :")
        for i in range(self.TotalLayerNumber):
            if i == self.TotalLayerNumber-1:
                print("This is the output layer")
            else:
                print("This is the ", i+1, " hidden layer")
            print("Shape of InputVector of this layer is : ", self.InputVectors[i].shape)
            print("Shape of WeightVector of this layer is : ", self.WeightVectors[i].shape)

