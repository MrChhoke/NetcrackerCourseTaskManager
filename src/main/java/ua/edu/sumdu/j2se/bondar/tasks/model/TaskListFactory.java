package ua.edu.sumdu.j2se.bondar.tasks.model;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type){
        switch (type){
            case LINKED:
                return new LinkedTaskList();
            case ARRAY:
                return new ArrayTaskList();
        }
        return null;
    }

}
