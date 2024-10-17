package com.example.projekatrma.models;

import androidx.annotation.NonNull;
import com.example.projekatrma.enums.Priority;
import java.util.ArrayList;
import java.util.List;


public class Day {

    private int id;

    private int dayNumber;
    private String month;

    private Priority maxPriority;

    private List<Task> tasks;


    public Day(int id,int dayNumber,String month) {
        this.id = id;
        this.dayNumber = dayNumber;
        this.month = month;
        tasks=new ArrayList<>();
        maxPriority=Priority.NONE;

    }
    public int getMonthIndex(){
        switch (month){
            case "January":
                return 0;
            case "February":
                return 1;
            case "March":
                return 2;
            case "April":
                return 3;
            case "May":
                return 4;
            case "June":
                return 5;
            case "July":
                return 6;
            case "August":
                return 7;
            case "September":
                return 8;
            case "October":
                return 9;
            case "November":
                return 10;
            case "December":
                return 11;
            default:
                return 0;
        }
    }
    @NonNull
    @Override
    public String toString() {

        return "Day{" +
                "id=" + id +
                ", dayNumber=" + dayNumber +
                ", month='" + month + '\'' +
                ", maxPriority=" + maxPriority +
                ", tasks=" + tasks +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
          return id == day.id && dayNumber == day.dayNumber && month.equals(day.month) && maxPriority == day.maxPriority && tasks.equals(day.tasks);
    }
    public void updateMaxPriority(Priority priority){
        if(priority.ordinal()>maxPriority.ordinal()){
            maxPriority=priority;

        }
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
        task.setDay(this);
        updateMaxPriority(task.getPriority());
    }

    public void checkMaxPriority(){
        maxPriority=Priority.NONE;
        for(Task task:tasks){
            updateMaxPriority(task.getPriority());
        }
    }


    public int getId() {
        return id;
    }

    public String getMonth() {
        return month;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public Priority getMaxPriority() {
        return maxPriority;
    }

}
