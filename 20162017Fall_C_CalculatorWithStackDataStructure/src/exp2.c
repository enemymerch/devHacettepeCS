
/*
	Mümin Can 
	Yılmaz
	21228932
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "sth.h"

/* numeric type enum. Hex - integer */
typedef enum NumericType{
	integer = 1,
	hex = 0
}NumericType;

/*Declarations for function in exp2.c*/
char * CalculateExpression(char **Expression, NumericType type);
int isParentheses(char *p);
int isRightParantheses(char *p);
int isLeftParantheses(char *p);
int isOperator(char *p);
int isNumeral(char *p);
int isParenthesesBalanced(char * expr);
int isExpressionCharactersValid(char *expr);
int isExpressionCharactersValid(char *expr);
int isOperator(char *p);
int getNextOperator(char ** ite);
int getNextNumber(char ** ite, int type);


/*main function*/
int main(int argc, char ** argv){
	/* Open input and output files*/
	FILE * inputFile;
	inputFile =  fopen(argv[1], "r");

	FILE * outputFile;
	outputFile = fopen(argv[2], "w");
	
	/*If There's something wrong with files !*/
	if(inputFile == NULL || outputFile == NULL){
		printf("Could not able to open the files");
		exit(1);	
	}

	/*Now read the input files for commands*/

	char line[100];/*line buffer*/

	Queue * Queue_Head = NULL;
	Queue * Queue_Tail = NULL;


	while((fgets(line, 100, inputFile))!=NULL){/*interprete every line !*/


		char pri[100] = "print";

		if(!strcmp(line, pri)){ /*Command is print !*/
			
			/*Create a new queue element */
			Queue * newElement = GetNewQueueElement("print");
			/*add the create element to the queue*/
			PushToQueue(&Queue_Head, &Queue_Tail, newElement);


			Queue * ite = PopFromQueue(&Queue_Head, &Queue_Tail);
			while(ite != NULL){
				fprintf(outputFile, "%s\n", ite->outputString);
				ite = PopFromQueue(&Queue_Head, &Queue_Tail);
			}
		}else{

			/*tokenizing the  line*/
			char * t1 = strtok(line, " |\"");/*Calculate*/


			/*numeric type integer - hex*/
			NumericType type ;

			char *t ;
			t = strtok(NULL, " |\"");

			if(strcmp(t,"integer") == 0){/*Checking the base of numbers in expression HEX - integer */
				type = integer;
			}
			else{
				type = hex;
			}

			/*Expression in  "....." */
			char * Expression = strtok(NULL, " \"");

			char * tempExp = malloc(sizeof(char)*50);
			strcpy(tempExp, Expression);
			
			/*output string for adding to the queue*/
			char * output;
			
			/*calculate expression and return the result */
			output = CalculateExpression(&tempExp, type);

			/*Create a new queue element for output for expression*/
			Queue * newElement = GetNewQueueElement(output);
			/*Add that output to the queue*/
			PushToQueue(&Queue_Head, &Queue_Tail, newElement);

		}
	}

	/*free allocated memory*/
	free(Queue_Tail);
	free(Queue_Head);
	fclose(inputFile);
	fclose(outputFile);

return 0;

}

/*parameters: expression in double quotes "..." and the type(integer-Hex) of the numbers in expression*/
/*return a pointer to the string which contains the outputstring*/
char * CalculateExpression(char ** Expression, NumericType type){

	/*output */
	char * output = malloc(sizeof(char)*50);


	char * temp =  *Expression;
	int expLen = strlen(*Expression);

	temp[expLen] = '\0';


	/*chech the expression for wrong characters and balance of parenstheses*/
	if(isExpressionCharactersValid(temp) &&  isParenthesesBalanced(temp) ){
	}else{
		strncpy(output, "error", 50);
		return output;
	}

	/*Create stacks for operands and operators*/
	OperandStack * operands = NULL;
	OperatorStack * operators = NULL;

	/*ite on expression*/
	char * ite = temp;

	/*lasTokens purpose is the controling the flow of expression interpreting*/
	int lastToken = 0 ;/*0 means lastoken was operator , 1 means lastoken was operand, 2 means lastoken was left paren, 3 means lastoken was right paren*/

	/*continue interpreting till ite is '\0' --> enf of string */
	while(*ite != '\0'){

		if(lastToken == 0){/*lasToken was operator*/
			/*eigher the next token is going to be a number or left parentheses*/
			if(isNumeral(ite)){ /*is it Number ?*/ 
				/*if so , add it to the operand stack*/
				int Number = getNextNumber(&ite, type);
				PushToOperandStack(&operands, GetNewOperandStackElement(Number));
				lastToken = 1;
			}else if(isLeftParantheses(ite)){/*is it leftParentheses*/
				/*if so add it to the operator stack !*/
				PushToOperatorStack(&operators , GetNewOperatorStackElement('('));
				ite = ite + 1;
				lastToken = 2;
			}else if(!isNumeral(ite) && isOperandStackEmpty(operands)){/* this is for expression with start "-2..." */
				if(isOperator(ite)){// First operand is like -2 or +2
					int t = getNextOperator(&ite);
					if(t == 2){/* 2 means +*/
						int Number = getNextNumber(&ite, type);
						PushToOperandStack(&operands, GetNewOperandStackElement(Number));
						lastToken = 1;
					}else if(t == 3){/* 3 means -*/
						int Number = getNextNumber(&ite, type);
						Number = (-1)*Number;
						PushToOperandStack(&operands, GetNewOperandStackElement(Number));
						lastToken = 1;
					}else{
						freeStacks(operands, operators);
						strncpy(output, "error", 50); // First operand is like *5 or /5 --> not cool
						return output;
					}
				}
			}else{
				freeStacks(operands, operators);
				strncpy(output, "error", 50); /*next Token was something wrong the interprete*/
				return output;
			}
		}else if(lastToken == 1){/*last token was a operand */
			/*eighter next Token is going to be a right parentheses or an operator*/
			if(isOperator(ite)){/*is it operator */
				/*if so get the operator */
				int operator = getNextOperator(&ite);
				char op;
				if(operator == -1){/*operator token causes an error*/
					freeStacks(operands, operators);
					strncpy(output, "error", 50); /*next Token was something wrong the interprete*/
					return output;
				}else{
					if(operator == 0){/*means operator is * */
						op = '*';
					}else if(operator == 1){ /*means operator is / */
						op = '/';
					}else if(operator == 2){/*means operator is + */
						op = '+';
					}else if(operator == 3){/*means operator is - */
						op = '-';
					}
				}

				if(!isOperatorStackEmpty(operators)){/* check if operators stack is empty */
					/*if not check if we can add the operator */
					OperatorStack *  topElement = TopFromOperatorStack(&operators);


					char topOperator = topElement->Operator;


					if( topOperator == '*' || topOperator == '/'){/*we need to do calculations before we add the new operator*/
						PopFromOperatorStack(&operators);

						OperandStack * temp1 = PopFromOperandStack(&operands);
						OperandStack * temp2 = PopFromOperandStack(&operands);
						int last1Number = temp1->Operand;
						int last2Number = temp2->Operand;
						free(temp1);
						free(temp2);
							
						if(topOperator == '*'){
							PushToOperandStack(&operands, GetNewOperandStackElement(last1Number*last2Number));
						}else{
							if(last1Number == 0){
								freeStacks(operands, operators);
								strncpy(output, "error", 50); /*divition by zero ! causes an error*/
								return output;
							}
							PushToOperandStack(&operands, GetNewOperandStackElement(last2Number/last1Number));
						}
						free(topElement);
					}
				}
				/*after calculation or no calculations add the newoperator*/
				PushToOperatorStack(&operators, GetNewOperatorStackElement(op));
				lastToken = 0;


			}else if(isRightParantheses(ite)){
				/*if so we need to do calculations*/

				OperatorStack * topElement = PopFromOperatorStack(&operators);/*pop an operator from stack !*/
				while(topElement->Operator != '(' && topElement!=NULL){/*calculate till we see a left parentheses*/

					OperandStack * temp1 = PopFromOperandStack(&operands);
					OperandStack * temp2 = PopFromOperandStack(&operands);

					int Operand1 = temp1->Operand;
					int Operand2 = temp2->Operand;

					free(temp1);
					free(temp2);

					if(topElement->Operator == '+'){
						PushToOperandStack(&operands, GetNewOperandStackElement(Operand1+Operand2));
					}else if(topElement->Operator == '-'){
						PushToOperandStack(&operands, GetNewOperandStackElement(Operand2-Operand1));
					}else if(topElement->Operator == '*'){
						PushToOperandStack(&operands, GetNewOperandStackElement(Operand1*Operand2));
					}else if(topElement->Operator == '/'){
						if(Operand1 == 0){
							freeStacks(operands, operators);
							strncpy(output, "error", 50); /* divition by zero*/
							return output;
						}else{
							PushToOperandStack(&operands, GetNewOperandStackElement(Operand2/Operand1));
						}
					}
				}

				ite = ite +1;
				lastToken = 3;
			}else{
				freeStacks(operands, operators);
				strncpy(output, "error", 50); /*wrong Token*/
				return output;
			}

		}else if(lastToken == 2){/*lastToken was a left parentheses*/
			/*Only a number can be accepteble*/
			if(isNumeral(ite)){
				int Number = getNextNumber(&ite, type);
				PushToOperandStack(&operands, GetNewOperandStackElement(Number));
				lastToken = 1;
			}else{
				freeStacks(operands, operators);
				strncpy(output, "error", 50); /*wrong Token*/
				return output;;
			}

		}else if(lastToken == 3){/*Last Token was right paren*/
			/*Only a operator can be acceptable*/
			if(isOperator(ite)){
				int operator = getNextOperator(&ite);
				char op;
				if(operator == -1){/*operator token causes an error*/
					freeStacks(operands, operators);
					strncpy(output, "error", 50); /*next Token was something wrong the interprete*/
					return output;
				}else{
					if(operator == 0){/*means operator is * */
						op = '*';
					}else if(operator == 1){ /*means operator is / */
						op = '/';
					}else if(operator == 2){/*means operator is + */
						op = '+';
					}else if(operator == 3){/*means operator is - */
						op = '-';
					}
				}
				if(!isOperatorStackEmpty(operators)){/* check if operators stack is empty */
					/*if not check if we can add the operator */
					OperatorStack *  topElement = TopFromOperatorStack(&operators);


					char topOperator = topElement->Operator;


					if( topOperator == '*' || topOperator == '/'){/*we need to do calculations before we add the new operator*/
						PopFromOperatorStack(&operators);

						OperandStack * temp1 = PopFromOperandStack(&operands);
						OperandStack * temp2 = PopFromOperandStack(&operands);
						int last1Number = temp1->Operand;
						int last2Number = temp2->Operand;
						free(temp1);
						free(temp2);
							
						if(topOperator == '*'){
							PushToOperandStack(&operands, GetNewOperandStackElement(last1Number*last2Number));
						}else{
							if(last1Number == 0){
								freeStacks(operands, operators);
								strncpy(output, "error", 50); /*divition by zero ! causes an error*/
								return output;
							}
							PushToOperandStack(&operands, GetNewOperandStackElement(last2Number/last1Number));
						}
						free(topElement);
					}
				}
				PushToOperatorStack(&operators, GetNewOperatorStackElement(op));
				lastToken = 0;

			}else{
				freeStacks(operands, operators);
				strncpy(output, "error", 50); /*wrong Token*/
				return output;
			}
		}
	}

	/*Now interpreting the expression is over !
	We have to calculate what's left !*/

	if(!(lastToken == 0 || lastToken == 2)){/* lastToken must be a number or a rightparentheses */
		/*Calculations*/

		while(TopFromOperatorStack(&operators) != NULL){
			OperatorStack * tempOperator = PopFromOperatorStack(&operators);

			char ope = tempOperator->Operator;
			free(tempOperator);

			OperandStack * tempOperand1 = PopFromOperandStack(&operands);
			OperandStack * tempOperand2 = PopFromOperandStack(&operands);
				
			int Operand1 = tempOperand1->Operand;
			int Operand2 = tempOperand2->Operand;

			free(tempOperand1);
			free(tempOperand2);

			if(ope== '+'){
				PushToOperandStack(&operands, GetNewOperandStackElement(Operand1+Operand2));
			}else if(ope == '-'){
				PushToOperandStack(&operands, GetNewOperandStackElement(Operand2-Operand1));
			}else if(ope == '*'){
				PushToOperandStack(&operands, GetNewOperandStackElement(Operand1*Operand2));
			}else if(ope == '/'){
				if(Operand1 == 0){
						/*free stacks*/
					freeStacks(operands, operators);


					strncpy(output, "error", 50); /*divition with 0*/
					return output;
			}else{
				PushToOperandStack(&operands, GetNewOperandStackElement(Operand2/Operand1));
				}
			}


		}
	}else{
		freeStacks(operands, operators);
		strncpy(output, "error", 50); /*something went wrong with interpreting the expression */
		return output;
	}

	/*After the last calculations 
		Our answer is in the top of the operands stack !*/
	int out = operands->Operand;

	/*free stacks*/
	freeStacks(operands, operators);

	/*prepare the output string !*/
	sprintf(output, "%d" , out);
	if(type == integer){
		sprintf(output, "integer %d" , out);	
	}else{
		if(out<0){
		sprintf(output, "hex -%X", -out);	
		}else{
			sprintf(output, "hex %X", out);
		}
		
	}
	

	return output;
}


/* checks if the next character is an operator*/
int isOperator(char *p){
	if( *p == '*' || *p == '/' || *p== '+' || *p=='-' ){
		return 1;
	}else{
		return 0;
	}
}
/*interpretes the expression and return an integer according to the operator*/
/*ite on expression string passes by address , so no need to return it .*/
int getNextOperator(char ** ite){
	
	char op[5]="";
	int i=0;
	while(isOperator(*ite)){/*ite on expression string passes by*/
		op[i] = **ite;
		*ite = *ite +1;
		i++;
	}
	if(strlen(op)>2){
		return -1;
	}else if(strcmp(op, "*")==0){
		return 0;/*Multiplication*/
	}else if(strcmp(op, "++")==0 || strcmp(op, "--")==0 || strcmp(op, "+")==0){
		return 2;/*Addition*/
	}else if(strcmp(op, "+-")==0 || strcmp(op, "-+")==0 || strcmp(op, "-")==0){
		return 3;/*Substraction*/
	}else if(strcmp(op, "/")==0){
		return 1;/*Division*/
	}

return -1;
}

/*checks if the char is a parentheses*/
int isParentheses(char *p){
	if(*p == '(' || *p== ')'){
		return 1;
	}else{
		return 0;
	}
}
/*interpretes the expression and return an integer according to the number and the type of the number*/
/*ite on expression string passes by address , so no need to return it .*/
int getNextNumber(char ** ite, int type){

	char number[10]="";
	int i=0;
	while(isNumeral(*ite) && **ite != '\0'){/*keep going till number is over isNumeral ?*/
		number[i] = **ite;
		*ite = *ite +1;
		i++;
	}

	int dec_Number;

	if(type == 1){
		dec_Number = strtol(number, NULL, 10);/*string(dec) to dec*/
	}else{
		dec_Number = strtol(number, NULL, 16);/*string to hex, hex to dec.*/ 
	}
return dec_Number;
}

/*checks is the next token is a operand or not */
int isNumeral(char *p){
	if( *p != '(' && *p != ')' && *p != '*' && *p != '/' && *p!= '+' && *p!='-' ){
		return 1;
	}
	return 0;
}
/*checks the all expression for wrong character*/
int isExpressionCharactersValid(char *expr){
	unsigned char *ite = expr;
	while(*ite !='\0' && ite !=NULL ){
		if(*ite != '0' && *ite != '1' && *ite !='2' && *ite !='3' && *ite !='4' && *ite !='5' && *ite !='6' && *ite !='7'
			&& *ite !='8' && *ite !='9' && *ite !='A' && *ite !='B' && *ite !='C' && *ite !='D' && *ite !='E' && *ite !='F'
			&& *ite !='(' && *ite !=')' && *ite !='+' && *ite !='-' && *ite !='*' && *ite !='/'){
			return 0;
		}
		ite = ite+1;
	}
	return 1;
}

/*checks the all expression for balance of parentheses*/
int isParenthesesBalanced(char * expr){
	int lparanCounter = 0;
	int rparanCounter = 0;

	char * ite = expr;
	while(*ite != '\0'){
		if(isLeftParantheses(ite)){
			lparanCounter++;
		}
		if(isRightParantheses(ite)){
			rparanCounter++;
		}

		ite = ite + sizeof(char);
	}

	if(lparanCounter == rparanCounter){
		return 1;
	}else{
		return 0;
	}

}

/* is the char is a left parentheses*/
int isLeftParantheses(char *p) {
	if (*p == '('){
		return 1;
	}
	else{ 
		return 0;
	}
}

/* is the char is a right parentheses*/
int isRightParantheses(char *p) {
	if(*p == ')'){
		return 1;
	}else{
		return 0;
	}
}