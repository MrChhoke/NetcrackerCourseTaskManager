package ua.edu.sumdu.j2se.bondar.tasks.model;


import java.io.Serializable;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Serializable {

    private static final long serialVersionUID = 8428557046825510638L;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public  abstract Task getTask(int index);
    public abstract Stream<Task> getStream();
}
