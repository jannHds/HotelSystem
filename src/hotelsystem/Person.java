
package hotelsystem;


public class Person {
    
    protected String Id;
    protected String name;
    protected String email;
    protected int age;
    protected String phoneNumber;
    protected String password; 

    public Person(String name , int age , String Id , String phoneNumber , String email, String password ){
        this.name = name;
        this.age = age;  
        this.Id = Id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password=password;
    }

    public String getName(){
        return name;
    }
    

    public void showDetails(){
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Id: " + Id);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phoneNumber);
    }

    public void updateContactDetails(String phoneNumber , String email ){
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}

class Staff extends Person{     
    public String jobTitle;
    public double salary;     

    public Staff(String name, int age, String Id ,String phoneNumber, String email,String password, String jobTitle, double salary){ 
        super(name, age, Id ,phoneNumber, email,password);
        this.jobTitle = jobTitle;       
        this.salary = salary;
    }

    public void raiseSalary(double amount){      
        this.salary += amount;
        System.out.println("Salary after raise: " + salary);          
    }         

    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Job Title: " + jobTitle);        
        System.out.println("Salary: " + salary);
    }      
            
}  

class  Customer extends Person {    
    protected Building build;

     Customer(String name, int age, String Id ,String phoneNumber, String email,String password, Building build){ 
        super(name, age, Id ,phoneNumber, email,password); 
        this.build = build;
    }

    public Building getBulid() {
        return build;
    }


    @Override
    public void showDetails() {
        super.showDetails();
        if (build != null) {
            System.out.println("Building: " + build.getBuildingID());
        } else {
            System.out.println("No Building assigned.");
        }
    }    
}
    
