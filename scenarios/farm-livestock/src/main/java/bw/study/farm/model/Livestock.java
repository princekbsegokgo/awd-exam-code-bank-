package bw.study.farm.model;

import java.time.LocalDate;

/** Animal tag must be unique and each animal must belong to a farmer. */
public class Livestock {
    private final int animalId;
    private final String tagNumber, species, breed, sex, status;
    private final LocalDate dateOfBirth;
    private final int farmerId;
    private final String farmerName;

    public Livestock(int animalId, String tagNumber, String species, String breed,
                     String sex, LocalDate dateOfBirth, String status,
                     int farmerId, String farmerName) {
        this.animalId = animalId;
        this.tagNumber = tagNumber;
        this.species = species;
        this.breed = breed;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.farmerId = farmerId;
        this.farmerName = farmerName;
    }

    public int getAnimalId() { return animalId; }
    public String getTagNumber() { return tagNumber; }
    public String getSpecies() { return species; }
    public String getBreed() { return breed; }
    public String getSex() { return sex; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getStatus() { return status; }
    public int getFarmerId() { return farmerId; }
    public String getFarmerName() { return farmerName; }
}