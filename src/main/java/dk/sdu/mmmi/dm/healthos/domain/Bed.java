package dk.sdu.mmmi.dm.healthos.domain;

/**
 *
 * @author Oliver Nordestgaard | olnor18
 */
public class Bed {
    private final int Bed_Id;
    private final String Bed_Number;

    public Bed(int Bed_Id, String Bed_Number){
        this.Bed_Id = Bed_Id;
        this.Bed_Number = Bed_Number;
    }

    public int getBed_Id(){
        return this.Bed_Id;
    }

    public String getBed_Number(){
        return this.Bed_Number;
    }
}
