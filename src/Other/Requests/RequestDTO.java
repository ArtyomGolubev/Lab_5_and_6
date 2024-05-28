package Other.Requests;

import java.io.Serializable;

public class RequestDTO implements Serializable {
    private AbstractRequest abstractRequest;

    public <T extends AbstractRequest> RequestDTO(T abstractRequest) {
        this.abstractRequest = abstractRequest;
    }

    public AbstractRequest getAbstractRequest() {
        return abstractRequest;
    }
}
