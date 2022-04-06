package ru.rob.dto.requests;

import com.google.common.base.Strings;
//import ru.git.gkh.core.enums.DebtResponseStatus;
import ru.rob.gkh.DebtResponseStatus;
//import ru.git.gkh.core.utils.SynchrDateFormat;
import ru.rob.gkh.SynchrDateFormat;

import java.util.Date;

public class DebtRequestFilterDto {
    public Integer start;
    public Integer limit;
    public DebtResponseStatus responseStatus;
    public Boolean hasDebt;
    public String requestNumber;
    public Date responseStartDate;
    public Date responseEndDate;

    public DebtRequestFilterDto(){}

    public static DebtRequestFilterDto of(
            final DebtResponseStatus responseStatus,
            final String requestNumber,
            final String responseStartDateStr,
            final String responseEndDateStr
    ){
        final DebtRequestFilterDto dto = new DebtRequestFilterDto();
        dto.responseStatus = responseStatus;
        dto.requestNumber = !Strings.isNullOrEmpty(requestNumber) ? requestNumber : null;
        dto.responseStartDate = SynchrDateFormat.parseDateNotThrows(responseStartDateStr);
        dto.responseEndDate = SynchrDateFormat.parseDateNotThrows(responseEndDateStr);
        return dto;
    }

    public static DebtRequestFilterDto of(
            final Integer start,
            final Integer limit,
            final String responseStatusStr,
            final Boolean hasDebt,
            final String requestNumber,
            final String responseStartDateStr,
            final String responseEndDateStr
    ){
        final DebtRequestFilterDto dto = new DebtRequestFilterDto();
        dto.start = start;
        dto.limit = limit;
        dto.responseStatus = DebtResponseStatus.get(responseStatusStr);
        dto.hasDebt = hasDebt;
        dto.requestNumber = !Strings.isNullOrEmpty(requestNumber) ? requestNumber : null;
        dto.responseStartDate = SynchrDateFormat.parseDateNotThrows(responseStartDateStr);
        dto.responseEndDate = SynchrDateFormat.parseDateNotThrows(responseEndDateStr);
        return dto;
    }
}
