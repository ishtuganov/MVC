package ishtuganov.springcourse.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 70, message = "name size should be between 2 and 70 chars")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "pattern: Tom Collins Smitt")
    @Column(name = "name")
    private String name;
    @Min(value = 1900, message = "birthdate should be more than 1900")
    @NotNull(message = "birth date shouldn't be empty")
    @Column(name = "birthdate")
    private int birthDate;
    @OneToMany(mappedBy = "person")
    private List<Book> books;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
