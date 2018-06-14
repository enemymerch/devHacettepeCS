Algorithm-->
	I didn't choose specific algorithm for finding shortest path , because of mustpass verticies . I thought they could be troubling but i didn't think about complexity. So i choosed to go with brutal search(recursive) and found all the path from SourceVertex to DestinationVertex .
	That makes finding ConstrainedShortestPath is so much easier but the time needed for this recursive algorithm is too much. 
	By my experience with on my own computer ,i  didn't wait for much long .(i waited 60sec(TOP) with given input example 1 , then i terminated process by myself).But when i used input from (Assignment-5 pdf) i worked. So i know it works fine but needs some time which makes this program terrible.



Process - Steps

1->Program parses the input ant creates the graph
2->Finds all Paths from sourceVertex to DestinationVertex
3->Finds ShortestPath from allPaths.
4->Finds ConstrainedShortestPath from allPaths too.
5->Writes to outputFile.
6->Terminates .
