package arguments.valid;

import arguments.checkers.Checker;
import arguments.exceptions.TypeException;
import elements.OrganizationType;
import elements.UnitOfMeasure;

public class ValidUnitOfMeasure extends ValidArgument<UnitOfMeasure> {

    public ValidUnitOfMeasure(Checker ... checkers) {
        super(checkers);
    }

    @Override
    public UnitOfMeasure parse(String argument) throws TypeException {
        try {
            result = UnitOfMeasure.valueOf(argument.toUpperCase());
            return result;
        } catch (IllegalArgumentException e) {
            throw new TypeException("Введенные данные не подходят под перечень!");
        } catch (NullPointerException e) {
            throw new TypeException("Данный enum пуст.");
        }
    }
}
