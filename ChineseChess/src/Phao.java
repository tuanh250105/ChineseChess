public class Phao extends Piece {
	public Phao(int row, int col, String color) {
		super(row, col, color);
	}

	@Override
	public String getSymbol() {
		return "砲";
	}

	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		// Pháo chỉ có thể di chuyển theo hàng hoặc cột
		if (row != newRow && col != newCol)
			return false;

		// Kiểm tra có quân cản trở
		int count = 0;
		if (row == newRow) { // Di chuyển theo hàng
			int minCol = Math.min(col, newCol);
			int maxCol = Math.max(col, newCol);
			for (int i = minCol + 1; i < maxCol; i++) {
				if (board[row][i] != null)
					count++; // Đếm quân cản trở
			}
		} else if (col == newCol) { // Di chuyển theo cột
			int minRow = Math.min(row, newRow);
			int maxRow = Math.max(row, newRow);
			for (int i = minRow + 1; i < maxRow; i++) {
				if (board[i][col] != null)
					count++; // Đếm quân cản trở
			}
		}

		// Nếu ô đích có quân
		if (board[newRow][newCol] != null) {
			// Pháo chỉ có thể ăn quân nếu có đúng 1 quân cản trở
			return count == 1 && !board[newRow][newCol].getColor().equals(this.color);
		} else {
			// Nếu ô đích trống, không được có quân cản trở nào giữa
			return count == 0;
		}
	}
}
