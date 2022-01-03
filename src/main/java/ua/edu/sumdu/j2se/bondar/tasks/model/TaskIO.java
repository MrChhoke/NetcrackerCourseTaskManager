package ua.edu.sumdu.j2se.bondar.tasks.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bondar.tasks.view.ConsoleUkrainianView;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class TaskIO {

    private final static Logger logger = LogManager.getLogger(ConsoleUkrainianView.class);

    public static void write(AbstractTaskList tasks, OutputStream out){
        try (ObjectOutputStream file = new ObjectOutputStream(out)){
            file.writeInt(tasks.size());
            for(Task task : tasks){
                file.writeObject(task.getTitle());
                if(task.isActive())
                    file.writeInt(1);
                else
                    file.writeInt(0);
                if(task.isRepeated()){
                    file.writeInt(task.getRepeatInterval());
                    file.writeObject(task.getStartTime());
                    file.writeObject(task.getEndTime());
                }else{
                    file.writeInt(0);
                    file.writeObject(task.getStartTime());
                }
            }
        } catch (IOException e) {
            logger.error("IOException  in TASKIO (write): " + e);
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in){
        try (ObjectInputStream file = new ObjectInputStream(in)){
            int sizeList = file.readInt();
            for(int i = 0; i < sizeList; i++){
                String title = (String) file.readObject();
                boolean isActive = false;
                if(file.readInt() == 1) isActive = true;
                int interval = file.readInt();
                if(interval != 0){
                    tasks.add(new Task(title,  (LocalDateTime) file.readObject(),
                            (LocalDateTime) file.readObject(), interval));
                }else{
                    tasks.add(new Task(title,  (LocalDateTime) file.readObject()));
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("IOException | ClassNotFoundException in TASKIO (read): " + e);
        }
    }
    public static void writeBinary(AbstractTaskList tasks, File file){
        try (FileOutputStream fileWriter = new FileOutputStream(file)) {
            write(tasks,fileWriter);
        } catch (IOException e) {
            logger.error("IOException  in TASKIO (writeBinary): " + e);
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file){
        try(FileInputStream fileReader = new FileInputStream(file)){
            read(tasks,fileReader);
        } catch (IOException e) {
            logger.error("IOException  in TASKIO (readBinary): " + e);
        }
    }


    public static void write(AbstractTaskList tasks, Writer out){
        Gson a = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder stringBuilder = new StringBuilder();
        if(tasks.size() == 0) return;
        stringBuilder.append('[');
        for(int i = 0; i < tasks.size(); i++){
            stringBuilder.append(a.toJson(tasks.getTask(i), Task.class));
            if(i < tasks.size() - 1)
                stringBuilder.append(", ");
            else
                stringBuilder.append(']');
        }
        try (BufferedWriter buffered = new BufferedWriter(out);){
            buffered.write(stringBuilder.toString());
        } catch (IOException e) {
            logger.error("IOException  in TASKIO (write): " + e);
        }

    }

    public static void read(AbstractTaskList tasks, Reader in){
        Gson a = new Gson();
        StringBuilder temp = new StringBuilder();
        Scanner scanner = new Scanner(in);
        if(!scanner.hasNextLine()) return;
        while(scanner.hasNextLine()){
            temp.append(scanner.nextLine());
        }
        Task[] b = a.fromJson(temp.toString(),Task[].class);
        for(int i = 0; i < b.length; i++){
            tasks.add(b[i]);
        }
    }
    public static void writeText(AbstractTaskList tasks, File file){
        try {
            write(tasks,new FileWriter(file));
        } catch (IOException e) {
            logger.error("IOException  in TASKIO (writeText): " + e);
        }
    }

    public static void readText(AbstractTaskList tasks, File file){
        try {
            read(tasks,new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("IOException  in TASKIO (readText): " + e);
        }
    }
}
