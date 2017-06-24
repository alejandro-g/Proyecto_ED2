package proyectoed2;

import java.io.File;
import java.io.Serializable;

public class Registro implements Serializable {

    private String name;
    private String birthdate;
    private int id;
    private float salary;
    private char asterisco;
    private int referencia;

    public Registro(String name, String birthdate, int id, float salary) {
        this.name = name;
        this.birthdate = birthdate;
        this.id = id;
        this.salary = salary;
        this.asterisco = '~';
        this.referencia = 0;
    }

    public Registro() {

    }

    public char getAsterisco() {
        return asterisco;
    }

    public void setAsterisco(char asterisco) {
        this.asterisco = asterisco;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
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

    public void setSalary(float salary) {
        this.salary = salary;
    }
    
    public int sizeOfRecord(){
        int size = 68;//tamaño que tendra cada registro
        return size;
    }

    @Override
    public String toString() {
        return "Registro{" + "name=" + name + ", birthdate=" + birthdate + ", id=" + id + ", salary=" + salary + '}';
    }

}
