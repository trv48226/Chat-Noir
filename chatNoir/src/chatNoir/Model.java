package chatNoir;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

public class Model {

	private Boolean ownerTurn;
	private Colors[][] board;
	private Boolean isVisited;
	private Colors getLastCatMove;
	/** A helper object to handle observer pattern behavior */
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Model() {
		board = new Colors[11][11];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				Colors temp = new Colors("white", i, j);
				board[i][j] = temp;
			}
		}
		board[5][5] = new Colors("Red", 5, 5);
		getLastCatMove = board[5][5];
		setEdge();
		setBlockedSquares();
		ownerTurn = false;
	}

	private void setBlockedSquares() {
		System.out.println();
		for (int i = 0; i < 11; i++) {
			Random r = new Random();
			int xValue = r.nextInt(10 - 0) + 0;
			int yValue = r.nextInt(10 - 0) + 0;
			if (board[xValue][yValue].getColor().equals("Red") && board[xValue][yValue].equals("Grey"))
				board[xValue][yValue].setColor("Grey");
		}
	}

	private void setEdge() {
		for (int i = 0; i < board.length; i++) {
			board[0][i].setColor("edge");
		}
		for (int i = 0; i < board.length; i++) {
			board[10][i].setColor("edge");
		}
		for (int i = 0; i < board.length; i++) {
			board[i][0].setColor("edge");
		}
		for (int i = 0; i < board.length; i++) {
			board[i][10].setColor("edge");
		}
	}

	public Colors getlastCatMove() {
		return null;
	}

	public void move(int x, int y) {
		if (ownerTurn == false) {
			if (isValidCatMove(x, y) == true) {
				board[x][y].setColor("Red");
				board[getLastCatMove.getxValue()][getLastCatMove.getyValue()].setColor("White");
				getLastCatMove.setxValue(x);
				getLastCatMove.setyValue(y);
				ownerTurn = true;
				pcs.firePropertyChange("valid", null, null);
			} else
				pcs.firePropertyChange("invalid", null, null);
		} else {
			if (isValidOwnerMove(x, y) == true) {
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
		int catX = getLastCatMove.getxValue();
		int catY = getLastCatMove.getyValue();
		if ((catX == x && catY == y - 1) || (catX == x - 1 && catY == y) || (catX == x + 1 && catY == y - 1)
				|| (catX == x - 1 && catY == y + 1) || (catX == x + 1 && catY == y)
				|| (catX == x && catY == y + 1) && board[x][y].getColor().equals("Grey")) {
			return true;
		}
		return false;
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
