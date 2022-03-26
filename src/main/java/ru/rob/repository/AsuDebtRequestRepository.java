package ru.rob.repository;

import ru.rob.entity.AsuDebtRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rob.integration.ResponseStatusType;

import java.util.List;

@Repository
public interface AsuDebtRequestRepository extends JpaRepository<AsuDebtRequest, String> {

    AsuDebtRequest findByNumber(String number);

    List<AsuDebtRequest> findAllByResponseStatus(ResponseStatusType status);

    @Query( "SELECT COUNT(d_r.id) " +
            "  FROM AsuDebtRequest d_r " +
            " WHERE d_r.responseStatus = :type " //+ " and d.task.id = :task and d.messageUuid is null and d.error is null"
    )
    Integer countForSend(@Param("type") ResponseStatusType type//, @Param("task") Long task
    );

    @Query(value =
            "SELECT d_r.id " +
                    "  FROM debt_request d_r " +
                    " WHERE d_r.response_status = 'SENT' "
            , nativeQuery = true)
    List<Object[]> findIdForSendNative();

}
