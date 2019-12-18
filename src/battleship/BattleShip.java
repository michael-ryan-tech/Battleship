package battleship;

import java.util.ArrayList;
import testing.Tester;

public class BattleShip {

	public enum GameState {INITIALIZING, PLAYING, OVER};

	private static final boolean DEBUG = Tester.DEBUG;

	public static GameState state;

	public static Player currentPlayer;
	private static Player nextPlayer;

	static ArrayList<Player> playerList;

	private static int turnCount = 0;

	private static void initialize(){

		if(DEBUG)
			System.out.println("[initialize]");

		state = GameState.INITIALIZING;
		playerList = new ArrayList<Player>();

		Player playerOne = new Player("Player One");
		
		// http://stackoverflow.com/questions/2979383/java-clear-the-console
		
		// Clear the Screen to hide the previous player's ships
		Tester.clearConsole();
		
		Player playerTwo = new Player("Player Two");

		// http://stackoverflow.com/questions/15972196/i-need-a-short-code-to-include-in-my-program-about-the-system-to-determine-rando

		// get first player
		if(new java.util.Random().nextBoolean()){
			playerList.add(playerOne);
			playerList.add(playerTwo);
		}else{
			playerList.add(playerTwo);
			playerList.add(playerOne);
		}

	}

	public static String getNextPlayer(){
		return nextPlayer.getPlayerName();
	}

	private static void runGame() {
		// TODO Auto-generated method stub

		if(DEBUG)
			System.out.println("[runGame]");

		state = GameState.PLAYING;

		// set the current player

		if (turnCount % 2 == 0){ // First Player

			currentPlayer = playerList.get(0);
			nextPlayer = playerList.get(1);

		}else{ // Second Player

			currentPlayer = playerList.get(1);
			nextPlayer = playerList.get(0);
		}
		
		// Clear the Screen to hide the previous player's ships
		Tester.clearConsole();
		
		// shows the current player's game board
		currentPlayer.seaBoard.printBoard();

		// get the hit call from the current player
		currentPlayer.getNextHit(currentPlayer);

		// if the game is not over, change turn
		if (state != GameState.OVER){
			turnCount++;
		}

	}

	// Default constructor
	public BattleShip(){

		initialize();

		do {
			runGame();
		}
		while(state == GameState.PLAYING);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new BattleShip();
		
	}

}
