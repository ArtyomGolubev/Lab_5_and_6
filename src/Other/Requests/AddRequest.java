package Other.Requests;

import Other.SpaceMarines.*;

public class AddRequest extends AbstractRequest {
    private String name;
    private Coordinates coordinates;
    private float health;
    private Integer heartCount;
    private AstartesCategory category;
    private Weapon weapon;
    private Chapter chapter;

    public AddRequest(String commandName) {
        super(commandName);
    }

    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public float getHealth() {
        return health;
    }
    public Integer getHeartCount() {
        return heartCount;
    }
    public AstartesCategory getCategory() {
        return category;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public Chapter getChapter() {
        return chapter;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public void setHealth(float health) {
        this.health = health;
    }
    public void setHeartCount(Integer heartCount) {
        this.heartCount = heartCount;
    }
    public void setCategory(AstartesCategory category) {
        this.category = category;
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}
