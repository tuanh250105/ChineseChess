import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Thiết lập giao diện người dùng trên luồng sự kiện Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tạo và hiển thị cửa sổ Cờ Tướng
                ChessBoard chessBoard = new ChessBoard();
                chessBoard.setVisible(true);
            }
        });
    }
}