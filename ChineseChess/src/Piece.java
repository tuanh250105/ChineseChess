public abstract class Piece {
    protected int row, col;
    protected String color; // "black" or "red"

    public Piece(int row, int col, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    // Phương thức trừu tượng để kiểm tra nước đi
    public abstract boolean isValidMove(int newRow, int newCol, Piece[][] board);
    
 // Phương thức trừu tượng để lấy biểu tượng quân cờ
    public abstract String getSymbol();

    // Getter và setter cho các thuộc tính nếu cần
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getColor() {
        return color;
    }
}
