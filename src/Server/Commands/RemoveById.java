package Server.Commands;

import Other.Exceptions.NotFoundException;
import Other.Requests.RemoveByIdRequest;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.EmptyResponse;
import Other.Responses.ErrorResponse;
import Other.Responses.RegardsResponse;

/**
 * Removes element from the collection by id.
 */

public class RemoveById extends AbstractCommand {
    public RemoveById(String consoleName) {
        super(consoleName, "(long id) Remove marine from the Imperium Army by id", "Marine removed!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        RemoveByIdRequest request = (RemoveByIdRequest) requestDTO.getAbstractRequest();
        if (request != null) {
            try {
                collectionProcessor.removeById(request.getId());
                return new RegardsResponse(getConsoleName(), regards);
            } catch (NotFoundException ex) {
                return new ErrorResponse(ex.toString());
            }
        } else {
            return new EmptyResponse();
        }
    }
}