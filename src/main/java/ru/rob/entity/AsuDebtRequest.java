package ru.rob.entity;

import org.hibernate.annotations.GenericGenerator;
import ru.rob.integration.DSRType;
import ru.rob.integration.ResponseStatusType;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "debt_request")
public class AsuDebtRequest {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "varchar(36)")
    public String id;

    @Column(name = "gis_id", nullable = false)
    public String gisId;

    @Column(name = "request_number", nullable = false)
    public String number;

    @Column(name = "start_date", nullable = false)
    public Date startDate;

    @Column(name = "end_date", nullable = false)
    public Date endDate;

    @Column(name = "house_addr", nullable = false)
    public String houseAddr;

    @Column(name = "house_guid", nullable = false)
    public String houseGuid;

    @Column(name = "flat_number")
    public String flatNumber;

    @Column(name = "response_date", nullable = false)
    public Date responseDate;

    @Column(name = "requester_org_name", nullable = false)
    public String requesterOrgName;

    @Column(name = "requester_org_guid", nullable = false)
    public String requesterOrgGuid;

    @Column(name = "requester_org_tel")
    public String requesterOrgTel;

    @Column(name = "requester_fio", nullable = false)
    public String requesterFio;

    @Column(name = "response_status", nullable = false)
    @Enumerated(EnumType.STRING)
    public ResponseStatusType responseStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id")
    public AsuDebtResponse response;

    public static AsuDebtRequest from(final DSRType item){
        final AsuDebtRequest debt = new AsuDebtRequest();
        debt.gisId = item.getSubrequestGUID();
        debt.number = item.getRequestInfo().getRequestNumber();
        debt.startDate = Date.from(item.getRequestInfo().getPeriod().getStartDate().toGregorianCalendar().toInstant());
        debt.endDate = Date.from(item.getRequestInfo().getPeriod().getEndDate().toGregorianCalendar().toInstant());
        debt.responseDate = Date.from(item.getRequestInfo().getResponseDate().toGregorianCalendar().toInstant());
        debt.houseAddr = item.getRequestInfo().getHousingFundObject().getAddress();
        debt.houseGuid = item.getRequestInfo().getHousingFundObject().getFiasHouseGUID();
        debt.flatNumber = item.getRequestInfo().getHousingFundObject().getAddressDetails();
        debt.requesterOrgName = item.getRequestInfo().getOrganization().getName();
        debt.requesterOrgGuid = item.getRequestInfo().getOrganization().getOrgRootGUID();
        debt.responseStatus = item.getResponseStatus();
        debt.requesterOrgTel = item.getRequestInfo().getOrganization().getTel();
        debt.requesterFio = item.getRequestInfo().getExecutorInfo().getFio();

        if(item.getResponseData() != null)
            debt.response = AsuDebtResponse.from(item.getResponseData());

        return debt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AsuDebtRequest request = (AsuDebtRequest) o;
        return Objects.equals(gisId, request.gisId)
                && number.equals(request.number)
                && startDate.equals(request.startDate)
                && endDate.equals(request.endDate)
                && houseAddr.equals(request.houseAddr)
                && houseGuid.equals(request.houseGuid)
                && responseDate.equals(request.responseDate)
                && requesterOrgName.equals(request.requesterOrgName)
                && requesterOrgGuid.equals(request.requesterOrgGuid)
                && Objects.equals(requesterOrgTel, request.requesterOrgTel)
                && requesterFio.equals(request.requesterFio)
                && responseStatus == request.responseStatus
                && response != null && response.equals(request.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, startDate, endDate, houseAddr, houseGuid, responseDate, requesterOrgName, requesterOrgGuid, requesterOrgTel, requesterFio, responseStatus, response);
    }
}
