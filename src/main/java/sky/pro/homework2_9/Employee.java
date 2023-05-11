package sky.pro.homework2_9;

import java.util.Objects;

public class Employee {
    private String fullName;
    private int salary;
    private int dept;
    private int id;
    private static int idCounter = 0;

    public Employee(String fullName, int salary, int dept) {
        this.fullName = fullName;
        this.salary = salary;
        this.dept = dept;
        this.id = idCounter++;
    }

    public String getFullName() {
        return fullName;
    }

    public int getDept() {
        return dept;
    }

    public int getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public void setFullName(String firstName) {
        this.fullName = firstName;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return fullName.equals(employee.fullName);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + fullName.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return                  fullName +
                " зарплата: " + salary +
                ", отдел: " + dept +
                ", id: " + id ;
    }
}
