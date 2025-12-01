import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton registerButton;
    private JButton loginButton;

    private RegisterPage registerPage;
    private DashboardPage dashboardPage;

    public WelcomePage() {
        initComponents();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if (registerPage == null) {
                    registerPage = new RegisterPage(); // Pass reference to MainGUI
                }
                registerPage.setVisible(true);
            }
        });

//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setVisible(false);
//                if (dashboardPage == null) {
//                    dashboardPage = new DashboardPage(); // Pass reference to MainGUI
//                }
//                dashboardPage.setVisible(true);
//            }
//        });

        setVisible(true);
    }

    public static void main(String[] args){
        new WelcomePage();
    }
}
