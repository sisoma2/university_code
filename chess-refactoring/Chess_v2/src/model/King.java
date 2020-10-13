package model;

/**
 * The Class King.
 */
public class King extends Piece{
	
	/**
	 * Instantiates a new king.
	 *
	 * @param row the row
	 * @param column the column
	 * @param color the color
	 */
	public King(int row, int column, EnumColor color){
		super(row,column,color);
		this.name="King";
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#rules(int, int, model.Piece, model.Piece[])
	 */
	public boolean rules(int y, int x, Piece pieceInPos, Piece pieces[]) {
		EnumColor colorP;
		int row = getRow();
		int column = getColumn();
		boolean canmove = false;
		
		y = y / 50 + 1;
		x = x / 50 + 1;
		if (column == y - 1
				&& row == x - 1)
			canmove = true;
		else if (column == y
				&& row == x - 1)
			canmove = true;
		else if (column == y + 1
				&& row == x - 1)
			canmove = true;
		else if (column == y - 1
				&& row == x)
			canmove = true;
		else if (column == y + 1
				&& row == x)
			canmove = true;
		else if (column == y - 1
				&& row == x + 1)
			canmove = true;
		else if (column == y
				&& row == x + 1)
			canmove = true;
		else if (column == y + 1
				&& row == x + 1)
			canmove = true;
		// conditions for castling - first move, no pieces in the way,
		// no squares under attack
		if (canmove) {
			if (pieceInPos!=null) {
				colorP = pieceInPos.getColor();
				if (colorP.equals(getColor()))
					canmove = false;
			}
		}
		
		if (canmove)
			setFirstMove(false);
		return canmove;
	}

	/**
	 * Coor. 
	 *
	 * @param tempCoor The temp coor
	 * @param movecase The movecase
	 * @return true if can do that move, else false.
	 */
	public boolean coor(int tempCoor[], int movecase) {
		boolean moveok = false;
		switch (movecase) {
			case 0:
				tempCoor[0]--;
				tempCoor[1]--;
				break;
			case 1:
				tempCoor[0]--;
				break;
			case 2:
				tempCoor[0]++;
				tempCoor[1]--;
				break;
			case 3:
				tempCoor[1]--;
				break;
			case 4:
				tempCoor[1]++;
				break;
			case 5:
				tempCoor[0]--;
				tempCoor[1]++;
				break;
			case 6:
				tempCoor[0]++;
				break;
			case 7:
				tempCoor[0]++;
				tempCoor[1]++;
				break;
		}
		if (tempCoor[0] >= 0 && tempCoor[0] < 9)
			if (tempCoor[1] >= 0 && tempCoor[1] < 9)
				moveok = true;
		return moveok;
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#squaresAttacked(int[], int, int, model.Piece[], int[][])
	 */
	public void squaresAttacked(int[] tempCoor, int oldx, int oldy, Piece[] pieces, int[][] squares) {
		int x,y;
		for (int k = 0; k < 8; k++) {
			if (coor(tempCoor, k)) {
				x = tempCoor[0];
				y = tempCoor[1];
				
				markSquares(squares, x, y);
				
			}
			tempCoor[0] = oldx;
			tempCoor[1] = oldy;
		}
	}
	
	/**
	 * Mark squares.
	 * Method that marks the squares depending on the color of the piece.
	 *
	 * @param squares the squares
	 * @param x the x
	 * @param y the y
	 */
	public void markSquares(int[][] squares, int x, int y){
		if(getColor().equals(EnumColor.WHITE)){
			if (squares[x][y] == 2)
				squares[x][y] += 1;
			else
				squares[x][y] = 1;
		}else{
			if (squares[x][y] == 1)
				squares[x][y] += 2;
			else
				squares[x][y] = 2;
		}
	}

	/* (non-Javadoc)
	 * @see model.Piece#check(model.Piece, model.Piece[])
	 */
	public boolean check(Piece king, Piece[] pieces) {
		return false;
	}
}
