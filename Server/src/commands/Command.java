package commands;

import communication.Argument;
import application.Context;
import communication.Response;

public abstract class Command {

    protected Context context;
    protected Argument[] arguments;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setParameters(Argument[] arguments) {
        this.arguments = arguments;
    }

    public abstract String getName();

    public abstract String getManual();

    public abstract Response execute();
}
