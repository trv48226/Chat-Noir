package chatNoir;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;
//Model
public class Model {

	private Boolean ownerTurn;
	private Colors[][] board;
	private Boolean[][] isVisited;
	private Colors lastCatMove;
	
	/** A helper object to handle observer pattern behavior */
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Model() {
		startNewGame();
	}

	public void setBlockedSquares() {
		for (int i = 0; i < 11; i++) {
			Random r = new Random();
			int xValue = r.nextInt(11);
			int yValue = r.nextInt(11);
			if (!board[xValue][yValue].getColor().equals("Red") && !board[xValue][yValue].equals("Grey")) {
				board[xValue][yValue].setColor("Grey");
			}else {
				i--;
			}
		}
	}

	public void setEdge() {
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

	public Colors getLastCatMove() {
		return lastCatMove;
	}

	public void move(int x, int y) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				isVisited[i][j] = false;
			}
		}	
		if (ownerTurn == false) {
			if (isValidCatMove(x, y) == true) {
				ownerTurn = true;
				if(board[x][y].getColor().equals("Edge")) {
					board[lastCatMove.getxValue()][lastCatMove.getyValue()].setColor("Edge");
				}
				board[x][y].setColor("Red");
				board[lastCatMove.getxValue()][lastCatMove.getyValue()].setColor("White");
				lastCatMove.setxValue(x);
				lastCatMove.setyValue(y);
				pcs.firePropertyChange("valid", null, null);
			} else
				pcs.firePropertyChange("invalid", null, null);
		} else {
			if (isValidOwnerMove(x, y) == true) {
				ownerTurn = false;
				board[x][y].setColor("Grey");
				pcs.firePropertyChange("valid", null, null);
			} else
				pcs.firePropertyChange("invalid", null, null);
		}
	}

	private boolean isValidOwnerMove(int x, int y) {
		if (board[x][y].getColor().equals("Red") || board[x][y].getColor().equals("Grey"))
			return false;
		return true;
	}

	private boolean isValidCatMove(int x, int y) {
		int catX = lastCatMove.getxValue();
		int catY = lastCatMove.getyValue();
		if (((catX == x && catY == y - 1) || (catX == x - 1 && catY == y) || (catX == x + 1 && catY == y - 1)
				|| (catX == x - 1 && catY == y + 1) || (catX == x + 1 && catY == y)
				|| (catX == x && catY == y + 1)) && !board[x][y].getColor().equals("Grey")) {
			return true;
		}
		return false;
	}
	
	private boolean pathToEdge(int x, int y) {
		if (board[x][y].getColor().equals("Edge")) {
			isVisited[x][y] = true;
			return true;
		}else if (isVisited[x][y] == true) {
		     return false;
		}else if (board[x][y].getColor().equals("Grey")) {
			isVisited[x][y] = true;
			return false;
		}else {
			isVisited[x][y] = true;
			return pathToEdge(x, y - 1) || pathToEdge(x - 1, y) || pathToEdge(x + 1, y - 1) || pathToEdge(x - 1, y + 1)
					|| pathToEdge(x + 1, y) || pathToEdge(x, y + 1);
		}
	}

	public String getFeedback() {
		if (pathToEdge(lastCatMove.getxValue(), lastCatMove.getyValue()) == true) {
			if (lastCatMove.getColor().equals("Edge"))
				return "The Cat wins the game";
			else if (ownerTurn == false)
				return "It is the Cat's turn";
			else
				return "It is the Owner's turn";
		} else
			return "The Owner wins the game";
	}

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
		pcs.firePropertyChange("New Game", null, null);
	}
	
	public String getBoardColor(int i, int j) {
		if(board[i][j].getColor() == "White") {
			return ("-fx-background-color: white");
		}else if(board[i][j].getColor() == "Grey") {
			return("-fx-background-color: #A9A9A9");
		}else if(board[i][j].getColor() == "Edge"){
			return("-fx-background-color: #DCDCDC");
		}else {
			return("-fx-background-color: #FF0000");
		}
	}

	/**
	 * Don't forget to create a way for Observers to subscribe to this
	 *
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public String getTitle() {
		return "Chat Noir";
	}
}
