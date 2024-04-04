package dk.sdu.mmmi.dm.healthos.domain;

/**
 *
 * @author Oliver Nordestgaard | olnor18
 */
public class Admission {
    private final int Admission_Id;
    private final int Patient_Id;
    private final int Room_Id;
    private final int Bed_Id;
    private final int Employee_Id;

    public Admission(int Admission_Id, int Patient_Id, int Room_Id, int Bed_Id, int Employee_Id){
        this.Admission_Id = Admission_Id;
        this.Patient_Id = Patient_Id;
        this.Room_Id = Room_Id;
        this.Bed_Id = Bed_Id;
        this.Employee_Id = Employee_Id;
    }

    public int getAdmission_Id(){
        return this.Admission_Id;
    }

    public int getPatient_Id(){
        return this.Patient_Id;
    }

    public int getRoom_Id(){
        return this.Room_Id;
    }

    public int getBed_Id(){
        return this.Bed_Id;
    }

    public int getEmployee_Id(){
        return this.Employee_Id;
    }


    @Override
    public String toString() {
        return "Admission{" + "Admission_Id=" + Admission_Id + ", Patient_Id=" + Patient_Id + ", Room_Id=" + Room_Id + ", Bed_Id=" + Bed_Id + ", Employee_Id=" + Employee_Id + '}';
    }

}

