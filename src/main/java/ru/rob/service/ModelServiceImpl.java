package ru.rob.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rob.integration.ResponseStatusType;
import ru.rob.repository.AsuDebtRequestRepository;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    private Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    @Autowired
    private AsuDebtRequestRepository repository;

    @Override
    public void tryRequest() {
        log.debug("EXEC SQL");
//        int count = repository.countForSend(ResponseStatusType.SENT);
//        log.debug("COUNT ids" + count);
        List<Object[]> idTupleList = repository.findIdForSendNative();
        System.out.println();
    }

}
