package Server.Commands;

import Other.Requests.FilterContainsNameRequest;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.ShowResponse;
import Other.SpaceMarines.SpaceMarine;

import java.io.IOException;
import java.util.LinkedList;

/**
 *  Display marines whose 'name' field value contains a given substring.
 */


public class FilterContainsName extends AbstractCommand {
    public FilterContainsName(String consoleName) {
        super(consoleName, "(String name) Display marines whose 'name' field contains a given substring", "Command executed!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) throws IOException {
        FilterContainsNameRequest request = (FilterContainsNameRequest) requestDTO.getAbstractRequest();
        LinkedList<SpaceMarine> marines = collectionProcessor.getMarinesByName(request.getSubstring());
        ShowResponse response = new ShowResponse(getConsoleName(), "");
        response.setMarines(marines);
        return response;
    }
}