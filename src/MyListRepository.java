import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MyListRepository {

    // Menambahkan data pada My List
    public void insert(Dashboard.CardData data) {
        String sql = "INSERT INTO my_list " +
                "(title, author, chapter, status, type, rating, my_status, my_progress, my_rating) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, data.title);
            ps.setString(2, data.author);
            ps.setString(3, data.chapter);
            ps.setString(4, data.status);
            ps.setString(5, data.type);
            ps.setString(6, data.rating);
            ps.setString(7, data.myStatus);
            ps.setString(8, data.myProgress);
            ps.setString(9, data.myRating);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Menampilkan data dari My List
    public List<Dashboard.CardData> getAll() {
        List<Dashboard.CardData> list = new ArrayList<>();
        String sql = "SELECT * FROM my_list";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Dashboard.CardData data = new Dashboard.CardData(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("chapter"),
                        rs.getString("status"),
                        rs.getString("type"),
                        rs.getString("rating")
                );
                data.myStatus = rs.getString("my_status");
                data.myProgress = rs.getString("my_progress");
                data.myRating = rs.getString("my_rating");

                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Update perubahan data dari edit My List
    public void update(Dashboard.CardData data) {
        String sql = "UPDATE my_list SET " +
                "my_status = ?, my_progress = ?, my_rating = ? " +
                "WHERE title = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, data.myStatus);
            ps.setString(2, data.myProgress);
            ps.setString(3, data.myRating);
            ps.setString(4, data.title);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Menghapus data dari My List
    public void delete(String title) {
        String sql = "DELETE FROM my_list WHERE title = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
