public class Xe extends Piece {
	public Xe(int row, int col, String color) {
		super(row, col, color);
	}

	@Override
	public String getSymbol() {
		return color.equals("Trắng") ? "車" : "車"; // Xe Trắng hoặc Xe Đen
	}

	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		// Xe di chuyển theo hàng hoặc cột, không thể vượt qua quân khác
		if (row != newRow && col != newCol)
			return false; // Không phải di chuyển theo hàng hoặc cột

		// Kiểm tra ô đích có quân cờ cùng màu không
		if (board[newRow][newCol] != null && board[newRow][newCol].color.equals(this.color)) {
			return false; // Không thể di chuyển đến ô có quân cờ cùng màu
		}

		// Kiểm tra có quân cản trở không
		if (row == newRow) { // Di chuyển theo hàng
			int minCol = Math.min(col, newCol) + 1;
			int maxCol = Math.max(col, newCol);
			for (int i = minCol; i < maxCol; i++) {
				if (board[row][i] != null)
					return false; // Có quân cản trở
			}
		} else if (col == newCol) { // Di chuyển theo cột
			int minRow = Math.min(row, newRow) + 1;
			int maxRow = Math.max(row, newRow);
			for (int i = minRow; i < maxRow; i++) {
				if (board[i][col] != null)
					return false; // Có quân cản trở
			}
		}
		return true; 
	}
}
