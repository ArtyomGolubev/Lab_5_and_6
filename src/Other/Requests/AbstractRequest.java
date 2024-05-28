package Other.Requests;

import java.io.Serializable;

public abstract class AbstractRequest implements Serializable {
    protected String commandName;
    protected String message = null;

    public AbstractRequest(String commandName) {
        this.commandName = commandName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommandName() {
        return commandName;
    }

    @Override
    public String toString() {
        return "Request {" +
                "commandName ='" + commandName + '\'' +
                ", message ='" + message + '\'' +
                '}';
    }
}
