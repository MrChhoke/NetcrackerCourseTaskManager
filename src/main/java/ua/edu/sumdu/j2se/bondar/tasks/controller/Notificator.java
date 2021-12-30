package ua.edu.sumdu.j2se.bondar.tasks.controller;

import ua.edu.sumdu.j2se.bondar.tasks.model.AbstractTaskList;

public interface Notificator {
    void eventNotificator(AbstractTaskList list);
    void startNotificator();
    void shutdownNotificator();
}
