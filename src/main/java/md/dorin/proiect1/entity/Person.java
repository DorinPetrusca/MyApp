package md.dorin.proiect1.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Acest cimp nu trebuie sa fie gol")
    @Size(min = 2, max = 100, message = "Numele trebuie sa fie intre 2 si 100 caractere")
    @Column(name = "fullname")
    private String fullName;

    @Min(value = 1900, message = "Anul de nastere trebuie sa fie mai mare decit 1900")
    @Max(value = 2024, message = "Anul de nastere trebuie sa fie mai mic decit 2024")
    @Column(name = "yearofbirth")
    private int yearOfBirth;

    @Email(message = "Adresa electronica trebuie sa fie valida")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Car> cars;

    public Person() {
    }

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
