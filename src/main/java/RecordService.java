import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecordService {

    private HashMap<String, String> records; // <Number, Name>
    private HashMap<String, ArrayList<String>> inverseRecords; // <Name, Numbers[]>

    public RecordService() {
        records = new HashMap<>();
        inverseRecords = new HashMap<>();
    }

    public HashMap<String, String> getRecords() {
        return records;
    }

    public boolean containsNumber(String number) {
        return records.containsKey(number);
    }

    public boolean containsName(String name) {
        return inverseRecords.containsKey(name);
    }

    public void setRecords(HashMap<String, String> records) {
        this.records = records;
        inverseRecords.clear();
        for (Map.Entry<String, String> member : records.entrySet()) {
            String number = member.getKey();
            String name = member.getValue();
            if (!inverseRecords.containsKey(name)) {
                inverseRecords.put(name, new ArrayList<>());
            }
            inverseRecords.get(name).add(number);
        }
    }

    public void addRecord(String number, String name) {
        records.put(number, name);
        if (!inverseRecords.containsKey(name)) {
            inverseRecords.put(name, new ArrayList<>());
        }
        inverseRecords.get(name).add(number);
    }

    public void deleteRecord(String number) {
        //if (!records.containsKey(number)) return;
        String name = records.get(number);
        records.remove(number);
        inverseRecords.get(name).remove(number);
        if (inverseRecords.get(name).isEmpty()) inverseRecords.remove(name);
    }

    public void deleteAllRecords() {
        records.clear();
        inverseRecords.clear();
    }

    public ArrayList<String> findMemberNumbersByName(String name) {
        return inverseRecords.get(name);
    }
}
