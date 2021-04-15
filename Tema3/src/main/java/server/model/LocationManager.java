package server.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class LocationManager {

    Queue<Location> locations = new LinkedList<>();

    public LocationManager() {
        this.locations.add(new Location("London", false, 0));
        this.locations.add(new Location("Berlin", false, 0));
    }

    public void addLocation(String city) {
        Location location = new Location(city, false, 0);
        locations.add(location);
    }

    public Location rentLocation(String city, int rentPeriod) {
        List<String> availableCities = new ArrayList<>();
        locations.stream().peek(location -> {
            if(!location.isRented())
                availableCities.add(location.getCity());
        }).collect(Collectors.toList());
        AtomicReference<Location> rented = null;
        if(availableCities.contains(city)) {
            locations.stream().peek(location -> {
                if(location.getCity().equals(city))
                    rented.set(location);
            }).collect(Collectors.toList());
            rented.get().setRented(true);
            rented.get().setRentPeriod(rentPeriod);
        }
        return rented.get();
    }

    public void printLocations() {
        for(Location location : locations) {
            System.out.println(location.toString());
        }
    }
}
