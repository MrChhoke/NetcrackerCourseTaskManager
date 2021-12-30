package ua.edu.sumdu.j2se.bondar.tasks.controller;


import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Controller controller = new ControllerImpl();
		controller.start();
	}
}
