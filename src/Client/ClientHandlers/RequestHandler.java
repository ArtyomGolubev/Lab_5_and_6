package Client.ClientHandlers;

import Other.Exceptions.NonexistentCommandException;
import Other.Requests.AbstractRequest;
import java.util.HashMap;

public class RequestHandler {
    private HashMap<String, AbstractRequest> requestHashMap = new HashMap<>();

    public RequestHandler(AbstractRequest... requests) {
        for (AbstractRequest request : requests) {
            this.requestHashMap.put(request.getCommandName(), request);
        }
    }

    public void addRequest(AbstractRequest request) {
        this.requestHashMap.put(request.getCommandName(), request);
    }

    public AbstractRequest get(String commandName) throws NonexistentCommandException {
        AbstractRequest request = this.requestHashMap.get(commandName);
        if (request != null) {
            return request;
        } else {
            throw new NonexistentCommandException("Command does not exist.");
        }
    }
}