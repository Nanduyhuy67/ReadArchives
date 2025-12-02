import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.util.ArrayList;

public class SearchPage {
    private JPanel SearchBar;
    private JTextField tfSearch;
    private JButton buttonSearch;
    private JFrame frame;
    private JList<String> listKomik;
    private DefaultListModel<String> listModel;

    // Data komik (contoh)
    private ArrayList<String> komikList = new ArrayList<>();

    public SearchPage() {
        // Data awal
        komikList.add("Solo Leveling");
        komikList.add("Omniscient Reader");
        komikList.add("Eleceed");
        komikList.add("Magic Emperor");
        komikList.add("Wind Breaker");

        frame = new JFrame("Search Komik");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel atas (search bar)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        tfSearch = new JTextField(20);
        buttonSearch = new JButton("Search");

        topPanel.add(tfSearch);
        topPanel.add(buttonSearch);

        // List hasil
        listModel = new DefaultListModel<>();
        listKomik = new JList<>(listModel);
        refreshList(komikList); // tampilkan semua di awal

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(listKomik), BorderLayout.CENTER);

        // Event tombol search
        buttonSearch.addActionListener(e -> searchKomik());

        // Event enter di text field
        tfSearch.addActionListener(e -> searchKomik());

        frame.setVisible(true);
    }

    private void searchKomik() {
        String keyword = tfSearch.getText().toLowerCase();
        ArrayList<String> filtered = new ArrayList<>();

        for (String k : komikList) {
            if (k.toLowerCase().contains(keyword)) {
                filtered.add(k);
            }
        }

        refreshList(filtered);
    }

    private void refreshList(ArrayList<String> data) {
        listModel.clear();
        for (String item : data) {
            listModel.addElement(item);
        }
    }

    public static void main(String[] args) {
        new SearchPage();
    }
}
