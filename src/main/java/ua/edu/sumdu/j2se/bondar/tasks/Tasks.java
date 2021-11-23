package ua.edu.sumdu.j2se.bondar.tasks;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        LinkedTaskList arr = new LinkedTaskList();
        for(Task task : tasks)
            if(task.nextTimeAfter(start) != null
                    && task.nextTimeAfter(start).compareTo(end) != 1) {
                arr.add(task);
            }

        Iterable<Task> tasks1 = new Iterable<Task>() {
            @Override
            public Iterator<Task> iterator() {
                return new Iterator<Task>() {
                    int index = 0;
                    @Override
                    public boolean hasNext() {
                        if(index < arr.size()) return true;
                        return false;
                    }

                    @Override
                    public Task next() {
                        return arr.getTask(index++);
                    }
                };
            }
        };
        return tasks1;
    }
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        SortedMap<LocalDateTime, Set<Task>> map = new TreeMap<>();
        Iterable<Task> tasks1 = incoming(tasks,start,end);
        LocalDateTime next;
        for (Task task : tasks1){
            next = task.nextTimeAfter(start);
            while(next != null && !next.isAfter(end)){
                if(map.containsKey(next)){
                    map.get(next).add(task);
                }else{
                    Set<Task> a = new HashSet<>();
                    a.add(task);
                    map.put(next,a);
                }
                next = task.nextTimeAfter(next);
            }
        }
        return map;
    }
}
