import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ProfilePage {
    private JPanel profilePagePanel;

    // Profile Section Components
    private JLabel JMyProfile;
    private JLabel JDisplay;
    private JLabel JEmail;
    private JLabel JPassword;
    private JLabel JDisplayValue;
    private JLabel JEmailValue;
    private JLabel JPasswordValue;
    private JButton BEditProfile;

    // Statistics Section Components
    private JLabel JStatistics;
    private JLabel JTotal;
    private JLabel JReading;
    private JLabel JCompleted;
    private JLabel JPlanToRead;
    private JLabel JTotalValue;
    private JLabel JReadingValue;
    private JLabel JCompletedValue;
    private JLabel JPlanToReadValue;

    // Navigation Components
    private JButton BMyProfile;
    private JButton BLibrary;
    private JButton BExit;

    // Color Scheme
    private Color primaryColor = new Color(51, 61, 87);
    private Color secondaryColor = new Color(249, 206, 146);
    private Color backgroundColor = Color.WHITE;
    private Color panelBackground = new Color(245, 247, 250);

    // Data storage
    private String currentDisplayName = "ReadArchive";
    private String currentEmail = "user@example.com";

    public ProfilePage() {
        createUI();
        applyStyling();
        setupEvents();
        loadDummyData();
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
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(panelBackground);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // NAVIGATION TABS
        JPanel navPanel = createNavigationPanel();
        mainContentPanel.add(navPanel);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // CONTENT AREA
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2, 20, 0));
        contentPanel.setOpaque(false);

        // LEFT PANEL - PROFILE
        JPanel profilePanel = createProfilePanel();
        contentPanel.add(profilePanel);

        // RIGHT PANEL - STATISTICS
        JPanel statsPanel = createStatisticsPanel();
        contentPanel.add(statsPanel);

        mainContentPanel.add(contentPanel);
        mainContentPanel.add(Box.createVerticalGlue());

        profilePagePanel.add(mainContentPanel, BorderLayout.CENTER);
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
        navPanel.setOpaque(false);
        navPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        navPanel.setBackground(Color.WHITE);

        BMyProfile = createNavButton("MY PROFILE");
        BLibrary = createNavButton("LIBRARY");
        BExit = createNavButton("EXIT");

        // Set My Profile as active initially
        BMyProfile.setBackground(primaryColor);
        BMyProfile.setForeground(secondaryColor);

        navPanel.add(BMyProfile);
        navPanel.add(BLibrary);
        navPanel.add(BExit);

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

        Dimension buttonSize = new Dimension(150, 40);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);

        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        return button;
    }

    private JPanel createProfilePanel() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        profilePanel.setBackground(backgroundColor);
        profilePanel.setBorder(createPanelBorder());

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JMyProfile = new JLabel("MY PROFILE");
        JMyProfile.setFont(new Font("Arial", Font.BOLD, 18));
        JMyProfile.setForeground(primaryColor);

        BEditProfile = new JButton("Edit Profile");
        BEditProfile.setFont(new Font("Arial", Font.PLAIN, 12));
        BEditProfile.setFocusPainted(false);

        headerPanel.add(JMyProfile, BorderLayout.WEST);
        headerPanel.add(BEditProfile, BorderLayout.EAST);

        // Profile Details - PAKAI GRID BAG LAYOUT untuk alignment presisi
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0); // Spacing vertikal
        gbc.weightx = 1.0;

        // Row 1: Display Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(createCompactRow("Display Name", JDisplayValue = new JLabel()), gbc);

        // Row 2: Email
        gbc.gridy = 1;
        detailsPanel.add(createCompactRow("Email", JEmailValue = new JLabel()), gbc);

        // Row 3: Password
        gbc.gridy = 2;
        detailsPanel.add(createCompactRow("Password", JPasswordValue = new JLabel("••••••••")), gbc);

        profilePanel.add(headerPanel, BorderLayout.NORTH);
        profilePanel.add(detailsPanel, BorderLayout.CENTER);

        return profilePanel;
    }

    // METODE untuk row yang lebih compact
    private JPanel createCompactRow(String label, JLabel value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JLabel labelComponent = new JLabel(label + ":");
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labelComponent.setForeground(new Color(85, 85, 85));

        value.setFont(new Font("Arial", Font.PLAIN, 14));
        value.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        row.add(labelComponent, BorderLayout.WEST);
        row.add(value, BorderLayout.CENTER);

        // Atur tinggi konsisten
        row.setPreferredSize(new Dimension(row.getPreferredSize().width, 30));

        return row;
    }

    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout());
        statsPanel.setBackground(backgroundColor);
        statsPanel.setBorder(createPanelBorder());

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JStatistics = new JLabel("STATISTICS");
        JStatistics.setFont(new Font("Arial", Font.BOLD, 18));
        JStatistics.setForeground(primaryColor);

        headerPanel.add(JStatistics, BorderLayout.WEST);

        // Statistics Grid
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Total Items
        gridPanel.add(createStatCard("Total Collections", JTotalValue = new JLabel("0")));

        // Reading
        gridPanel.add(createStatCard("Reading", JReadingValue = new JLabel("0")));

        // Completed
        gridPanel.add(createStatCard("Completed", JCompletedValue = new JLabel("0")));

        // Plan to Read
        gridPanel.add(createStatCard("Plan to Read", JPlanToReadValue = new JLabel("0")));

        statsPanel.add(headerPanel, BorderLayout.NORTH);
        statsPanel.add(gridPanel, BorderLayout.CENTER);

        return statsPanel;
    }

    private JPanel createStatCard(String title, JLabel value) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(248, 250, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, primaryColor),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(127, 140, 141));

        value.setFont(new Font("Arial", Font.BOLD, 24));
        value.setForeground(primaryColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    private Border createPanelBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        );
    }

    private void applyStyling() {
        // Style Edit Button
        BEditProfile.setBackground(primaryColor);
        BEditProfile.setForeground(secondaryColor);
        BEditProfile.setBorderPainted(false);

        // Style Navigation Buttons
        setupNavButtonHover(BMyProfile);
        setupNavButtonHover(BLibrary);
        setupNavButtonHover(BExit);
    }

    private void setupNavButtonHover(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(primaryColor)) {
                    button.setBackground(new Color(240, 240, 240));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(primaryColor)) {
                    button.setBackground(Color.WHITE);
                }
            }
        });
    }

    private void setupEvents() {
        // Navigation buttons
        BMyProfile.addActionListener(e -> {
            resetNavButtons();
            BMyProfile.setBackground(primaryColor);
            BMyProfile.setForeground(secondaryColor);
            // Tetap di halaman profile, tidak perlu pindah
        });

        BLibrary.addActionListener(e -> {
            resetNavButtons();
            BLibrary.setBackground(primaryColor);
            BLibrary.setForeground(secondaryColor);
            // Panggil MainControl untuk pindah ke Library
            MainControl.showDashboard(); // atau MainControl.showLibraryPage() jika ada
        });

        BExit.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Edit Profile Button - Navigasi ke EditProfilePage via MainControl
        BEditProfile.addActionListener(e -> {
            MainControl.showEditProfilePage();
        });
    }

    private void resetNavButtons() {
        JButton[] navButtons = {BMyProfile, BLibrary, BExit};
        for (JButton button : navButtons) {
            button.setBackground(Color.WHITE);
            button.setForeground(primaryColor);
        }
    }

    private void loadDummyData() {
        // Set user data
        setUserData(currentDisplayName, currentEmail);

        // Set dummy statistics
        setStatistics(127, 8, 89, 30);
    }

    // ===== PUBLIC METHODS UNTUK INTEGRASI DENGAN MAINCONTROL =====

    // Method untuk update data dari EditProfilePage
    public void setUserData(String displayName, String email) {
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

    // Getter untuk data (digunakan EditProfilePage untuk pre-fill)
    public String getDisplayName() {
        return currentDisplayName;
    }

    public String getEmail() {
        return currentEmail;
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

    // Test standalone (jika ingin test sendiri)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Profile Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setMinimumSize(new Dimension(900, 600));
            frame.setContentPane(new ProfilePage().getProfilePagePanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}