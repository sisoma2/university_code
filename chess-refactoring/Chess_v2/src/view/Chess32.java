package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import control.MousePressListener;
import model.*;


/**
 * Main class Chess32.
 * 
 * @author Luis Alvarez, Marc Elias.
 *
 */
public class Chess32 extends java.applet.Applet {
	
	/**serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Pieces array. */
	public Piece[] pieces = new Piece[32];
	
	/** If a piece can be selected or not. */
	private boolean ok = false;
	
	/** Number of clicks. */
	private int numclicks = 0;
	
	/** Old X value. */
	private int oldX;
	
	/** Old Y value. */
	private int oldY;
	
	/** Identifies selected piece. */
	int pieceChosen = 0;
	
	/** Array squares. */
	int squares[][] = new int[12][12];
	
	/** If there is a check and show it. */
	boolean checked;
	
	/** Indicates which king is checked. */
	int whichKing; 
	
	/** Used to alternate turns. */
	//int whos_turn; 
	
	/** Indicates if a pawn has to be promoted. */
	boolean promotion; 
	
	/**
	 * Returns attribute ok.
	 *
	 * @return ok
	 */
	public boolean getOk(){
		return ok;
	}
	
	/**
	 * Gets the numclicks.
	 *
	 * @return the numclicks
	 */
	public int getNumclicks() {
		return numclicks;
	}

	/**
	 * Sets the numclicks.
	 *
	 * @param numclicks The new numclicks.
	 */
	public void setNumclicks(int numclicks) {
		this.numclicks = numclicks;
	}

	/**
	 * Sets the image.
	 *
	 * @param piece Piece whose image has to be set.
	 */
	public void setImage(Piece piece) {
		String fileName;
		String blackName = "brbnbbbqbk";
		String whiteName = "wrwnwbwqwk";
		String tempName = "";
		String fileExt = ".gif";

		if (piece.getColor().equals(EnumColor.BLACK))
			tempName = blackName;
		else
			tempName = whiteName;
		
		if(piece instanceof PawnB){
			piece.setMyPic(getImage(getCodeBase(), "bp.gif"));
		} else if(piece instanceof PawnW){
			piece.setMyPic(getImage(getCodeBase(), "wp.gif"));
		}else if(piece.getName().equals("Rook")){
			fileName = tempName.substring(0, 2) + fileExt;
			piece.setMyPic(getImage(getCodeBase(), fileName));
		} else if(piece.getName().equals("Bishop")){
			fileName = tempName.substring(4, 6) + fileExt;
			piece.setMyPic(getImage(getCodeBase(), fileName));
		} else if(piece.getName().equals("Knight")){
			fileName = tempName.substring(2, 4) + fileExt;
			piece.setMyPic(getImage(getCodeBase(), fileName));
		} else if(piece.getName().equals("Queen")){
			fileName = tempName.substring(6, 8) + fileExt;
			piece.setMyPic(getImage(getCodeBase(), fileName));
		} else if(piece.getName().equals("King")){
			fileName = tempName.substring(8, 10) + fileExt;
			piece.setMyPic(getImage(getCodeBase(), fileName));
		}
	}
	
	/**
	 * Initializes the other pieces.
	 */
	public void initOtherPieces(){
		pieces[0]=new Rook(1,1,EnumColor.BLACK);
		pieces[1]=new Knight(1,2,EnumColor.BLACK);
		pieces[2]=new Bishop(1,3,EnumColor.BLACK);
		pieces[3]=new Queen(1,4,EnumColor.BLACK);
		pieces[4]=new King(1,5,EnumColor.BLACK);
		pieces[5]=new Bishop(1,6,EnumColor.BLACK);
		pieces[6]=new Knight(1,7,EnumColor.BLACK);
		pieces[7]=new Rook(1,8,EnumColor.BLACK);
		
		pieces[24]=new Rook(8,1,EnumColor.WHITE);
		pieces[25]=new Knight(8,2,EnumColor.WHITE);
		pieces[26]=new Bishop(8,3,EnumColor.WHITE);
		pieces[27]=new Queen(8,4,EnumColor.WHITE);
		pieces[28]=new King(8,5,EnumColor.WHITE);
		pieces[29]=new Bishop(8,6,EnumColor.WHITE);
		pieces[30]=new Knight(8,7,EnumColor.WHITE);
		pieces[31]=new Rook(8,8,EnumColor.WHITE);
	}
	

	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		// this declares the pieces, can't use them without this
		for (int i = 8; i < 16; i++) {
			pieces[i] = new PawnB(2,i-7,EnumColor.BLACK);
		}
		
		for (int i = 16; i < 24; i++) {
			pieces[i] = new PawnW(7,i-15,EnumColor.WHITE);
		}
		
		initOtherPieces();
		
		//Sets an image to the pieces
		for(Piece piece:pieces){
			setImage(piece);
		}
		
		// This puts pieces in their original settings in the class
		// top left corner is [0][0]
		// bottom right is [7][7]
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				squares[i][j] = 0;
		
		addMouseListener(new MousePressListener());
		//whos_turn = 0;
		checked = false;
		promotion = false;
	}

	/**
	 * Row to x.
	 *
	 * @param r The row.
	 * @return myX The X position.
	 */
	public int rowToX(int r) {
		int myX;
		int iHeight = getHeight();
		myX = r * iHeight / 8 - 50;
		return myX;
	}

	/**
	 * Column to y.
	 *
	 * @param c The column.
	 * @return muY The Y position.
	 */
	public int colToY(int c) {
		int myY;
		int iWidth = getWidth();
		myY = c * iWidth / 8 - 50;
		return myY;
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		setSize(400, 400);

		int iWidth = getWidth();
		int iHeight = getHeight();
		for (int i = 0; i < 8; i = i + 2) {
			for (int j = 0; j < 8; j = j + 2) {
				g.setColor(Color.red);
				g.fillRect(j * iWidth / 8, (1 + i) * iWidth / 8, iWidth / 8,
						iHeight / 8);
				g.fillRect((1 + j) * iWidth / 8, i * iWidth / 8, iWidth / 8,
						iHeight / 8);
			}
		}
		for (Piece piece:pieces) {
			piece.setXPosition(rowToX(piece.getColumn()));
			piece.setYPosition(colToY(piece.getRow()));
			if (piece.isVisible())
				g.drawImage(piece.getMyPic(), piece.getXPosition(),
						piece.getYPosition(), iWidth / 8, iHeight / 8, this);
		}
		if (ok) {
			g.setColor(Color.green.darker());
			g.drawRect(pieces[pieceChosen].getXPosition(),
					pieces[pieceChosen].getYPosition(), 50, 50);
			g.setColor(Color.green.brighter().brighter());
			g.drawRect(pieces[pieceChosen].getXPosition() + 1,
					pieces[pieceChosen].getYPosition() + 1, 48, 48);
			g.setColor(Color.green.darker());
			g.drawRect(pieces[pieceChosen].getXPosition() + 2,
					pieces[pieceChosen].getYPosition() + 2, 46, 46);
		}
	}

		/**
		 * Board id.
		 * Method that selects the piece in the square which has been clicked.
		 *
		 * @param x The x
		 * @param y The y
		 */
		public void boardID(int x, int y) {
			for (int i = 0; i < 32; i++) {
				if (((y / 50) + 1 == pieces[i].getRow()) && ((x / 50) + 1 == pieces[i].getColumn())) {
					pieceChosen = i; // this identifies which piece has been selected
					
					/*if (whos_turn == 0 && pieces[pieceChosen].getColor().equals(EnumColor.WHITE))
						ok = true; // this says we found a white piece and we arready to move it
					
					if (whos_turn == 1 && pieces[pieceChosen].getColor().equals(EnumColor.BLACK))
						*/ok = true; 
				}
			}
		}

		/**
		 * Check method.
		 * Method that checks if there is a king checked.
		 *
		 * @return true If there is a king checked, else false
		 */
		public boolean checkMethod() {
			Piece king;

			for (Piece piece:pieces) {
				if(piece.getColor().equals(EnumColor.WHITE)) king = pieces[4];
				else king = pieces[28];
				
				checked=piece.check(king, pieces);
				if(checked){
					if(piece.getColor().equals(EnumColor.WHITE)) whichKing = 4;
					else whichKing = 28;
					break;
				}
				
			}
			return checked;
		}

		// this determines squares attacked
		// pieces 0 - 15 = black; pieces 16 - 31 = white
		// 0 - square not attacked
		// 1 - sqaure attacked by white
		// 2 - square attacked by black
		// 3 - square attacked by both
		/**
		 * Squares attacked.
		 * Checks which squares can be attacked and by who.
		 *
		 * @return true, if successful
		 */
		public boolean squaresAttacked() {
			int tempCoor[] = new int[2];
			int oldx, oldy;
			
			for(Piece piece:pieces){
				tempCoor[0] = piece.getRow();
				tempCoor[1] = piece.getColumn();
				tempCoor[0]--;
				tempCoor[1]--;
				oldx = tempCoor[0];
				oldy = tempCoor[1];
				piece.squaresAttacked(tempCoor, oldx, oldy, pieces, squares);
			}
			
				
			for (int i = 0; i < 8; i++) // this displays the squares attacked
			{
				for (int j = 0; j < 8; j++)
					System.out.print(squares[i][j]);
				System.out.println("");
			}
			System.out.println("********");
			for (int i = 0; i < 8; i++)
				// need to reinitialize for the next iteration
				for (int j = 0; j < 8; j++)
					squares[i][j] = 0;
			return true;
		}

		/**
		 * Pawn promotion.
		 * Transforms a pawn in the piece that the player chooses.
		 *
		 * @param oldX the oldx
		 * @param oldY the oldy
		 */
		public void pawnPromotion(int oldX, int oldY) {
			String newPiece;
			boolean finished = false;
			while (!finished) {
				newPiece = JOptionPane.showInputDialog("Choose a new piece Q / R / B / K");
				Piece actualPiece = pieces[pieceChosen];
				
				if (newPiece.equalsIgnoreCase("Q")) {
					
					pieces[pieceChosen]=new Queen(oldX,oldY,actualPiece.getColor());
					setImage(pieces[pieceChosen]);
					
					finished = true;
				}
				if (newPiece.equalsIgnoreCase("R")) {
					
					pieces[pieceChosen]=new Rook(oldX,oldY,actualPiece.getColor());
					setImage(pieces[pieceChosen]);
					
					finished = true;
				}
				if (newPiece.equalsIgnoreCase("B")) {
					
					pieces[pieceChosen]=new Bishop(oldX,oldY,actualPiece.getColor());
					setImage(pieces[pieceChosen]);
					
					finished = true;
				}
				if (newPiece.equalsIgnoreCase("K")) {
					
					pieces[pieceChosen]=new Knight(oldX,oldY,actualPiece.getColor());
					setImage(pieces[pieceChosen]);
					
					finished = true;
				}
			}
			promotion = false;
		}

		// this function does the assignment that allows for movement
		// x and y are the coordinates of the square you're moving to
		/**
		 * Move piece.
		 * Method that actually moves the piece and controls the game.
		 *
		 * @param x the x
		 * @param y the y
		 */
		public void movePiece(int x, int y) {
			boolean canMove;
			ok = false; // needed to release the current piece if you can't move
						// it
			// this detects if there is a piece or not to the destination square
			boolean foundPiece = false; // added chess 15
			
			Piece pieceInPos=null;

			oldX = y / 50 + 1;
			oldY = x / 50 + 1;
			for (Piece piece:pieces) {
				if (pieces[pieceChosen] != piece) {
					if (oldX == piece.getRow() && oldY == piece.getColumn()) {
						foundPiece = true;
						pieceInPos=piece;
					}
				}
			}
			
			if(!foundPiece) pieceInPos=null;
			
			canMove = pieces[pieceChosen].rules(x, y, pieceInPos, pieces);
			
			if (canMove) {
				promotion=checkPawnPromotion(oldX);
				if (promotion)
					pawnPromotion(oldX,oldY);
				
				oldX = pieces[pieceChosen].getRow();
				oldY = pieces[pieceChosen].getColumn();
				pieces[pieceChosen].setRow(y / 50 + 1);
				pieces[pieceChosen].setColumn(x / 50 + 1);
				for (Piece piece:pieces) // checks for another piece
				{
					if (pieces[pieceChosen] != piece) // don't take self
					{
						if (pieces[pieceChosen].getRow() == piece.getRow()
								&& pieces[pieceChosen].getColumn() == piece.getColumn()
								&& !piece.getName().equals("King")) {
							piece.setRow(11); // actually removes piece from
												// board
							piece.setColumn(11);
							piece.setVisible(false);
						}
					}

				}
				if (checkMethod()) {
					if (pieces[pieceChosen].getColor().equals(pieces[whichKing].getColor())) {
						pieces[pieceChosen].setRow(oldX);
						pieces[pieceChosen].setColumn(oldY);
					}
				}
				squaresAttacked();
				// after the calculation repaint is called to display the movement
				//whos_turn = (whos_turn + 1) % 2; //this toggles between the players
				repaint();
				if (checked) {
					JOptionPane.showMessageDialog(null, "check");
					checked = false;
				}
			} else
				repaint();
		}
		
		/**
		 * Check pawn promotion.
		 * Checks if the piece is a pawn and if has to be promoted.
		 *
		 * @param x Row of the piece
		 * @return true if has to be promoted, else false
		 */
		public boolean checkPawnPromotion(int x){
			boolean prom=false;
			EnumColor color=pieces[pieceChosen].getColor();
			
			if(pieces[pieceChosen].getName().equals("Pawn")){
				if(color.equals(EnumColor.WHITE)){
					prom=(x==1);
				}else{
					prom=(x==8);
				}
			}
			return prom;
		}
	}
