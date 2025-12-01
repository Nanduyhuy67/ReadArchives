import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton makeAnAccountButton;

    private RegisterPage registerPage;

    public RegisterPage() {
        makeAnAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if (registerPage == null) {
                    registerPage = new RegisterPage(); // Pass reference to MainGUI
                }
                registerPage.setVisible(true);
            }
        });
        add(makeAnAccountButton);
        setVisible(true);
    }
}
