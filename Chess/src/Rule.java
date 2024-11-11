public class Rule {
    public boolean tinhHopLe(Piece[][] board, Piece piece, int startX, int startY, int endX, int endY) {
        switch (piece.getName()) {
            case "将":
            	return diChuyenKing(piece.getColor(), startX, startY, endX, endY);
            case "帥":
                return diChuyenKing(piece.getColor(), startX, startY, endX, endY);
            case "仕":
            	return diChuyenSi(piece.getColor(), startX, startY, endX, endY);
            case "士":
                return diChuyenSi(piece.getColor(), startX, startY, endX, endY);
            case "象":
            	return diChuyenTuong(board,piece.getColor(), startX, startY, endX, endY);
            case "相":
                return diChuyenTuong(board,piece.getColor(), startX, startY, endX, endY);
            case "車":
                return diChuyenXe(board, startX, startY, endX, endY);
            case "馬":
                return diChuyenMa(board, startX, startY, endX, endY);
            case "炮":
            	return diChuyenPhao(board, startX, startY, endX, endY);
            case "砲":
                return diChuyenPhao(board, startX, startY, endX, endY);
            case "兵":
            	return diChuyenTot(piece.getColor(), startX, startY, endX, endY);
            case "卒":
                return diChuyenTot(piece.getColor(), startX, startY, endX, endY);
            default:
                return false;
        }
    }
    //Kiểm tra di chuyển của Tướng
    private boolean diChuyenKing(String color, int startX, int startY, int endX, int endY) {
        if (Math.abs(startX - endX) + Math.abs(startY - endY) == 1) {
            if (color.equals("Đen")) {
                return endX >= 7 && endX <= 9 && endY >= 3 && endY <= 5; // Cung Đen
            } else {
                return endX >= 0 && endX <= 2 && endY >= 3 && endY <= 5; // Cung Trắng
            }
        }
        return false;
    }
    //Kiểm tra di chuyển của Sĩ
    private boolean diChuyenSi(String color, int startX, int startY, int endX, int endY) {
        if (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 1) {
            if (color.equals("Đen")) {
                return endX >= 7 && endX <= 9 && endY >= 3 && endY <= 5; // Cung Đen
            } else {
                return endX >= 0 && endX <= 2 && endY >= 3 && endY <= 5; // Cung Trắng
            }
        }
        return false;
    }
    //Kiểm tra di chuyển của Tượng
    private boolean diChuyenTuong(Piece[][] board,String color, int startX, int startY, int endX, int endY) {
        if (Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 2) {
			if (color.equals("Trắng")) {
                return endX <= 4 && !oBiChiem(board, (startX + endX) / 2, (startY + endY) / 2); // Tượng chỉ được ở nửa bàn cờ của Trắng và không bị cản
            } else {
                return endX >= 5 && !oBiChiem(board, (startX + endX) / 2, (startY + endY) / 2); // Tượng chỉ được ở nửa bàn cờ của Đen và không bị cản
            }
        }
        return false;
    }
    //Kiểm tra di chuyển của Xe
    private boolean diChuyenXe(Piece[][] board, int startX, int startY, int endX, int endY) {
        if (startX == endX) {
            return checkDuongBiChan(board, startX, startY, endX, endY, true);
        } else if (startY == endY) {
            return checkDuongBiChan(board, startX, startY, endX, endY, false);
        }
        return false;
    }
    //Kiểm tra di chuyển của Mã
    private boolean diChuyenMa(Piece[][] board, int startX, int startY, int endX, int endY) {
        if ((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || 
            (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) {
            if (Math.abs(startX - endX) == 2) {
                if (oBiChiem(board, startX + (endX - startX) / 2, startY)) return false; // Cản ngang
            } else {
                if (oBiChiem(board, startX, startY + (endY - startY) / 2)) return false; // Cản dọc
            }
            return true;
        }
        return false;
    }
    //Kiểm tra di chuyển của Pháo
    private boolean diChuyenPhao(Piece[][] board, int startX, int startY, int endX, int endY) {
    	if (startX == endX) {
            int obstacleCount = 0;
            for (int i = Math.min(startY, endY) + 1; i < Math.max(startY, endY); i++) {
                if (oBiChiem(board, startX, i)) obstacleCount++;
            }
            return (obstacleCount == 0 || obstacleCount == 1);
        } else if (startY == endY) {
            int obstacleCount = 0;
            for (int i = Math.min(startX, endX) + 1; i < Math.max(startX, endX); i++) {
                if (oBiChiem(board, i, startY)) obstacleCount++;
            }
            return (obstacleCount == 0 || obstacleCount == 1);
        }
        return false;
    }
    //Kiểm tra di chuyển của Tốt
    private boolean diChuyenTot(String color, int startX, int startY, int endX, int endY) {
    	if (color.equals("Trắng")) {
            if (startX <= 4) {
                return endX == startX + 1 && startY == endY; // Chỉ đi thẳng khi chưa qua sông
            } else {
                return (endX == startX && Math.abs(endY - startY) == 1) || // Đi ngang
                       (endX == startX + 1 && endY == startY); // Đi thẳng
            }
        } else {
            if (startX >= 5) {
                return endX == startX - 1 && startY == endY; // Chỉ đi thẳng khi chưa qua sông
            } else {
                return (endX == startX && Math.abs(endY - startY) == 1) || // Đi ngang
                       (endX == startX - 1 && endY == startY); // Đi thẳng
            }
        }
    }
    //Kiểm tra ô bị chiếm
    private boolean oBiChiem(Piece[][] board, int x, int y) {
        return board[x][y] != null;
    }
    //Kiểm tra đường đi bị chặn
    private boolean checkDuongBiChan(Piece[][] board, int startX, int startY, int endX, int endY, boolean isHorizontal) {
        int min = isHorizontal ? Math.min(startY, endY) + 1 : Math.min(startX, endX) + 1;
        int max = isHorizontal ? Math.max(startY, endY) : Math.max(startX, endX);

        for (int i = min; i < max; i++) {
            int x = isHorizontal ? startX : i;
            int y = isHorizontal ? i : startY;
            if (oBiChiem(board, x, y)) {
                return false; // Đường đi bị chặn
            }
        }
        return true;
    }
    //Kiểm tra chiếu bí
    public boolean checkChieuBi(Piece[][] board, String color) {
        // Tìm vị trí của Tướng
        int kingX = -1, kingY = -1;
        String kingName = color.equals("Trắng") ? "帥" : "將";

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getName().equals(kingName) && piece.getColor().equals(color)) {
                    kingX = i;
                    kingY = j;
                    break;
                }
            }
            if (kingX != -1) break;
        }

        // Kiểm tra xem Tướng có đang bị chiếu không
        if (!chieuTuong(board, kingX, kingY, color)) {
            return false; // Nếu không bị chiếu, không phải chiếu bí
        }

        // Kiểm tra tất cả các nước đi hợp lệ của Tướng
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (Math.abs(dx) + Math.abs(dy) == 1) { // Chỉ di chuyển 1 ô
                    int newX = kingX + dx;
                    int newY = kingY + dy;
                    if (tinhHopLe(board, new Piece(kingName, color), kingX, kingY, newX, newY)) {
                        // Nếu có nước đi hợp lệ, không phải chiếu bí
                        return false;
                    }
                }
            }
        }

        // Nếu không có nước đi hợp lệ, thì là chiếu bí
        return true;
    }
    	

    private boolean chieuTuong(Piece[][] board, int kingX, int kingY, String color) {
    	// Kiểm tra xem Tướng có bị chiếu không
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(color)) {
                    // Nếu quân cờ không cùng màu, kiểm tra xem nó có thể tấn công Tướng không
                    if (tinhHopLe(board, piece, i, j, kingX, kingY)) {
                        return true; // Tướng đang bị chiếu
                    }
                }
            }
        }
        return false; // Tướng không bị chiếu
    }
    
    
    //Mô tả nước đi
    public String cachDiQuanCo(Piece piece) {
        switch (piece.getName()) {
            case "将":
            	return "将: Đi một ô ngang hoặc dọc trong phạm vi cung (3x3).";
            case "帥":
                return "帥: Đi một ô ngang hoặc dọc trong phạm vi cung (3x3).";
            case "仕":
            	return "仕: Đi chéo 1 ô trong phạm vi cung (3x3).";
            case "士":
                return "士: Đi chéo 1 ô trong phạm vi cung (3x3).";
            case "象":
            	return "象: Đi chéo 2 ô và không được qua sông.";
            case "相":
                return "相: Đi chéo 2 ô và không được qua sông.";
            case "車":
                return "車: Đi ngang hoặc dọc, không bị cản đường.";
            case "馬":
                return "馬: Đi theo hình chữ L, nếu có quân cản ở bên cạnh thì không di chuyển được.";
            case "炮":
            	return "炮: Đi ngang hoặc dọc như xe, nhưng phải nhảy qua đúng 1 quân để ăn quân.";
            case "砲":
                return "砲: Đi ngang hoặc dọc như xe, nhưng phải nhảy qua đúng 1 quân để ăn quân.";
            case "兵":
            	return "兵: Đi một ô thẳng, khi qua sông có thể đi ngang hoặc thẳng.";
            case "卒":
                return "卒: Đi một ô thẳng, khi qua sông có thể đi ngang hoặc thẳng.";
            default:
                return "";
        }
    }
}
