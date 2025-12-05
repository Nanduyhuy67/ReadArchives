import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage {
    public JPanel mainPanel;
    private JLabel ReadArchives;
    private JLabel WelcomeToReadArchives;
    private JLabel Login;
    private JLabel Password;
    private JTextField loginField;
    private JPasswordField passwordField1
;
    private JButton registerButton;
    private JButton loginButton;

    // Variabel untuk menyimpan warna
    private Color primaryColor = new Color(51, 61, 87);
    private Color secondaryColor = new Color(249, 206, 146);
    private Color backgroundColor = new Color(255, 255, 255);

    public WelcomePage() {
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

        // Tambahkan label ke header panel
        headerPanel.add(ReadArchives, BorderLayout.CENTER);

        // Berikan padding atas-bawah untuk header
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                fullWidthBorder, BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        // ========== CONTENT PANEL (TENGAH) ==========
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false); // Transparan agar background terlihat

        WelcomeToReadArchives = new JLabel("Welcome to ReadArchives!");
        WelcomeToReadArchives.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel loginSection = new JPanel();
        loginSection.setLayout(new BoxLayout(loginSection, BoxLayout.Y_AXIS));
        loginSection.setOpaque(false);
        loginSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        Login = new JLabel("Login:");
        Login.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginSection.add(Login);
        loginSection.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing kecil

        loginField = new JTextField();
        loginField.setMaximumSize(new Dimension(300, 35));
        loginField.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginSection.add(loginField);
        loginSection.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing antara field

        JPanel passwordSection = new JPanel();
        passwordSection.setLayout(new BoxLayout(passwordSection, BoxLayout.Y_AXIS));
        passwordSection.setOpaque(false);
        passwordSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        Password = new JLabel("Password:");
        Password.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordSection.add(Password);
        passwordSection.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing kecil

        passwordField1 = new JPasswordField();
        passwordField1.setMaximumSize(new Dimension(300, 35));
        passwordField1.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordSection.add(passwordField1);

        // Gabungkan kedua section ke formPanel
        formPanel.add(loginSection);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing antar section
        formPanel.add(passwordSection);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton = new JButton("Register");
        loginButton = new JButton("Login");

        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(loginButton);

        // Tambahkan semua ke content panel
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(WelcomeToReadArchives);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(formPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
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
        }

        // ========== WELCOME MESSAGE ==========
        if (WelcomeToReadArchives != null) {
            WelcomeToReadArchives.setFont(new Font("Arial", Font.PLAIN, 18));
            WelcomeToReadArchives.setForeground(Color.BLACK);
        }

        // ========== FORM LABELS ==========
        if (Login != null) {
            Login.setFont(new Font("Arial", Font.BOLD, 14));
            Login.setForeground(Color.BLACK);
        }

        if (Password != null) {
            Password.setFont(new Font("Arial", Font.BOLD, 14));
            Password.setForeground(Color.BLACK);
        }

        // ========== TEXT FIELDS ==========
        if (loginField != null) {
            loginField.setBackground(secondaryColor);
            Border loginFieldBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(secondaryColor, 2),
                    BorderFactory.createEmptyBorder(12, 50, 12, 50)
            );
            loginField.setBorder(loginFieldBorder);
//            loginField.setFocusPainted(false);
            loginField.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        if (passwordField1 != null) {
            passwordField1.setBackground(secondaryColor);
            Border passwordFieldBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(secondaryColor, 2),
                    BorderFactory.createEmptyBorder(12, 50, 12, 50)
            );
            passwordField1.setBorder(passwordFieldBorder);
//            passwordField1.setFocusPainted(false);
            passwordField1.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        // ========== BUTTONS ==========
        if (registerButton != null) {
            registerButton.setBackground(primaryColor);
            registerButton.setForeground(secondaryColor);
            registerButton.setFont(new Font("Arial", Font.BOLD, 14));
            Border registerBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryColor, 1),
                    BorderFactory.createEmptyBorder(12, 40, 12, 40)
            );
            registerButton.setBorder(registerBorder);
            registerButton.setFocusPainted(false);
        }

        if (loginButton != null) {
            loginButton.setBackground(primaryColor);
            loginButton.setForeground(secondaryColor);
            loginButton.setFont(new Font("Arial", Font.BOLD, 14));
            Border loginBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(primaryColor, 1),
                    BorderFactory.createEmptyBorder(12, 50, 12, 50)
            );
            loginButton.setBorder(loginBorder);
            loginButton.setFocusPainted(false);
        }
    }

    private void setupEventListeners() {
        if (registerButton != null) {
            registerButton.addActionListener(e -> MainControl.showRegister());
        }

        if (loginButton != null) {
            loginButton.addActionListener(e -> {
                String username = loginField.getText().trim();
                String password = new String(passwordField1.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please fill in both fields!",
                            "Input Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    MainControl.showDashboard();
                    loginField.setText("");
                    passwordField1.setText("");
                }
            });
        }

        if (passwordField1 != null) {
            passwordField1.addActionListener(e -> loginButton.doClick());
        }
    }
}