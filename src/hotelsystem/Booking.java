
package hotelsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Booking {
   
    protected String bookingId;
    protected Building build;
    protected Customer customer;
    protected LocalDate bookingDate;
    protected LocalDate checkInTime;
    protected LocalDate checkOutTime;
    public static final String AVAILABLE = "Available";
    public static final String NOTAVAILABLE = "NotAvailable";
    protected String status;//uml 
    protected boolean insurance;
    
    public Booking(String bookingId, Building build, Customer customer, LocalDate bookingDate,
            LocalDate checkInTime, LocalDate checkOutTime) {
        this.bookingId = bookingId;
        this.build= build;
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.insurance=true;

        
        if (build.checkAvailability(checkInTime, checkOutTime)) {
            this.status = AVAILABLE;   
        } else {this.status = NOTAVAILABLE;}
    }

    public void withOutInsurance( ) {
        this.insurance = false;
    }
    

    public boolean createBooking() {
        if (build.checkAvailability(checkInTime, checkOutTime)) {
            build.addBooking(checkInTime, checkOutTime);
            status = "Confirmed"; 
            System.out.println("Booking created successfully for Building " + build.getBuildingID() +
             " from " + checkInTime + " to " + checkOutTime);
            return true;
        } else { System.out.println("Failed to create booking Building " + build.getBuildingID() +
                    " not available from " + checkInTime + " to " + checkOutTime);
            return false; } }

    public void cancelBooking() {
        if (status.equals("Confirmed")) {
            build.cancelBooking(checkInTime, checkOutTime);
            this.status = "Cancelled";
            System.out.println("The booking has been cancelled for Building " + build.getBuildingID() 
                               + " from " + checkInTime + " to " + checkOutTime);
        } else {
            System.out.println("The booking cannot be cancelled because it is not confirmed");} }

    public void displayBooking() {
        System.out.println("Booking ID: " + bookingId 
                + ", Building: " + build.getBuildingID() 
                + ", Customer name: " + customer.getName()
                + ", Date: " + bookingDate 
                + ", Status: " + status);}
    public abstract double calculateTotalCost();
    
}

class PremiumBooking extends Booking {
    
    private boolean maid;
    private boolean driver;

    public PremiumBooking(String bookingId, Building build, Customer customer, LocalDate bookingDate,
            LocalDate checkInTime, LocalDate checkOutTime) {
        super(bookingId, build, customer, bookingDate, checkInTime, checkOutTime);
        this.maid=true;
        this.driver=true;
    }

    public void removeMaid(){
        this.maid=false;
    }
    
    public void removeDriver(){
        this.driver=false;
    }
    
    public int HasMaid(){ 
        if(maid) return 750;
        return 0;
    }
    public int HasDriver(){ 
        if(driver) return 500;
        return 0;
    }

    @Override
    public double calculateTotalCost() {
        double totalCost = build.calculatePrice() + HasDriver()+ HasMaid() ;       
        return totalCost; }

    @Override
    public void displayBooking() {
        super.displayBooking();
    }
}


class StandardBooking extends Booking {
    boolean wifi;
    boolean withFurniture; 

    public StandardBooking(String bookingId, Building build, Customer customer, LocalDate bookingDate,
            LocalDate checkInTime, LocalDate checkOutTime) {
        super(bookingId, build, customer, bookingDate, checkInTime, checkOutTime);
        this.wifi=true;
        this.withFurniture=true;
    }
    public void withOutWifi(){
        this.wifi=false;
    }
    public void withOutFurniture(){
        this.withFurniture=false;
    }

    @Override
    public double calculateTotalCost() {
        double totalCost = build.calculatePrice();
        if(wifi) {
            totalCost += 100; }
        if(withFurniture){
            totalCost += 200;
        }
        return totalCost;
    }

    @Override
    public void displayBooking() {
        super.displayBooking();
    }
}