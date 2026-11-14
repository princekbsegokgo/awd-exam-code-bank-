package cse24_033.dao;

import cse24_033.model.Farmer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FarmerDao {
    // Subquery calculates the number of animals that each farmer owns.
    private static final String SELECT_COLUMNS =
        "SELECT f.farmer_id, f.full_name, f.village, f.phone, " +
        "(SELECT COUNT(*) FROM livestock a WHERE a.farmer_id=f.farmer_id) AS animal_count " +
        "FROM farmers f ";

    public List<Farmer> findAll() throws SQLException {
        List<Farmer> result = new ArrayList<>();
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(SELECT_COLUMNS + "ORDER BY f.full_name");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(fromRow(rs));
        }
        return result;
    }

    public Farmer findById(int id) throws SQLException {
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(SELECT_COLUMNS + "WHERE f.farmer_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? fromRow(rs) : null;
            }
        }
    }

    public void create(String name, String village, String phone) throws SQLException {
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(
                  "INSERT INTO farmers(full_name,village,phone) VALUES (?,?,?)")) {
            ps.setString(1, name);
            ps.setString(2, village);
            ps.setString(3, phone.isEmpty() ? null : phone);
            ps.executeUpdate();
        }
    }

    public boolean update(int id, String name, String village, String phone) throws SQLException {
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(
                  "UPDATE farmers SET full_name=?,village=?,phone=? WHERE farmer_id=?")) {
            ps.setString(1, name);
            ps.setString(2, village);
            ps.setString(3, phone.isEmpty() ? null : phone);
            ps.setInt(4, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(
                  "DELETE FROM farmers WHERE farmer_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public int countAnimals(int farmerId) throws SQLException {
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(
                  "SELECT COUNT(*) FROM livestock WHERE farmer_id=?")) {
            ps.setInt(1, farmerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private Farmer fromRow(ResultSet rs) throws SQLException {
        return new Farmer(rs.getInt("farmer_id"), rs.getString("full_name"),
                rs.getString("village"), rs.getString("phone"),
                rs.getInt("animal_count"));
    }
}
