package ishtuganov.springcourse.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 1, max = 70, message = "name size should be between 1 and 70 chars")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 70, message = "name size should be between 2 and 70 chars")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "pattern: Tom Collins")
    @Column(name = "author_name")
    private String authorName;
    @Min(value = 0, message = "year should be more than 0")
    @NotNull(message = "year shouldn't be empty")
    @Column(name = "year")
    private int year;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "taken_at")
    private Date takenAt;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    public Book() {}
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
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Date getTakenAt() {
        return takenAt;
    }
    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isExpired() {
        if (takenAt == null) return false;

        long diffInDays = (new Date().getTime() - takenAt.getTime()) / (1000 * 60 * 60 * 24);
        return diffInDays >= 5;
    }
}
