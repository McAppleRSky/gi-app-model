package ru.rob.entity;

import org.hibernate.annotations.GenericGenerator;
import ru.rob.integration.DSRType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "debt_response")
public class AsuDebtResponse {

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

    @Column(name = "executor_guid", nullable = false)
    public String executorGuid;

    @Column
    public String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id")
    public List<AsuDebtResponseDetail> details;

    public List<AsuDebtResponseDetail> getDetails(){
        return details == null ? details = new ArrayList<>() : details;
    }

    public static AsuDebtResponse from(final DSRType.ResponseData item) {
        final AsuDebtResponse debt = new AsuDebtResponse();
        debt.hasDebt = item.isHasDebt();
        debt.executorFio = item.getExecutorInfo().getFio();
        debt.description = item.getDescription();
        if(item.getDebtInfo() != null && !item.getDebtInfo().isEmpty())
            debt.details = item.getDebtInfo().stream()
                    .map(AsuDebtResponseDetail::from)
                    .collect(Collectors.toList());

        return debt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsuDebtResponse)) return false;
        AsuDebtResponse that = (AsuDebtResponse) o;
        return hasDebt.equals(that.hasDebt)
                && executorFio.equals(that.executorFio)
                && executorGuid.equals(that.executorGuid)
                && Objects.equals(description, that.description)
                && details.equals(that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasDebt, executorFio, description, details);
    }
}

