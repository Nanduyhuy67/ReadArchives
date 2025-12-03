import javax.swing.*;

public class ProfilePage {
    private JLabel JmyProfile;
    private JLabel Jstatistic;
    private JLabel Jdisplay;
    private JLabel JgetDisplay;
    private JLabel Jemail;
    private JLabel JgetEmail;
    private JLabel Jtotal;
    private JLabel Jreading;
    private JLabel Jcompleted;
    private JLabel JplanToRead;
    private JPanel profilePagePanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ProfilePage");
        frame.setContentPane(new ProfilePage().profilePagePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Constructor
    public ProfilePage() {
        // Contoh: data dummy (nanti diganti database)
        setUserData("ReadArchive", "user1@email.com");

        // Contoh statistik
        setStatistics(24, 5, 18, 12);
    }

    // Method untuk set data user
    public void setUserData(String displayName, String email) {
        JgetDisplay.setText(displayName);
        JgetEmail.setText(email);
    }

    // Method untuk set statistik
    public void setStatistics(int total, int reading, int completed, int planToRead) {
        Jtotal.setText("Total: " + total);
        Jreading.setText("Reading: " + reading);
        Jcompleted.setText("Completed: " + completed);
        JplanToRead.setText("Plan to Read: " + planToRead);
    }

    public JPanel getMainPanel() {
        return profilePagePanel;
    }
}

