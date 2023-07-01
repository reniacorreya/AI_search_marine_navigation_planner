/* Class that implements code to run AStar search
 * extended from BestF 
 * contains code to calculate fCost based on heuristic that is specific to AStar
 * @author: Student ID: 220031271
 * 
 */
public class AStar extends BestF{
    
    /**
     * Method to calculate the fCost based on heuristics and path cost.
     *
     * @param node the node for which the cost needs to be calculated
     */
    @Override
    public void calculateFCost(Node node){
        //calculate manhattan distance heuristic
        double manhattanDistance = findTriangularGridManhattanDistance(node);
        node.setHCost(manhattanDistance);

        //fCost is sum of path cost and hCost in case of AStar
        node.setFCost(manhattanDistance + node.getPathCost());
    }
}