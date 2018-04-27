#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

#define MAX_DIR_LENGTH 200
#define MAX_WORD_LENGTH 50
#define MAX_LINE_LENGTH 512

typedef enum TreeTraversel{
	postOrder = -1,
	inOrder = 0,
	preOrder = 1 
}TraverselType;


typedef struct LocationLog
{
	char fileName[20];
	int count;
	struct LocationLog * nextLog;
}Log;


typedef struct BinarySearchTreeNode
{
	/*String*/
	char str[MAX_WORD_LENGTH];
	/*Depth*/
	unsigned int depth;
	/*String location log list*/
	Log * logList;

	struct BinarySearchTreeNode * Left;
	struct BinarySearchTreeNode * Right;
}Node;


/*Function Declerations*/
Node * RemoveFromTree(Node * root, char * word);
Node * SearchElementIntoTree(Node * root, char * key);
void PrintTree(Node * root, TraverselType type, FILE * outputFile);
void StructTheBinaryTree(Node ** BinarySearchTree, char * DirName);
void Add2Tree(Node ** root, char * word, char * fileName, unsigned int depth);
void SaveLog(Log  ** logList, char * fileName);
Node * CopyElements(Node * Target, Node * Source);
void PrintElement(Node * element, FILE * outputFile);
int GetTotalWordCount(Node * element);
Node *  GetNewTreeElement(char * word);
void FreeTree();
void MeltTxtFileIntoTree(char * txtDir, char * fileName, Node ** root);
int StrCiCmp(char const * a, char const * b) ;
int isTxt(char * dirName);
int isDirectory(char * dirName);
char * CatStr(char * currentDirName, char * nextDirName);


int main(int argc, char ** argv){
	char * inputDir = argv[1];
	/*Goint to read the argv files*/
	FILE * commandFile = fopen(argv[2], "r");
	FILE * outputFile = fopen("output.txt", "w");

	if(!commandFile){
		perror("Command File cannot be opened.\n");
		return 1;
	}

	/*Create root*/
	Node * BinarySearchTree = NULL;
	
	/*Struct the Binary Tree*/
	StructTheBinaryTree(&BinarySearchTree, inputDir);

	/*Start Reading the commands*/
	char lineBuffer[MAX_LINE_LENGTH]; 
	while(fgets(lineBuffer, MAX_LINE_LENGTH, commandFile)){
		printf("%s", lineBuffer);
		printf("Tokens\n");
		fprintf(outputFile, "%s", lineBuffer);

		char * command = strtok(lineBuffer, " \n");

		if(!StrCiCmp(command, "PRINT")){
			TraverselType type;

			strtok(NULL, " \n");
			char * printKey = strtok(NULL, " \n");
			if(printKey == NULL){
				type = postOrder;
			}else if(!StrCiCmp(printKey, "ASC")){
				type = postOrder; 
			}else if(!StrCiCmp(printKey, "DSC")){
				type = inOrder;
			}
			PrintTree(BinarySearchTree, type, outputFile);
		}else if(!StrCiCmp(command, "REMOVE")){
			char * word = strtok(NULL, " \n");
			BinarySearchTree =  RemoveFromTree(BinarySearchTree, word);
		}else if(!StrCiCmp(command, "SEARCH")){
			char * word = strtok(NULL, " \n");
			Node * temp = SearchElementIntoTree(BinarySearchTree, word);
			PrintElement(temp, outputFile);
		}else if(!StrCiCmp(command, "ADD")){
			char * tempDir = strtok(NULL, " \n");
			StructTheBinaryTree(&BinarySearchTree, tempDir);
		}
	}

	fclose(outputFile);
	fclose(commandFile);
	return 0;
}



Node * RemoveFromTree(Node * root, char * word){
	/*Base Case --> root == NULL*/
	if(root==NULL){
		return root;
	}

	/*Our difference*/
	int difference = StrCiCmp(root->str, word);
	/*if the word is smaller than root->str*/
	if(difference>0){/*Smaller*/
		root->Left =  RemoveFromTree(root->Left, word);
	}else if(difference<0){/*Bigger*/
		root->Right =  RemoveFromTree(root->Right, word);
	}else{/*Same*/
		/*Goint to do remove operation*/

		if(root->Left == NULL){
			Node * temp = root->Right;
			free(root);
			return temp;
		}else if(root->Right == NULL){
			Node * temp = root->Left;
			free(root);
			return temp;
		}


		/*going to find the min of right root*/

		Node * temp  = root->Right;
		while(temp->Left=NULL){
			temp = temp->Left;
		}

		root = CopyElements(root, temp);

		RemoveFromTree(root->Right, temp->str);
		
	}
	return root;

}

Node * SearchElementIntoTree(Node * root, char * key){
	/*In - Order Traversel*/
	if(root == NULL){/* NO ITEM, NOPE*/
		return root;
	}
	if(!StrCiCmp(root->str, key)){ /*MATCH ! ! !*/
		return root; /* RETURN MATCH*/
	}

	Node * temp1 = SearchElementIntoTree(root->Left, key);
	Node * temp2 = SearchElementIntoTree(root->Right, key);

	if(temp1 != NULL){/* key found on the left side */
		return temp1;
	}else if(temp2 != NULL){/* key found on the left side */
		return temp2;
	}

	/* NOPE, NOT FOUND */
	return NULL;
}


void PrintTree(Node * root, TraverselType type, FILE * outputFile){
	if(root !=NULL){
		if(type == inOrder){
			PrintTree(root->Left, type, outputFile);
			PrintElement(root, outputFile);
			PrintTree(root->Right, type, outputFile);
		}else if(type == postOrder){
			PrintElement(root, outputFile);
			PrintTree(root->Left, type, outputFile);
			PrintTree(root->Right, type, outputFile);
		}else{/*PreOrder*/
			PrintTree(root->Right, type, outputFile);
			PrintElement(root, outputFile);
			PrintTree(root->Left, type, outputFile);
		}
	}
}



/*DIR OPERATIONS*/

/*This is the recursive function that structs the tree.!*/
void StructTheBinaryTree(Node ** BinarySearchTree, char * DirName){
	printf("\nAt the Top of StructTheBinaryTree\nCurrnet Dir : %s\n", DirName);
	/*First we will go deep in to the dirs*/
	/*Our directory iterator element*/
	struct dirent * ite;

	/*Our DIR */
	DIR * Dir = opendir(DirName);

	/*Check if dir exists*/
	if(!Dir){
		printf("Directory Doesn't Exits.\n");
		return;
	}

	/*Now we're going to iterate in this directory 
	and see if we can go deep into another dir.*/

	/*Before .txt files we're going to look into the other "dir"'s */
	while((ite=readdir(Dir))!=NULL){
		if(isDirectory(ite->d_name)){/*Directory -->> GO DEEP*/
			printf("Found Dir: %s\n", ite->d_name);
			char * temp = CatStr(DirName, ite->d_name);
			printf("Next dir is :%s\n", temp);
			StructTheBinaryTree(&(*BinarySearchTree), temp);
			free(temp);
		}
	}


	/*Now, go back to the head of dir*/
	rewinddir(Dir);

	/*Now going to read the .txt files*/
	while((ite=readdir(Dir))!=NULL){
		if(isTxt(ite->d_name)){
			char * t = CatStr(DirName, ite->d_name);
			MeltTxtFileIntoTree(t, ite->d_name, BinarySearchTree);
			//PrintTree(*BinarySearchTree);
			free(t);
		}
	}
}

int StrCiCmp(char const * a, char const * b) {/*used  "const" , just in case*/
    for (;; a++, b++) {
        int d = tolower(*a) - tolower(*b);
        if (d != 0 || !*a || !*b)
            return d;
    }
}


void SaveLog(Log  ** logList, char * fileName){
	/*Create a new log*/
	Log * newLog = (Log*)malloc(sizeof(Log));
	newLog->nextLog = NULL;
	newLog->count = 1;
	strncpy(newLog->fileName, fileName, 20);

	/*First we're going to see if the file already exitst*/

	if(*logList == NULL){
		*logList = newLog;
		
	}else{
		/*Go to the tail of the list*/
		Log * ite = *logList;


		int found = 0;
		while(ite!=NULL){
			if(!StrCiCmp(ite->fileName, fileName)){
				ite->count = ite->count + 1;
				found =1;
				break;
			}
			ite = ite->nextLog;
		}
		/*Now add the new log to the tail*/
		Log * ite2 = *logList;
		while(ite2->nextLog!=NULL){
			ite2 = ite2->nextLog;
		}
		if(found != 1 && ite2->nextLog == NULL){
			ite2->nextLog = newLog;	
		}
		
	}
}

Node *  GetNewTreeElement(char * word){

	Node * newElement = (Node*)malloc(sizeof(Node));

	newElement->Right = NULL;
	newElement->Left = NULL;
	newElement->logList = NULL;

	newElement->depth = 0;

	strncpy(newElement->str, word, MAX_WORD_LENGTH);

	return newElement;
}

int GetTotalWordCount(Node * element){
	Log * ite = element->logList;
	int total = 0;
	while(ite != NULL){
		total = total + ite->count;
		ite = ite->nextLog;
	}
	return total;
}


void Add2Tree(Node ** root, char * word, char * fileName, unsigned int depth){
	/*TODO:: Have to chech if this word already exits*/
	depth++;	

	if(*root==NULL ){
		/*Add new element*/
		Node * newElement = GetNewTreeElement(word);
		newElement->depth = depth;
		*root = newElement;

		/*Log*/ 
		SaveLog(&((*root)->logList), fileName);

	}else{
		int difference = StrCiCmp((*root)->str, word);
		if(difference<0){
			Add2Tree(&((*root)->Right), word, fileName, depth);
		}else if(difference>0){
			Add2Tree(&((*root)->Left), word, fileName, depth);
		}else{
			SaveLog(&((*root)->logList), fileName);
		}
	}
}


Node * CopyElements(Node * Target, Node * Source){


	Target->depth = Source->depth;

	strncpy(Target->str, Source->str, MAX_WORD_LENGTH);

	Target->logList = Source->logList;
	return Target;
}

void PrintElement(Node * element, FILE * outputFile){
	printf("%s\n", element->str);
	fprintf(outputFile, "%s\n", element->str);
	
	int total = GetTotalWordCount(element);
	printf("\t%d\n", total);
	fprintf(outputFile, "\t%d\n", total);
	
	printf("\t%d\n", element->depth);
	fprintf(outputFile, "\t%d\n", element->depth);

	Log * ite = element->logList;
	while(ite!=NULL){
		printf("\t%s %d\n", ite->fileName, ite->count);
		fprintf(outputFile, "\t%s %d\n", ite->fileName, ite->count);
		ite = ite->nextLog;
	}
}




void FreeTree(){
/*TODO::*/
}


/*Given char pointer to .txt file dir,
  Read words from .txt file and  add them to the tree
  :: Iterative function*/
void MeltTxtFileIntoTree(char * txtDir, char * fileName, Node ** root){

	/*Let's open the file*/
	FILE * txtFile ;
	txtFile = fopen(txtDir, "r");


	if(txtFile == NULL){
		printf("Cannot open the files in this dir : %s\n", txtDir);
		return;
	}


	/*Word Buffer for reading*/
	char  wordBuffer[1024] ;

	/*Start reading the file word by word*/

	/*Keep reading a word at a time till EOF(End of FILE)*/
	while((fscanf(txtFile, "%1024s", wordBuffer)==1)){
		/*TODO:: Have to restruct the string && check string for Compatibility*/
		Add2Tree(root, wordBuffer, fileName, 0);

	}
	/*Free buffer and close the file*/
	fclose(txtFile);
}


/*OTHER FUNCTIONS*/

/*Compares String Case insensitive*/


/*DIR OPERATIONS*/



int isTxt(char * dirName){
	if((strstr(dirName, ".txt"))!=NULL){
		/*.txt file --> Not a directory*/
		return 1;
	}

	/*Not a .txt file*/
	return 0;
}

int isDirectory(char * dirName){

	/*Cheching POSIX's "." and ".."*/
	if(!strcmp(dirName, ".") || !strcmp(dirName, "..") ){
		/* "." or ".." , --> Not a directory*/
		return 0;
	}

	/*Goint to check if this file is a .txt file*/
	if((strstr(dirName, ".txt"))!=NULL){
		/*.txt file --> Not a directory*/
		return 0;
	}

	/*This is a directory*/
	return 1;
}

/* Takes: str1 = "./root/usr" , str2 ="local" 
	Returns: a pointer to "./root/usr/local" 
	(New string will be kept in heap) */
char * CatStr(char * currentDirName, char * nextDirName){
	/*Get space from the heap*/
	char * temp = (char*)malloc(sizeof(char)*MAX_DIR_LENGTH);

	/*Now, going to concatenate these strings
	 But, before concatenating them , have to concatenate "/" to temp*/
	strcpy(temp, currentDirName);
	strcat(temp, "/");
	strcat(temp, nextDirName);

	return temp;
}



