package ru.coffeeturbo.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Email
    String email;

    @JsonIgnore
    @NonNull
    String password;
}
