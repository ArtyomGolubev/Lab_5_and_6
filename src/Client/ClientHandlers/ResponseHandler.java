package Client.ClientHandlers;

import Other.Responses.*;

public class ResponseHandler {
    public String handleResponse(AbstractResponse response) {
        if (response instanceof ErrorResponse) {
            return "ERROR: " + response;
        } else if (response instanceof EmptyResponse) {
            return null;
        } else if (response instanceof RegardsResponse) {
            return "EXECUTED: " + response;
        } else if (response instanceof ExitResponse) {
            System.exit(0);
        }
        return response.toString();
    }
}