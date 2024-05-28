package Other.Responses;

import java.io.Serializable;

public class ResponseDTO implements Serializable {
    private final AbstractResponse abstractResponse;

    public <T extends AbstractResponse> ResponseDTO(T abstractResponse) {
        this.abstractResponse = abstractResponse;
    }

    public AbstractResponse getAbstractResponse() {
        return abstractResponse;
    }
}
