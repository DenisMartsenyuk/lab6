package commands;

import communication.Response;

public class CommandRemoveById extends Command {

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getManual() {
        return "Удалить элемент из коллекции по его \"id\". Параметры: id.";
    }

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.removeById((Integer)arguments[0].getValue()));
    }
}
