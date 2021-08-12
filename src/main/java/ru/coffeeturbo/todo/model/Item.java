package ru.coffeeturbo.todo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String description;

    @Temporal(TemporalType.TIMESTAMP)
    Date created;

    boolean done;

    //    @JoinColumn(name = "user_id")
//    @ManyToOne
//    User user;
//
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<Category> categories;
}
