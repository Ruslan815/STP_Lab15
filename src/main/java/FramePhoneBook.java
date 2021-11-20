import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FramePhoneBook extends Frame implements ActionListener {

    private RecordService recordService;
    private final List memberList;
    private Button[] buttons;
    private FrameInputMemberData frameInputMemberData;
    private FrameMessageBox messageBox;
    private FrameFindNumberByName frameFindNumberByName;
    private FileService fileService;

    public FramePhoneBook() {
        this.recordService = PhoneBook.recordService;
        this.fileService = PhoneBook.fileService;

        Label listLabel = new Label("Список абонентов:");
        listLabel.setBounds(10, 32, 150, 15);

        memberList = new List();
        memberList.setBounds(10, 50, 520, 300);
        memberList.setMultipleMode(false);

        buttons = new Button[7];
        addButton(0, "Добавить", 10, 360, 100, 25);
        addButton(1, "Редактировать", 10, 390, 100, 25);
        addButton(2, "Удалить запись", 160, 360, 100, 25);
        addButton(3, "Очистить книгу", 140, 390, 140, 25);
        addButton(4, "Найти", 300, 360, 100, 55);
        addButton(5, "Записать в файл", 410, 360, 120, 25);
        addButton(6, "Загрузить из файла", 410, 390, 120, 25);

        this.add(listLabel);
        this.add(memberList);

        this.setBackground(new Color(0x804DBE));
        this.setForeground(new Color(0x0926A2));
        this.setResizable(false);
        this.setBounds(600, 250, 550, 440);
        this.setTitle("Phone Book by Ru815");
        this.setLayout(null);
        this.setVisible(true);

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        dispose();
                    }
                }
        );
    }

    private void addButton(int buttonId, String text, int x, int y, int width, int height) {
        buttons[buttonId] = new Button(text);
        buttons[buttonId].setBounds(x, y, width, height);
        buttons[buttonId].addActionListener(this);
        this.add(buttons[buttonId]);
    }

    public void addMemberToList(String memberInfo) {
        memberList.add(memberInfo);
    }

    public void addRecordsToList(HashMap<String, String> members) {
        ArrayList<String> newList = new ArrayList<>();
        for (Map.Entry<String, String> member : members.entrySet()) {
            String number = member.getKey();
            String name = member.getValue();
            newList.add(name + "|" + number);
        }
        Collections.sort(newList);
        for (String rec: newList) {
            addMemberToList(rec);
        }
        memberList.setVisible(true);
    }

    public void deleteMemberFromList(String memberInfo) {
        memberList.remove(memberInfo);
    }

    public void clearList() {
        memberList.removeAll();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Добавить":
                frameInputMemberData = new FrameInputMemberData(true, null, null);
                break;
            case "Редактировать":
                if (memberList.getSelectedIndex() == -1) {
                    messageBox = new FrameMessageBox("Выберите абонента!");
                } else {
                    String[] record = memberList.getItem(memberList.getSelectedIndex()).split("\\|");
                    String name = record[0];
                    String number = record[1];
                    frameInputMemberData = new FrameInputMemberData(false, name, number);
                }
                break;
            case "Удалить запись":
                if (memberList.getSelectedIndex() == -1) {
                    messageBox = new FrameMessageBox("Выберите абонента!");
                } else {
                    recordService.deleteRecord(memberList.getItem(memberList.getSelectedIndex()).split("\\|")[1]);
                    deleteMemberFromList(memberList.getItem(memberList.getSelectedIndex()));
                }
                break;
            case "Очистить книгу":
                recordService.deleteAllRecords();
                clearList();
                break;
            case "Найти":
                frameFindNumberByName = new FrameFindNumberByName();
                break;
            case "Записать в файл":
                fileService.writeRecordsToFile("db.txt");
                break;
            case "Загрузить из файла":
                HashMap<String, String> recordsFromFile = fileService.readRecordsFromFile("db.txt");
                if (recordsFromFile == null) break;
                recordService.setRecords(recordsFromFile);
                clearList();
                addRecordsToList(recordsFromFile);
                break;
            default:
                break;
        }
    }
}
