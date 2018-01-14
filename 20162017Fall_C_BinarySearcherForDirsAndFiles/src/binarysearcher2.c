#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>
#include <ctype.h>

typedef struct list
{
    char* list_name;
    int number;
    struct list*next;
}List;
typedef struct cell
{
    char* wordName;  /* name of the word */
    int totalNumber; /* total number of word in the dictionary */
    int depth;        /* word's depth in the tree */
    struct cell* left; /* left node of the word */
    struct cell* right; /* right node of the word */
    struct list* listInfo; /* list of file information about the word */
}Cell;
/* In readAllTexts function , dir parameter denotes the given input directory, str denotes to path of all directories into system and dictionary denotes to binary search tree*/
/* In this function, if program encounter with some .txt file creates this file's path as string and calls readSingleTextFile function*/
/* But if encounters with another directory, then updates path, open this new directory and recursively returns. */
void readAllTexts(DIR* dir,char* str,Cell** dictionary);

/* In readAllTexts function , file parameter denotes the input file,  dictionary denotes to binary search tree and fileName denotes to name of the input file*/
/* In this function, program simply reads all words into the current file, convert all words as lower case strings and add these words into the tree*/
void readSingleTextFile(FILE*file, Cell** dictionary, char* fileName);

/* In addWordIntoTree function ,  dictionary denotes to binary search tree, word denotes to current word and fileName denotes to name of the input file*/
/* In this function, if the word is not exist in the tree, find a position for this word in the tree and add into that place. If node is already exist in the tree update total number and list informations about this node.  */
void addWordIntoTree(Cell**dictionary,char* word,char* fileName);

/* This function takes givenString variable as input convert to lower case form and finally returns the new string. */
char* convertStringToLowerCase(char* givenString);

/* In this function, dictionary denotes the tree and givenWord denotes to a word name which is wanted to be removed into tree. */
/* In that function, if the given word's right node is empty then the parent node of this node's left node equal to given word's left node and also delete the given word. If given word's right node is not empty then, find leftmost node on the right node of given word. Replace it. And finally delete this node.  */
void deleteWordFromTree(Cell** dictionary,char* givenWord);

/* In readCommandFile function, commandFileName parameter denotes to command.txt file's path and dictionary denotes the binary search tree.*/
/* In this function, program reads commands.txt file and executes all commands. */
void readCommandFile(char*commandFileName,Cell** dictionary);

/* In searchWord function, dictionary denotes to binary search tree and str denotes to searched node into tree. */
/* In that function, program tries to find this word's node. If it's find the node, then print node's all informations. If not returns. */
void searchWord(Cell** dictionary,char* str);

/* In this function root denotes to head of the tree and control flag determines which type should this function print the tree. */
/* If control flag is equal to one, function prints the function with ascending order, if it is equal to two, function prints the tree with descending order. */
void printTree(Cell* root,int control);

/* In this function head parameter denotes to input node and fileName denotes to input node's file name. */
/* If head was firstly read by the program list information updated as number of the word with one and file name with the given fileName parameter.*/
/* If given word already exists in the tree and program reads this word again in the same or existence file, then finds the file name and update number of the word for given file. */
/* If the given word is already exists in the tree  and program reads this word again in the different file, update the informations according to that situation. */
void updateListInformation(Cell*head,char*fileName);

void updateDepths(Cell* root,int newDepth);
int main(int argc, char *argv[])
{
    Cell** dictionary=NULL; /* Binary search tree. */
    DIR *dir;               /* Initial directory object. */
    dir=opendir(argv[1]);    /* Reads the given directory. */
    char* str=(char*)malloc(200*sizeof(char)); /* Store the path */
    strcpy(str, argv[1]);
    str[strlen(str)-2]=0;

    readAllTexts(dir,str, dictionary); /* Reads all words and add into the tree. */


    char* commandFileName= (char*)malloc(200*sizeof(char));
    strcpy(commandFileName,argv[2]); /* Reads the command.txt file. */
    readCommandFile(commandFileName,dictionary); /* Read and implement commands. */

    closedir(dir);     /* Close the directory. */
    return 0;
}
void readAllTexts(DIR*dir,char* str,Cell** dictionary)
{
    struct dirent *direntry; /* could be a file, or a directory */


        while((direntry=readdir(dir))!=NULL) /* Reads all file of the given directory  */
    {

         if(strstr(direntry->d_name,".txt")!=NULL || strstr(direntry->d_name,".text")!=NULL) /* If the current file is a txt file then this bracket will be operated */
         {
         char* tempString =(char*)malloc(100*sizeof(char)); /* creates temporary string, this string denotes of this text file's path */
         strcpy(tempString,str);
         strcat(tempString,"\\");
         strcat(tempString,"\\");
         strcat(tempString,direntry->d_name);

         FILE* file =fopen(tempString,"r");     /* opens and reads this file  */

         readSingleTextFile(file,dictionary,direntry->d_name); /* reads this file objects with file object, dictionary as binary tree and this file's name with direnty->d_name variable */

         fclose(file);      /* close the file object */

         }else if(strcmp(direntry->d_name,".")!=0 && strcmp(direntry->d_name,"..")!=0) /* if the given file is a directory then update the path and read this new directory */
         {

             strcat(str,"\\");
             strcat(str,"\\");
             strcat(str,direntry->d_name); /* path was updated with new file name */

             dir=opendir(str); /* opens a new directory */
             readAllTexts(dir,str,dictionary); /* then reads this directory recursively */
         }
    }



}
void readSingleTextFile(FILE*file,Cell** dictionary,char* fileName)
{
    char* pch=(char*)malloc(200*sizeof(char)); /*  This variable denotes to a string without all punctuation marks. Except '-' */
    strcpy(pch,"");
    int flag=0;
    if(file!=NULL)
    {
        char line [ 20000 ]; /* or other suitable maximum line size */
        while ( fgets ( line, sizeof line, file ) != NULL ) /* read a line */
        {

            pch= strtok(line," ,)(?=%!\n\t\r^#\\/_{}&:[]*+");   /* Splits the line with these punctuation marks */

            while(pch!=NULL)
            {
                  addWordIntoTree(dictionary,convertStringToLowerCase(pch),fileName); /* Convert the word to lower case and then add current word into the binary search tree.  */
                  pch= strtok(NULL," ,)(?=%!\n\t\r^#\\/_{}&:[]*+");
            }

        }
    }
}
void addWordIntoTree(Cell** dictionary,char* word,char* fileName)
{
    if(strcmp(word,"e.g")!=0 && strcmp(word,"e.g.")!=0&&strstr(word,".")!=NULL)
    {
        word[strlen(word)-1]=0;
    }else if(strcmp(word,"e.g")==0 || strcmp(word,"e.g.")==0)
    {
        strcpy(word,"eg");
    }

    Cell* newCell=(Cell*)malloc(sizeof(Cell)); /* Firstly creates a new cell. */
    newCell->listInfo=NULL;

    newCell->wordName=(char*)malloc(100*sizeof(char)); /* allocate a memory for word's name. */

    strcpy(newCell->wordName,word); /* initialize word's name. */
    newCell->left=NULL;             /* initialize left node. */
    newCell->right=NULL;            /* initialize right node. */
    newCell->totalNumber=1;         /* initialize total number. */
    newCell->depth=1;               /* initialize depth. */
    if((*dictionary)==NULL)  /* If dictionary is empty then assign it with new cell and return. */
    {

        newCell->listInfo=(List*)malloc(sizeof(List));
        newCell->listInfo->list_name=(char*)malloc(sizeof(char));
        strcpy(newCell->listInfo->list_name,fileName);
        newCell->listInfo->number=1;
        newCell->listInfo->next=NULL;

        *dictionary=newCell;
        return;
    }

        Cell* head = *dictionary;  /* This variable denotes to head of the dictionary. */
        while(1)
        {

            if(strcmp(head->wordName,newCell->wordName)==0)  /* If tree already has this word then update total number and list information of this node */
            {

                head->totalNumber++;  /* Increase total number. */
                updateListInformation(head,fileName); /* Update list information with that function. */
                //strcat(head->list,"\n");
                //strcat(head->list,newCell->list);
                break;
            }
            else if(strcmp(head->wordName,newCell->wordName)>0) /* If current word name is greater than new cell's word name then this bracket will be operated. */
            {
                newCell->depth++; /* Depth will increase */
                if(head->left==NULL) /* If left node of this node is equal to null then assign it as new cell.  */
                {
                    newCell->listInfo=(List*)malloc(sizeof(List));
                    newCell->listInfo->list_name=(char*)malloc(sizeof(char));
                    strcpy(newCell->listInfo->list_name,fileName);
                    newCell->listInfo->number=1;
                    newCell->listInfo->next=NULL;
                    head->left=newCell;
                    break;
                }
                head=head->left; /* Continue over left node of the root node. */

            }
            else if(strcmp(head->wordName,newCell->wordName)<0) /* If current word name is less than new cell's word name then this bracket will be operated. */
            {
                newCell->depth++; /* Depth will increase */
                if(head->right==NULL) /* If right node of this node is equal to null then assign it as new cell.  */
                {
                    newCell->listInfo=(List*)malloc(sizeof(List));
                    newCell->listInfo->list_name=(char*)malloc(sizeof(char));
                    strcpy(newCell->listInfo->list_name,fileName);
                    newCell->listInfo->number=1;
                    newCell->listInfo->next=NULL;

                    head->right=newCell;
                    break;
                }
                head=head->right; /* Continue over right node of the root node. */

            }
        }

     return;
}

char* convertStringToLowerCase(char* givenString)
{
    int i;
    for( i = 0; givenString[i]; i++)
    {
        givenString[i] = tolower(givenString[i]); /* convert all elements of a string to lower case */
    }
    return givenString;  /* returns new string */
}
void deleteWordFromTree(Cell** dictionary,char* givenWord)
{
    if(*dictionary==NULL) /* If the dictionary is empty then exit the function. */
    {
        return;
    }
    Cell* root=*dictionary;  /* This variable denotes the head of the tree. */
    while(1)
    {

        if(strcmp(root->wordName,givenWord)==0)
        {
            Cell* temp=root->right;
            if(temp->left==NULL) /* If we can not leftmost node then replace root and right node of the root. */
            {
                Cell* newNode = temp;
                strcpy(root->wordName,temp->wordName); /* update root's word name */
                root->totalNumber=temp->totalNumber; /* update root's total number */
                root->listInfo=temp->listInfo;      /* update root's list information */
                root->right=root->right->right;    /* update root's right node */
                free(temp);
                return;
            }
            while(temp->left->left!=NULL) /* finds leftmost of current node's right node. */
            {
                temp=temp->left;
            }

            Cell* node = (Cell*)malloc(sizeof(Cell));
            Cell*deletedTempNode = temp->left;
            node->listInfo=(List*)malloc(sizeof(List)); /* node variable's attributes denotes simply equals to exchanged node's attributes */
            node->listInfo=temp->left->listInfo;

            node->wordName=(char*)malloc(100*sizeof(char));
            strcpy(node->wordName,temp->left->wordName);

            node->totalNumber =temp->left->totalNumber;
            node->depth=temp->left->depth;
            node->left=NULL;
            node->right=NULL;

            if(temp->left->right!=NULL) /* if deleted node's right node is not empty then update links */
            {
                temp->left=temp->left->right;
            }
            else
            temp->left=temp->left->left; /* If not assign it to null */

            free(deletedTempNode);  /* delete exchanged node. */

            root->listInfo=node->listInfo;
            strcpy(root->wordName,node->wordName);
            root->totalNumber=node->totalNumber;
            free(node);

            return;
        }else if(strcmp(root->wordName,givenWord)>0) /* If root's word name greater than the given word then continue the search over root's left node. */
        {
            if(root->left==NULL) /* If there is no left node of the root then returns the function. */
            {
                return;
            }
            if(root->left->left==NULL && strcmp(root->left->wordName,givenWord)==0)
            {
                Cell* temp = root->left;
                root->left=root->left->right;
                free(temp);
                return;
            }
            root=root->left;
        }else if(strcmp(root->wordName,givenWord)<0) /* If root's word name less than the given word then continue the search over root's left node. */
        {
            if(root->right==NULL) /* If there is no right node of the root then returns the function. */
            {
                return;
            }
            if(root->right->right==NULL && strcmp(root->right->wordName,givenWord)==0)
            {
                Cell* temp = root->right;
                root->right=root->right->left;
                free(temp);
                return;
            }
            root=root->right;
        }
    }
    Cell* headOfDictionary=*dictionary;
    updateDepths(headOfDictionary,1);
}

void readCommandFile(char* commandFileName,Cell** dictionary)
{
    FILE* file =fopen(commandFileName,"r");  /* Opens command.txt file. */
    char* str=(char*)malloc(100*sizeof(char));  /* This variable denotes to wanted word's name which may wanted to be searched or removed. */
    char* commandString=(char*)malloc(100*sizeof(char));  /* This variable denotes to all commands */
    if(file!=NULL)
    {
        char line [ 128 ]; /* or other suitable maximum line size */
        while ( fgets ( line, sizeof line, file ) != NULL ) /* read a line */
        {
        strcpy(commandString,line);
            if(strstr(line,"PRINT TREE ASC")!=NULL)  /* If input command is print the tree with ascending order then this bracket will be operated. */
            {
                printf("%s",commandString);  /* Firstly prints the command */
                if(*dictionary!=NULL)
                {
                    Cell* root=*dictionary; /* Travel into tree with root object */
                    printTree(root,1);  /* print tree ascending order. Flag value determines print type*/
                }

            }
            else if(strstr(line,"PRINT TREE DSC")!=NULL) /* If input command is print the tree with descending order then this bracket will be operated. */
            {

                 printf("%s",commandString); /* Firstly prints the command */
                 if(*dictionary!=NULL)
                 {
                     Cell* root=*dictionary; /* Travel into tree with root object */
                     printTree(root,2);       /* print tree with descending order. Flag value determines print type */
                 }

            }
            else if(strstr(line,"SEARCH")!=NULL)  /* If input command is search the given word then this bracket will be operated.*/
            {
                    str=strtok(line," \n");
                    str=strtok(NULL," \n");
                    str=convertStringToLowerCase(str);
                    printf("%s\n",line); /* Prints command */
                    printf("%s\n",str);  /* Prints searched word's name */
                    searchWord(dictionary,str); /* Lastly, search the word with searchWord function*/
            }
            else if(strstr(line,"REMOVE")!=NULL)  /* If input command is remove the given word then this bracket will be operated.*/
            {
                    str=strtok(line," \n");
                    str=strtok(NULL," \n");
                    str=convertStringToLowerCase(str);
                    printf("%s ",line); /* Prints command */
                    printf("%s\n",str); /* Prints removed word's name */
                    deleteWordFromTree(dictionary,str); /* Lastly, remove the word with deleteWordFromTree function*/
            } else if (strstr(line,"ADD")!=NULL)
            {
                    str=strtok(line," \n");
                    str=strtok(NULL," \n");
                    FILE*file=fopen(str,"r");
                    readSingleTextFile(file,dictionary,str); /* Read given path and add these file's whole word into the tree. */
                    fclose(file);
            }

        }
    }
    fclose(file); /* Finally close the file. */
}
void searchWord(Cell** dictionary,char* str)
{
    if(*dictionary==NULL) /* If the dictionary is empty then returns. */
    {
        return;
    }

    Cell* root=*dictionary; /* Initially root variable denotes to head of the tree. */

    while(1)
    {
        if(strcmp(root->wordName,str)==0) /* If searched node is founded, then print that node's information into screen. */
        {
            printf("\t%d\n\t%d\n",root->totalNumber,root->depth);
            List* rootList =root->listInfo;
        while(rootList!=NULL)
            {
                printf("\t%s %d\n",rootList->list_name,rootList->number);
                rootList=rootList->next;
            }
            return;
        }
        else if(strcmp(root->wordName,str)>0) /* If root's word name greater than searched word then continue the search over root's left node. */
        {
            if(root->left==NULL)  /* If that node is equal to null then returns. */
            {
                break;
            }
            root=root->left; /* Update root. */
        }
        else if(strcmp(root->wordName,str)<0) /* If root's word name less than searched word then continue the search over root's right node. */
        {
            if(root->right==NULL) /* If that node is equal to null then returns. */
            {
                break;
            }
            root=root->right; /* Update root. */
        }
    }

}
void printTree(Cell* root,int control)
{
    if(root->right==NULL && root->left==NULL) /* If tree has only one node then prints this node and returns. */
        {
            printf("%s\n\t%d\n\t%d\n",root->wordName,root->totalNumber,root->depth);
            List* rootList =root->listInfo;
        while(rootList!=NULL)
            {
                printf("\t%s %d\n",rootList->list_name,rootList->number);
                rootList=rootList->next;
            }
            return;
        }

    if(control==1) /* That means print the tree with ascending order */
    {
        if(root->left!=NULL)
        {
            printTree(root->left,control); /* Reach leftmost element of the tree and prints this node */
        }
        /* then prints this node's parent node */
        printf("%s\n\t%d\n\t%d\n",root->wordName,root->totalNumber,root->depth);
        List* rootList =root->listInfo;
        while(rootList!=NULL)
            {
                printf("\t%s %d\n",rootList->list_name,rootList->number);
                rootList=rootList->next;
            }

        if(root->right!=NULL) /* then continue over this parent node's right node recursively */
        {
            printTree(root->right,control);
        }
    }
    if(control==2) /* That means print the tree with descending order */
    {
        if(root->right!=NULL)
        {
            printTree(root->right,control); /* Reach rightmost element of the tree and prints this node */
        }

        printf("%s\n\t%d\n\t%d\n",root->wordName,root->totalNumber,root->depth);
        List* rootList =root->listInfo;
        while(rootList!=NULL)
            {
                printf("\t%s %d\n",rootList->list_name,rootList->number);
                rootList=rootList->next;
            }

        if(root->left!=NULL)/* then continue over this parent node's left node recursively */
        {
            printTree(root->left,control);
        }
    }
}
void updateListInformation(Cell*head,char*fileName)
{
    if(head->listInfo==NULL)
    {
        head->listInfo=(List*)malloc(sizeof(List));
        head->listInfo->list_name=(char*)malloc(100*sizeof(char));
        strcpy(head->listInfo->list_name,fileName);
        head->listInfo->number=1;
        head->listInfo->next=NULL;
        return;
    }
    List* temp1=head->listInfo; /* This temporary variable has created because of travel into nodes for update existent node's number */
    List* temp2=head->listInfo; /* This temporary variable has created because of travel into nodes for add new list into list */
    int control=0;
    while(temp1!=NULL)
    {
        if(strcmp(temp1->list_name,fileName)==0)
        {
            temp1->number++;
            return;
        }
        temp1=temp1->next;
    }
    while(temp2->next!=NULL)
    {
        temp2=temp2->next;
    }
    List* newList= (List*)malloc(sizeof(List));
    newList->list_name=(char*)malloc(100*sizeof(char));
    strcpy(newList->list_name,fileName);
    newList->number=1;
    newList->next=NULL;
    temp2->next= newList;
    return;
}
void updateDepths(Cell* root,int newDepth)
{
    if(root==NULL)  /* base condition */
    {
        return;
    }
    root->depth=newDepth; /* first update root's depth then update left and right nodes' depths. */
    updateDepths(root->left,newDepth+1);
    updateDepths(root->right,newDepth+1);
}

