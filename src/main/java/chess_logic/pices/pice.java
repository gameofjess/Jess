package chess_logic.pices;

public abstract class pice {

	public boolean isWhite;
	public int[] position = new int[2];

	public pice(boolean isWhite, int[] position){
		this.isWhite = isWhite;
		this.position = position;
	}

	public abstract int[][] getMoves();
}
