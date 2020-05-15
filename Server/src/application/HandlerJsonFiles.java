package application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class HandlerJsonFiles {


    public static String readFile(String path) throws IOException {
        if(path == null)
            throw  new FileNotFoundException("Вы не ввели имя файла!");
        if(!path.substring(path.length() - 5, path.length()).equals(".json"))
            throw new FileNotFoundException("Этот файл не .json.");
        Path file = Paths.get(path);
        if (!Files.exists(file)) {
            throw new FileNotFoundException("Файла не существует или путь указан не верно! Создайте файл или измените путь и перезапустите программу.");
        }
        if (!Files.isReadable(file) && !Files.isWritable(file)) {
            throw new FileNotFoundException("Нет прав на чтение и запись! Предоставьте их и перезапустите программу.");
        }
        if (!Files.isReadable(file)) {
            throw new FileNotFoundException("Нет прав на чтение! Предоставьте их и перезапустите программу.");
        }
        if (!Files.isWritable(file)) {
            throw new FileNotFoundException("Нет прав на запись! Предоставьте их и перезапустите программу.");
        }
        String data = "";
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        while ((line = bufferedReader.readLine()) != null) {
            data = data + line + "\n";
        }
        bufferedReader.close();
        return data;
    }

    public static void saveFile(String path, String data) throws IOException {
        Path file = Paths.get(path);
        if (!Files.isWritable(file)) {
            throw new FileNotFoundException("Нет прав на запись! Все изменения коллекции не будут применены. Сохранение невозможно!");
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path));
        outputStreamWriter.write(data);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
