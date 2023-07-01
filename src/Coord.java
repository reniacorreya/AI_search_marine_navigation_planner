/********************Starter Code
 * 
 * This represents the coordinate data structure (row, column)
 * and prints the required output
 *
 *
 * @author at258
 *   
 */

public class Coord {
	private int r;//row
	private int c;//column
	private byte direction; //dir ∈ {0, 1} , 0 if upward pointing triangle and 1 if downward pointing triangle.

	public Coord(int row,int column) {
		r=row;
		c=column;
		if(r%2 == c%2){		//Upward pointing triangle if row and column coordinates are both odd or both even at the same time. 
			direction = 0;
		}
		else{				//Downward pointing triangle otherwise 
			direction = 1;
		}
	}

	public String toString() {
		return "("+r+","+c+")";
	}

	public int getR() {
		return r;
	}
	public int getC() {
		return c;
	}

	public short getDirection(){
		return direction;
	}

	@Override
	public boolean equals(Object o) {

		Coord coord=(Coord) o;
		if(coord.r==r && coord.c==c) {
			return true;
		}
		return false; 

	}

}
