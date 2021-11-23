package ua.edu.sumdu.j2se.bondar.tasks;


import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task> {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public  abstract Task getTask(int index);
    public abstract Stream<Task> getStream();
}
