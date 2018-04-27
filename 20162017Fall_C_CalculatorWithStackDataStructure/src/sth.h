/*
	Mümin Can 
	Yılmaz
	21228932
*/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*Strucks for stacks and queue*/
typedef struct OperandStack
{
	int Operand;
	struct OperandStack * next;
}OperandStack;

typedef struct OperatorStack
{
	char Operator;
	struct OperatorStack * next;
}OperatorStack;

typedef struct Queue
{
	char outputString[50];
	struct Queue * next;
}Queue;


/*Declarations of functions*/
Queue * GetNewQueueElement(char * outputString);
void PushToQueue(Queue ** head, Queue ** tail, Queue *  element);
Queue * PopFromQueue(Queue **head, Queue **tail);
int isQueueEmpty(Queue * head, Queue *tail);


OperandStack * GetNewOperandStackElement(const int Operand);
void PushToOperandStack(OperandStack ** head, OperandStack *  newElement);
OperandStack * PopFromOperandStack(OperandStack ** head);
OperandStack * TopFromOperandStack(OperandStack ** head);
int isOperandStackEmpty(OperandStack *head);

OperatorStack * GetNewOperatorStackElement(const char Operator);
void PushToOperatorStack(OperatorStack ** head, OperatorStack * newElement);
OperatorStack * PopFromOperatorStack(OperatorStack ** head);
OperatorStack * TopFromOperatorStack(OperatorStack ** head);
int isOperatorStackEmpty(OperatorStack *head);

void freeStacks(OperandStack * st1, OperatorStack *s2);