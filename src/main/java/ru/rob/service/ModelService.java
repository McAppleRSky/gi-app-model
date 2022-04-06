package ru.rob.service;

import ru.rob.dto.requests.DebtRequestFilterDto;

public interface ModelService {

    void tryRequest();

    int formResponses(DebtRequestFilterDto filter);

}
