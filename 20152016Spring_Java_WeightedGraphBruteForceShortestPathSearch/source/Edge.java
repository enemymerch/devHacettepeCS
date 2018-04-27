/**
 * DirectedGraphs's Edge 
 * 
 * @author mcany
 *
 */
public class Edge {
	private int SourceVertex;
	private int DestinationVertex;
	private int Weight;
	
	public Edge(Edge T){
		this.SourceVertex = T.getSourceVertex();
		this.DestinationVertex = T.getDestinationVertex();
		this.Weight = T.getWeight();
	}
	
	public Edge(int SourceVertex, int DestinationVertex, int Weight){
		this.SourceVertex = SourceVertex;
		this.DestinationVertex = DestinationVertex;
		this.Weight = Weight;
	}
	/**
	 * 
	 * @param t
	 * @return true if two Edges is ths same 
	 */
	public boolean isEqual(Edge t){
		if(this.getDestinationVertex() == t.getDestinationVertex() &&
				this.getSourceVertex() == t.getSourceVertex()){
			return true;
		}
		return false;
	}
	@Override
	public String toString(){
		return "SourceVertex :"+Integer.toString(this.SourceVertex)+" DestinationVertex :"+Integer.toString(this.DestinationVertex)+" Weight :"
				+Integer.toString(this.Weight);
	}
	/**
	 * @return the sourceVertex
	 */
	public int getSourceVertex() {
		return SourceVertex;
	}

	/**
	 * @param sourceVertex the sourceVertex to set
	 */
	public void setSourceVertex(int sourceVertex) {
		SourceVertex = sourceVertex;
	}

	/**
	 * @return the destinationVertex
	 */
	public int getDestinationVertex() {
		return DestinationVertex;
	}

	/**
	 * @param destinationVertex the destinationVertex to set
	 */
	public void setDestinationVertex(int destinationVertex) {
		DestinationVertex = destinationVertex;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return Weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		Weight = weight;
	}
}
