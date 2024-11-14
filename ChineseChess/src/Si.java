public class Si extends Piece {
	public Si(int row, int col, String color) {
		super(row, col, color);
	}

	// Sĩ: Đi xéo 1 ô mỗi nước. Sĩ luôn luôn phải ở trong cung như Tướng.
	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		if (this.color.equals("Trang")) {
			if (newRow < 0 || newRow > 2 || newCol < 3 || newCol > 5) {
				return false;
			}
		} else {
			if (newRow < 7 || newRow > 9 || newCol < 3 || newCol > 5) {
				return false;
			}
		}

		if (Math.abs(newRow - this.row) == 1 && Math.abs(newCol - this.col) == 1) {
			if (board[newRow][newCol] == null || !board[newRow][newCol].color.equals(this.color)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getSymbol() {
		return color.equals("Trang") ? "仕" : "士"; // Sĩ Trắng hoặc Sĩ Đen
	}
}