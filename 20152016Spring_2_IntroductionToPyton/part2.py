cont = "A"
while(cont is not "E"):
  print "1.Prime numbers' calculation(n) \n2.Combination of two numbers(n,t)"
  ch = raw_input('What is your choice ?\t')
  if ch is "1":
    n = int(raw_input("Enter n number\t"))
    i = 1
    for i in range (1,n):
      di = 1
      count = 0
      remainder = 0
      for di in range (1,i+1):
        remainder = int(i % di)
        if remainder is 0:
          count = count +1
        di = di+1
      if count<3:
        print i
      i = i +1
        
  if ch is "2":
    n = int (raw_input("Enter n number\t"))
    nfact = 1
    r = int(raw_input("Enter t number \t"))
    rfact = 1
    t = n - r 
    tfact = 1
    print "%d" % (t)
    i = 1
    for i in range(1,n):
      nfact = nfact*(i+1)
      i = i+1
    for i in range(1,r):
      rfact = rfact*(i+1)
      i=i+1
    for i in range(1,t):
      tfact = tfact * (i+1) 
      i=i+1
    result = int(nfact/(rfact*tfact))
    print "Combination(n,r) : %d" % (result)
  if ch is not  "1" and ch is not "2":
    print ("Wrong choice ! ")
  cont = raw_input("Enter E to Exit \nAnychar to continue\t")
