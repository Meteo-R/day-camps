package com.mr.daycamps.infrastructure.users;

import com.mr.daycamps.infrastructure.authentication.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DC_PARENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "set")
public class ParentEntity extends UserEntity {

    @NotBlank
    @Size(max = 30)
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @NotBlank
    @Size(max = 30)
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

}
