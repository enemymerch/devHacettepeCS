import numpy as np
import scipy.io as sio
from sklearn.neighbors import KNeighborsClassifier

#Reads NYTrain.mat file from given dir and returns imInfo
def GetNYTrainSet(dir):
    NYTrainMat = sio.loadmat(dir, squeeze_me=True,
                         struct_as_record=False)
    return NYTrainMat['imInfo']

#Reads RomeTrain.mat file from given dir and return imInfo
def GetRomeTrainSet(dir):
    RomeTrainMat = sio.loadmat(dir, squeeze_me=True,
                               struct_as_record=False)
    return RomeTrainMat['imInfo']

#Reads NYFeature.mat file from given dir and return feats
def GetNYFeatureSet(dir):
    NYFeatureMat = sio.loadmat(dir, squeeze_me=True,
                               struct_as_record=False)
    return NYFeatureMat['feats']

#Reads RomeFeature.mat file from given dir and return feats
def GetRomeFeatureSet(dir):
    RomeFeatureSet =sio.loadmat(dir, squeeze_me=True,
                               struct_as_record=False)
    return RomeFeatureSet['feats']


#This function takes X(Features vector's matrix) and Y(Train vector's matrix)
#And writes it them into X.txt and Y.txt
def WriteXY2File(X, Y):
    FeaturesDataFile = open("X.txt", "w")
    TrainDataFile = open("Y.txt", "w")

    for x in X:
        for c in x:
            if not isinstance(c, str):
                FeaturesDataFile.write(str(c)+" ")
        FeaturesDataFile.write("\n")

    for y in Y:
        for c in y:
            if not isinstance(c, str):
                TrainDataFile.write(str(c)+" ")
        TrainDataFile.write("\n")


#Reads X.# txt and converts strings to X(returns :Features vector matrix nxd nparray)
def GetFeatureVector():
    print("Reading Features from Indexed File...")
    featuresdatafile = open("X.txt", "r")
    featuresdata = []
    for x in featuresdatafile.readlines():
        feature = []
        tokens = x.split(" ")[:-1]
        for c in tokens:
            feature.append(float(c))
        featuresdata.append(feature)

    return np.asarray(featuresdata)

#Reads Y.txt and converts strings to Y(returns :Train vectors matrix nx2 nparray)
def GetTrainVector():
    print("Reading TrainData from Indexed File...")
    TrainDataFile = open("Y.txt","r")
    TrainData = []
    for y in TrainDataFile.readlines():
        Train = []
        tokens = y.split(" ")[:-1]
        for c in tokens:
            Train.append(float(c))
        TrainData.append(Train)

    return  np.asarray(TrainData)

#This method reads all .mat files and
#Creates FeatureData and TrainData matrixes
#FeatureData(NxD), TrainData(Nx2)
#And writes them into txt files
def CreateIndexedData(RomeFeatDir, NYFeatDir, RomeTrainDir, NYTrainDir):
    import os.path
    if os.path.exists("X.txt") and os.path.exists("Y.txt"):
        return
    #Reading .mat files
    RomeFeats = GetRomeFeatureSet(RomeFeatDir)
    NYFeats = GetNYFeatureSet(NYFeatDir)

    RomeTrain = GetRomeTrainSet(RomeTrainDir)
    NYTrain = GetNYTrainSet(NYTrainDir)

    print("Features and Train Files are Loaded!")

    FeatureData = []
    TrainData = []
    print("Parsing Datasets\nAnd Matching Names via Features Data and Train Data \n")

    for tra in RomeTrain:
        TrainData.append([tra.latitude, tra.longitude])
        for feat in RomeFeats:
            if feat.name == tra.name:
                FeatureData.append(np.concatenate([[1], feat.gist, feat.tiny16]))
                break

    # i is a index for NYFeats
    #Because if i didn't inserted NYFeats
    #Feature matrix was going to be like this;
    #X[:27000]--->Rome X[27000:52000]---->NY
    #This way datas are shuffled
    i=0
    for tra in NYTrain:
        TrainData.insert(i, (tra.latitude, tra.longitude))
        for feat in NYFeats:
            if feat.name == tra.name:
                FeatureData.insert(i, np.concatenate([[1], feat.gist, feat.tiny16]))
                i = i+2
                break
    #Write name matched FeatureData and TrainData to textfile
    WriteXY2File(FeatureData, TrainData)

#Calculate W (ClosedForm Formula)
def CalculateW(X, Y):

    Xt = X.transpose()

    m1 = np.dot(Xt,X)
    m2 = np.linalg.pinv(m1)
    m3 = np.dot(m2,Xt)
    W = np.dot(m3,Y)

    return W

#This is our model for lin.reg.
#Takes W and Transpose of X
#Estimates a GPS
def PredictGPS(W, Xt):
    EstimatedCoor = np.dot(Xt,W)
    return EstimatedCoor

def GetTrainSet(NYDir, RomeDir):
    NYTrain = GetNYTrainSet(NYDir)
    RomeTrain = GetRomeTrainSet(RomeDir)

    TrainData = []
    for coor in RomeTrain:
        TrainData.append([coor.latitude, coor.longitude])

    for coor in NYTrain:
        TrainData.append([coor.latitude,coor.longitude])

    TrainData = np.array(TrainData)
    return TrainData[TrainSetBeginIndex:TrainSetEndIndex]

def GetLabelSet(NYDir, RomeDir, TrainSetBeginIndex, TrainSetEndIndex):
    NYTrain = GetNYTrainSet(NYDir)
    RomeTrain = GetRomeTrainSet(RomeDir)


    Label = []
    for coor in RomeTrain:
        Label.append(0)
    i=0
    for coor in NYTrain:
        Label.insert(i,1)
        i = i+2

    Label = np.array(Label)
    return Label[TrainSetBeginIndex:TrainSetEndIndex]
#This is our kNN
#Takes a TrainSetSize and Estimated GPSData Matrix to estimate city
def PredictCity(TrainData,TrainSetBeginIndex, TrainSetEndIndex, EstimatedGPSData, NYTrainDir, RomeTrainDir):
    Label = GetLabelSet(NYTrainDir, RomeTrainDir, TrainSetBeginIndex, TrainSetEndIndex)
    kNN = KNeighborsClassifier(n_neighbors=11)
    #fit only TrainSetSize---> TrainSetSize+TestSetSize = #images
    kNN.fit(TrainData[TrainSetBeginIndex:TrainSetEndIndex],Label)

    PredictedLabels = kNN.predict(EstimatedGPSData)

    return PredictedLabels


def CalculateAccuracy(EstimatedLabels, Labels, NumberOfSamples):
    NumberOfCorrectEstimation = 0
    for i in range(len(EstimatedLabels)):
        if EstimatedLabels[i] == Labels[i]:
            NumberOfCorrectEstimation += 1

    return (NumberOfCorrectEstimation/NumberOfSamples)*100



"Starts HERE! "

#These dir. are used in lot of parts in the code
#Change them before running the code
RomeTrainDir = "C:/Users/MCan/Desktop/Rome_NY_location_images/Rome_Train.mat"
NYTrainDir = "C:/Users/MCan/Desktop/Rome_NY_location_images/NY_Train.mat"
RomeFeatDir = "C:/Users/MCan/Desktop/Rome_NY_location_images/Rome_Features.mat"
NYFeatDir = "C:/Users/MCan/Desktop/Rome_NY_location_images/NY_Features.mat"

#First load all .mat files
CreateIndexedData(RomeFeatDir, NYFeatDir, RomeTrainDir, NYTrainDir)

#Get Datas
Feat = GetFeatureVector()
Train = GetTrainVector()

TrainSetBeginIndex = 0
TrainSetEndIndex = 10000

W = CalculateW(Feat[TrainSetBeginIndex:TrainSetEndIndex], Train[TrainSetBeginIndex:TrainSetEndIndex])

EstimatedGPSCoors = []
#[TrainSetSize+1:TotalDataSize-1] is TestSetSize
for testData in Feat[TrainSetEndIndex:len(Feat)-1]:
    EstimatedGPSCoors.append(PredictGPS(W, testData))


EstimatedCityLabels = PredictCity(Train,TrainSetBeginIndex, TrainSetEndIndex, EstimatedGPSCoors, NYTrainDir, RomeTrainDir)
CityLabels = GetLabelSet(NYTrainDir,
                        RomeTrainDir,
                         TrainSetEndIndex,len(Feat)-1)

print("Our Accuracy is :", CalculateAccuracy(EstimatedCityLabels, CityLabels,(len(Feat)-(TrainSetEndIndex-TrainSetBeginIndex))))


