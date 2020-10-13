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
 * Class CheckTest.
 */
public class CheckTest {
	
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
	 * Tests if the method check in pawnW works.
	 */
	@Test
	public void testCheckPawnW(){
		initPieces();
		Piece piece=pieces[16];
		piece.setRow(2);
		piece.setColumn(4);
		Piece king = pieces[4];
		
		Assert.assertSame(true,piece.check(king, pieces));
	}
	
	/**
	 * Tests if the method check in pawnB works.
	 */
	@Test
	public void testCheckPawnB(){
		initPieces();
		Piece piece=pieces[8];
		piece.setRow(7);
		piece.setColumn(4);
		Piece king = pieces[28];
		
		Assert.assertSame(true,piece.check(king, pieces));
	}
	
	/**
	 * Tests if the method check in bishop works.
	 */
	@Test
	public void testCheckBishop(){
		initPieces();
		Piece piece=pieces[26];
		piece.setRow(3);
		piece.setColumn(3);
		
		Piece pawn=pieces[11];
		pawn.setRow(3);
		pawn.setColumn(4);
		
		Piece king = pieces[4];
		
		Assert.assertSame(true,piece.check(king, pieces));
	}
	
	/**
	 * Tests if the method check in knight works.
	 */
	@Test
	public void testCheckKnight(){
		initPieces();
		Piece piece=pieces[30];
		piece.setRow(3);
		piece.setColumn(4);
		Piece king = pieces[4];
		
		Assert.assertSame(true,piece.check(king, pieces));
	}
	
	/**
	 * Tests if the method check in queen works.
	 */
	@Test
	public void testCheckQueen(){
		initPieces();
		Piece piece=pieces[3];
		piece.setRow(5);
		piece.setColumn(5);
		
		Piece pawn=pieces[20];
		pawn.setColumn(6);
		
		Piece king = pieces[28];
		
		Assert.assertSame(true,piece.check(king, pieces));
	}
	
	/**
	 * Tests if the method check in rook works.
	 */
	@Test
	public void testCheckRook(){
		initPieces();
		Piece piece=pieces[31];
		piece.setRow(6);
		piece.setColumn(5);
		
		Piece pawn=pieces[12];
		pawn.setColumn(6);
		
		Piece king = pieces[4];
		
		Assert.assertSame(true,piece.check(king, pieces));
	}
}
