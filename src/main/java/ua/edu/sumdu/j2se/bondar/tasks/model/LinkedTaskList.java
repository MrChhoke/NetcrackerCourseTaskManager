package ua.edu.sumdu.j2se.bondar.tasks.model;


import java.util.Iterator;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList implements Cloneable{


    private Node first = new Node();
    private int size;


    private class Node{
        Task task = new Task();
        Node next = null;
        Node prev = null;
    }


    public int size(){return size;}

    public void add(Task task) {

        Node current = first;

        while(current.next != null){
            current = current.next;
        }
        Node saver = current;
        current.next = new Node();
        current = current.next;
        current.task = task.clone();
        this.size++;
        current.prev = saver;
    }

    public boolean remove(Task task){

        Node current = first.next;

        Node previous = null;
        int count = 0;
        int index = -1;
        for(int i = 0; i < size && current != null; i++) {
            if (current.task.getStartTime() == task.getStartTime()) {
                count++;
                if(current.task.getTitle().equals(task.getTitle())){
                    index = i;
                    if(index == 0){
                        first.next = current.next;
                        size--;
                        return true;
                    }else{
                        previous.next = current.next;
                    }

                }
            }
            previous = current;
            current = current.next;
        }
        if(index != -1 && count > 1) { size--; }
        if(index != -1){
            return true;
        }
        return false;
    }


    public Task getTask(int index){
        Node current = first.next;

        for(int i = 0; i < index && i < size; i++){
            current = current.next;
        }
        return current.task;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            Node current = first.next;
            Node previous = null;
            boolean canCallRemove = false;

            @Override
            public boolean hasNext() {
                if(current != null) return true;
                return false;
            }

            @Override
            public Task next() {
                canCallRemove = true;
                previous = current;
                current = current.next;
                return previous.task;
            }

            @Override
            public void remove() {
                if(!canCallRemove)
                    throw new IllegalStateException();
                canCallRemove = false;
                previous = previous.prev;
                current = current.prev;
                if(previous == null){
                    first = first.next;
                    current = first;
                    return;
                }
                previous.next = current.next;
                current = previous.next;
            }
        };
    }

    @Override
    public int hashCode() {
        int result = 0;
        Node current = first.next;

        while(current.next != null){
            result += current.task.hashCode();
            current = current.next;
        }

        return result % size;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){return true;}

        if(obj == null || obj.getClass() != getClass()){ return false;}


       LinkedTaskList list = (LinkedTaskList)obj;

        int indexStart = 0;
        for(int i = 0; i < size; i++){
            if(getTask(i).equals(list.getTask(0))){
                indexStart = i;
                break;
            }
        }

        for(int i = indexStart, j = 0; i < size && j < list.size; i++, j++){
            if(!getTask(i).equals(list.getTask(j))) return false;
        }
        return true;
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList temp = (LinkedTaskList) super.clone();
        temp.first = new Node();
        temp.size = 0;
        Node current = first.next;
        while(current != null){
            temp.add(current.task);
            current = current.next;
        }
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for(Task arr: this){
            temp.append(arr.toString());
            temp.append('\n');
            temp.append('\n');
        }
        return String.valueOf(temp);
    }

    @Override
    public Stream<Task> getStream() {
        Stream.Builder<Task> builder = Stream.builder();
        for(Task temp : this){
            builder.add(temp);
        }
        return builder.build();
    }
}
