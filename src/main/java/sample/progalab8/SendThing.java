package sample.progalab8;

import java.io.Serializable;

public class SendThing implements Serializable {
    private Ticket ticket;
    private String command;
    private String argument;
    private String login;

    public SendThing(Ticket ticket, String command, String argument, String login) {
        this.ticket = ticket;
        this.command = command;
        this.argument = argument;
        this.login = login;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "SendThing{" +
                "ticket=" + ticket +
                ", command='" + command + '\'' +
                ", argument='" + argument + '\'' +
                '}';
    }
}
