import java.util.ArrayList;
import java.util.Collections;

/* Class contains code to run DFS search
 * extended from BFS 
 * contains code to perform insert and remove from frontier specific to DFS .
 * @author: Student ID: 220031271
 * 
 */
public class DFS extends BFS{

    /**
     * Method to insert into frontier for DFS.
     * The list is first flipped and then inserted at the end of the frontier
     *
     * @param frontier 			frontier list
     * @param successors        successors list
     */
	@Override
    public void insertIntoFrontier(ArrayList<Node> frontier, ArrayList<Node> successors){
        Collections.reverse(successors); //reverse the list so that the tie breaking priority is flipped in DFS
        frontier.addAll(successors);    //add the successors at the end
    }

    /**
     * Method to remove from frontier for DFS.
     * Node is removed from the end of the frontier list
     *
     * @param frontier 			frontier list
     * @return 				    node that needs to be removed
     */
	@Override
    public Node removeFromFrontier(ArrayList<Node> frontier){
        int lastIndex=frontier.size()-1;               
		Node currentNode = frontier.remove(lastIndex); //remove from the end of the list to meet LIFO
        return currentNode;
            
    }

}