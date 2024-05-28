package Server.Commands;

import Other.Requests.AddRequest;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.EmptyResponse;
import Other.Responses.RegardsResponse;
import Other.SpaceMarines.SpaceMarine;
import Server.Interfaces.StandartCommand;

import java.time.LocalDate;

/**
 * Adds a new element to collection
 */


public class Add extends AbstractCommand implements StandartCommand {
    public Add(String consoleName) {
        super(consoleName, "(standart) Add a new marine to the Imperium Army", "New marine joined the Imperium Army ranks!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        AddRequest request = (AddRequest) requestDTO.getAbstractRequest();
        if (request != null) {
            SpaceMarine marine = new SpaceMarine(
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
            collectionProcessor.addNewMarine(marine);
            return new RegardsResponse(getConsoleName(), regards);
        }
        return new EmptyResponse();
    }
}