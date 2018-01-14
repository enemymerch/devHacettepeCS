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
		/* QUEUE */

/* Creates a new Queue element in heap and return a pointer to it*/
Queue * GetNewQueueElement(const char * outputString){
	/* Allocate memory from heap for queue element*/
	Queue * newElement = malloc(sizeof(Queue));

	/*Copy outputString into the element*/
	strncpy(newElement->outputString, outputString, 50);

	/*New element point to NULL*/
	newElement->next = NULL;

return newElement;
}

/*Pushes a created element into the queue */
void PushToQueue(Queue ** head, Queue ** tail, Queue *  element){
	if(*head==NULL && *tail==NULL){/* if head is null so tail should be and that should make my if-else statement : "head == NULL && tail == NULL.*/
	/*But i used  || OR statement no matter what ! --> just to be sure*/

	/*Queue is empty*/
	*head = element;
	*tail = element;
	/*So tail and head now is pointing to only the one (first) element of the queue*/
	}else{
		/*Queue is not empty*/
		/*new element will be added to the tail of the queue*/
		/*So head won't change but tail will*/
		
		(*tail)->next = element;
		*tail = element;
	}
	/* passed by reference -- no need to return anything !*/
}

/*Pops a element from queue - from the head of the list*/
Queue * PopFromQueue(Queue **head, Queue **tail){
	if(*head==NULL || *tail == NULL){
		return NULL;
	}
	Queue * ite = *head;

	*head = (*head)->next;

	/*First element poped*/
	return ite;/*Returning first element*/
}

/*is Queue is empty ?*/
int isQueueEmpty(Queue * head, Queue *tail){
	if(head == NULL || tail == NULL){
		return 1;
	}else{
		return 0;
	}
}

		/*OPERAND STACK*/


/*Creates a new Operand stack element and returns a pointer to it*/
OperandStack * GetNewOperandStackElement(const int Operand){
	OperandStack * newElement = malloc(sizeof(OperatorStack));

	newElement->Operand = Operand;

	newElement->next = NULL;

	return newElement;
}


/*Pushed a created element to the operand stack*/
void PushToOperandStack(OperandStack ** head, OperandStack *  newElement){
	if(*head == NULL){
		*head = newElement;
	}else{
		newElement->next = *head;
		*head = newElement;
	}
}

/*Pops a element from the operand stack*/
OperandStack * PopFromOperandStack(OperandStack ** head){
	if(*head == NULL){
		return NULL;
	}
	
	OperandStack * ite = *head;
	*head = ite->next;
	
	return ite;
}


/*Return a pointer to the top element of the operand stack*/
OperandStack * TopFromOperandStack(OperandStack ** head){
	return *head;
}

/*is operand stack is empty ?*/
int isOperandStackEmpty(OperandStack *head){
	if(head == NULL){
		return 1;
	}else{
		return 0;
	}
}


					/*OPERATOR STACK */
/*Creates a new operator stack element and returns a pointer to it*/
OperatorStack * GetNewOperatorStackElement(const char Operator){

	OperatorStack * newElement = malloc(sizeof(OperatorStack));

	newElement->Operator = Operator;

	newElement->next = NULL;

	return newElement;
}

/*Pushes a new element to the operator stack*/
void PushToOperatorStack(OperatorStack ** head, OperatorStack * newElement){
	if(*head == NULL){
		*head = newElement;
	}else{
		newElement->next = *head;
		*head = newElement;
	}
}

/*Pops the top element from the stack and return a pointer to it*/
OperatorStack * PopFromOperatorStack(OperatorStack ** head){
	if(*head == NULL){
		return NULL;
	}

	OperatorStack * ite = *head;
	*head = ite->next;

	return ite;
}

/*Return a pointer to top element from the stack*/
OperatorStack * TopFromOperatorStack(OperatorStack ** head){
	return *head;
}
/* is Operator stack empty ?*/
int isOperatorStackEmpty(OperatorStack *head){
	if(head == NULL){
		return 1;
	}else{
		return 0;
	}
}

void freeStacks(OperandStack * st1, OperatorStack *st2){
	free(st1);
	free(st2);
}