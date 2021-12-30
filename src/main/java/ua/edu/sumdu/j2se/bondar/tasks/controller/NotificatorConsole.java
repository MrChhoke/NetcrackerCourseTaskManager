package ua.edu.sumdu.j2se.bondar.tasks.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bondar.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bondar.tasks.model.Task;
import ua.edu.sumdu.j2se.bondar.tasks.model.Tasks;
import ua.edu.sumdu.j2se.bondar.tasks.view.ConsoleUkrainianView;
import ua.edu.sumdu.j2se.bondar.tasks.view.View;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class NotificatorConsole implements Notificator, Runnable{
    private boolean working;
    private AbstractTaskList list;
    private final static Logger logger = LogManager.getLogger(Notificator.class);
    private View view = new ConsoleUkrainianView();

    public NotificatorConsole(AbstractTaskList list) {
        this.list = list;
    }

    @Override
    public void run() {
        startNotificator();
        eventNotificator(list);
    }

    @Override
    public void eventNotificator(AbstractTaskList list) {
        while (working){
            SortedMap<LocalDateTime, Set<Task>> map = Tasks.calendar(list.getStream().collect(Collectors.toList()),
                            LocalDateTime.now(),LocalDateTime.now().plusSeconds(2));
            for(LocalDateTime times : map.keySet()){
                Set<Task> sets = map.get(times);
                for(Task task : sets){
                    view.notification(task);
                }
            }
            try {
                Thread.sleep(1999);
            } catch (InterruptedException e) {
                logger.error("Помилка роботи потоку у eventNotificator()");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void startNotificator() {
        working = true;

    }

    @Override
    public void shutdownNotificator() {
        working = false;
    }
}
