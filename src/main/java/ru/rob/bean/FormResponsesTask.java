package ru.rob.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.git.gkh.capremont.tasks.TaskParams;
import ru.git.gkh.dto.debtRequests.DebtRequestFilterDto;
import ru.git.gkh.entity.TaskControlStatusEnum;
import ru.git.gkh.system.handbooks.DebtRequestBean;
import ru.git.gkh.utils.task.Task;

public class FormResponsesTask extends Task {
    private final DebtRequestBean debtRequestBean;
    private final TaskParams params;

    private final static Logger log = LoggerFactory.getLogger(FormResponsesTask.class);

    public FormResponsesTask(final TaskParams params, final DebtRequestBean debtRequestBean) {
        super(params.getId(), params.getObserver(), params.getDescription(), params.getUserName());
        this.debtRequestBean = debtRequestBean;
        this.params = params;
    }

    @Override
    public void run() {
        try {
            initTask(params);
            final int count = debtRequestBean.formResponses((DebtRequestFilterDto) params.get("filter"));
            finishTask(String.format("Сформировано ответов: %s", count));
        } catch (Exception e) {
            destroyTask(TaskControlStatusEnum.ERROR);
            log.error("Ошибка при формировании ответов на запросы о задолженности", e);
        }

    }
}
