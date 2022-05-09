package com.mr.daycamps.infrastructure.authentication;

import com.mr.daycamps.domain.authentication.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "DC_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "set")
public abstract class UserEntity {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "DC_USER_SEQUENCE"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "\\+?[0-9]+")
    @Column(name = "PHONE", nullable = false)
    private String phone;

    @NotBlank
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", length = 20, nullable = false)
    private Role role;

}
