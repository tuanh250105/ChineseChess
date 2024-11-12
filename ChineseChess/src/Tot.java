public class Tot extends Piece {

	public Tot(int row, int col, String color) {
		super(row, col, color);
	}

	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		// Kiểm tra di chuyển hợp lệ cho quân Tốt
		if (color.equals("Trắng")) {
			// Tốt Trắng: nếu ở hàng 4 trở lên, có thể đi ngang một ô (qua sông)
			if (row == 4) {
				return (newRow == row + 1 && newCol == col) || (newRow == row && Math.abs(newCol - col) == 1);
			} else {
				return newRow == row + 1 && newCol == col; // Di chuyển một bước lên đối với quân Trắng
			}
		} else {
			// Tốt Đen: nếu ở hàng 5 trở xuống, có thể đi ngang một ô (qua sông)
			if (row == 5) {
				return (newRow == row - 1 && newCol == col) || (newRow == row && Math.abs(newCol - col) == 1);
			} else {
				return newRow == row - 1 && newCol == col; // Di chuyển một bước xuống đối với quân Đen
			}
		}
	}

	@Override
	public String getSymbol() {
		return color.equals("Trắng") ? "兵" : "卒"; // Tốt Trắng hoặc Tốt Đen
	}
}
