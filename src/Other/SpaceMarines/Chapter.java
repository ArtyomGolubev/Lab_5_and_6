package Other.SpaceMarines;

import java.io.Serializable;
import java.util.Objects;

public class Chapter implements Serializable {

    private final String chaptername; //Поле не может быть null, Строка не может быть пустой
    private final String parentLegion;

    public Chapter(String name, String parentLegion) {
        this.chaptername = name;
        this.parentLegion = parentLegion;
    }

    public String getChapterName() {
        return chaptername;
    }

    public String getParentLegion() {
        return parentLegion;
    }

    @Override
    public String toString() {
        return "{" +
                "Name:" + chaptername + '\'' +
                ", Parent Legion:" + parentLegion +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Chapter chapter = (Chapter) obj;
        return Objects.equals(chaptername, chapter.chaptername) && Objects.equals(parentLegion, chapter.parentLegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chaptername, parentLegion);
    }
}