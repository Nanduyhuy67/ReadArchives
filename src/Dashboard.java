import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class Dashboard {
    public Container mainPanel;

    // Navigation Components
    private JButton BProfile;
    private JButton BLibrary;
    private JButton BExit;

    // Filter Components
    private JButton BWebComic;
    private JButton BWebNovel;
    private JButton BMyList;

    // Color Scheme
    private Color primaryColor = new Color(51, 61, 87);
    private Color secondaryColor = new Color(249, 206, 146);
    private Color backgroundColor = Color.WHITE;
    private Color panelBackground = new Color(245, 247, 250);

    // Components untuk diakses dari luar
    private JPanel cardsContainer;
    private JLabel totalLabel;
    private JLabel welcomeLabel;

    // Data storage
    private List<CardData> allCards = new ArrayList<>();
    private List<CardData> webComicCards = new ArrayList<>();
    private List<CardData> webNovelCards = new ArrayList<>();
    private List<CardData> myListCards = new ArrayList<>();

    // Backend repository untuk koneksi MySQL
    private MyListRepository myListRepo = new MyListRepository();

    // State untuk mengetahui filter yang aktif
    private String currentFilter = "ALL";

    public Dashboard() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(panelBackground);

        // ========================= HEADER =========================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel headerTitle = new JLabel("ReadArchive");
        headerTitle.setForeground(secondaryColor);
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));

        headerPanel.add(headerTitle, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ========================= MAIN CONTENT =========================
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(panelBackground);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // NAVIGATION TABS - DI TENGAH ATAS
        JPanel navPanel = createNavigationPanel();
        mainContentPanel.add(navPanel, BorderLayout.NORTH);

        // ====================== CONTENT WRAPPER ======================
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(panelBackground);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // ====================== SCROLLABLE CONTENT AREA ======================
        JScrollPane scrollContent = createScrollableContent();
        contentWrapper.add(scrollContent, BorderLayout.CENTER);

        // Add content wrapper to main content
        mainContentPanel.add(contentWrapper, BorderLayout.CENTER);

        // Add main content to main panel
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);

        // Inisialisasi data
        initializeSampleData();
        myListCards = myListRepo.getAll(); // Load data My List dari database (backend)
        displayAllCards(); // Tampilkan semua data awal
    }

    // ===== INISIALISASI DATA =====
    private void initializeSampleData() {
        // WebComic data
        webComicCards.add(new CardData("Solo Leveling", "Chugong", "179/179", "Completed", "WEBCOMIC", "9.5"));
        webComicCards.add(new CardData("Tower of God", "SIU", "560/Ongoing", "On Going", "WEBCOMIC", "9.2"));
        webComicCards.add(new CardData("The Beginning After The End", "TurtleMe", "180/Ongoing", "On Going", "WEBCOMIC", "8.8"));

        // WebNovel data
        webNovelCards.add(new CardData("Omniscient Reader", "싱숑", "651/651", "Completed", "WEBNOVEL", "9.8"));
        webNovelCards.add(new CardData("Lord of the Mysteries", "Cuttlefish", "1294/1294", "Completed", "WEBNOVEL", "9.7"));
        webNovelCards.add(new CardData("Reverend Insanity", "Gu Zhen Ren", "2236/Hiatus", "Hiatus", "WEBNOVEL", "9.6"));
        webNovelCards.add(new CardData("The Second Coming of Gluttony", "Ro Yuen", "485/485", "Completed", "WEBNOVEL", "9.0"));

        // My List data dengan status dan progress yang berbeda
        CardData myList1 = new CardData("Solo Leveling", "Chugong", "50/179", "Reading", "WEBCOMIC", "9.5");
        myList1.myStatus = "Reading";
        myList1.myProgress = "50/179";
        myList1.myRating = null;
        myListCards.add(myList1);

        CardData myList2 = new CardData("Omniscient Reader", "싱숑", "651/651", "Completed", "WEBNOVEL", "9.8");
        myList2.myStatus = "Completed";
        myList2.myProgress = "651/651";
        myList2.myRating = "10.0";
        myListCards.add(myList2);

        CardData myList3 = new CardData("Lord of the Mysteries", "Cuttlefish", "700/1294", "Reading", "WEBNOVEL", "9.7");
        myList3.myStatus = "On Hold";
        myList3.myProgress = "700/1294";
        myList3.myRating = null;
        myListCards.add(myList3);

        // Gabung semua untuk tampilan awal
        allCards.addAll(webComicCards);
        allCards.addAll(webNovelCards);
    }

    // ===== SCROLLABLE CONTENT AREA =====
    private JScrollPane createScrollableContent() {
        // Panel utama untuk konten yang bisa discroll
        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        scrollablePanel.setBackground(panelBackground);

        // Container untuk blue panel
        JPanel centerHolder = new JPanel(new GridBagLayout());
        centerHolder.setBackground(panelBackground);

        // ====================== BLUE CONTENT PANEL ======================
        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(primaryColor);
        bluePanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        bluePanel.setLayout(new BoxLayout(bluePanel, BoxLayout.Y_AXIS));

        // ===================== ROW ATAS: WELCOME LABEL =====================
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Label Welcome di pojok kiri
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topRow.add(welcomeLabel, BorderLayout.WEST);

        bluePanel.add(topRow);
        bluePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ===================== FILTER TABS DI DALAM KOTAK BIRU =====================
        JPanel filterPanel = createFilterPanelInsideBlueBox();
        bluePanel.add(filterPanel);
        bluePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ===================== TOTAL ITEMS LABEL =====================
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setOpaque(false);

        totalLabel = new JLabel("Total: " + allCards.size() + " items");
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        totalPanel.add(totalLabel);

        bluePanel.add(totalPanel);
        bluePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ========================= CARDS CONTAINER =========================
        cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
        cardsContainer.setOpaque(false);

        bluePanel.add(cardsContainer);

        centerHolder.add(bluePanel);
        scrollablePanel.add(centerHolder);

        // Tambahkan padding bawah biar bisa scroll lebih jauh
        scrollablePanel.add(Box.createRigidArea(new Dimension(0, 100)));

        // Buat JScrollPane dengan scrollablePanel
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(panelBackground);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Customize scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setBackground(panelBackground);
        verticalScrollBar.setForeground(primaryColor);

        return scrollPane;
    }

    // ===== FILTER PANEL =====
    private JPanel createFilterPanelInsideBlueBox() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filterPanel.setOpaque(false);
        filterPanel.setBackground(primaryColor);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JPanel filterContainer = new JPanel();
        filterContainer.setLayout(new BoxLayout(filterContainer, BoxLayout.X_AXIS));
        filterContainer.setBackground(Color.WHITE);
        filterContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        filterContainer.setMaximumSize(new Dimension(450, 35));

        BWebComic = createFilterButtonInsideBlueBox("WEBCOMIC");
        BWebNovel = createFilterButtonInsideBlueBox("WEBNOVEL");
        BMyList = createFilterButtonInsideBlueBox("MY LIST");

        // Set semua item aktif secara default
        filterContainer.add(BWebComic);
        filterContainer.add(BWebNovel);
        filterContainer.add(BMyList);

        filterPanel.add(filterContainer);

        setupFilterEvents();

        return filterPanel;
    }

    private JButton createFilterButtonInsideBlueBox(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setBackground(Color.WHITE);
        button.setForeground(primaryColor);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Dimension buttonSize = new Dimension(150, 35);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        if (text.equals("MY LIST")) {
            button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        return button;
    }

    private void setupFilterEvents() {
        BWebComic.addActionListener(e -> {
            resetFilterButtons();
            BWebComic.setBackground(secondaryColor);
            BWebComic.setForeground(primaryColor);
            currentFilter = "WEBCOMIC";
            System.out.println("Filter: WebComic selected (" + webComicCards.size() + " items)");
            displayFilteredCards(webComicCards, false);
        });

        BWebNovel.addActionListener(e -> {
            resetFilterButtons();
            BWebNovel.setBackground(secondaryColor);
            BWebNovel.setForeground(primaryColor);
            currentFilter = "WEBNOVEL";
            System.out.println("Filter: WebNovel selected (" + webNovelCards.size() + " items)");
            displayFilteredCards(webNovelCards, false);
        });

        BMyList.addActionListener(e -> {
            resetFilterButtons();
            BMyList.setBackground(secondaryColor);
            BMyList.setForeground(primaryColor);
            currentFilter = "MY LIST";
            System.out.println("Filter: My List selected (" + myListCards.size() + " items)");
            displayFilteredCards(myListCards, true); // true = di My List
        });
    }

    private void resetFilterButtons() {
        JButton[] filterButtons = {BWebComic, BWebNovel, BMyList};
        for (JButton button : filterButtons) {
            button.setBackground(Color.WHITE);
            button.setForeground(primaryColor);
        }
    }

    // ===== TAMPILKAN SEMUA CARD =====
    private void displayAllCards() {
        clearCards();
        currentFilter = "ALL";
        for (CardData cardData : allCards) {
            addCardToContainer(cardData, false);
        }
        updateTotalItems(allCards.size());
    }

    // ===== TAMPILKAN CARDS BERDASARKAN FILTER =====
    private void displayFilteredCards(List<CardData> cards, boolean isMyList) {
        clearCards();
        for (CardData cardData : cards) {
            addCardToContainer(cardData, isMyList);
        }
        updateTotalItems(cards.size());
    }

    private void addCardToContainer(CardData data, boolean isMyList) {
        JPanel card = createCardComponent(data, isMyList);
        cardsContainer.add(card);
        cardsContainer.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing antar card
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    // ===== NAVIGATION PANEL =====
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        navPanel.setOpaque(false);
        navPanel.setBackground(panelBackground);

        JPanel tabsContainer = new JPanel();
        tabsContainer.setLayout(new BoxLayout(tabsContainer, BoxLayout.X_AXIS));
        tabsContainer.setBackground(Color.WHITE);
        tabsContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        tabsContainer.setMaximumSize(new Dimension(450, 45));

        BProfile = createNavButton("PROFILE");
        BLibrary = createNavButton("LIBRARY");
        BExit = createNavButton("EXIT");

        // Set Library as active
        BLibrary.setBackground(primaryColor);
        BLibrary.setForeground(secondaryColor);

        tabsContainer.add(BProfile);
        tabsContainer.add(BLibrary);
        tabsContainer.add(BExit);

        navPanel.add(tabsContainer);

        setupNavigationEvents();

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

    private void setupNavigationEvents() {
        BProfile.addActionListener(e -> {
            resetNavButtons();
            BProfile.setBackground(primaryColor);
            BProfile.setForeground(secondaryColor);
            System.out.println("Navigating to Profile via MainControl");

            // Panggil MainControl untuk pindah ke ProfilePage
            MainControl.showProfilePage();
        });

        BLibrary.addActionListener(e -> {
            resetNavButtons();
            BLibrary.setBackground(primaryColor);
            BLibrary.setForeground(secondaryColor);
            System.out.println("Already in Library");
            displayAllCards();
            resetFilterButtons();
            // Tidak perlu panggil MainControl karena sudah di Dashboard
        });

        BExit.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                // Jika user memilih NO, kembalikan Library sebagai aktif
                resetNavButtons();
                BLibrary.setBackground(primaryColor);
                BLibrary.setForeground(secondaryColor);
            }
        });
    }

    private void resetNavButtons() {
        JButton[] navButtons = {BProfile, BLibrary, BExit};
        for (JButton button : navButtons) {
            button.setBackground(Color.WHITE);
            button.setForeground(primaryColor);
        }
    }

    // ===== CARD COMPONENT =====
    private JPanel createCardComponent(CardData data, boolean isMyList) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(secondaryColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor.darker(), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        card.setMaximumSize(new Dimension(750, isMyList ? 150 : 120));

        // Panel kiri untuk konten
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(data.title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(primaryColor);

        // Author
        JLabel authorLabel = new JLabel("by " + data.author);
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        authorLabel.setForeground(primaryColor);

        // Chapter Progress (berbeda antara My List dan non-My List)
        String chapterText;
        if (isMyList && data.myProgress != null) {
            chapterText = "My Progress: " + data.myProgress;
        } else {
            chapterText = "Chapter " + data.chapter;
        }

        JLabel chapterLabel = new JLabel(chapterText);
        chapterLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        chapterLabel.setForeground(primaryColor);

        // Type
        JLabel typeLabel = new JLabel("Type: " + data.type);
        typeLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        typeLabel.setForeground(primaryColor);

        // Status (hanya untuk My List)
        if (isMyList && data.myStatus != null) {
            JLabel statusLabel = new JLabel("Status: " + data.myStatus);
            statusLabel.setFont(new Font("Arial", Font.BOLD, 13));

            // Warna berbeda berdasarkan status
            switch(data.myStatus) {
                case "Reading":
                    statusLabel.setForeground(new Color(0, 150, 0));
                    break;
                case "Completed":
                    statusLabel.setForeground(new Color(0, 100, 200));
                    break;
                case "On Hold":
                    statusLabel.setForeground(new Color(200, 150, 0));
                    break;
                case "Dropped":
                    statusLabel.setForeground(new Color(200, 0, 0));
                    break;
                case "Plan to Read":
                    statusLabel.setForeground(new Color(150, 0, 200));
                    break;
                default:
                    statusLabel.setForeground(primaryColor);
            }

            contentPanel.add(statusLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        contentPanel.add(authorLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(chapterLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(typeLabel);

        // Rating display (hanya untuk My List yang completed)
        if (isMyList && data.myRating != null && data.myStatus != null && data.myStatus.equals("Completed")) {
            JLabel myRatingLabel = new JLabel("My Rating: " + data.myRating + "/10");
            myRatingLabel.setFont(new Font("Arial", Font.BOLD, 13));
            myRatingLabel.setForeground(new Color(200, 0, 0));
            contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            contentPanel.add(myRatingLabel);
        }

        // Panel kanan untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        if (isMyList) {
            // Untuk My List: Tombol Edit dan Remove
            JPanel myListButtonsPanel = new JPanel();
            myListButtonsPanel.setLayout(new BoxLayout(myListButtonsPanel, BoxLayout.Y_AXIS));
            myListButtonsPanel.setOpaque(false);

            // Tombol Edit (untuk semua: progress, status, rating)
            JButton editBtn = new JButton("Edit");
            editBtn.setFont(new Font("Arial", Font.BOLD, 11));
            editBtn.setBackground(new Color(60, 80, 120));
            editBtn.setForeground(secondaryColor);
            editBtn.setFocusPainted(false);
            editBtn.setBorderPainted(false);
            editBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            editBtn.setPreferredSize(new Dimension(130, 30));
            editBtn.setMaximumSize(new Dimension(130, 30));

            editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editBtn.setBackground(new Color(70, 90, 130));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    editBtn.setBackground(new Color(60, 80, 120));
                }
            });

            editBtn.addActionListener(e -> {
                editMyListItem(data);
            });

            // Tombol Remove
            JButton removeBtn = new JButton("Remove");
            removeBtn.setFont(new Font("Arial", Font.BOLD, 11));
            removeBtn.setBackground(new Color(180, 60, 60));
            removeBtn.setForeground(Color.WHITE);
            removeBtn.setFocusPainted(false);
            removeBtn.setBorderPainted(false);
            removeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            removeBtn.setPreferredSize(new Dimension(130, 30));
            removeBtn.setMaximumSize(new Dimension(130, 30));

            removeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    removeBtn.setBackground(new Color(200, 70, 70));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    removeBtn.setBackground(new Color(180, 60, 60));
                }
            });

            removeBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(card,
                        "Remove '" + data.title + "' from My List?",
                        "Confirm Removal", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Hapus dari database
                    myListRepo.delete(data.title);
                    // Hapus dari list (UI)
                    myListCards.remove(data);
                    displayFilteredCards(myListCards, true);
                    JOptionPane.showMessageDialog(card,
                            "Removed from My List!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            myListButtonsPanel.add(editBtn);
            myListButtonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            myListButtonsPanel.add(removeBtn);
            buttonPanel.add(myListButtonsPanel);

        } else {
            // Untuk non-My List: Tombol Add to My List
            JButton addToListBtn = new JButton("Add to My List");
            addToListBtn.setFont(new Font("Arial", Font.BOLD, 12));
            addToListBtn.setBackground(primaryColor);
            addToListBtn.setForeground(secondaryColor);
            addToListBtn.setFocusPainted(false);
            addToListBtn.setBorderPainted(false);
            addToListBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addToListBtn.setPreferredSize(new Dimension(130, 35));
            addToListBtn.setMaximumSize(new Dimension(130, 35));

            addToListBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    addToListBtn.setBackground(primaryColor.darker());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    addToListBtn.setBackground(primaryColor);
                }
            });

            addToListBtn.addActionListener(e -> {
                // Cek apakah sudah ada di My List
                boolean exists = false;
                for (CardData item : myListCards) {
                    if (item.title.equals(data.title)) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    // Gunakan dialog sederhana
                    showAddToMyListDialog(data);
                } else {
                    JOptionPane.showMessageDialog(card,
                            "'" + data.title + "' is already in My List!",
                            "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            buttonPanel.add(Box.createVerticalGlue());
            buttonPanel.add(addToListBtn);
            buttonPanel.add(Box.createVerticalGlue());
        }

        card.add(contentPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.EAST);

        // Hover effect untuk card
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(239, 196, 136));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(secondaryColor);
            }
        });

        return card;
    }

    // ===== DIALOG UNTUK ADD TO MY LIST =====
    private void showAddToMyListDialog(CardData data) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add to My List - " + data.title);
        dialog.setModal(true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(backgroundColor);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(backgroundColor);

        // Title
        JLabel titleLabel = new JLabel("Add '" + data.title + "' to My List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(primaryColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(240, 245, 250));
        infoPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel typeLabel = new JLabel("Type: " + data.type);
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        typeLabel.setForeground(primaryColor);

        JLabel chapterLabel = new JLabel("Total Chapters: " + data.chapter);
        chapterLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        chapterLabel.setForeground(primaryColor);

        JLabel statusLabel = new JLabel("Status: " + data.status);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(primaryColor);

        infoPanel.add(typeLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(chapterLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(statusLabel);

        // Form fields
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setBackground(backgroundColor);
        formPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Status
        JLabel lblStatus = new JLabel("Your Status:");
        lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
        lblStatus.setForeground(primaryColor);
        String[] statusOptions = {"Reading", "Completed", "On Hold", "Dropped", "Plan to Read"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setSelectedItem("Reading");

        // Progress
        JLabel lblProgress = new JLabel("Your Progress:");
        lblProgress.setFont(new Font("Arial", Font.BOLD, 13));
        lblProgress.setForeground(primaryColor);
        JTextField progressField = new JTextField("0/" + extractTotalChapters(data.chapter));
        progressField.setFont(new Font("Arial", Font.PLAIN, 13));

        // Rating
        JLabel lblRating = new JLabel("Your Rating (1-10):");
        lblRating.setFont(new Font("Arial", Font.BOLD, 13));
        lblRating.setForeground(primaryColor);
        JTextField ratingField = new JTextField();
        ratingField.setFont(new Font("Arial", Font.PLAIN, 13));
        ratingField.setEnabled(false);

        formPanel.add(lblStatus);
        formPanel.add(statusCombo);
        formPanel.add(lblProgress);
        formPanel.add(progressField);
        formPanel.add(lblRating);
        formPanel.add(ratingField);

        // Status change listener
        statusCombo.addActionListener(e -> {
            String selectedStatus = (String) statusCombo.getSelectedItem();
            boolean isCompleted = "Completed".equals(selectedStatus);
            ratingField.setEnabled(isCompleted);
            lblRating.setEnabled(isCompleted);
            if (!isCompleted) {
                ratingField.setText("");
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(backgroundColor);

        JButton cancelBtn = createStyledButton("Cancel", new Color(180, 180, 180), primaryColor);
        JButton addBtn = createStyledButton("Add to My List", primaryColor, secondaryColor);

        cancelBtn.addActionListener(e -> dialog.dispose());

        addBtn.addActionListener(e -> {
            String status = (String) statusCombo.getSelectedItem();
            String progress = progressField.getText().trim();
            String rating = ratingField.getText().trim();

            // Validasi
            if (!progress.matches("\\d+/\\d+")) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter progress in format: current/total (e.g., 10/100)",
                        "Invalid Format", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ("Completed".equals(status) && !rating.isEmpty()) {
                try {
                    double ratingValue = Double.parseDouble(rating);
                    if (ratingValue < 1 || ratingValue > 10) {
                        JOptionPane.showMessageDialog(dialog,
                                "Rating must be between 1 and 10",
                                "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter a valid number for rating",
                            "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Create My List entry
            CardData myListData = new CardData(
                    data.title,
                    data.author,
                    data.chapter,
                    data.status,
                    data.type,
                    data.rating
            );
            myListData.myStatus = status;
            myListData.myProgress = progress;
            if ("Completed".equals(status) && !rating.isEmpty()) {
                myListData.myRating = String.format("%.1f", Double.parseDouble(rating));
            }

            // Add to My List
            myListCards.add(myListData);

            // Menyimpan ke database (backend)
            myListRepo.insert(myListData);

            dialog.dispose();

            // Refresh jika sedang menampilkan My List
            if (currentFilter.equals("MY LIST")) {
                displayFilteredCards(myListCards, true);
            }

            JOptionPane.showMessageDialog(null,
                    "Successfully added '" + data.title + "' to My List!\n" +
                            "Status: " + status + "\n" +
                            "Progress: " + progress,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(addBtn);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    public void addToMyList(CardData myListData) {
        myListCards.add(myListData);

        // Refresh My List jika sedang ditampilkan
        if (currentFilter.equals("MY LIST")) {
            displayFilteredCards(myListCards, true);
        }

        JOptionPane.showMessageDialog(null,
                "Successfully added '" + myListData.title + "' to My List!\n" +
                        "Status: " + myListData.myStatus + "\n" +
                        "Progress: " + myListData.myProgress,
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===== EDIT MY LIST ITEM =====
    private void editMyListItem(CardData data) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit - " + data.title);
        dialog.setModal(true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(backgroundColor);

        JLabel titleLabel = new JLabel("Edit '" + data.title + "'");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        formPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        formPanel.setBackground(backgroundColor);

        // Status
        JLabel statusLabel = new JLabel("Your Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        String[] statusOptions = {"Reading", "Completed", "On Hold", "Dropped", "Plan to Read"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setSelectedItem(data.myStatus != null ? data.myStatus : "Reading");

        // Progress
        JLabel progressLabel = new JLabel("Your Progress:");
        progressLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField progressField = new JTextField(data.myProgress != null ? data.myProgress : "0/" + extractTotalChapters(data.chapter));

        // Rating
        JLabel ratingLabel = new JLabel("Your Rating (1-10):");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField ratingField = new JTextField(data.myRating != null ? data.myRating : "");

        // Note
        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        noteLabel.setForeground(Color.GRAY);
        JLabel ratingNote = new JLabel("Rating only for 'Completed'");
        ratingNote.setFont(new Font("Arial", Font.ITALIC, 11));
        ratingNote.setForeground(Color.GRAY);

        formPanel.add(statusLabel);
        formPanel.add(statusCombo);
        formPanel.add(progressLabel);
        formPanel.add(progressField);
        formPanel.add(ratingLabel);
        formPanel.add(ratingField);
        formPanel.add(noteLabel);
        formPanel.add(ratingNote);

        // Status change listener
        statusCombo.addActionListener(e -> {
            String selectedStatus = (String) statusCombo.getSelectedItem();
            boolean isCompleted = "Completed".equals(selectedStatus);
            ratingField.setEnabled(isCompleted);
            ratingLabel.setEnabled(isCompleted);

            if (!isCompleted) {
                ratingField.setText("");
            }
        });

        // Initial state
        if (!"Completed".equals(data.myStatus)) {
            ratingField.setEnabled(false);
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 0)); // HAPUS PADDING BAWAH

        JButton cancelBtn = createStyledButton("Cancel", new Color(180, 180, 180), primaryColor);
        JButton saveBtn = createStyledButton("Save Changes", primaryColor, secondaryColor);

        cancelBtn.addActionListener(e -> dialog.dispose());

        saveBtn.addActionListener(e -> {
            String status = (String) statusCombo.getSelectedItem();
            String progress = progressField.getText().trim();
            String rating = ratingField.getText().trim();

            // Validasi
            if (!progress.matches("\\d+/\\d+")) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter progress in format: current/total (e.g., 10/100)",
                        "Invalid Format", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (status.equals("Completed") && !rating.isEmpty()) {
                try {
                    double ratingValue = Double.parseDouble(rating);
                    if (ratingValue < 1 || ratingValue > 10) {
                        JOptionPane.showMessageDialog(dialog,
                                "Rating must be between 1 and 10",
                                "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please enter a valid number for rating",
                            "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Update data
            data.myStatus = status;
            data.myProgress = progress;

            if (status.equals("Completed") && !rating.isEmpty()) {
                data.myRating = String.format("%.1f", Double.parseDouble(rating));
            } else {
                data.myRating = null;
            }

            // Simpan ke database
            myListRepo.update(data);

            dialog.dispose();

            // Refresh display
            displayFilteredCards(myListCards, true);

            JOptionPane.showMessageDialog(null,
                    "Updated '" + data.title + "' successfully!\n" +
                            "Status: " + status + "\n" +
                            "Progress: " + progress +
                            (rating.isEmpty() ? "" : "\nRating: " + rating + "/10"),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        mainPanel.add(buttonPanel);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
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

    // ===== HELPER METHODS =====
    private String extractTotalChapters(String chapterStr) {
        if (chapterStr.contains("/")) {
            String[] parts = chapterStr.split("/");
            if (parts.length > 1) {
                if (parts[1].matches("\\d+")) {
                    return parts[1];
                } else {
                    return parts[0];
                }
            }
        }
        return "100";
    }

    public void updateTotalItems(int count) {
        totalLabel.setText("Total: " + count + " items");
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    public void updateWelcomeMessage(String message) {
        welcomeLabel.setText(message);
    }

    public void clearCards() {
        cardsContainer.removeAll();
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    // ===== DATA CLASS =====
    public static class CardData {
        public String title;
        public String author;
        public String chapter;
        public String status;
        public String type;
        public String rating;
        public String myStatus;
        public String myProgress;
        public String myRating;

        public CardData(String title, String author, String chapter, String status,
                        String type, String rating) {
            this.title = title;
            this.author = author;
            this.chapter = chapter;
            this.status = status;
            this.type = type;
            this.rating = rating;
        }
    }

    public JPanel getMainPanel() {
        return (JPanel) mainPanel;
    }

    // Main method untuk testing (opsional, karena sudah ada MainControl)
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("ReadArchive - Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLocationRelativeTo(null);

        Dashboard dashboard = new Dashboard();
        frame.setContentPane(dashboard.getMainPanel());

        frame.setVisible(true);
    }
}