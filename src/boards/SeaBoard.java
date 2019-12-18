package boards;

import java.util.ArrayList;

import ships.Ship;
import testing.Tester;

public class SeaBoard {

	final static int DIMENSION = 10;
	private static final boolean DEBUG = Tester.DEBUG;

	private int playerNumber;

	public enum occupiedSpace {PLACED, HIT, MISS}

	public occupiedSpace[][] shipBoard;

	// ArrayList to store the player's ships
	public ArrayList<Ship> shipList = new ArrayList<Ship>(0);

	public static int getDimension(){

		if(DEBUG)
			System.out.println("\n[getDimension]");

		return DIMENSION;
	}

	public int getPlayerNumber(){
		return playerNumber;
	}

	/*
	 * function - createGameBoard 
	 * creates new board for hits and for ships
	 */
	public void createShipBoard(){

		if(DEBUG)
			System.out.println("\n[createShipBoard]");

		// Create gameBoard
		shipBoard = new occupiedSpace[DIMENSION][DIMENSION];
	}

	/*
	 * function - placeShips
	 * declares and places each ship on the Sea Board
	 */
	public void placeShips(){

		if(DEBUG)
			System.out.println("\n[placeShips]");

		printNewBoard();

		// Create ships

		// Carrier
		Ship carrier = new Ship("Carrier", 5, 'A');

		addShip(carrier);

		printBoard();


		// Battleship
		Ship battleship = new Ship("Battleship", 4, 'B');

		addShip(battleship);

		printBoard();


		// Cruiser
		Ship cruiser = new Ship("Cruiser", 3, 'C');

		addShip(cruiser);

		printBoard();


		// Submarine
		Ship submarine = new Ship("Sbumarine", 3, 'S');

		addShip(submarine);

		printBoard();


		// Destroyer
		Ship destroyer = new Ship("Destroyer", 2, 'D');

		addShip(destroyer);

		printBoard();

	}

	/*
	 * function - addShip
	 * adds the ship to the ship list
	 * validates the ship
	 * then places the ship
	 */
	private void addShip(Ship s){

		shipList.add(s);

		validateShip(s);

		placeNewShip(s);
	}


	/*
	 * function - validateShip
	 * if there is a collision with another ship,
	 * the function asks the user to place the ship again
	 */
	private void validateShip(Ship s){

		boolean collision = false;

		int collisionCount = 0;

		do{
			// reset collision
			collision = false;

			collision = isCollision(s);

			if (DEBUG)
				System.out.printf("Collision: [%b]\n", collision);

			if (collision){
				System.out.println("\nYour ship had a collision.\n");
				collisionCount++;

				// re-place ship if there was a collision
				s.rePlaceShip();
			}

			if (DEBUG)
				System.out.printf("Collision Count: [%d]", collisionCount);

		} while (collision);

		if(DEBUG){
			System.out.printf("Collision: [%b]\n", collision);
			System.out.println("Placing Ship");
		}
	}

	/*
	 * function - is collision
	 * searches through the Sea Board to see if there is a collision
	 */
	public boolean isCollision(Ship s){

		if(DEBUG)
			System.out.println("\n[isCollision]");

		int startRow, startCol;
		int endRow, endCol;
		int row, col;

		// Get the row and column for start position
		startRow = s.getStartPos().get(0);
		startCol = s.getStartPos().get(1);

		// Get the row and column for the end position
		endRow = s.getEndPos().get(0);
		endCol = s.getEndPos().get(1);

		if (DEBUG)
			System.out.printf("startRow: [%d] startCol: [%d] endRow: [%d] endCol: [%d]\n" ,
					startRow, startCol, endRow, endCol);

		if(s.getIsVertical()){

			// column is constant
			for(row = startRow; row <= endRow; row++){

				if(DEBUG)
					System.out.printf("Checking Vertical Collision: Row: [%d] Col: [%d]\n", row, startCol);

				if (isOccupied(row, startCol)){

					if (DEBUG)
						System.out.println("There has been a collision at (" + row + ", " + startCol + ")");

					return true;

				}

			}

			//returns false if no collision is detected

			if (DEBUG)
				System.out.println("That is a valid move, placing ship");


		} else {
			// row is constant
			for(col = startCol; col <= endCol; col++){

				if(DEBUG)
					System.out.printf("Row: [%d] Col: [%d]", startRow, col);

				if (isOccupied(startRow, col)){

					if (DEBUG)
						System.out.println("There has been a collision");

					return true;

				}

			}

			// return false if no collision is detected

			if (DEBUG)
				System.out.println("That is a valid move, placing ship");

		}

		return false;

	}

	/*
	 * function - placeNewShip
	 * if there are no problems placing the ship
	 * place the ship on the Sea Board
	 */
	public void placeNewShip(Ship s){

		if(DEBUG)
			System.out.println("\n[placeShip]");

		int startRow, startCol;
		int endRow, endCol;
		int row, col;

		// Get the row and column for start position
		startRow = s.getStartPos().get(0);
		startCol = s.getStartPos().get(1);

		// Get the row and column for the end position
		endRow = s.getEndPos().get(0);
		endCol = s.getEndPos().get(1);

		// if there is no collision, start placing ship
		if (!isCollision(s)){

			if(s.getIsVertical()){

				if (DEBUG)
					System.out.printf("isVertical: [%b]\n", s.getIsVertical());

				// column is constant
				for(row = startRow; row <= endRow; row++){

					if(DEBUG){
						System.out.printf("startRow: [%d] col: [%d]\n", row, startCol);
					}

					// mark ship as placed on Sea Board
					shipBoard[row][startCol] = SeaBoard.occupiedSpace.PLACED;

					if(DEBUG)
						System.out.printf("SeaBoard.shipBoard[row][startCol]: [%s]\n", shipBoard[row][startCol].toString());

				}

			} else {

				if (DEBUG)
					System.out.printf("isVertical: [%b]\n", s.getIsVertical());

				// row is constant
				for(col = startCol; col <= endCol; col++){

					if(DEBUG){
						System.out.printf("row: [%d] col: [%d]\n", startRow, col);
					}

					// mark ship as placed on Sea Board
					shipBoard[startRow][col] = SeaBoard.occupiedSpace.PLACED;

					if(DEBUG)
						System.out.printf("SeaBoard.shipBoard[startRow][col]: [%s]\n", shipBoard[startRow][col].toString());

				}

			}
		}

	}

	/*
	 * function - isOccupied
	 * searches Sea Board to see if cell is occupied
	 */
	public  boolean isOccupied(int row, int col){

		if(DEBUG)
			System.out.println("\n[isOccupied]");

		if (shipBoard[row][col] != null){

			if(DEBUG)
				System.out.println("That space is occupied");

			return true;
		}else{
			if(DEBUG)
				System.out.println("That space is empty");

			return false;
		}

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

		case 2:
			return 'B';

		case 4:
			return 'C';

		case 6:
			return 'D';

		case 8:
			return 'E';

		case 10:
			return 'F';

		case 12:
			return 'G';

		case 14:
			return 'H';

		case 16:
			return 'I';

		case 18:
			return 'J';

		default:
			System.out.print("Your entry is invalid");
			return '0';
		}

	}

	/*
	 * function - printNewBoard
	 * Prints empty game board
	 */
	public static void printNewBoard(){

		if (DEBUG)
			System.out.println("[printNewBoard]");

		// print number row
		System.out.print(" |1|2|3|4|5|6|7|8|9|10\n");

		for (int row = 0; row < DIMENSION * 2; row++){

			if (row % 2 == 0){ // user output row

				if (row >= 0){ 
					for (int col = 0; col < DIMENSION * 2; col++){

						if (col == 0){

							if (DEBUG)
								System.out.printf("\nColumn Number: [%d]\n", col);

							System.out.print(toChar(row));

						}else if(col % 2 == 0){ // if even print ship

							System.out.print(" ");

						} else
							System.out.print("|"); // if odd print vertical separator

					} // End column
				}

			} else { // grid row

				System.out.println();

				for(int n = 0; n < DIMENSION * 2 + 2; n++)
					System.out.print("-"); // print horizontal separator

				System.out.println();
			}
		} // End row

		System.out.println("\n");
	}

	/*
	 * function - printBoard
	 * Prints Sea board with all filled cells
	 */
	public void printBoard(){

		if (DEBUG)
			System.out.println("[printBoard]");

		char shipID;

		Ship s = new Ship();

		System.out.println();

		// print number row
		System.out.println(" |1|2|3|4|5|6|7|8|9|10");

		for (int row = 0; row < DIMENSION * 2; row++){

			if (row % 2 == 0){

				if (row >= 0){

					for (int col = -1; col < DIMENSION * 2; col++){

						if (col == -1){

							System.out.print(toChar(row) + "|");

						}else if(col % 2 == 0){ // if even print ship

							if(DEBUG){
								System.out.printf(" row: [%d] col: [%d]", row, col);
								System.out.printf("row/2: [%d] col/2: [%d]", row/2, (col-1)/2);
							}

							if (shipBoard[row/2][col/2] == occupiedSpace.HIT){


								System.out.print("X");

								

							}else if (shipBoard[row/2][col/2] == occupiedSpace.PLACED){


								// get the ship at the given coordinate
								s = getShipAt(row/2,col/2, true);


								// get the Ship ID for the given ship
								shipID = s.getShipID();

								System.out.print(shipID);

								

							}else if (shipBoard[row/2][col/2] == occupiedSpace.MISS){

								System.out.print("M");

							}else{
								System.out.print(" ");
							}
						} else
							System.out.print("|"); // if odd print vertical separator

					} // End col
				}
			} else {
				System.out.println();

				for(int n = 0; n < DIMENSION * 2 + 2; n++)
					System.out.print("-"); // print horizontal separator

				System.out.println();
			}
		} // End row

		System.out.println("\n");
	}

	/*
	 * function - getShipAt
	 * returns the ship at the given coordinates
	 */
	public  Ship getShipAt(int row, int col, boolean checkShip){

		for(Ship s : shipList){

			// Finds if the current ship is hit 
			if (s.isHit(row, col, checkShip))
				return s;
		}

		return null;
	}

	// Default constructor
	public SeaBoard(){

		if(DEBUG)
			System.out.println("\n[SeaBoard()]");

		createShipBoard();

		placeShips();
	}

}
