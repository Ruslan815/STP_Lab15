import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameInputMemberData extends Frame implements ActionListener {

    private RecordService recordService;
    private TextField nameField;
    private TextField numberField;
    private final boolean isNew;
    private FrameMessageBox messageBox;
    private String memberNumber;

    public FrameInputMemberData(boolean isNewMember, String name, String number) {
        this.recordService = PhoneBook.recordService;
        isNew = isNewMember;
        if (!isNew) memberNumber = number;

        Label nameLabel = new Label("Введите имя:");
        nameLabel.setBounds(10, 30, 150, 15);
        this.add(nameLabel);

        nameField = new TextField();
        nameField.setBounds(10, 50, 330, 25);
        if (!isNew) nameField.setText(name);
        this.add(nameField);

        Label numberLabel = new Label("Введите номер телефона:");
        numberLabel.setBounds(10, 80, 150, 15);
        this.add(numberLabel);

        numberField = new TextField();
        numberField.setBounds(10, 100, 330, 25);
        if (!isNew) numberField.setText(number);
        this.add(numberField);

        Button okButton = new Button("Продолжить");
        okButton.setBounds(70, 140, 100, 25);
        okButton.addActionListener(this);
        this.add(okButton);

        Button cancelButton = new Button("Отмена");
        cancelButton.setBounds(180, 140, 100, 25);
        cancelButton.addActionListener(this);
        this.add(cancelButton);

        this.setBackground(new Color(0x804DBE));
        this.setForeground(new Color(0x0926A2));
        this.setResizable(false);
        this.setBounds(600, 250, 350, 190);
        this.setTitle("Ввод данных абонента");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Продолжить":
                String name = nameField.getText();
                String number = numberField.getText();
                if (name.isEmpty()) {
                    messageBox = new FrameMessageBox("Введите имя!");
                    break;
                } else if (number.isEmpty()) {
                    messageBox = new FrameMessageBox("Введите номер телефона!");
                    break;
                }

                if (isNew && recordService.containsNumber(number)) {
                    messageBox = new FrameMessageBox("Такой номер уже существует!");
                    break;
                }
                if (!isNew) {
                    recordService.deleteRecord(memberNumber);
                }
                recordService.addRecord(number, name);
                PhoneBook.mainFrame.clearList();
                PhoneBook.mainFrame.addRecordsToList(recordService.getRecords());
            case "Отмена":
                dispose();
                break;
            default:
                messageBox = new FrameMessageBox("Unknown action!");
                break;
        }
    }
}
