package ua.edu.sumdu.j2se.bondar.tasks.controller;

import ua.edu.sumdu.j2se.bondar.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bondar.tasks.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Controller {
    AbstractTaskList list = null;
    View view = null;


    void start();
    void saveTasks();
    void readTasks();
}
