import java.io.*;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterSkinConsultationManager extends Consultation implements SkinConsultationManager{

    public static void main(String[] args)  {
        WestminsterSkinConsultationManager consultationManager = new WestminsterSkinConsultationManager();
        //Reading from the date file
        try{
            consultationManager.readData();
        }catch (Exception ignored){

        }
        boolean user_menu = true;
        //Console Menu
        while (user_menu) {
            try {
                System.out.println();
                System.out.println("|------------------------------------------------------------------|");
                System.out.println("|______________Welcome to the Consultation System !________________|");
                System.out.println("|------------------------------------------------------------------|");
                System.out.println("|--------------Please enter the relevant number--------------------|");
                System.out.println("|------------------------------------------------------------------|");
                System.out.println("|------1: Add a New Doctor_________________________________________|");
                System.out.println("|------2: Delete a Doctor -----------------------------------------|");
                System.out.println("|------3: Print the list of doctors--------------------------------|");
                System.out.println("|------4: Save the details in a File-------------------------------|");
                System.out.println("|------5: Open the Graphical User Interface------------------------|");
                System.out.println("|------6: Exit the System------------------------------------------|");
                System.out.println("|------------------------------------------------------------------|");
                System.out.println();
                Scanner scanner1 = new Scanner(System.in);
                System.out.print("Enter the option number: ");
                int selectedOption = scanner1.nextInt();
                switch (selectedOption) {
                    case 1 -> consultationManager.addNewDoctor();
                    case 2 -> consultationManager.deleteDoctor();
                    case 3 -> consultationManager.printDoctorList();
                    case 4 -> consultationManager.saveInFile();
                    case 5 -> new GUI();
                    case 6 -> user_menu = false;
                    default -> System.out.println("Invalid Choice, Please try again");
                }
            } catch (Exception ignored) {
            }
        }
    }
    //add a new doctor to the system
    @Override
    public void addNewDoctor() {
        boolean user = true;
        while (user) {
            if (doctorList.size() >= 10) {
                System.out.println("The doctor list is full.");
                break;
            } else {
                Doctor doctor = new Doctor();
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Please enter the Doctor's Name: ");
                String name = scanner2.nextLine();
                doctor.setName(name);
                System.out.println("Please enter Doctor's Sur Name: ");
                String surname = scanner2.nextLine();
                doctor.setSurname(surname);
                System.out.println("Please enter Doctor's Birth Date (dd/MM/yyyy): ");
                String DOB = scanner2.nextLine();
                doctor.setDateOfBirth(DOB);
                System.out.println("Please enter Doctor's Mobile Number: ");
                int mobileNumber = scanner2.nextInt();
                doctor.setMobileNumber(mobileNumber);
                System.out.println("Please enter Doctor's Specialisation: ");
                scanner2.nextLine();
                String specialist = scanner2.nextLine();
                doctor.setSpecialisation(specialist);
                System.out.println("Please enter Doctor's Medical Licence Number: ");
                int medicalLicence = scanner2.nextInt();
                doctor.setMedicalLicenceNumber(medicalLicence);
                System.out.println("Please confirm below details to add the user: ");
                System.out.println("First Name: " + name);
                System.out.println("Surname: " + surname);
                System.out.println("Date of Birth: " + DOB);
                System.out.println("Mobile Number: " + mobileNumber);
                System.out.println("Specialisation: " + specialist);
                System.out.println("Medical License Number: " + medicalLicence);
                System.out.println();
                System.out.println("Please enter Y to confirm or N to re-enter the details");
                scanner2.nextLine();
                String confirmation = scanner2.nextLine();
                if (confirmation.equalsIgnoreCase("Y")) {
                    doctorList.add(doctor);
                    System.out.println("Doctor has been added to the System");
                } else if (confirmation.equalsIgnoreCase("N")) {
                    continue;
                }
                Scanner scanner3 = new Scanner(System.in);
                System.out.print("Do you want to add another Doctor ? ( Y/ N): ");
                String userInput = scanner3.nextLine();
                if (userInput.equalsIgnoreCase("N")) {
                    user = false;
                } else {
                    System.out.println("Invalid Choice");
                    break;
                }
            }
        }
    }
    //Delete a doctor from the system
    @Override
    public void deleteDoctor() {
        System.out.println("Please enter the Doctor's Medical License Number: ");
        Scanner scanner4 = new Scanner(System.in);
        int medicalLicence = scanner4.nextInt();
        for (int x = 0; x <= doctorList.size(); x++) {
            if (doctorList.get(x).getMedicalLicenceNumber() == medicalLicence) {
                String nameRemove = doctorList.get(x).getName();
                System.out.println("Do you want to delete doctor " + nameRemove + " from the List ? (Y/N)");
                scanner4.nextLine();
                String confirm = scanner4.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    System.out.println("Doctor is removed from the list: ");
                    System.out.println("Doctor's Name: " + doctorList.get(x).getName());
                    System.out.println("Doctor's Sur Name: " + doctorList.get(x).getSurname());
                    System.out.println("Doctor's Date of Birth: " + doctorList.get(x).getDateOfBirth());
                    System.out.println("Doctor's Mobile Number: " + doctorList.get(x).getMobileNumber());
                    System.out.println("Doctor's Specialisation: " + doctorList.get(x).getSpecialisation());
                    System.out.println("Doctor's Medical License Number: " + doctorList.get(x).getMedicalLicenceNumber());
                    doctorList.remove(x);
                    System.out.println();
                    System.out.println("Total Number of Doctors in the System: " + doctorList.size());
                    break;
                } else if (confirm.equalsIgnoreCase("n")) {
                    break;
                } else {
                    System.out.println("Please enter a valid input");
                }
            } else {
                System.out.println("Unable to find the doctor with the mentioned Medical License Number.");
                break;
            }
        }
    }
    //Print the list of doctors
    @Override
    public void printDoctorList() {
        System.out.println();
        if (doctorList.isEmpty()) {
            System.out.println("The Doctor List is Empty");
            System.out.println();
        } else {
            Collections.sort(doctorList);
            System.out.println("List of Doctors: ");
            System.out.println();
            for (int y = 0; y <= doctorList.size() - 1; y++) {
                System.out.println("Doctor No: " + (y + 1));
                System.out.println("Doctor's Name: " + doctorList.get(y).getName());
                System.out.println("Doctor's Sur Name: " + doctorList.get(y).getSurname());
                System.out.println("Doctor's Date of Birth: " + doctorList.get(y).getDateOfBirth());
                System.out.println("Doctor's Mobile Number: " + doctorList.get(y ).getMobileNumber());
                System.out.println("Doctor's Specialisation: " + doctorList.get(y).getSpecialisation());
                System.out.println("Doctor's Medical License Number: " + doctorList.get(y).getMedicalLicenceNumber());
                System.out.println();
            }
        }
    }
    //Save the system data to a File
    @Override
    public void saveInFile() {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DataFile.txt"))) {
            for (Doctor doctor : doctorList) {
                out.writeObject(doctor);
                System.out.println("Doctor data saved to the file");
            }
            for (Consultation consultation: consultations){
                out.writeObject(consultation);
                System.out.println("Consultation data saved to the file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Get data from the file to the system
    void readData() throws IOException{

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("DataFile.txt"))) {
            doctorList.add((Doctor) in.readObject());
            consultations.add((Consultation) in.readObject());
        } catch (EOFException ignored){

        } catch (IOException | ClassNotFoundException e ) {
            e.printStackTrace();
        }

    }

}
