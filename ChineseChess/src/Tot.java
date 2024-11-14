public class Tot extends Piece {

	public Tot(int row, int col, String color) {
		super(row, col, color);
	}

//	Tốt: Đi một ô mỗi nước. Nếu tốt chưa vượt qua sông, nó chỉ có thể đi thẳng tiến. Khi đã vượt
//	sông rồi, tốt có thể đi ngang 1 nước hay đi thẳng tiến 1 bước mỗi nước.
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		boolean isCrossedRiver = (color.equals("Den") && row < 5) || (color.equals("Trang") && row > 4);
		// Kiểm tra ô đích có quân cờ cùng màu không
		if (board[newRow][newCol] != null && board[newRow][newCol].color.equals(this.color)) {
			return false;
		}
		if (!isCrossedRiver) {

			if (color.equals("Trang")) {
				return newRow == row + 1 && newCol == col;
			} else {
				return newRow == row - 1 && newCol == col;
			}
		} else {

			boolean isMovingStraight = (newRow == row + 1 && newCol == col) || (newRow == row - 1 && newCol == col);
			boolean isMovingSideways = (newRow == row && Math.abs(newCol - col) == 1);

			if (isMovingSideways && board[newRow][newCol] == null) {
				return true;
			}

			return isMovingStraight || isMovingSideways;
		}
	}

	@Override
	public String getSymbol() {
		return color.equals("Trang") ? "兵" : "卒"; // Tốt Trắng hoặc Tốt Đen
	}
}
