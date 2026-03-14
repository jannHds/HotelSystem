# Hotel Booking System

A desktop-based booking management system developed using **Java**, **Java Swing**, and **MySQL**. The system allows customers and staff to manage bookings, update personal information, and interact with hotel services through a graphical user interface.

## Overview
This project was developed as part of the course **Object-Oriented Programming II (CSC236)**.  
The goal of the project is to demonstrate the practical implementation of **Object-Oriented Programming (OOP)** concepts such as inheritance, polymorphism, encapsulation, and modular design within a real-world booking management system.

The system allows users to create and manage bookings, update personal information, and calculate booking prices dynamically based on booking type and selected services.

## Features
- Customer and staff login system
- New customer registration
- Create Standard or Premium bookings
- Cancel existing bookings
- Update customer contact information
- Dynamic booking price calculation
- Staff dashboard for viewing bookings
- Input validation and error handling
- MySQL database integration

## Technologies Used
- Java
- Java Swing
- JDBC
- MySQL
- Apache NetBeans
- Git & GitHub

## OOP Structure
The project follows an object-oriented design:

**Booking**
- StandardBooking
- PremiumBooking

**Building**
- Apartment
- Villa

**Person**
- Customer
- Staff

## Development Team
This project was developed collaboratively by:

- Jana Hassan Algarni
- Leen Abdulrahman Hadari
- Asma Abbas Albeshri
- Lujan Abdullah Alqahtani
- Fatimah Hassan Alnasser
- Lamar Dhafir Alamri

## Project Structure
```text
HotelSystem/
├── build/
├── nbproject/
├── src/
│   ├── hotelsystem/
│   │   ├── Booking.java
│   │   ├── Building.java
│   │   ├── GUI.java
│   │   └── Person.java
│   └── main/
│       ├── Login-cuate.png
│       ├── customer.png
│       ├── staff.png
│       ├── logo.png
│       └── other image assets
├── build.xml
└── README.md
