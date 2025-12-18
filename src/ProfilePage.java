import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePage {
    private JPanel profilePagePanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Navigation Components
    private JButton BMyProfile;
    private JButton BLibrary;
    private JButton BExit;

    // Profile Section Components (untuk card PROFILE)
    private JLabel JMyProfile;
    private JLabel JDisplayValue;
    private JLabel JEmailValue;
    private JLabel JPasswordValue;
    private JButton BEditProfile;
    private JButton BShowPassword;

    // Statistics Section Components
    private JLabel JStatistics;
    private JLabel JTotalValue;
    private JLabel JReadingValue;
    private JLabel JCompletedValue;
    private JLabel JPlanToReadValue;

    // Color Scheme
    private Color primaryColor = new Color(51, 61, 87);
    private Color secondaryColor = new Color(249, 206, 146);
    private Color backgroundColor = Color.WHITE;
    private Color panelBackground = new Color(245, 247, 250);

    // Data storage
    private String currentDisplayName = "ReadArchive";
    private String currentEmail = "user@example.com";
    private String currentPassword = "";
    private boolean passwordVisible = false;

    public ProfilePage() {
        createUI();
        applyStyling();
        setupEvents();
        loadDummyData();

        // Set tombol PROFILE sebagai aktif
        setActiveButton(BMyProfile);
    }

    private void createUI() {
        profilePagePanel = new JPanel();
        profilePagePanel.setLayout(new BorderLayout());
        profilePagePanel.setBackground(panelBackground);

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel headerTitle = new JLabel("ReadArchive");
        headerTitle.setForeground(secondaryColor);
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));

        headerPanel.add(headerTitle, BorderLayout.WEST);
        profilePagePanel.add(headerPanel, BorderLayout.NORTH);

        // MAIN CONTENT PANEL
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(panelBackground);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // NAVIGATION PANEL (tombol-tombol)
        JPanel navPanel = createNavigationPanel();
        mainContentPanel.add(navPanel, BorderLayout.NORTH);

        // CARD LAYOUT PANEL (hanya untuk PROFILE card)
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(panelBackground);

        // Card 1: PROFILE (satu-satunya card)
        JPanel profileCard = createProfileCard();
        cardPanel.add(profileCard, "PROFILE");

        mainContentPanel.add(cardPanel, BorderLayout.CENTER);
        profilePagePanel.add(mainContentPanel, BorderLayout.CENTER);
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        navPanel.setOpaque(false);
        navPanel.setBackground(panelBackground);

        // Container untuk tabs dengan background putih
        JPanel tabsContainer = new JPanel();
        tabsContainer.setLayout(new BoxLayout(tabsContainer, BoxLayout.X_AXIS));
        tabsContainer.setBackground(Color.WHITE);
        tabsContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        tabsContainer.setMaximumSize(new Dimension(450, 45));

        BMyProfile = createNavButton("PROFILE");
        BLibrary = createNavButton("LIBRARY");
        BExit = createNavButton("EXIT");

        tabsContainer.add(BMyProfile);
        tabsContainer.add(BLibrary);
        tabsContainer.add(BExit);

        navPanel.add(tabsContainer);
        return navPanel;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setBackground(Color.WHITE);
        button.setForeground(primaryColor);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Dimension buttonSize = new Dimension(150, 45);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        if (text.equals("EXIT")) {
            button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        return button;
    }

    private JPanel createProfileCard() {
        JPanel profileCard = new JPanel(new BorderLayout());
        profileCard.setBackground(panelBackground);

        // CONTENT PANEL untuk Profile
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();

        // LEFT PANEL - MY PROFILE
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 40);

        JPanel profilePanel = createProfileContentPanel();
        contentPanel.add(profilePanel, gbc);

        // RIGHT PANEL - STATISTICS
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 40, 0, 0);

        JPanel statsPanel = createStatisticsPanel();
        contentPanel.add(statsPanel, gbc);

        profileCard.add(contentPanel, BorderLayout.CENTER);
        return profileCard;
    }

    private JPanel createProfileContentPanel() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        profilePanel.setBackground(backgroundColor);
        profilePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Header dengan Edit Profile - DI TENGAH ATAS
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0)); // Spasi bawah lebih besar

        JMyProfile = new JLabel("MY PROFILE");
        JMyProfile.setFont(new Font("Arial", Font.BOLD, 18));
        JMyProfile.setForeground(primaryColor);

        BEditProfile = new JButton("Edit Profile");
        BEditProfile.setFont(new Font("Arial", Font.PLAIN, 12));
        BEditProfile.setFocusPainted(false);
        BEditProfile.setPreferredSize(new Dimension(90, 28));

        headerPanel.add(JMyProfile, BorderLayout.WEST);
        headerPanel.add(BEditProfile, BorderLayout.EAST);

        // Profile Details - SAMA SEPERTI SEBELUMNYA TAPI DI TENGAH
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        detailsPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Display Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 8, 0); // Spasi bawah kecil untuk label
        JLabel displayLabel = new JLabel("Display Name:");
        displayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        displayLabel.setForeground(new Color(85, 85, 85));
        detailsPanel.add(displayLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0); // Spasi bawah lebih besar untuk value
        JDisplayValue = new JLabel("ReadArchive");
        JDisplayValue.setFont(new Font("Arial", Font.PLAIN, 14));
        JDisplayValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        detailsPanel.add(JDisplayValue, gbc);

        // Email
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 8, 0);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(new Color(85, 85, 85));
        detailsPanel.add(emailLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        JEmailValue = new JLabel("user@example.com");
        JEmailValue.setFont(new Font("Arial", Font.PLAIN, 14));
        JEmailValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        detailsPanel.add(JEmailValue, gbc);

        // Password
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 8, 0);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(85, 85, 85));
        detailsPanel.add(passwordLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0); // Tidak ada spasi bawah
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        passwordPanel.setBackground(Color.WHITE);

        JPasswordValue = new JLabel("••••••••");
        JPasswordValue.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordValue.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        BShowPassword = new JButton("👁");
        BShowPassword.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        BShowPassword.setPreferredSize(new Dimension(35, 28));
        BShowPassword.setFocusPainted(false);
        BShowPassword.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        BShowPassword.setBackground(new Color(245, 245, 245));
        BShowPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        BShowPassword.setToolTipText("Show password");

        passwordPanel.add(JPasswordValue, BorderLayout.CENTER);
        passwordPanel.add(BShowPassword, BorderLayout.EAST);

        detailsPanel.add(passwordPanel, gbc);

        // Tambahkan ruang kosong di bawah untuk push content ke atas
        gbc.gridy = 6;
        gbc.weighty = 1.0; // Berat vertikal untuk mengisi ruang kosong
        gbc.fill = GridBagConstraints.VERTICAL;
        detailsPanel.add(Box.createVerticalGlue(), gbc);

        profilePanel.add(headerPanel, BorderLayout.NORTH);
        profilePanel.add(detailsPanel, BorderLayout.CENTER);

        // UKURAN: LEBAR TETAP, TINGGI SAMA STATISTICS
        profilePanel.setPreferredSize(new Dimension(280, 380));
        profilePanel.setMaximumSize(new Dimension(280, 380));
        profilePanel.setMinimumSize(new Dimension(260, 380));

        return profilePanel;
    }

    // STATISTICS PANEL - BESAR TETAP
    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout());
        statsPanel.setBackground(backgroundColor);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JStatistics = new JLabel("STATISTICS");
        JStatistics.setFont(new Font("Arial", Font.BOLD, 18));
        JStatistics.setForeground(primaryColor);

        headerPanel.add(JStatistics, BorderLayout.WEST);

        // Statistics Grid - 2x2
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Total Collections
        JTotalValue = new JLabel("127");
        JPanel totalCard = createStatCard("Total Collections", JTotalValue);
        gridPanel.add(totalCard);

        // Reading
        JReadingValue = new JLabel("8");
        JPanel readingCard = createStatCard("Reading", JReadingValue);
        gridPanel.add(readingCard);

        // Completed
        JCompletedValue = new JLabel("89");
        JPanel completedCard = createStatCard("Completed", JCompletedValue);
        gridPanel.add(completedCard);

        // Plan to Read
        JPlanToReadValue = new JLabel("30");
        JPanel planCard = createStatCard("Plan to Read", JPlanToReadValue);
        gridPanel.add(planCard);

        statsPanel.add(headerPanel, BorderLayout.NORTH);
        statsPanel.add(gridPanel, BorderLayout.CENTER);

        // UKURAN TETAP BESAR
        statsPanel.setPreferredSize(new Dimension(450, 380));
        statsPanel.setMaximumSize(new Dimension(450, 380));
        statsPanel.setMinimumSize(new Dimension(400, 380));

        return statsPanel;
    }

    private JPanel createStatCard(String title, JLabel value) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(248, 250, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, primaryColor),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(127, 140, 141));

        value.setFont(new Font("Arial", Font.BOLD, 28));
        value.setForeground(primaryColor);
        value.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    private void applyStyling() {
        // Style Edit Button
        BEditProfile.setBackground(primaryColor);
        BEditProfile.setForeground(secondaryColor);
        BEditProfile.setBorderPainted(false);
        BEditProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Style Show Password Button
        if (BShowPassword != null) {
            BShowPassword.setBackground(new Color(245, 245, 245));
            BShowPassword.setForeground(new Color(100, 100, 100));
            BShowPassword.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }
    }

    // Method untuk mengatur tombol aktif
    private void setActiveButton(JButton button) {
        // Reset semua tombol
        JButton[] navButtons = {BMyProfile, BLibrary, BExit};
        for (JButton btn : navButtons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(primaryColor);

            // Atur border
            if (btn.getText().equals("EXIT")) {
                btn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            } else {
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        }

        // Set tombol aktif
        button.setBackground(primaryColor);
        button.setForeground(secondaryColor);

        // Untuk tombol aktif, pastikan border konsisten
        if (!button.getText().equals("EXIT")) {
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
    }

    private void setupEvents() {
        // Navigation buttons - SEDERHANA TANPA REDIRECTING
        BMyProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(BMyProfile);
                System.out.println("PROFILE button clicked");
                // Tetap di halaman ProfilePage
            }
        });

        BLibrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set tombol Library aktif sebentar
                setActiveButton(BLibrary);

                // Kembalikan ke tombol Profile (untuk visual feedback)
                SwingUtilities.invokeLater(() -> {
                    // Tunggu sedikit untuk memberikan visual feedback
                    try {
                        Thread.sleep(150); // Delay singkat 150ms
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    // Kembalikan tombol ke Profile
                    setActiveButton(BMyProfile);

                    // Navigasi ke Dashboard
                    MainControl.showDashboard();
                });

                System.out.println("LIBRARY button clicked - navigating to dashboard");
            }
        });

        BExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set tombol Exit aktif
                setActiveButton(BExit);

                // Tampilkan dialog konfirmasi
                int response = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?", "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    // Jika user batal, kembalikan ke tombol Profile
                    setActiveButton(BMyProfile);
                }

                System.out.println("EXIT button clicked");
            }
        });

        // Edit Profile Button
        BEditProfile.addActionListener(e -> {
            MainControl.showEditProfilePage();
        });

        // Show/Hide Password Button
        if (BShowPassword != null) {
            BShowPassword.addActionListener(e -> {
                passwordVisible = !passwordVisible;

                if (passwordVisible) {
                    JPasswordValue.setText(currentPassword);
                    BShowPassword.setText("🔒");
                    BShowPassword.setToolTipText("Hide password");
                } else {
                    JPasswordValue.setText("••••••••");
                    BShowPassword.setText("👁");
                    BShowPassword.setToolTipText("Show password");
                }
            });
        }
    }

    private void loadDummyData() {
        setUserData(currentDisplayName, currentEmail, currentPassword);
    }

    // ===== PUBLIC METHODS UNTUK INTEGRASI =====

    public void setUserData(String displayName, String email, String password) {
        if (displayName != null && !displayName.isEmpty()) {
            currentDisplayName = displayName;
            if (JDisplayValue != null) {
                JDisplayValue.setText(displayName);
            }
        }
        if (email != null && !email.isEmpty()) {
            currentEmail = email;
            if (JEmailValue != null) {
                JEmailValue.setText(email);
            }
        }
        this.currentPassword = password;

    }

    // Setter individual untuk MainControl.updateUserProfile()
    public void setDisplayName(String displayName) {
        this.currentDisplayName = displayName;
        if (JDisplayValue != null) {
            JDisplayValue.setText(displayName);
        }
    }

    public void setEmail(String email) {
        this.currentEmail = email;
        if (JEmailValue != null) {
            JEmailValue.setText(email);
        }
    }

    public void setPassword(String password) {
        this.currentPassword = password;
        passwordVisible = false;
        if (JPasswordValue != null) {
            JPasswordValue.setText("••••••••");
        }
        if (BShowPassword != null) {
            BShowPassword.setText("👁");
            BShowPassword.setToolTipText("Show password");
        }
    }

    public String getDisplayName() {
        return currentDisplayName;
    }

    public String getEmail() {
        return currentEmail;
    }

    public String getPassword() {
        return currentPassword;
    }

    public void setStatistics(int total, int reading, int completed, int planToRead) {
        if (JTotalValue != null) JTotalValue.setText(String.valueOf(total));
        if (JReadingValue != null) JReadingValue.setText(String.valueOf(reading));
        if (JCompletedValue != null) JCompletedValue.setText(String.valueOf(completed));
        if (JPlanToReadValue != null) JPlanToReadValue.setText(String.valueOf(planToRead));
    }

    public JPanel getProfilePagePanel() {
        return profilePagePanel;
    }

    // Method untuk reset ke tombol PROFILE
    public void resetToProfile() {
        setActiveButton(BMyProfile);
    }

    // Test standalone
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("ReadArchive - Simple Navigation");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(1000, 700);
//            frame.setMinimumSize(new Dimension(900, 600));
//
//            ProfilePage profilePage = new ProfilePage();
//            frame.setContentPane(profilePage.getProfilePagePanel());
//
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
}