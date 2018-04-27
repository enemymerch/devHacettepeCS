import sys
import time


def Recursive(Matrix , X , Y ,x , y):
    M = len(X)
    N = len(Y)
    if (x is M or y is N) :
        return ""
    else:
        if X[x] is Y[y]:
            return Y[y] + Recursive(Matrix, X, Y, x+1, y+1)
        else:
            east = val(Matrix, X, Y, x, y+1)
            south = val(Matrix, X, Y, x+1, y)
            southern_east = val(Matrix, X, Y, x+1, y+1)
            if southern_east + 1 <= south + 2 and southern_east + 1 <= east + 2 :
                return Y[y] + Recursive(Matrix, X, Y, x+1, y+1)
            elif south + 2 <= east + 2:
                return "-" + Recursive(Matrix,X,Y,x+1,y)
            else:
                return "-" + Recursive(Matrix,X,Y,x,y+1)

def val(Matrix, X, Y, x, y):
    M = len(X)
    N = len(Y)

    if x is M:
        return Matrix[x][y]
    elif y is N:
        return Matrix[x][y]
    else:
        east = val(Matrix, X, Y, x, y+1)
        south = val(Matrix, X, Y, x+1, y)
        southern_east = val(Matrix,X,Y,x+1,y+1)
        if X[x] is Y[y]:
            return southern_east
        elif southern_east + 1 <= south + 2 and southern_east + 1 <= east + 2 :
            return southern_east + 1
        elif south + 2 <= east + 2:
            return south+2
        else:
            return east+2

def first_inil(X , Y):
    M = len(X)
    N = len(Y)
    Matrix = [[0 for i in range(N + 1)] for i in range(0,M + 1)]


    for j in range(N + 1):
        Matrix[M][j] = 2 * (N - j)
    for i in range(M + 1):
        Matrix[i][N] = 2 * (M - i)
    return Matrix

def writeOutput(Cost,X,Y,OptString,output_Des,temp):

    text_file = open(output_Des , "w")
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

def readInput(input_dest):

    text_file = open(input_dest , "r")
    str  = text_file.read()
    str_list  = str.split("\n")
    text_file.close()
    return str_list


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


Matrix = first_inil(StringX , StringY)
OptString  = Recursive(Matrix, StringX, StringY, 0, 0)
Cost = val(Matrix,StringX,StringY,0,0)
writeOutput(Cost, StringX, StringY, OptString, output_dest, temp)
end = time.time()

elapsed_time = end - start
print(elapsed_time)