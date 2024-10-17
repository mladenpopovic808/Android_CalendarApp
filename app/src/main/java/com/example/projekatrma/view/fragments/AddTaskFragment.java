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

public class AddTaskFragment extends Fragment {

    private Button createTaskButton;
    private Button cancelTaskButton;
    private EditText taskDescription;
    private EditText timeFrom;
    private EditText timeTo;
    private EditText taskTitle;
    private RadioGroup radioGroup;

    private CalendarViewModel calendarViewModel;
    private TasksViewModel tasksViewModel;

    public AddTaskFragment() {
        super(R.layout.fragment_add_task);
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
        TextView taskHeader = view.findViewById(R.id.headerTextView);
        radioGroup = view.findViewById(R.id.radioGroup);
        createTaskButton = view.findViewById(R.id.createTastButton);
        cancelTaskButton = view.findViewById(R.id.cancelButton);
        taskDescription = view.findViewById(R.id.editTextDescription);
        timeFrom = view.findViewById(R.id.editTextTime1);
        taskTitle = view.findViewById(R.id.editTextTitle);
        timeTo = view.findViewById(R.id.editTextTime2);
        taskHeader.setText(calendarViewModel.getSelectedDay().getValue().getMonth()+" "+calendarViewModel.getSelectedDay().getValue().getDayNumber()+" 2023");
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
    private boolean doesTitleAlreadyExists(String title){
        for(Task task:calendarViewModel.getSelectedDay().getValue().getTasks()){
            if(task.getTitle().equalsIgnoreCase(title)){
                return true;
            }
        }
        return false;
    }

    private void initListeners(){

        createTaskButton.setOnClickListener(v -> {
            String title=taskTitle.getText().toString();
            String description=taskDescription.getText().toString();
            String startTime=timeFrom.getText().toString();
            String endTime=timeTo.getText().toString();
            if(title.isEmpty() || description.isEmpty() || startTime.isEmpty() || endTime.isEmpty()){
                Toast.makeText(getContext(),"Popunite sva polja",Toast.LENGTH_SHORT).show();
                return;
            }
            if(doesTitleAlreadyExists(title)){
                Toast.makeText(getContext(),"Vec postoji aktivnost sa tim nazivom",Toast.LENGTH_SHORT).show();
                return;
            }
            startTime=formatTime(startTime);
            endTime=formatTime(endTime);
            if(startTime==null || endTime==null){
                Toast.makeText(getContext(), "Invalid time", Toast.LENGTH_SHORT).show();
                return;
            }


            //kreirati aktiviti.
            Priority priority;
            Toast.makeText(getContext(),radioGroup.getCheckedRadioButtonId()+"",Toast.LENGTH_SHORT).show();
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.radioButton1:
                    priority=Priority.LOW;
                    break;
                case R.id.radioButton2:
                    priority=Priority.MEDIUM;
                    break;
                case R.id.radioButton3:
                    priority=Priority.HIGH;
                    break;
                default:
                    priority=Priority.NONE;
                    break;
            }
            Task task=new Task(title,description,startTime,endTime,priority,false);
            tasksViewModel.addTask(task);
            task.setDay(calendarViewModel.getSelectedDay().getValue());
            //calendarViewModel.getSelectedDay().getValue().addTask(task);
            calendarViewModel.getSelectedDay().getValue().checkMaxPriority();

            FragmentTransaction fragmentTransaction=getParentFragment().getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.outerFragmentFcv,new DailyPlanFragment());
            fragmentTransaction.commit();

        });


        cancelTaskButton.setOnClickListener(v -> getParentFragment().getChildFragmentManager().popBackStack());





    }

}

















