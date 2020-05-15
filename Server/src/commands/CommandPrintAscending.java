package commands;

import communication.Response;

public class CommandPrintAscending extends Command {

    @Override
    public String getName() {
        return "print_ascending";
    }

    @Override
    public String getManual() {
        return "Вывести элементы коллекции в порядке возрастания";
    }

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.printAscending());
    }
}
