package com.demo.kudaclone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 125)
    @Column(name = "name", length = 50)
    private String name;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Size(max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

//    @Size(max = 256)
//    @Column(name = "image_url", length = 256)
//    private String imageUrl;

    @Column(name = "created_date")
    @JsonIgnore
    private Instant createDate = null;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;
    
    @Override
    public String toString() {
        return String.format(
                "name: %s, \t email: %s, \n password: %s",
                this.name, this.email, this.password);
    }
}
