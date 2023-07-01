import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* Class contains code to run IDAStar search
 * extended from AStar 
 * contains code to run limited DFS search, code to print final output specific to this search.
 * @author: Student ID: 220031271
 * 
 */
public class IDAStar extends AStar{

    double fCostLimit;								//to store the fCost limit in each iteration
    Set<Double> prunedNodeCost = new HashSet<>();	//set of costs from nodes that were pruned due to the fCostLimit
    int totalExploredCount = 0;						//Total no of nodes visited in all iterations
    
	/**
     * Method to run IDAStar
     *
     * @param conf 				configuration in which the search needs to be run
     */
    public void runSearch(Conf conf){
		start = conf.getS();
        goal = conf.getG();
		//create initial node and calculate the fCost
        Node initialNode = new Node(start, null, null);
        calculateFCost(initialNode);
		//assign fCost of start node as the initial limit
		fCostLimit = initialNode.getFCost();
        int i=1; //to track no of iterations
		while(true){
			System.out.println("\nIteration "+i+"- fCostLimit :"+fCostLimit);
			runLimitedDFS(conf, initialNode);	//run DFS with limited depth (fCost)
            System.out.println("Nodes visited in iteration "+i+": "+explored.size());
            totalExploredCount=totalExploredCount+explored.size(); //adds up in each iteration to get the total no of nodes visited
           //returns if goal is found or there are no more pruned nodes
            if(goalNode!=null || prunedNodeCost.isEmpty()){
                return;
            }
			//set next minimum fCost value as the limit
            fCostLimit = prunedNodeCost.stream().min(Double::compareTo).get();
            prunedNodeCost.remove(fCostLimit);	//remove the new value from prunedNodeCost set
            i+=1;
        }
            
	}

	/**
     * Method to run limited DFS based on the fCost limit.
     *      
     * @param conf 			configuration on which the search needs to be run
     * @param initialNode   initial node
     */
    public void runLimitedDFS(Conf conf, Node initialNode){
		start = conf.getS();
		goal = conf.getG();

		//initialize frontier list
		ArrayList<Node> frontier = new ArrayList<Node>();
        
		//initialize explored list (values will be cleared for each iteration)
		explored = new ArrayList<Node>();	
		//add initial node to frontier	
		frontier.add(initialNode);
        
		//iterate until frontier is empty
		while(!frontier.isEmpty()){
			printFrontier(frontier);            		
			Node currentNode = removeFromFrontier(frontier);
			//if fCost of the current node is greater than the limit then skip the iteration
            if(currentNode.getFCost() > fCostLimit){
				//add the cost to prunedNodeCost set
                prunedNodeCost.add(currentNode.getFCost());
                continue;
            }
			//add the current node to explored list if the fCost is within the limit
            explored.add(currentNode);
			if(currentNode.getState().equals(goal)){
				goalNode = currentNode;
				return;
			}
			//get successors list of the current node and insert to frontier
			ArrayList<Node> successors = new ArrayList<Node>();
            successors = successor(currentNode, frontier, conf.getMap());
            insertIntoFrontier(frontier, successors);			
		}
		return;
	}

    /**
     * Method to insert into frontier for DFS.
     * The list is first flipped and then inserted at the end of the frontier
     *
     * @param frontier 			frontier list
     * @param successors        successors list
     */
    public void insertIntoFrontier(ArrayList<Node> frontier, ArrayList<Node> successors){
        Collections.reverse(successors);	//in DFS reverse the successors and then insert to frontier to follow the tie breaking strategy
        frontier.addAll(successors);
    }

	/**
     * Method to remive from frontier for DFS.
     * The node is removed from the end of the frontier list
     *
     * @param frontier 			frontier list
	 * @return					Node that is removed
     */
    public Node removeFromFrontier(ArrayList<Node> frontier){
        int endIndex=frontier.size()-1;
		Node currentNode = frontier.remove(endIndex); // remove from end of the list to implent LIFO
        return currentNode;
            
    }

	/**
     * Method to print final search output
     */
    @Override
    public void printFinalSearchOutput(){
		System.out.println("\nResult");
        if(goalNode!=null){
			printPathCoordinates();
			printPathDirections();
			System.out.println(goalNode.getPathCost());
		}
		else{
			System.out.println("\nfail");
		}
		//no of nodes visited is the sum of nodes visited in all iterations
		System.out.println(totalExploredCount);
	}
    
	/**
     * Method to print frontier for IDAStar with fCost, but not in the order of the fCost
     *
     * @param frontier 			frontier list
     */
    @Override
    public void printFrontier(Collection frontier){
        boolean firstNode = true;
        ArrayList<Node> nodes = new ArrayList<Node>(frontier);
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

}
