
package hotelsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Building {
  
    protected String Building_id;
    protected boolean isBooked;
    protected String address;
    protected int size;
   
    protected List<BookingPeriod> bookingHistory;

    public Building(String Building_id,String address,int size) {
        this.Building_id= Building_id;
        this.address = address;
        this.size = size;
        
        this.bookingHistory = new ArrayList<>();
    }

    public boolean checkAvailability(LocalDate checkIn, LocalDate checkOut) {
        for (BookingPeriod period : bookingHistory) {
            if (period.overlapsWith(checkIn, checkOut)) {
                return false;
            }
        }
        return true;
    }

    public void addBooking(LocalDate checkIn, LocalDate checkOut) {
        bookingHistory.add(new BookingPeriod(checkIn, checkOut));
        isBooked = true;
    }

    public void cancelBooking(LocalDate checkIn, LocalDate checkOut) {
        for (int i = 0; i < bookingHistory.size(); i++) {
            BookingPeriod period = bookingHistory.get(i);
            if (period.getCheckIn().equals(checkIn) && period.getCheckOut().equals(checkOut)) {
                bookingHistory.remove(i);
                break;
            }
        }
        if (bookingHistory.isEmpty()) {
            isBooked = false;
        }
    }

    public String getBuildingID() {
        return Building_id;
    }

    public String getAddress(){
        return address;
    }

    public int getSize() {
        return size;
    }

    public double calculatePrice() {
        double basePrice = 1000; 
        return basePrice;
    }

    private class BookingPeriod {
        private LocalDate checkIn;
        private LocalDate checkOut;

        public BookingPeriod(LocalDate checkIn, LocalDate checkOut) {
            this.checkIn = checkIn;
            this.checkOut = checkOut;
        }

        public boolean overlapsWith(LocalDate otherCheckIn, LocalDate otherCheckOut) {
            return !(otherCheckOut.isBefore(checkIn) || otherCheckIn.isAfter(checkOut));
        }

        public LocalDate getCheckIn() {
            return checkIn;
        }

        public LocalDate getCheckOut() {
            return checkOut;
        }
    }
    
    @Override
    public String toString() {
        return "Building ID: " + Building_id +  ", Address: " + address;
}


}

class Apartment extends Building {
   
    protected boolean elevator;
    protected boolean security_system;
    protected int floor;

    public Apartment(String Building_id,String address,int size,int floor) {
        super(Building_id, address, size);
        this.Building_id=Building_id;
        this.floor=floor;
        this.elevator=true;
        this.security_system=true;
        
    }
    
    
    public int getFloor(){
        return floor;
    }
    
    public void withOutElevator() {
        this.elevator = false;
    }

    public void withOutSecurity_system() {
        this.security_system = false;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    
    
    @Override

    public double calculatePrice() {
        double basePrice = super.calculatePrice();
        if (elevator) basePrice += 200;
        if (security_system) basePrice += 150;
        basePrice += floor * 5;
        return basePrice;
    }

}

class Villa extends Building {
   
    protected int numberOfFloors;
    protected boolean garden;
    protected boolean swimmingPool;

    public Villa(String Building_id, String address, int size,int numberOfFloors) {
        super( Building_id, address, size);
        this.numberOfFloors = numberOfFloors;
        this.garden = true;
        this.swimmingPool = true;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public void withOutGarden() {
        this.garden = false;
    }

    public void withOutSwimmingPool() {
        this.swimmingPool = false;
    }
    
    @Override
    public double calculatePrice() {
        double price = super.calculatePrice();
        price += numberOfFloors * 30;
        if (garden) price += 1000;
        if (swimmingPool) price += 500;
        return price;
    }

}
