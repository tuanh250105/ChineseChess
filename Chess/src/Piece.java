public class Piece {
    private String name;
    private String color;

    public Piece(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return name.equals(piece.name) && color.equals(piece.color);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + color.hashCode();
    }
    public void setColor(String color) {
        this.color = color;
    }
}