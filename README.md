# Task Planner Application

## Project Description
This application is designed to help users **plan and manage their daily tasks** efficiently, especially during busy periods like exam weeks. Users can prioritize tasks, view schedules by day, and organize their responsibilities with ease. Tasks are color-coded based on priority:

- **Red**: Highest priority  
- **Yellow/Orange**: Medium priority  
- **Green**: Lowest priority  

The app features smooth navigation, data persistence, and intuitive design, enabling users to track and manage their schedules effectively.

---

## Features

### 1. Splash Screen
- Checks if the user is already logged in.  
- Redirects to the **Main Screen** if logged in, or the **Login Screen** otherwise.  
- Displays a meaningful image, such as a logo.

### 2. Login Screen
- Users must enter **username**, **email**, and **password**.
- **Validation**:  
   - Email must follow proper format.  
   - Password: At least 5 characters, one uppercase letter, and one digit. Special characters are disallowed.  
   - Displays error messages using **Toast**, hidden **TextView**, or **Snackbar**.
- Credentials are checked against a stored resource file.

### 3. Main Screen
The main screen features a **Bottom Navigation Menu** to switch between:  
1. **Calendar View**  
2. **Daily Task Overview**  
3. **User Profile**

---

### 4. Calendar View
- Displays a **scrollable calendar** (Grid RecyclerView).  
- Each day is color-coded based on the **highest priority** task.  
- Clicking on a day opens a detailed list of tasks for that day.  
- The current month is displayed and updated dynamically as users scroll.

---

### 5. Daily Task Overview
- Displays **all tasks for the selected day**, sorted by time.  
- Features:  
   - **Past Tasks**: Option to show/hide completed tasks, with gray background for visibility.  
   - **Search**: Filter tasks by name.  
   - **Sort**: Sort tasks by priority.  
- **Task Actions**:  
   - **View Details**: Open detailed task view.  
   - **Edit**: Modify task information.  
   - **Delete**: Confirm deletion via Snackbar.
- **Floating Action Button** to add new tasks.

---

### 6. Task Details
- Displays full information about a task.  
- Users can:  
   - Navigate to the **previous** or **next task** by swiping left/right.  
   - Edit or delete the task, with a confirmation step for deletion.

---

### 7. Add New Task
- Allows users to input:  
   - **Task name**  
   - **Time**  
   - **Description**  
- Ensures no overlapping tasks exist for the same time slot.

---

### 8. Edit Task
- Opens a pre-filled form where users can modify task details.  
- Prevents overlapping time slots and displays feedback via Toast/Snackbar.  
- Successfully updated tasks trigger a Toast message and refresh task views.

---

### 9. User Profile
- Displays user information.  
- Features:  
   - **Change Password**: Validates and updates the new password.  
   - **Log Out**: Redirects users back to the Login Screen.

---

## Technologies

- **ViewModel** and **LiveData**: Manage UI-related data efficiently.  
- **SharedPreferences**: Persist user information, such as login credentials.  
- **Grid RecyclerView**: For displaying the calendar view.  
- **Snackbar/Toast**: User feedback for actions like errors, confirmations, and success.  

---

## Additional Notes
- Preloaded tasks are initialized within the **ViewModel** to simplify testing.  
- Task data does not persist after app restarts, as tasks are managed solely in ViewModels.  

---

This app provides a clean, intuitive user experience for **task scheduling and prioritization**, helping users stay organized and focused on their goals.
