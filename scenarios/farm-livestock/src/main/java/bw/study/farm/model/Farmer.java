package bw.study.farm.model;

/** One farmer can own many livestock records. JavaBean getters support JSP EL. */
public class Farmer {
    private final int farmerId;
    private final String fullName;
    private final String village;
    private final String phone;
    private final int animalCount;

    public Farmer(int farmerId, String fullName, String village, String phone, int animalCount) {
        this.farmerId = farmerId;
        this.fullName = fullName;
        this.village = village;
        this.phone = phone;
        this.animalCount = animalCount;
    }

    public int getFarmerId() { return farmerId; }
    public String getFullName() { return fullName; }
    public String getVillage() { return village; }
    public String getPhone() { return phone; }
    public int getAnimalCount() { return animalCount; }
}