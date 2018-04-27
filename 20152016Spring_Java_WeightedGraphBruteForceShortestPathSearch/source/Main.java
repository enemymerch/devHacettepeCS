import java.util.*;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		InputOutput ioStream = new InputOutput();
		ArrayList<String> lines = ioStream.readLines(args[0]);
		Graph DirectedGraph = new Graph();
		
		for(int i=1;i<lines.size();i++){
			DirectedGraph.addVertex(i);
		}
		// Getting command ->> SourceVertex(int) , DestinationVertex(int)
		String[] command = lines.get(0).split(",");
		int SourceVertex = Integer.parseInt(command[0].split(":")[1]);
		int DestinationVertex = Integer.parseInt(command[1].split(":")[1]);
		
		// MustPassVerticies
		ArrayList<Integer> MustPassVertecies = new ArrayList<Integer>();
		// Parsing input and Constructing new Graph
		for(int i=1;i<lines.size();i++){
			String[] temp = lines.get(i).split(" ");
			if(temp.length>2){
				MustPassVertecies.add(i);
			}
			String[] Edges = temp[1].split(",");
			
			String VertexDestination ;
			String weight;
			for(int j=0;j<Edges.length;j++){
				VertexDestination = Edges[j].split(Pattern.quote("("))[0];
				if(VertexDestination.equalsIgnoreCase("")){
					weight = null;
				}else{
					weight = Edges[j].split(Pattern.quote("("))[1].split(Pattern.quote(")"))[0];
					Edge tEdge = new Edge(i, Integer.parseInt(VertexDestination.trim()), Integer.parseInt(weight.trim()));
					DirectedGraph.addEdge(tEdge);
				}	
			}
		}
		//ShortestPath && ConstrainedShortestPath
		Stack<Edge> ShortestPath = new Stack<Edge>();
		Stack<Edge> ConstrainedShortestPath = new Stack<Edge>();

		//Find ShortestPath
		ShortestPath = ShortestPath(DirectedGraph, SourceVertex, DestinationVertex);	
		//Find ConstrainedShortestPath
		ConstrainedShortestPath = ConstrainedShortestPath(DirectedGraph,SourceVertex,DestinationVertex,MustPassVertecies);
		
		//
		String ShortestPathOuput = ShortestPathOutput(ShortestPath);
		String ConstrainedShortestPathOutput = ConstrainedShortestPathOutput(ConstrainedShortestPath,MustPassVertecies);
		
		ioStream.WriteToFile(args[1], ShortestPathOuput, ConstrainedShortestPathOutput);

	}
	
	/**
	 * 
	 * @param Path
	 * @return ArrayList<Integer> Verticies which Path contains
	 */
	public static ArrayList<Integer> getVertecies(Stack<Edge> Path){
		ArrayList<Integer> Vertecies = new ArrayList<Integer>();
		for(int i=0;i<Path.size();i++){
			Vertecies.add(Path.get(i).getSourceVertex());
			if((i+1) == Path.size()){
				Vertecies.add(Path.get(i).getDestinationVertex());
			}
		}
		return Vertecies;
	}
	
	/**
	 * 
	 * @param ShortestPath
	 * @return need output string part of ShortestPathOutput
	 */
	public static String ShortestPathOutput(Stack<Edge> ShortestPath){
		ArrayList<Integer> Vertecies = getVertecies(ShortestPath);
		// Creating Path part of output
		String Path="Path=(";
		for(int i=0;i<Vertecies.size();i++){
			Path = Path + Integer.toString(Vertecies.get(i)) + " ";
		}
		Path = Path + ")";
		String Distance = "";
		Distance = "Distance="+Double.toString((double)getCost(ShortestPath))+"     ";
		
		return "Shortest Path:  "+ Distance+ Path +"\n\n";
	}
	/**
	 * 
	 * @param ConstrainedShortestPath
	 * @param MustPassVertecies
	 * @return need output string part of ConstrainedShortestPathOutput
	 */
	public static String ConstrainedShortestPathOutput(Stack<Edge> ConstrainedShortestPath,ArrayList<Integer> MustPassVertecies){
		ArrayList<Integer> Vertecies = getVertecies(ConstrainedShortestPath);
		// Creating Path part of output
		String Path="Path=(";
		for(int i=0;i<Vertecies.size();i++){
			if(MustPassVertecies.contains(Vertecies.get(i))){
				Path = Path + Integer.toString(Vertecies.get(i)) + "(mustpass) ";
			}else{
				Path = Path + Integer.toString(Vertecies.get(i)) + " ";
			}
		}
		Path = Path + ")";
		String Distance = "";
		Distance = "Distance="+Double.toString((double)getCost(ConstrainedShortestPath))+"     ";
		
		return "Constrained Shortest Path:  "+Distance+Path+"\n\n";
	
	}
	/**
	 * 
	 * @param dGraph
	 * @param From Vertex
	 * @param To Vertex
	 * @param MustPassVertecies 
	 * @return ConstrainedShortestPath
	 */
	public static Stack<Edge> ConstrainedShortestPath(Graph dGraph,int From, int To,ArrayList<Integer> MustPassVertecies){
		//Create a List which will hold the Paths(Stack<Edge>) 
		List<Stack<Edge>> Paths = new ArrayList<Stack<Edge>>();
		//Get Paths
		Paths = getPaths(dGraph,Paths,new Stack<Edge>(), From, To);
		// Find Paths which include MustPassVertecies
		Stack<Edge> ConstrainedShortestPath = new Stack<Edge>();
		// Get ConstrainedPaths
		Paths = getConstrainedPaths(Paths,MustPassVertecies);
		//Find the shortest Constrained Path
		ConstrainedShortestPath = Paths.get(0);
		for(int i=0;i<Paths.size();i++){
			if(getCost(Paths.get(i)) < getCost(ConstrainedShortestPath)){
				ConstrainedShortestPath = Paths.get(i);
			}
		}
		
		return ConstrainedShortestPath;
	}
	/**
	 * Called by ConstrainedShortestPath
	 * @param Paths-->List of Paths
	 * @param MustPassVertecies-->mustpass vertices
	 * @return ---> List of Paths which includes mustpass
	 * Method deletes path , if path doesn't include mustpass vertices
	 */
	public static List<Stack<Edge>> getConstrainedPaths(List<Stack<Edge>> Paths,ArrayList<Integer> MustPassVertecies){
		
		for(int i=0;i<Paths.size();i++){
			Stack<Edge> tempStack = Paths.get(i);
			for(int j=0;j<MustPassVertecies.size();j++){
				if(!Contains(tempStack, MustPassVertecies.get(j))){
					Paths.remove(tempStack);
					i=0;
					break;
				}
			}
		}
		return Paths;
	}
	/**
	 * Called by getConstrainedPaths Method
	 * @param Path
	 * @param MustPassVertex
	 * @return true if Path contains mustpassVertex
	 */
	public static boolean Contains(Stack<Edge> Path, int MustPassVertex){
		
		for(int i=0;i<Path.size();i++){
			Edge tempEdge = Path.get(i);
			// Last Edge
			if((i+1) == Path.size() && tempEdge.getDestinationVertex() == MustPassVertex){
				return true;
			}
			if(tempEdge.getSourceVertex() == MustPassVertex){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param dGraph
	 * @param From
	 * @param To
	 * @return ShortestPath(don't care about mustpassvertecies)
	 */
	public static Stack<Edge> ShortestPath(Graph dGraph,int From, int To){

		List<Stack<Edge>> Paths = new ArrayList<Stack<Edge>>();
		Paths = getPaths(dGraph,Paths,new Stack<Edge>(), From, To);
		Stack<Edge> ShortestPath = new Stack<Edge>();
		ShortestPath = Paths.get(0);
		
		for(int i=0;i<Paths.size();i++){
			if(getCost(Paths.get(i))<getCost(ShortestPath)){
				ShortestPath = Paths.get(i);
			}
		}
		return ShortestPath;
	}
	
	public static int getCost(Stack<Edge> Path){
		int Cost = 0;
		for(int i=0;i<Path.size();i++){
			Cost += Path.get(i).getWeight();
		}
		return Cost;
	}
	
	/**
	 * Generel method for getting all paths
	 * from SourceVertex  to DestinationVertex
	 * @param dGraph
	 * @param Paths
	 * @param Path
	 * @param From
	 * @param To
	 * @return List<Stack<Edge>>  Paths
	 */
	public static List<Stack<Edge>> getPaths(Graph dGraph,List<Stack<Edge>> Paths,Stack<Edge> Path,int From, int To){

		if(From == To){
			Stack<Edge> tempPath = new Stack<Edge>();

			for(int i=0;i<Path.size();i++){
				Edge tempEdge = new Edge(Path.get(i));
				tempPath.push(tempEdge);
			}
			//Created new stack and added it to Paths List 
			Paths.add(tempPath);
			// Popped old stack
			Path.pop();
			return Paths;
		}else{

			for(int i=0;i<dGraph.getEdges(From).size();i++){				
				int tempVertex = dGraph.getEdges(From).get(i).getDestinationVertex();
				
				if(!Path.contains(dGraph.getEdges(From).get(i))){
				Path.push(dGraph.getEdges(From).get(i));
				getPaths(dGraph, Paths, Path, tempVertex, To);
				}
			}
			
			if(!Path.isEmpty()){
				Path.pop();
			}
			return Paths;
		}
	}
}
