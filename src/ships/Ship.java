package ships;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Character;
import boards.SeaBoard;
import testing.Tester;

public class Ship {

	final int DIMENSION = SeaBoard.getDimension();
	public static final boolean DEBUG = Tester.DEBUG;

	private String Name;

	private int MaxHits;  // Track Hits
	private int HitCount;

	private char shipID;

	// ArrayLists for Start Position and End Position <Row, Col>
	private ArrayList<Integer> startPos = new ArrayList<Integer>(2);
	private ArrayList<Integer> endPos = new ArrayList<Integer>(2);
	private ArrayList<Integer> possibleInputs = new ArrayList<Integer>();

	private boolean isSunk;		// Is the ship sunk
	private boolean isVertical;	// Orientation of the ship

	public boolean getIsSunk(){

		if (DEBUG)
			System.out.println("\n[getIsSunk]");

		return isSunk;
	}

	/*
	 * function - setMaxHits
	 * Sets the number of max hits before ship is sunk
	 */
	public void setMaxHits(int Hits){

		if(DEBUG)
			System.out.println("\n[setMaxHits");

		MaxHits = Hits;

	}

	/*
	 * function - getMaxHits
	 * Returns Max Hits before ship is sunk
	 */
	public int getMaxHits(){

		if(DEBUG)
			System.out.println("\n[getMaxHits]");

		return MaxHits;

	}

	/*
	 * function - getShipID
	 * returns the Character used to Identify the ship
	 */
	public char getShipID(){

		return shipID;
	}

	/*
	 * function getIsVertical
	 * returns the orientation of the ship
	 */
	public boolean getIsVertical(){
		return isVertical;
	}

	/*
	 * function - setOrientation
	 * Sets the orientation of the ship
	 */
	public void setOrientation(){

		if(DEBUG)
			System.out.println("\n[setOrientation]");

		int Menu = -1;

		Scanner input = new Scanner(System.in);

		do{

			System.out.printf("What orientation is the [%s] (%d)? 1: Horizontal 2: Vertical  : ", Name, MaxHits);

			// http://stackoverflow.com/questions/2696063/java-util-scanner-error-handling
			while (!input.hasNextInt()) {
				System.out.println("int, please!");
				input.nextLine();
			}

			Menu = input.nextInt();

		} while(Menu != 1 && Menu != 2); // Error handler

		if (Menu == 1)
			isVertical = false;
		else
			isVertical = true;
	}

	/*
	 * function isHit
	 * checks to see if this ship is hit with given coordinates
	 * if so, adds one to hitCount
	 */
	public boolean isHit(int row, int col, boolean checkShip){

		int startRow, startCol;
		int endRow, endCol;
		boolean hit = false;

		// Get the row and column for start position
		startRow = getStartPos().get(0);
		startCol = getStartPos().get(1);

		// Get the row and column for the end position
		endRow = getEndPos().get(0);
		endCol = getEndPos().get(1);

		if (isVertical){
			// If vertical, column is constant

			for(int r = startRow; r <= endRow; r++){

				/*
				 * if called coordinate is the current coordinate,
				 * there is a hit
				 */
				if (r == row && startCol == col)
					hit = true;

			}


		} else{

			// If horizontal, row is constant

			for(int c = startCol; c <= endCol; c++){

				/*
				 * if called coordinate is the current coordinate,
				 * there is a hit
				 */
				if (c == col && startRow == row)
					hit = true;

			}

		} // end if (isVertical)

		// if the ship is hit and the function is called to hit the ship, add one to hitCount
		if(hit && !checkShip)
			hitShip();

		return hit;
	}

	/*
	 * function hitShip
	 * Adds one to the number of hits for each hit
	 * Checks to see if ship is sunk
	 */
	public void hitShip(){

		if(DEBUG)
			System.out.println("\n[hitShip]");

		HitCount++;

		if(MaxHits == HitCount)
			isSunk = true;

	}

	/*
	 * function - setStartPos<row, col>
	 * Gets the start position
	 */
	public void setStartPos(){

		if(DEBUG)
			System.out.println("\n[setStartPos]");

		int row = -1, col = -1;
		String sRow;

		Scanner input = new Scanner(System.in);

		System.out.println("\nStart Position");

		// get row
		do{

			System.out.print("\nEnter the row letter A-J: ");

			sRow = input.nextLine();

			row = toRowInt(sRow);

		} while (row < 0 || row > DIMENSION - 1); // row is out of bounds of the game board

		// get column
		do{

			System.out.print("\nEnter the column number 1-10: ");

			col = input.nextInt() - 1;

		} while (col < 0 || col > DIMENSION - 1); // column is out of bounds of the game board

		startPos.add(row);
		startPos.add(col);
	}

	/*
	 * function setPossibleInputs<row1, row2> or <col1, col2>
	 * After getting Orientation and Start Position,
	 * sets the possible end position based on the ship size
	 * up or down for vertical, left or right for horizontal
	 * if the end position runs off the board, the end position is not valid,
	 * and is not included
	 */
	private void setPossibleInputs(){

		if (DEBUG)
			System.out.println("[setPossibleInputs]");

		int row, col, startRow = startPos.get(0), startCol = startPos.get(1);

		// clear possible inputs
		possibleInputs.clear();

		if(DEBUG)
			System.out.printf("Number of possibleInputs after clearing: [%d]\n", possibleInputs.size());

		if (isVertical){
			// If vertical, column is constant

			col = startCol;

			if (DEBUG)
				System.out.printf("Possible Row 1: [%d]\n", startRow - this.MaxHits + 2);

			// UP
			if (startRow - this.MaxHits + 1 > 0){ // Ship end row is still on board

				row = startRow - this.MaxHits + 1;

				// error handler for destroyer returning same row
				if (startRow - row == 0)
					row = row - 1;

				if(row >= 0)
					possibleInputs.add(row);

				if (DEBUG)
					System.out.printf("possibleInputs(0): [%d]\n", possibleInputs.get(possibleInputs.size()-1));

			}

			if (DEBUG)
				System.out.printf("Possible Row 2: [%d]\n", startRow + this.MaxHits - 1);

			// down
			if (startRow + this.MaxHits - 1 <= DIMENSION){ // Ship end row is still on board

				row = startRow + this.MaxHits - 1;

				if(row <= DIMENSION)
					possibleInputs.add(row);

				if (DEBUG)
					System.out.printf("possibleInputs(1): [%d]", possibleInputs.get(possibleInputs.size()-1));

			}

		} else{

			// If horizontal, row is constant

			row = startRow;

			if (DEBUG)
				System.out.printf("Possible Row 1: [%d]\n", startCol - MaxHits + 2);

			// Left
			if (startCol - this.MaxHits + 2 > 0){ // Ship end col is still on board

				col = startCol - this.MaxHits + 2;

				if (col > 0)
					possibleInputs.add(col);

				if (DEBUG)
					System.out.printf("possibleInputs(0): [%d]", possibleInputs.get(possibleInputs.size()-1));
			} 

			// right
			if (DEBUG)
				System.out.printf("Possible Col 2: [%d]\n", startCol + this.MaxHits);

			if (startCol + this.MaxHits - 1 <= DIMENSION){ // Ship end col is still on board

				col = startCol + this.MaxHits;

				if (col <= DIMENSION)
					possibleInputs.add(col);

				if (DEBUG)
					System.out.printf("possibleInputs(1): [%d]", possibleInputs.get(possibleInputs.size()-1));
			}

		} // end if (isVertical)

	}

	/*
	 * function - setEndPos<row, col>
	 * sets end position based on orientation of the ship
	 * and input from user
	 */
	public void setEndPos(){


		// endPos.clear()
		if(DEBUG)
			System.out.println("\n[setEndPos]");

		int row = -1, col = -1;
		int startRow, startCol;
		int endRow, endCol;

		String sRow;
		char cRow;

		Scanner input = new Scanner(System.in);

		//Set possible inputs for end position
		setPossibleInputs();

		System.out.println("\nEnd Position");

		// If orientation is vertical, get row and set column
		if (isVertical){
			// get row

			if (DEBUG)
				System.out.printf("possibleInputs.size(): [%d]", possibleInputs.size());

			// Display possible inputs
			for(Integer number : possibleInputs){

				if (DEBUG)
					System.out.printf("Number: [%d] toChar([%d]): [%c]", number, number, toChar(number));

				// Print the row letter
				System.out.print(toChar(number));

				// add dash after first letter
				if (possibleInputs.indexOf(number) == 0 && possibleInputs.size() > 1)
					System.out.print("-");
			}

			//add a colon after the second letter
			System.out.print(": ");

			do{

				sRow = input.nextLine();

				row = toRowInt(sRow);

			} while (row < 0 || row > DIMENSION - 1); // row is out of bounds of the game board

			// set column
			col = startPos.get(1);

			endPos.add(row);
			endPos.add(col);

		} else {
			// If orientation is Horizontal, get column, set row

			for(Integer number : possibleInputs){

				// Print the column Number
				System.out.print(number);

				// if there are two numbers add dash after first number
				if (possibleInputs.indexOf(number) == 0 && possibleInputs.size() > 1)
					System.out.print("-");
			}

			//add a colon after the second letter
			System.out.print(": ");

			// get column
			do{

				col = input.nextInt() - 1;

			} while (col < 0 || col > DIMENSION - 1); // column is out of bounds of the game board

			// set row
			row = startPos.get(0);

			endPos.add(row);
			endPos.add(col);

		} // End if

		// Get the row and column for start position
		startRow = getStartPos().get(0);
		startCol = getStartPos().get(1);

		// Get the row and column for the end position
		endRow = getEndPos().get(0);
		endCol = getEndPos().get(1);

		// if the end position is less than the start position, switch end and start
		if(endRow < startRow || endCol < startCol){

			ArrayList<Integer> tempPos = new ArrayList<Integer>(2);

			// startPos -> tempPos
			tempPos = startPos;

			// endPos -> startPos
			startPos = endPos;

			// tempPos -> endPos
			endPos = tempPos;

			// Get the row and column for start position
			startRow = getStartPos().get(0);
			startCol = getStartPos().get(1);

			// Get the row and column for the end position
			endRow = getEndPos().get(0);
			endCol = getEndPos().get(1);

		}
	}

	public ArrayList<Integer> getStartPos(){

		if(DEBUG)
			System.out.println("\n[getStartPos]");

		return(startPos);
	}

	public ArrayList<Integer> getEndPos(){

		if(DEBUG)
			System.out.println("\n[getEndPos]");

		return(endPos);
	}

	public String getName(){
		return Name;
	}

	/*
	 * function - toRowInt
	 * returns an integer for the row letter
	 */
	public int toRowInt(String sRow){

		if(DEBUG)
			System.out.println("\n[Ship.toRowInt]");

		String input;
		char cRow;
		int ret;

		input = sRow.toLowerCase();

		cRow = sRow.charAt(0);

		cRow = Character.toLowerCase(cRow);

		ret = (int)cRow - 97;

		if (DEBUG)
			// ASCII char
			System.out.println(ret);

		if(ret > (int)'j' - 97){
			System.out.println("Entry is invalid.");
			return -1;
		}
		return ret;
	}

	/*
	 * function toChar
	 * turns row number into a character
	 */
	public static char toChar(int row){

		if(DEBUG)
			System.out.println("\n[toChar]");

		switch (row){

		case 0:
			return 'A';

		case 1:
			return 'B';

		case 2:
			return 'C';

		case 3:
			return 'D';

		case 4:
			return 'E';

		case 5:
			return 'F';

		case 6:
			return 'G';

		case 7:
			return 'H';

		case 8:
			return 'I';

		case 9:
			return 'J';

		default:
			System.out.print("Your entry is invalid");
			return '0';
		}

	}

	/*
	 * function - rePlaceShip
	 * places the ship again if there was a collision
	 */
	public void rePlaceShip(){

		// clear the Arrays after each collision
		startPos.clear();
		endPos.clear();

		setOrientation();

		setStartPos();
		setEndPos();

	}

	// constructor
	public Ship(String shipName, int maxHits, char ID){

		if(DEBUG)
			System.out.println("\n[Ship Constructor]");

		Name = shipName;
		shipID = ID;
		isSunk = false;
		setMaxHits(maxHits);

		// clear the Arrays after each collision
		startPos.clear();
		endPos.clear();

		setOrientation();

		setStartPos();
		setEndPos();

	}

	// Default Constructor
	public Ship(){

	}

}
