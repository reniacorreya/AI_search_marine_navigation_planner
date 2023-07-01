/********************Starter Code
 * 
 * Starter class for CS5011 A1 - Search - Marine Navigation Planner
 * Class contains the main method, code to invoke the corresponding search method
 * and code for printing the map 
 * 
 * @author at258
 * @author: Student ID: 220031271
 * 
 * run with 
 * java A1main <Algo> <ConfID>
 * 
 */

public class A1main {

	/**
     * Main Method.
     *
     * @param args[0] 	algorithm
     * @param args[1]   configuration
     */
	public static void main(String[] args) {
		Conf conf;			//configuration object to retrieve the configuration based on input
		String algorithm;	//to store the algorithm name
		try{
			algorithm =  args[0];
			conf = Conf.valueOf(args[1]);
		

			//Uncomment here for debugging only
			/*
		 
			System.out.println("Configuration:"+args[1]);
			System.out.println("Map:");
			printMap(conf.getMap(), conf.getS(), conf.getG());
			System.out.println("Departure port: Start (r_s,c_s): "+conf.getS());
			System.out.println("Destination port: Goal (r_g,c_g): "+conf.getG());
			System.out.println("Search algorithm: "+algorithm);
			System.out.println();
			*/

			//invoke runSearch method
			Search search = runSearch(algorithm,conf);		
			
			//print the search output
			if(search!=null){
				search.printFinalSearchOutput();
			}

		}
		catch(IllegalArgumentException e){
			System.out.println("Configuration Not Found!!!");
			return;
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Please provide Search Name(BFS|DFS|BestF|AStar|Biderectional|IDAStar) and Configuration name as arguments.");
			return;
		}
		catch(Exception e){
			System.out.println("An exception occurred!!!");
			System.out.println(e.getMessage());
			return;
		}		
	}	

	/**
     * Method to instantiate the corresponding search class and run the 
	 * search method based on the algorithm.
     *
     * @param algo 				search algorithm to be executed
     * @param conf         		configuration in which search should be run
     * @return 					search object with the result
     */
	private static Search runSearch(String algo, Conf conf) {
		Search search = null;
		switch(algo) {
		case "BFS": //run Breadth First Search
		 search = new BFS();
			search.runSearch(conf);
			break;
		case "DFS": //run Depth First Search
		 search = new DFS();
			search.runSearch(conf);
			break;  
		case "BestF": //run Best First Search
			search = new BestF();
			search.runSearch(conf);
			break;
		case "AStar": //run AStar Search
			search = new AStar();
			search.runSearch(conf);
			break;
		case "Bidirectional": //run Bidirectional search (Advanced)
			search = new Bidirectional();
			search.runSearch(conf);
			break;
		case "IDAStar": //run IDAStar search(Advanced)
			search = new IDAStar();
			search.runSearch(conf);
			break;
		default: System.out.println("Invalid Search");
		}
		return search;
	}

	private static void printMap(Map m, Coord init, Coord goal) {

		int[][] map=m.getMap();

		System.out.println();
		int rows=map.length;
		int columns=map[0].length;

		//top row
		System.out.print("  ");
		for(int c=0;c<columns;c++) {
			System.out.print(" "+c);
		}
		System.out.println();
		System.out.print("  ");
		for(int c=0;c<columns;c++) {
			System.out.print(" -");
		}
		System.out.println();

		//print rows 
		for(int r=0;r<rows;r++) {
			boolean right;
			System.out.print(r+"|");
			if(r%2==0) { //even row, starts right [=starts left & flip right]
				right=false;
			}else { //odd row, starts left [=starts right & flip left]
				right=true;
			}
			for(int c=0;c<columns;c++) {
				System.out.print(flip(right));
				if(isCoord(init,r,c)) {
					System.out.print("S");
				}else {
					if(isCoord(goal,r,c)) {
						System.out.print("G");
					}else {
						if(map[r][c]==0){
							System.out.print(".");
						}else{
							System.out.print(map[r][c]);
						}
					}
				}
				right=!right;
			}
			System.out.println(flip(right));
		}
		System.out.println();
	}

	private static boolean isCoord(Coord coord, int r, int c) {
		//check if coordinates are the same as current (r,c)
		if(coord.getR()==r && coord.getC()==c) {
			return true;
		}
		return false;
	}


	public static String flip(boolean right) {
        //prints triangle edges
		if(right) {
			return "\\"; //right return left
		}else {
			return "/"; //left return right
		}

	}

}
