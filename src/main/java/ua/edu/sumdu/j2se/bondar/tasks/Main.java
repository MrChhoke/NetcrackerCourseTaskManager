package ua.edu.sumdu.j2se.bondar.tasks;


import java.util.Iterator;

public class Main {

	public static void main(String[] args) throws CloneNotSupportedException {
		Task a1 = new Task("A",10);
		Task a2 = new Task("B",10);
		Task a4 = new Task("C",10);
		Task a3 = new Task("D",10);
		LinkedTaskList list = new LinkedTaskList();
		list.add(a1);
		list.add(a4);

		LinkedTaskList list1 = list.clone();
		list.remove(list.getTask(0));
		list.remove(list.getTask(0));
		int size = list1.size();
		for(int i = 0; i < size; i++){
			System.out.println(list1.getTask(0).getTitle());
			list1.remove(list1.getTask(0));
		}

	}
}
