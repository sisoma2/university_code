package model;

import java.awt.Image;

/**
 * The Class Piece.
 */
public abstract class Piece{

	/** The row. */
	protected int row;		//int row;
	
	/** The column. */
	protected int column;		//int column;
	
	/** The X position. */
	protected int xPosition;
	
	/** The Y position. */
	protected int yPosition;
	
	/** The pic. */
	protected Image myPic;	//Image myPic
	
	/** If is visible. */
	protected boolean visible;//boolean visible	//counts as taken
	
	/** If is the first move. */
	protected boolean firstMove;
	
	/** The color. */
	protected EnumColor color;	//EnumColor color;
	
	/** The name. */
	protected String name;	//String name;
	
	/**
	 * Instantiates a new piece.
	 *
	 * @param row the row
	 * @param column the column
	 * @param color the color
	 */
	public Piece(int row, int column, EnumColor color) {
		this.row = row;
		this.column = column;
		this.color = color;
		this.xPosition = 0;
		this.yPosition = 0;
		this.visible = true;
		this.firstMove = true;
	}

	/**
	 * Gets the row.
	 *
	 * @return The row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Sets the row.
	 *
	 * @param row Rhe new row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Gets the column.
	 *
	 * @return The column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Sets the column.
	 *
	 * @param column The new column
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Gets the X position.
	 *
	 * @return The X position
	 */
	public int getXPosition() {
		return xPosition;
	}

	/**
	 * Sets the X position.
	 *
	 * @param xPosition The new X position
	 */
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Gets the y position.
	 *
	 * @return The Y position
	 */
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * Sets the Y position.
	 *
	 * @param yPosition The new Y position
	 */
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * Checks if is visible.
	 *
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the visible.
	 *
	 * @param visible The new state of visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Checks if is first move.
	 *
	 * @return true, if is first move
	 */
	public boolean isFirstMove() {
		return firstMove;
	}

	/**
	 * Sets the first move.
	 *
	 * @param firstMove The new state of firstMove
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	/**
	 * Gets the color.
	 *
	 * @return The color
	 */
	public EnumColor getColor() {
		return color;
	}

	/**
	 * Gets the name.
	 *
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the pic.
	 *
	 * @return The pic
	 */
	public Image getMyPic() {
		return myPic;
	}

	/**
	 * Sets the pic.
	 *
	 * @param myPic The new pic
	 */
	public void setMyPic(Image myPic) {
		this.myPic = myPic;
	}
	
	/**
	 * Rules.
	 * Movements that a piece can do.
	 *
	 * @param y The y
	 * @param x The x
	 * @param pieceInPos The piece in the new position
	 * @param pieces The piece
	 * @return true if can move, else false
	 */
	public abstract boolean rules(int y, int x, Piece pieceInPos, Piece pieces[]);
	
	/**
	 * Check.
	 * Methot that checks if the piece is checking any king.
	 *
	 * @param king the king
	 * @param pieces the pieces
	 * @return true if is checking the king, else false
	 */
	public abstract boolean check(Piece king, Piece[] pieces);
	
	/**
	 * Squares attacked.
	 * This method calculates which squares can be attacked and by who.
	 *
	 * @param tempCoor The tempCoor
	 * @param oldX The oldx
	 * @param oldY The oldy
	 * @param pieces The pieces
	 * @param squares The squares
	 */
	public abstract void squaresAttacked(int tempCoor[], int oldX, int oldY, Piece[] pieces, int[][] squares);
	
	
	/**
	 * Diagolan Squares attacked.
	 * Variation of squaresAttacked for pieces with diagonal movement.
	 *
	 * @param tempCoor The tempCoor
	 * @param oldx the oldx
	 * @param oldy the oldy
	 * @param pieces The pieces
	 * @param squares The squares
	 */
	public void diagonalSquaresAttacked(int[] tempCoor, int oldx, int oldy, Piece[] pieces, int[][] squares) {
		int x, y;
		boolean piecethere;
		for (int k = 0; k < 4; k++) {
			piecethere = false;
			here: for (int l = 0; l < 7; l++) {
				if (diagonalCoor(tempCoor, k)) {
					x = tempCoor[0];
					y = tempCoor[1];
					for (int m = 0; m < 32; m++)
						if (pieces[m].getRow() - 1 == x
								&& pieces[m].getColumn() - 1 == y)
							piecethere = true;
					
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
					
					if (piecethere)
						break here;
				}
			}
			tempCoor[0] = oldx;
			tempCoor[1] = oldy;
		}
	}
	
	/**
	 * Side Squares attacked.
	 * Variation of squaresAttacked for pieces with side movement.
	 *
	 * @param tempCoor The tempCoor
	 * @param oldx the oldx
	 * @param oldy the oldy
	 * @param pieces The pieces
	 * @param squares The squares
	 */
	public void sideSquaresAttacked(int[] tempCoor, int oldx, int oldy, Piece[] pieces, int[][] squares) {
		int x, y;
		boolean piecethere;
		for (int k = 0; k < 4; k++) {
			piecethere = false;
			here: for (int l = 0; l < 7; l++) {
				if (sideCoor(tempCoor, k)) {
					x = tempCoor[0];
					y = tempCoor[1];
					for (int m = 0; m < 32; m++)
						if (pieces[m].getRow() - 1 == x
								&& pieces[m].getColumn() - 1 == y)
							piecethere = true;
					
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
					
					if (piecethere)
						break here;
				}
			}
			tempCoor[0] = oldx;
			tempCoor[1] = oldy;
		}
	}
	
	/**
	 * Side coor.
	 * Coor for pieces that have a side movement.
	 *
	 * @param tempCoor The temp coor
	 * @param moveCase The move case
	 * @return true if can move, else false
	 */

	public boolean sideCoor(int tempCoor[], int moveCase){
		boolean moveOk = false;
		switch (moveCase) {
			case 0:
				tempCoor[0]--;
				break;
			case 1:
				tempCoor[1]++;
				break;
			case 2:
				tempCoor[0]++;
				break;
			case 3:
				tempCoor[1]--;
				break;
		}
		
		if (tempCoor[0] >= 0 && tempCoor[0] < 9)
			if (tempCoor[1] >= 0 && tempCoor[1] < 9)
				moveOk = true;
		return moveOk;
	}
	
	/**
	 * Diagonal coor.
	 * Coor for pieces that have a diagonal movement.
	 *
	 * @param tempCoor the temp coor
	 * @param moveCase the move case
	 * @return true if can move, else false
	 */
	public boolean diagonalCoor(int tempCoor[], int moveCase){
		boolean moveOk = false;
		switch (moveCase) {
			case 0: // up to the left
				tempCoor[0]--;
				tempCoor[1]--;
				break;
			case 1: // up to the right
				tempCoor[0]--;
				tempCoor[1]++;
				break;
			case 2: // down to the right
				tempCoor[0]++;
				tempCoor[1]++;
				break;
			case 3: // down to the left
				tempCoor[0]++;
				tempCoor[1]--;
				break;
		}
		if (tempCoor[0] >= 0 && tempCoor[0] < 9)
			if (tempCoor[1] >= 0 && tempCoor[1] < 9)
				moveOk = true;
		return moveOk;
	}
	
	/**
	 * Side move.
	 * Checks if the side movement is correct.
	 *
	 * @param x the x
	 * @param y the y
	 * @param pieceInPos the piece in pos
	 * @param pieces the pieces
	 * @return true if can move, else false
	 */
	public boolean sideMove(int x, int y, Piece pieceInPos, Piece[] pieces) {
		int moveCase = 0, length = 0;
		int tempCoor[] = new int[2];
		boolean canMove = false;
		EnumColor color2;
		
		y = y / 50 + 1;
		x = x / 50 + 1;
		
		if (getColumn() == y) {
			// case 0 up
			if (getRow() - x > 0) {
				length = getRow() - x;
				moveCase = 0;
				canMove = true;
			}
			// case 2 down
			if (x - getRow() > 0) {
				length = x - getRow();
				moveCase = 2;
				canMove = true;
			}
		}
		if (getRow() == x) {
			// case 1 right
			if (y - getColumn() > 0) {
				length = y - getColumn();
				moveCase = 1;
				canMove = true;
			}
			// case 3 left
			if (getColumn() - y > 0) {
				length = getColumn() - y;
				moveCase = 3;
				canMove = true;
			}
		}
		if (canMove) {
			tempCoor[0] = getRow();
			tempCoor[1] = getColumn();
			for (int i = 0; i < length - 1; i++) {
				sideCoor(tempCoor, moveCase);
				for (int j = 0; j < 32; j++)
					if (tempCoor[0] == pieces[j].getRow()
							&& tempCoor[1] == pieces[j].getColumn())
						canMove = false;
			}
		}
		
		if(pieceInPos!=null){
			if (canMove) {
					color2 = pieceInPos.getColor();	
					if (getColor().equals(color2))
						canMove = false;
				}
			
			if (pieceInPos.getName().equals("King"))
				canMove = false;
		}
		
		return canMove;
	}
	
	/**
	 * Diagonal move.
	 * Checks if the diagonal movement is correct.
	 *
	 * @param x the x
	 * @param y the y
	 * @param pieceInPos the piece in pos
	 * @param pieces the pieces
	 * @return true if can move, else false
	 */
	public boolean diagonalMove(int x, int y, Piece pieceInPos, Piece pieces[]) {
		int moveCase = 0, length = 0;
		int tempCoor[] = new int[2];
		int row = getRow(), column = getColumn();
		EnumColor color2;
		boolean canMove = false;
		y = y / 50 + 1;
		x = x / 50 + 1;
		if (row - x == column - y) {
			// case 1 up to the left
			if (row - x > 0
					&& column - y > 0) {
				length = row - x;
				moveCase = 0;
				canMove = true;
			}
		}
		if (row - x == y - column) {
			// case 2 up to the right
			if (row - x > 0
					&& y - column > 0) {
				length = row - x;
				moveCase = 1;
				canMove = true;
			}
		}
		if (x - row == y - column) {
			// case 3 down to the right
			if (x - row > 0
					&& y - column > 0) {
				length = x - row;
				moveCase = 2;
				canMove = true;
			}
		}
		if (x - row == column - y) {
			// case 4 down to the left
			if (x - row > 0
					&& column - y > 0) {
				length = x - row;
				moveCase = 3;
				canMove = true;
			}
		}
		if (canMove) {
			tempCoor[0] = row;
			tempCoor[1] = column;
			for (int i = 0; i < length - 1; i++) {
				diagonalCoor(tempCoor, moveCase);
				for (int j = 0; j < 32; j++)
					if (tempCoor[0] == pieces[j].getRow()
							&& tempCoor[1] == pieces[j].getColumn())
						canMove = false;
			}
		}
		
		if(pieceInPos!=null){
			if (canMove) {
				
				color2 = pieceInPos.getColor();
				if (getColor().equals(color2))
					canMove = false;
				
			}
			
			if (pieceInPos.getName().equals("King"))
				canMove = false;
		}
		
		return canMove;
	}
	
	/**
	 * Diagonal check.
	 * Check for pieces that have a diagonal movement.
	 *
	 * @param king the king
	 * @param pieces the pieces
	 * @return true, if successful
	 */
	public boolean diagonalCheck(Piece king, Piece[] pieces) {
		boolean check=false;
		// case 0 up to the left
		if (king.getRow() - getRow() == king.getColumn() - getColumn())
			if (king.getRow() - getRow() > 0 && king.getColumn() - getColumn() > 0)
				check=auxiliarDiagonalCheck(0, pieces, king);
		// case 1 up to the right
		if (king.getRow() - getRow() == getColumn() - king.getColumn())
			if (king.getRow() - getRow() > 0 && getColumn() - king.getColumn() > 0)
				check=auxiliarDiagonalCheck(1, pieces, king);
		// case 2 down to the right
		if (getRow() - king.getRow() == getColumn()
				- king.getColumn())
			if (getRow() - king.getRow() > 0 && getColumn() - king.getColumn() > 0)
				check=auxiliarDiagonalCheck(2, pieces, king);
		// case 3 down to the left
		if (getRow() - king.getRow() == king.getColumn() - getColumn())
			if (getRow() - king.getRow() > 0 && king.getColumn() - getColumn() > 0)
				check=auxiliarDiagonalCheck(3, pieces, king);
		
		return check;
	}
	
	/**
	 * Auxiliar diagonal check.
	 *
	 * @param control the control
	 * @param pieces the pieces
	 * @param king the king
	 * @return true, if successful
	 */
	public boolean auxiliarDiagonalCheck(int control, Piece[] pieces, Piece king) {
		int length;
		int tempcoor[] = new int[2];
		boolean check = true;

		length = Math.abs(king.getRow() - getRow());
		tempcoor[0] = king.getRow();
		tempcoor[1] = king.getColumn();
		
		for (int i = 0; i < length - 1; i++) {
			diagonalCoor(tempcoor, control);
			for (Piece piece:pieces)
				if (tempcoor[0] == piece.getRow() && tempcoor[1] == piece.getColumn())
					check = false;
		}

		return check;
	}
	
	/**
	 * Side check.
	 * Check for the pieces that have a side movement.
	 *
	 * @param king the king
	 * @param pieces the pieces
	 * @return true, if successful
	 */
	public boolean sideCheck(Piece king, Piece[] pieces) {
		boolean check=false;
		if (getColumn() == king.getColumn()) {
			// up
			if (getRow() - king.getRow() > 0)
				check=auxiliarSideCheck(0, pieces, king);
			// down
			if (king.getRow() - getRow() > 0)
				check=auxiliarSideCheck(2, pieces, king);
		}
		if (getRow() == king.getRow()) {
			// right
			if (king.getColumn() - getColumn() > 0)
				check=auxiliarSideCheck(1, pieces, king);
			// left
			if (getColumn() - king.getColumn() > 0)
				check=auxiliarSideCheck(3, pieces, king);
		}
		
		return check;
	}
	
	/**
	 * Auxiliar side check.
	 *
	 * @param control the control
	 * @param pieces the pieces
	 * @param king the king
	 * @return true, if successful
	 */
	public boolean auxiliarSideCheck(int control, Piece[] pieces, Piece king) {
		int length = 0;
		int tempcoor[] = new int[2];
		boolean check = true;

		// the the rook and the king are on the same column substract rows
		if (control == 0 || control == 2)
			length = Math.abs(king.getRow() - getRow());
		
		if (control == 1 || control == 3)
			length = Math.abs(king.getColumn() - getColumn());
		
		tempcoor[0] = getRow();
		tempcoor[1] = getColumn();
		
		for (int i = 0; i < length - 1; i++) {
			sideCoor(tempcoor, control);
			for (Piece piece:pieces)
				if (tempcoor[0] == piece.getRow() && tempcoor[1] == piece.getColumn())
					check = false;
		}
		
		return check;
	}
}
