import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.BorderFactory;

public class WelcomePage extends JFrame {
    public JPanel mainPanel;
    public JLabel ReadArchives;
    private JTextField loginField;
    private JButton registerButton;
    private JButton loginButton;
    private JPasswordField passwordField1;

    private void applyHeaderBorder() {
        ReadArchives.setOpaque(true);            // WAJIB
        ReadArchives.setBackground(Color.WHITE); // biar kontras

        ReadArchives.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
                        BorderFactory.createEmptyBorder(6, 14, 6, 14)
                )
        );

        ReadArchives.revalidate();
        ReadArchives.repaint();
    }

    public WelcomePage() {
//        ReadArchives.setOpaque(true);
//        ReadArchives.setBackground(Color.WHITE);
//        ReadArchives.setBorder(
//                BorderFactory.createCompoundBorder(
//                        BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
//                        BorderFactory.createEmptyBorder(8, 16, 8, 16)
//                )
//        );

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainControl.showRegister();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainControl.showDashboard();
            }
        });
    }
}


