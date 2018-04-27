square = "S"
rectangle = "R"
triangle = "T"
cont = "A"
i = 0
j = 0
while(cont is not "E"):
  commands=raw_input('').split()
  if commands[0] is square :
    row = int(commands[1])
    column = row
    for i in range(0,row):
      i = i+1
      for j in range(0,column):
        print "*",
        j = j+1
      print ""
  if commands[0] is rectangle :
    row = int(commands[1])
    column = int(commands[2])
    for i in range(0,row):
      for j in range(0,column):
        print "*",
        j = j+1
      print ""
      i = i+1
  if commands[0] is triangle :
    row = int(commands[2])
    column = 1
    for i in range(0,row):
      for j in range(0,column):
        print "*",
        j = j+1
      print ""
      i = i+1
      column = column + 1
  cont = raw_input('')
