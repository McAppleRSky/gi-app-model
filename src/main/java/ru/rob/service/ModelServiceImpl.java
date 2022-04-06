package ru.rob.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rob.dto.requests.DebtRequestFilterDto;
import ru.rob.integration.ResponseStatusType;
import ru.rob.repository.AsuDebtRequestRepository;

@Service
public class ModelServiceImpl implements ModelService {

    private final Logger LOGGER = LoggerFactory.getLogger(ModelServiceImpl.class);

    @Autowired
    private AsuDebtRequestRepository repository;

    @Override
    public void tryRequest() {
        LOGGER.info(" -> EXEC SQL");
        int count = repository.countForSendNative()
//                .countForSend(ResponseStatusType.SENT)
                ;
        LOGGER.info(" -> COUNT ids : " + count);
//        List<Object[]> idTupleList = repository.findIdForSendNative();
        System.out.println();
    }

    @Override
    public int formResponses(DebtRequestFilterDto filter) {
        return 0;
    }

}
