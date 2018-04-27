import java.util.*;

public class Graph {
	// Key(integer) is the Vertex !
	// and every vertex has its own Edges(ArrayList) !
	private Map<Integer , ArrayList<Edge>> AdjacencyList;
	
	public Graph(){// Graph contains a HashMap(Vertex,Vertex's Edge list)
		AdjacencyList = new HashMap<>();
	}
	/**
	 * @param VertexKey
	 * @return that Vertex's edgeList 
	 */
	public ArrayList<Edge> getEdges(int VertexKey){
		return this.AdjacencyList.get(VertexKey);
	}
	/**
	 * Adds Vertex to AdjacencyList
	 * @param VertexKey
	 * @return
	 */
	public boolean addVertex(int VertexKey){
		if(!this.AdjacencyList.containsKey(VertexKey)){
			this.AdjacencyList.put(VertexKey, new ArrayList<Edge>());
			return true;
		}
		return false;
	}
	@Override
	public String toString(){
		return this.AdjacencyList.toString();
	}
	/**
	 * Adds "Edge t" to that vertex's edge list 
	 * @param t
	 * @return
	 */
	public boolean addEdge(Edge t){
		if(this.AdjacencyList.containsKey(t.getSourceVertex())){
			ArrayList<Edge> temp = this.AdjacencyList.get(t.getSourceVertex());
			for(int i=0;i<temp.size();i++){
				if(temp.get(i).isEqual(t)){
					//System.out.println("Edge is already added!");
					return false;
				}
			}
			//System.out.println("Edge is succesfully added!");
			temp.add(t);
			return true;
		}else{
			//System.out.println("No such Vertex , cannot add the edge!");
			return false;
		}
	}
}
