public class Phao extends Piece {
	public Phao(int row, int col, String color) {
		super(row, col, color);
	}

//	Pháo: Đi ngang và dọc giống như xe. Điểm khác biệt là nếu pháo muốn ăn quân, pháo phải
//	nhảy qua đúng 1 quân nào đó. Khi không ăn quân, tất cả những điểm từ chỗ đi đến chỗ đến
//	phải không có quân cản.

	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		if (row != newRow && col != newCol)
			return false;

		// Kiểm tra có quân cản trở
		int count = 0;
		if (row == newRow) { 
			int minCol = Math.min(col, newCol);
			int maxCol = Math.max(col, newCol);
			for (int i = minCol + 1; i < maxCol; i++) {
				if (board[row][i] != null)
					count++;
			}
		} else if (col == newCol) { 
			int minRow = Math.min(row, newRow);
			int maxRow = Math.max(row, newRow);
			for (int i = minRow + 1; i < maxRow; i++) {
				if (board[i][col] != null)
					count++;
			}
		}

		// Nếu ô đích có quân
		if (board[newRow][newCol] != null) {
			return count == 1 && !board[newRow][newCol].getColor().equals(this.color);
		} else {
			return count == 0;
		}
	}
	
	@Override
	public String getSymbol() {
		return "砲";
	}
}
