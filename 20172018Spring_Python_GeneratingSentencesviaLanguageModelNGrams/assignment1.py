import utils as utls
import numpy as np
import math
import sys


datasetFileName = "C:\\Users\\Can\\workspace\\dataset.txt"
resultFileName = "results.txt";

datasetFileName = sys.argv[1]
resultFileName = sys.argv[2]


isSmoothed = True
corpusModel = utls.CorpusModel(datasetFileName) # needs datasetFileName

#getting corpus
corpus = corpusModel.getDataBetween(0,50);
testData = corpusModel.getDataBetween(50,60);

# uncomment for real run
corpus = corpusModel.getTrainData();
testData = corpusModel.getTestData();


print("Corpus Length : ",  len(corpus))

# creating CountVectorizer objects
uniGramCV = utls.CountVectorizer(analyzer=utls.tokenize, ngram=1);
biGramCV = utls.CountVectorizer(analyzer=utls.tokenize, ngram=2);
triGramCV = utls.CountVectorizer(analyzer=utls.tokenize, ngram=3);

# getting count of the ngram words
uniTempCounts = np.asarray(uniGramCV.fit_transform(corpus))
biTempCounts = np.asarray(biGramCV.fit_transform(corpus))
triTempCounts = np.asarray(triGramCV.fit_transform(corpus))

# getting dictionaries for ngram models
uniGramDictionary = uniGramCV.get_feature_names()
biGramDictionary = biGramCV.get_feature_names()
triGramDictionary = triGramCV.get_feature_names()

# init. ngram models
uniGramModel = utls.UniGramModel(uniGramDictionary, uniTempCounts, isSmoothed)
biGramModel = utls.BiGramModel(biGramDictionary, biTempCounts, isSmoothed)
triGramModel = utls.TriGramModel(triGramDictionary, triTempCounts, isSmoothed)

# calculating ngram model probabilities
uniGramProbabilities = uniGramModel.calculateProbabilityTable();
biGramProbabilities = biGramModel.calculateProbabilityTable(uniGramModel.dictionary, uniGramModel.countTable);
triGramProbabilities = triGramModel.calculateProbabilityTable(uniGramModel.dictionary, biGramModel.countTable);


uniGramGeneratedSentences = [];
biGramGeneratedSentences = [];
triGramGeneratedSentences = [];
resultFile = open(resultFileName,'w') ;
#generating mails
resultFile.write("\n\nGenerated emails and probs.\n\n")
print("\n\nGenerated emails and probs.\n\n")
for i in range(40):
    #unigram
    resultFile.write("\nunigramGenerated:");
    unigramSentence = (uniGramModel.generateEmail());
    tempStr = "prob:"+ str(uniGramModel.getLogProbabilityOfSentence(unigramSentence))+" sentence: "+str(unigramSentence )
    resultFile.write(tempStr);
    
    #bigram
    
    resultFile.write("\nbigramGenerated:");
    bigramSentence = (biGramModel.generateEmail(uniGramDictionary) );
    tempStr = "prob:"+ str(biGramModel.getLogProbabilityOfSentence(bigramSentence, uniGramDictionary, "<s>"))+" sentence: "+ str( bigramSentence );
    resultFile.write(tempStr);
    
    #trigram    
    resultFile.write("\ntrigramGenerated:");
    triGramFirstPrefix = "";
    prefix2Index = biGramModel.getRandom(len(uniGramDictionary), uniGramDictionary.index("<s>"));
    trigramSentence = (triGramModel.generateEmail(uniGramDictionary, uniGramDictionary[prefix2Index]));
    tempStr = "prob:"+ str(triGramModel.getLogProbabilityOfSentence(trigramSentence, uniGramDictionary, "<s>"))+" sentence: "+ str(trigramSentence );
    resultFile.write(tempStr);
    resultFile.write("\n--\n")        





#evaluation
unigramCounter = 0.0;
bigramCounter = 0.0;
trigramCounter = 0.0;
resultFile.write("\n\n\n Evaluation \n\n\n:");
print("\n\n\n Evaluation \n\n\n:");
for data in testData:
    resultFile.write("Sentence : " + data);
    tokens = data.split(" ")    
    N = len(tokens);
    uniGramLogProb = uniGramModel.getLogProbabilityOfSentence(data);
    biGramLogProb = biGramModel.getLogProbabilityOfSentence(data, uniGramDictionary, "<s>")
    triGramLogProb = triGramModel.getLogProbabilityOfSentence(data, uniGramDictionary, "<s>")
    
    uniGramPerplexity  =  math.pow(2, (-1*(uniGramLogProb/N)));
    tempStr = "\nunigramPerplexity : " + str(uniGramPerplexity);
    resultFile.write(tempStr);    
    biGramPerplexity  =  math.pow(2, (-1*(biGramLogProb/N)));
    tempStr = "\nbigramPerplexity : " + str(biGramPerplexity);
    resultFile.write(tempStr)    
    triGramPerplexity  =  math.pow(2, (-1*(triGramLogProb/N)));
    tempStr ="\ntrigramPerplexity : " + str(triGramPerplexity);
    resultFile.write(tempStr);    
    resultFile.write("\n--\n")    
        
    



resultFile.close();
    
    
    
    