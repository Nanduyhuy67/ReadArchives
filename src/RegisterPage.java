import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;
import dao.UserDAO;

public class RegisterPage {
    public JPanel mainPanel;
    private JLabel ReadArchives;
    private JLabel SignUp;
    private JLabel Email;
    private JLabel Password;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JButton makeAnAccountButton;
    private JButton exitButton;

    // Variabel untuk menyimpan warna (konsisten dengan WelcomePage)
    private Color primaryColor = new Color(51, 61, 87);
    private Color secondaryColor = new Color(249, 206, 146);
    private Color backgroundColor = new Color(255, 255, 255);

    public RegisterPage() {
        createUIManually();
        applyStyling();
        setupEventListeners();
    }

    private void createUIManually() {
        // **PANEL UTAMA YANG MEMBENTANG PENUH**
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        // ========== HEADER PANEL (MEMBENTANG PENUH ATAS) ==========
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);

        // **LABEL HEADER YANG MEMBENTANG PENUH**
        ReadArchives = new JLabel("ReadArchives", SwingConstants.LEFT);

        // **BORDER MEMBENTANG PENUH DI BAWAH HEADER**
        Border fullWidthBorder = BorderFactory.createMatteBorder(0, 0, 4, 0, primaryColor);
        headerPanel.setBorder(fullWidthBorder);

        // Tambahkan padding kiri untuk label agar tidak terlalu mentok
        ReadArchives.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // Tambahkan label ke header panel
        headerPanel.add(ReadArchives, BorderLayout.CENTER);

        // Berikan padding atas-bawah untuk header
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                fullWidthBorder, BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        // ========== CONTENT PANEL (TENGAH) DENGAN BORDER ==========
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(true); // Opaque agar background border terlihat
        contentPanel.setBackground(backgroundColor);

        // BORDER SEKELILING CONTENT PANEL
        Border contentBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor, 2), // Border luar
                BorderFactory.createEmptyBorder(30, 40, 30, 40) // Padding dalam
        );
        contentPanel.setBorder(contentBorder);
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        SignUp = new JLabel("Sign Up");
        SignUp.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form panel - UKURAN LEBIH KOMPAK
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel emailSection = new JPanel();
        emailSection.setLayout(new BoxLayout(emailSection, BoxLayout.Y_AXIS));
        emailSection.setOpaque(false);
        emailSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        Email = new JLabel("Email:");
        Email.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailSection.add(Email);
        emailSection.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing kecil

        emailField = new JTextField();
        // UKURAN DIKECILKAN - lebih pendek dari sebelumnya
        emailField.setMaximumSize(new Dimension(400, 35));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailSection.add(emailField);
        emailSection.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing antara field

        JPanel passwordSection = new JPanel();
        passwordSection.setLayout(new BoxLayout(passwordSection, BoxLayout.Y_AXIS));
        passwordSection.setOpaque(false);
        passwordSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        Password = new JLabel("Password:");
        Password.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordSection.add(Password);
        passwordSection.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing kecil

        passwordField1 = new JPasswordField();
        passwordField1.setMaximumSize(new Dimension(400, 35));
        passwordField1.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordSection.add(passwordField1);

        // Gabungkan kedua section ke formPanel
        formPanel.add(emailSection);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing antar section
        formPanel.add(passwordSection);

        // Button panel - MENGIKUTI POLA WELCOMEPAGE
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitButton = new JButton("Exit");
        makeAnAccountButton = new JButton("Make an Account");

        // TAMBAHKAN TOMBOL KE PANEL DENGAN GAP SEPERTI DI WELCOMEPAGE
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); // GAP KECIL 20px
        buttonPanel.add(makeAnAccountButton);

        // Tambahkan semua ke content panel
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(SignUp);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(formPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        // **WRAPPER PANEL UNTUK MEMUSATKAN KONTEN**
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerWrapper.add(contentPanel, gbc);

        // ========== ASSEMBLY ==========
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
    }

    private void applyStyling() {
        // ========== HEADER STYLING ==========
        if (ReadArchives != null) {
            ReadArchives.setFont(new Font("Arial", Font.BOLD, 36));
            ReadArchives.setForeground(secondaryColor);

            if (ReadArchives.getBorder() == null) {
                ReadArchives.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            }
        }

        // ========== SIGN UP MESSAGE ==========
        if (SignUp != null) {
            SignUp.setFont(new Font("Arial", Font.BOLD, 25)); // Diperbesar dan ditebalkan
            SignUp.setForeground(Color.BLACK);
        }

        // ========== FORM LABELS ==========
        if (Email != null) {
            Email.setFont(new Font("Arial", Font.BOLD, 14));
            Email.setForeground(Color.BLACK);
        }

        if (Password != null) {
            Password.setFont(new Font("Arial", Font.BOLD, 14));
            Password.setForeground(Color.BLACK);
        }

        // ========== TEXT FIELDS ==========
        if (emailField != null) {
            emailField.setBackground(secondaryColor);
            Border emailFieldBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(secondaryColor, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            );
            emailField.setBorder(emailFieldBorder);
            emailField.setFont(new Font("Arial", Font.PLAIN, 14));
            emailField.putClientProperty("JTextField.placeholderText", "Enter your email");

            // Set margin untuk teks agar tidak terlalu ke pinggir
            emailField.setMargin(new Insets(0, 5, 0, 5));
        }

        if (passwordField1 != null) {
            passwordField1.setBackground(secondaryColor);
            Border passwordFieldBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(secondaryColor, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            );
            passwordField1.setBorder(passwordFieldBorder);
            passwordField1.setFont(new Font("Arial", Font.PLAIN, 14));
            passwordField1.putClientProperty("JPasswordField.placeholderText", "Enter your password");

            // Set margin untuk teks agar tidak terlalu ke pinggir
            passwordField1.setMargin(new Insets(0, 5, 0, 5));

            // Set echo char untuk password
            passwordField1.setEchoChar('•');
        }

        // ========== BUTTONS ==========
        if (exitButton != null) {
            exitButton.setBackground(primaryColor);
            exitButton.setForeground(secondaryColor);
            exitButton.setFont(new Font("Arial", Font.BOLD, 14));
            Border exitBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryColor, 1),
                    BorderFactory.createEmptyBorder(10, 40, 10, 40)
            );
            exitButton.setBorder(exitBorder);
            exitButton.setFocusPainted(false);
        }

        if (makeAnAccountButton != null) {
            makeAnAccountButton.setBackground(primaryColor);
            makeAnAccountButton.setForeground(secondaryColor);
            makeAnAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
            Border accountBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryColor, 1),
                    BorderFactory.createEmptyBorder(10, 30, 10, 30)
            );
            makeAnAccountButton.setBorder(accountBorder);
            makeAnAccountButton.setFocusPainted(false);
        }
    }

    private void setupEventListeners() {
        // Listener untuk tombol Exit
        if (exitButton != null) {
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Konfirmasi sebelum kembali ke login
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure you want to go back to login?",
                            "Exit to Login",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        MainControl.showWelcome(); // Kembali ke WelcomePage (login)

                        // Reset form
                        emailField.setText("");
                        passwordField1.setText("");
                    }
                }
            });
        }

        // Listener untuk tombol Make an Account
        if (makeAnAccountButton != null) {
            makeAnAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String email = emailField.getText().trim();
                    String password = new String(passwordField1.getPassword()).trim();

                    if (email.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "Please fill in both fields!",
                                "Input Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Validasi email sederhana
                        if (!email.contains("@") || !email.contains(".")) {
                            JOptionPane.showMessageDialog(null,
                                    "Please enter a valid email address!",
                                    "Invalid Email", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Validasi password minimal 6 karakter
                        if (password.length() < 6) {
                            JOptionPane.showMessageDialog(null,
                                    "Password must be at least 6 characters long!",
                                    "Weak Password", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // --- LOGIKA BARU: REGISTER KE DATABASE ---
                        UserDAO userDAO = new UserDAO();

                        // Karena di form Register hanya ada Email, kita gunakan bagian depan email sebagai username sementara
                        String username = email.split("@")[0];

                        User newUser = new User(email, username, password);

                        if (userDAO.register(newUser)) {
                            JOptionPane.showMessageDialog(null, "Account created successfully! Please Login.");

                            MainControl.showWelcome(); // Pindah ke halaman Login

                            // Reset form
                            emailField.setText("");
                            passwordField1.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Registration failed. Email might already be taken.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        // ------------------------------------------
                    }
                }
            });
        }

        // Tambahkan listener untuk Enter key di password field
        if (passwordField1 != null) {
            passwordField1.addActionListener(e -> makeAnAccountButton.doClick());
        }
    }
}