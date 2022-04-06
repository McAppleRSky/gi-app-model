package ru.rob.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.git.gkh.buh.calc.ProtocolBean;
import ru.git.gkh.buh.calc.event.NachCalcProgressUpdateEvent;
import ru.git.gkh.buh.calc.event.SseEvent;
import ru.git.gkh.capremont.tasks.TaskParams;
import ru.git.gkh.core.enums.ProcessState;
import ru.git.gkh.core.utils.StringUtils;
import ru.git.gkh.core.utils.SynchrDateFormat;
import ru.git.gkh.entity.TaskControlStatusEnum;
import ru.git.gkh.entity.report.ProtocolStore;
import ru.git.gkh.entity.sec.Usr;
import ru.git.gkh.sse.Event;
import ru.git.gkh.sse.SseMessageSendBean;
import ru.git.gkh.utils.observers.ScriptObserver;
import ru.git.gkh.utils.task.TaskControlBean;
import ru.git.gkh.utils.task.TaskObserver;
import ru.git.gkh.utils.task.TaskSseEvent;
import ru.prosoftlab.model.gkh.process.ProtocolType;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import java.util.Date;
import java.util.List;

public class Task implements Runnable, ScriptObserver {

    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    protected TaskObserver observer;
    protected long id;
    protected String description;
    protected Date startDate;
    protected String user;
    protected Long threadId;

    private Integer cntCurr;
    private Integer cntAll;
    private Integer percentDone;

    private Integer cntErr = 0;
    private Integer cntDone = 0;

    protected List<String> gridIds;
    protected String elId;

    private int protocolBufferSize = 50000;

    private ProtocolStore protocolStore = null; // Протокол
    private StringBuilder protocolStoreData = new StringBuilder(protocolBufferSize); //Буфер для временного хранения протокола
    private int savedLength = 0;

    private TaskControlBean taskControlBean;

    private SseMessageSendBean sseMessageSendBean = null;

    public Integer getCntCurr() {
        return cntCurr;
    }

    public Integer getCntAll() {
        return cntAll;
    }

    public Integer getPercentDone() {
        return percentDone;
    }

    public void setCntCurr(Integer cntCurr) {
        this.cntCurr = cntCurr;
    }

    public void setElement(String element) {
        this.elId = element;
    }

    public void setCntAll(Integer cntAll) {
        this.cntAll = cntAll;
    }

    public void setPercentDone(Integer percentDone) {
        this.percentDone = percentDone;
    }

    public Integer getCntErr() {
        return cntErr;
    }

    public synchronized void addErr() {
        this.cntErr++;
    }

    public Integer getCntDone() {
        return cntDone;
    }

    public synchronized void addDone() {
        this.cntDone++;
    }

    public String getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Task(long id, String user) {
        this.id = id;
        this.user = user;
    }

    public Task(long id, TaskObserver observer, String description, String user) {
        this.id = id;
        this.observer = observer;
        this.description = description;
        this.startDate = new Date();
        this.user = user;
        if (!user.isEmpty()) {
            sendInitEvent();
        }
    }

    public ProtocolStore getProtocolStore() {
        return protocolStore;
    }

    public Task(long id, String description, String user) {
        this(id, null, description, user);
    }

    public Task(long id, TaskObserver observer, String description) {
        this(id, observer, description, null);
    }

    public void sendInitEvent() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_START);
        sendEvent(event);
    }

    public void updateTask(String msg) {
        Integer percent = null;
        if (cntCurr != null && cntAll != null) {
            Integer percDone = percentDone == null ? 0 : percentDone;
            percent = (cntCurr * (100 - percDone)) / cntAll + percDone;
        }
        String percenStr = percent == null ? null : percent.toString();
        updateTask(percenStr, msg);
    }

    public void updateTask(String progress, String msg) {
        LOG.trace("updateTask: progress {}, msg {}", progress, msg);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        //---нужно посылать, чтобы после log off задача все равно отображалась
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");

        if (progress != null) builder.add("progress", progress);
        builder.add("message", msg != null ? msg : "");
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_PROGRESS);
        sendEvent(event);
    }

    public void initTask(TaskParams params) throws Exception {

        this.threadId = Thread.currentThread().getId();
        params.put("threadId", this.threadId);

        if (taskControlBean == null)
            this.taskControlBean = (TaskControlBean) new InitialContext().lookup("java:module/TaskControlBeanEJB");
        if (taskControlBean != null) {
            taskControlBean.saveTaskControl(params);
        }
    }

    public void finishTask(String status) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        //---нужно посылать, чтобы после log off задача все равно отображалась
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");
        builder.add("progress", "100");
        builder.add("status", status);
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_FINISH);
        sendEvent(event);
    }

    public void destroyTask(TaskControlStatusEnum status) {
        try {
            if (taskControlBean != null) {
                taskControlBean.destroyTaskControl(this.id, this.threadId, status);
            }
        } catch (Exception e) {
        }
    }

    public void errorTask(String error) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        //---нужно посылать, чтобы после log off задача все равно отображалась
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");

        builder.add("error", error);
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_ERROR);
        sendEvent(event);
    }

    public void protocol() {
        if (protocolStore != null) {
            protocol(protocolStore.id);
        }
    }

    public void protocol(Long protocolId) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        //---нужно посылать, чтобы после log off задача все равно отображалась
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");

        builder.add("protocol", protocolId);
        builder.add("ext", "zip");
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_PROTOCOL);
        sendEvent(event);
    }

    public void epdLoad(Long storeId, String ext) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        //---нужно посылать, чтобы после log off задача все равно отображалась
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");

        builder.add("epdId", storeId);
        builder.add("ext", ext);
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_EPD_LOAD);
        sendEvent(event);
    }

    public void reportLoad(Long storeId, String ext) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        //---нужно посылать, чтобы после log off задача все равно отображалась
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");

        builder.add("storeId", storeId);
        builder.add("ext", ext);
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_REPORT_LOAD);
        sendEvent(event);
    }

    public void gorodLoad(Long gorodFileId, String ext) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        //---нужно посылать, чтобы после log off задача все равно отображалась
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");

        builder.add("gorodFileId", gorodFileId);
        builder.add("ext", ext);
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_GOROD_LOAD);
        sendEvent(event);
    }

    public void fileStorageLoad(Long fileId, String ext, String tooltip) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("taskId", id);
        //---нужно посылать, чтобы после log off задача все равно отображалась
        builder.add("timeStr", SynchrDateFormat.formatTime(startDate));
        builder.add("description", description);
        builder.add("user", user != null ? user.trim() : "null");

        builder.add("fileId", fileId);
        builder.add("ext", ext);
        builder.add("tooltip", tooltip);
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_FILE_STORAGE_LOAD);
        sendEvent(event);
    }

    private void sendEvent(TaskSseEvent event) {
        //На кластере не сработает
        /*if (observer != null) {
            observer.handleEvent(event);
        }*/

        //Переносим на jms
        /*try {
            GlobalHandler.sendMessage(event.getRawData(), event.getEvent().toString(), user);
        } catch (Throwable th) {
            LOG.error("Error sending sse message", th);
        }*/

        try {
            if (sseMessageSendBean == null) {
                sseMessageSendBean = (SseMessageSendBean) new InitialContext().lookup("java:module/SseMessageSendEJB");
            }
            sseMessageSendBean.sendMessage(event.getRawData(), event.getEvent().toString(), user);
        } catch (Throwable th) {
            LOG.error("Error sending sse message", th);
        }

    }

    /**
     * Посылает сообщение клиенту, о том что нужно обновить списки по заданному массиву id-шников
     */
    public void updateGrids() {
        if (!this.gridIds.isEmpty()) {
            updateGrids(this.gridIds);
        }
    }

    public void updateGrids(List<String> gridIds) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (String gridId : gridIds) {
            builder.add(gridId);
        }
        TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_UPDATE_GRIDS);
        sendEvent(event);
    }


    @Override
    public void run() {}


    /// Функционал для работы с протоколом

    //Создает объект протокола, но пока нигде не сохраняет
    public ProtocolStore createProtocol(ProtocolType type, String source, Usr user) {
        protocolStore = new ProtocolStore();
        protocolStore.svd = new Date();
        protocolStore.state = ProcessState.RUN;
        protocolStore.protocolType = type;
        protocolStore.source = source;
        protocolStore.usr = user;
        protocolStore.fvd = new Date();

        return protocolStore;

    }

    //Запись в протокол
    public void writelnProtocol(String message) {
        protocolStoreData.append(message);
        writeProtocol("\n");
    }

    //Запись в протокол
    public void writeProtocol(String message) {
        if (protocolStore != null) {
            protocolStoreData.append(message);

            if (protocolBufferSize > 0 && protocolStoreData.length() > protocolBufferSize) {
                protocolStore.fvd = new Date();
                saveProtocol();
            }
        }
    }


    /*Установка максимального размера буффера, после которого идет сохранение протокола в БД
     * По умолчанию - 50000
     * Если размер установлен в 0, то сохранение не происходит и протокол накапливается, пока не будет вызвал saveProtocol */
    public void setProtocolBufferSize(int size) {
        protocolBufferSize = size;
    }

    public void updateProtocolFinish() {
        updateProtocolFinish(null);
    }

    public void updateProtocolError() {
        updateProtocolError(null);
    }

    public void updateProtocolFinish(String resultTxt) {
        if (protocolStore != null) {
            protocolStore.state = ProcessState.READY;
            protocolStore.fvd = new Date();
            if (resultTxt != null && resultTxt.length() > 256) {
                resultTxt = resultTxt.substring(0, 255);
            }
            protocolStore.resultTxt = resultTxt;
        }
    }

    public void updateProtocolError(String resultTxt) {
        if (protocolStore != null) {
            protocolStore.state = ProcessState.ERROR;
            protocolStore.fvd = new Date();
            if (resultTxt != null && resultTxt.length() > 256) {
                resultTxt = resultTxt.substring(0, 255);
            }
            protocolStore.resultTxt = resultTxt != null ? resultTxt : "Ошибка";
        }
    }


    /**
     * Сохранение протокола в отдельной транзакции
     **/
    public void saveProtocol() {
        if (protocolStore == null) {
            return;
        }  //Нет необходимости, Throwable отловится
        try {
            ProtocolBean protocolBean = (ProtocolBean) new InitialContext().lookup("java:module/ProtocolBean");
            protocolBean.saveStore(protocolStore, protocolStoreData, savedLength + 1);
            savedLength = savedLength + protocolStoreData.length();
            protocolStoreData = new StringBuilder(protocolBufferSize);

        } catch (Throwable e) {
            LOG.error("ошибка при записи в файл протокола " + protocolStore.protocolType + " " + e.getMessage(), e);
        }
    }

    public void elementProgress(int progress, String msg) {
        if (!StringUtils.isEmpty(elId)) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("progress", progress);
            builder.add("msg", msg);
            builder.add("elementId", this.elId);
            TaskSseEvent event = new TaskSseEvent(builder.build().toString(), Event.TASK_ELEMENT_PROGRESS);
            sendEvent(event);
        }
    }

    public int updateProgress(int offset, int limit, int step, int oldProgress){
        if(limit == 0) return 100;
        final int current = (int) ((offset / (float)limit) * 100);
        if(current >= (step + oldProgress)) {
            updateTask(String.valueOf(current), offset + " / " + limit);
            return current;
        } else {
            return oldProgress;
        }
    }

    @Override
    public void handleEvent(SseEvent event) {
        try {
            if (event instanceof NachCalcProgressUpdateEvent) {
                NachCalcProgressUpdateEvent ev = (NachCalcProgressUpdateEvent) event;
                Number progress = ev.getCompletionPercent();
                updateTask(progress != null ? String.valueOf(progress.intValue()) : "1", ev.getProgressMessage());
            } else if (event instanceof TaskSseEvent) {
                sendEvent((TaskSseEvent) event);
            }
        } catch (Exception e) {
            LOG.error("Ошибка получения обработки сообщения: " + e.getMessage());
        }
    }

}
