package Server.Commands;

import Other.Exceptions.NotFoundException;
import Other.Requests.RequestDTO;
import Other.Requests.UpdateRequest;
import Other.Responses.AbstractResponse;
import Other.Responses.ErrorResponse;
import Other.Responses.RegardsResponse;
import Other.SpaceMarines.*;

/**
 * Updates element of collection by id.
 * Offers user an opportunity to fill required fields
 */


public class Update extends AbstractCommand {
    public Update(String consoleName) {
        super(consoleName, "(long id) Update marine by id", "Marine is updated!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        UpdateRequest request = (UpdateRequest) requestDTO.getAbstractRequest();
        String name;
        Coordinates coordinates;
        float health;
        Integer heartCount;
        AstartesCategory category;
        Weapon weapon;
        Chapter chapter;

        try {
            SpaceMarine outdated = collectionProcessor.getMarineById(request.getId());
            if (!request.getName().isEmpty()) {
                name = request.getName();
            } else {
                name = outdated.getName();
            }
            if (request.getCoordinates() != null) {
                coordinates = request.getCoordinates();
            } else {
                coordinates = outdated.getCoordinates();
            }
            if (request.getHealth() != 0.0f) {
                health = request.getHealth();
            } else {
                health = outdated.getHealth();
            }
            if (request.getHeartCount() != 0) {
                heartCount = request.getHeartCount();
            } else {
                heartCount = outdated.getHeartCount();
            }
            if (request.getCategory() != null) {
                category = request.getCategory();
            } else {
                category = outdated.getCategory();
            }
            if (request.getWeapon() != null) {
                weapon = request.getWeapon();
            } else {
                weapon = outdated.getWeapon();
            }
            if (request.getChapter() != null) {
                chapter = request.getChapter();
            } else {
                chapter = outdated.getChapter();
            }

            SpaceMarine newMarine = new SpaceMarine(
                    outdated.getId(),
                    name,
                    coordinates,
                    outdated.getCreationDate(),
                    health,
                    heartCount,
                    category,
                    weapon,
                    chapter);
            collectionProcessor.setMarineById(outdated.getId(), newMarine);
            return new RegardsResponse(getConsoleName(), regards);
        } catch (NotFoundException ex) {
            return new ErrorResponse(ex.toString());
        }
    }

}
