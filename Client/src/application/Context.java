package application;

import arguments.TemplateServerCommand;
import arguments.builders.ProductBuilder;
import arguments.exceptions.CheckerException;
import arguments.exceptions.InvalidAmountArgumentException;
import arguments.exceptions.TypeException;
import arguments.valid.*;
import commands.CommandExecuteScript;
import commands.CommandExit;
import commands.CommandHelpClient;
import commands.HandlerCommands;
import communication.Request;
import communication.Response;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

public class Context {
    public HandlerServer handlerServer;
    public HandlerInput handlerInput;

    public HandlerCommands handlerCommands;
    public RequestBuilder requestBuilder;
    public HashMap<String, CommandType> commands;

    public Context() {
        handlerServer = new HandlerServer();

        handlerCommands = new HandlerCommands(this);
        requestBuilder = new RequestBuilder(this);
        commands = new HashMap<>();
    }

    public void initialization(String[] args) {
        if(args.length != 2) {
            System.out.println("Некорректно введены данные о хосте и порте!");
            System.exit(1);
        }

        String host = "";
        int port = 0;
        try {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Некорректно введены данные о хосте и порте!");
            System.exit(1);
        }

        handlerInput = new HandlerInput();

        requestBuilder.addCommand(new TemplateServerCommand("add").setBuildArgument(new ProductBuilder(handlerInput)))
                      .addCommand(new TemplateServerCommand("add_if_min").setBuildArgument(new ProductBuilder(handlerInput)))
                      .addCommand(new TemplateServerCommand("clear"))
                      .addCommand(new TemplateServerCommand("help"))
                      .addCommand(new TemplateServerCommand("info"))
                      .addCommand(new TemplateServerCommand("print_ascending"))
                      .addCommand(new TemplateServerCommand("filter_less_than_unit_of_measure").setValidArgument(new ValidUnitOfMeasure()))
                      .addCommand(new TemplateServerCommand("remove_by_id").setValidArgument(new ValidInteger()))
                      .addCommand(new TemplateServerCommand("remove_all_by_manufacture_cost").setValidArgument(new ValidLong()))
                      .addCommand(new TemplateServerCommand("remove_first"))
                      .addCommand(new TemplateServerCommand("reorder"))
                      .addCommand(new TemplateServerCommand("show"))
                      .addCommand(new TemplateServerCommand("update").setValidArgument(new ValidInteger()).setBuildArgument(new ProductBuilder(handlerInput)));

        handlerCommands.addCommand(new CommandExecuteScript().setValidArgument(new ValidString()))
                       .addCommand(new CommandHelpClient())
                       .addCommand(new CommandExit());

        try {
            handlerServer.connect(host, port);
        } catch (SocketException e) {
            System.out.println("Невозможно подключиться к серверу: " + e.getMessage());
            System.exit(1);
        }
    }

    private void processingRequests(ArrayList<Request> requests, int delayMillis) throws IOException, ClassNotFoundException {
        if(handlerInput.isSIN() && requests.size() > 0) {
            handlerServer.sendRequests(requests);
            ArrayList<Response> responses = handlerServer.receiveResponse(delayMillis);
            for (Response response : responses) {
                System.out.println(response.getNameCommand() + ": " + response.getResultComand());
            }
            requests.clear();
        }
        System.out.println();
    }

    public void run() {
        System.out.println("Клиентское приложение запущено.\n" +
                "Введите \"help\" для просмотра перечня команд сервера.\n" +
                "Введите \"help_client\" для просмотра перечня команд клиента.\n" +
                "Вводите команды:");
        ArrayList<Request> requests = new ArrayList<>();

        while (true) {
            ArrayList<String> data;
            try {
                if ((data = handlerInput.getData()).size() != 0) {
                    if(commands.get(data.get(0)) == null) {
                        throw new NullPointerException();
                    }
                    if(commands.get(data.get(0)) == CommandType.SERVER) {
                        requests.add(requestBuilder.getRequest(data));
                    } else if(commands.get(data.get(0)) == CommandType.CLIENT) {
                        System.out.println(handlerCommands.executeCommand(data));
                    }
                }
                if(requests.size() > 0)
                    processingRequests(requests, 200);
            } catch (NullPointerException e) {
                System.out.println("Не найдена такая команда!");
            } catch (InvalidAmountArgumentException e) {
                System.out.println("Неверное количество параметров!");
            } catch (TypeException e) {
                System.out.println("Тип введенных данных не соотвествует с указанному!");
            } catch (CheckerException e) {
                System.out.println("Введенные данные не подходят по ограничениям!");
            } catch (ClassNotFoundException e) {
                System.out.println("Не удалось дессериализовать ответ сервера.");
            } catch (SocketTimeoutException e) {
                System.out.println("Ответ на запрос не пришел. Возможно, сервер временно недоступен.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
