package uz.pdp.planboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDate deadline;
    @ManyToMany
    private List<Users> users;
    @ManyToOne
    private Columns column;
    @OneToMany
    private List<Comment> comments;
    @ManyToOne
    private Attachment attachment;

}
