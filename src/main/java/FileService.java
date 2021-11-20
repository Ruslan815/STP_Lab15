import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class FileService {

    RecordService recordService;
    FrameMessageBox messageBox;

    public FileService() {
        this.recordService = PhoneBook.recordService;
    }

    public void writeRecordsToFile(String filename) {
        Properties properties = new Properties();
        properties.putAll(recordService.getRecords());
        try {
            properties.store(new FileOutputStream(filename), null);
        } catch (IOException exception) {
            messageBox = new FrameMessageBox("Ошибка записи в файл!");
            return;
        }
        messageBox = new FrameMessageBox("Контакты записаны в файл.");
    }

    public HashMap<String, String> readRecordsFromFile(String filename) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filename));
        } catch (IOException e) {
            messageBox = new FrameMessageBox("Ошибка считывания из файла!");
            return null;
        }
        HashMap<String, String> newRecords = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            newRecords.put(key, properties.get(key).toString());
        }
        messageBox = new FrameMessageBox("Контакты загружены из файла.");
        return newRecords;
    }
}
