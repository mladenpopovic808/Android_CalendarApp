package com.example.projekatrma.view.recycler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projekatrma.R;
import com.example.projekatrma.models.Day;

import java.util.function.Consumer;

import timber.log.Timber;

public class DayAdapter extends ListAdapter<Day, DayAdapter.DayViewHolder> {

    private final Consumer<Day> onDayClick;

    public DayAdapter(@NonNull DiffUtil.ItemCallback<Day> diffCallback, Consumer<Day> onDayClick) {
        super(diffCallback);
        this.onDayClick = onDayClick;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);

        return new DayViewHolder(view,parent.getContext(),position->{
            Day day = getItem(position);
            onDayClick.accept(day);

        });
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day day = getItem(position);
        holder.bind(day);
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {


        public DayViewHolder(@NonNull View itemView,Context context, Consumer<Integer> onDayClick) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if(getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                    onDayClick.accept(getBindingAdapterPosition());
            });
        }

        public void bind(Day day) {
            //prepoznaje dan normalno,samo treba povezati sa view-om.
            TextView textView=itemView.findViewById(R.id.calendarItem);
            textView.setText(String.valueOf(day.getDayNumber()));
            switch(day.getMaxPriority()){
                case LOW:
                    textView.setBackgroundColor(Color.rgb(0,255,0));
                    break;
                case MEDIUM:
                    textView.setBackgroundColor(Color.rgb(255,255,0));
                    break;
                case HIGH:
                    textView.setBackgroundColor(Color.rgb(255,0,0));
                    break;
                default:
                    textView.setBackgroundColor(Color.rgb(255,255,255));
                    break;
            }

        }
    }
}

