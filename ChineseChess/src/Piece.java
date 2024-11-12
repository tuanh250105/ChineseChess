public abstract class Piece {
	protected int row, col;
	protected String color;

	public Piece(int row, int col, String color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}

	// Phương thức trừu tượng để kiểm tra nước đi
	public abstract boolean isValidMove(int newRow, int newCol, Piece[][] board);

	public abstract String getSymbol();

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public String getColor() {
		return color;
	}
}
