package battleship;

import java.util.Scanner;

import boards.HitBoard;
import boards.SeaBoard;
import ships.Ship;
import testing.Tester;

/*
 * Maintains player information for the game BattleShip
 * 	
 */
public class Player {

	private static final boolean DEBUG = Tester.DEBUG;

	public  HitBoard hitBoard;
	public  SeaBoard seaBoard;

	private  String name;
	private  String playerNumber;

	private void createHitBoard() {
		// TODO Auto-generated method stub

		if(DEBUG)
			System.out.println("[Player.createHitBoard]");

		hitBoard = new HitBoard();

	}

	private void createSeaBoard(){

		if (DEBUG)
			System.out.println("[Player.createSeaBoard]");

		seaBoard = new SeaBoard();

		if(DEBUG)
			System.out.printf("Sea Board Number: [%d]",seaBoard.getPlayerNumber());
	}

	private void setPlayerName(){

		if (DEBUG)
			System.out.println("[setPlayerName]");

		Scanner input = new Scanner(System.in);

		System.out.printf("[%s], enter your name:\n", playerNumber);

		name = input.nextLine();

		if(DEBUG)
			System.out.printf("[%s]'s name: [%s]\n", playerNumber, name);
	}

	public String getPlayerName(){

		if (DEBUG)
			System.out.println("[getPlayerName]");

		return name;
	}

	public SeaBoard getSeaBoard() {
		return seaBoard;
	}

	/*
	 * function - getNextHit
	 * gets the next guess from the player
	 */
	public void getNextHit(Player p){

		int  row, col;
		String sRow;
		boolean hit = false;

		Scanner input = new Scanner(System.in);

		if (DEBUG)
			System.out.println("[getNextHit]");

		do{

			// get guessed row
			System.out.printf("[%s], enter your guessed Row ([%c]-[%c]):\n", name, Ship.toChar(0), Ship.toChar(SeaBoard.getDimension()-1));

			sRow = input.nextLine();

			row = hitBoard.toRowInt(sRow);

			// get guessed column
			System.out.printf("Enter your guessed Column (1-[%d])\n", SeaBoard.getDimension());

			col = input.nextInt() - 1;

			// set guessed call
			hit = hitBoard.callHit(row, col, p);

		} while (hit == true);
	}

	// Default Constructor
	public Player(String playerNum){

		if (DEBUG)
			System.out.println("[Player]");

		playerNumber = playerNum;

		setPlayerName();

		createHitBoard();
		createSeaBoard();

	}

}
