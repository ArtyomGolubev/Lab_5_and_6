package SpaceMarines;

import java.time.LocalDate;
import java.util.Objects;

public class SpaceMarine implements Comparable<SpaceMarine> {

    private final Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final float health; //Значение поля должно быть больше 0
    private final Integer heartCount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 3
    private final AstartesCategory category; //Поле может быть null
    private final Weapon weaponType; //Поле не может быть null
    private final Chapter chapter; //Поле может быть null

    public SpaceMarine(Long id, String name, Coordinates coordinates, LocalDate creationDate, float health, Integer heartCount, AstartesCategory category, Weapon weaponType, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.heartCount = heartCount;
        this.category = category;
        this.weaponType = weaponType;
        this.chapter = chapter;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public float getHealth() {
        return health;
    }

    public Integer getHeartCount() {
        return heartCount;
    }

    public AstartesCategory getAstartesCategory() {
        return category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public Chapter getChapter() {
        return chapter;
    }

    @Override
    public String toString() {
        return "Marine: {" +
                "Id:" + id +
                ", Name:" + name +
                ", Coordinates:" + coordinates +
                ", CreationDate:" + creationDate +
                ", Amount of HP:" + health +
                ", Amount of hearts: " + heartCount +
                ", Astartes Category:" + category +
                ", Weapon:" + weaponType +
                ", Chapter:" + chapter +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SpaceMarine sm = (SpaceMarine) obj;
        return Objects.equals(id, sm.id) && Objects.equals(name, sm.name) && Objects.equals(coordinates, sm.coordinates) && Objects.equals(creationDate, sm.creationDate) && Objects.equals(health, sm.health) && Objects.equals(heartCount, sm.heartCount) && weaponType == sm.weaponType && Objects.equals(chapter, sm.chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, health, heartCount, category, weaponType, chapter);
    }

    @Override
    public int compareTo(SpaceMarine m) {
        int compareById = Long.compare(this.id, m.getId());
        if (compareById != 0) {
            return compareById;
        }

        int compareByName = this.name.compareTo(m.getName());
        if (compareByName != 0) {
            return compareByName;
        }

        int compareByHealth = Float.compare(this.health, m.getHealth());
        if (compareByHealth != 0) {
            return compareByHealth;
        }
        return this.heartCount.compareTo(m.getHeartCount());
    }
}