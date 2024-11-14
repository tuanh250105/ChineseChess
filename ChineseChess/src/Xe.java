public class Xe extends Piece {
	public Xe(int row, int col, String color) {
		super(row, col, color);
	}

//	Xe: Đi ngang hay dọc trên bàn cờ miễn là đừng bị quân khác cản đường từ điểm đi đến điểm
//	đến.

	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		if (row != newRow && col != newCol)
			return false;
		if (board[newRow][newCol] != null && board[newRow][newCol].color.equals(this.color)) {
			return false;
		}

		if (row == newRow) {
			int minCol = Math.min(col, newCol) + 1;
			int maxCol = Math.max(col, newCol);
			for (int i = minCol; i < maxCol; i++) {
				if (board[row][i] != null)
					return false;
			}
		} else if (col == newCol) {
			int minRow = Math.min(row, newRow) + 1;
			int maxRow = Math.max(row, newRow);
			for (int i = minRow; i < maxRow; i++) {
				if (board[i][col] != null)
					return false;
			}
		}
		return true; 
	}
	
	@Override
	public String getSymbol() {
		return color.equals("Trang") ? "車" : "車"; // Xe Trắng hoặc Xe Đen
	}
}
