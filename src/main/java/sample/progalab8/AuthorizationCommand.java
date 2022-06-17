package sample.progalab8;

public class AuthorizationCommand implements Command {
    private Receiver theReceiver;

    public AuthorizationCommand(Receiver receiver){
        this.theReceiver = receiver;
    }
    @Override
    public SendThing execute() {
        return theReceiver.authCommand();
    }
}
