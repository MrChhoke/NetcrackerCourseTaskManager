package ua.edu.sumdu.j2se.bondar.tasks.view;

import ua.edu.sumdu.j2se.bondar.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bondar.tasks.model.Task;

public interface View {
    void mainPage();
    void taskPage(AbstractTaskList list);
    void createTaskPage(AbstractTaskList list);
    void deleteTaskPage(AbstractTaskList list);
    void changeTaskPage(AbstractTaskList list);
    void timeTaskPage(AbstractTaskList list);
    void notification(Task task);
    void notificationOff();
    void notificationOn();
    void inputMismatch();
}
