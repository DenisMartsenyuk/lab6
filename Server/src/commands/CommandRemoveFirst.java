package commands;

import communication.Response;

public class CommandRemoveFirst extends Command {

    @Override
    public String getName() {
        return "remove_first";
    }

    @Override
    public String getManual() {
        return "Удалить первый элемент коллекции.";
    }

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.removeFirst());
    }

}
