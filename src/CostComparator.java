import java.util.Comparator;  
/* Comparator Class to customize priority of the priority queue used in informed search
 * @author: Student ID: 220031271
 * 
 */
public class CostComparator implements Comparator{  
   
    /**
     * Compares the fCosts of the nodes
     * if it is equal, compares the actions of the nodes
     * if it is equal, compres the depth of the nodes.
     * Helps to break tie for nodes in informed search 
     *
     * @param o1 			node 1
     * @param o2         	node 2
     * @return 				comparison result
     */
    @Override
    public int compare(Object o1,Object o2){  
        
        Node n1=(Node)o1;  
        Node n2=(Node)o2;
        double n1FCost = n1.getFCost();        
        double n2FCost = n2.getFCost();
        //compare by fCost
        if(n1FCost > n2FCost){
            return 1;
        }
        if(n1FCost < n2FCost){
            return -1;
        }

        //if fCost is same, compare by tie breaking strategy (Right, Down, Left, Up)
        int n1ActionPriorityValue = getActionPriorityValue(n1.getAction());
        int n2ActionPriorityValue = getActionPriorityValue(n2.getAction());
        if(n1ActionPriorityValue > n2ActionPriorityValue){
            return 1;
        }
        if(n1ActionPriorityValue < n2ActionPriorityValue){
            return -1;
        }

        //if both fCost and action is same, compare by depth of the node
        int n1Depth = n1.getDepth();
        int n2Depth = n2.getDepth();
        if(n1Depth > n2Depth){
            return 1;
        }
        if(n1Depth < n2Depth){
            return -1;
        }

        return 0;                
    }
                
     

    /**Method to find the priority of the actions according to the tie breaking strategy
     * @param action    action for which the priority is required
     * @return          priority value
     */
    private int getActionPriorityValue(String action){
        //assign priority for the actions
        switch(action){
            case "Right": return 1;
            case "Down": return 2;
            case "Left": return 3;
            case "Up": return 4;            
        }
        return 0;
    }
}