import java.util.ArrayList;
import java.util.Collections;

/* Class contains code to run Bidirectional search
 * extended from BFS 
 * contains code to run Bidirectional search, successor function, code to print final output specific to this search.
 * @author: Student ID: 220031271
 * 
 */
public class Bidirectional extends BFS{
    Node intersectionNode;			//node to store the intersection node in the search
    Node intersectionNodeFromStart;	//node to store the intersection node from start frontier (has path from start to intersection node)
    Node intersectionNodeFromEnd;	//node to store the intersection node from end frontier (has path from goal to intersection node)
    ArrayList<Node> startExplored;	//explored list for search from the start node
    ArrayList<Node> endExplored;	//explored list for search from the goal node

	/**
     * Method to run Bidirectional search
     *
     * @param conf         		configuration in which search should be run
     */
    public void runSearch(Conf conf){
		start = conf.getS();
		goal = conf.getG();

		ArrayList<Node> startFrontier = new ArrayList<Node>();	//frontier list for search from the start node
        ArrayList<Node> endFrontier = new ArrayList<Node>();	//frontier list for search from the start node

        startExplored = new ArrayList<Node>();
        endExplored = new ArrayList<Node>();
        
		//return if start or goal cell is land - no path 
        int[][] map=conf.getMap().getMap();
        if(!(isNotLand(start.getR(),start.getC(),map) && isNotLand(goal.getR(),goal.getC(),map))){
            return;
        }

		//make start and goal node and add to respective frontiers
		Node startNode = new Node(start, null, null);
        Node goalNode = new Node(goal, null, null);        
		
		startFrontier.add(startNode);
        endFrontier.add(goalNode);
		
		//iterate until the frontier lists are empty
		while(!(startFrontier.isEmpty()||endFrontier.isEmpty())){
            System.out.println("Start Frontier");
            printFrontier(startFrontier);
            System.out.println("End Frontier");
            printFrontier(endFrontier);
            System.out.println();
            
			//get the respective current nodes and add to corresponding explored lists
			Node currentNodeFromStart = removeFromFrontier(startFrontier);
            Node currentNodeFromEnd = removeFromFrontier(endFrontier);
            startExplored.add(currentNodeFromStart);
			endExplored.add(currentNodeFromEnd);
			
			//checks if currentNodeFromStart is in the explored list of search started from the goal
            if(endExplored.contains(currentNodeFromStart)){
				//get the intersection node from both the searches' explored list
				intersectionNodeFromStart = currentNodeFromStart;                
                int endExploredIndex = endExplored.indexOf(currentNodeFromStart); 
                intersectionNodeFromEnd = endExplored.get(endExploredIndex);
				return;
			}
			//checks if currentNodeFromEnd is in the explored list of search started from the start
            if(startExplored.contains(currentNodeFromEnd)){
				//get the intersection node from both the searches' explored list
				intersectionNodeFromEnd = currentNodeFromEnd;
                int startExploredIndex = startExplored.indexOf(currentNodeFromEnd);
                intersectionNodeFromStart = startExplored.get(startExploredIndex);
				return;
			}
			
			//expand current node and get successors list for the search from the start
			ArrayList<Node> startSuccessors = new ArrayList<Node>();
            startSuccessors = successor(currentNodeFromStart, startFrontier, startExplored, conf.getMap());
            insertIntoFrontier(startFrontier, startSuccessors);	
            
			//expand current node and get successors list for the search from the goal
            ArrayList<Node> endSuccessors = new ArrayList<Node>();
            endSuccessors = successor(currentNodeFromEnd, endFrontier, endExplored, conf.getMap());			
			//reverse the list as the tie breaking order needs to be flipped in case of search from goal
            Collections.reverse(endSuccessors);
            insertIntoFrontier(endFrontier, endSuccessors);
		}
		return;
	}

	/**
     * Method to get successors of a node based on the rules
     *
     * @param currentNode 			node for which successors should be found
     * @param frontier         		frontier list
	 * @param explored				list of nodes explored 
	 * @param m						map 
     * @return 						list of successors
     */
    public ArrayList<Node> successor(Node currentNode, ArrayList<Node> frontier, ArrayList<Node> explored, Map m){

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
     * Method to print final search output for bidirectional search
     */
    @Override
    public void printFinalSearchOutput(){ 		
        System.out.println("Result");
		//path is found if intersection is found in both the searches
		if(intersectionNodeFromStart!=null && intersectionNodeFromEnd!=null){
            System.out.println("Intersection Node: "+intersectionNodeFromStart.getState());
			printPathCoordinates();
			printPathDirections();
			printPathCost();
		}
		else{
			System.out.println("\nfail");
		}
		//No of nodes visited is the sum of nodes visited in both the searches
		System.out.println(startExplored.size()+endExplored.size());
	}

	/**
     * Method to print pathCost for bidirectional search
     */
    public void printPathCost(){
		//path cost is the sum of path costs to the intersection node in both the searches
        double pathCost = intersectionNodeFromStart.getPathCost() + intersectionNodeFromEnd.getPathCost();
        System.out.println(pathCost);

    }

	/**
     * Method to print path coordinates for bidirectional search
     */
	public void printPathCoordinates(){
		String pathCoordinates="";
        Node node = intersectionNodeFromStart;
        //get path from start to intersection node        
		while(node!=null){
			pathCoordinates = node.getState().toString()+pathCoordinates;
			node = node.getPredecessor();
		}
		//get path from intersection node to goal
        node = intersectionNodeFromEnd;
        while(node.getPredecessor()!=null){
			node = node.getPredecessor();
            pathCoordinates = pathCoordinates+node.getState().toString();	
		}
		System.out.println(pathCoordinates);
	}

	/**
     * Method to print path directions for bidirectional search
     */
	public void printPathDirections(){
		String pathDirections="";        
        //get path from start to intersection node
		Node node = intersectionNodeFromStart;		
		while(node.getPredecessor()!=null){
			pathDirections = node.getAction()+" "+pathDirections;
			node = node.getPredecessor();
		}

		//get path from intersection node to start
        //flip the actions to its opposite (Left becomes Right, Right becomes Left, Up becomes Down and Down becomes Up)
        invertPathDirection(intersectionNodeFromEnd);
        node = intersectionNodeFromEnd;
        while(node.getPredecessor()!=null){
			pathDirections = pathDirections.trim()+" "+node.getAction();		 
            node = node.getPredecessor();	
		}
		System.out.println(pathDirections);
	}

	/**
     * Method to invert the actions of the path found in the search from the goal
     *
     * @param node 					intersection node for which the path needs to be flipped
     */
    void invertPathDirection(Node node){
		//flip the actions to its opposite (Left becomes Right, Right becomes Left, Up becomes Down and Down becomes Up)
        while(node.getPredecessor()!=null){
            String action = node.getAction();
            switch(action){
                case "Left":    node.setAction("Right");
                                break;
                case "Up":      node.setAction("Down");
                                break;
                case "Right":   node.setAction("Left");
                                break;
                case "Down":    node.setAction("Up");
                                break;
            }
            node = node.getPredecessor();	
		}
                    
    }  

}
