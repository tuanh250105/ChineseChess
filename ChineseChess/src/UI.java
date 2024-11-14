import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UI {

	private static final int ROWS = 10;
	private static final int COLS = 9;
	public static Piece[][] board = new Piece[ROWS][COLS];
	private static Piece selectedPiece = null;
	private static String currentTurn = "Trang";
	private static ArrayList<Point> highlightedMoves = new ArrayList<>();

	public static void initializeBoard() {

		board[0][4] = new King(0, 4, "Trang"); // Tướng Trắng
		board[9][4] = new King(9, 4, "Den"); // Tướng Đen

		board[0][3] = new Si(0, 3, "Trang"); // Sĩ Trắng
		board[0][5] = new Si(0, 5, "Trang"); // Sĩ Trắng

		board[9][3] = new Si(9, 3, "Den"); // Sĩ Đen
		board[9][5] = new Si(9, 5, "Den"); // Sĩ Đen

		board[0][2] = new Tuong(0, 2, "Trang"); // Tượng Trắng
		board[0][6] = new Tuong(0, 6, "Trang"); // Tượng Trắng

		board[9][2] = new Tuong(9, 2, "Den"); // Tượng Đen
		board[9][6] = new Tuong(9, 6, "Den"); // Tượng Đen

		board[0][1] = new Ma(0, 1, "Trang"); // Mã Trắng
		board[0][7] = new Ma(0, 7, "Trang"); // Mã Trắng

		board[9][1] = new Ma(9, 1, "Den"); // Mã Đen
		board[9][7] = new Ma(9, 7, "Den"); // Mã Đen

		board[0][0] = new Xe(0, 0, "Trang"); // Xe Trắng
		board[0][8] = new Xe(0, 8, "Trang"); // Xe Trắng

		board[9][0] = new Xe(9, 0, "Den"); // Xe Đen
		board[9][8] = new Xe(9, 8, "Den"); // Xe Đen

		board[2][1] = new Phao(2, 1, "Trang"); // Pháo Trắng
		board[2][7] = new Phao(2, 7, "Trang"); // Pháo Trắng

		board[7][1] = new Phao(7, 1, "Den"); // Pháo Đen
		board[7][7] = new Phao(7, 7, "Den"); // Pháo Đen

		// Tốt trắng
		board[3][0] = new Tot(3, 0, "Trang");
		board[3][2] = new Tot(3, 2, "Trang");
		board[3][4] = new Tot(3, 4, "Trang");
		board[3][6] = new Tot(3, 6, "Trang");
		board[3][8] = new Tot(3, 8, "Trang");

		// Tốt đen
		board[6][0] = new Tot(6, 0, "Den");
		board[6][2] = new Tot(6, 2, "Den");
		board[6][4] = new Tot(6, 4, "Den");
		board[6][6] = new Tot(6, 6, "Den");
		board[6][8] = new Tot(6, 8, "Den");
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
						if (selectedPiece == null && board[row][col] != null
								&& board[row][col].color.equals(currentTurn)) {
							selectedPiece = board[row][col];
							// 2
							System.out.println("Đã chọn quân " + selectedPiece.getSymbol() + " tại vị trí (" + (row + 1)
									+ ", " + (col + 1) + ")");
							highlightSelectedSquare(row, col, buttons);
							showValidMoves(selectedPiece, buttons);
						} else if (selectedPiece != null) {
							if (selectedPiece.isValidMove(row, col, board)) {
								System.out.println("Di chuyển quân " + selectedPiece.getSymbol() + " đến vị trí ("
										+ (row + 1) + ", " + (col + 1) + ")");
								int oldRow = selectedPiece.getRow();
								int oldCol = selectedPiece.getCol();
								clearHighlight(oldRow, oldCol, buttons);
								board[row][col] = selectedPiece;
								board[oldRow][oldCol] = null;
								selectedPiece.row = row;
								selectedPiece.col = col;
								selectedPiece = null;
								updateBoardUI(board, buttons);

								// Kiểm tra xem Tướng có bị ăn không
								if (isKingCaptured(currentTurn)) {
									System.out.println("Tướng của " + currentTurn + " đã bị ăn! Trò chơi kết thúc!");
									JOptionPane.showMessageDialog(null,
											"Tướng của " + currentTurn + " đã bị ăn! Trò chơi kết thúc!");
									System.exit(0);
								}

								// Kiểm tra chiếu bí
								if (isCheckmate(currentTurn)) {
									System.out.println("Chiếu bí! " + currentTurn + " đã thua!");
									JOptionPane.showMessageDialog(null, "Chiếu bí! " + currentTurn + " đã thua!");
									System.exit(0);
								}
								// Kiểm tra xem hai tướng có đối mặt nhau không
								if (areKingsFacingEachOther()) {
									System.out.println("Hai tướng đang đối mặt nhau!");
									JOptionPane.showMessageDialog(null, "Hai tướng đang đối mặt nhau!");
								}

								// Chuyển lượt
								currentTurn = currentTurn.equals("Trang") ? "Den" : "Trang"; // Chuyển lượt
							} else if (board[row][col] != null && board[row][col].color.equals(currentTurn)) {
								clearHighlightedMoves(buttons);
								clearHighlight(selectedPiece.getRow(), selectedPiece.getCol(), buttons);
								selectedPiece = board[row][col];
								highlightSelectedSquare(row, col, buttons);
								showValidMoves(selectedPiece, buttons);
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
					validMoves.add("(" + (i + 1) + ", " + (j + 1) + ")");
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
		buttons[row][col].setBackground(null);
	}

	// Hàm xóa màu các ô di chuyển hợp lệ
	private static void clearHighlightedMoves(JButton[][] buttons) {
		for (Point point : highlightedMoves) {
			buttons[point.x][point.y].setBackground(null);
		}
		highlightedMoves.clear(); 
	}

	// Hàm kiểm tra xem Tướng có bị ăn không
	private static boolean isKingCaptured(String color) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] instanceof King && board[i][j].color.equals(color)) {
					return false; 
				}
			}
		}
		return true; 
	}

	// Hàm kiểm tra xem hai tướng có đối mặt nhau hay không
	private static boolean areKingsFacingEachOther() {
		int kingWhiteRow = -1, kingWhiteCol = -1;
		int kingBlackRow = -1, kingBlackCol = -1;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] instanceof King) {
					if (board[i][j].color.equals("Trang")) {
						kingWhiteRow = i;
						kingWhiteCol = j;
					} else {
						kingBlackRow = i;
						kingBlackCol = j;
					}
				}
			}
		}

		if (kingWhiteRow == kingBlackRow) {
			int startCol = Math.min(kingWhiteCol, kingBlackCol) + 1;
			int endCol = Math.max(kingWhiteCol, kingBlackCol);
			for (int col = startCol; col < endCol; col++) {
				if (board[kingWhiteRow][col] != null) {
					return false; 
				}
			}
			return true; 
		}
		return false; 
	}

	// Hàm kiểm tra chiếu bí
	private static boolean isCheckmate(String color) {
		if (!isKingInCheck(color)) {
			return false;
		}

		// Kiểm tra tất cả các quân cờ
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] != null && board[i][j].color.equals(color)) {
					for (int x = 0; x < ROWS; x++) {
						for (int y = 0; y < COLS; y++) {
							if (board[i][j].isValidMove(x, y, board)) {
								Piece tempPiece = board[x][y];
								board[x][y] = board[i][j];
								board[i][j] = null;
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

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] instanceof King && board[i][j].color.equals(color)) {
					kingRow = i;
					kingCol = j;
					break;
				}
			}
		}

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] != null && !board[i][j].color.equals(color)) {
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
					buttons[i][j].setFont(new Font("Serif", Font.PLAIN, 30));

					// Đổi màu nền cho quân cờ Trang
					if (board[i][j].color.equals("Trang")) {
						buttons[i][j].setBackground(Color.WHITE); // Màu nền cho quân cờ Trang
					} else {
						buttons[i][j].setBackground(Color.LIGHT_GRAY); // Màu nền cho quân cờ Den
					}
				} else {
					buttons[i][j].setText("");
					buttons[i][j].setBackground(null);
				}
			}
		}
	}
}