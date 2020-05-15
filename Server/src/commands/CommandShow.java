package commands;

import communication.Response;

public class CommandShow extends Command {

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getManual() {
        return "Вывести все элементы коллекции в строковом представлении.";
    }

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.getAlphabet());
    }
}
