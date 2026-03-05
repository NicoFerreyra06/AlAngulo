package com.nicoferreyra.alangulo.model;

import com.nicoferreyra.alangulo.enums.eRol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @NotBlank
    @Column(unique = true)
    private String email;
    private String photo;

    @Enumerated(EnumType.STRING)
    private eRol rol;
}
