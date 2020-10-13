package model;


/**
 * The Class Queen.
 */
public class Queen extends Piece{
	
	/**
	 * Instantiates a new queen.
	 *
	 * @param row the row
	 * @param column the column
	 * @param color the color
	 */
	public Queen(int row, int column, EnumColor color){
		super(row,column,color);
		this.name="Queen";
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#rules(int, int, model.Piece, model.Piece[])
	 */
	public boolean rules(int y, int x, Piece pieceInPos, Piece pieces[]) {
		boolean canmove = false;
		
		canmove = sideMove(x, y, pieceInPos, pieces) ^ diagonalMove(x, y, pieceInPos, pieces);
		
		return canmove;
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#squaresAttacked(int[], int, int, model.Piece[], int[][])
	 */
	public void squaresAttacked(int[] tempCoor, int oldx, int oldy, Piece[] pieces, int[][] squares) {
		tempCoor[0] = getRow();
		tempCoor[1] = getColumn();
		tempCoor[0]--;
		tempCoor[1]--;
		oldx = tempCoor[0];
		oldy = tempCoor[1];
		
		diagonalSquaresAttacked(tempCoor, oldx, oldy, pieces, squares);
		
		tempCoor[0] = getRow();
		tempCoor[1] = getColumn();
		tempCoor[0]--;
		tempCoor[1]--;
		oldx = tempCoor[0];
		oldy = tempCoor[1];
		
		sideSquaresAttacked(tempCoor, oldx, oldy, pieces, squares);
	}

	/* (non-Javadoc)
	 * @see model.Piece#check(model.Piece, model.Piece[])
	 */
	public boolean check(Piece king, Piece[] pieces) {
		boolean check=false;
		
		check = diagonalCheck(king, pieces) ^ sideCheck(king, pieces);
		
		return check;
	}
	
	
}
