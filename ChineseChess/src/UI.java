import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UI {

	private static final int ROWS = 10;
	private static final int COLS = 9;
	public static Piece[][] board = new Piece[ROWS][COLS];
	private static Piece selectedPiece = null; // Biến để lưu quân cờ đang được chọn
	private static String currentTurn = "Trắng"; // Biến để theo dõi lượt hiện tại
	private static ArrayList<Point> highlightedMoves = new ArrayList<>(); // Danh sách các ô đã tô màu

	public static void initializeBoard() {

		board[0][4] = new King(0, 4, "Trắng"); // Tướng Trắng
		board[9][4] = new King(9, 4, "Đen"); // Tướng Đen
		board[0][3] = new Si(0, 3, "Trắng"); // Sĩ Trắng
		board[0][5] = new Si(0, 5, "Trắng"); // Sĩ Trắng
		board[9][3] = new Si(9, 3, "Đen"); // Sĩ Đen
		board[9][5] = new Si(9, 5, "Đen"); // Sĩ Đen
		board[0][2] = new Tuong(0, 2, "Trắng"); // Tượng Trắng
		board[0][6] = new Tuong(0, 6, "Trắng"); // Tượng Trắng
		board[9][2] = new Tuong(9, 2, "Đen"); // Tượng Đen
		board[9][6] = new Tuong(9, 6, "Đen"); // Tượng Đen
		board[0][1] = new Ma(0, 1, "Trắng"); // Mã Trắng
		board[0][7] = new Ma(0, 7, "Trắng"); // Mã Trắng
		board[9][1] = new Ma(9, 1, "Đen"); // Mã Đen
		board[9][7] = new Ma(9, 7, "Đen"); // Mã Đen
		board[0][0] = new Xe(0, 0, "Trắng"); // Xe Trắng
		board[0][8] = new Xe(0, 8, "Trắng"); // Xe Trắng
		board[9][0] = new Xe(9, 0, "Đen"); // Xe Đen
		board[9][8] = new Xe(9, 8, "Đen"); // Xe Đen
		board[2][1] = new Phao(2, 1, "Trắng"); // Pháo Trắng
		board[2][7] = new Phao(2, 7, "Trắng"); // Pháo Trắng
		board[7][1] = new Phao(7, 1, "Đen"); // Pháo Đen
		board[7][7] = new Phao(7, 7, "Đen"); // Pháo Đen

		// Đặt Tốt Trắng ở các ô trên hàng 3
		board[3][0] = new Tot(3, 0, "Trắng");
		board[3][2] = new Tot(3, 2, "Trắng");
		board[3][4] = new Tot(3, 4, "Trắng");
		board[3][6] = new Tot(3, 6, "Trắng");
		board[3][8] = new Tot(3, 8, "Trắng");

		// Đặt Tốt Đen ở các ô trên hàng 6
		board[6][0] = new Tot(6, 0, "Đen");
		board[6][2] = new Tot(6, 2, "Đen");
		board[6][4] = new Tot(6, 4, "Đen");
		board[6][6] = new Tot(6, 6, "Đen");
		board[6][8] = new Tot(6, 8, "Đen");
	}

	public static void showUI(Piece[][] board) {
		JFrame frame = new JFrame("Bàn cờ Tướng");
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(ROWS, COLS));
		JButton[][] buttons = new JButton[ROWS][COLS];

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setPreferredSize(new Dimension(60, 60));
				// 1
				// Hiển thị quân cờ trên bàn cờ
				if (board[i][j] != null) {
					buttons[i][j].setText(board[i][j].getSymbol());
					buttons[i][j].setFont(new Font("Serif", Font.PLAIN, 30));
				}

				final int row = i;
				final int col = j;
				buttons[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Nếu chưa chọn quân, chọn quân và đánh dấu
						if (selectedPiece == null && board[row][col] != null
								&& board[row][col].color.equals(currentTurn)) {
							selectedPiece = board[row][col];
							// 2
							System.out.println("Đã chọn quân " + selectedPiece.getSymbol() + " tại vị trí (" + row
									+ ", " + col + ")");
							highlightSelectedSquare(row, col, buttons); // Tô màu ô cờ đang chọn
							showValidMoves(selectedPiece, buttons); // Hiển thị nước đi hợp lệ
						}
						// Nếu đã chọn quân, kiểm tra di chuyển hợp lệ và thực hiện di chuyển
						else if (selectedPiece != null) {

							if (selectedPiece.isValidMove(row, col, board)) {
								System.out.println("Di chuyển quân " + selectedPiece.getSymbol() + " đến vị trí (" + row
										+ ", " + col + ")");
								int oldRow = selectedPiece.getRow();
								int oldCol = selectedPiece.getCol();
								clearHighlight(oldRow, oldCol, buttons); // Xóa màu ô cờ đã chọn
								// Di chuyển quân tới ô mới
								board[row][col] = selectedPiece;
								board[oldRow][oldCol] = null; // Xóa quân cũ
								selectedPiece.row = row; // Cập nhật vị trí quân
								selectedPiece.col = col;
								selectedPiece = null; // Bỏ chọn quân
								updateBoardUI(board, buttons); // Cập nhật giao diện

								// Kiểm tra xem Tướng có bị ăn không
								if (isKingCaptured(currentTurn)) {
									JOptionPane.showMessageDialog(null,
											"Tướng của " + currentTurn + " đã bị ăn! Trò chơi kết thúc!");
									System.exit(0);
								}

								// Kiểm tra chiếu bí
								if (isCheckmate(currentTurn)) {
									JOptionPane.showMessageDialog(null, "Chiếu bí! " + currentTurn + " đã thua!");
									System.exit(0);
								}

								// Chuyển lượt
								currentTurn = currentTurn.equals("Trắng") ? "Đen" : "Trắng"; // Chuyển lượt
							} else if (board[row][col] != null && board[row][col].color.equals(currentTurn)) {
								// Nếu chọn quân cùng màu, bỏ chọn quân cũ và chọn quân mới
								clearHighlightedMoves(buttons); // Xóa màu các ô di chuyển hợp lệ
								clearHighlight(selectedPiece.getRow(), selectedPiece.getCol(), buttons);
								selectedPiece = board[row][col];
								highlightSelectedSquare(row, col, buttons); // Tô màu ô cờ đang chọn
								showValidMoves(selectedPiece, buttons); // Hiển thị nước đi hợp lệ
							}
						}
					}
				});

				boardPanel.add(buttons[i][j]);
			}
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.add(boardPanel);
		frame.setVisible(true);
	}

	// Hàm hiển thị nước đi hợp lệ cho quân cờ đã chọn
	private static void showValidMoves(Piece piece, JButton[][] buttons) {
		ArrayList<String> validMoves = new ArrayList<>();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (piece.isValidMove(i, j, board)) {
					validMoves.add("(" + i + ", " + j + ")");
					buttons[i][j].setBackground(Color.GREEN); // Tô màu xanh cho ô di chuyển hợp lệ
					highlightedMoves.add(new Point(i, j));
				}
				if (board[i][j] != null && !board[i][j].color.equals(piece.color) && piece.isValidMove(i, j, board)) {
					buttons[i][j].setBackground(Color.RED); // Tô màu đỏ cho ô có thể ăn
				}
			}
		}
		System.out.println("Nước đi hợp lệ cho quân " + piece.getSymbol() + ": " + String.join(", ", validMoves));
	}

	// Hàm tô màu ô cờ đang chọn
	private static void highlightSelectedSquare(int row, int col, JButton[][] buttons) {
		buttons[row][col].setBackground(Color.YELLOW); // Tô màu vàng cho ô đang chọn
	}

	// Hàm xóa màu ô cờ đã chọn
	private static void clearHighlight(int row, int col, JButton[][] buttons) {
		buttons[row][col].setBackground(null); // Khôi phục màu sắc ban đầu
	}

	// Hàm xóa màu các ô di chuyển hợp lệ
	private static void clearHighlightedMoves(JButton[][] buttons) {
		for (Point point : highlightedMoves) {
			buttons[point.x][point.y].setBackground(null); // Khôi phục màu sắc ban đầu
		}
		highlightedMoves.clear(); // Xóa danh sách các ô đã tô màu
	}

	// Hàm kiểm tra xem Tướng có bị ăn không
	private static boolean isKingCaptured(String color) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] instanceof King && board[i][j].color.equals(color)) {
					return false; // Tướng còn tồn tại
				}
			}
		}
		return true; // Tướng đã bị ăn
	}

	// Hàm kiểm tra chiếu bí
	private static boolean isCheckmate(String color) {
		// Kiểm tra xem Tướng có bị chiếu không
		if (!isKingInCheck(color)) {
			return false; // Nếu không bị chiếu, không phải chiếu bí
		}

		// Kiểm tra tất cả các quân cờ của người chơi
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] != null && board[i][j].color.equals(color)) {
					// Kiểm tra tất cả các nước đi hợp lệ cho quân cờ
					for (int x = 0; x < ROWS; x++) {
						for (int y = 0; y < COLS; y++) {
							if (board[i][j].isValidMove(x, y, board)) {
								// Tạm thời di chuyển quân cờ
								Piece tempPiece = board[x][y];
								board[x][y] = board[i][j];
								board[i][j] = null;

								// Kiểm tra xem Tướng có bị chiếu không sau khi di chuyển
								if (!isKingInCheck(color)) {
									// Khôi phục lại bàn cờ
									board[i][j] = board[x][y];
									board[x][y] = tempPiece;
									return false;
								}

								// Khôi phục lại bàn cờ
								board[i][j] = board[x][y];
								board[x][y] = tempPiece;
							}
						}
					}
				}
			}
		}

		return true;
	}

	// Hàm kiểm tra chiếu Tướng
	private static boolean isKingInCheck(String color) {
		int kingRow = -1;
		int kingCol = -1;

		// Tìm vị trí của Tướng
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] instanceof King && board[i][j].color.equals(color)) {
					kingRow = i;
					kingCol = j;
					break;
				}
			}
		}

		// Kiểm tra tất cả các quân cờ của đối thủ
		String opponentColor = color.equals("Trắng") ? "Đen" : "Trắng";
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] != null && board[i][j].color.equals(opponentColor)) {
					if (board[i][j].isValidMove(kingRow, kingCol, board)) {
						return true; // Tướng bị chiếu
					}
				}
			}
		}

		return false;
	}

	// Hàm cập nhật lại bàn cờ trên giao diện
	private static void updateBoardUI(Piece[][] board, JButton[][] buttons) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] != null) {
					buttons[i][j].setText(board[i][j].getSymbol());
					buttons[i][j].setFont(new Font("Serif", Font.PLAIN, 30)); // Đặt lại kích thước font chữ

					// Đổi màu nền cho quân cờ trắng
					if (board[i][j].color.equals("Trắng")) {
						buttons[i][j].setBackground(Color.WHITE); // Màu nền cho quân cờ trắng
					} else {
						buttons[i][j].setBackground(Color.LIGHT_GRAY); // Màu nền cho quân cờ đen
					}
				} else {
					buttons[i][j].setText("");
					buttons[i][j].setBackground(null); // Khôi phục màu nền cho ô trống
				}
			}
		}
	}
}