# -*- coding: utf-8 -*-
"""
Created on Thu Apr 26 18:50:39 2018

@author: Can
"""
import sys
import numpy as np
import dataset as ds
import tensorflow as tf

#init global variables
vectorSize = 200
classNumber = 2;
# placeholders of tensorflow
x = tf.placeholder('float', [None, vectorSize])
y = tf.placeholder('float', [None, classNumber])


def predict(x, hl1, hl2, ol):
    # forward operation
    l1_net = tf.add(tf.matmul(x, hl1['weights']), hl1['biases'])
    l1_out = tf.nn.relu(l1_net)

    l2_net = tf.add(tf.matmul(l1_out, hl2['weights']), hl2['biases'])
    l2_out = tf.nn.relu(l2_net);

    prediction = tf.add(tf.matmul(l2_out, ol['weights']), ol['biases'])

    return prediction

def train(hl1, hl2, ol, dataset, epochs=10, learningRate=0.007):
    #first, forward ---->
    prediction = predict(x, hl1, hl2, ol)
    #then, loss
    loss = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits_v2(logits = prediction, labels=y))
    #optimize
    optimizer = tf.train.GradientDescentOptimizer(learningRate).minimize(loss)
    
    #init tensor session && variables
    session = tf.InteractiveSession()
    tf.global_variables_initializer().run()
    
    for epoch in range(epochs):
        totalLoss = 0
        dataset.resetBatch()
        for _ in range(int(len(dataset.trainingData)/dataset.batchSize)):
            batch_x, batch_y = dataset.nextBatch()
            _, localLoss = session.run([optimizer, loss], feed_dict = {x: batch_x, y: batch_y})
            totalLoss += localLoss 
        print('Total Loss is : ', totalLoss)
        if totalLoss == 0.0:
            break

def evaluate(hl1, hl2, ol, testData, testLabels):
    prediction = predict(x, hl1, hl2, ol)
    correct = tf.equal(tf.argmax(prediction, 1), tf.argmax(y,1))
    accuracy = tf.reduce_mean(tf.cast(correct, 'float'))
    print('acc : ' , accuracy.eval({x:testData, y:testLabels}))



#init neural network parameters
hl1NeuronNumber = 400;
hl2NeuronNumber = 200;
batchSize = 5;

#init neural network
hl1 = {'weights': tf.Variable(tf.random_normal([vectorSize, hl1NeuronNumber])), 'biases':tf.Variable(tf.random_normal([hl1NeuronNumber]))}
hl2 = {'weights': tf.Variable(tf.random_normal([hl1NeuronNumber, hl2NeuronNumber])), 'biases':tf.Variable(tf.random_normal([hl2NeuronNumber]))}
ol = {'weights': tf.Variable(tf.random_normal([hl2NeuronNumber, classNumber])), 'biases':tf.Variable(tf.random_normal([classNumber]))}

#init dataset, thus going to be needing command line agruments
positivesDirectory = sys.argv[1]
negativedDirectory = sys.argv[2]
vectorsDirectory = sys.argv[3]
trainingDataPercentage = int(sys.argv[4])
#init dataset object --> includes every data that is going to be needed
myDataset = ds.Dataset(positivesDirectory=positivesDirectory, negativesDirectory=negativedDirectory, vectorsDirectory=vectorsDirectory, trainingDataPercentage=trainingDataPercentage, batchSize=batchSize)
myDataset.createDatasets()
testData, testLabels = myDataset.testData, myDataset.testLabels

#let's train the model
epochs = 20
learningRate = 0.007
train(hl1, hl2, ol, myDataset, epochs, learningRate)
#then, evaluate
evaluate(hl1, hl2, ol, testData, testLabels)