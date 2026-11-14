package bw.study.farm.service;

import bw.study.farm.dao.FarmerDao;
import bw.study.farm.dao.LivestockDao;
import bw.study.farm.model.Farmer;
import bw.study.farm.model.Livestock;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** The service layer owns validation and business rules, not the JSP or Servlet. */
public class FarmService {
    private static final Set<String> SPECIES =
            Set.of("Cattle", "Goat", "Sheep", "Chicken", "Donkey");
    private static final Set<String> SEXES = Set.of("Female", "Male");
    private static final Set<String> STATUSES = Set.of("Active", "Sold", "Deceased");

    private final FarmerDao farmers = new FarmerDao();
    private final LivestockDao animals = new LivestockDao();

    public List<Farmer> allFarmers() throws SQLException { return farmers.findAll(); }
    public List<Livestock> searchAnimals(String query) throws SQLException {
        return animals.search(query);
    }
    public Farmer getFarmer(int id) throws SQLException { return farmers.findById(id); }
    public Livestock getAnimal(int id) throws SQLException { return animals.findById(id); }

    public void saveFarmer(Integer id, String fullName, String village, String phone)
            throws SQLException {
        String name = required(fullName, "Full name", 100);
        String town = required(village, "Village", 80);
        String contact = phone == null ? "" : phone.trim();
        if (contact.length() > 25)
            throw new IllegalArgumentException("Phone number must not exceed 25 characters.");
        if (id == null) {
            farmers.create(name, town, contact);
        } else if (!farmers.update(positive(id, "Farmer ID"), name, town, contact)) {
            throw new IllegalArgumentException("Farmer not found.");
        }
    }

    public void deleteFarmer(int id) throws SQLException {
        positive(id, "Farmer ID");
        if (farmers.findById(id) == null)
            throw new IllegalArgumentException("Farmer not found.");
        if (farmers.countAnimals(id) > 0)
            throw new IllegalArgumentException(
                "This farmer owns livestock. Reassign or delete the animals first.");
        if (!farmers.delete(id)) throw new IllegalArgumentException("Farmer not found.");
    }

    public void saveAnimal(Integer id, String tagNumber, String species, String breed,
                           String sex, String birthDateText, String status,
                           String farmerIdText) throws SQLException {
        String tag = required(tagNumber, "Tag number", 30).toUpperCase(Locale.ROOT);
        if (!tag.matches("[A-Z0-9-]{3,30}"))
            throw new IllegalArgumentException("Tag must use 3-30 letters, digits or hyphens.");
        String kind = required(species, "Species", 20);
        String animalBreed = required(breed, "Breed", 80);
        String animalSex = required(sex, "Sex", 10);
        String animalStatus = required(status, "Status", 20);
        if (!SPECIES.contains(kind)) throw new IllegalArgumentException("Invalid species.");
        if (!SEXES.contains(animalSex)) throw new IllegalArgumentException("Invalid sex.");
        if (!STATUSES.contains(animalStatus)) throw new IllegalArgumentException("Invalid status.");
        int farmerId = parseId(farmerIdText, "Farmer");
        if (farmers.findById(farmerId) == null)
            throw new IllegalArgumentException("Choose an existing farmer.");

        LocalDate dob = null;
        if (birthDateText != null && !birthDateText.isBlank()) {
            try { dob = LocalDate.parse(birthDateText); }
            catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Birth date must be a valid date (YYYY-MM-DD).");
            }
            if (dob.isAfter(LocalDate.now()))
                throw new IllegalArgumentException("Birth date cannot be in the future.");
        }
        if (id != null) positive(id, "Animal ID");
        if (animals.tagExists(tag, id))
            throw new IllegalArgumentException("An animal with this tag number already exists.");

        if (id == null) {
            animals.create(tag, kind, animalBreed, animalSex, dob, animalStatus, farmerId);
        } else if (!animals.update(id, tag, kind, animalBreed, animalSex, dob, animalStatus, farmerId)) {
            throw new IllegalArgumentException("Animal not found.");
        }
    }

    public void deleteAnimal(int id) throws SQLException {
        positive(id, "Animal ID");
        if (!animals.delete(id)) throw new IllegalArgumentException("Animal not found.");
    }

    public static int parseId(String raw, String label) {
        try { return positive(Integer.parseInt(raw), label); }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(label + " ID must be a whole number.");
        }
    }
    private static int positive(int value, String label) {
        if (value < 1) throw new IllegalArgumentException(label + " must be positive.");
        return value;
    }
    private static String required(String text, String label, int maxLength) {
        String value = text == null ? "" : text.trim();
        if (value.isEmpty() || value.length() > maxLength)
            throw new IllegalArgumentException(label + " is required (maximum " + maxLength + " characters).");
        return value;
    }
}