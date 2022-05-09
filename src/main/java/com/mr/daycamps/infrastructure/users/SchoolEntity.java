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
@Table(name = "DC_SCHOOL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "set")
public class SchoolEntity extends UserEntity {

    @NotBlank
    @Size(max = 30)
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotBlank
    @Size(max = 30)
    @Column(name = "ADDRESS", nullable = false)
    private String address;

}
