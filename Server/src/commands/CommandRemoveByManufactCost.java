package commands;

import communication.Response;

public class CommandRemoveByManufactCost extends Command {

    @Override
    public String getName() {
        return "remove_all_by_manufacture_cost";
    }

    @Override
    public String getManual() {
        return "Удалить элементы из коллекции, значение поля \"manufactureCost\" которого эквивалентно заданному. Параметры: manufactureCost.";
    }

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.removeAllByManufactureCost((Long)arguments[0].getValue()));
    }

}
