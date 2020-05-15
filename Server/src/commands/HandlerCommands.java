package commands;

import application.Context;
import communication.Request;
import communication.Response;

import java.util.HashMap;

public class HandlerCommands {

    private Context context;

    private HashMap<String, Command> commands;

    public HandlerCommands(Context context) {
        this.context = context;
        commands = new HashMap<>();
    }

    public HandlerCommands setCommand(Command command) {
        command.setContext(context);
        commands.put(command.getName(), command);
        return this;
    }

    public Response executeCommand(Request request) {
        commands.get(request.getName()).setParameters(request.getArguments());
        return commands.get(request.getName()).execute();
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

}
