package ru.rob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rob.entity.AsuDebtRequest;
import ru.rob.integration.ResponseStatusType;

import java.util.List;

@Repository
public interface AsuDebtRequestRepository extends JpaRepository<AsuDebtRequest, String> {

    AsuDebtRequest findByNumber(String number);

    List<AsuDebtRequest> findAllByResponseStatus(ResponseStatusType status);

    @Query( "SELECT COUNT(d_r.id) " +
            "  FROM AsuDebtRequest d_r "
           +" WHERE d_r.responseStatus = :type "
            //+" WHERE d_r.responseStatus = :type " //+ " and d.task.id = :task and d.messageUuid is null and d.error is null"
    )
    Integer countForSend(@Param("type") ResponseStatusType type//, @Param("task") Long task
    );

    @Query(value =
//            "ALTER SESSION SET CURRENT_SCHEMA=VLGD_PROD; " +
            "SELECT COUNT(d_r.id) " +
            "  FROM VLGD_PROD.debt_request d_r "
            //+" WHERE d_r.responseStatus = :type " //+ " and d.task.id = :task and d.messageUuid is null and d.error is null"
            +" WHERE d_r.response_status = 'SENT' "
            , nativeQuery = true)
    Integer countForSendNative();

    @Query(value =
            "  SELECT d_r.id " +
            "    FROM debt_request d_r " +
            "   WHERE d_r.response_status = 'SENT' "
           +"ORDER BY d_r.id "
            , nativeQuery = true)
    List<Object[]> findIdForSendNative();

}
