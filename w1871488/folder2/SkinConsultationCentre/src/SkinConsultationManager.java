import java.util.ArrayList;

interface SkinConsultationManager{
    //List of Doctors
    ArrayList<Doctor> doctorList = new ArrayList<>();

    void addNewDoctor();
    void deleteDoctor();
    void printDoctorList();
    void saveInFile();

}