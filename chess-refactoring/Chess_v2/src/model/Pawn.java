/*
 * 
 */
package model;

// TODO: Auto-generated Javadoc
/**
 * The Class Pawn.
 */
public abstract class Pawn extends Piece{
	
	/**
	 * Compare row.
	 *
	 * @param x the x
	 * @return true, if successful
	 */
	public abstract boolean compareRow(int x);
	
	/**
	 * Instantiates a new pawn.
	 *
	 * @param row the row
	 * @param column the column
	 * @param color the color
	 */
	public Pawn(int row, int column, EnumColor color){
		super(row,column,color);
		this.name="Pawn";
	}

	/* (non-Javadoc)
	 * @see model.Piece#rules(int, int, model.Piece, model.Piece[])
	 */
	public boolean rules(int y, int x, Piece pieceInPos, Piece[] pieces) {
		boolean canMove = false;
		
		y = y / 50 + 1;
		x = x / 50 + 1;
		if (getColumn() == y){
			if (getColumn() == y && compareRow(x)) {
				if (pieceInPos == null){ // can move
					setFirstMove(false);
					canMove = true;
				}
			}
			
			else if ((getRow() == x + 2 || getRow() == x - 2 )&& isFirstMove()) {
				if (pieceInPos == null) {
					setFirstMove(false);
					canMove = true;
				}
			}
		}
		else if ((getColumn() == y + 1 || getColumn() == y - 1) && compareRow(x)) {
			if (pieceInPos != null && !(pieceInPos.getColor().equals(getColor()))) {
				setFirstMove(false);
				canMove = true;
			}		
		}
	
		if (pieceInPos != null)
			if (pieceInPos.getName().equals("King"))
				canMove = false;
		
		return canMove;
	}
}
