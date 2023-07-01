import java.util.Comparator; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Collection;

/* Class that contains code to run BestF search
 * extended from Search 
 * contains code to run informed search, print frontier with fCost, make node for informed search, 
 * calculate manhattan distance, calculate fCost based on heuristic that is specific to BestF
 * @author: Student ID: 220031271
 * 
 */
public class BestF extends Search{
    
    /**
     * Method to run informed search based on the heuristics.
     *
     * @param conf         		configuration in which search should be run
     */
    public void runSearch(Conf conf){
		start = conf.getS();
		goal = conf.getG();
        
        //create comparator to determine the priority in the PriorityQueue
        Comparator<Node> comparator = new CostComparator();
        PriorityQueue<Node> frontier = new PriorityQueue<Node>(1, comparator);

        //initialize the explored list
		explored = new ArrayList<Node>();

        //create start node, calculate the fCost and add to frontier
		Node initialNode = new Node(start, null, null);
        calculateFCost(initialNode);
		frontier.add(initialNode);
		
        //iterate until frontier is empty
		while(!frontier.isEmpty()){
			printFrontier(frontier);

            //removes the node with highest priority
			Node currentNode = frontier.remove();

            //add to explored list
            explored.add(currentNode);

            //initialize the goal and returns if goal is found
			if(currentNode.getState().equals(goal)){
				goalNode = currentNode;
				return;
			}			
            //get the successors list
			ArrayList<Node> successors = successor(currentNode, frontier, conf.getMap());	
            //add successors to frontier	
			frontier.addAll(successors);			
		}
		return;
	}

    /**
     * Method to print the items in the frontier along with the cost.
     * Nodes are printed in the order of the priority
     *
     * @param frontier 			frontier list
     */
    public void printFrontier(Collection frontier){
        boolean firstNode = true;
        Comparator<Node> comparator = new CostComparator();

        //copy to an array list and sort according to the priority
        ArrayList<Node> nodes = new ArrayList<Node>(frontier);
        Collections.sort(nodes, comparator);
		System.out.print("[");
		for (Node node : nodes){	
			if(!firstNode){
				System.out.print(",");
			}
            else{	
                firstNode = false;	
            }
			System.out.print(node.getState().toString()+":"+node.getFCost());
		}
		System.out.println("]");

	}
    
    /**
     * Method to calculate and assign the fCost for BestF Search. 
     * fCost = hCost     
     * @param node 				node for which the cost needs to be calculated
     */
    public void calculateFCost(Node node){
        //get manhattan distance heuristic
        double manhattanDistance = findTriangularGridManhattanDistance(node);
        node.setHCost(manhattanDistance);
        //fCost is same as hCost in BestF
        node.setFCost(manhattanDistance);
        
    }

    /**
     * Method to calculate the manhattan distance for informed Search.     
     * @param node 				node for which the distance needs to be calculated
     */
    public double findTriangularGridManhattanDistance(Node node){
        double manhattanDistance = 0;

        //calculate manhattan distance
        if(!node.getState().equals(goal)){
            int nodeR = node.getState().getR();
            int nodeC = node.getState().getC();
            short nodeDirection = node.getState().getDirection();

            int goalR = goal.getR();
            int goalC = goal.getC();
            short goalDirection = goal.getDirection();

            int an = -nodeR;
            float bn = (nodeR + nodeC - nodeDirection)/2;
            float cn = bn - nodeR + nodeDirection;

            int ag = -goalR;
            float bg = (goalR + goalC - goalDirection)/2;
            float cg = bg - goalR + goalDirection;

            manhattanDistance = Math.abs(ag - an) + Math.abs(bg - bn) + Math.abs(cg - cn);
        }
        
        return manhattanDistance;
    }

    /**
     * Method to create a node with its state, predecessor and action.
     * Also assigns fCost for informed search      
     * @param r 				search algorithm to be executed
     * @param c         		configuration in which search should be run
     * @param predecessor 		parent node of the node to be created
     * @param action         	action to reach the node from the parent node
     * @return 					node object that has been created
     */
    @Override
    public Node makeNode(int r, int c, Node predecessor, String action){
        Coord state = new Coord(r,c);
		Node node = new Node(state, predecessor, action);
        //fCost is calculated and assigned in case of informed search
        calculateFCost(node);
        return node;
    }

}