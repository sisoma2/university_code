package model;

/**
 * The Class Knight.
 */
public class Knight extends Piece{
	
	/**
	 * Instantiates a new knight.
	 *
	 * @param row the row
	 * @param column the column
	 * @param color the color
	 */
	public Knight(int row, int column, EnumColor color){
		super(row,column,color);
		this.name="Knight";
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#rules(int, int, model.Piece, model.Piece[])
	 */
	public boolean rules(int y, int x, Piece pieceInPos, Piece pieces[]){
		boolean canmove = false;
		int row = getRow(), column = getColumn();
		EnumColor color2;

		y = y / 50 + 1;
		x = x / 50 + 1;
		if (((row == x - 1) || (row == x + 1))
				&& ((column == y + 2) || (column == y - 2)))
			canmove = true;
		if ((row == x - 2 || row == x + 2)
				&& ((column == y + 1) || (column == y - 1)))
			canmove = true;
		if (canmove) {
			if (pieceInPos!=null) {
				color2 = pieceInPos.getColor();
				if (color2.equals(getColor()))
					canmove = false;
			}
		}
		
		if(pieceInPos!=null)
			if (pieceInPos.getName().equals("King"))
				canmove = false;
		
		return canmove;
	}

	/**
	 * Coor.
	 *
	 * @param tempCoor the temp coor
	 * @param movecase the movecase
	 * @return true, if successful
	 */
	public boolean coor(int tempCoor[], int movecase) {
		boolean moveok = false;
		switch (movecase) {
			case 0:
				tempCoor[0] -= 2;
				tempCoor[1] += 1;
				break;
			case 1:
				tempCoor[0] -= 1;
				tempCoor[1] += 2;
				break;
			case 2:
				tempCoor[0] += 1;
				tempCoor[1] += 2;
				break;
			case 3:
				tempCoor[0] += 2;
				tempCoor[1] += 1;
				break;
			case 4:
				tempCoor[0] += 2;
				tempCoor[1] -= 1;
				break;
			case 5:
				tempCoor[0] += 1;
				tempCoor[1] -= 2;
				break;
			case 6:
				tempCoor[0] -= 1;
				tempCoor[1] -= 2;
				break;
			case 7:
				tempCoor[0] -= 2;
				tempCoor[1] -= 1;
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
		int x, y;
		for (int j = 0; j < 8; j++) {
			coor(tempCoor, j);
			x = tempCoor[0];
			y = tempCoor[1];
			if (tempCoor[0] < 8 && tempCoor[0] >= 0) {
				if (tempCoor[1] < 8 && tempCoor[1] >= 0) {
					markSquares(squares, x, y);
					
				}
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
		boolean checked=false;
		
		if ((king.getRow() == getRow() - 1 || king.getRow() == getRow() + 1)
				&& (king.getColumn() == getColumn() - 2 || king.getColumn() == getColumn() + 2)) {
			checked = true;
		}
		if ((king.getRow() == getRow() - 2 || king.getRow() == getRow() + 2)
				&& (king.getColumn() == getColumn() - 1 || king.getColumn() == getColumn() + 1)) {
			checked = true;
		}
		
		
		return checked;
	}
}
