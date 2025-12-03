import javax.swing.*;

// fitur sampingan, kalo ga sempet gausah make gpp
public class EditProfilePage {
    private JCheckBox cBdisplay;
    private JTextField tFdisplay;
    private JCheckBox cBemail;
    private JTextField tFemail;
    private JCheckBox cBpassword;
    private JButton bTsave;
    private JButton bTcancel;
    private JLabel JEditProfile;
    private JLabel JSelect;
    private JPasswordField tFpassword;
    private JPanel editProfilePanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("EditProfilePage");
        frame.setContentPane(new EditProfilePage().editProfilePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public EditProfilePage() {

        // ---- Kondisi awal textfield = disabled ----
        tFdisplay.setEnabled(false);
        tFemail.setEnabled(false);
        tFpassword.setEnabled(false);

        // ---- Listener checkbox display ----
        cBdisplay.addActionListener(e ->
                tFdisplay.setEnabled(cBdisplay.isSelected())
        );

        // ---- Listener checkbox email ----
        cBemail.addActionListener(e ->
                tFemail.setEnabled(cBemail.isSelected())
        );

        // ---- Listener checkbox password ----
        cBpassword.addActionListener(e ->
                tFpassword.setEnabled(cBpassword.isSelected())
        );

        // ---- Tombol Save ----
        bTsave.addActionListener(e -> {

            if (cBdisplay.isSelected()) {
                String newDisplay = tFdisplay.getText();
                System.out.println("Display Name: " + newDisplay);
            }

            if (cBemail.isSelected()) {
                String newEmail = tFemail.getText();
                System.out.println("Email: " + newEmail);
            }

            if (cBpassword.isSelected()) {
                String newPass = new String(tFpassword.getPassword());
                System.out.println("Password: " + newPass);
            }

            JOptionPane.showMessageDialog(null,
                    "Changes saved successfully!");
        });

        // ---- Tombol Cancel ----
        bTcancel.addActionListener(e -> {
            // Reset semua pilihan
            cBdisplay.setSelected(false);
            cBemail.setSelected(false);
            cBpassword.setSelected(false);

            tFdisplay.setEnabled(false);
            tFemail.setEnabled(false);
            tFpassword.setEnabled(false);

            tFdisplay.setText("");
            tFemail.setText("");
            tFpassword.setText("");

            JOptionPane.showMessageDialog(null, "Changes canceled.");
        });
    }

    // Dipake kalau kamu mau return panel ini ke JFrame utama
    public JPanel getMainPanel() {
        return editProfilePanel;
    }

}

