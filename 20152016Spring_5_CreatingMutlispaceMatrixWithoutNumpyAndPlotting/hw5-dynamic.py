import sys
import time


def Opt(Matrix , X , Y , currX, currY):
    M = len(X)
    N = len(Y)
    if currX is M or currY is N:
        return "-"
    elif currX is M and currY is N:
        return ""
    elif X[currX] is Y[currY]:# X[i] matched with Y[j]
        return Y[currY] + Opt(Matrix , X , Y ,currX + 1, currY + 1)# Going for Opt(x[i+1],y[j+1])
    elif Matrix[currX+1][currY+1] + 1 <= Matrix[currX+1][currY] + 2 and Matrix[currX+1][currY+1]+1 <= Matrix[currX][currY+1] + 2:# X[i] did not matched with Y[j]
        return Y[currY] + Opt(Matrix , X , Y , currX +1 , currY + 1)#Going for Opt(x[i+1],y[j+1])
    elif Matrix[currX+1][currY] + 2 <= Matrix[currX][currY+1] + 2:#X[i] matched with gap
        return "-" + Opt(Matrix , X , Y , currX+1 , currY)# Going for  Opt(x[i+1],y[j])# Going for Opt(X[i+1],Y[j])
    else:# Y[j] matched with gap
        return "-" + Opt(Matrix , X , Y , currX , currY + 1) #Going for Opt(X[i], Y[j+1])

def complete_Matrix(Matrix , X , Y):
    M = len(X)
    N = len(Y)

    i = M - 1
    while(i >- 1):
        j = N - 1
        while(j > -1):
            if Matrix[i][j] is 0 :
                if X[i] is Y[j]:
                    Matrix[i][j] = Matrix[i+1][j+1]
                elif Matrix[i+1][j+1] + 1 < Matrix[i+1][j] + 2 and Matrix[i+1][j+1] + 1 < Matrix[i][j+1] + 2 :
                    Matrix[i][j] = Matrix[i+1][j+1] + 1
                elif Matrix[i+1][j] + 2 <= Matrix[i][j+1] + 2:
                    Matrix[i][j] = Matrix[i+1][j] + 2
                else:
                    Matrix[i][j] = Matrix[i][j+1] + 2
            else:
                print("Did not compeleted the Matrix")
                return Matrix
            j -= 1
        i -= 1
    return Matrix
def createCompleteMatrix(X,Y):
    M = len(X)
    N = len(Y)
    Matrix = [[0 for i in range(N + 1)] for i in range(0,M + 1)]

    i = M - 1
    while(i >- 1):
        j = N - 1
        while(j > -1):
            if Matrix[i][j] is 0 :
                if X[i] is Y[j]:
                    Matrix[i][j] = Matrix[i+1][j+1]
                elif Matrix[i+1][j+1] + 1 < Matrix[i+1][j] + 2 and Matrix[i+1][j+1] + 1 < Matrix[i][j+1] + 2 :
                    Matrix[i][j] = Matrix[i+1][j+1] + 1
                elif Matrix[i+1][j] + 2 <= Matrix[i][j+1] + 2:
                    Matrix[i][j] = Matrix[i+1][j] + 2
                else:
                    Matrix[i][j] = Matrix[i][j+1] + 2
            else:
                print("Did not compeleted the Matrix")
                return Matrix
            j -= 1
        i -= 1
    return Matrix

    for j in range(N + 1):
        Matrix[M][j] = 2 * (N - j)
    for i in range(M + 1):
        Matrix[i][N] = 2 * (M - i)
    return Matrix

def first_inil(X , Y):
    M = len(X)
    N = len(Y)
    Matrix = [[0 for i in range(N + 1)] for i in range(0,M + 1)]


    for j in range(N + 1):
        Matrix[M][j] = 2 * (N - j)
    for i in range(M + 1):
        Matrix[i][N] = 2 * (M - i)
    return Matrix


def readInput(input_dest):

    text_file = open(input_dest , "r")
    str  = text_file.read()
    str_l  = str.split("\n")
    text_file.close()
    return str_l

def writeOutput(Cost,X,Y,OptString,output_Des,temp):

    text_file = open(output_Des , 'w')
    text = "Edit Distance : " +  str(Cost)+ "\n"
    text_file.write(text)
    M = len(X)
    N = len(OptString)
    if M - N is not 0 :
        for i in range(M-N):
            OptString = OptString + "-"

    if temp is 1:
        for i in range(len(X)):
            if X[i] is OptString[i]:
                text = OptString[i] + " "+ X[i] + " 0\n"
            elif OptString[i] == "-":
                text = OptString[i] + " " + X[i] + " 2\n"
            else:
                text = OptString[i] + " " + X[i] + " 1\n"
            text_file.write(text)
    else:
        for i in range(len(X)):
            if X[i] is OptString[i]:
                text = X[i] + " " + OptString[i] + " 0\n"
            elif OptString[i] == "-":
                text = X[i] + " " + OptString[i] + " 2\n"
            else:
                text = X[i] + " " + OptString[i] + " 1\n"
            text_file.write(text)

    text_file.close()


start = time.time()
input_dest = sys.argv[1]
output_dest = sys.argv[2]

input_strings = readInput(input_dest)

temp =  0
if len(input_strings[0]) >= len(input_strings[1]):
    StringX = input_strings[0]
    StringY = input_strings[1]
    temp = 0
else:
    StringX = input_strings[1]
    StringY = input_strings[0]
    temp = 1
Matrix = first_inil(StringX , StringY)# Creating first matrix with basic values
Matrix = complete_Matrix(Matrix , StringX , StringY)#Filling all matrix with cost values
OptString = Opt(Matrix,StringX,StringY,0,0)#Finding Optimized String
writeOutput(Matrix[0][0], StringX, StringY, OptString, output_dest, temp)#Writing Output

end = time.time()

elapsed_time = end - start
print(elapsed_time)