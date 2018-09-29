package at.fhj.swd;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private int salary;

    protected Employee(){}

    public Employee(int id, String firstName, String lastName, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public void raiseSalary(int salaryRaise) {
        this.salary += salaryRaise;
    }

    public int getSalary() {
        return salary;
    }
}
