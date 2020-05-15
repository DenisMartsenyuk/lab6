package arguments.builders;

import application.HandlerInput;
import arguments.checkers.DoubleLargerValue;
import arguments.checkers.IntegerLessValue;
import arguments.checkers.LongLargerValue;
import arguments.exceptions.CheckerException;
import arguments.exceptions.InvalidAmountArgumentException;
import arguments.exceptions.TypeException;
import arguments.valid.*;
import elements.*;

import java.io.IOException;
import java.util.*;

public class ProductBuilder extends ArgumentBuilder<Product>{

    public ProductBuilder(HandlerInput handlerInput) {
        super(handlerInput);

        addArgument(new ValidString(), "Введите значение поля \"name\" одним словом.");
        addArgument(new ValidDouble(), "Введите значение поля X для \"coordinates\" X - дробное, его надо записать через ТОЧКУ!");
        addArgument(new ValidInteger(new IntegerLessValue(999)), "Введите значение поля Y для \"coordinates\" Y - целое и меньше 999!");
        addArgument(new ValidDouble(new DoubleLargerValue(.0)), "Введите значение поля \"price\". Его значение должно быть > 0. Если число дробное, то его надо записать через ТОЧКУ!");
        addArgument(new ValidString(), "Введите значение поля \"partNumber\" одним словом.");
        addArgument(new ValidLong(), "Введите значение поля \"manufactureCost\" - Оно ЦЕЛОЕ!");
        addArgument(new ValidUnitOfMeasure(), "Введите значение поля \"unitOfMeasure\". Принимаемые значения:\n" + Arrays.toString(UnitOfMeasure.values()));
        addArgument(new ValidString(), "Введите значение поля \"name\" для \"manufacturer\" одним словом.");
        addArgument(new ValidLong(new LongLargerValue(0L)), "Введите значение поля \"annualTurnover\" для \"manufacturer\". Оно ЦЕЛОЕ!");
        addArgument(new ValidOrganizationType(), "Введите значение поля \"type\" для \"manufacturer\". Принимаемые значения:\n" + Arrays.toString(OrganizationType.values()));
    }

    @Override
    public Product build() {
        int index = 0;
        while (index < arguments.size()) {
            try {
                if (arguments.get(index).getResult() == null) {
                    if (handlerInput.isSIN())
                        System.out.println(descriptions.get(arguments.get(index)));
                    ArrayList<String> data = handlerInput.getData();
                    if (data.size() != 1) {
                        throw new InvalidAmountArgumentException();
                    }
                    arguments.get(index).get(data.get(0));
                    index++;
                }
            } catch (IOException e) {
                System.out.println("Ошибка ввода!");
            } catch (InvalidAmountArgumentException e) {
                if (handlerInput.isSIN())
                    System.out.println("Неверное количество аргументов!");
            } catch (TypeException e) {
                if (handlerInput.isSIN()) {
                    if (e.getMessage() == null) {
                        System.out.println("Тип введенных данных не соотвествует с указанному!");
                    } else {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (CheckerException e) {
                if (handlerInput.isSIN()) {
                    if (e.getMessage() == null) {
                        System.out.println("Введенные данные не подходят по ограничениям!");
                    } else {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return new Product((String) arguments.get(0).getResult(), new Coordinates((Double) arguments.get(1).getResult(),
                (Integer) arguments.get(2).getResult()), (Double) arguments.get(3).getResult(),
                (String) arguments.get(4).getResult(), (Long) arguments.get(5).getResult(),
                (UnitOfMeasure) arguments.get(6).getResult(), new Organization((String) arguments.get(7).getResult(),
                (Long) arguments.get(8).getResult(), (OrganizationType) arguments.get(9).getResult()));
    }
}