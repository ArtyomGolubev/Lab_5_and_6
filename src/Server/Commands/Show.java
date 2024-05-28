package Server.Commands;

import Other.Requests.RequestDTO;
import Other.Requests.ShowRequest;
import Other.Responses.AbstractResponse;
import Other.Responses.EmptyResponse;
import Other.Responses.ErrorResponse;
import Other.Responses.ShowResponse;
import Other.SpaceMarines.SpaceMarine;
import Server.Interfaces.StandartCommand;

import java.util.LinkedList;

/**
 * Shows the collection.
 */


public class Show extends AbstractCommand implements StandartCommand {

    public Show(String consoleName) {
        super(consoleName, "(int amount) Shows the Imperium Army list", "Imperium Army list shown!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        ShowRequest request = (ShowRequest) requestDTO.getAbstractRequest();
        if (request != null) {
            if (request.getAmount() < 0) {
                ShowResponse response = new ShowResponse(getConsoleName(), regards);
                response.setMarines(collectionProcessor.getCollection());
                return response;
            } else {
                LinkedList<SpaceMarine> marinesToSend = new LinkedList<>();
                if (request.getAmount() <= collectionProcessor.getCollection().size()) {
                    for (int i = 0; i < request.getAmount(); i++) {
                        marinesToSend.add(collectionProcessor.getCollection().get(i));
                    }
                    ShowResponse response = new ShowResponse(getConsoleName(), regards);
                    response.setMarines(marinesToSend);
                    return response;
                } else {
                    return new ErrorResponse("The army is big and fearsome.. But not like that.");
                }
            }
        } else {
            return new EmptyResponse();
        }
    }
}
