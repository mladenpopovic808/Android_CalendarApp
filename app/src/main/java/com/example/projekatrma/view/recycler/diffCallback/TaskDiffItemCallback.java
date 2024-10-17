package com.example.projekatrma.view.recycler.diffCallback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.projekatrma.models.Task;

public class TaskDiffItemCallback extends DiffUtil.ItemCallback<Task>{
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.equals(newItem);
    }
}
