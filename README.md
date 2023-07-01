# AI_search_marine_navigation_planner
Marine Navigation Planner using uninformed and informed search algorithms

A marine navigation route planner is a system that helps compute the best route for a vessel. There are numerous marine navigational apps and simulators to be used for professional or recreational purposes. This is a simplified version of a marine navigation route planner for autonomous ferries to navigate the sea surrounding the Republic of Izaland and connecting its 400 islands [1] . An autonomous ferry is used to transport passengers from an island to another without human intervention. To better plan waypoints on the navigation charts, we will use a map inspired by a 2D triangular mesh [2] , where a portion of Izaland’s sea and land is represented via a 2D triangular grid. Our route finder planner is to be operated by an agent whose aim is to find the best route from the departure port to the destination port, and we refer to this simply as a ferry agent in the spec. The agent moves from the centre of a triangle to the centre of an adjacent triangle but some triangles cannot be crossed as they represent portions of land.

Our agent is equipped with a 2D map where cells are all equilateral triangles of the same size as shown in Figure 1. The agent starts from a cell where we assume there are awaiting passengers at a departure port S, and brings the passengers to a destination port G, in the most efficient way to provide the best possible customer service.

![image](https://github.com/reniacorreya/AI_search_marine_navigation_planner/assets/122054528/3a148112-8a4a-4273-9805-5473af136370)

Figure 1: Example map


The ferry agent moves in the navigation map according to the following rules:

- The map is formed by triangles organised on a square grid of NxN dimensions (where N is a positive integer, N=6 in Fig. 1) where each coordinate in the grid represents the centre of a triangle (marked as grey dots in Fig. 1). The grid is constructed by alternating an upwards pointing triangle with a downwards pointing triangle to fill the N positions in the row. The top row starts with an upwards pointing triangle, and the row continues with alternating triangles shapes. The second row starts with a downwards pointing triangle, etc...
- The maps are then simply represented as a two-dimensional coordinate system (r,c) of cells where r is the row and c is the column.
- Maps for evaluation are provided with the starter code (see Section 4.2) and are represented as Java 2D arrays of integers, int[][] map=new int[rows][cols], where rows=cols=N.
- Free cells are marked with a ‘0’ (white cells in Fig.1).
- The agent starts at cell S and then terminates at cell G (e.g., in Fig. 1 S=(1,1), G=(3,4)).
- The agent can only move along the grid, from free cell to free cell using the coordinate system. We assume that each movement is from the centre of a cell to the centre of the next cell and that each step has cost 1.
- From a cell, the agent moves in the triangle map in 3 directions, Left, Right, and Down if the triangle points upwards and Left, Right, and Up if the triangle points downwards, as shown in Figures 2-3.
- Cells marked with ‘1’ in the map represent land, therefore the ferry agent should not cross any of those cells.
- The agent cannot go beyond any map boundaries.
- If a path cannot be found, the agent must inform the user with a failure message.
  
Directions:

![image](https://github.com/reniacorreya/AI_search_marine_navigation_planner/assets/122054528/daf31c05-9a16-4b70-adf2-a1e69cfe169e)

Figure 2: Upwards pointing triangles

![image](https://github.com/reniacorreya/AI_search_marine_navigation_planner/assets/122054528/15074cee-f197-4d04-8374-d87e555038d9)

Figure 3: Downwards pointing triangles

Running Instructions:

-	The source code is placed inside a directory called src/. 
	A1main.java includes the main method and can be compiled using the following command from src/.
	javac A1main.java

-	Followed by the compilation, A1main can be executed using the following command from src/.	
	java A1main <DFS|BFS|AStar|BestF|Bidirectional|IDAStar> <ConfID>

-	<DFS|BFS|AStar|BestF|Bidirectional|IDAStar> is the search algorithm that will be executed. 
	Any one of the search algorithms should be given as an argument. 

-	<ConfID> is the configuration name that determines the map, source and goal of the search. 
	It is defined in the Conf class. 
	Eg: java A1main AStar JCONF00

Advanced Ferry Agent

-	Bidirectional search using BFS (Bidirectional) and Iterative Deepening AStar search (IDAStar) are implemented as part of the advanced ferry agent. 
	The output format for these two searches are different inorder to print more informative details.
