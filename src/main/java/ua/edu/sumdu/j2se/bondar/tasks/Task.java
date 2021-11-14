package ua.edu.sumdu.j2se.bondar.tasks;


/**
 * @author bondar
 * @version 1.0
 * */

public class Task implements Cloneable {


    /**
     * Атрибути класа
     * @param title назва події
     * @param active подія активна
     * @param time час, коли подія відбудеться
     * @param start час, коли подія відбудеться вперше
     * @param end час, після якого подія не буде відбуватися
     * @param interval інтервал з яким відбувається подія
     * */

    private String title;
    private boolean active;
    private int time;
    private int start;
    private int end;
    private int interval = -1;


    public Task(){

    }

    /**
     * Конструктор класа Task
     * @param title назва події
     * @param time час, коли подія відбудеться
     * */
    public Task(String title, int time){
        if(time < 0){
            throw  new  IllegalArgumentException();
        }
        this.title = title;
        this.time = time;
    }

    /**
     * Конструктор класа Task
     * @param title назва події
     * @param start час, коли подія відбудеться вперше
     * @param end час, після якого подія не буде відбуватися
     * @param interval інтервал з яким відбувається подія
     * */
    public Task(String title, int start, int end, int interval){
        if(start < 0 || end < 0 || interval<= 0){
            throw new  IllegalArgumentException();
        }
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public Task(Task task){
        this.title = task.title;
        this.start = task.start;
        this.active = task.active;
        this.end = task.end;
        this.interval = task.interval;
        this.time = task.time;
    }

    /**
     * Метод для отримання імені події
     * @return {@code title} імя подія
     * */
    public String getTitle(){
        String temp = "123";
        if(title != null) temp = title;
        return temp;
    }


    /**
     * Метод для встановлення нової імені події
     * @param title імя подія
     * */
    public void setTitle(String title){
       this.title = title;
    }

    /**
     * Метод для визначення чи подія активна
     * @return {@code active} повертає булеве значення чи активна подія
     * */
    public boolean isActive(){
        return active;
    }

    /**
     * Метод для встановлення активності події
     * @param  active булеве значення чи активна подія
     * */
    public void setActive(boolean active){
        this.active = active;
    }

    /**
     * Метод для отримання часу першої події
     * @return {@code start} якщо подія повторюється,то час початку першої події,
     * або {@code time} якщо подія не повторюється,то час події
     * */
    public int getTime(){
        if(isRepeated()) {
            return start;
        }
        return time;
    }

    /**
     * Метод для встановлення часу події
     * @param time час події
     * */
    public void setTime(int time){
        interval = -1;
        if(time < 0){
            throw new  IllegalArgumentException();
        }
        this.time = time;
    }

    /**
     * Метод для отримання часу події
     * @return {@code start} час початку першої, якщо подія повторюється,
     * або {@code time} час події, якщо подія не повторюється
     * */
    public int getStartTime(){
        if(isRepeated()) {
            return start;
        }
        return time;
    }


    /**
     * Метод для отримання часу завершення подій
     * @return {@code end} час кінця першої, якщо подія повторюється,
     * або {@code time} час кінця події, якщо подія не повторюється
     * */
    public int getEndTime(){
        if(isRepeated()) {
            return end;
        }
        return time;
    }

    /**
     * Метод для отримання інтералу часу
     * @return {@code interval} інтервал часу, якщо подія повторюється,
     * або {@code 0} інтервал часу, якщо подія не повторюється
     * */
    public int getRepeatInterval(){
        if(isRepeated()) {
            return interval;
        }
        else
            return 0;
    }

    /**
     * Метод для встановлення часу події
     * @param start час, коли подія відбудеться вперше
     * @param end час, після якого подія не буде відбуватися
     * @param interval інтервал з яким відбувається подія
     * */
    public void setTime(int start, int end, int interval){
        if(start < 0 || end < 0 || interval<= 0){
            throw new  IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    /**
     * Метод для перевірки чи подія повторюється
     * @return {@code false} негативний результат якщо подія не повторюється,
     * або {@code true} позитивний результат якщо подія повторюється
     * */
    public boolean isRepeated(){
        if(interval == -1) {
            return false;
        }
        return true;
    }

    /**
     * Метод для отримання часу наступної події
     * @param current теперішній час
     * @return {@code getStartTime()} якщо подія не повторюється і активна,
     * {@code getStartTime()} якщо подія повторюється і активна іще час її першого початку не настав,
     * або temp якщо подія повторюється і активна іще час її першого початку пройшов,
     * в інших випадках {@code -1}
     * */
    public int nextTimeAfter(int current){
        if(isActive() && !isRepeated() && getStartTime() > current) {
            return getStartTime();
        }

        if(getStartTime() > current && current + interval <= getEndTime() && isActive() && isRepeated()) {
            return getStartTime();
        }

        if(getStartTime() <= current && current + interval <= getEndTime() && isActive() && isRepeated()) {
            int temp = getStartTime();
            while( temp <= current ) {
                temp += interval; }
            return temp;
        }
        return -1;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += title.hashCode();
        result += (!active ? 1 : 0);
        result += time;
        result += start;
        result += end;
        result += (interval == -1 ? 47 : interval);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != getClass()) return false;

        Task task = (Task)obj;
        if(!title.equals(task.title)) return false;
        if(task.active != active) return false;
        if(task.start != start) return false;
        if(task.end != end) return false;
        if(task.interval != interval) return false;
        if(task.time != time) return false;

        return true;
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        String temp = "Title: " + time + "\nStart time: " + getStartTime() + "\nEnd time: " +
                getEndTime() + "\nActive: " + active + "\nInterval: " + interval;
        return temp;
    }
}
