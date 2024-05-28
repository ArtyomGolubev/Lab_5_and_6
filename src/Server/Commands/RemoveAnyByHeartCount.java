package Server.Commands;

import Other.Requests.RemoveAnyByHeartCountRequest;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.RegardsResponse;
import Other.SpaceMarines.SpaceMarine;

/**
 * Removes element with a certain amount of hearts.
 */


public class RemoveAnyByHeartCount extends AbstractCommand {
    public RemoveAnyByHeartCount(String consoleName) {
        super(consoleName, "(int heartCount) Remove marine with a given heart count from the Imperium Army", "Marine removed!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        RemoveAnyByHeartCountRequest request = (RemoveAnyByHeartCountRequest) requestDTO.getAbstractRequest();
        SpaceMarine marine = collectionProcessor.getMarinesByHeartCount(request.getHeartCount())[0];
        collectionProcessor.getCollection().remove(marine);
        return new RegardsResponse(getConsoleName(), regards);
    }
}
