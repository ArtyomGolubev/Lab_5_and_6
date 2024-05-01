package SpaceMarines;

import java.util.Objects;

public class Coordinates {
    private final Float x; //Максимальное значение поля: 138; Поле не может быть null
    private final float y;

    public Coordinates(Float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" +
                "x =" + x +
                ", y =" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates c = (Coordinates) obj;
        return y == c.y && Objects.equals(x, c.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}