public class King extends Piece {
	public King(int row, int col, String color) {
		super(row, col, color);
	}

	// Tướng: Đi từng ô một, đi ngang hoặc dọc. Tướng luôn luôn phải ở trong phạm vi
	// cung và không được đi ra ngoài. Cung tức là hình vuông 2x2 được đánh dấu bằng
	// đường chéo hình chữ X

	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		if ((Math.abs(newRow - row) == 1 && newCol == col) || (Math.abs(newCol - col) == 1 && newRow == row)) {
			if (this.color.equals("Trang")) {
				if (newRow < 0 || newRow > 2 || newCol < 3 || newCol > 5) {
					return false;
				}
			} else {
				if (newRow < 7 || newRow > 9 || newCol < 3 || newCol > 5) {
					return false;
				}
			}

			if (board[newRow][newCol] != null && board[newRow][newCol].color.equals(this.color)) {
				return false;
			}

			return true;
		}

		return false;
	}

	@Override
	public String getSymbol() {
		return color.equals("Trang") ? "帥" : "將"; // Tướng Trắng hoặc Tướng Đen
	}
}