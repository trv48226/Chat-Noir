package chatNoir;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

/**
 * Model class (the backend code for Chat Noir)
 * 
 * @author Trevor and Lukas
 * @Version Spring2020
 */
public class Model {
	/** True if its the owner's turn. Otherwise it is the cats turn */
	private Boolean ownerTurn;
	/** Double array of colors to color the board */
	private Colors[][] board;
	/**
	 * Double boolean array to check if a specific position in the board has been
	 * looked at for the pathToEdge algorithm
	 */
	private Boolean[][] isVisited;
	/** Color object to hold information on the last cat move */
	private Colors lastCatMove;
	/** True if the game has been won. False if it hasn't yet */
	private Boolean gameWon;

	/** A helper object to handle observer pattern behavior */
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/** Default Constructor to start the game */
	public Model() {
		startNewGame();
	}

	/**
	 * Starts a new game by resetting a board with 11 blocked spaces and declaring
	 * where the cat starts, where the edges are, and setting other game variables
	 */
	public void startNewGame() {
		isVisited = new Boolean[11][11];
		board = new Colors[11][11];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				Colors temp = new Colors("White", i, j);
				board[i][j] = temp;
				isVisited[i][j] = false;
			}
		}
		board[5][5] = new Colors("Red", 5, 5);
		lastCatMove = board[5][5];
		setEdge();
		setBlockedSquares();
		ownerTurn = false;
		gameWon = false;
		pcs.firePropertyChange("New Game", null, null);
	}

	/**
	 * Sets 11 random blocked squares. This is used by startNewGames()
	 */
	private void setBlockedSquares() {
		for (int i = 0; i < 11; i++) {
			Random r = new Random();
			int xValue = r.nextInt(11);
			int yValue = r.nextInt(11);
			if (!board[xValue][yValue].getColor().equals("Red") && !board[xValue][yValue].equals("Grey")) {
				board[xValue][yValue].setColor("Grey");
			} else {
				i--;
			}
		}
	}

	/**
	 * Sets the outer most squares to be edge pieces
	 */
	private void setEdge() {
		for (int i = 0; i < board.length; i++) {
			board[0][i].setColor("Edge");
		}
		for (int i = 0; i < board.length; i++) {
			board[10][i].setColor("Edge");
		}
		for (int i = 0; i < board.length; i++) {
			board[i][0].setColor("Edge");
		}
		for (int i = 0; i < board.length; i++) {
			board[i][10].setColor("Edge");
		}
	}

	/**
	 * Method to move the cat or add a blocked square depending on if it is a valid
	 * user move. If it isn't a valid move, the propertyChange will reflect that
	 * 
	 * @param x the x on the board with the value to be moved
	 * @param y the y on the board with the value to be moved
	 */
	public void move(int x, int y) {
		if (gameWon != true) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					isVisited[i][j] = false;
				}
			}
			if (ownerTurn == false) {
				if (isValidCatMove(x, y) == true) {
					ownerTurn = true;
					board[lastCatMove.getxValue()][lastCatMove.getyValue()].setColor("White");
					if (board[x][y].getColor().equals("Edge")) {
						lastCatMove.setColor("Edge");
						gameWon = true;
					} else {
						lastCatMove.setxValue(x);
						lastCatMove.setyValue(y);
					}
					board[x][y].setColor("Red");
					pcs.firePropertyChange("valid", null, null);
				} else
					pcs.firePropertyChange("invalid cat move", null, null);
			} else {
				if (isValidOwnerMove(x, y) == true) {
					ownerTurn = false;
					board[x][y].setColor("Grey");
					pcs.firePropertyChange("valid", null, null);
				} else
					pcs.firePropertyChange("invalid owner move", null, null);
			}
		}
	}

	/**
	 * Checks if the move sent to the method is a valid move or not. If the owner
	 * tries to block a square occupied by the cat or a square that is already
	 * blocked, it is invalid and returns false. Otherwise true
	 * 
	 * @param x the x value on the board of the possible move
	 * @param y the y value on the board of the possible move
	 * @return true if the square is not already blocked and isn't occupied by the
	 *         cat. Else return false.
	 */
	private boolean isValidOwnerMove(int x, int y) {
		if (board[x][y].getColor().equals("Red") || board[x][y].getColor().equals("Grey"))
			return false;
		return true;
	}

	/**
	 * Checks to see if the cat made a valid move. Checks the surrounding 6 squares
	 * to see if that location is a valid move or not
	 * 
	 * @param x the x value on the board of the possible move
	 * @param y the y value on the board of the possible move
	 * @return true if the possible move is within the 6 surrounding blocks and
	 *         isn't grey. Else return false.
	 */
	private boolean isValidCatMove(int x, int y) {
		int catX = lastCatMove.getxValue();
		int catY = lastCatMove.getyValue();
		if (((catX == x && catY == y - 1) || (catX == x - 1 && catY == y) || (catX == x + 1 && catY == y - 1)
				|| (catX == x - 1 && catY == y + 1) || (catX == x + 1 && catY == y) || (catX == x && catY == y + 1))
				&& !board[x][y].getColor().equals("Grey")) {
			return true;
		}
		return false;
	}

	/**
	 * Recursive algorithm that checks if the cat has a path to the edge. Starts at
	 * the cats location and recursively checks the surrounding 6 spots to find an
	 * edge. Updates isVisited to make sure it doesn't do an infinite loop.
	 * 
	 * @param x the x value on the board containing the cat
	 * @param y the y value on the board containing the cat
	 * @return true if a path to the edge was found. Return false otherwise.
	 */
	private boolean pathToEdge(int x, int y) {
		if (board[x][y].getColor().equals("Edge")) {
			isVisited[x][y] = true;
			return true;
		} else if (isVisited[x][y] == true) {
			return false;
		} else if (board[x][y].getColor().equals("Grey")) {
			isVisited[x][y] = true;
			return false;
		} else {
			isVisited[x][y] = true;
			return pathToEdge(x, y - 1) || pathToEdge(x - 1, y) || pathToEdge(x + 1, y - 1) || pathToEdge(x - 1, y + 1)
					|| pathToEdge(x + 1, y) || pathToEdge(x, y + 1);
		}
	}

	/**
	 * Feedback method to show information to the player
	 * 
	 * @return Whose turn it is or declare who won
	 */
	public String getFeedback() {
		if(gameWon == true)
				return "The Cat wins the game";
		else if (pathToEdge(lastCatMove.getxValue(), lastCatMove.getyValue()) == true) {
			if (ownerTurn == false)
				return "It is the Cat's turn";
			else
				return "It is the Owner's turn";
		} else {
			gameWon = true;
			return "The Owner wins the game";
		}
	}

	/**
	 * Board Color method to give the view a useable color
	 * 
	 * @param x the x value of board
	 * @param y the y value of the board
	 * @return a useable color for the view to use
	 */
	public String getBoardColor(int x, int y) {
		if (board[x][y].getColor() == "White") {
			return ("-fx-background-color: white");
		} else if (board[x][y].getColor() == "Grey") {
			return ("-fx-background-color: #A9A9A9");
		} else if (board[x][y].getColor() == "Edge") {
			return ("-fx-background-color: #DCDCDC");
		} else {
			return ("-fx-background-color: #FF0000");
		}
	}

	/**
	 * A listener for the propertChanges
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Getter for the Color object lastCatMove
	 * 
	 * @return information for the last cat move
	 */
	public Colors getLastCatMove() {
		return lastCatMove;
	}

	/**
	 * Getter for title
	 * 
	 * @return the title for the view
	 */
	public String getTitle() {
		return "Chat Noir";
	}
}
