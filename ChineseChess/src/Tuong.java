public class Tuong extends Piece {
    public Tuong(int row, int col, String color) {
        super(row, col, color);
    }
    @Override
    public String getSymbol() {
        return color.equals("Trắng") ? "相" : "象";  // Tượng Trắng hoặc Tượng Đen
    }
    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        // Tượng di chuyển chéo 2 ô, không thể đi qua các ô đã chiếm giữ
        if (Math.abs(newRow - row) != 2 || Math.abs(newCol - col) != 2) return false;
        
        // Kiểm tra có quân cản trở ở giữa không
        int midRow = (row + newRow) / 2;
        int midCol = (col + newCol) / 2;
        return board[midRow][midCol] == null; // Nếu có quân cản trở thì không hợp lệ
    }
}
