// File: AddToMyListForm.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddToMyListForm {
    private JDialog dialog;
    private JComboBox<String> statusCombo;
    private JTextField progressField;
    private JTextField ratingField;
    private JLabel ratingLabel;
    private Dashboard.CardData cardData;
    private Dashboard dashboard;

    // Color Scheme konsisten dengan Dashboard
    private Color primaryColor = new Color(51, 61, 87);
    private Color secondaryColor = new Color(249, 206, 146);
    private Color backgroundColor = Color.WHITE;

    // Status options
    private String[] statusOptions = {"Reading", "Completed", "On Hold", "Dropped", "Plan to Read"};

    public AddToMyListForm(Dashboard dashboard, Dashboard.CardData cardData) {
        this.dashboard = dashboard;
        this.cardData = cardData;
        createDialog();
    }

    private void createDialog() {
        dialog = new JDialog();
        dialog.setTitle("Add to My List - " + cardData.title);
        dialog.setModal(true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(backgroundColor);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(backgroundColor);

        // Title
        JLabel titleLabel = new JLabel("Add '" + cardData.title + "' to My List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(primaryColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Info item yang akan ditambahkan
        JPanel infoPanel = createInfoPanel();

        // Form fields
        JPanel formPanel = createFormPanel();

        // Button panel
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(240, 245, 250));
        infoPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel typeLabel = new JLabel("Type: " + cardData.type);
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        typeLabel.setForeground(primaryColor);

        JLabel chapterLabel = new JLabel("Total Chapters: " + cardData.chapter);
        chapterLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        chapterLabel.setForeground(primaryColor);

        JLabel originalStatusLabel = new JLabel("Status: " + cardData.status);
        originalStatusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        originalStatusLabel.setForeground(primaryColor);

        infoPanel.add(typeLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(chapterLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(originalStatusLabel);

        return infoPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBackground(backgroundColor);
        formPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Status
        JLabel statusLabel = new JLabel("Your Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        statusLabel.setForeground(primaryColor);

        statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setSelectedItem("Reading");
        styleComboBox(statusCombo);

        // Progress
        JLabel progressLabel = new JLabel("Your Progress:");
        progressLabel.setFont(new Font("Arial", Font.BOLD, 13));
        progressLabel.setForeground(primaryColor);

        progressField = new JTextField("0/" + extractTotalChapters(cardData.chapter));
        styleTextField(progressField);

        // Rating
        ratingLabel = new JLabel("Your Rating (1-10):");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 13));
        ratingLabel.setForeground(primaryColor);

        ratingField = new JTextField();
        styleTextField(ratingField);
        ratingField.setEnabled(false); // Disable dulu, aktif jika status = Completed

        // Note
        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        noteLabel.setForeground(Color.GRAY);

        JLabel ratingNote = new JLabel("Rating only for 'Completed'");
        ratingNote.setFont(new Font("Arial", Font.ITALIC, 11));
        ratingNote.setForeground(Color.GRAY);

        // Add components
        formPanel.add(statusLabel);
        formPanel.add(statusCombo);
        formPanel.add(progressLabel);
        formPanel.add(progressField);
        formPanel.add(ratingLabel);
        formPanel.add(ratingField);
        formPanel.add(noteLabel);
        formPanel.add(ratingNote);

        // Status change listener
        statusCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStatus = (String) statusCombo.getSelectedItem();
                boolean isCompleted = "Completed".equals(selectedStatus);
                ratingField.setEnabled(isCompleted);
                ratingLabel.setEnabled(isCompleted);

                if (!isCompleted) {
                    ratingField.setText("");
                }
            }
        });

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(backgroundColor);

        JButton cancelBtn = createButton("Cancel", new Color(180, 180, 180), primaryColor);
        JButton addBtn = createButton("Add to My List", primaryColor, secondaryColor);

        cancelBtn.addActionListener(e -> dialog.dispose());

        addBtn.addActionListener(e -> {
            if (validateInput()) {
                addToMyList();
                dialog.dispose();
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(addBtn);

        return buttonPanel;
    }

    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 35));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setPreferredSize(new Dimension(150, 30));
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        combo.setPreferredSize(new Dimension(150, 30));
    }

    private boolean validateInput() {
        String status = (String) statusCombo.getSelectedItem();
        String progress = progressField.getText().trim();
        String rating = ratingField.getText().trim();

        // Validate progress format
        if (!progress.matches("\\d+/\\d+")) {
            JOptionPane.showMessageDialog(dialog,
                    "Please enter progress in format: current/total (e.g., 10/100)",
                    "Invalid Format", JOptionPane.ERROR_MESSAGE);
            progressField.requestFocus();
            return false;
        }

        // Validate rating if status is Completed
        if ("Completed".equals(status)) {
            if (rating.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Rating is required for 'Completed' status",
                        "Missing Rating", JOptionPane.WARNING_MESSAGE);
                ratingField.requestFocus();
                return false;
            }

            try {
                double ratingValue = Double.parseDouble(rating);
                if (ratingValue < 1 || ratingValue > 10) {
                    JOptionPane.showMessageDialog(dialog,
                            "Rating must be between 1 and 10",
                            "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                    ratingField.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a valid number for rating",
                        "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                ratingField.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void addToMyList() {
        String status = (String) statusCombo.getSelectedItem();
        String progress = progressField.getText().trim();
        String rating = ratingField.getText().trim();

        // Create copy for My List
        Dashboard.CardData myListCopy = new Dashboard.CardData(
                cardData.title,
                cardData.author,
                cardData.chapter,
                cardData.status,
                cardData.type,
                cardData.rating
        );

        myListCopy.myStatus = status;
        myListCopy.myProgress = progress;

        if ("Completed".equals(status) && !rating.isEmpty()) {
            myListCopy.myRating = String.format("%.1f", Double.parseDouble(rating));
        } else {
            myListCopy.myRating = null;
        }

        // Panggil method di Dashboard untuk menambahkan data
        if (dashboard != null) {
            dashboard.addToMyList(myListCopy);
        }

        dialog.dispose();
    }

    private String extractTotalChapters(String chapterStr) {
        // Extract total chapters from string like "179/179" or "560/Ongoing"
        if (chapterStr.contains("/")) {
            String[] parts = chapterStr.split("/");
            if (parts.length > 1) {
                // Check if second part is a number
                if (parts[1].matches("\\d+")) {
                    return parts[1];
                } else {
                    // If it's "Ongoing" or similar, use first part as total for now
                    return parts[0];
                }
            }
        }
        return "100"; // Default
    }

    public void show() {
        dialog.setVisible(true);
    }

    // Untuk testing standalone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create sample data
            Dashboard.CardData sampleData = new Dashboard.CardData(
                    "Solo Leveling",
                    "Chugong",
                    "179/179",
                    "Completed",
                    "WEBCOMIC",
                    "9.5"
            );

            // Test the form
            AddToMyListForm form = new AddToMyListForm(null, sampleData);
            form.show();
        });
    }
}