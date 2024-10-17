package com.example.projekatrma.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekatrma.R;
import com.example.projekatrma.enums.Priority;
import com.example.projekatrma.models.Task;
import com.example.projekatrma.viewModels.CalendarViewModel;
import com.example.projekatrma.viewModels.TasksViewModel;

import timber.log.Timber;

public class ConfigureTaskFragment extends Fragment {

    private TextView taskHeader;
    private Button saveTaskButton;

    private Button cancelTaskButton;
    private EditText taskDescription;
    private EditText timeFrom;
    private EditText timeTo;
    private EditText taskTitle;
    private RadioGroup radioGroup;
    private CalendarViewModel calendarViewModel;
    private TasksViewModel tasksViewModel;


    public ConfigureTaskFragment() {
        super(R.layout.fragment_configure_task);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarViewModel=new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        tasksViewModel=new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
        init(view);
    }
    private void init(View view){
        initView(view);
        initListeners();
    }
    private void initView(View view){
        taskHeader = view.findViewById(R.id.headerConfig);
        radioGroup = view.findViewById(R.id.radioGroupConfigure);
        saveTaskButton = view.findViewById(R.id.saveTaskConfig);
        cancelTaskButton = view.findViewById(R.id.cancelButton);
        taskDescription = view.findViewById(R.id.descriptionConfig);
        timeFrom = view.findViewById(R.id.time1Config);
        timeTo = view.findViewById(R.id.time2Config);
        taskTitle = view.findViewById(R.id.titleConfigure);

        taskHeader.setText(calendarViewModel.getSelectedDay().getValue().getMonth()+" "+calendarViewModel.getSelectedDay().getValue().getDayNumber()+" "+"2023");
        taskDescription.setText(tasksViewModel.getSelectedTask().getValue().getDescription());
        timeFrom.setText(tasksViewModel.getSelectedTask().getValue().getStartTime());
        timeTo.setText(tasksViewModel.getSelectedTask().getValue().getEndTime());
        taskTitle.setText(tasksViewModel.getSelectedTask().getValue().getTitle());
        switch (tasksViewModel.getSelectedTask().getValue().getPriority()){
            case LOW:
                radioGroup.check(R.id.radioButton1Config);
                break;
            case MEDIUM:
                radioGroup.check(R.id.radioButton2Config);
                break;
            case HIGH:
                radioGroup.check(R.id.radioButton3Config);
                break;
        }
    }

    private boolean doesTitleAlreadyExists(String title){
        for(Task task:calendarViewModel.getSelectedDay().getValue().getTasks()){
            if(task.equals(tasksViewModel.getSelectedTask().getValue())){
                continue;
            }
            if(task.getTitle().equalsIgnoreCase(title)){
                return true;
            }
        }
        return false;
    }

    private boolean validateData(String title,String description,String from,String to){
        if(title.isEmpty() || description.isEmpty() || from.isEmpty() || to.isEmpty()){
            Toast.makeText(getContext(),"Please fill all fields",Toast.LENGTH_SHORT).show();
            return false;
        }
        from=formatTime(from);
        to=formatTime(to);
        if(from==null || to==null){
            Toast.makeText(getContext(),"Invalid time format",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(doesTimeAlreadyExists(from,to)){
            Toast.makeText(getContext(),"Time already exists",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(doesTitleAlreadyExists(title)){
            Toast.makeText(getContext(),"Title already exists",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void initListeners() {
        saveTaskButton.setOnClickListener(v -> {
            String title=taskTitle.getText().toString();
            String description=taskDescription.getText().toString();
            String from=timeFrom.getText().toString();
            String to=timeTo.getText().toString();

            if(validateData(title,description,from,to)==false){
                return;
            }
            Priority priority=getPriorityFromRadioGroup();

            Task task=tasksViewModel.getSelectedTask().getValue();
            task.setTitle(title);
            task.setDescription(description);
            task.setStartTime(from);
            task.setEndTime(to);
            task.setPriority(priority);
            tasksViewModel.updateSelectedTask(task);
            calendarViewModel.getSelectedDay().getValue().checkMaxPriority();

           // tasksViewModel.addTask(task);
            Toast.makeText(getContext(),"Task changed",Toast.LENGTH_SHORT).show();
            FragmentTransaction fragmentTransaction=getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.outerFragmentFcv,new DailyPlanFragment());
            fragmentTransaction.commit();
        });

    }
    private Priority getPriorityFromRadioGroup(){
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.radioButton1Config:
                return Priority.LOW;
            case R.id.radioButton2Config:
                return Priority.MEDIUM;
            case R.id.radioButton3Config:
                return Priority.HIGH;
        }
        return null;
    }
    private boolean doesTimeAlreadyExists(String from,String to){

        for(int i=0;i<tasksViewModel.getTasks().getValue().size();i++){
            if(tasksViewModel.getTasks().getValue().get(i).equals(tasksViewModel.getSelectedTask().getValue())){
                continue;
            }

            //Timber.e(tasksViewModel.getTasks().getValue().get(i).getTime()+" "+tasksViewModel.getTasks().getValue().get(i).getEndTime());
            if(tasksViewModel.getTasks().getValue().get(i).getStartTime().equals(from) && tasksViewModel.getTasks().getValue().get(i).getEndTime().equals(to)){
                Timber.e("Time already exists");
                return true;
            }
        }
        return false;
    }

    private String formatTime(String time){
        if(time.contains(":")){
            String[] parts=time.split(":");
            String hour=parts[0];


            if(Integer.parseInt(hour)>24 || Integer.parseInt(hour)<0){
                return null;
            }
            String minute=parts[1];
            if(Integer.parseInt(minute)>60 || Integer.parseInt(minute)<0){
                return null;
            }
            if(hour.length()==1){
                hour="0"+hour;
                Timber.e("Dodajem 0"+" "+hour);
            }
            if(minute.length()==1){
                minute="0"+minute;
            }
            return hour+":"+minute;
        }
        else{
            if(time.length()==1){
                time="0"+time;
            }
            return time+":00";
        }
    }


}













