import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.Serializable;

public class ChessBoard extends JFrame {
	
	private static final long serialVersionUID = 1L;
    private Piece[][] board;
    private JButton[][] buttons;
    private Piece selectedPiece;
    private int selectedX, selectedY;
    private Rule rule;
    private String currentPlayer;
    private JLabel moveDescriptionLabel;
    public ChessBoard() {
        rule = new Rule();
        board = new Piece[10][9];
        buttons = new JButton[10][9];
        selectedPiece = null;
        selectedX = -1;
        selectedY = -1;
        currentPlayer = "Trắng";
        Board();
        UI();
    }

    private void Board() {
    	// Đặt Tướng
        board[0][4] = new Piece("帥", "Trắng"); // Tướng Trắng
        board[9][4] = new Piece("將", "Đen"); // Tướng Đen

        // Đặt Sĩ
        board[0][3] = new Piece("仕", "Trắng"); // Sĩ Trắng
        board[0][5] = new Piece("仕", "Trắng"); // Sĩ Trắng
        board[9][3] = new Piece("士", "Đen"); // Sĩ Đen
        board[9][5] = new Piece("士", "Đen"); // Sĩ Đen

        // Đặt Tượng
        board[0][2] = new Piece("相", "Trắng"); // Tượng Trắng
        board[0][6] = new Piece("相", "Trắng"); // Tượng Trắng
        board[9][2] = new Piece("象", "Đen"); // Tượng Đen
        board[9][6] = new Piece("象", "Đen"); // Tượng Đen

        // Đặt Mã
        board[0][1] = new Piece("馬", "Trắng"); // Mã Trắng
        board[0][7] = new Piece("馬", "Trắng"); // Mã Trắng
        board[9][1] = new Piece("馬", "Đen"); // Mã Đen
        board[9][7] = new Piece("馬", "Đen"); // Mã Đen

        // Đặt Xe
        board[0][0] = new Piece("車", "Trắng"); // Xe Trắng
        board[0][8] = new Piece("車", "Trắng"); // Xe Trắng
        board[9][0] = new Piece("車", "Đen"); // Xe Đen
        board[9][8] = new Piece("車", "Đen"); // Xe Đen

        // Đặt Pháo
        board[2][1] = new Piece("砲", "Trắng"); // Pháo Trắng
        board[2][7] = new Piece("砲", "Trắng"); // Pháo Trắng
        board[7][1] = new Piece("砲", "Đen"); // Pháo Đen
        board[7][7] = new Piece("砲", "Đen"); // Pháo Đen

        // Đặt Tốt
        board[3][0] = new Piece("兵", "Trắng"); // Tốt Trắng
        board[3][2] = new Piece("兵", "Trắng"); // Tốt Trắng
        board[3][4] = new Piece("兵", "Trắng"); // Tốt Trắng
        board[3][6] = new Piece("兵", "Trắng"); // Tốt Trắng
        board[3][8] = new Piece("兵", "Trắng"); // Tốt Trắng

        board[6][0] = new Piece("卒", "Đen"); // Tốt Đen
        board[6][2] = new Piece("卒", "Đen"); // Tốt Đen
        board[6][4] = new Piece("卒", "Đen"); // Tốt Đen
        board[6][6] = new Piece("卒", "Đen"); // Tốt Đen
        board[6][8] = new Piece("卒", "Đen"); // Tốt Đen
    }

    private void UI() {
        setTitle("Cờ Tướng");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Sử dụng BorderLayout

        moveDescriptionLabel = new JLabel(""); // Khởi tạo JLabel
        moveDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa
        add(moveDescriptionLabel, BorderLayout.NORTH); // Thêm JLabel vào phía Bắc

        JPanel boardPanel = new JPanel(new GridLayout(10, 9)); // Tạo một JPanel cho bàn cờ
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                updateButton(i, j);
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    private void updateButton(int i, int j) {
        Piece piece = board[i][j];
        if (piece != null) {
            buttons[i][j].setText(piece.getName());
            buttons[i][j].setForeground(piece.getColor().equals("Đen") ? Color.BLACK : Color.WHITE);
        } else {
            buttons[i][j].setText("");
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int x, y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Piece clickedPiece = board[x][y];
            
            // Nếu đã chọn quân cờ trước đó
            if (selectedPiece != null) {
                // Nếu quân cờ được chọn là quân cờ cùng màu
                if (clickedPiece != null && clickedPiece.getColor().equals(currentPlayer)) {
                    // Đặt lại màu ô của quân cờ trước đó
                    buttons[selectedX][selectedY].setBackground(null);
                    selectedPiece = clickedPiece; // Cập nhật quân cờ đã chọn
                    selectedX = x;
                    selectedY = y;
                    buttons[x][y].setBackground(Color.YELLOW); // Tô màu ô mới
                    
                    // Hiển thị cách đi của quân cờ
                    String moveDescription = rule.cachDiQuanCo(selectedPiece);
                    moveDescriptionLabel.setText(moveDescription); // Cập nhật JLabel với hướng dẫn
                } else {
                    // Kiểm tra xem nước đi có hợp lệ không
                    if (rule.tinhHopLe(board, selectedPiece, selectedX, selectedY, x, y)) {
                        Piece capturedPiece = board[x][y];
                        board[selectedX][selectedY] = null;
                        board[x][y] = selectedPiece;
                        updateButton(selectedX, selectedY);
                        updateButton(x, y);
                        
                        if (capturedPiece != null) {
                            JOptionPane.showMessageDialog(null, "Bạn đã ăn quân " + capturedPiece.getName());
                        }
                        
                        checkGameStatus();
                        switchPlayer();
                    }
                    // Đặt lại màu ô của quân cờ trước đó
                    buttons[selectedX][selectedY].setBackground(null);
                    selectedPiece = null; // Đặt lại quân cờ đã chọn
                    selectedX = -1;
                    selectedY = -1;
                    moveDescriptionLabel.setText(""); // Xóa hướng dẫn
                }
            } else {
                // Nếu chưa chọn quân cờ nào
                if (clickedPiece != null && clickedPiece.getColor().equals(currentPlayer)) {
                    selectedPiece = clickedPiece; // Cập nhật quân cờ đã chọn
                    selectedX = x;
                    selectedY = y;
                    buttons[x][y].setBackground(Color.YELLOW); // Tô màu ô mới
                    
                    // Hiển thị cách đi của quân cờ
                    String moveDescription = rule.cachDiQuanCo(selectedPiece);
                    moveDescriptionLabel.setText(moveDescription); // Cập nhật JLabel với hướng dẫn
                }
            }
        } 
    }
    
    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("Trắng") ? "Đen" : "Trắng";
    }

    private void checkGameStatus() {
        // Kiểm tra tình trạng cờ (chiếu, chiếu bí)
        if (rule.checkChieuBi(board, currentPlayer)) {
            JOptionPane.showMessageDialog(null, currentPlayer + " đã thua!");
            resetGame();
        }
    }
    
    private void resetGame() {
        // Đặt lại bàn cờ về trạng thái ban đầu
        Board();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                updateButton(i, j);
            }
        }
        currentPlayer = "Trắng"; // Bắt đầu lại với người chơi Trắng
    }
}
