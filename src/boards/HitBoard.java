package boards;

import battleship.BattleShip;
import battleship.Player;
import boards.SeaBoard;
import testing.Tester;
import ships.Ship;

public class HitBoard {
	
	final static int DIMENSION = SeaBoard.getDimension();
	
	private static final boolean DEBUG = Tester.DEBUG;
	
	private enum hitResult {HIT, MISS};
	
	private  hitResult[][] hitBoard;
	
	/*
	 * function createHitBoard
	 * creates the Hit Board used to store the hits and misses called by
	 * the player 
	 */
	private  void createHitBoard(){
		
		// Create hitBoard
		hitBoard = new hitResult[DIMENSION][DIMENSION];
	}
	
	public  boolean callHit(int row, int col, Player p){
		
		Ship s = new Ship();
		
		// if getShipAt returns a ship, the ship was hit
		s = p.seaBoard.getShipAt(row, col, false);
		
		if (s != null){ // hit
			
			// place hit on hitBoard
			hitBoard[row][col] = hitResult.HIT;
			
			// find if the ship was sunk; if the Battleship was sunk
			// the game is over
			if(s.getIsSunk()){
				
				// check for battleship
				if (s.getName() == "Battleship"){
					System.out.printf("You WON! You sunk [%s]'s Battleship\n", BattleShip.getNextPlayer());
					BattleShip.state = BattleShip.GameState.OVER;
				
				}else{
					
					// if the Battleship was not sunk, output the ship that was sunk
					System.out.printf("You sunk [%s]'s [%s].\n", BattleShip.getNextPlayer(), s.getName());
				}
			// if the ship was not sunk, output the ship that was hit
			} else {
				
				System.out.printf("You hit [%s]'s [%s].\n", BattleShip.getNextPlayer(), s.getName());
			
			} // end if(s.getIsSunk())
			
			printBoard();
			
			return true;
		
		} else { // place a miss on hitBoard
			
			hitBoard[row][col] = hitResult.MISS;
			
			printBoard();
			
			return false;
		}
		
	}
	
	/*
	 * function - printBoard
	 * Prints Sea board with all filled cells
	 */
	public  void printBoard(){
		
		if (DEBUG)
			System.out.println("[printBoard]");

		System.out.println();

		// print number row
		System.out.println(" |1|2|3|4|5|6|7|8|9|10");

		for (int row = 0; row < DIMENSION * 2; row++){

			if (row % 2 == 0){

				if (row >= 0){

					for (int col = -1; col < DIMENSION * 2; col++){

						if (col == -1){

							System.out.print(SeaBoard.toChar(row) + "|");

						}else if(col % 2 == 0){ // if even print ship
							
							if(DEBUG){
								System.out.printf(" row: [%d] col: [%d]", row, col);
								System.out.printf("row/2: [%d] col/2: [%d]", row/2, (col-1)/2);
							}
							
							// need to round down
							if (hitBoard[row/2][col/2] == hitResult.HIT)
								System.out.print("H");
							else
								System.out.print("M");

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
	
	public static void printNewBoard(){
		
		SeaBoard.printNewBoard();
	}
	
	/*
	 * function - toRowInt
	 * returns an integer for the row letter
	 */
	public int toRowInt(String sRow){

		if(DEBUG)
			System.out.println("\n[HitBoard.toRowInt]");

		String input;
		char cRow;
		int ret;

		input = sRow.toLowerCase();

		cRow = sRow.charAt(0);

		ret = (int)cRow - 97;

		// ASCII char
		System.out.println((int)'a');

		if(ret > (int)'j' - 97){
			System.out.println("Entry is invalid.");
			return -1;
		}
		return ret;
	}
	
	// Default constructor
	public  HitBoard(){
		
		if(DEBUG)
			System.out.println("\n[HitBoard()]");

		createHitBoard();
		
	}
	
}
