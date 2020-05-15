package commands;

import communication.Response;

public class CommandClear extends Command {

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getManual() {
        return "Очистить коллекцию.";
    }

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.clear());
    }
}
