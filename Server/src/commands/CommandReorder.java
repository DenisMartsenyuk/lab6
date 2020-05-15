package commands;

import communication.Response;

public class CommandReorder extends Command {

    @Override
    public String getName() {
        return "reorder";
    }

    @Override
    public String getManual() {
        return "Отсортировать коллекцию в порядке, обратном нынешнему.";
    }

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.reverse());
    }
}
