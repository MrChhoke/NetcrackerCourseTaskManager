package ua.edu.sumdu.j2se.bondar.tasks.model;


import java.util.Iterator;
import java.util.stream.Stream;


/**
 * @author bondar
 * @version 1.0
 * */

public class ArrayTaskList extends AbstractTaskList implements Cloneable{

    /**
     * @param size заповність масиву на даний час
     * @param capacity загальний розмір масиву
     * @param []array масив задач
     * */

    private int size = 0;
    private int capacity = 10;
    private Task []array = new Task[capacity];

    public ArrayTaskList(){

    }

    /**
     * Метод для додавання нової задачі в масиві
     * @param task посилання на об'єкт завдання
     * */
    public void add(Task task){
        if(task == null){
            throw new NullPointerException();
        }
        if(capacity == size){
            array = reserve();
        }
        array[size] = task.clone();
        size++;
    }


    /**
     * Метод для пошуку та видалення (якщо таких об'єктів з однаковим часом більше 1)
     * конкретної в масиві
     * @param task посилання на об'єкт завдання
     * @return {@code true} якщо об'єкт був у масиві, в іншому випадку {@code false}
     * */
    public boolean remove(Task task){
       int count = 0;
       int index = -1;
        for(int i = 0; i < size; i++) {
                if (array[i].getStartTime() == task.getStartTime()) {
                    count++;
                    if(array[i].getTitle().equals(task.getTitle())){
                        index = i;
                }
            }
        }

        if(index != -1 && count > 1) {
            for (int i = index; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            size--;
        }
        if(index != -1){
            return true;
        }
        return false;
    }

    /**
     * Метод для отримання заповненості масиву на даний час
     * @return {@code size}
     * */
    public int size(){
        return size;
    }

    /**
     * Метод для отримання посилання на якийсь з елементів масиву
     * @param index індекс елементу який починається з 0
     * @return {@code array[index]} посилання на об'єкт масиву
     * */
    public Task getTask(int index) {
        if (index >= capacity && index < 0){
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    /**
     * Метод для перевизначення довжини масиву
     * @return {@code newArray} посилання на новий масив
     * */
    private Task []reserve(){
        capacity *= 2;
        Task []newArray = new Task[capacity];
        for(int i = 0; i < array.length; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int index;
            private boolean canCalledRemove = false;

            @Override
            public void remove() {
                if(!canCalledRemove)
                    throw new IllegalStateException();
                canCalledRemove = false;

                for(int i = index - 1; i < size; i++){
                    array[i] = array[i + 1];
                }
                size--;
                index--;
                array[size] = null;

            }


            @Override
            public boolean hasNext() {
                if(index < size) return true;
                return false;
            }
            @Override
            public Task next() {
                canCalledRemove = true;
                return array[index++];
            }

        };
    }



    @Override
    public int hashCode() {
        int result = size;
        for(int i = 0; i < size; i++)
            result += array[i].hashCode();
        return result % size;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != getClass()) return false;

        ArrayTaskList temp = (ArrayTaskList)obj;
        Task tempTask = array[0];

        int indexStart = 0;
        while (indexStart < temp.size && !temp.array[indexStart].equals(tempTask)){indexStart++;}

        for(int i = indexStart; i < temp.size && indexStart < size; i++, indexStart++){
            if(!temp.array[i].equals(array[indexStart])) return false;
        }

        return true;
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList temp = (ArrayTaskList) super.clone();
        temp.array = new Task[size];
        for(int i = 0; i < size; i++){
            temp.array[i] = array[i].clone();
        }
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < size; i++){
            temp.append(array[i].toString());
            temp.append('\n');
            temp.append('\n');
        }

        return String.valueOf(temp);
    }


    @Override
    public Stream<Task> getStream() {
        return Stream.of(array).limit(size);
    }
}
