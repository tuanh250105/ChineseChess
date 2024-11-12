public class Ma extends Piece {
    public Ma(int row, int col, String color) {
        super(row, col, color);
    }
    @Override
    public String getSymbol() {
        return color.equals("Trắng") ? "馬" : "馬";  // Mã Trắng hoặc Mã Đen (cùng biểu tượng)
    }
    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        // Mã di chuyển theo hình chữ L (2 ô theo một hướng và 1 ô theo hướng vuông góc)
        int dRow = Math.abs(newRow - row);
        int dCol = Math.abs(newCol - col);
        return (dRow == 2 && dCol == 1) || (dRow == 1 && dCol == 2);
    }
}
