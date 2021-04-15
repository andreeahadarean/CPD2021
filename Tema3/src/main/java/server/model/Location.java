package server.model;

public class Location {

    private String city;
    private boolean isRented;
    private int rentPeriod;

    public Location(String city, boolean isRented, int rentPeriod) {
        this.city = city;
        this.isRented = isRented;
        this.rentPeriod = rentPeriod;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public int getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(int rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", isRented=" + isRented +
                ", rentPeriod=" + rentPeriod +
                '}';
    }

    public void rent(int rentPeriod) {
        if(isRented) {
            System.out.println("The location in rented!");
        } else {
            this.rentPeriod = rentPeriod;
            this.isRented = true;
            System.out.println("You rented this location for " + rentPeriod + "months");
        }
    }
}
