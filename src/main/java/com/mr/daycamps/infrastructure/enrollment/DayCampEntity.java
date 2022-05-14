package com.mr.daycamps.infrastructure.enrollment;

import com.mr.daycamps.infrastructure.users.SchoolEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DC_DAY_CAMP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayCampEntity {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "DC_DAY_CAMP_SEQUENCE"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    private SchoolEntity school;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "DC_ENROLLMENT",
            joinColumns = @JoinColumn(name = "DAY_CAMP_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID")
    )
    Set<ChildEntity> children = new HashSet<>();

}
