import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AddNewCollections {
    private JPanel addCollectionPanel;

    // Components
    private JLabel titleLabel;
    private JCheckBox typeManhwa;
    private JCheckBox typeManga;
    private JCheckBox typeWebnovel;
    private JLabel titleFieldLabel;
    private JTextField titleField;
    private JLabel authorLabel;
    private JTextField authorField;
    private JLabel totalChapterLabel;
    private JTextField totalChapterField;
    private JLabel currentChapterLabel;
    private JTextField currentChapterField;
    private JLabel statusLabel;
    private JComboBox<String> statusComboBox;
    private JLabel ratingLabel;
    private JComboBox<String> ratingComboBox;
    private JButton saveButton;
    private JButton cancelButton;

    // Color Scheme
    private Color primaryColor = new Color(51, 61, 87);
    private Color secondaryColor = new Color(249, 206, 146);
    private Color backgroundColor = Color.WHITE;
    private Color panelBackground = new Color(245, 247, 250);

    // Constants for consistent sizing
    private final int FIELD_WIDTH = 300;
    private final int FIELD_HEIGHT = 35;
    private final int COMBO_WIDTH = 250;
    private final int COMBO_HEIGHT = 35;
    private final int BUTTON_WIDTH = 120;
    private final int BUTTON_HEIGHT = 40;

    public AddNewCollections() {
        createUI();
        applyStyling();
        setupEvents();
    }

    private void createUI() {
        addCollectionPanel = new JPanel();
        addCollectionPanel.setLayout(new BorderLayout());
        addCollectionPanel.setBackground(panelBackground);

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        titleLabel = new JLabel("Add New Collections");
        titleLabel.setForeground(secondaryColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        addCollectionPanel.add(headerPanel, BorderLayout.NORTH);

        // MAIN CONTENT - Pakai GridBagLayout untuk kontrol posisi yang lebih presisi
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(panelBackground);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0); // Spacing vertikal
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ==== TYPE SECTION ====
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(createTypeSection(), gbc);

        // ==== TITLE ====
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(createLabel("Title:"), gbc);

        gbc.gridx = 1;
        titleField = createTextField();
        contentPanel.add(titleField, gbc);

        // ==== AUTHOR ====
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(createLabel("Author:"), gbc);

        gbc.gridx = 1;
        authorField = createTextField();
        contentPanel.add(authorField, gbc);

        // ==== TOTAL CHAPTER ====
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(createLabel("Total Chapter:"), gbc);

        gbc.gridx = 1;
        totalChapterField = createNumberField();
        contentPanel.add(totalChapterField, gbc);

        // ==== CURRENT CHAPTER ====
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(createLabel("Current:"), gbc);

        gbc.gridx = 1;
        currentChapterField = createNumberField();
        contentPanel.add(currentChapterField, gbc);

        // ==== STATUS ====
        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPanel.add(createLabel("Status:"), gbc);

        gbc.gridx = 1;
        statusComboBox = createStatusComboBox();
        contentPanel.add(statusComboBox, gbc);

        // ==== RATING ====
        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPanel.add(createLabel("Rating:"), gbc);

        gbc.gridx = 1;
        ratingComboBox = createRatingComboBox();
        contentPanel.add(ratingComboBox, gbc);

        // ==== BUTTONS ====
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(30, 0, 0, 0);
        contentPanel.add(createButtonPanel(), gbc);

        addCollectionPanel.add(contentPanel, BorderLayout.CENTER);
    }

    // ===== HELPER METHODS FOR CREATING CONSISTENT COMPONENTS =====

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(primaryColor);
        label.setPreferredSize(new Dimension(150, FIELD_HEIGHT));
        label.setMinimumSize(new Dimension(150, FIELD_HEIGHT));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        field.setMinimumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        field.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        return field;
    }

    private JTextField createNumberField() {
        JTextField field = createTextField();
        // Hanya terima angka
        field.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a)
                    throws javax.swing.text.BadLocationException {
                if (str == null) return;
                // Cek apakah string hanya berisi angka
                if (str.matches("\\d*")) {
                    super.insertString(offs, str, a);
                }
            }
        });
        return field;
    }

    private JPanel createTypeSection() {
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        typePanel.setOpaque(false);
        typePanel.setPreferredSize(new Dimension(FIELD_WIDTH + 150, 50));

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typeLabel.setForeground(primaryColor);
        typeLabel.setPreferredSize(new Dimension(60, 30));

        typeManhwa = createCheckBox("Manhwa");
        typeManga = createCheckBox("Manga");
        typeWebnovel = createCheckBox("Webnovel");

        typePanel.add(typeLabel);
        typePanel.add(typeManhwa);
        typePanel.add(typeManga);
        typePanel.add(typeWebnovel);

        return typePanel;
    }

    private JCheckBox createCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBox.setForeground(primaryColor);
        checkBox.setBackground(panelBackground);
        checkBox.setFocusPainted(false);
        checkBox.setPreferredSize(new Dimension(100, 30));
        return checkBox;
    }

    private JComboBox<String> createStatusComboBox() {
        String[] statusOptions = {"Reading", "Plan to Read", "Completed", "Dropped"};
        JComboBox<String> combo = new JComboBox<>(statusOptions);
        combo.setFont(new Font("Arial", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(COMBO_WIDTH, COMBO_HEIGHT));
        combo.setMinimumSize(new Dimension(COMBO_WIDTH, COMBO_HEIGHT));
        combo.setMaximumSize(new Dimension(COMBO_WIDTH, COMBO_HEIGHT));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JComboBox<String> createRatingComboBox() {
        String[] ratingOptions = {"Select Rating", "★☆☆☆☆ (1)", "★★☆☆☆ (2)", "★★★☆☆ (3)", "★★★★☆ (4)", "★★★★★ (5)"};
        JComboBox<String> combo = new JComboBox<>(ratingOptions);
        combo.setFont(new Font("Arial", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(COMBO_WIDTH, COMBO_HEIGHT));
        combo.setMinimumSize(new Dimension(COMBO_WIDTH, COMBO_HEIGHT));
        combo.setMaximumSize(new Dimension(COMBO_WIDTH, COMBO_HEIGHT));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        saveButton = createButton("Save", primaryColor, secondaryColor);
        cancelButton = createButton("Cancel", new Color(220, 220, 220), primaryColor);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (bgColor.equals(primaryColor)) {
                    button.setBackground(new Color(41, 51, 77));
                } else {
                    button.setBackground(new Color(200, 200, 200));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void applyStyling() {
        // Style text fields with consistent border
        Border fieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        );

        titleField.setBorder(fieldBorder);
        authorField.setBorder(fieldBorder);
        totalChapterField.setBorder(fieldBorder);
        currentChapterField.setBorder(fieldBorder);

        // Style combo boxes border
        Border comboBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        statusComboBox.setBorder(comboBorder);
        ratingComboBox.setBorder(comboBorder);
    }

    private void setupEvents() {
        // Checkbox events untuk Type (biar hanya satu yang bisa dipilih)
        typeManhwa.addActionListener(e -> {
            if (typeManhwa.isSelected()) {
                typeManga.setSelected(false);
                typeWebnovel.setSelected(false);
            }
        });

        typeManga.addActionListener(e -> {
            if (typeManga.isSelected()) {
                typeManhwa.setSelected(false);
                typeWebnovel.setSelected(false);
            }
        });

        typeWebnovel.addActionListener(e -> {
            if (typeWebnovel.isSelected()) {
                typeManhwa.setSelected(false);
                typeManga.setSelected(false);
            }
        });

        // Save Button
        saveButton.addActionListener(e -> {
            if (validateInput()) {
                saveCollection();
                resetForm();
                JOptionPane.showMessageDialog(null,
                        "Collection saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Kembali ke halaman sebelumnya
                if (MainControl.class != null) {
                    MainControl.showDashboard();
                }
            }
        });

        // Cancel Button
        cancelButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to cancel? All unsaved changes will be lost.",
                    "Confirm Cancel",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                resetForm();

                // Kembali ke halaman sebelumnya
                if (MainControl.class != null) {
                    MainControl.showDashboard();
                }
            }
        });

        // Auto-calculate jika total chapter diisi
        totalChapterField.addActionListener(e -> {
            if (!totalChapterField.getText().isEmpty() && currentChapterField.getText().isEmpty()) {
                currentChapterField.setText("0");
            }
        });
    }

    private boolean validateInput() {
        // Validasi Type dipilih
        if (!typeManhwa.isSelected() && !typeManga.isSelected() && !typeWebnovel.isSelected()) {
            showError("Please select a type (Manhwa, Manga, or Webnovel)");
            typeManhwa.requestFocus();
            return false;
        }

        // Validasi Title tidak kosong
        if (titleField.getText().trim().isEmpty()) {
            showError("Title cannot be empty");
            titleField.requestFocus();
            return false;
        }

        // Validasi Author tidak kosong
        if (authorField.getText().trim().isEmpty()) {
            showError("Author cannot be empty");
            authorField.requestFocus();
            return false;
        }

        // Validasi Total Chapter
        String totalText = totalChapterField.getText().trim();
        if (!totalText.isEmpty()) {
            try {
                int total = Integer.parseInt(totalText);
                if (total <= 0) {
                    showError("Total Chapter must be greater than 0");
                    totalChapterField.requestFocus();
                    return false;
                }

                // Validasi Current vs Total
                String currentText = currentChapterField.getText().trim();
                if (!currentText.isEmpty()) {
                    int current = Integer.parseInt(currentText);
                    if (current > total) {
                        showError("Current chapter cannot be greater than total chapters");
                        currentChapterField.requestFocus();
                        return false;
                    }
                    if (current < 0) {
                        showError("Current chapter cannot be negative");
                        currentChapterField.requestFocus();
                        return false;
                    }
                }
            } catch (NumberFormatException e) {
                showError("Total Chapter must be a valid number");
                totalChapterField.requestFocus();
                return false;
            }
        }

        // Validasi Current Chapter
        String currentText = currentChapterField.getText().trim();
        if (!currentText.isEmpty()) {
            try {
                int current = Integer.parseInt(currentText);
                if (current < 0) {
                    showError("Current chapter cannot be negative");
                    currentChapterField.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                showError("Current Chapter must be a valid number");
                currentChapterField.requestFocus();
                return false;
            }
        }

        // Validasi Rating dipilih
        if (ratingComboBox.getSelectedIndex() == 0) {
            showError("Please select a rating");
            ratingComboBox.requestFocus();
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void saveCollection() {
        // Ambil data dari form
        String type = "";
        if (typeManhwa.isSelected()) type = "Manhwa";
        else if (typeManga.isSelected()) type = "Manga";
        else if (typeWebnovel.isSelected()) type = "Webnovel";

        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String totalChapter = totalChapterField.getText().trim();
        String currentChapter = currentChapterField.getText().trim();
        String status = (String) statusComboBox.getSelectedItem();
        String rating = (String) ratingComboBox.getSelectedItem();

        // Simpan ke console (nanti bisa ke database)
        System.out.println("=== New Collection Saved ===");
        System.out.println("Type: " + type);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Total Chapter: " + (totalChapter.isEmpty() ? "Not specified" : totalChapter));
        System.out.println("Current Chapter: " + (currentChapter.isEmpty() ? "0" : currentChapter));
        System.out.println("Status: " + status);
        System.out.println("Rating: " + rating);
        System.out.println("============================");

        // Tambahkan ke MainControl jika perlu
        if (MainControl.getProfilePage() != null) {
            // Update statistics di ProfilePage
            // Misalnya tambah 1 ke total collections
        }
    }

    private void resetForm() {
        // Reset semua field
        typeManhwa.setSelected(false);
        typeManga.setSelected(false);
        typeWebnovel.setSelected(false);

        titleField.setText("");
        authorField.setText("");
        totalChapterField.setText("");
        currentChapterField.setText("");

        statusComboBox.setSelectedIndex(0);
        ratingComboBox.setSelectedIndex(0);

        // Fokus ke field pertama
        typeManhwa.requestFocus();
    }

    // Getter untuk panel
    public JPanel getAddCollectionPanel() {
        return addCollectionPanel;
    }

    // Method untuk testing standalone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add New Collection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 750);
            frame.setMinimumSize(new Dimension(580, 720));

            // Center the frame
            frame.setLocationRelativeTo(null);

            // Add the panel
            frame.setContentPane(new AddNewCollections().getAddCollectionPanel());
            frame.setVisible(true);
        });
    }
}