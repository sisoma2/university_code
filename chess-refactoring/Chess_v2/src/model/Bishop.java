package model;

/**
 * The Class Bishop.
 */
public class Bishop extends Piece{
	
	/**
	 * Instantiates a new bishop.
	 *
	 * @param row the row
	 * @param column the column
	 * @param color the color
	 */
	public Bishop(int row, int column, EnumColor color){
		super(row,column,color);
		this.name="Bishop";
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#rules(int, int, model.Piece, model.Piece[])
	 */
	public boolean rules(int y, int x, Piece pieceInPos, Piece pieces[]) {
		boolean canmove = false;
		
		canmove = diagonalMove(x, y, pieceInPos, pieces);
			
		return canmove;
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#squaresAttacked(int[], int, int, model.Piece[], int[][])
	 */
	public void squaresAttacked(int[] tempCoor, int oldx, int oldy, Piece[] pieces, int[][] squares) {
		diagonalSquaresAttacked(tempCoor, oldx, oldy, pieces, squares);
	}

	/* (non-Javadoc)
	 * @see model.Piece#check(model.Piece, model.Piece[])
	 */
	public boolean check(Piece king, Piece[] pieces) {	
		return diagonalCheck(king, pieces);
	}
}
