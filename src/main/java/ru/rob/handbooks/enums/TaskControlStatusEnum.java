package ru.rob.handbooks.enums;

/**
 * Created with IntelliJ IDEA.
 * User: balyshev_av
 * Date: 03.06.15
 * Time: 10:22
 */
public enum TaskControlStatusEnum {

    RUNNING("Выполнение"),
    SUCCESS("Успешно выполнено"),
    ERROR("Ошибка выполнения"),
    STOP("Остановлено"),
    INTERRUPTED("Прервано");

    public String name;

    public String getName() {
        return this.name;
    }

    private TaskControlStatusEnum(String name) {
        this.name = name;
    }

}
