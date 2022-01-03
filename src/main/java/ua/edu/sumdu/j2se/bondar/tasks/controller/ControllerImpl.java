package ua.edu.sumdu.j2se.bondar.tasks.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bondar.tasks.model.*;
import ua.edu.sumdu.j2se.bondar.tasks.view.ConsoleUkrainianView;
import ua.edu.sumdu.j2se.bondar.tasks.view.View;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControllerImpl implements Controller{
    private static AbstractTaskList list = new LinkedTaskList();
    private static View view = new ConsoleUkrainianView();
    private Scanner scanner = new Scanner(System.in);
    private final static Logger logger = LogManager.getLogger(ControllerImpl.class);
    private NotificatorConsole notificator = new NotificatorConsole(list);
    private Thread threadNotificator = new Thread(notificator);
    private boolean notificationOn = false;

    ControllerImpl() throws IOException {
        readTasks();
    }


    @Override
    public void start() {
        while(true){
            menuContext();
            saveTasks();
        }
    }

    private void menuContext(){
        int menu = 0;
        view.mainPage();
        try {
            menu = Integer.valueOf(scanner.nextLine());
        }catch (InputMismatchException | NumberFormatException e){
            view.inputMismatch();
            logger.error("Невідома команда у menuContext()" + e);
        }

        for(int i = 0; i < 50; i++){
            System.out.println();
        }
        if(menu == 1)
            view.taskPage(list);
        if(menu == 2)
            view.createTaskPage(list);
        if(menu == 3)
            view.deleteTaskPage(list);
        if(menu == 4)
            view.changeTaskPage(list);
        if(menu == 5 && !notificationOn) {
            view.notificationOn();
            threadNotificator.start();
            notificationOn = true;
        }
        if(menu == 6 && notificationOn) {
            view.notificationOff();
            notificator.shutdownNotificator();
            notificationOn = false;
        }
        if(menu == 7)
            view.timeTaskPage(list);
    }

    @Override
    public void saveTasks() {
        Writer writer = null;
        try {
            writer = new FileWriter(new File("tasks.json"));
        } catch (IOException e) {
            logger.error("Помилка запису задач:" + e);
        }
        TaskIO.write(list,writer);
    }

    @Override
    public void readTasks() {
        Reader reader = null;
        try {
            reader = new FileReader(new File("tasks.json"));
        } catch (FileNotFoundException e) {
            logger.error("Помилка зчитування задач:" + e);
        }
        TaskIO.read(list,reader);
    }

}
