# HMS Application 🏥  
### **Lab Group: **  
### **Group 3**


This is a NTU project for module SC2002 (Object Oriented Programming).

HMS The **Hospital Management System (HMS)** is a CLI-based application designed to automate core hospital operations such as patient management, staff management, appointment scheduling, and billing. By offering role-based functionalities for patients, doctors, pharmacists, and administrators, HMS ensures efficient workflows and a streamlined user experience.



---

## **Table of Contents**  
- HMS Overview 🏥  
- Setup Instructions  
  - User Login Setup  
  - Feature Configuration  
- Documentation  
  - Code Structure  
  - CLI Commands  
- Tech Stack  
- Contributors  

---

## **Setup Instructions**

### User Login Setup  

1. **Run the Application**:  
   ```bash  
   java -jar HMSApp.jar  
   ```  

2. **Initialize Default Users**:  
   - Add default roles for **Patient**, **Doctor**, **Pharmacist**, and **Administrator**.  

3. **Login and Explore Features**:  
   - Each user role is prompted with a menu of features upon login.  

---

## **Documentation**

### Code Structure  
The codebase is modularly organized to ensure maintainability:  
- **Model**: Represents entities like patients, staff, inventory, and appointments.  
- **Boundary**: Handles user interactions through CLI menus.  
- **Controller**: Implements the core logic and manages the flow between components.  
- **Repository**: Stores in-memory data for application state.  

### CLI Commands  
The CLI interface provides simple, intuitive commands for user interactions, ensuring smooth navigation across all user roles.  

---

## **Tech Stack**  

### Languages & Frameworks:  
- Java  
- OOP Principles  

### Utilities:  
- Command Line Interface (CLI) for interaction  
- Custom Logger for transaction and event history tracking  

---

## **Contributors**  

The MediCare 🏥 team collaborated across all phases of development, from ideation to deployment:  

| **Name**          | **Github Username** | **Role**        |  
|--------------------|---------------------|-----------------|  
| Alice Tan          | @alice-tan93       | Project Lead    |  
| Bryan Lim          | @bryanlim1998      | Backend Dev     |  
| Chloe Wong         | @chloewonghms      | Backend Dev     |  
| Daniel Ng          | @danielng12        | CLI Specialist  |  
| Esther Lee         | @estherleeHMS      | Testing & QA    |  

---  

This streamlined document highlights the essential features and contributions of the **Hospital Management System (HMS)**.
