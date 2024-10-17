package com.example.projekatrma.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projekatrma.R;
import com.example.projekatrma.enums.Priority;
import com.example.projekatrma.models.Task;
import java.util.function.Consumer;

import timber.log.Timber;

public class TaskAdapter extends ListAdapter<Task,TaskAdapter.TaskViewHolder> {
    private final Consumer<Task> onTaskClick;
    private final Consumer<Task> deleteConsumer;
    private final Consumer<Task> configConsumer;


    public TaskAdapter(Consumer<Task>configListener, Consumer<Task>deleteListener, @NonNull DiffUtil.ItemCallback<Task> diffCallback, Consumer<Task>onTaskClick){
        super(diffCallback);
        this.onTaskClick = onTaskClick;
        this.deleteConsumer = deleteListener;
        this.configConsumer = configListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false),parent.getContext(), position->{
            Task task = getItem(position);
            onTaskClick.accept(task);
        },position->{
            Task task = getItem(position);
            deleteConsumer.accept(task);
        },position->{
            Task task = getItem(position);
            configConsumer.accept(task);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task=getItem(position);
        holder.bind(task);
    }
     static class TaskViewHolder extends RecyclerView.ViewHolder{
        private final Context context;
        private final Consumer<Integer> deleteC;
        private final Consumer<Integer> configC;

         public TaskViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onTaskClick, Consumer<Integer> deleteC, Consumer<Integer> configC) {
             super(itemView);
             this.context=context;
             this.deleteC=deleteC;
             this.configC=configC;
             itemView.setOnClickListener(v -> {
                 if(getBindingAdapterPosition() != RecyclerView.NO_POSITION){
                     onTaskClick.accept(getBindingAdapterPosition());
                 }
             });
         }


         public void bind(Task task){
             ConstraintLayout layout = itemView.findViewById(R.id.taskItemLayout);
             ImageView icon = itemView.findViewById(R.id.taskPicture);
             TextView time= itemView.findViewById(R.id.taskTime);
             TextView title = itemView.findViewById(R.id.taskTitle);
             ImageButton deleteButton = itemView.findViewById(R.id.buttonDelete);
             ImageButton configureButton= itemView.findViewById(R.id.buttonConfigure);

             Priority priority = task.getPriority();


                 if(task.isStatus()){ //ako je gotov task
                     layout.setBackgroundColor(context.getResources().getColor(R.color.gray));
                     if(priority == Priority.LOW){
                         //load low.png
                         Glide.with(context).load(R.drawable.low).into(icon);
                 }
                        else if(priority == Priority.MEDIUM){
                            Glide.with(context).load(R.drawable.medium).into(icon);
                        }
                        else if(priority == Priority.HIGH){
                            Glide.with(context).load(R.drawable.high).into(icon);
                        }
                    }
                 else{

                    if(priority == Priority.LOW){
                        layout.setBackgroundColor(context.getResources().getColor(R.color.green));
                        //load low.png
                        Glide.with(context).load(R.drawable.low).into(icon);

                    }else if(priority == Priority.MEDIUM){
                        layout.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                        Glide.with(context).load(R.drawable.medium).into(icon);
                    }else if(priority == Priority.HIGH){
                        layout.setBackgroundColor(context.getResources().getColor(R.color.red));
                        Glide.with(context).load(R.drawable.high).into(icon);

                    }else if (priority == Priority.NONE){
                        icon.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                        layout.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
                    }
                 }
             time.setText(task.getTime());
             title.setText(task.getTitle());
             deleteButton.setOnClickListener(v -> {
                 if(getBindingAdapterPosition() != RecyclerView.NO_POSITION){
                     deleteC.accept(getBindingAdapterPosition());
                 }
             });
             configureButton.setOnClickListener(v -> {
                    if(getBindingAdapterPosition() != RecyclerView.NO_POSITION){
                        configC.accept(getBindingAdapterPosition());
                    }
                });

             //Da li ovde treba da setujem listenere za buttone?,da prosledim adapteru 2 listenera
             //pa da ih ovde setujem?


         }

     }
}
















