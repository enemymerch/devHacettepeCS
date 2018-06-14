Created by: Mümin Can Yılmaz


code-files: assignment1.py, utils.py

assignment1.py contains the main code.
utils.py contains methods and classes that used.


utils.py-->class-->CorpusModel : used for reading,getting and dividing the data. Gets dataFileDirectory(string) and reads it(takes each line as sentence, meaning each line is a different data). 

utils.py-->class-->NGramModel(superclass), UniGramModel(subclass), BiGramModel(subclass), TriGramModel(subclass) : these classes are used to represent the ngram models. Contains probabilities, counts and dictionaries. 