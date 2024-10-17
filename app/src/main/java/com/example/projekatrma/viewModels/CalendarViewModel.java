package com.example.projekatrma.viewModels;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projekatrma.enums.Priority;
import com.example.projekatrma.models.Day;
import com.example.projekatrma.models.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import timber.log.Timber;


public class CalendarViewModel extends ViewModel {
    private MutableLiveData<List<Day>> daysMutable = new MutableLiveData<>();
    private MutableLiveData<Day> selectedDay = new MutableLiveData<>();
    private List<Day> dayList;

    public CalendarViewModel() {
        dayList = new ArrayList<>();

        // Get the current year
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String previousMonth = "dummy";
        int k = 0;
        // Loop through each day in the year
        for (int i = 1; i <= 365; i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_YEAR, i);

            // Create a new Day object with the date and default priority
            //generate random priority %3


            //get month name
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.getDefault());
            if (!previousMonth.equals(month)) {
                previousMonth = month;
                k = 1;
            }

            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            //Rucno generisanje radi lakseg testiranja
            Day day = new Day(i, k % (daysInMonth + 1), month);

            if (month.equals("April")) {
                for (int j = 0; j < 3; j++) {
                    String startTime = formatTime(9 + j);
                    String endTime = formatTime(10 + j);
                    Priority priority1 = getRandomPriority();
                    Task task = new Task("Title taska " + j, "Opis taska", startTime, endTime, priority1, false);
                    day.addTask(task);
                }
            }
            dayList.add(day);
            k++;
        }
        daysMutable.setValue(dayList);
    }





    private String formatTime(int time) {
        String returnStr = Integer.toString(time);

        if (returnStr.length() == 1) {
            returnStr = "0" + returnStr;
        }
        return returnStr + ":00";
    }

        private Priority getRandomPriority(){
           //random between 1 and 3
            Random random = new Random();
            int randomInt = random.nextInt(3) + 1;
            switch (randomInt){
                case 1:return Priority.LOW;
                case 2:return Priority.MEDIUM;
                case 3:return Priority.HIGH;
                default:return Priority.NONE;
            }

        }
        public LiveData<List<Day>> getDays() {
            return daysMutable;
        }
        public List<Day> getDayList() {
            return dayList;
        }


        public MutableLiveData<Day> getSelectedDay() {
            return selectedDay;
        }
        public void setSelectedDay(Day selectedDay) {this.selectedDay.setValue(selectedDay);
        }

}

