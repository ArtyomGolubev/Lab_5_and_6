package Other.Requests;

import Client.ClientHandlers.Checker;
import Other.Exceptions.WrongParameterException;
import Other.SpaceMarines.*;

public class UpdateRequest extends AbstractRequest implements ExtendedRequest {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private float health;
    private Integer heartCount;
    private AstartesCategory category;
    private Weapon weapon;
    private Chapter chapter;

    public UpdateRequest(String commandName) {
        super(commandName);
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

    @Override
    public void setParameters(String... parameters) throws WrongParameterException {
        if (!parameters[0].isEmpty()) {
            if (Checker.CorrectNumberChecker(parameters[0], Long.class)) {
                this.id = Long.parseLong(parameters[0]);
            } else {
                throw new WrongParameterException("Wrong number entered.");
            }
        } else {
            throw new WrongParameterException("Empty parameter entered.");
        }
    }
}
