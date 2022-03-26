package ru.rob.entity;

import org.hibernate.annotations.GenericGenerator;
import ru.rob.integration.DebtInfoType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "debt_response_detail")
public class AsuDebtResponseDetail {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "varchar(36)")
    public String id;

    @ManyToOne
    @JoinColumn(name = "response_id", insertable = false, updatable = false)
    public transient AsuDebtResponse response;

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
    public List<AsuDebtResponseCourtSolution> courtSolutions;

    public List<AsuDebtResponseCourtSolution> getCourtSolution(){
        return courtSolutions == null ? courtSolutions = new ArrayList<>() : courtSolutions;
    }

    public static AsuDebtResponseDetail from(final DebtInfoType item) {
        final AsuDebtResponseDetail detail = new AsuDebtResponseDetail();
        detail.firstName = item.getPerson().getFirstName();
        detail.lastName = item.getPerson().getLastName();
        detail.middleName= item.getPerson().getMiddleName();
        detail.snils = item.getPerson().getSnils();

        return detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsuDebtResponseDetail detail = (AsuDebtResponseDetail) o;
        return firstName.equals(detail.firstName)
                && lastName.equals(detail.lastName)
                && Objects.equals(middleName, detail.middleName)
                && Objects.equals(snils, detail.snils);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, middleName, snils);
    }
}

