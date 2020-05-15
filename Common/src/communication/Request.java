package communication;

import java.io.Serializable;

public class Request implements Serializable {

    private String name;
    private Argument[] arguments;

    public Request(String name, Argument ... arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public Argument[] getArguments() {
        return arguments;
    }
}
