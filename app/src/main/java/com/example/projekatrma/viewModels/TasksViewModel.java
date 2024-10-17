package com.example.projekatrma.viewModels;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.projekatrma.enums.Priority;
import com.example.projekatrma.models.Task;
import com.example.projekatrma.view.activities.MainActivity;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import timber.log.Timber;

public class TasksViewModel extends ViewModel {

    private MutableLiveData<List<Task>> tasks = new MutableLiveData<>();

    private MutableLiveData<Task> selectedTask=new MutableLiveData<>();
    private ArrayList<Task>taskList=new ArrayList<>();
    

    public TasksViewModel() {
        ArrayList<Task> list = new ArrayList<>();
        this.tasks.setValue(list);
        Task task=new Task();
        selectedTask.setValue(task);
    }

    public void filterTasksByPriority(Priority priority){
        List<Task> filteredList = taskList.stream().filter(task -> task.getPriority()==priority).collect(Collectors.toList());
        tasks.setValue(filteredList);
    }
    public void filterTasks(String filter){
        System.out.println("Ovo je pocetna lista"+taskList.toString());
        List<Task> filteredList = taskList.stream().filter(task -> task.getTitle().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());
        Toast.makeText(MainActivity.viewPager.getContext(), filteredList.toString(), Toast.LENGTH_SHORT).show();
        tasks.setValue(filteredList);
    }
    public void filterFinishedTasks(boolean finished){
        List<Task> filteredList = taskList.stream().filter(task -> task.isStatus()==finished).collect(Collectors.toList());
        tasks.setValue(filteredList);
    }
    public void updateSelectedTask(Task task){
        selectedTask.setValue(task);
    }

    public void addTask(Task task){
        taskList.add(task);
        ArrayList<Task> list = new ArrayList<>(taskList);
        sortListByTime(list);

        tasks.setValue(list);
    }
    public void removeTask(int id){
        //koristimo optional da bi mogli da proverimo da li je prazan
        //U sustini,bolje je ovako da se koristi nego da pitamo obican objekat da li je null
        Optional<Task> taskObject=taskList.stream().filter(task -> task.getId()==id).findFirst();
        if(taskObject.isPresent()){
            taskList.remove(taskObject.get());
            ArrayList<Task> list = new ArrayList<>(taskList);
            setTasks(list);//sortira se
            taskList.remove(taskObject.get());
            //mora ovako da se radi,nece da prepozna da je obrisano.
        }
    }
    private void sortListByTime(List<Task> list){
        list.sort(new Comparator());
        tasks.setValue(list);
    }

    private class Comparator implements java.util.Comparator<Task> { @Override

    public int compare(Task o1, Task o2) {
        LocalTime startTime1 = LocalTime.parse(o1.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime startTime2 = LocalTime.parse(o2.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
        int startTimeComparison = startTime1.compareTo(startTime2);
        if (startTimeComparison != 0) {
            return startTimeComparison;
        }
        // If start times are equal, compare end times
        LocalTime endTime1 = LocalTime.parse(o1.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime2 = LocalTime.parse(o2.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
        return endTime1.compareTo(endTime2);
    }
    }


    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> list) {

        sortListByTime(list);
        this.tasks.setValue(list);

    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
        List<Task>activeTasks=taskList.stream().filter(task -> task.isStatus()==false).collect(Collectors.toList());
        //this.taskList = taskList;
        sortListByTime(activeTasks);
        tasks.setValue(activeTasks);
    }

    public MutableLiveData<Task> getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask.setValue(selectedTask);
    }
}

