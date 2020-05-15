package commands;

import communication.Response;
import elements.UnitOfMeasure;

public class CommandPrintLessUnitOfMeasure extends Command {

    @Override
    public String getName() {
        return "filter_less_than_unit_of_measure";
    }

    @Override
    public String getManual() {
        return "Вывести элементы, значения поля  \"unitOfMeasure\" которых меньше заданного. Параметры: unitOfMeasure";
    }

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.printLessThanUnitOfMeasure((UnitOfMeasure)arguments[0].getValue()));
    }
}
