### To RUN
run the files as pointed as at the .pdf 

```bash
python3 assignment4.py positive.txt negative.txt vectors.txt 75
```
if, no command line args are given, program will look for needed files in the working directory and tale trainingDataPercentage as %80.

##### Default Variables :
Learning rate default is 0.007. You can change it in the line :
```python
learningRate = 0.007 # line 85
train(hl1, hl2, ol, myDataset, epochs, learningRate ) # line 85
```

Batch Size default is 5. You can change it in the line :
```python
batchSize = 5; # lines 66
```

Hidden Layers Neuron Numbers. You can change it in the lines:
```python
hl1NeuronNumber = 400; # line 64
hl2NeuronNumber = 200; # line 65
```


###  dataset.py
dataset.py includes a class named dataset. Which manipulates the given files and creates the needed trainnig and test data with labels. Initialization :
```python
myDataset = ds.Dataset(positivesDirectory=positivesDirectory, negativesDirectory=negativedDirectory, vectorsDirectory=vectorsDirectory, trainingDataPercentage=trainingDataPercentage, batchSize=batchSize)
myDataset.createDatasets() # line 80

```


### assignment4.py
includes the main operation code.
initializes the dataset object. Creates the neuron network model.
Then trains it.
