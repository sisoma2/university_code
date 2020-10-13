package model;

/**
 * The Class Rook.
 */
public class Rook extends Piece{
	
	/**
	 * Instantiates a new rook.
	 *
	 * @param row the row
	 * @param column the column
	 * @param color the color
	 */
	public Rook(int row, int column, EnumColor color){
		super(row,column,color);
		this.name="Rook";
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#rules(int, int, model.Piece, model.Piece[])
	 */
	public boolean rules(int y, int x, Piece pieceInPos, Piece[] pieces) {
		boolean canmove = false;
		
		canmove = sideMove(x, y, pieceInPos, pieces);
		
		if (canmove) setFirstMove(false);
		
		return canmove;
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#squaresAttacked(int[], int, int, model.Piece[], int[][])
	 */
	public void squaresAttacked(int[] tempCoor, int oldx, int oldy, Piece[] pieces, int[][] squares) {
		sideSquaresAttacked(tempCoor, oldx, oldy, pieces, squares);
	}

	/* (non-Javadoc)
	 * @see model.Piece#check(model.Piece, model.Piece[])
	 */
	public boolean check(Piece king, Piece[] pieces) {
		return sideCheck(king, pieces);
	}
}
