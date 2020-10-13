package testing;

import model.Bishop;
import model.EnumColor;
import model.King;
import model.Knight;
import model.PawnB;
import model.PawnW;
import model.Piece;
import model.Queen;
import model.Rook;
import org.junit.*;

/**
 * Class RulesTest.
 */
public class RulesTest {
	
	/** Pieces array. */
	public Piece[] pieces = new Piece[32];
	
	/**
	 * Initializes the pieces.
	 */
	public void initPieces(){
		pieces[0]=new Rook(1,1,EnumColor.BLACK);
		pieces[1]=new Knight(1,2,EnumColor.BLACK);
		pieces[2]=new Bishop(1,3,EnumColor.BLACK);
		pieces[3]=new Queen(1,4,EnumColor.BLACK);
		pieces[4]=new King(1,5,EnumColor.BLACK);
		pieces[5]=new Bishop(1,6,EnumColor.BLACK);
		pieces[6]=new Knight(1,7,EnumColor.BLACK);
		pieces[7]=new Rook(1,8,EnumColor.BLACK);
		
		for (int i = 8; i < 16; i++) {
			pieces[i] = new PawnB(2,i-7,EnumColor.BLACK);
		}
		
		for (int i = 16; i < 24; i++) {
			pieces[i] = new PawnW(7,i-15,EnumColor.WHITE);
		}
		
		pieces[24]=new Rook(8,1,EnumColor.WHITE);
		pieces[25]=new Knight(8,2,EnumColor.WHITE);
		pieces[26]=new Bishop(8,3,EnumColor.WHITE);
		pieces[27]=new Queen(8,4,EnumColor.WHITE);
		pieces[28]=new King(8,5,EnumColor.WHITE);
		pieces[29]=new Bishop(8,6,EnumColor.WHITE);
		pieces[30]=new Knight(8,7,EnumColor.WHITE);
		pieces[31]=new Rook(8,8,EnumColor.WHITE);
	}
	
	/**
	 * Tests if the method rules in pawn works.
	 */
	@Test
	public void testRulesPawn(){
		initPieces();
		Piece pawn = pieces[8];
		Piece pieceInPos=null;
		int x = (4-1)*50;
		int y = (1-1)*50;
		
		Assert.assertSame(true, pawn.rules(y, x, pieceInPos, pieces));
	}
	
	/**
	 * Tests if the method rules in bishop works.
	 */
	@Test
	public void testRulesBishop(){
		initPieces();
		Piece bishop = pieces[2];
		Piece pieceInPos=pieces[26];
		int x = (5-1)*50;
		int y = (7-1)*50;
		
		Piece pawn = pieces[11];
		pawn.setRow(3);
		pawn.setColumn(4);
		
		Assert.assertSame(true, bishop.rules(y, x, pieceInPos, pieces));
	}
	
	/**
	 * Tests if the method rules in king works.
	 */
	@Test
	public void testRulesKing(){
		initPieces();
		Piece king = pieces[4];
		Piece pieceInPos=pieces[5];
		int x = (1-1)*50;
		int y = (6-1)*50;
		
		Assert.assertSame(false, king.rules(y, x, pieceInPos, pieces));
	}
	
	/**
	 * Tests if the method rules in knight works.
	 */
	@Test
	public void testRulesKnight(){
		initPieces();
		Piece knight = pieces[1];
		Piece pieceInPos=pieces[21];
		int x = (3-1)*50;
		int y = (3-1)*50;
		
		Assert.assertSame(true, knight.rules(y, x, pieceInPos, pieces));
	}
	
	/**
	 * Tests if the method rules in queen works.
	 */
	@Test
	public void testRulesQueen(){
		initPieces();
		Piece queen = pieces[3];
		Piece pieceInPos=null;
		int x = (4-1)*50;
		int y = (7-1)*50;
		
		Piece pawn = pieces[12];
		pawn.setRow(5);
		pawn.setColumn(5);
		
		Assert.assertSame(true, queen.rules(y, x, pieceInPos, pieces));
	}
	
	/**
	 * Tests if the method rules in rook works.
	 */
	@Test
	public void testRulesRook(){
		initPieces();
		Piece rook = pieces[0];
		rook.setRow(3);
		Piece pieceInPos=pieces[28];
		pieceInPos.setRow(3);
		pieceInPos.setColumn(3);
		int x = (4-1)*50;
		int y = (7-1)*50;
		
		Assert.assertSame(false, rook.rules(y, x, pieceInPos, pieces));
	}
}
