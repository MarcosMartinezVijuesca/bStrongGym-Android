package com.svalero.bstronggym.domain;

public class Monitor {
    private long id;
    private String name;
    private String dni;
    private String hireDate;
    private float salary;
    private boolean available;
    private String specialty;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getHireDate() { return hireDate; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }
    public float getSalary() { return salary; }
    public void setSalary(float salary) { this.salary = salary; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}
