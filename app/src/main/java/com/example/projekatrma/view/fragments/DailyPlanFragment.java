package com.example.projekatrma.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekatrma.R;
import com.example.projekatrma.enums.Priority;
import com.example.projekatrma.models.Task;
import com.example.projekatrma.view.recycler.adapter.TaskAdapter;
import com.example.projekatrma.view.recycler.diffCallback.TaskDiffItemCallback;
import com.example.projekatrma.viewModels.CalendarViewModel;
import com.example.projekatrma.viewModels.TasksViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.function.Consumer;

import timber.log.Timber;

public class DailyPlanFragment extends Fragment {
    //kaze da kad selektujem,u calendarViewModelu setujem selektovan dan,pa da ovde prikazem sve taskove tog dana.
    private TextView textViewMonth;
    private CheckBox checkBox;
    private EditText editText;
    private Button buttonLow;
    private Button buttonMedium;
    private Button buttonHigh;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private CalendarViewModel calendarViewModel;
    private TasksViewModel tasksViewModel;
    private TaskAdapter taskAdapter;

    public DailyPlanFragment() {
        super(R.layout.fragment_daily_plan);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarViewModel=new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        //u calendar fragmentu smo upisivali u viewModel nad aktivitijem,sada isto uzimamo taj view model
        //nad aktivitijem,jer se oni nalaze u istom aktivitiju.
        tasksViewModel=new ViewModelProvider(requireActivity()).get(TasksViewModel.class);

        //setujemo taskove izabranog dana.
//        try {
//        tasksViewModel.setTasks((ArrayList<Task>) calendarViewModel.getSelectedDay().getValue().getTasks());
//        Toast.makeText(getContext(),tasksViewModel.getTasks().getValue().toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT).show();
//        }catch (NullPointerException e){
//            Toast.makeText(getContext(), "Usao u excp", Toast.LENGTH_SHORT).show();
//        }
        init(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        tasksViewModel.setTaskList((ArrayList<Task>) calendarViewModel.getSelectedDay().getValue().getTasks());
        //tasksViewModel.setTasks((ArrayList<Task>) calendarViewModel.getSelectedDay().getValue().getTasks());
        textViewMonth.setText(calendarViewModel.getSelectedDay().getValue().getMonth()+" "+calendarViewModel.getSelectedDay().getValue().getDayNumber()+" 2023");
    }
    private void init(View view){
        initView(view);
        initAdapter();
        initRecycler();
        initListeners();
        initObservers();
    }
    private void initAdapter(){
        Consumer<Task> configConsumer=initEditTaskConsumer();
        Consumer<Task> deleteConsumer=initDeleteTaskConsumer();
        Consumer<Task> showTaskConsumer=initShowTaskConsumer();
        taskAdapter=new TaskAdapter(configConsumer,deleteConsumer,new TaskDiffItemCallback(),showTaskConsumer);
        //treba ti da kada obrises listener,da se azurira day i da mu se azirura max priority.
    }
    private Consumer<Task> initShowTaskConsumer(){
        return task -> {
            //otvara se aktiviti za editovanje taska.
            tasksViewModel.setSelectedTask(task);
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.outerFragmentFcv, new ShowTaskFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        };
    }


    private Consumer<Task> initEditTaskConsumer(){
        return task -> {
            //otvara se aktiviti za editovanje taska.
            tasksViewModel.setSelectedTask(task);
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.outerFragmentFcv, new ConfigureTaskFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        };
    }
    private Consumer<Task> initDeleteTaskConsumer(){
        return task -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete task");
            builder.setMessage("Are you sure you want to delete this task?");
            builder.setPositiveButton("Yes", (dialog, which) -> {

                tasksViewModel.removeTask(task.getId());
                task.getDay().getTasks().remove(task);
                calendarViewModel.getSelectedDay().getValue().checkMaxPriority();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
        };
    }
    private void initObservers(){
        tasksViewModel.getTasks().observe(getViewLifecycleOwner(),tasks -> taskAdapter.submitList(tasks));
    }
    private void initListeners(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                tasksViewModel.filterTasks(s.toString());
            }
        });
        buttonLow.setOnClickListener(v -> tasksViewModel.filterTasksByPriority(Priority.LOW));
        buttonMedium.setOnClickListener(v -> tasksViewModel.filterTasksByPriority(Priority.MEDIUM));
        buttonHigh.setOnClickListener(v -> tasksViewModel.filterTasksByPriority(Priority.HIGH));

        floatingActionButton.setOnClickListener(v -> {
            //otvara se aktiviti za dodavanje novog taska.
        });
        checkBox.setOnClickListener(v -> tasksViewModel.filterFinishedTasks(checkBox.isChecked()));
        floatingActionButton.setOnClickListener(v -> {
            // Get the outer fragment
            Fragment outerFragment = getParentFragment();

            if (outerFragment != null) {
                // Get the fragment manager for the outer fragment
                FragmentManager fragmentManager = outerFragment.getChildFragmentManager();

                // Replace the current fragment with a new fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.outerFragmentFcv, new AddTaskFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }






        });

    }
    private void initRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdapter);
    }
    private void initView(View view){
        textViewMonth=view.findViewById(R.id.textViewMonth);
        checkBox=view.findViewById(R.id.checkBox);
        editText=view.findViewById(R.id.editTextSearch);
        buttonLow=view.findViewById(R.id.buttonLow);
        buttonMedium=view.findViewById(R.id.buttonMedium);
        buttonHigh=view.findViewById(R.id.buttonHigh);
        recyclerView=view.findViewById(R.id.recyclerView);
        floatingActionButton=view.findViewById(R.id.floatingActionButton);




    }
}























