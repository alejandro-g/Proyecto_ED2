
package proyectoed2;

import java.io.File;
import java.io.Serializable;

public class Registro implements Serializable{
 
private String name;
private String birthdate;
private int id;
private double salary;

public Registro(String name, String birthdate, int id, double salary){
    this.name = name;
    this.birthdate = birthdate;
    this.id = id;
    this.salary = salary; 
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Registro{" + "name=" + name + ", birthdate=" + birthdate + ", id=" + id + ", salary=" + salary + '}';
    }

}
