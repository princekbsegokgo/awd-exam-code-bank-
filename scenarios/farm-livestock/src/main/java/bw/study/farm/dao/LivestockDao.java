package cse24_033.dao;

import cse24_033.model.Livestock;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivestockDao {
    private static final String SELECT_COLUMNS =
        "SELECT a.animal_id, a.tag_number, a.species, a.breed, a.sex, " +
        "a.date_of_birth, a.status, a.farmer_id, f.full_name AS farmer_name " +
        "FROM livestock a JOIN farmers f ON f.farmer_id=a.farmer_id ";

    public List<Livestock> search(String search) throws SQLException {
        String sql = SELECT_COLUMNS +
          "WHERE a.tag_number LIKE ? OR a.species LIKE ? OR f.full_name LIKE ? " +
          "ORDER BY a.animal_id DESC";
        List<Livestock> result = new ArrayList<>();
        String like = "%" + (search == null ? "" : search.trim()) + "%";
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(fromRow(rs));
            }
        }
        return result;
    }

    public Livestock findById(int id) throws SQLException {
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(SELECT_COLUMNS + "WHERE a.animal_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? fromRow(rs) : null;
            }
        }
    }

    public boolean tagExists(String tag, Integer exceptId) throws SQLException {
        String sql = "SELECT 1 FROM livestock WHERE tag_number=? AND (? IS NULL OR animal_id<>?)";
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tag);
            if (exceptId == null) {
                ps.setNull(2, Types.INTEGER); ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(2, exceptId); ps.setInt(3, exceptId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void create(String tag, String species, String breed, String sex,
                       LocalDate dob, String status, int farmerId) throws SQLException {
        String sql = "INSERT INTO livestock(tag_number,species,breed,sex,date_of_birth,status,farmer_id) " +
                     "VALUES (?,?,?,?,?,?,?)";
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            bindFields(ps, tag, species, breed, sex, dob, status, farmerId);
            ps.executeUpdate();
        }
    }

    public boolean update(int id, String tag, String species, String breed, String sex,
                          LocalDate dob, String status, int farmerId) throws SQLException {
        String sql = "UPDATE livestock SET tag_number=?,species=?,breed=?,sex=?, " +
                     "date_of_birth=?,status=?,farmer_id=? WHERE animal_id=?";
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            bindFields(ps, tag, species, breed, sex, dob, status, farmerId);
            ps.setInt(8, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection con = Database.connection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM livestock WHERE animal_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private void bindFields(PreparedStatement ps, String tag, String species,
                            String breed, String sex, LocalDate dob, String status,
                            int farmerId) throws SQLException {
        ps.setString(1, tag);
        ps.setString(2, species);
        ps.setString(3, breed);
        ps.setString(4, sex);
        if (dob == null) ps.setNull(5, Types.DATE);
        else ps.setDate(5, Date.valueOf(dob));
        ps.setString(6, status);
        ps.setInt(7, farmerId);
    }

    private Livestock fromRow(ResultSet rs) throws SQLException {
        Date date = rs.getDate("date_of_birth");
        return new Livestock(
            rs.getInt("animal_id"), rs.getString("tag_number"),
            rs.getString("species"), rs.getString("breed"), rs.getString("sex"),
            date == null ? null : date.toLocalDate(), rs.getString("status"),
            rs.getInt("farmer_id"), rs.getString("farmer_name"));
    }
}
