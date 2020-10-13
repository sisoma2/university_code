package model;

/**
 * The Class PawnW.
 */
public class PawnW extends Pawn {
	
	/**
	 * Instantiates a new pawn w.
	 *
	 * @param row the row
	 * @param column the column
	 * @param color the color
	 */
	public PawnW(int row, int column, EnumColor color){
		super(row,column,color);
		this.name="Pawn";
	}
	
	/* (non-Javadoc)
	 * @see model.Pawn#compareRow(int)
	 */
	public boolean compareRow(int x){
		return(getRow() == x+1);
	}
	
	/* (non-Javadoc)
	 * @see model.Piece#squaresAttacked(int[], int, int, model.Piece[], int[][])
	 */
	public void squaresAttacked(int[] tempCoor, int oldx, int oldy, Piece[] pieces, int[][] squares) {
		if (getRow() != 1) {
			if (getColumn() == 1) // to handle pawns on the first
										// column
			{
				if (squares[getRow() - 2][getColumn()] == 2)
					squares[getRow() - 2][getColumn()] += 1;
				else
					squares[getRow() - 2][getColumn()] = 1;
			} else if (getColumn() == 8) // to handle pawns on the
												// last column
			{
				if (squares[getRow() - 2][getColumn() - 2] == 2)
					squares[getRow() - 2][getColumn() - 2] += 1;
				else
					squares[getRow() - 2][getColumn() - 2] = 1;
			} else // to handle pawns in between first and last columns
			{
				if (squares[getRow() - 2][getColumn()] == 2)
					squares[getRow() - 2][getColumn()] += 1;
				else
					squares[getRow() - 2][getColumn()] = 1;
				if (squares[getRow() - 2][getColumn() - 2] == 2)
					squares[getRow() - 2][getColumn() - 2] += 1;
				else
					squares[getRow() - 2][getColumn() - 2] = 1;
			}
		}
	}

	/* (non-Javadoc)
	 * @see model.Piece#check(model.Piece, model.Piece[])
	 */
	public boolean check(Piece king, Piece[] pieces) {
		boolean checked = false;
		if (king.getRow() == getRow() - 1 && king.getColumn() == getColumn() + 1) {
			checked = true;
		}
		if (king.getRow() == getRow() - 1 && king.getColumn() == getColumn() - 1) {
			checked = true;
		}
		return checked;
	}
}
