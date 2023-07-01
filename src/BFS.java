import java.util.ArrayList;
import java.util.Collection;

/* Class contains code to run BFS search
 * extended from Search 
 * contains code to run uninformed search, print frontier, insert and remove from frontier.
 * @author: Student ID: 220031271
 * 
 */
public class BFS extends Search{

	/**
     * Method to run uninformed search.
     *
     * @param conf    configuration in which search should be run
     */
    public void runSearch(Conf conf){
		start = conf.getS();
		goal = conf.getG();

		//create frontier list
		ArrayList<Node> frontier = new ArrayList<Node>();

		//initialize explored list
		explored = new ArrayList<Node>();

		//create start node and add to frontier
		Node initialNode = new Node(start, null, null);		
		frontier.add(initialNode);
		
		//iterate until frontier is empty
		while(!frontier.isEmpty()){
			printFrontier(frontier);
            		
			Node currentNode = removeFromFrontier(frontier);

			//add current node to explored list
            explored.add(currentNode);

			//initialize the goal and returns if goal is found
			if(currentNode.getState().equals(goal)){
				goalNode = currentNode;
				return;
			}
			
			//get the successor nodes and insert into frontier
			ArrayList<Node> successors = new ArrayList<Node>();
            successors = successor(currentNode, frontier, conf.getMap());
            insertIntoFrontier(frontier, successors);			
		}
		return;
	}

	/**
     * Method to insert into frontier at the end
     *
     * @param frontier 			frontier list
	 * @param successors 		successors list that should be added to the frontier
     */
    public void insertIntoFrontier(ArrayList<Node> frontier, ArrayList<Node> successors){
        frontier.addAll(successors); //add the successors at the end of the list
    }

	/**
     * Method to remove from frontier from the front
     *
     * @param frontier 			frontier list
	 * @return 					node that is removed
     */
    public Node removeFromFrontier(ArrayList<Node> frontier){
        int frontIndex=0;			
		Node currentNode = frontier.remove(frontIndex); //remove the node in the front of the list
        return currentNode;
            
    }

	/**
     * Method to print the frontier
     *
     * @param frontier 			frontier list
     */
    public void printFrontier(Collection<Node> frontier){
		boolean firstNode = true;
        System.out.print("[");
		for (Node node : frontier){	
			if(!firstNode){
				System.out.print(",");
			}
            else{	
                firstNode = false;	
            }		
			System.out.print(node.getState().toString());
		}
		System.out.println("]");
	}

}