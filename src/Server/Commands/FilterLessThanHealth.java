package Server.Commands;

import Other.Requests.FilterLessThanHealthRequest;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.ShowResponse;
import Other.SpaceMarines.SpaceMarine;

import java.util.LinkedList;
import java.util.List;

/**
 * Display elements whose 'health' field value is less than a given one.
 */


public class FilterLessThanHealth extends AbstractCommand {
    public FilterLessThanHealth(String consoleName) {
        super(consoleName, "(float health) Display every marine with amount of HP less than given", "Command executed!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        FilterLessThanHealthRequest request = (FilterLessThanHealthRequest) requestDTO.getAbstractRequest();
        LinkedList<SpaceMarine> marines = new LinkedList<>(List.of(collectionProcessor.getMarinesWithLessThanCertainHealth(request.getHealth())));
        ShowResponse response = new ShowResponse(getConsoleName(), regards);
        response.setMarines(marines);
        return response;
    }
}