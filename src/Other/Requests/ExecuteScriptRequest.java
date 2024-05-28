package Other.Requests;

import Client.ClientHandlers.Checker;

public class ExecuteScriptRequest extends AbstractRequest implements ExtendedRequest {
    String fileName;

    public ExecuteScriptRequest(String commandName) {
        super(commandName);
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public void setParameters(String... parameters) {
        if (Checker.ValidNameChecker(parameters[0])) {
            this.fileName = parameters[0];
        }
    }
}
