import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import model.User;
import dao.UserDAO;

public class EditProfilePage {
    private JPanel editProfilePanel;
    private JCheckBox cBdisplay;
    private JTextField tFdisplay;
    private JCheckBox cBemail;
    private JTextField tFemail;
    private JCheckBox cBpassword;
    private JPasswordField tFpassword;
    private JButton bTsave;
    private JButton bTcancel;
    private JLabel JEditProfile;
    private JLabel JSelect;

    private Color primaryColor = new Color(51, 61, 87);
    private Color secondaryColor = new Color(249, 206, 146);
    private Color backgroundColor = Color.WHITE;

    public EditProfilePage() {
        createUI();
        applyStyling();
        setupEvents();

    }

    //Untuk menyimpan data user saat ini
    private String originalDisplayName;
    private String originalEmail;
    private String originalPassword;

    private JPanel createRow(JCheckBox checkBox, JComponent field) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);

        // Checkbox fix width
        checkBox.setPreferredSize(new Dimension(140, 30));
        checkBox.setMaximumSize(new Dimension(140, 30));
        checkBox.setMinimumSize(new Dimension(140, 30));

        // Field fix width
        field.setPreferredSize(new Dimension(250, 30));
        field.setMaximumSize(new Dimension(250, 30));
        field.setMinimumSize(new Dimension(250, 30));

        row.add(checkBox);
        row.add(Box.createRigidArea(new Dimension(15, 0)));
        row.add(field);

        return row;
    }

    private void createUI() {
        editProfilePanel = new JPanel();
        editProfilePanel.setLayout(new BorderLayout());
        editProfilePanel.setBackground(backgroundColor);

        // TITLE BAR
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(primaryColor);

        JEditProfile = new JLabel("EDIT PROFILE", SwingConstants.CENTER);
        JEditProfile.setForeground(secondaryColor);
        JEditProfile.setFont(new Font("Arial", Font.BOLD, 28));
        titlePanel.add(JEditProfile);

        editProfilePanel.add(titlePanel, BorderLayout.NORTH);

        // CONTENT
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel selectRow = new JPanel();
        selectRow.setLayout(new BoxLayout(selectRow, BoxLayout.X_AXIS));
        selectRow.setOpaque(false);

        selectRow.add(Box.createRigidArea(new Dimension(0, 0)));
        selectRow.add(Box.createRigidArea(new Dimension(0, 0)));

        JSelect = new JLabel("Check fields you want to edit:");
        JSelect.setFont(new Font("Arial", Font.BOLD, 16));

        selectRow.add(JSelect);

        contentPanel.add(Box.createRigidArea(new Dimension(140, 0)));
        contentPanel.add(selectRow);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Display Name
        cBdisplay = new JCheckBox("Display Name");
        tFdisplay = new JTextField();
        tFdisplay.setBackground(new Color(240, 240, 240));
        contentPanel.add(createRow(cBdisplay, tFdisplay));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Email
        cBemail = new JCheckBox("Email");
        tFemail = new JTextField();
        tFemail.setBackground(new Color(240, 240, 240));
        contentPanel.add(createRow(cBemail, tFemail));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Password
        cBpassword = new JCheckBox("Password");
        tFpassword = new JPasswordField();
        tFpassword.setBackground(new Color(240, 240, 240));
        contentPanel.add(createRow(cBpassword, tFpassword));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // BUTTONS
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        bTsave = new JButton("Save Changes");
        bTcancel = new JButton("Cancel");

        buttonPanel.add(bTsave);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(bTcancel);

        contentPanel.add(buttonPanel);

        editProfilePanel.add(contentPanel, BorderLayout.CENTER);

        // Disable awal
        tFdisplay.setEnabled(false);
        tFemail.setEnabled(false);
        tFpassword.setEnabled(false);
    }

    private void applyStyling() {
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        tFdisplay.setFont(labelFont);
        tFemail.setFont(labelFont);
        tFpassword.setFont(labelFont);

        Border fieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        tFdisplay.setBorder(fieldBorder);
        tFemail.setBorder(fieldBorder);
        tFpassword.setBorder(fieldBorder);

        bTsave.setBackground(primaryColor);
        bTsave.setForeground(secondaryColor);
        bTsave.setFocusPainted(false);

        bTcancel.setBackground(primaryColor);
        bTcancel.setForeground(secondaryColor);
        bTcancel.setFocusPainted(false);
    }

    private void setupEvents() {
        cBdisplay.addActionListener(e -> {
            boolean selected = cBdisplay.isSelected();
            tFdisplay.setEnabled(selected);
            tFdisplay.setBackground(selected ? Color.WHITE : new Color(240, 240, 240));

            if (selected) {
                tFdisplay.selectAll();
            }
        });

        cBemail.addActionListener(e -> {
            boolean selected = cBemail.isSelected();
            tFemail.setEnabled(selected);
            tFemail.setBackground(selected ? Color.WHITE : new Color(240, 240, 240));

            if (selected) {
                tFemail.selectAll();
            }
        });

        cBpassword.addActionListener(e -> {
            boolean selected = cBpassword.isSelected();
            tFpassword.setEnabled(selected);
            tFpassword.setBackground(selected ? Color.WHITE : new Color(240, 240, 240));
        });

        bTsave.addActionListener(e -> {
            // 1. Validasi input
            if (!validateInput()) {
                return;
            }

            // 2. Simpan perubahan ke ProfilePage via MainControl
            saveChangesToProfile();

            // 3. Reset form
            resetForm();

            // 4. Kembali ke ProfilePage
            MainControl.showProfilePage();

            // 5. Tampilkan pesan sukses
            JOptionPane.showMessageDialog(null,
                    "Changes saved successfully!");
        });

        bTcancel.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to cancel? Changes will be lost.",
                    "Cancel Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                // Reset form
                resetForm();

                // Kembali ke ProfilePage
                MainControl.showProfilePage();
            }
        });
    }

    // ===== METHODS BARU UNTUK INTEGRASI =====

    private boolean validateInput() {
        // Validasi email format
        if (cBemail.isSelected()) {
            String email = tFemail.getText().trim();
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid email address (example@domain.com)",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Validasi password length
        if (cBpassword.isSelected()) {
            char[] password = tFpassword.getPassword();
            if (password.length < 6) {
                JOptionPane.showMessageDialog(null,
                        "Password must be at least 6 characters",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Validasi display name tidak kosong jika dipilih
        if (cBdisplay.isSelected()) {
            String displayName = tFdisplay.getText().trim();
            if (displayName.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Display name cannot be empty",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Simple email validation
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }

    private void saveChangesToProfile() {
        // 1. Tentukan nilai baru (Gunakan nilai baru jika checkbox dicentang, jika tidak gunakan nilai asli)
        String finalDisplayName = cBdisplay.isSelected() ? tFdisplay.getText().trim() : originalDisplayName;
        String finalEmail = cBemail.isSelected() ? tFemail.getText().trim() : originalEmail;
        String finalPassword = cBpassword.isSelected() ? new String(tFpassword.getPassword()) : originalPassword;

        // 2. Siapkan objek User dengan data baru
        User updatedUser = new User();
        updatedUser.setDisplayName(finalDisplayName);
        updatedUser.setEmail(finalEmail);
        updatedUser.setPassword(finalPassword);
        // Username tidak diubah, boleh dikosongkan atau diset sembarang karena tidak masuk query update
        updatedUser.setUsername(finalEmail.split("@")[0]);

        // 3. Panggil DAO untuk update database
        UserDAO dao = new UserDAO();
        boolean success = dao.update(updatedUser, originalEmail); // Gunakan originalEmail untuk WHERE clause

        if (success) {
            // Jika database berhasil diupdate, baru update UI
            MainControl.updateUserProfile(finalDisplayName, finalEmail);

            // Update juga password di ProfilePage (tambahkan method ini di MainControl nanti)
            MainControl.updateUserPassword(finalPassword);

            // Tampilkan pesan sukses
            JOptionPane.showMessageDialog(null, "Profile updated successfully!");

            // Reset form dan navigasi kembali
            resetForm();
            MainControl.showProfilePage();
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update profile. Email might be already taken.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        cBdisplay.setSelected(false);
        cBemail.setSelected(false);
        cBpassword.setSelected(false);

        tFdisplay.setEnabled(false);
        tFemail.setEnabled(false);
        tFpassword.setEnabled(false);

        tFdisplay.setText("");
        tFemail.setText("");
        tFpassword.setText("");

        tFdisplay.setBackground(new Color(240, 240, 240));
        tFemail.setBackground(new Color(240, 240, 240));
        tFpassword.setBackground(new Color(240, 240, 240));
    }

    // Method untuk pre-fill data dari ProfilePage
    public void setCurrentData(String displayName, String email, String password) {
        // Simpan data asli
        this.originalDisplayName = displayName;
        this.originalEmail = email;
        this.originalPassword = password;

        if (displayName != null) {
            tFdisplay.setText(displayName);
        }
        if (email != null) {
            tFemail.setText(email);
        }
        // Password field biarkan kosong atau reset
        tFpassword.setText("");
    }

    public JPanel getEditProfilePanel() {
        return editProfilePanel;
    }

    // Test standalone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Edit Profile");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 600);
            frame.setContentPane(new EditProfilePage().getEditProfilePanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}