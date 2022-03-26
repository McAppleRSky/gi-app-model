package ru.rob.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "debt_response_court_solution")
public class AsuDebtResponseCourtSolution {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "varchar(36)")
    public String id;

    @ManyToOne
    @JoinColumn(name = "response_detail_id", insertable = false, updatable = false)
    public transient AsuDebtResponseDetail detail;

    @Column(name = "file_path", nullable = false)
    public String filePath;

    @Column(name = "file_name", nullable = false)
    public String fileName;

    @Column(name = "description")
    public String description;

}
