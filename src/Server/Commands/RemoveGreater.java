package Server.Commands;

import Other.Requests.AddRequest;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.RegardsResponse;
import Other.SpaceMarines.SpaceMarine;
import Server.Interfaces.StandartCommand;

import java.time.LocalDate;

/**
 * Removes elements greater than given.
 */


public class RemoveGreater extends AbstractCommand implements StandartCommand {
    public RemoveGreater(String consoleName) {
        super(consoleName, "(standart) Remove marines greater than given", "Marines removed!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        AddRequest request = (AddRequest) requestDTO.getAbstractRequest();
        SpaceMarine comparator = new SpaceMarine(
                (long) (Math.random() * Long.MAX_VALUE),
                request.getName(),
                request.getCoordinates(),
                LocalDate.now(),
                request.getHealth(),
                request.getHeartCount(),
                request.getCategory(),
                request.getWeapon(),
                request.getChapter()
        );
        collectionProcessor.getCollection().removeIf(marine -> comparator.compareTo(marine) < 0);
        return new RegardsResponse(getConsoleName(), regards);
    }
}
