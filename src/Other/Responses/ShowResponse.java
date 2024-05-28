package Other.Responses;

import Other.SpaceMarines.SpaceMarine;

import java.util.LinkedList;

public class ShowResponse extends AbstractResponse {
    private LinkedList<SpaceMarine> marines;

    public ShowResponse(String commandName, String message) {
        super(commandName, message);
    }

    public LinkedList<SpaceMarine> getMarines() {
        return marines;
    }

    public void setMarines(LinkedList<SpaceMarine> marines) {
        this.marines = marines;
        StringBuilder stringBuilder = new StringBuilder();
        for (SpaceMarine m: this.marines) {
            stringBuilder.append(m).append("\n");
        }
        this.message = stringBuilder.toString();
    }

    @Override
    public String toString() {
        return this.message;
    }
}
