//
// Created by MCan on 10.12.2016.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


typedef enum CellStatus{
    Free = 0,
    Processing = 1
}Status;

typedef enum CarStatus{
    NotStarted = -1,
    InProces = 0,
    Manifactured = 1
}CarStatus;

typedef enum ReportCommandType{
    IndividualCar = 1,
    AllCars =2,
    Departments=3
}ReportType;

/*For keeping the Reports in a list*/
typedef struct Report{
    enum ReportCommandType ReportType;
    int T;
    char Info[10];
    int exec;

    struct Report * nextReport;
}Report;

/*Deparment cell example:(Cell1 = Frame1)*/
typedef struct DepartmentCell{
    /*Cell Information*/
    int CellNumber;
    enum CellStatus sta;

    /*processing Info*/
    struct Car * processingCar;
    int CarEnterenceTime;

    /*Keeping Log of cell*/
    struct CarList  * ProccesedCars;


    /*For list structure*/
    struct DepartmentCell * nextCell;
}Cell;

/*For creating a list of cars.This struct is used for , allocating the cars and keeping them in a list*/
typedef struct Car{
    /*Car ınfo*/
    char Model[50];
    char Code[10];
    CarStatus sta;
    int ProcudeT;
    int StartT;

    struct Car * nextCar;
}Car;
/*This struct is for WaitingQueue and ProcessedCarList , basically list of pointers to car*/
typedef struct CarList{
    struct Car * car;

    struct CarList * next;
}CarList;

/*Department -- Factory*/
typedef struct Department{
    /*Department Part*/
    struct Department * nextDepartment;
    int T ;
    char DepName[50];

    /*CellList part*/
    struct DepartmentCell * CellList;


    /*Waiting Queue Part*/
    struct CarList * WaitingQueue;
}Department;

/*Functions*/
void MakeReport(Department ** Factory, int FactoryTime,Car ** Cars, ReportType type, char * Info, int TotalProcessTime);
void printDepartmentLog(Department * dep);
Car * GetCarByCode(Car * carList, char * Code);
Report * GetReport(Report ** Reports, int FactoryTime);
void TakeCarsFromWaitingQueues(Department ** Factory, int  FactoryTime);
void RunDepartments(Department ** Factory, int FactoryTime);
void StartProducingTheCar(Department ** Factory, Car * nextCar);
Car * PopFromCarList(CarList ** cList);
void Push2CarList(CarList ** WaitingQueue, Car * nextCar);
Car *GetCar2Produce(Car ** CarList, int FactoryTime);
int isEveryCarComplete(Car * CarList);
void AddReport(Report ** ReportList, int T, ReportType type, char * Info);
void AddNewCar2List(Car ** CarList, int T, char * Model, char * Code);
void AddDept(Department ** Factory, int T, char * DepartmentName, int TotalCellNumber);
int StrCiCmp(char const *a, char const *b);
void PrintFactory(Department * Factory);
void freeLists(Department ** Factory, Car ** CarList, Report ** ReportList);
int isCarListEmpty(CarList * cList);
int isDeparmtnetFull(Department * dep);
int isCarInThisDepartment(Department * dep, Car * car);
int GetPassedTime(Department * dep, Car * car, int FactoryTime);

int main (int argc, char ** argv){

    /*Going to read all lines from file , and allocate space to hold lines*/
    FILE * inputFile = NULL;
    inputFile = fopen(argv[1], "r");

    if(inputFile==NULL){
        printf("File couldn't be opened.\n");
        return 1;
    }

    /*max line number :200*/
    int lineNumber = 0;
    char * lines[200];
    int j =0;
    for(j=0;j<200;j++){/*Initialize all line pointers to NULL*/
        lines[j] = NULL;
    }


    lines[lineNumber] = malloc(sizeof(char)*255);
    while(fgets(lines[lineNumber], 255, inputFile) !=NULL && lineNumber < 100){
        lineNumber++;
        lines[lineNumber] = malloc(sizeof(char)*255);
    }
    free(lines[lineNumber]);
    /*Going to substrack 1 because before adding the line, i increased lineNumber 1*/
    lineNumber--;


    /*Our Factory*/
    Department * Factory = NULL;

    /*Now , going to operate all the addDep Commands*/
    int TotalProcessTime = 0;
    for(j=0;j<lineNumber;j++){
        char temp[255];
        if(lines[j] != NULL){
            strncpy(temp, lines[j], 255);

            char * Command = strtok(temp, " \n");
            if(!StrCiCmp(Command, "adddept")){
                int CellNumber = atoi(strtok(NULL, " \n"));
                char * DeptName = strtok(NULL, " \n");
                int T = atoi(strtok(NULL, " \n"));
                TotalProcessTime = TotalProcessTime + T;

                AddDept(&Factory, T, DeptName, CellNumber);
                lines[j] = NULL;
            }
        }

    }
    /*Now, we're going to execute printFactory Commands*/
    for(j=0;j<lineNumber;j++){
        char temp[255];
        if(lines[j] != NULL){
            strncpy(temp, lines[j], 255);

            char * Command = strtok(temp, " \n");
            if(!StrCiCmp(Command, "printfactory")){
                PrintFactory(Factory);
                lines[j] = NULL;
            }
        }
    }

    /*Now, going to Create all cars !*/
    Car * Cars = NULL;
    /*Going to read all Cars */
    for(j=0;j<lineNumber;j++){
        char temp[255];
        if(lines[j] != NULL){
            strncpy(temp, lines[j], 255);

            char * Command = strtok(temp, " \n");
            if(!StrCiCmp(Command, "Produce")){
                int T = atoi(strtok(NULL, " \n"));
                char * CarModel = strtok(NULL, " \n");
                char * CarCode = strtok(NULL, " \n");

                /*Creating new Car*/
                AddNewCar2List(&Cars, T, CarModel, CarCode);
                lines[j] = NULL;
            }

        }
    }
    /*Now going to create a list of report commands*/
    Report * Reports = NULL;
    for(j=0;j<lineNumber;j++){
        if(lines[j] != NULL){
            char temp[255];
            strncpy(temp, lines[j], 255);
            char * Command = strtok(temp, " \n");

            if(!StrCiCmp(Command, "report")){
                char * Info = "none";
                ReportType type ;
                int T = atoi(strtok(NULL, " \n"));

                char * token = strtok(NULL, " \n");
                if(!StrCiCmp(token, "car")){
                    type = IndividualCar;
                    Info = strtok(NULL, " \n");
                }else if(!StrCiCmp(token, "cars")){
                    type = AllCars;
                }else{
                    type = Departments;
                }

                AddReport(&Reports, T, type, Info);
                lines[j] =  NULL;
            }
        }
    }


    /*Start Factory*/
    int Time = 0;
    while(!isEveryCarComplete(Cars)){/*Finish after every car is manufactured*/
        Time++;
        /* Check For Cars to Produce!*/
        Car * nextCar = GetCar2Produce(&Cars, Time);
        while(nextCar != NULL){
            /*Start Producing the Cars*/
            StartProducingTheCar(&Factory, nextCar);
            nextCar = GetCar2Produce(&Cars, Time);
        }
        /*Update Factory!*/
        RunDepartments(&Factory, Time);
        TakeCarsFromWaitingQueues(&Factory, Time);

        /*Check For Reports to report!*/
        Report * nextReport = GetReport(&Reports, Time);
        while(nextReport != NULL){
            nextReport->exec = 1;
            if(nextReport->ReportType == IndividualCar){
                printf("Command: Report Car %d %s\n",nextReport->T, nextReport->Info);
                MakeReport(&Factory, Time, &Cars, nextReport->ReportType, nextReport->Info, TotalProcessTime);
            }else if(nextReport->ReportType == AllCars){
                printf("Command: Report Car %d\n",nextReport->T);
                Car * iteCars = Cars;
                while(iteCars != NULL){
                    MakeReport(&Factory, Time, &Cars, nextReport->ReportType, iteCars->Code, TotalProcessTime);
                    iteCars = iteCars->nextCar;
                }
            }else{
                printf("Command: Report Departments %d\n", nextReport->T);
                MakeReport(&Factory, Time, &Cars, nextReport->ReportType, nextReport->Info, TotalProcessTime);
            }
            nextReport = GetReport(&Reports, Time);
        }
    }

    fclose(inputFile);
    freeLists(&Factory, &Cars, &Reports);
    return 0;
}

/*For Printing the Report Command--> Little complicated, hard to read. */
void MakeReport(Department ** Factory, int FactoryTime,Car ** Cars, ReportType type, char * Info, int TotalProcessTime){
    if(type != Departments){

        /*Going to find the car !*/
        Car * tCar = GetCarByCode(*Cars, Info);
        int t = 13 + strlen(tCar->Model)+strlen(tCar->Code);
        int j;
        for(j=0;j<t;j++){
            printf("-");
        }
        printf("\n");
        printf("|Report for %s %s|\n", tCar->Model, tCar->Code);
        for(j=0;j<t;j++){
            printf("-");
        }
        printf("\n");
        if(tCar->sta == NotStarted){
            Department * ite = *Factory;
            while(ite!=NULL){
                printf("%s:0, ",ite->DepName);
                ite = ite->nextDepartment;
            }
            printf("| Start Time%d | Complete:0.0%% | Not complete\n", tCar->ProcudeT);
        }else if(tCar->sta == InProces){
            Department * ite = *Factory;
            int totalProcess=0;
            int carFound = 0;
            while(ite!=NULL){
                printf("|%s:", ite->DepName);
                if(carFound==1){/*means car already found before !*/
                    printf("0,");
                }else{
                    int temp1 = isCarInThisDepartment(ite, tCar);
                    if(temp1!=0){
                        carFound = 1;
                        if(temp1 == 1){
                            /*No need to calculate passed time , car is in the waiting queue*/
                            printf("0, ");
                        }else{/*Means temp1 = 2 , means car is in the process*/
                            int temp2 = GetPassedTime(ite, tCar, FactoryTime);
                            totalProcess = totalProcess + temp2;
                            printf("%d, ", temp2);
                        }
                    }else{
                        totalProcess = totalProcess + ite->T;
                        printf("%d, ", ite->T);
                    }
                }
                ite = ite->nextDepartment;
            }
            float comp = ((float)(totalProcess)/(float)(TotalProcessTime))*100;
            printf("| Start Time%d | Complete:%.2f%% | Not complete\n", tCar->StartT, comp);
        }else{/*Car is completed*/
            Department * ite = *Factory;
            while(ite!=NULL){
                printf("%s:%d, ", ite->DepName, ite->T);
                ite = ite->nextDepartment;
            }
            printf("| Start Time%d | Complete:100.00%% | Complete\n", tCar->StartT);
        }
    }else{/*Departments*/
        Department * ite = *Factory;
        printDepartmentLog(ite);
    }
}

/*This is a recursive function for also for report command.*/
void printDepartmentLog(Department * dep){

    if (dep->nextDepartment != NULL){
        printDepartmentLog(dep->nextDepartment);
    }

    int t = 28+ strlen(dep->DepName);

    Cell *iteCell = dep->CellList;
    while(iteCell!=NULL){
        int i;
        for(i=0;i<t;i++){
            printf("-");
        }
        printf("\n");

        printf("|Report for Department \"%s %d\"|\n", dep->DepName, iteCell->CellNumber);
        for(i=0;i<t;i++){
            printf("-");
        }
        printf("\n");
        if(iteCell->sta == Free){
            printf("%s %d is now free.\n", dep->DepName, iteCell->CellNumber);
        }else{
            printf("I am currently processing %s %s\n", (iteCell->processingCar)->Model , (iteCell->processingCar)->Code);
        }

        if(iteCell->ProccesedCars!= NULL){
            printf("Processed Cars\n");
        }
        CarList * iteC = iteCell->ProccesedCars;
        int j = 1;
        while(iteC != NULL){
            printf("%d. ", j);
            printf("%s %s\n", (iteC->car)->Model, (iteC->car)->Code);
            iteC = iteC->next;
            j++;
        }


        iteCell = iteCell->nextCell;
    }
    return;
}
/* For report command
* Calculates the passed time for car , from the time to now(only for one department)*/
int GetPassedTime(Department * dep, Car * car, int FactoryTime){
    /*Going to find the car in dep*/
    Cell * iteCell = dep->CellList;
    while(iteCell!=NULL){
        Car * temp = iteCell->processingCar;
        if( iteCell->sta == Processing && !StrCiCmp(car->Code, temp->Code) ){
            return FactoryTime-iteCell->CarEnterenceTime;
        }
        iteCell = iteCell->nextCell;
    }
    return 0;
}
/* If car in this departments waiting queue return 1, if it's in manufacturing in this dep, return 2.
* Not in this department return0*/
int isCarInThisDepartment(Department * dep, Car * car){
    /*First , going chechk if this car in this dep's waiting queue*/
    CarList * iteWait = dep->WaitingQueue;
    while(iteWait != NULL){
        Car * temp = iteWait->car;
        if(!StrCiCmp(car->Code, temp->Code)){
            return 1;
        }
        iteWait = iteWait->next;
    }
    /* Car not found on the waiting queue
     * Now , going to check the cells*/
    Cell * iteCell = dep->CellList;
    while(iteCell!=NULL){
        Car * temp = iteCell->processingCar;
        if( iteCell->sta == Processing && !StrCiCmp(car->Code, temp->Code) ){
            return 2;
        }
        iteCell = iteCell->nextCell;
    }

    return 0;
}
/*Return a pointer to car , searches car according to the "CODE" of a car */
Car * GetCarByCode(Car * carList, char * Code){
    Car * ite = carList;
    while(ite != NULL){
        int temp = strncmp(ite->Code,Code, strlen(ite->Code));
        if(temp == 0){
            return ite;
        }
        ite = ite->nextCar;
    }
    return NULL;
}
/* If there's a report command in this Time , return the report*/
Report * GetReport(Report ** Reports, int FactoryTime){
    Report * ite = *Reports;
    while(ite != NULL ){
        if(ite->T == FactoryTime && ite->exec ==0){
            return ite;
        }
        ite = ite->nextReport;
    }
    return NULL;
}
/* For every department , takes cars from waititngqueues to manufacturing till waititng queues are empty or dep is full*/
void TakeCarsFromWaitingQueues(Department ** Factory, int  FactoryTime){
    Department * ite = *Factory;
    int i = 0;
    while(ite != NULL){
        while(!isCarListEmpty(ite->WaitingQueue) && !isDeparmtnetFull(ite)){
            Cell * iteCell = ite->CellList;
            /*we are going to find a cell that is free*/
            while(iteCell != NULL){
                if(iteCell->sta!=Processing){
                    break;
                }
                iteCell = iteCell->nextCell;
            }
            /*Now we're going to occupy this cell */
            iteCell->sta = Processing;
            iteCell->processingCar = PopFromCarList(&ite->WaitingQueue);
            if(i == 0){
                (iteCell->processingCar)->StartT = FactoryTime;
            }
            iteCell->CarEnterenceTime = FactoryTime;
        }
        ite = ite->nextDepartment;
        i++;
    }
}
/* Update the factory according to the Factory Time
*  If there's a car that  have been complete , transfer the car to next department*/
void RunDepartments(Department ** Factory, int FactoryTime){
    Department * ite = *Factory;
    while(ite!=NULL){
        Cell * iteCell = ite->CellList;
        while(iteCell!=NULL){
            if(iteCell->sta == Processing && iteCell->processingCar!=NULL){
                int t = FactoryTime - iteCell->CarEnterenceTime;
                if(t == ite->T){
                    /* Procces of this car in this department's cell is over.
                     * Going to send this car to next department*/
                    /* Before sending the car ,
                     * we need to add this car to the proccesedCars of this cell*/
                    Push2CarList(&(iteCell->ProccesedCars), iteCell->processingCar);

                    /* Send this car to the next department.*/
                    if(ite->nextDepartment != NULL){
                        Department * nextDeparment = ite->nextDepartment;
                        Push2CarList(&(nextDeparment->WaitingQueue), iteCell->processingCar);
                    }else{
                        /* This means the procces of this car is over , COMPLETE!
                         * Not going to push to nextDeparment*/
                        Car * CompleteCar = iteCell->processingCar;
                        CompleteCar->sta = Manifactured;

                    }
                    /* Now, going to free the cell */
                    iteCell->sta = Free;
                    iteCell->processingCar = NULL;
                }
            }
            iteCell = iteCell->nextCell;
        }
        ite = ite->nextDepartment;
    }
}


void StartProducingTheCar(Department ** Factory, Car * nextCar){
    /*We're going to add nextCar the waiting queue to the factory's first department's waiting queue*/
    Department * ite = *Factory;

    Push2CarList(&(ite->WaitingQueue), nextCar);

}

int isCarListEmpty(CarList * cList){
    if(cList == NULL){
        return 1;
    }
    return 0;
}

int isDeparmtnetFull(Department * dep){
    Cell * ite = dep->CellList;
    while(ite!=NULL){
        if(ite->sta == Free){
            return 0;
        }
        ite = ite->nextCell;
    }
    return 1;
}

Car * PopFromCarList(CarList ** cList){
    if(*cList != NULL){
        Car * temp = (*cList)->car;
        (*cList) = (*cList)->next;
        return temp;
    }
    return NULL;
}

void Push2CarList(CarList ** cList, Car * nextCar){
    CarList * newListElement = (CarList*)malloc(sizeof(CarList));

    newListElement->car = nextCar;
    newListElement->next = NULL;

    if(*cList == NULL){
        *cList = newListElement;
    }else{
        CarList * ite = *cList;
        while(ite->next !=NULL){
            ite = ite->next;
        }
        ite->next = newListElement;
    }

}

Car * GetCar2Produce(Car ** CarList, int FactoryTime){
    Car * ite = *CarList;
    while(ite != NULL){
        if(ite->ProcudeT == FactoryTime && ite->sta== NotStarted){
            ite->sta = InProces;
            return ite;
        }
        ite = ite->nextCar;
    }
    return NULL;
}

int isEveryCarComplete(Car * CarList){
    Car * ite = CarList;
    while(ite != NULL){
        if(ite->sta != Manifactured){
            return 0;
        }
        ite = ite->nextCar;
    }
    return 1;
}





void AddReport(Report ** ReportList, int T, ReportType type, char * Info){
    Report * newReport = (Report*)malloc(sizeof(Report));

    newReport->T = T;
    newReport->nextReport = NULL;
    strncpy(newReport->Info, Info, 10);
    newReport->ReportType = type;
    newReport->exec = 0;

    if(*ReportList==NULL){
        *ReportList = newReport;
    }else{
        Report * ite = *ReportList;
        while(ite->nextReport != NULL){
            ite = ite->nextReport;
        }
        ite->nextReport = newReport;
    }
}

void AddNewCar2List(Car ** CarList, int T, char * Model, char * Code){
    Car * newCar = (Car*)malloc(sizeof(Car));

    /*Initialize the new Car*/
    newCar->sta = NotStarted;
    strncpy(newCar->Code, Code, 10);
    strncpy(newCar->Model, Model, 50);
    newCar->ProcudeT = T;
    newCar->StartT = 0;
    newCar->nextCar = NULL;

    /*Adding Car to the list*/
    if(*CarList == NULL){
        *CarList = newCar;
    }else{
        Car * ite = *CarList;
        while(ite->nextCar != NULL){
            ite = ite->nextCar;
        }
        ite->nextCar = newCar;

    }
}

void AddDept(Department ** Factory, int T, char * DepartmentName, int TotalCellNumber){
    /*Creating new Department !!*/
    Department * newDepartment = (Department*)malloc(sizeof(Department));

    /*Initiailize newDepartent*/
    strncpy(newDepartment->DepName, DepartmentName, 50);
    newDepartment->T = T;
    newDepartment->nextDepartment = NULL;


    /*Creating Cell for Department*/
    newDepartment->CellList = NULL;
    int i = 1 ;
    while(i <= TotalCellNumber){
        /*Creaitng NewCell*/
        Cell * newCell = (Cell*)malloc(sizeof(Cell));

        /*Initializing newCell*/
        newCell->CellNumber = i;
        newCell->processingCar = NULL;
        newCell->sta = Free;
        newCell->ProccesedCars = NULL;
        newCell->nextCell=NULL;
        newCell->CarEnterenceTime = 0;

        /*Adding newCell to the newDepartments->CellList*/
        if(newDepartment->CellList == NULL){
            newDepartment->CellList = newCell;
        }else{
            /*iterate through the end of the list !*/
            Cell * ite = newDepartment->CellList;
            while(ite->nextCell != NULL){
                ite = ite->nextCell;
            }
            /*Now we're at the end of the list , we can add the new Cell to the list*/
            ite->nextCell = newCell;
        }


        i++;
    }
    /*Initializing WaitingQueue to NULL for newDepartment*/
    newDepartment->WaitingQueue = NULL;

    /* Now we have our newDepartment Initialized
     * Going to add this department to the factory */

    /*Adding newDepartment to the factory*/
    if(*Factory == NULL){
        *Factory = newDepartment;
    }else{
        Department * ite = *Factory;
        while(ite->nextDepartment != NULL){
            ite = ite->nextDepartment;
        }
        ite->nextDepartment = newDepartment;
    }

    /* Now we added the new department
     * Work is Done !*/
}
/*Compares String Case insensitive*/
int StrCiCmp(char const *a, char const *b) {/*used  "const" , just in case*/
    for (;; a++, b++) {
        int d = tolower(*a) - tolower(*b);
        if (d != 0 || !*a || !*b)
            return d;
    }
}

void PrintFactory(Department * Factory){
    Department * ite = Factory;
    int temp = 0;
    int i = 0;
    while(ite != NULL){
        i++;

        /*Now we have to iterate through the cell list*/
        int j = 0;
        while(j < temp){
            printf(" ");
            j++;
        }
        printf("-");
        Cell * ite2 = ite->CellList;
        while(ite2 !=NULL){
            temp = temp + strlen(ite->DepName)+2;
            printf("%s%d", ite->DepName, ite2->CellNumber);
            printf(" ");
            ite2 = ite2->nextCell;
        }
        printf("\n");

        ite = ite->nextDepartment;
    }
}

void freeLists(Department ** Factory, Car ** CarList, Report ** ReportList){
    /*Free Departments*/
    Department * ite = *Factory;
    while(ite != NULL){
        /*Free the Cells*/
        Cell * ite2  = ite->CellList;
        while(ite2!=NULL){
            Cell * temp = ite2;
            ite2  = ite2->nextCell;
            free(temp);
        }
        Department * temp = ite;
        ite = ite->nextDepartment;
        free(temp);
    }


    /*Free Cars*/
    Car * carIte = *CarList;
    while(carIte != NULL){
        Car * temp = carIte;
        carIte = carIte->nextCar;
        free(temp);
    }

    /*Free Reports*/
    Report * repIte = *ReportList;
    while(repIte != NULL){
        Report * temp = repIte;
        repIte = repIte->nextReport;
        free(temp);
    }
}