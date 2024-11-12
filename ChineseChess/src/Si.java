public class Si extends Piece {
	public Si(int row, int col, String color) {
		super(row, col, color);
	}

	@Override
	public String getSymbol() {
		return color.equals("Trắng") ? "仕" : "士"; // Sĩ Trắng hoặc Sĩ Đen
	}

	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		// Kiểm tra xem quân Sĩ có nằm trong cung không
		if (this.color.equals("Trắng")) {
			if (newRow < 0 || newRow > 2 || newCol < 3 || newCol > 5) {
				return false; // Sĩ Trắng không được ra ngoài cung
			}
		} else {
			if (newRow < 7 || newRow > 9 || newCol < 3 || newCol > 5) {
				return false; // Sĩ Đen không được ra ngoài cung
			}
		}

		// Kiểm tra di chuyển xéo
		if (Math.abs(newRow - this.row) == 1 && Math.abs(newCol - this.col) == 1) {
			// Kiểm tra ô đích có quân cờ không
			if (board[newRow][newCol] == null || !board[newRow][newCol].color.equals(this.color)) {
				return true; // Nước đi hợp lệ
			}
		}
		return false; // Nước đi không hợp lệ
	}
}
