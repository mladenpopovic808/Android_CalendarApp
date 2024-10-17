package com.example.projekatrma.models;

import com.example.projekatrma.R;
import com.example.projekatrma.enums.Priority;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class Task {
    private int id;
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private Priority priority;
    private String picture;
    private boolean status; // 0 - not done, 1 - done
    private Day day;
    private static int taskCounter=0;
    public Task() {
       // this.id = taskCounter++;
        this.title = "Title";
        this.description = "defaultni opis";
        this.startTime = "00:00";
        this.endTime = "00:00";
        this.priority = Priority.NONE;
        this.status = false;
        picture= R.drawable.logo+"";
    }

    private String formatSystemTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        return currentTime;

    }
    private int getCurrentMonthIndex(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        int currentMonthIndex = Integer.parseInt(sdf.format(new Date()));
        return currentMonthIndex-1;
    }
    private int getCurrentDayNumber(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        int currentDayNumber = Integer.parseInt(sdf.format(new Date()));
        return currentDayNumber;
    }


    public Task(String title, String description, String startTime, String endTime, Priority priority, boolean status) {
        this.id = taskCounter++;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        picture= R.drawable.logo+"";

    }


    public Task(Day selectedDay, String title, String description, String startTime, String endTime, Priority priority, boolean i) {
        this.id = taskCounter++;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.status = i;
        picture= R.drawable.logo+"";

    }
    public void isTaskOver(){

        if(day.getMonthIndex()==getCurrentMonthIndex()){
            if(day.getDayNumber()==getCurrentDayNumber()){
                if(endTime.compareTo(formatSystemTime())==0){
                    status=false;
                }else if(endTime.compareTo(formatSystemTime())<0){
                    status=true;
                }else{
                    status=false;
                }
            }else if(day.getDayNumber()<getCurrentDayNumber()){
                status=true;
            }else{
                status=false;

            }
        }else if(day.getMonthIndex()<getCurrentMonthIndex()){
            status=true;
        }else {
            status=false;
        }


    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", day=" + day.getDayNumber()+" "+day.getMonth() +
                ", priority=" + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && status == task.status && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(startTime, task.startTime) && Objects.equals(endTime, task.endTime) && priority == task.priority && Objects.equals(day, task.day);
    }


    public String getTime(){
        return startTime+" - "+endTime;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setDay(Day day) {
        this.day = day;
        isTaskOver();//setuje status
    }

    public Priority getPriority() {

        return priority;
    }

    public Day getDay() {
        return day;
    }



    public boolean isStatus() {
        return status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
        day.updateMaxPriority(priority);

    }
    public void setEndTime(String endTime) {
        isTaskOver();//setuje status

        this.endTime = endTime;
    }
    public void setStartTime(String startTime) {
        isTaskOver();//setuje status
        this.startTime = startTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
