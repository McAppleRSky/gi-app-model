package ru.rob.handbooks.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "debt_response_detail")
public class DebtResponseDetail {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "varchar(36)")
    public String id;

    @ManyToOne
    @JoinColumn(name = "response_id", insertable = false, updatable = false)
    public transient DebtResponse response;

    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @Column(name = "middle_name")
    public String middleName;

    @Column
    public String snils;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_detail_id")
    public List<DebtResponseCourtSolution> courtSolutions;

}
