package hotelsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


public class GUI extends JFrame {
         
    public JLabel label1; 
    public JTextField textField; 
    public JLabel label2;
    public JPasswordField PasswordField; 
    public JButton button;  
     
    public GUI(){
      
        
     // === Window setup ===   
     setTitle("Login");
     setSize(850,400);
     setDefaultCloseOperation(EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setResizable(false);
     setLayout(new FlowLayout()); 
     
     // === Main panel using BorderLayout ===
     JPanel MainPanel = new JPanel(new BorderLayout());
     MainPanel.setBackground(Color.WHITE);
      
     // === Left-side image ===
     JLabel imageLabel = new JLabel();
     ImageIcon icon=new ImageIcon(getClass().getResource("/main/Login-cuate.png"));
     imageLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH)));
     imageLabel.setPreferredSize(new Dimension(350,400));  

     // === Login form panel ===
     JPanel loginP = new JPanel(null);
     loginP.setPreferredSize(new Dimension(500,400));
     loginP.setBackground(Color.WHITE);
     
     // === Logo on the top right ===
     ImageIcon logoIcon = new ImageIcon(getClass().getResource("/main/logo1.png"));
     JLabel logoLabel = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH)));
     logoLabel.setBounds(360, 10, 100, 40); 
     loginP.add(logoLabel);

     // === Title label ===
     JLabel title = new JLabel("Login");
     title.setFont(new Font("Arial",Font.BOLD,24));
     title.setForeground(new Color(50, 50, 50));
     title.setBounds(120, 50, 150, 30);
     
     // === ID input field ===
     JTextField id =new JTextField ("ID");
     id.setBounds(120, 90, 250, 35);
     id.setFont(new Font("Segoe UI", Font.PLAIN, 14));
     id.setForeground(Color.GRAY);
     // Placeholder logic for ID field
     id.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (id.getText().equals("ID")) {
                    id.setText("");
                    id.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (id.getText().isEmpty()) {
                    id.setText("ID");
                    id.setForeground(Color.GRAY);
                }
            }
        });
     
     // === Password input field ===
     JPasswordField pass=new JPasswordField();
     pass.setBounds(120, 140, 250, 35);
     pass.setEchoChar((char) 0);
     pass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
     pass.setForeground(Color.GRAY);
     // Placeholder logic for Password field
     pass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(pass.getPassword()).equals("Password")) {
                    pass.setText("");
                    pass.setEchoChar('*'); 
                    pass.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(pass.getPassword()).isEmpty()) {
                    pass.setText("Password");
                    pass.setEchoChar((char) 0); 
                    pass.setForeground(Color.GRAY);
                }
            }
        });
     
     // ===== LogIn =====
     JButton loginButton = new JButton("Login");
     loginButton.setBounds(120, 200, 250, 40);  
     loginButton.setBackground(new Color(155, 119, 219));
     loginButton.setForeground(Color.WHITE);
     loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
     loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
     loginButton.setBorder(BorderFactory.createEmptyBorder());
     
     // Hover effect for login button
     loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(185, 145, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(155, 119, 219));
            }
        });
     
        
     // ===== create account(signUp)=====
     JButton signUp = new JButton("Don't have an account? Create one");
     signUp.setBounds(120, 260, 250, 25);
     signUp.setBorderPainted(false);// Disable the border around the button 
     signUp.setContentAreaFilled(false);// Make the button background transparent (no default gray fill) 
     signUp.setFocusPainted(false);// Remove the focus highlight (the blue outline when selected)
     signUp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
     signUp.setForeground(new Color(30, 80, 200));
     signUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
     
     // === Add all components to login panel ===
     loginP.add(title);
     loginP.add(id);
     loginP.add(pass);
     loginP.add(loginButton);
     loginP.add(signUp);
     // === Add image and login panel to main panel ===
     MainPanel.add(imageLabel,BorderLayout.WEST);
     MainPanel.add(loginP,BorderLayout.CENTER);
     // Add main panel to the JFrame
     add(MainPanel);
     
    // === Login action ===
    loginButton.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e) {
             String ida = id.getText();
             String password = new String(pass.getPassword());
             // Try to login as Staff
        if (tryLogin("Staff", ida, password)) {
            return;
        }// Try to login as Customer
        if (tryLogin("Customer", ida, password)) {
            return;
        }// If both fail, show error
        JOptionPane.showMessageDialog(GUI.this, "Invalid ID or Password");
    }
         // Reusable login logic for both Staff and Customer
         private boolean tryLogin(String tableName, String id, String password) {
             
             String columnId = tableName.equals("Customer") ? "customer_id" : "id";
             String sql = "SELECT * FROM " + tableName + " WHERE " + columnId + " = ? AND password = ?";
             String url = "jdbc:mysql://localhost:3306/LegendsCompany"; 
             String user = "root"; 
             String p = "Jhds2005";
            
             try (Connection conn = DriverManager.getConnection(url,user,p);
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                 stmt.setString(1, id);
                 stmt.setString(2, password);
                 ResultSet rs = stmt.executeQuery();
        
                 if (rs.next()) {
                     String name = rs.getString("name");
                     if (tableName.equals("Staff")) {
                         int age = rs.getInt("age");
                         String phone = rs.getString("phoneNumber");
                         String email = rs.getString("email");
                         String jobTitle = rs.getString("jobTitle");
                         double salary = rs.getDouble("salary");
                         // create Staff object
                         Staff staff = new Staff(name, age, id, phone, email, password, jobTitle, salary);
                         new StaffGUI(staff).setVisible(true);
                     } else {
                         int age = rs.getInt("age");
                         String phone = rs.getString("phone_number");
                         String email = rs.getString("email");
                         // create customer object
                         Customer customer = new Customer(name, age, id, phone, email, password, null);
                         new CustomerGUI(customer).setVisible(true);
                     }
                     dispose();// Close the login window
                     return true;
                 }
             } catch (SQLException e) {
                 JOptionPane.showMessageDialog(GUI.this, "Database Error");
                 e.printStackTrace();
             }
             return false;
         }});
    
    // === Sign-up button logic ===
    signUp.addActionListener(new ActionListener(){
         
         @Override
         public void actionPerformed(ActionEvent e) {
             LogOn secW=new LogOn();// Open the sign-up form
             secW.setVisible(true);
         }
         
     });
     
    
    setVisible(true);// Show the login window
   
    }
    
    static class StaffGUI extends JFrame {

    private Staff staff;
    private JLabel nameLabel;
    private JLabel jobLabel;
    private JLabel salaryLabel;
    private JTable bookingsTable;
    private JComboBox<String> filterCombo;


    public StaffGUI(Staff staff) {
        this.staff = staff;

        // ===== Staff Frame Setup =====
        setTitle("Staff Dashboard");
        setSize(850, 520); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ==== Image ====
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/main/staff.png"));
        Image img = icon.getImage().getScaledInstance(300, 180, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        imageLabel.setPreferredSize(new Dimension(350, 450));
      

        // ===== Right Panel (Info + Controls) =====
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.WHITE);
        imageLabel.setBounds(490, 10, 300, 180);
        rightPanel.add(imageLabel);

        // Staff name label
        nameLabel = new JLabel("Welcome, " + staff.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(new Color(155, 119, 219)); 
        nameLabel.setBounds(50, 30, 400, 30);
        rightPanel.add(nameLabel);

        // Job title label
        jobLabel = new JLabel("Job Title: " + staff.jobTitle);
        jobLabel.setBounds(50, 80, 400, 25);
        rightPanel.add(jobLabel);

        // Salary label
        salaryLabel = new JLabel("Salary: " + staff.salary);
        salaryLabel.setBounds(50, 120, 400, 25);
        rightPanel.add(salaryLabel);

        // Raise salary button
        JButton raiseBtn = new JButton("Raise Salary");
        raiseBtn.setBounds(50, 160, 200, 35);
        raiseBtn.setBackground(new Color(155, 119, 219)); 
        raiseBtn.setForeground(Color.WHITE);
        rightPanel.add(raiseBtn);
        
        // Filter bookings by type
        filterCombo = new JComboBox<>(new String[]{"All", "Premium", "Standard"});
        filterCombo.setBounds(400, 160, 200, 30);
        rightPanel.add(filterCombo);

        // ===== Logout Button (Icon Only) =====
        JButton logoutBtn = new JButton("");
        logoutBtn.setBounds(270, 160, 100, 35);
        rightPanel.add(logoutBtn);
        ImageIcon logoutIcon = new ImageIcon(getClass().getResource("/main/sign-out-alt.png"));
        Image iconImg = logoutIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        logoutBtn.setIcon(new ImageIcon(iconImg));
        logoutBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
        logoutBtn.setIconTextGap(10);
        logoutBtn.setFocusPainted(false);// Remove the focus highlight (the blue outline when selected)
        logoutBtn.setBorderPainted(false);// Disable the border around the button
        logoutBtn.setContentAreaFilled(false);// Make the button background transparent (no default gray fill)
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setForeground(new Color(60, 60, 60)); 
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // ===== "Logout" =====
        JLabel logoutLabel = new JLabel("Logout");
        logoutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutLabel.setForeground(Color.DARK_GRAY);
        logoutLabel.setBounds(270, 195, 100, 20); 
        logoutLabel.setVisible(false);// Hide the logout label 
        rightPanel.add(logoutLabel);

        // Hover effect for logout button
        logoutBtn.addMouseListener(new MouseAdapter() { 
            @Override  
            public void mouseEntered(MouseEvent e) {     
                logoutLabel.setVisible(true); 
            }
            @Override
            public void mouseExited(MouseEvent e) {
                logoutLabel.setVisible(false); 
            }});

        // Reload bookings on filter selection
        filterCombo.addActionListener(e -> {
        String selected = (String) filterCombo.getSelectedItem();
        loadAllBookings(selected);
        });

        // ==== booking Table ====
        bookingsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBounds(50, 220, 600, 230); 
        logoutBtn.setBackground(new Color(200, 200, 200));
        logoutBtn.setForeground(Color.DARK_GRAY);
        rightPanel.add(scrollPane); 

        // Add the panel to frame
        add(rightPanel, BorderLayout.CENTER);

        // Load all bookings initially
        loadAllBookings("All");

        // ===== Raise Salary Action =====
        raiseBtn.addActionListener(e -> {
            // Dialog window for input
            JDialog raiseDialog = new JDialog(this, "Raise Salary", true);
            raiseDialog.setSize(300, 180);
            raiseDialog.setLayout(null);
            raiseDialog.setLocationRelativeTo(this);
            raiseDialog.getContentPane().setBackground(Color.WHITE);
  
            // ===== Label =====
            JLabel label = new JLabel("Enter raise amount:");
            label.setBounds(30, 20, 200, 25);
            raiseDialog.add(label);
   
            // ===== Text Field =====
            JTextField amountField = new JTextField();
            amountField.setBounds(30, 50, 220, 25);
            raiseDialog.add(amountField);
 
            // ===== Confirm Button =====
            JButton confirmBtn = new JButton("Apply Raise");
            confirmBtn.setBounds(60, 90, 160, 40);
            confirmBtn.setFocusPainted(false);
            confirmBtn.setForeground(Color.WHITE);
            confirmBtn.setBackground(new Color(155, 119, 219)); 
            confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            confirmBtn.setBorder(BorderFactory.createLineBorder(new Color(153, 102, 255), 2));
  
            raiseDialog.add(confirmBtn);
            // Hover effect
           Color originalColor = confirmBtn.getBackground();
           Color hoverColor = new Color(134, 95, 207); 
           confirmBtn.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseEntered(MouseEvent e) {
                   confirmBtn.setBackground(hoverColor);
               }
               @Override
               public void mouseExited(MouseEvent e) {
                   confirmBtn.setBackground(originalColor);
               }
           });

           // Raise salary logic
            confirmBtn.addActionListener(event -> {
                try {
                    if (amountField.getText().trim().isEmpty()) {       
                        JOptionPane.showMessageDialog(raiseDialog, "Please enter an amount.");
                        return;
                    }
                    double amount = Double.parseDouble(amountField.getText());
                    staff.raiseSalary(amount);// Update staff object         
                    salaryLabel.setText("Salary: " + staff.salary);// Refresh UI     
                    updateSalaryInDB(staff.Id, staff.salary);// Update DB       
                    raiseDialog.dispose();      
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(raiseDialog, "Invalid number.");
                }
            });
            raiseDialog.setVisible(true);
        });
 
        // Add an action listener to Open the login screen again
        logoutBtn.addActionListener(e -> {
            dispose();
            new GUI();
        });

        setVisible(true);
    }

    // === Load Bookings from Database based on type ===
    private void loadAllBookings(String filter) {
        // Database connection
        String url = "jdbc:mysql://localhost:3306/LegendsCompany";
        String user = "root";
        String password = "Jhds2005";
    
        // Create a table model with column headers
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Booking ID", "Customer ID", "Building ID",
            "Check-In", "Check-Out", "Type", "Price (SAR)"});

        try (Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement()) {
            // If filter is "All" or "Premium", fetch premium bookings
            if (filter.equals("All") || filter.equals("Premium")) {
                ResultSet rs = stmt.executeQuery("SELECT booking_id, customer_id, building_id, check_in, check_out, price FROM PremiumBooking");
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getString("booking_id"),rs.getString("customer_id"),
                        rs.getString("building_id"),rs.getDate("check_in"),rs.getDate("check_out"),
                        "Premium",String.format("%.2f", rs.getDouble("price")) + " SAR"
                    });
                }
            }// If filter is "All" or "Standard", fetch standard bookings
            if (filter.equals("All") || filter.equals("Standard")) {
                ResultSet rs = stmt.executeQuery("SELECT booking_id, customer_id, building_id, check_in, check_out, price FROM StandardBooking");
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getString("booking_id"), rs.getString("customer_id"),
                    rs.getString("building_id"), rs.getDate("check_in"), rs.getDate("check_out"),
                    "Standard",String.format("%.2f", rs.getDouble("price")) + " SAR"
            }); }
    }
            // Set the updated model to the bookings table
            bookingsTable.setModel(model);
            
        } catch (SQLException e) {
             // Handle DB errors and show message to user
            JOptionPane.showMessageDialog(this, "Failed to load bookings.");
            e.printStackTrace();
        }
    }
    
    // === Update staff salary in the database ===
    private void updateSalaryInDB(String staffId, double newSalary) {
        // SQL query to update the salary for a specific staff member
        String sql = "UPDATE Staff SET salary = ? WHERE Id = ?";
        String url = "jdbc:mysql://localhost:3306/LegendsCompany";
        String user = "root";
        String password = "Jhds2005";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind parameters to query
            stmt.setDouble(1, newSalary);
            stmt.setString(2, staffId);
            // Execute update
            stmt.executeUpdate();

            System.out.println("Salary updated in database.");
        } catch (Exception e) {
            // Error handling
            JOptionPane.showMessageDialog(this, "Failed to update salary in database.");
            e.printStackTrace();}
    }

    }
    
    static class CustomerGUI extends JFrame {

    private Customer customer;
    private JLabel nameLabel, phoneLabel, emailLabel;
    private JTable bookingsTable;
    
    

    public CustomerGUI(Customer customer) {
        this.customer = customer;

        // ===== Customer Frame Setup =====
        setTitle("Customer Dashboard");
        setSize(850, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Customer Image ===
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/main/customer.png"));
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        imageLabel.setPreferredSize(new Dimension(350, 450));

        // === Right Panel ===
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.WHITE);
        imageLabel.setBounds(580, 0, 220, 220);
        rightPanel.add(imageLabel);

        // === Customer Info Labels ===
        nameLabel = new JLabel("Welcome, " + customer.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(new Color(155, 119, 219)); 
        nameLabel.setBounds(50, 30, 400, 30);
        rightPanel.add(nameLabel);

        phoneLabel = new JLabel("Phone: " + customer.phoneNumber);
        phoneLabel.setBounds(50, 80, 400, 25);
        rightPanel.add(phoneLabel);

        emailLabel = new JLabel("Email: " + customer.email);
        emailLabel.setBounds(50, 120, 400, 25);
        rightPanel.add(emailLabel);

        // ==== Edit Info Button ====
        JButton editInfoBtn = new JButton("Edit Info");
        editInfoBtn.setBounds(50, 160, 150, 40);
        editInfoBtn.setBackground(new Color(155, 119, 219)); 
        editInfoBtn.setForeground(Color.WHITE);
        editInfoBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editInfoBtn.setFocusPainted(false);// Remove the focus highlight (the blue outline when selected)
        editInfoBtn.setBorder(BorderFactory.createEmptyBorder());// Remove the button's border by setting an empty (invisible) border
        rightPanel.add(editInfoBtn);

        // Hover effect
        editInfoBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                editInfoBtn.setBackground(new Color(185, 145, 255)); }
            public void mouseExited(MouseEvent e) {
                editInfoBtn.setBackground(new Color(155, 119, 219));
            }});

        
        // ==== New Booking Button ====
        JButton newBookingBtn = new JButton("New Booking");
        newBookingBtn.setBounds(220, 160, 150, 40);
        newBookingBtn.setBackground(new Color(155, 119, 219));
        newBookingBtn.setForeground(Color.WHITE);
        newBookingBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        newBookingBtn.setFocusPainted(false);// Remove the focus highlight (the blue outline when selected)
        newBookingBtn.setBorder(BorderFactory.createEmptyBorder());// Remove the button's border by setting an empty (invisible) border
        rightPanel.add(newBookingBtn);
        
        JButton cancelBookingBtn = new JButton("Cancel Booking");
        cancelBookingBtn.setBounds(390, 160, 150, 40);
        cancelBookingBtn.setBackground(new Color(220, 80, 80));
        cancelBookingBtn.setForeground(Color.WHITE);
        cancelBookingBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelBookingBtn.setFocusPainted(false);
        cancelBookingBtn.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.add(cancelBookingBtn);
        
        // Hover effect
        cancelBookingBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                cancelBookingBtn.setBackground(new Color(255, 100, 100)); }
            public void mouseExited(MouseEvent e) {
                cancelBookingBtn.setBackground(new Color(220, 80, 80)); }
        });

        // Cancel booking hover effect
        cancelBookingBtn.addActionListener(e -> {
            int row = bookingsTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking to cancel.");    
                return;}
            String bookingId = bookingsTable.getValueAt(row, 0).toString();
            String buildingId = bookingsTable.getValueAt(row, 1).toString();
            String bookingType = bookingsTable.getValueAt(row, 4).toString();

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);   
            if (confirm != JOptionPane.YES_OPTION) return;
            if (cancelBooking(bookingId, buildingId, bookingType)) {
                JOptionPane.showMessageDialog(this, "Booking cancelled.");
                loadCustomerBookings(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking.");
            }
        });

        
        // Hover effect for New booking
        newBookingBtn.addMouseListener(new MouseAdapter() {  
            public void mouseEntered(MouseEvent e) {
                newBookingBtn.setBackground(new Color(160, 120, 230)); }
            public void mouseExited(MouseEvent e) {
                newBookingBtn.setBackground(new Color(130, 90, 200));
        }});

        // ==== Logout Icon Button ====
        JButton logoutBtn = new JButton();
        logoutBtn.setBounds(500, 20, 40, 40);
        ImageIcon logoutIcon = new ImageIcon(getClass().getResource("/main/sign-out-alt.png"));
        Image logoutImg = logoutIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        logoutBtn.setIcon(new ImageIcon(logoutImg));
        logoutBtn.setFocusPainted(false);// Remove the focus highlight (the blue outline when selected)
        logoutBtn.setBorderPainted(false);// Disable the border around the button
        logoutBtn.setContentAreaFilled(false);// Make the button background transparent (no default gray fill)
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));// Change the mouse cursor to a hand icon when hovering over the logout button
        logoutBtn.setToolTipText("Logout");

        // Label to temporarily display "Logout" text
        JLabel logoutLabel = new JLabel("Logout");
        logoutLabel.setBounds(550, 25, 60, 30);
        logoutLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutLabel.setForeground(Color.DARK_GRAY);
        logoutLabel.setVisible(false); // Initially hidden

        rightPanel.add(logoutLabel);
        rightPanel.add(logoutBtn);

        // Logout action
        logoutBtn.addActionListener(e -> {
            logoutLabel.setVisible(true); 
            // Set a timer for 1.5 seconds to hide label and switch screen
            Timer timer = new Timer(1500, evt -> { 
                logoutLabel.setVisible(false);
                dispose();
                new GUI();
            });
            timer.setRepeats(false);
            timer.start();
        });   

        //Create the table to display customer bookings
        bookingsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookingsTable);// Make table scrollable
        scrollPane.setBounds(50, 220, 600, 230);
        rightPanel.add(scrollPane);

        add(rightPanel, BorderLayout.CENTER);

        //Load only the bookings related to the current customer
        loadCustomerBookings(); // Custom method (you probably implemented this)

        // Edit Info button: opens a dialog to edit customer information
        editInfoBtn.addActionListener(e -> {
            showEditDialog();
        });

        newBookingBtn.addActionListener(e -> {
            new NewBookingDialog(this, customer, this::loadCustomerBookings).setVisible(true);
        });


        setVisible(true);
    }

    // === Cancel booking and update building availability ===
    private boolean cancelBooking(String bookingId, String buildingId, String bookingType) {
        // Determine which table to delete from based on booking type
        String table = bookingType.equals("Premium") ? "PremiumBooking" : "StandardBooking";
        String deleteSQL = "DELETE FROM " + table + " WHERE booking_id = ?";
        String buildingType = ""; // we'll retrieve it first
        // Query to get the type of building (Apartment or Villa)
        String getBuildingTypeSQL = "SELECT building_type FROM " + table + " WHERE booking_id = ?";
    
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LegendsCompany", "root", "Jhds2005")) {
            // === Step 1: Get building type (Villa/Apartment) ===
            try (PreparedStatement stmt = conn.prepareStatement(getBuildingTypeSQL)) {
                stmt.setString(1, bookingId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    buildingType = rs.getString("building_type");
                } else {
                    return false; // booking not found
                }
            }// === Step 2: Delete the booking from the table ===
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
                deleteStmt.setString(1, bookingId);
                deleteStmt.executeUpdate();
            }// === Step 3: Update the building record to mark it as available ===
            String updateSQL = "UPDATE " + buildingType + " SET isBooked = FALSE WHERE building_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                updateStmt.setString(1, buildingId);
                updateStmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // === Dialog to edit phone/email ===
    private void showEditDialog() {
        // === Create the dialog ===
        JDialog editDialog = new JDialog(this, "Edit Contact Info", true);
        editDialog.setSize(370, 250);
        editDialog.setLayout(null);
        editDialog.setLocationRelativeTo(this);
        editDialog.getContentPane().setBackground(Color.WHITE);
  
        // ==== Phone Label & Field ====
        JLabel phoneLbl = new JLabel("Phone:");
        phoneLbl.setBounds(30, 30, 100, 25);
        JTextField phoneField = new JTextField(customer.phoneNumber);
        phoneField.setBounds(100, 30, 220, 25);
  
        // ==== Email Label & Field ====
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(30, 70, 100, 25);
        JTextField emailField = new JTextField(customer.email);
        emailField.setBounds(100, 70, 220, 25);
  
        // ==== Save Button ====
        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setBounds(50, 140, 120, 40);
        saveBtn.setFocusPainted(false);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setBackground(new Color(155, 119, 219));
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        saveBtn.setBorder(BorderFactory.createLineBorder(new Color(153, 102, 255), 2));
  
        // ==== Cancel Button ====
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(200, 140, 120, 40);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(new Color(120, 120, 120)); 
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // ==== Hover effects for Save Button ====
        Color saveOriginal = saveBtn.getBackground();
        Color saveHover = new Color(134, 95, 207);
        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveBtn.setBackground(saveHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                saveBtn.setBackground(saveOriginal);
            }
        });
        // === Hover effect for Cancel Button ===
        Color cancelOriginal = cancelBtn.getBackground();
        Color cancelHover = new Color(100, 100, 100);
        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelBtn.setBackground(cancelHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                cancelBtn.setBackground(cancelOriginal);
            }
        });

        // ==== Save Action ====
        saveBtn.addActionListener(e -> { 
            String phone = phoneField.getText();
            String email = emailField.getText();
            // Update in DB and UI if successful
            if (updateCustomerInDB(phone, email)) {
                customer.updateContactDetails(phone, email);
                phoneLabel.setText("Phone: " + phone);
                emailLabel.setText("Email: " + email);
                editDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(editDialog, "Failed to update info in database.");  
            }
    
        });

        // ==== Cancel Action ====
        cancelBtn.addActionListener(e -> {
            editDialog.dispose();
        });

        // ==== Add to Dialog ====
        editDialog.add(phoneLbl);
        editDialog.add(phoneField);
        editDialog.add(emailLbl);
        editDialog.add(emailField);
        editDialog.add(saveBtn);
        editDialog.add(cancelBtn);
        editDialog.setVisible(true);

    }

    // === Update customer contact info in DB ===
    private boolean updateCustomerInDB(String phone, String email) {
   
        String sql = "UPDATE Customer SET phone_number = ?, email = ? WHERE customer_id = ?";
        String url = "jdbc:mysql://localhost:3306/LegendsCompany";
        String user = "root";
        String password = "Jhds2005";
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Bind parameters to the update query
            stmt.setString(1, phone);
            stmt.setString(2, email);
            stmt.setString(3, customer.Id);
            stmt.executeUpdate();// Execute update
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // === Load only bookings for this customer ===
    private void loadCustomerBookings() {
        String url = "jdbc:mysql://localhost:3306/LegendsCompany";
        String user = "root";
        String password = "Jhds2005";

        // Define table structure (columns)
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Booking ID", "Building ID", "Check-In", "Check-Out", "Type", "Price (SAR)"}); 

        try (Connection conn = DriverManager.getConnection(url, user, password);
         Statement stmt = conn.createStatement()) {
            // === Load Premium Bookings ===
            ResultSet rs = stmt.executeQuery("SELECT booking_id, building_id, check_in, check_out FROM PremiumBooking WHERE customer_id = '" + customer.Id + "'");
            while (rs.next()) {
                String buildingId = rs.getString("building_id");
                Date checkIn = rs.getDate("check_in");
                Date checkOut = rs.getDate("check_out");
                // Calculate price using Villa logic
                double price = calculatePrice(buildingId, "Villa", checkIn.toLocalDate(), checkOut.toLocalDate());

                model.addRow(new Object[]{rs.getString("booking_id"),buildingId,
                    checkIn,checkOut,"Premium", price});
            }// === Load Standard Bookings ===
            rs = stmt.executeQuery("SELECT booking_id, building_id, check_in, check_out FROM StandardBooking WHERE customer_id = '" + customer.Id + "'");
            while (rs.next()) {
                String buildingId = rs.getString("building_id");
                Date checkIn = rs.getDate("check_in");
                Date checkOut = rs.getDate("check_out");
                // Calculate price using Apartment logic
                double price = calculatePrice(buildingId, "Apartment", checkIn.toLocalDate(), checkOut.toLocalDate());

                model.addRow(new Object[]{rs.getString("booking_id"), buildingId,
                    checkIn, checkOut,"Standard", price});
            }// Update table with retrieved data
            bookingsTable.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load your bookings.");
            e.printStackTrace();
        }
    }
    
    // === Calculate booking price based on building type and size ===
    private double calculatePrice(String buildingId, String type, LocalDate in, LocalDate out) {
        long days = ChronoUnit.DAYS.between(in, out);// Calculate number of booking days
        if (days <= 0) return 0;// Invalid period, return 0
        String table = type.equals("Villa") ? "Villa" : "Apartment";
        String sql = "SELECT size FROM " + table + " WHERE building_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LegendsCompany", "root", "Jhds2005");
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, buildingId);// Bind building ID
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
           
                int size = rs.getInt("size");// Get size of building
                double pricePerDay = type.equals("Villa") ? size * 15 : size * 10;
                return pricePerDay * days;// Total price = daily rate * days
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return 0;
    }

    }   
 
    static class NewBookingDialog extends JDialog {
 
        private Customer customer;
        private JTextArea featureArea;
        private JTextField locationField;
        private JComboBox<String> buildingCombo;
        private JComboBox<String> typeCombo;
        private JComboBox<String> modeCombo;
        private JTextField checkInField;
        private JTextField checkOutField;
        private JTextField priceField;  
        private JCheckBox option1;
        private JCheckBox option2;


   
        public NewBookingDialog(JFrame parent, Customer customer, Runnable onBookingConfirmed) {
            super(parent, "New Booking", true);
            this.customer = customer;
            
            // === Dialog setup ===
            setSize(500, 540);
            setLayout(null);
            setLocationRelativeTo(parent);
            getContentPane().setBackground(Color.WHITE);
            
            // === Building type selector ===
            JLabel typeLabel = new JLabel("Building Type:");
            typeLabel.setBounds(30, 30, 100, 25);
            typeCombo = new JComboBox<>(new String[]{"Apartment", "Villa"});
            typeCombo.setBounds(150, 30, 200, 25);
            
            // === Building ID selector (populated based on type) ===
            JLabel buildingLabel = new JLabel("Building ID:");
            buildingLabel.setBounds(30, 70, 100, 25);
            buildingCombo = new JComboBox<>();
            buildingCombo.setBounds(150, 70, 200, 25);
            
            // === Location (auto-filled, not editable) ===
            JLabel locationLabel = new JLabel("Location:");
            locationLabel.setBounds(30, 110, 100, 25);
            locationField = new JTextField();
            locationField.setBounds(150, 110, 300, 25);
            locationField.setEditable(false);
            
            // === Check-in and Check-out dates ===
            JLabel checkInLabel = new JLabel("Check-In:");
            checkInLabel.setBounds(30, 150, 100, 25);
            checkInField = new JTextField("2025-05-10");
            checkInField.setBounds(150, 150, 200, 25);
            JLabel checkOutLabel = new JLabel("Check-Out:");
            checkOutLabel.setBounds(30, 190, 100, 25);
            checkOutField = new JTextField("2025-05-15");
            checkOutField.setBounds(150, 190, 200, 25);
            
            // Update price when dates change
            checkInField.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    updatePriceField();
                }
            });
            checkOutField.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    updatePriceField();
                }
            });

            // === Booking type (Standard or Premium) ===
            JLabel modeLabel = new JLabel("Booking Type:");
            modeLabel.setBounds(30, 230, 100, 25);
            modeCombo = new JComboBox<>(new String[]{"Standard", "Premium"});
            modeCombo.setBounds(150, 230, 200, 25);

            // === Optional features (text changes based on type) ===
            option1 = new JCheckBox(); option1.setBounds(150, 270, 200, 25);
            option2 = new JCheckBox(); option2.setBounds(150, 300, 200, 25);
            JCheckBox insurance = new JCheckBox("Insurance"); insurance.setBounds(150, 330, 200, 25);  
            add(option1); 
            add(option2); 
            add(insurance);
       
            // === Feature description area ===
            JLabel featuresLabel = new JLabel("Features:");
            featuresLabel.setBounds(30, 370, 100, 25);
            featureArea = new JTextArea();
            featureArea.setEditable(false);
            JScrollPane featureScroll = new JScrollPane(featureArea);
            featureScroll.setBounds(150, 370, 300, 70);
            add(featuresLabel); 
            add(featureScroll);
            
            // Update options (text + price) when booking type changes
            modeCombo.addActionListener(e -> {
                String type = modeCombo.getSelectedItem().toString();
                if (type.equals("Standard")) {
                    option1.setText("WiFi");
                    option2.setText("Furniture");
                } else {
                    option1.setText("Maid");
                    option2.setText("Driver");
                }
            });
            modeCombo.setSelectedIndex(0);
            
            option1.addActionListener(e -> updatePriceField());
            option2.addActionListener(e -> updatePriceField());
            modeCombo.addActionListener(e -> {
    
                if (modeCombo.getSelectedItem().equals("Standard")) {
                    option1.setText("WiFi");
                    option2.setText("Furniture");
                } else {
                    option1.setText("Maid");
                    option2.setText("Driver");
                }
                option1.setSelected(false);
                option2.setSelected(false);
                updatePriceField(); 
            });

            // Load buildings when type changes
            typeCombo.addActionListener(e -> {
                buildingCombo.removeAllItems();
                loadAvailableBuildings(typeCombo.getSelectedItem().toString(), buildingCombo);
            });
            typeCombo.setSelectedIndex(0);
       
            // Load building details on selection
            buildingCombo.addActionListener(e -> {
                String buildingType = (String) typeCombo.getSelectedItem();
                String buildingId = (String) buildingCombo.getSelectedItem();
                if (buildingId != null) {
                    loadBuildingFeatures(buildingType, buildingId);
                }
            });
            
            // === Confirm Booking Button ===
            JButton confirmBtn = new JButton("Confirm Booking");
            confirmBtn.setBounds(150, 460, 180, 40);
            confirmBtn.setBackground(new Color(155, 119, 219));
            confirmBtn.setForeground(Color.WHITE);
            confirmBtn.setFocusPainted(false);
            add(typeLabel);
            add(typeCombo);
            add(buildingLabel);
            add(buildingCombo);
            add(locationLabel);
            add(locationField);
            add(checkInLabel);
            add(checkInField);
            add(checkOutLabel);
            add(checkOutField);
            add(modeLabel);
            add(modeCombo);
      
            add(confirmBtn);
            
            // === Price Display ===
            priceField = new JTextField();
            priceField.setBounds(150, 420, 200, 25);
            priceField.setEditable(false);
            add(priceField);
            JLabel priceLabel = new JLabel("Total Price:");
            priceLabel.setBounds(30, 420, 100, 25);
            add(priceLabel);


            buildingCombo.addActionListener(e -> {
              updatePriceField();
            });

            // === Booking confirmation logic ===
            confirmBtn.addActionListener(e -> {
                String buildingType = typeCombo.getSelectedItem().toString();
                String buildingId = (String) buildingCombo.getSelectedItem();
                String checkIn = checkInField.getText();
                String checkOut = checkOutField.getText();
                String bookingType = modeCombo.getSelectedItem().toString();

                // Validate inputs
                if (buildingId == null || checkIn.isEmpty() || checkOut.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.");
                    return;
                }

                try {
                    LocalDate in = LocalDate.parse(checkIn);
                    LocalDate out = LocalDate.parse(checkOut);
                    long days = ChronoUnit.DAYS.between(in, out);

                    if (days < 2 || days > 30) {
                        JOptionPane.showMessageDialog(this, "Booking duration must be between 2 and 30 days.");
                        return;
                    }

                    if (!isBookingPeriodAvailable(buildingId, checkIn, checkOut)) {
                        JOptionPane.showMessageDialog(this, "This building is already booked during the selected period.");
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.");
                    return;
                }
                double price = calculateBookingPrice(buildingId, buildingType, LocalDate.parse(checkIn), LocalDate.parse(checkOut));
                // Show confirmation dialog with final price
                int confirm = JOptionPane.showConfirmDialog( this,
                    "Total price: " + price + " SAR\nDo you want to confirm this booking?",
                    "Confirm Price", JOptionPane.YES_NO_OPTION);


                if (confirm != JOptionPane.YES_OPTION) {
                    return; 
                }

                // Insert booking into DB
                if (insertBooking(buildingId, buildingType, checkIn, checkOut, bookingType,
                        option1.isSelected(), option2.isSelected(), insurance.isSelected())) {
                    JOptionPane.showMessageDialog(this, "Booking confirmed!");
                    dispose();
                    onBookingConfirmed.run();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to confirm booking.");
                }
            });
        }
        // Calculates the base booking price based on building size, type, and duration
        private double calculateBookingPrice(String buildingId, String type, LocalDate in, LocalDate out) {
             long days = ChronoUnit.DAYS.between(in, out);
              if (days <= 0) return 0;

              String table = type.equals("Villa") ? "Villa" : "Apartment";
              String sql = "SELECT size FROM " + table + " WHERE building_id = ?";

              try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LegendsCompany", "root", "Jhds2005");
                      PreparedStatement stmt = conn.prepareStatement(sql)) {
      
                  stmt.setString(1, buildingId);
                  ResultSet rs = stmt.executeQuery();
                  if (rs.next()) {
                      int size = rs.getInt("size");
                      double baseRate = type.equals("Villa") ? 15 : 10;
                      return size * baseRate * days;
                  }
    
              } catch (SQLException e) {
                  e.printStackTrace();
              }
              return 0;
        }

        // Updates the priceField with calculated total price based on building, dates, and selected extras
        private void updatePriceField() {
            String buildingId = (String) buildingCombo.getSelectedItem();
            String buildingType = (String) typeCombo.getSelectedItem();
            String bookingType = (String) modeCombo.getSelectedItem();
    
            try {
       
                LocalDate in = LocalDate.parse(checkInField.getText());
                LocalDate out = LocalDate.parse(checkOutField.getText());
                long days = ChronoUnit.DAYS.between(in, out);
                if (days <= 0) {
                    priceField.setText("");
                    return;
                }// Calculate extras per day
                if (buildingId != null) {
                    double basePrice = calculateBookingPrice(buildingId, buildingType, in, out);
                    double extraPerDay = 0;
                    
                    if (bookingType.equals("Standard")) {
                        if (option1.isSelected()) extraPerDay += 20;// WiFi
                        if (option2.isSelected()) extraPerDay += 30;// Furniture
                    } else { // Premium
                        if (option1.isSelected()) extraPerDay += 50;   // Maid
                        if (option2.isSelected()) extraPerDay += 60;   // Driver
                    }
                    double total = basePrice + (extraPerDay * days);
                    priceField.setText(String.format("%.2f", total) + " SAR");
                }
            } catch (Exception ex) {
                priceField.setText("");
            }
        }

        // Loads available building IDs from the selected table (Villa or Apartment)
        private void loadAvailableBuildings(String type, JComboBox<String> combo) {
  
            String table = type.equals("Villa") ? "Villa" : "Apartment";
            String sql = "SELECT building_id FROM " + table; 

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LegendsCompany", "root", "Jhds2005");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    combo.addItem(rs.getString("building_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace(); }
        }

        // Loads building features (based on type) and fills UI fields accordingly
        private void loadBuildingFeatures(String type, String id) {
       
            String sql = type.equals("Villa")
                    ? "SELECT address, number_of_floors, garden, swimming_pool FROM Villa WHERE building_id = ?"
                    : "SELECT address, floor, elevator, security_system FROM Apartment WHERE building_id = ?";
          try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LegendsCompany", "root", "Jhds2005");
                    PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    locationField.setText(rs.getString("address"));
                    StringBuilder sb = new StringBuilder();
                    if (type.equals("Villa")) {
                        sb.append("Floors: ").append(rs.getInt("number_of_floors")).append("\n");
                        sb.append("Garden: ").append(rs.getBoolean("garden") ? "Yes" : "No").append("\n");
                        sb.append("Swimming Pool: ").append(rs.getBoolean("swimming_pool") ? "Yes" : "No");
                    } else {
                        sb.append("Floor: ").append(rs.getInt("floor")).append("\n");                   
                        sb.append("Elevator: ").append(rs.getBoolean("elevator") ? "Yes" : "No").append("\n");
                        sb.append("Security System: ").append(rs.getBoolean("security_system") ? "Yes" : "No");
                    }
                    featureArea.setText(sb.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace(); }
        }
   
        // Checks if the given booking period overlaps with any existing bookings
        private boolean isBookingPeriodAvailable(String buildingId, String checkIn, String checkOut) {
            String[] tables = {"StandardBooking", "PremiumBooking"};
            String sqlTemplate = "SELECT * FROM %s WHERE building_id = ? AND NOT (check_out <= ? OR check_in >= ?)";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LegendsCompany", "root", "Jhds2005")) {
                for (String table : tables) {
                    String sql = String.format(sqlTemplate, table);
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, buildingId);
                        stmt.setDate(2, java.sql.Date.valueOf(checkIn));
                        stmt.setDate(3, java.sql.Date.valueOf(checkOut));
          
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            System.out.println(" Conflict found in table: " + table);
                            return false;
                        }  } }
                return true; 
            } catch (SQLException e) {
                e.printStackTrace();
                return false; }
        }
   
        // Inserts a new booking into either the Standard or Premium booking table and marks the building as booked
        private boolean insertBooking(String buildingId, String buildingType, String checkIn, String checkOut,
                String bookingType, boolean opt1, boolean opt2, boolean insuranceFlag) {
            String table = bookingType.equals("Premium") ? "PremiumBooking" : "StandardBooking";
            String bookingId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            double price = calculateBookingPrice(buildingId, buildingType, LocalDate.parse(checkIn), LocalDate.parse(checkOut));
            String sql, update;
            // Build appropriate INSERT query depending on booking type
            if (bookingType.equals("Premium")) {
   
                sql = "INSERT INTO PremiumBooking (booking_id, building_id, building_type, customer_id, booking_date, check_in, check_out, status, insurance, maid, driver, price) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, 'Confirmed', ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO StandardBooking (booking_id, building_id, building_type, customer_id, booking_date, check_in, check_out, status, insurance, wifi, with_furniture, price) "
                        +"VALUES (?, ?, ?, ?, ?, ?, ?, 'Confirmed', ?, ?, ?, ?)";
            }
            update = "UPDATE " + buildingType + " SET isBooked = TRUE WHERE building_id = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LegendsCompany", "root", "Jhds2005");
                    PreparedStatement insertStmt = conn.prepareStatement(sql);
                    PreparedStatement updateStmt = conn.prepareStatement(update)) {
               
                insertStmt.setString(1, bookingId);
                insertStmt.setString(2, buildingId);
                insertStmt.setString(3, buildingType);
                insertStmt.setString(4, customer.Id);
                insertStmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                insertStmt.setDate(6, java.sql.Date.valueOf(checkIn));
                insertStmt.setDate(7, java.sql.Date.valueOf(checkOut));
                insertStmt.setBoolean(8, insuranceFlag);
                insertStmt.setBoolean(9, opt1);
                insertStmt.setBoolean(10, opt2);
                insertStmt.setDouble(11, price);
                insertStmt.executeUpdate();
                updateStmt.setString(1, buildingId);
                updateStmt.executeUpdate();
     
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;  }
        }
    }

    public class LogOn extends JFrame {

        public LogOn() {
        
            // === Window setup === 
            setTitle("Create Account");      
            setSize(400, 450);      
            setLocationRelativeTo(null);       
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(false);
 
            // === Form panel ===
            JPanel formPanel = new JPanel(null);
            formPanel.setBackground(Color.WHITE);
            
            // === Title label ===
            JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 22));
            title.setBounds(0, 20, 400, 30);
            formPanel.add(title);
            
            // === Input fields with placeholders ===
            final JTextField nameField = new JTextField("Name");
            nameField.setBounds(75, 70, 250, 30);
            addPlaceholder(nameField, "Name");

            final JTextField ageField = new JTextField("Age");
            ageField.setBounds(75, 110, 250, 30);
            addPlaceholder(ageField, "Age");

            final JTextField idField = new JTextField("ID");
            idField.setBounds(75, 150, 250, 30);
            addPlaceholder(idField, "ID");

            final JTextField phoneField = new JTextField("Phone Number");
            phoneField.setBounds(75, 190, 250, 30);
            addPlaceholder(phoneField, "Phone Number");

            final JTextField emailField = new JTextField("Email");
            emailField.setBounds(75, 230, 250, 30);
            addPlaceholder(emailField, "Email");
        
            JPasswordField passwordField = new JPasswordField("Password");
            passwordField.setBounds(75, 270, 250, 30);
            addPlaceholder(passwordField, "Password"); 
        
           // === Create account button ===
           JButton createButton = new JButton("Create Account");
           createButton.setBounds(75, 320, 250, 35);
           createButton.setBackground(new Color(155, 119, 219));
           createButton.setForeground(Color.WHITE);
           createButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
           createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
           createButton.setBorder(BorderFactory.createEmptyBorder());
     
           // Hover effect for create button 
           createButton.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseEntered(MouseEvent e) {
                   createButton.setBackground(new Color(185, 145, 255));
            }
               @Override
               public void mouseExited(MouseEvent e) {             
                   createButton.setBackground(new Color(155, 119, 219));
            }
           });

           // === Account creation logic ===
           createButton.addActionListener(e -> {
           
               String name = nameField.getText().trim();
               String age = ageField.getText().trim();           
               String id = idField.getText().trim();         
               String phone = phoneField.getText().trim();        
               String email = emailField.getText().trim();        
               String password = new String(passwordField.getPassword()).trim();
 
               if (name.isEmpty() || name.equals("Name") || 
                       age.isEmpty() || age.equals("Age") ||    
                       id.isEmpty() || id.equals("ID") ||      
                       phone.isEmpty() || phone.equals("Phone Number") ||         
                       email.isEmpty() || email.equals("Email") ||      
                       password.isEmpty() || password.equals("Password")) {           
                   JOptionPane.showMessageDialog(null, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);         
                   return;       
               }

           
               try {
                   // === Age check ===
                   int ageInt = Integer.parseInt(age);
                   if (ageInt <= 0) throw new NumberFormatException();              
                   if (ageInt < 18) {               
                       JOptionPane.showMessageDialog(null, "You must be at least 18 years old to create an account.", "Age Restriction", JOptionPane.WARNING_MESSAGE);                         
                       return;           
                   }
                   Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/LegendsCompany", "root", "Jhds2005");
                   // === Insert into database ===
                   String insertSQL = "INSERT INTO Customer (customer_id, name, age, phone_number, email, password, building_id, building_type) " 
                           +"VALUES (?, ?, ?, ?, ?, ?, NULL, NULL)";           
                   PreparedStatement pstmt = conn.prepareStatement(insertSQL);           
                   pstmt.setString(1, id);         
                   pstmt.setString(2, name);           
                   pstmt.setInt(3, ageInt);          
                   pstmt.setString(4, phone);          
                   pstmt.setString(5, email);       
                   pstmt.setString(6, password); 
              
                   int rows = pstmt.executeUpdate();
                   if (rows > 0) {
             
                       JOptionPane.showMessageDialog(null, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                       dispose();
                       new GUI();
                       resetFields(nameField, "Name", ageField, "Age", idField, "ID", phoneField, "Phone Number", emailField, "Email", passwordField, "Password");
                   }
                   pstmt.close();
                   conn.close();
               } catch (NumberFormatException ex) {
                   JOptionPane.showMessageDialog(null, "Age must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
           
               } catch (SQLException ex) {
                   JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);           
               }});

     
           formPanel.add(nameField);
           formPanel.add(ageField);
           formPanel.add(idField);
           formPanel.add(phoneField);
           formPanel.add(emailField);
           formPanel.add(passwordField);
           formPanel.add(createButton);
           
           add(formPanel);
           setVisible(true);
    }
        // === Add placeholder text logic ===
        private void addPlaceholder(JTextField field, String placeholder) {
       
            field.setForeground(Color.GRAY);
            field.setText(placeholder);
            field.addFocusListener(new FocusAdapter() {
          
                public void focusGained(FocusEvent e) {
                    if (field.getText().equals(placeholder)) {
                        field.setText("");
                        field.setForeground(Color.BLACK);
                        if (field instanceof JPasswordField) {
                            ((JPasswordField) field).setEchoChar('*');
                         }}}

                public void focusLost(FocusEvent e) {
              
                    if (field.getText().isEmpty()) {
                        field.setForeground(Color.GRAY);
                        field.setText(placeholder);
                        if (field instanceof JPasswordField) {
                            ((JPasswordField) field).setEchoChar((char) 0);
                        }}}});
        }
        // === Reset all fields back to default placeholders ===
        private void resetFields(JTextField f1, String p1, JTextField f2, String p2,      
                JTextField f3, String p3, JTextField f4, String p4,  
                JTextField f5, String p5, JTextField f6, String p6) {
 
            JTextField[] fields = {f1, f2, f3, f4, f5, f6};
            String[] placeholders = {p1, p2, p3, p4, p5, p6};
      
            for (int i = 0; i < fields.length; i++) {
                fields[i].setText(placeholders[i]);
                fields[i].setForeground(Color.GRAY);
                if (fields[i] instanceof JPasswordField) {    
                    ((JPasswordField) fields[i]).setEchoChar((char) 0); }} }   
    
    }
    
    public static void main(String[] args) {
            new GUI();     
    }}