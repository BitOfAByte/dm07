package dk.sdu.mmmi.dm.healthos.persistance;

import dk.sdu.mmmi.dm.healthos.domain.Employee;
import dk.sdu.mmmi.dm.healthos.domain.Patient;
import dk.sdu.mmmi.dm.healthos.domain.Admission;
import dk.sdu.mmmi.dm.healthos.domain.Bed;
import dk.sdu.mmmi.dm.healthos.domain.IPersistanceHandler;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oliver Nordestgaard | olnor18
 */
public class PersistanceHandler implements IPersistanceHandler{
    private static PersistanceHandler instance;
    private Connection connection = null;
    private PreparedStatement statement;
    Dotenv dotenv = Dotenv.load();
    
    private PersistanceHandler(){
        initializePostgresqlDatabase();
    }

    public static PersistanceHandler getInstance(){
        if (instance == null) {
            instance = new PersistanceHandler();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + dotenv.get("db_host") + ":" + dotenv.get("db_port") + "/" + dotenv.get("db_name"), dotenv.get("db_user"), dotenv.get("db_password"));
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees");
            ResultSet sqlReturnValues = stmt.executeQuery();
            int rowcount = 0;
            List<Employee> returnValue = new ArrayList<>();
            while (sqlReturnValues.next()){
                returnValue.add(new Employee(sqlReturnValues.getInt(1), sqlReturnValues.getString(2), sqlReturnValues.getInt(3), sqlReturnValues.getInt(4), sqlReturnValues.getInt(5), sqlReturnValues.getInt(6)));
            }
            return returnValue;
        } catch (SQLException ex) {
            System.out.println("Failed to get data from the database " + ex.getCause() + ex.getMessage());
            return null;
        }
    }

    @Override
    public Employee getEmployee(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()){
               return null;
            }
            return new Employee(sqlReturnValues.getInt(1), sqlReturnValues.getString(2), sqlReturnValues.getInt(3), sqlReturnValues.getInt(4), sqlReturnValues.getInt(5), sqlReturnValues.getInt(6));
        } catch (SQLException ex) {
            System.out.println("Failed to get data from the database " + ex.getCause() + ex.getMessage());
            return null;
        }    
    }

    /*
    Implement all of the following. Beware that the model classes are as of yet not properly implemented
    */

    @Override
    public boolean createEmployee(Employee employee) {
        try {
            statement = connection.prepareStatement("INSERT INTO employees (id, name, phone, position_id, department_id, room_id) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setInt(3, employee.getPhone());
            statement.setInt(4, employee.getPosition_id());
            statement.setInt(5, employee.getDepartment_id());
            statement.setInt(6, employee.getRoom_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert data into the database " + e.getCause() + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Patient> getPatients() {
        try {
            statement = connection.prepareStatement("SELECT * FROM patients");
            ResultSet resultSet = statement.executeQuery();
            List<Patient> returnValue = new ArrayList<>();
            while (resultSet.next()){
                returnValue.add(new Patient(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
            return returnValue;
        } catch (SQLException e) {
            System.out.println("Failed to get data from the database " + e.getCause() + e.getMessage());
            return null;
        }
    }


    @Override
    public Patient getPatient(int id) {
        try {
            statement = connection.prepareStatement("SELECT * FROM patients WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                System.out.println("No patient with that ID found");
            }
            return new Patient(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
        } catch (SQLException e) {
            System.out.println("Failed to get data from the database " + e.getCause() + " " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean createPatient(Patient patient) {
        //make HealthOS support this action in the presentation layer too.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Bed> getBeds() {
        try {
            statement = connection.prepareStatement("SELECT * FROM beds");
            ResultSet resultSet = statement.executeQuery();
            List<Bed> returnValue = new ArrayList<>();
            while (resultSet.next()){
                returnValue.add(new Bed(resultSet.getInt(1), resultSet.getString(2)));
            }
            return returnValue;
        } catch (SQLException e) {
            System.out.println("Failed to get data from the database " + e.getCause() + e.getMessage());
            return null;
        }
    }

    @Override
    public Bed getBed(int id) {
        try {
            statement = connection.prepareStatement("SELECT * FROM beds WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                System.out.println("No bed with that ID found");
            }
            return new Bed(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            System.out.println("Failed to get data from the database " + " " + e.getCause() + " " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean createBed(Bed bed) {
        try {
            statement = connection.prepareStatement("INSERT INTO beds (id, bed_number) VALUES (?, ?)");
            statement.setInt(1, bed.getBed_Id());
            statement.setString(2, bed.getBed_Number());
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to insert data into the database " + e.getCause() + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Admission> getAdmissions() {
        try {
            statement = connection.prepareStatement("SELECT * FROM admissions");
            ResultSet resultSet = statement.executeQuery();
            List<Admission> returnValue = new ArrayList<>();
            while (resultSet.next()){
                returnValue.add(new Admission(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5)));
            }
            return returnValue;
        } catch (SQLException e) {
            System.out.println("Failed to get data from the database " + e.getCause() + e.getMessage());
            return null;
        }
    }

    @Override
    public Admission getAdmission(int id) {
        try {
            statement = connection.prepareStatement("SELECT * FROM admissions WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
               return null;
            }
            return new Admission(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5));
        } catch (SQLException e) {
            System.out.println("Failed to get data from the database " + e.getCause() + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean createAdmission(Admission admission) {
        try {
            statement = connection.prepareStatement("INSERT INTO admissions (id, patient_id, bed_id, room_id, assigned_employee_id) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, admission.getAdmission_Id());
            statement.setInt(2, admission.getPatient_Id());
            statement.setInt(3, admission.getBed_Id());
            statement.setInt(4, admission.getEmployee_Id());
            statement.setInt(5, admission.getRoom_Id());
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to insert data into the database " + e.getCause() + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAdmission(int id) {
        try {
            statement = connection.prepareStatement("DELETE FROM admissions WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to delete data from the database " + e.getCause() + e.getMessage());
            return false;
        }
    }

}
