package chess_logic.pieces;

public abstract class piece {

	public boolean isWhite;
	public int[] position = new int[2];

	public piece(boolean isWhite, int[] position){
		this.isWhite = isWhite;
		this.position = position;
	}

	public abstract int[][] getMoves();
}
