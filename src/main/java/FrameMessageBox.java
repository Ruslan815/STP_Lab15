import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameMessageBox extends Frame {

    public FrameMessageBox(String message) {

        Label text = new Label(message);
        text.setBounds(70, 75, 220, 25);
        this.add(text);

        this.setBackground(new Color(0x804DBE));
        this.setForeground(new Color(0x0926A2));
        this.setResizable(false);
        this.setBounds(600, 250, 300, 150);
        this.setTitle("Предупреждение!");
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
}
