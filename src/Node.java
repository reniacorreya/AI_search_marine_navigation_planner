/* Class that stores information needed for each node/cell in the search.
 * @author: Student ID: 220031271
 * 
 */
public class Node{

    private Coord state;        //stores the coordinates 
    private Node predecessor;   //store the parent node
    private String action;      //store the action performed from the parent node (Right, Down, Left, Up)
    private double pathCost = 0;//path cost from start to the node

    private int depth = 0;      //depth of the node
    private double fCost = 0;   //fCost of the node (BestF = heuristic, AStar = pathCost+heuristic)
    private double hCost = 0;   //hueristic cost 


    public Node(Coord state, Node predecessor, String action){
        this.state = state;
        this.predecessor = predecessor;
        this.action = action;
        if(predecessor!=null){
            this.depth = 1 + predecessor.getDepth(); //adds up by one from the parent node
        }
        if(predecessor!=null){
            this.pathCost = 1 + predecessor.getPathCost();  //adds up by one from the parent node (step cost =1)
        }
    }
    
    public Coord getState(){
        return this.state;
    }

    public void setState(Coord state){
        this.state = state;
    }

    public Node getPredecessor(){
        return this.predecessor;
    }

    public void setPredecessor(Node predecessor){
        this.predecessor = predecessor;
    }

    public double getPathCost(){
        return this.pathCost;
    }

    public void setPathCost(double pathCost){
        this.pathCost = pathCost;
    }

    public int getDepth(){
        return this.depth;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    public String getAction(){
        return this.action;
    }

    public void setAction(String action){
        this.action = action;
    }

    public void setHCost(double hCost){
        this.hCost = hCost;
    }

    public double getHFCost(){
        return this.hCost;
    }

    public void setFCost(double fCost){
        this.fCost = fCost;
    }

    public double getFCost(){
        return this.fCost;
    }

    @Override
    public boolean equals(Object o) {

        if (o != null && o instanceof Node) {
            Node nd = (Node) o;
            if(nd.state.equals(state)) {
			    return true;
            }
		}
		return false; 
    }

}