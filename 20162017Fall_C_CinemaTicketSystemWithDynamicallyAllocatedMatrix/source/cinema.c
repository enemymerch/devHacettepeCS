#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

/*This is Struct for CinemaHall*/
struct CinemaHall{
    /* Hallname and Movie*/
    char  Movie[50];

    /*Information about sizes of hall*/
    int HallWidth;
    int HallHeight;
    char * Hall;
    char HallName[50];
    struct CinemaHall * next;
}typedef CinemaHall;

/*These are for seats in halls */
#define EMPTY_SEAT ' '
#define STUDENT_SEAT 's'
#define FULLFARE_SEAT 'f'


/*Function Definitions*/
CinemaHall * GetCinemaHallbyHallName(CinemaHall * HallList, char * HallName);
CinemaHall * GetCinemaHallbyMovieName(CinemaHall * HallList, char * MovieName);
CinemaHall * InsertHall(CinemaHall * HallList, CinemaHall * newHall);
CinemaHall * GetNewHall(int hallWidht, int hallHeight, char * movie, char * hallName);

int SizeOfToken(char * line ,int tokenNumber);
int SeatLabelToIndex(char * SeatLabel, int charSizeofSeatLabel, int hallWidht, int hallHeight);
int Power(int x, int power);
int StrCiCmp(char const *a, char const *b);
int  ReadLine(FILE * fp,char * buffer);
int IsSeatsEmpty(char * hall, int NumberOfTickets);

void DeallocateList(CinemaHall * List);


/*MAIN*/
int main(int argc,char ** argv){
    /* Create empty hallslist */
    CinemaHall * HallList = NULL;

    /* Line Buffer to Read a line form input file*/
    char  line[255];
    /*Open input/output files*/
    FILE * InputFp, *OutputFp;
    InputFp = fopen(argv[1], "r");
    OutputFp = fopen("output.txt", "w");
    /*Check if there's something wrong with files!*/
    if(InputFp==NULL || OutputFp==NULL){
        printf("Error opening .txt files\n");
        exit(1);
    }


    /*Read line at a time and process the command!*/
    while(ReadLine(InputFp, line)){
        /*First token of line is the Command*/
        char * Command = strtok(line, " ");


        /* charSizeofSeatLabel variable is because for BUYTICKET AND CALCELTICKET COMMAND
         * SeatLabels could be G4 which has size of 2 chars but it could be G13 too ,
         * which has size of 3. With pointer i cannot learn the size info
         * for interpreting the index for hall.*/
        int charSizeofSeatLabel= SizeOfToken(line, 2);


        /* COMMAND IS CREATEHALL*/
        if(!StrCiCmp(Command, "CREATEHALL")){
            /*Get the line's tokens*/
            char * HallName = strtok(NULL, " ");
            char * MovieName = strtok(NULL, " ");
            int HallWidth = atoi(strtok(NULL, " "));
            int HallHeight = atoi(strtok(NULL, " "));

            /*Get new hall variable which allocated from HEAP
             * and insert it to the HallList*/
            CinemaHall *newHall = GetNewHall(HallWidth, HallHeight, MovieName, HallName);
            HallList = InsertHall(HallList,newHall);

        /* COMMAND IS BUYTICKET*/
        }else if(!StrCiCmp(Command, "BUYTICKET")){
            /*Get the line's tokens*/
            char * MovieName = strtok(NULL, " ");
            char * SeatLabel = strtok(NULL, " ");
            char * TicketType = strtok(NULL, " ");
            int NumberOfTickets = atoi(strtok(NULL, " "));

            /* Getting the pointer for CinemaHall by MovieName*/
            CinemaHall * cHall = GetCinemaHallbyMovieName(HallList, MovieName);

            /*If cHall is NULL
             * There's no movie in the CinemaHalls that plays it */
            if(cHall == NULL){
                if(!StrCiCmp(MovieName, "\"\"")){
                    /*If moviename is empty from Command , like : ""  */
                    printf("ERROR: Movie name cannot be empty\n");
                    fprintf(OutputFp,"ERROR: Movie name cannot be empty\n");
                }else{
                    /*If given movieName is incorret like:London_hs_fallen*/
                    printf("ERROR: movie name %s is incorrect\n", MovieName);
                    fprintf(OutputFp,"ERROR: movie name %s is incorrect\n", MovieName);
                }
            }else{/*If there is such a cinemaHall with given MovieName*/

                /*Get rid of the \" which HallName and Movie has */
                char temp1[50];
                strncpy(temp1, cHall->HallName, 50);
                char * token1 ;
                token1 = strtok(temp1, "\"");

                char temp2[50];
                strncpy(temp2, cHall->Movie, 50);
                char * token2 = strtok(temp2, "\"");

                /*Get the index from SeatLabel for Hall(which seats are)*/
                int index = SeatLabelToIndex(SeatLabel, charSizeofSeatLabel, cHall->HallWidth, cHall->HallHeight);
                if(index <0){/*If index is under 0*/
                    /*There's no seat for that label*/
                    printf("ERROR: Seat %s is not defined at %s\n", SeatLabel, token1);
                    fprintf(OutputFp,"ERROR: Seat %s is not defined at %s\n", SeatLabel, token1);
                }else{
                    /*If index isn't under 0 , BUY THE TICKET*/
                    int j;

                    for(j = 1;j<=NumberOfTickets;j++,index++){/*For Number of Tıckets */

                        /*Iterator for seats*/
                        char * ite = cHall->Hall;
                        ite = ite+index;/*We found the needed seat.(index comes from SeatLabel of Command)*/
                        if(j==1 &&!IsSeatsEmpty(ite, NumberOfTickets)){/*If seats are not empty */
                            printf("ERROR: Specified seat(s) in %s are not available! They have been already taken.\n", token1);
                            fprintf(OutputFp,"ERROR: Specified seat(s) in %s are not available! They have been already taken.\n", token1);
                            break;
                        }
                        /*Seats are available*/
                        if(!StrCiCmp(TicketType, "STUDENT")){/*Occupy the seat according to the Type of Tıcket*/
                            *(ite) = STUDENT_SEAT;
                        }else{
                            *(ite) = FULLFARE_SEAT;
                        }

                        /*Output*/
                        if(j == 1){
                            fprintf(OutputFp, "%s [%s] Seat(s) ", token1, token2);
                            printf("%s [%s] Seat(s) ", token1, token2);
                        }

                        /*Output*/
                        fprintf(OutputFp, "%s", SeatLabel);
                        printf("%s", SeatLabel);
                        if(j<NumberOfTickets){
                            fprintf(OutputFp, ",");
                            printf(",");
                        }
                        *SeatLabel = (*SeatLabel)+1;
                        /*Output*/
                        if(j==NumberOfTickets){
                            fprintf(OutputFp, " successfully sold.\n");
                            printf(" successfully sold.\n");
                        }

                    }
                }

            }
        }else if(!StrCiCmp(Command, "CANCELTICKET")){/* COMMAND IS BUYTICKET*/
            /*Get tokens from command(input.txt-argv[1])*/
            char * MovieName = strtok(NULL, " ");
            char * SeatLabel = strtok(NULL, " ");
            /*Get Needed CinemaHall by MovieName*/
            CinemaHall * cHall = GetCinemaHallbyMovieName(HallList, MovieName);
            if(cHall == NULL){/*If chall is NULL means: there's something wrong with command*/
                if(!StrCiCmp(MovieName, "\"\"")){/*given movie name is "" */
                    printf("ERROR: Movie name cannot be empty\n");
                    fprintf(OutputFp,"ERROR: Movie name cannot be empty\n");
                }else{/*Given movieName is not correct*/
                    printf("ERROR: movie name %s is incorrect\n", MovieName);
                    fprintf(OutputFp,"ERROR: movie name %s is incorrect\n", MovieName);
                }
            }else{/*cHall is not empty*/
                /*Getting rid of the \"  double quotes. */
                char temp1[50];
                strncpy(temp1, cHall->HallName, 50);
                char * token1 ;
                token1 = strtok(temp1, "\"");

                char temp2[50];
                strncpy(temp2, cHall->Movie, 50);
                char * token2 = strtok(temp2, "\"");

                /*There's a newline character at the end of SeatLabel
                 * Getting rid of that too*/
                SeatLabel = strtok(SeatLabel, "\n");

                /*Getting index from SeatLabel , so i can reach the right seat*/
                int index = SeatLabelToIndex(SeatLabel, charSizeofSeatLabel, cHall->HallWidth, cHall->HallHeight);
                if(index <0){/*if index is under 0 : means there's a seat such SeatLabel*/
                    printf("ERROR: Seat %s is not defined at %s\n", SeatLabel, token1);
                    fprintf(OutputFp,"ERROR: Seat %s is not defined at %s\n", SeatLabel, token1);
                }else{/*index is not under 0 */
                    /*Create iterator for Hallseats*/
                    char * ite = cHall->Hall;
                    /*Reaching the right seat*/
                    ite = ite+index;
                    if(*ite != EMPTY_SEAT){/*Seat is already empty ?*/
                        *ite = EMPTY_SEAT;/*If not cancel the ticket !*/
                        printf("%s [%s] Purchase cancelled. Seat %s is now free.\n", token1, token2, SeatLabel);
                        fprintf(OutputFp, "%s [%s] Purchase cancelled. Seat %s is now free.\n", token1, token2, SeatLabel);
                    }else{/*Seat is already empty*/
                        printf("ERROR: Seat %s in Red-Hall was not sold.\n", SeatLabel);
                        fprintf(OutputFp,"ERROR: Seat %s in Red-Hall was not sold.\n", SeatLabel);
                    }
                }

            }
        }else if(!StrCiCmp(Command, "SHOWHALL")){/*Command is SHOWHALL*/
            /*Tokens from Command*/
            char * HallName = strtok(NULL, " ");
            HallName = strtok(HallName, "\n");
            /*Get the Right CinemaHall by HallName*/
            CinemaHall * cHall = GetCinemaHallbyHallName(HallList, HallName);

            /*Getting rid of the double quotes*/
            char temp[50];
            strncpy(temp, cHall->HallName, 50);
            char * token1 ;
            token1 = strtok(temp, "\"");

            /*Output*/
            printf("%s sitting plan\n", token1);
            fprintf(OutputFp, "%s sitting plan\n", token1);

            int i,j,z;
            /*Output*/
            for(z=0;z<((cHall->HallWidth)*2)+3;z++){
                fprintf(OutputFp, "-");
                printf("-");
            }
            /*Output*/
            fprintf(OutputFp, "\n");
            printf("\n");
            char * ite = cHall->Hall;
            for (i = 0; i<cHall->HallHeight;i++){
                fprintf(OutputFp, "%d|", (i+1));
                printf( "%d|", (i+1));
                for(j=0;j<cHall->HallWidth;j++){
                    fprintf(OutputFp, "|%c", *ite);
                    printf("|%c", *ite);
                    ite++;
                }
                fprintf(OutputFp, "|\n");
                printf("|\n");

                for(z=0;z<((cHall->HallWidth)*2)+3;z++){
                    fprintf(OutputFp, "-");
                    printf("-");
                }
                fprintf(OutputFp,"\n");
                printf("\n");
            }

            char tChar = 'A';
            for(z=0;z<cHall->HallWidth;z++, tChar++){
                fprintf(OutputFp," %c", tChar);
                printf(" %c", tChar);
            }
            fprintf(OutputFp,"\n");
            printf("\n");

            fprintf(OutputFp,"  C U R T A I N   \n");
            printf("  C U R T A I N   \n");
        }else if(!StrCiCmp(Command, "STATISTICS")){/*Command is STATISTICS*/

            /*Iterate through all CinemaHalls*/
            CinemaHall * HallListIterator = HallList;

            while(HallListIterator !=NULL){/*Keep looping till it is the end of the list*/
                /*Create char iterator seats */
                char * HallIterator = HallListIterator->Hall;
                /*Counters for Seat Numbers*/
                int StudentSeatsNumber = 0;
                int FullFareSeatsNumber = 0;
                int i;

                /*Search All seats */
                for(i = 0;i<(HallListIterator->HallWidth)*(HallListIterator->HallHeight);i++,HallIterator++){
                    if(*HallIterator!=EMPTY_SEAT){/*Seat is empty ?*/
                        /*NOT*/
                        if(*HallIterator == STUDENT_SEAT){/*Is it for a student ?*/
                            StudentSeatsNumber++;
                        }else{/* It is for a adult*/
                            FullFareSeatsNumber++;
                        }
                    }
                }
                /*Getting rid of the double quotes*/
                char temp[50];
                strncpy(temp, HallListIterator->HallName, 50);
                char * token1 ;
                token1 = strtok(temp, "\"");

                /*Calculate the Sum*/
                int sum=0;
                sum = (StudentSeatsNumber*7)+(FullFareSeatsNumber*10);

                /*Output*/
                fprintf(OutputFp,"%s %d student(s), %d full fare(s), sum:%d TL\n",token1,  StudentSeatsNumber, FullFareSeatsNumber, sum);
                printf("%s %d student(s), %d full fare(s), sum:%d TL\n",token1,  StudentSeatsNumber, FullFareSeatsNumber, sum);

                HallListIterator = HallListIterator->next;
            }
        }


    }
    DeallocateList(HallList);
    fclose(InputFp);
    fclose(OutputFp);
    return 0;
}

/* To get rid of the dangling pointer !
 * This function takes care of them*/
void DeallocateList(CinemaHall * List){
    CinemaHall * ite = List;
    while(ite !=NULL){
        /*iterate to next CinemaHall variable*/
        CinemaHall * temp = ite;
        ite = ite->next;

        /*Free Allocated space*/
        free(temp->HallName);
        free(temp->Movie);
        free(temp->Hall);
        free(temp);
    }
}

/*Check for seats
 * Are they empty of not */
int IsSeatsEmpty(char * hall, int NumberOfTickets){
    int i;
    char * ite = hall;
    for(i=1;i<=NumberOfTickets;i++,ite++){
        if(*ite != EMPTY_SEAT){
            return 0;/*They're not empty*/
        }
    }
    return 1;/*empty*/
}

/*Iterate all CinemaHalls and find the needed(byHallName) one and return a pointer to it*/
CinemaHall * GetCinemaHallbyHallName(CinemaHall * HallList, char * HallName){

    CinemaHall * iterator = HallList;
    while(iterator!=NULL){
        if(StrCiCmp(iterator->HallName, HallName) == 0){
            return iterator;
        }
        iterator = iterator->next;
    }
    return iterator;
}
/*Iterate all CinemaHalls and find the needed(byMovieName) one and return a pointer to it*/
CinemaHall * GetCinemaHallbyMovieName(CinemaHall * HallList, char * MovieName){
    CinemaHall * iterator = HallList;
    while(iterator != NULL){
        if(StrCiCmp(iterator->Movie, MovieName) == 0){
            return iterator;
        }
        iterator = iterator->next;
    }
    return iterator;
}

/*Returns the size of a token --> For SeatLabels*/
int SizeOfToken(char * line ,int tokenNumber){
    char * iterator = line;
    int counter = 0;
    int i;
    for(i = 0; i<tokenNumber;){
        if(*(iterator) != ' ' && *(iterator) != '\n'){
            counter++;
        }else{
            i++;
            if(i != tokenNumber){
                counter = 0;
            }
        }
        iterator++;
    }
    return counter;
}


/* Converting seat label to index
 * So , we can manipulate allocated memory */
int SeatLabelToIndex(char * SeatLabel, int charSizeofSeatLabel, int hallWidht, int hallHeight){

    char * iterator = SeatLabel;
    /* column is the first char of seat label*/
    char Column = *iterator;
    iterator++;

    int Row = 0;
    int digitNumber;

    for(digitNumber=charSizeofSeatLabel-1;digitNumber>0;digitNumber--,iterator++) {
        Row = Row + ((*iterator) -'0') * Power(10, digitNumber - 1);
    }

    if(Row>hallHeight){
        return -1;
    }
    int Colmn = ((toupper(Column))-'A')+1;
    if(Colmn>hallWidht){
        return -1;
    }
    return ((Row-1)*hallWidht)+(Colmn-1);
}

/* Returns x^power*/
int Power(int x, int power){
    int pow = x;
    while(power > 1){
        pow = pow*x;
        power--;
    }
    if(power  == 0){
        return 1;
    }
    return pow;
}

/*Insert a CinemaHall to the HallList*/
CinemaHall * InsertHall(CinemaHall * HallList, CinemaHall * newHall){
    /*Gonna insert the newHall to the head of the list */

    newHall->next = HallList;
    HallList = newHall;

    /*do not need to return the HallList but whatever :D*/
    return HallList;
}

/*Allocate enough  space for a new CinemaHall struct and return a pointer to it*/
CinemaHall * GetNewHall(int hallWidht, int hallHeight, char * movie, char * hallName){

    /*Create a new Hall struct*/
    CinemaHall* newHall = malloc(sizeof(CinemaHall));

    newHall->HallWidth = hallWidht;
    newHall->HallHeight = hallHeight;

    /*Allocate memory that needed for seats*/
    newHall->Hall = malloc(sizeof(char)*(hallHeight*hallWidht));
    /*Initialize all seats to empty*/
    char * iterator = newHall->Hall;
    int hallSize = hallHeight*hallWidht;
    int i;
    /*Empty all the tickets*/
    for(i=0;i<hallSize;i++){
        *(iterator+i) = EMPTY_SEAT;
    }

    /*Copy the needed information into the struct*/
    strncpy(newHall->Movie, movie, 50);
    strncpy(newHall->HallName, hallName, 50);

    newHall->next = NULL;
    return newHall;
}

/*Compares String Case insensitive*/
int StrCiCmp(char const *a, char const *b) {/*used  "const" , just in case*/
    for (;; a++, b++) {
        int d = tolower(*a) - tolower(*b);
        if (d != 0 || !*a || !*b)
            return d;
    }
}
/*Readline to buffer*/
int  ReadLine(FILE * fp,char * buffer){
    if(fgets(buffer, 255, fp)){
        return 1;
    }

    return 0;
}
