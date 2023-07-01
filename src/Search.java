import java.util.ArrayList;
import java.util.Collection;

/* Class contains code to for general search algorithm.
 * @author: Student ID: 220031271
 * 
 */
public abstract class Search{

	Node goalNode;				//stores the goal node if found
	ArrayList<Node> explored;	//stores the list of expanded/explored nodes
	Coord goal;					//coordinates of the goal
	Coord start;				//coordinates of the start

    public abstract void runSearch(Conf conf);
	public abstract void printFrontier(Collection<Node> frontier);

	public Node getGoalNode() {
		return goalNode;
	}

    public ArrayList<Node> getExplored() {
		return explored;
	}
	
	/**
     * Method to create node.
     * The list is first flipped and then inserted at the end of the frontier
     *
     * @param r 			row index of the node
     * @param c        		column index of the node
	 * @param predecessor	parent node
	 * @param action		action to reach the node from the parent node
	 * @return				node that is created
     */
	public Node makeNode(int r, int c, Node predecessor, String action){
        Coord state = new Coord(r,c);
		Node node = new Node(state, predecessor, action);
        return node;
    }


	/**
     * Method to get the successors list.
     *
     * @param currentNode		node for which successors list need to be created
	 * @param frontier 			frontier list
	 * @param m					Map
     * @return 			        successors list
     */
	public ArrayList<Node> successor(Node currentNode, Collection frontier, Map m){

		int[][] map=m.getMap();
		int rows=map.length;		//No of rows in the map
		int columns=map[0].length;	//No of columns in the map

		//get the coordinates and direction of the current node
		int currentR = currentNode.getState().getR();	
		int currentC = currentNode.getState().getC();
        short currentDirection = currentNode.getState().getDirection();

		//successors list to store the valid adjacent nodes
		ArrayList<Node> successors = new ArrayList<Node>();

		//check if the current node is not in the right boundary
		if(currentC!=columns-1){			
			
			//check if right node is free
			if(isNotLand(currentR,currentC+1,map)){
				//make node with action Right
				Node node = makeNode(currentR,currentC+1,currentNode, "Right");

				//add to list if not present in frontier and explored lists
				if(!frontier.contains(node) && !explored.contains(node))
					successors.add(node);
			}
		}

		//check if the current node is not in the bottom boundary and is an upward pointing triangle
		if(currentR!=rows-1 && currentDirection == 0){
			//check if down node is free
			if(isNotLand(currentR+1,currentC,map)){
				//make node with action Down
				Node node = makeNode(currentR+1,currentC,currentNode, "Down");
				//add to list if not present in frontier and explored lists
				if(!frontier.contains(node) && !explored.contains(node))
					successors.add(node);
			}
		}		

		//check if the current node is not in the left boundary
		if(currentC!=0){
			//check if left node is free
			if(isNotLand(currentR,currentC-1,map)){
				//make node with action Left
				Node node = makeNode(currentR,currentC-1,currentNode,"Left");
				//add to list if not present in frontier and explored lists
				if(!frontier.contains(node) && !explored.contains(node))
					successors.add(node);
			}
		}

		//check if the current node is not in the top boundary and is a downward pointing triangle
		if(currentR!=0 && currentDirection == 1){
			//check if up node is free
			if(isNotLand(currentR-1,currentC,map)){
				//make node with action Up
				Node node = makeNode(currentR-1,currentC,currentNode,"Up");
				//add to list if not present in frontier and explored lists
				if(!frontier.contains(node) && !explored.contains(node))
					successors.add(node);
			}
		}
		
		return successors;

	}

	/**
     * Method to print path coordinates
     */
	public void printPathCoordinates(){
		String pathCoordinates="";
		Node node = goalNode;
		//concatenate the path coordinates to a string by iterating over the predecessor node
		while(node!=null){
			pathCoordinates = node.getState().toString()+pathCoordinates; //string is appended from front as the iteration starts from goal
			node = node.getPredecessor();
		}
		System.out.println(pathCoordinates);
	}

	/**
     * Method to print path directions
     */
	public void printPathDirections(){
		String pathDirections="";
		Node node = goalNode;
		//concatenate the action to a string by iterating over the predecessor node
		while(node.getPredecessor()!=null){
			pathDirections = node.getAction()+" "+pathDirections; //string is appended from front as the iteration starts from goal
			node = node.getPredecessor();
		}
		System.out.println(pathDirections);
	}

	/**
     * Method to print final search output
     */
	public void printFinalSearchOutput(){
		//check if goal node is found
		if(goalNode!=null){
			printPathCoordinates();
			printPathDirections();
			System.out.println(goalNode.getPathCost());
		}
		else{
			System.out.println("\nfail");
		}
		//prints number of nodes visited
		System.out.println(explored.size());
	}

	/**
     * Method to check if a cell is free or if it is land
     * @param x 		row index
     * @param y        	column index
	 * @param map 		map
	 * @return			true if it is free, false if it is land
     */
	public boolean isNotLand(int x, int y, int[][] map){
		if(map[x][y]==0){
			return true; //returns true if the cell is free
		}
		return false;	//returns false if the cell is land
	}

}