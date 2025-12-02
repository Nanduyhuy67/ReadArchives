import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.BorderFactory;

public class RegisterPage{
    public JPanel mainPanel;
    public JLabel ReadArchives;
    private JTextField textField1;
    private JButton makeAnAccountButton;
    private JPasswordField passwordField1;

    private RegisterPage registerPage;

    public RegisterPage() {
        SwingUtilities.invokeLater(() -> {
            if (ReadArchives != null) {
                ReadArchives.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(51,61,87), 2),
                                BorderFactory.createEmptyBorder(10, 20, 10, 20)
                        )
                );
            }
        });

        makeAnAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainControl.showWelcome();
            }
        });
    }
}
