import javax.swing.*;
import java.awt.*;

public class Dashboard {

    public Container mainPanel;

    public static void main(String[] args) {
        new DashboardPage();
    }

    private Color primaryColor = new Color(51, 61, 87);      // Biru tua
    private Color secondaryColor = new Color(249, 206, 146); // Cream

    public Dashboard() {
        JFrame frame = new JFrame("ReadArchive Dashboard");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // ========================= HEADER =========================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primaryColor);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("ReadArchive");
        title.setForeground(secondaryColor);
        title.setFont(new Font("Serif", Font.BOLD, 42));

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 5));
        menu.setOpaque(false);

        JLabel profile = headerLabel("Profile");
        JLabel library = headerLabel("Library");
        JLabel exit = headerLabel("Exit");

        menu.add(profile);
        menu.add(library);
        menu.add(exit);

        header.add(title, BorderLayout.WEST);
        header.add(menu, BorderLayout.EAST);

        // ====================== CONTENT WRAPPER BIRU ======================
        JPanel centerWrapper = new JPanel();
        centerWrapper.setBackground(primaryColor);
        centerWrapper.setPreferredSize(new Dimension(850, 350));
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));

        JLabel totalLabel = new JLabel("Total: XX Items");
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ===================== ROW ATAS: TOTAL ITEMS + BUTTON =====================
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false); // supaya menyatu dengan panel biru

        // kiri = total items
        topRow.add(totalLabel, BorderLayout.WEST);

        // kanan = tombol add new collections
        JButton addBtn = new JButton("Add New Collections");
        addBtn.setFont(new Font("Arial", Font.BOLD, 12));
        addBtn.setBackground(secondaryColor);
        addBtn.setForeground(primaryColor);
        addBtn.setFocusPainted(false);

        topRow.add(addBtn, BorderLayout.EAST);

        centerWrapper.add(topRow);
        centerWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        // ========================= GRID 4 KOTAK =========================
        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setOpaque(false);

        grid.add(card("Title", "author?", "Chapter XX/XX", "Reading"));
        grid.add(card("Title", "author?", "Chapter XX/XX", "Completed", "11/100"));
        grid.add(card("Title", "author?", "Chapter XX/XX", "Plan to Read"));
        grid.add(card("Title", "author?", "Chapter XX/XX", "Reading"));

        centerWrapper.add(grid);

        // ========================= FOOTER PAGINATION =========================
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(primaryColor);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel prev = footerLabel("[Prev]");
        JLabel next = footerLabel("[Next]");
        JLabel pageInfo = new JLabel("Page 1 of X", SwingConstants.CENTER);
        pageInfo.setForeground(Color.WHITE);
        pageInfo.setFont(new Font("Arial", Font.PLAIN, 14));

        footer.add(prev, BorderLayout.WEST);
        footer.add(pageInfo, BorderLayout.CENTER);
        footer.add(next, BorderLayout.EAST);

        // ========================== ADD TO MAIN ==========================
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel centerHolder = new JPanel(new GridBagLayout());
        centerHolder.setBackground(Color.WHITE);
        centerHolder.add(centerWrapper, new GridBagConstraints());

        mainPanel.add(centerHolder, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private JLabel headerLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(secondaryColor);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println(text + " clicked!");
            }
        });

        return lbl;
    }

    private JLabel footerLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println(text + " clicked!");
            }
        });

        return lbl;
    }

    private JPanel card(String title, String author, String chapter, String status) {
        return card(title, author, chapter, status, null);
    }

    private JPanel card(String title, String author, String chapter, String status, String rate) {
        JPanel c = new JPanel();
        c.setBackground(secondaryColor);
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel a = new JLabel(author);
        JLabel ch = new JLabel(chapter);

        JLabel st = new JLabel("Stats: " + status);
        st.setFont(new Font("Arial", Font.BOLD, 13));

        c.add(t);
        c.add(Box.createRigidArea(new Dimension(0, 5)));
        c.add(a);
        c.add(ch);

        c.add(Box.createRigidArea(new Dimension(0, 10)));
        c.add(st);

        if (rate != null) {
            JLabel r = new JLabel("Rate: " + rate);
            r.setFont(new Font("Arial", Font.BOLD, 12));
            c.add(r);
        }

        return c;
    }
}