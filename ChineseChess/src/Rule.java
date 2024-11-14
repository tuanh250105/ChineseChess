public class Rule {
	public boolean isValidMove(Piece piece, int newRow, int newCol, Piece[][] board) {
		if (piece != null) {
			return piece.isValidMove(newRow, newCol, board);
		}
		return false;
	}
}
