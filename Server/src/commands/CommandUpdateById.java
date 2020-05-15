package commands;

import communication.Response;
import elements.Product;

public class CommandUpdateById extends Command {

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getManual() {
        return "Обновить значение элемента коллекции по полю \"id\". Параметры: id {element}.";
}

    @Override
    public Response execute() {
        return new Response(getName(), context.productList.updateById((Integer)arguments[0].getValue(), (Product)arguments[1].getValue()));
    }


}
