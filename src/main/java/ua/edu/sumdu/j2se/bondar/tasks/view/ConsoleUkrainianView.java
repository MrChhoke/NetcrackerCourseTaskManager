package ua.edu.sumdu.j2se.bondar.tasks.view;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bondar.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bondar.tasks.model.Task;
import ua.edu.sumdu.j2se.bondar.tasks.model.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class ConsoleUkrainianView implements View{
    private final static Logger logger = LogManager.getLogger(ConsoleUkrainianView.class);


    @Override
    public void mainPage() {
        System.out.println(
                        "1.\tПереглянути список задач\n" +
                        "2.\tСтворити задачу\n" +
                        "3.\tВидалити задачу\n" +
                        "4.\tРедагувати існуючу задачу\n" +
                        "5.\tВключити повідомлення\n" +
                        "6.\tВиключити повідомлення\n" +
                        "7.\tЗадачі за певним часом");
    }

    @Override
    public void taskPage(AbstractTaskList list) {
        System.out.println("Задачі, які існують:");
        int i = 0;
        if(list.size() == 0) {
            System.out.println("Задачі відсутні!");
            return;
        }
        for(Task task : list){
            System.out.println((i+1) + ")" + task);
            i++;
        }
    }

    @Override
    public void createTaskPage(AbstractTaskList list) {
        System.out.println("Створення задачі:");
        String title;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть ім'я задачі: ");
        title = scanner.nextLine();
        System.out.println("Задача повторюється (+/-): ");
        String reapet = scanner.nextLine();
        String startTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if(reapet.equals("-")){
            System.out.println("Введіть час події у наступному форматі - yyyy-MM-dd HH:mm -> ");
            startTime = scanner.nextLine();
            LocalDateTime start = null;
            try {
                start = LocalDateTime.parse(startTime, formatter);
            }catch (DateTimeParseException e){
                System.out.println("Некоректна дата!");
                logger.error("Некоректна дата!");
                return;
            }
            Task task = new Task(title,start);
            task.setActive(true);
            list.add(task);
            logger.info("Cтворено задачу: " + title);
            return;
        }
        System.out.println("Введіть час початку події у наступному форматі - yyyy-MM-dd HH:mm -> ");
        startTime = scanner.nextLine();
        try {
            LocalDateTime start = LocalDateTime.parse(startTime,formatter);
            System.out.println("Введіть час закінчення події у наступному форматі - yyyy-MM-dd HH:mm -> ");
            String endTime = scanner.nextLine();
            LocalDateTime end = LocalDateTime.parse(endTime,formatter);
            System.out.println("Введіть час інтервал події (ціле значення): ");
            int interval = scanner.nextInt();
            Task task = new Task(title,start,end, interval);
            task.setActive(true);
            list.add(task);
            logger.info("Cтворено задачу: " + title);
        }catch (DateTimeParseException e){
            System.out.println("Некоректна дата!");
            logger.error("Некоректна дата!");
            return;
        }
    }

    @Override
    public void deleteTaskPage(AbstractTaskList list) {
        taskPage(list);
        System.out.println("Введіть індекс для видалення:");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        list.remove(list.getTask(index-1));
    }

    @Override
    public void changeTaskPage(AbstractTaskList list) {
        taskPage(list);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть задачу, яку хочете змінити за індексом: ");
        int index = scanner.nextInt();
        index--;
        System.out.println("Який параметр ви хочете змінити?\n" +
                "1. Назву\n" +
                "2. Час\n" +
                "3. Інтервал\n" +
                "4. Відмінити зміни");

        System.out.println("Ваш вибір: ");
        int indexChange = scanner.nextInt();
        if(indexChange == 1){
            System.out.println("Введіть нову назву:");
            String newTitle = scanner.nextLine();
            list.getTask(index).setTitle(newTitle);
            return;
        }
        if(indexChange == 3){
            if(!list.getTask(index).isRepeated()) return;
            System.out.println("Введіть новий інтервал:");
            int newInterval = scanner.nextInt();
            list.getTask(index).setTime(list.getTask(index).getStartTime(), list.getTask(index).getEndTime(),newInterval);
            return;
        }
        if(indexChange == 2){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            if(!list.getTask(index).isRepeated()){
                System.out.println("Введіть новий час у наступному форматі - yyyy-MM-dd HH:mm -> ");
                String time = scanner.nextLine();
                list.getTask(index).setTime(LocalDateTime.parse(time,formatter));
            }
            try {
                System.out.println("Введіть новий час початку події у наступному форматі - yyyy-MM-dd HH:mm -> ");
                String startTime = scanner.nextLine();
                LocalDateTime start = LocalDateTime.parse(startTime, formatter);
                System.out.println("Введіть новий час закінчення події у наступному форматі - yyyy-MM-dd HH:mm -> ");
                String endTime = scanner.nextLine();
                LocalDateTime end = LocalDateTime.parse(endTime, formatter);
                list.getTask(index).setTime(start, end, list.getTask(index).getRepeatInterval());
            }catch (DateTimeParseException e){
                System.out.println("Некоректна дата!");
                logger.error("Некоректна дата! " + e);
            }
        }
    }

    @Override
    public void timeTaskPage(AbstractTaskList list) {
        SortedMap<LocalDateTime, Set<Task>> tasks;
        Scanner scanner = new Scanner(System.in);
        LocalDateTime start,end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            System.out.println("Введіть час початку події у наступному форматі - yyyy-MM-dd HH:mm -> ");
            String startTime = scanner.nextLine();
            start = LocalDateTime.parse(startTime,formatter);
            System.out.println("Введіть час закінчення події у наступному форматі - yyyy-MM-dd HH:mm -> ");
            String endTime = scanner.nextLine();
            end = LocalDateTime.parse(endTime,formatter);
        }catch (DateTimeParseException e){
            System.out.println("Некоректна дата!");
            logger.error("Некоректна дата! " + e);
            return;
        }
        System.out.println(start);
        System.out.println(end);
        tasks = Tasks.calendar(list.getStream().collect(Collectors.toList()), start,end);
        System.out.println(tasks.size());
        for(LocalDateTime a : tasks.keySet()){
            Set<Task> tasks1 = tasks.get(a);
            for(Task task : tasks1){
                System.out.println(task);
            }
        }
    }

    public void notification(Task task){
        System.out.println("\nПовідомлення -> наступна задача: \n" + task);
    }

    public void notificationOn(){
        System.out.println("\nПовідомлення включені");
    }

    public void notificationOff(){
        System.out.println("\nПовідомлення виключені");
    }

    @Override
    public void inputMismatch() {
        System.out.println("Невідома команда!\n");
    }
}
