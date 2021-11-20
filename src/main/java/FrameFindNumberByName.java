import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class FrameFindNumberByName extends Frame implements ActionListener {

    private TextField nameField;
    private TextField numberField;
    private FrameMessageBox messageBox;
    private RecordService recordService;

    public FrameFindNumberByName() {
        this.recordService = PhoneBook.recordService;

        Label nameLabel = new Label("Введите имя:");
        nameLabel.setBounds(10, 30, 150, 15);
        this.add(nameLabel);

        nameField = new TextField();
        nameField.setBounds(10, 50, 330, 25);
        this.add(nameField);

        Label numberLabel = new Label("Найденные номера:");
        numberLabel.setBounds(10, 80, 150, 15);
        this.add(numberLabel);

        numberField = new TextField();
        numberField.setBounds(10, 100, 330, 25);
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
        this.setTitle("Поиск номеров абонента");
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
                if (name.isEmpty()) {
                    messageBox = new FrameMessageBox("Введите имя!");
                    break;
                }
                if (!recordService.containsName(name)) {
                    messageBox = new FrameMessageBox("Такого имени нету в книге!");
                    break;
                }
                ArrayList<String> numbers = recordService.findMemberNumbersByName(name);
                numberField.setText(numbers.toString().replace("[", "").replace("]", ""));
                break;
            case "Отмена":
                dispose();
                break;
            default:
                messageBox = new FrameMessageBox("Unknown action!");
                break;
        }
    }
}
