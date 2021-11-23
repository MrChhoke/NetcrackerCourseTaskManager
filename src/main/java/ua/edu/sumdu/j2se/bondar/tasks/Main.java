package ua.edu.sumdu.j2se.bondar.tasks;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Main {


	public static void main(String[] args) throws InterruptedException {

		Task a2 = new Task("2", LocalDateTime.now().plusSeconds(10000),LocalDateTime.now().plusSeconds(20000), 1500);
		Task a3 = new Task("3", LocalDateTime.now().plusSeconds(100),LocalDateTime.now().plusSeconds(200), 30);
		Task a4 = new Task("4", LocalDateTime.now().plusSeconds(100),LocalDateTime.now().plusSeconds(200), 30);
		Task a5 = new Task("5", LocalDateTime.now().plusSeconds(100),LocalDateTime.now().plusSeconds(200), 30);
		Task a6 = new Task("6", LocalDateTime.now().plusSeconds(100),LocalDateTime.now().plusSeconds(200), 30);
		Task a1 = new Task("ABOBA", LocalDateTime.now().plusSeconds(9999));
		a1.setActive(true);
		a2.setActive(true);
		a3.setActive(true);
		a4.setActive(true);
		a5.setActive(true);
		a6.setActive(true);
		AbstractTaskList arr =TaskListFactory.createTaskList(ListTypes.types.ARRAY);
		arr.add(a1);
		arr.add(a2);
		arr.add(a3);
		arr.add(a4);
		arr.add(a5);
		arr.add(a6);
		Iterable<Task> a = new Iterable<Task>() {
			@Override
			public Iterator<Task> iterator() {
				return arr.iterator();
			}
		};

//		Iterable<Task> b = Tasks.incoming(a,LocalDateTime.now(),LocalDateTime.now().plusSeconds(1500));
//		for(Task temp: b){
//			System.out.println(temp);
//		}
		SortedMap<LocalDateTime, Set<Task>> map = Tasks.calendar(a,LocalDateTime.now().plusSeconds(5000),LocalDateTime.now().plusSeconds(150000));
		for(Map.Entry<LocalDateTime, Set<Task>> ab : map.entrySet()){
			System.out.println("Key = " + ab.getKey() + "Value = " + ab.getValue() + '\n');
		}
	}
}
