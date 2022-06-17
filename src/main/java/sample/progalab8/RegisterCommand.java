package sample.progalab8;

public class RegisterCommand implements Command {
    private Receiver theReceiver;


    public RegisterCommand(Receiver receiver){
        this.theReceiver = receiver;

    }
    @Override
    public SendThing execute() {
        return theReceiver.registerCommand();
    }
}
