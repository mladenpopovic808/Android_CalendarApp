package com.example.projekatrma.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.projekatrma.R;
import com.example.projekatrma.models.Day;
import com.example.projekatrma.models.Task;
import com.example.projekatrma.viewModels.CalendarViewModel;
import com.example.projekatrma.viewModels.TasksViewModel;

import timber.log.Timber;

public class ShowTaskFragment extends Fragment {

    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewTime;
    private TextView header;
    private Button buttonEdit;
    private Button buttonDelete;
    private TasksViewModel taskViewModel;

    private CalendarViewModel calendarViewModel;
    public ShowTaskFragment() {
        super(R.layout.fragment_show_task);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskViewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
        calendarViewModel=new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);

        init(view);

    }
    private void init(View view){
        initView(view);
        initListeners();

    }
    private void initListeners() {
    buttonEdit.setOnClickListener(v-> {

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.outerFragmentFcv, new ConfigureTaskFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    });

    buttonDelete.setOnClickListener(v->{
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete task");
        builder.setMessage("Are you sure you want to delete this task?");
        builder.setPositiveButton("Yes", (dialog, which) -> {

            Task task=taskViewModel.getSelectedTask().getValue();
            taskViewModel.removeTask(task.getId());
            task.getDay().getTasks().remove(task);
            calendarViewModel.getSelectedDay().getValue().checkMaxPriority();
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.outerFragmentFcv, new DailyPlanFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

    });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();


    });
    }

    private void initView(View view){
        textViewTitle = view.findViewById(R.id.titleShowTask);
        textViewDescription = view.findViewById(R.id.descriptionShowTask);
        textViewTime = view.findViewById(R.id.timeShowTask);
        header = view.findViewById(R.id.headerShowTask);
        buttonEdit = view.findViewById(R.id.editShowTask);
        buttonDelete = view.findViewById(R.id.deleteShowTask);

        textViewTitle.setText(taskViewModel.getSelectedTask().getValue().getTitle());
        textViewDescription.setText(taskViewModel.getSelectedTask().getValue().getDescription());
        textViewTime.setText(taskViewModel.getSelectedTask().getValue().getTime());
        Day day=taskViewModel.getSelectedTask().getValue().getDay();
        String headerText=day.getDayNumber()+""+day.getMonth()+","+"2023";
        header.setText(headerText);


    }

}






















