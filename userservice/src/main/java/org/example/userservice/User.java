package main.java.org.example.userservice;

@Entity
@Data
public class User {
    @id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String emale;
}