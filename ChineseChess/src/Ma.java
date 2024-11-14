public class Ma extends Piece {
	public Ma(int row, int col, String color) {
		super(row, col, color);
	}

	// Mã: Đi ngang 2 ô và dọc 1 ô (hay dọc 2 ô và ngang 1 ô) cho mỗi nước đi.
	// Nếu có quân nằm bên ngay bên cạnh mã và cản đường ngang 2 (hay đường dọc 2),
	// mã bị cản không được đi đường đó.
	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		// Tính độ lệch hàng và cột
		int dRow = Math.abs(newRow - row);
		int dCol = Math.abs(newCol - col);

		// Kiểm tra di chuyển của quân Mã: đi theo hình chữ L
		if ((dRow == 2 && dCol == 1) || (dRow == 1 && dCol == 2)) {
			// Kiểm tra quân cản (ô trung gian)
			if (dRow == 2) {
				int blockRow = row + (newRow - row) / 2;
				if (board[blockRow][col] != null) {
					return false;
				}
			} else if (dCol == 2) {
				int blockCol = col + (newCol - col) / 2;
				if (board[row][blockCol] != null) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

	@Override
	public String getSymbol() {
		return "馬";
	}
}
