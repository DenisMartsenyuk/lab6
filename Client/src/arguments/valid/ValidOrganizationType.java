package arguments.valid;

import arguments.checkers.Checker;
import arguments.exceptions.TypeException;
import elements.OrganizationType;

public class ValidOrganizationType extends ValidArgument<OrganizationType> {

    public ValidOrganizationType(Checker ... checkers) {
        super(checkers);
    }

    @Override
    public OrganizationType parse(String argument) throws TypeException {
        try {
            result = OrganizationType.valueOf(argument.toUpperCase());
            return result;
        } catch (IllegalArgumentException e) {
            throw new TypeException("Введенные данные не подходят под перечень!");
        } catch (NullPointerException e) {
            throw new TypeException("Данный enum пуст.");
        }
    }
}
