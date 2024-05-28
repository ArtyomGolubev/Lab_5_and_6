package Other.Requests;

public class ShowRequest extends AbstractRequest {
    private int amount = -1;

    public ShowRequest(String commandName) {
        super(commandName);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
