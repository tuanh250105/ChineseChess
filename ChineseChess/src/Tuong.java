public class Tuong extends Piece {
	public Tuong(int row, int col, String color) {
		super(row, col, color);
	}

//Tượng: Đi chéo 2 ô (ngang 2 và dọc 2) cho mỗi nước đi. Tượng chỉ được phép ở 1 bên của bàn cờ, không được di chuyển sang nửa bàn cờ của đối phương. Nuớc đi của tượng sẽ không hợp lệ khi có một quan cờ nằm chạn giữa đường đi.	

	@Override
	public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
		if (Math.abs(newRow - row) != 2 || Math.abs(newCol - col) != 2) {
			return false;
		}

		// Kiểm tra ô đích có quân cờ cùng màu không
		if (board[newRow][newCol] != null && board[newRow][newCol].color.equals(this.color)) {
			return false;
		}

		int midRow = (row + newRow) / 2;
		int midCol = (col + newCol) / 2;
		return board[midRow][midCol] == null;
	}

	@Override
	public String getSymbol() {
		return color.equals("Trang") ? "相" : "象"; // Tượng Trắng hoặc Tượng Đen
	}
}