package dk.sdu.mmmi.dm.healthos.domain;

/**
 *
 * @author Oliver Nordestgaard | olnor18
 */
public class Patient {
    private final int id;
    private final String name;
    private final String phone;
    private final String cpr;

    public Patient(int id, String name, String phone, String cpr) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cpr = cpr;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getCpr() {
        return this.cpr;
    }

}
