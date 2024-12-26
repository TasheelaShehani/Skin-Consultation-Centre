import java.io.Serial;
import java.io.Serializable;

public class Doctor extends Person implements Comparable<Doctor>, Serializable {

    private int medicalLicenceNumber;
    private String specialisation;

    @Serial
    private static final long serialVersionUID=1L;

    public int getMedicalLicenceNumber(

    ) {
        return medicalLicenceNumber;
    }

    public void setMedicalLicenceNumber(int medicalLicenceNumber) {
        this.medicalLicenceNumber = medicalLicenceNumber;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {

        this.specialisation = specialisation;
    }

    //Method to create a comparable for doctor list sorting
    @Override
    public int compareTo(Doctor d) {
        int result = this.getSurname().compareTo(d.getSurname());
        return Integer.compare(result, 0);
    }
}