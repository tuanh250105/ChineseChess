public class King extends Piece {
    public King(int row, int col, String color) {
        super(row, col, color);
    }

    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        // Tướng di chuyển 1 ô theo chiều ngang hoặc dọc
        if ((Math.abs(newRow - row) == 1 && newCol == col) || (Math.abs(newCol - col) == 1 && newRow == row)) {
            // Kiểm tra xem Tướng có nằm trong cung không
            if (this.color.equals("Trắng")) {
                if (newRow < 0 || newRow > 2 || newCol < 3 || newCol > 5) {
                    return false; // Tướng Trắng không được ra ngoài cung
                }
            } else {
                if (newRow < 7 || newRow > 9 || newCol < 3 || newCol > 5) {
                    return false; // Tướng Đen không được ra ngoài cung
                }
            }

            // Kiểm tra ô đích có quân cờ cùng màu không
            if (board[newRow][newCol] != null && board[newRow][newCol].color.equals(this.color)) {
                return false; // Không thể di chuyển đến ô có quân cờ cùng màu
            }

            return true; // Nước đi hợp lệ
        }

        return false; // Nếu không di chuyển theo chiều ngang hoặc dọc, không hợp lệ
    }

    @Override
    public String getSymbol() {
        return color.equals("Trắng") ? "帥" : "將";  // Tướng Trắng hoặc Tướng Đen
    }
}
