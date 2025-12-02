import javax.swing.*;

public class MainControl {

    private static JFrame frame;

    public static void main(String[] args){
        frame = new JFrame("ReadArchives");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showWelcome();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void showWelcome(){
        frame.setContentPane(new WelcomePage().mainPanel);
        frame.revalidate();
    }

    public static void showRegister(){
        frame.setContentPane(new RegisterPage().mainPanel);
        frame.revalidate();
    }

    public static void showDashboard(){
        frame.setContentPane(new DashboardPage().mainPanel);
        frame.revalidate();
    }
}