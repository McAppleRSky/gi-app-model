package ru.rob.handbooks.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "debt_response")
public class DebtResponse {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "varchar(36)")
    public String id;

    @Column(name = "has_debt", nullable = false)
    public Boolean hasDebt;

    @Column(name = "executor_fio", nullable = false)
    public String executorFio;

    @Column(name = "executor_guid", nullable = false, columnDefinition = "varchar(36)")
    public String executorGuid;

    @Column
    public String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id")
    public List<DebtResponseDetail> details;


    public List<DebtResponseDetail> getDetails(){
        return details == null ? details = new ArrayList<>() : details;
    }

}
