package ua.edu.sumdu.j2se.bondar.tasks;


public abstract class AbstractTaskList implements Iterable<Task> {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public  abstract Task getTask(int index);

    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList list = new LinkedTaskList();
        for(int i = 0; i < size(); i++){
            if(getTask(i).isActive() && getTask(i).getStartTime() > from && getTask(i).getEndTime() < to){
                list.add(getTask(i));
            }
        }
        return list;
    }

}
