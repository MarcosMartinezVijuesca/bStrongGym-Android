package com.svalero.bstronggym.contract;

import com.svalero.bstronggym.model.Workout;
import java.util.List;

public interface WorkoutContract {

    interface View {
        void onWorkoutsLoaded(List<Workout> workouts);
        void onWorkoutSaved();
        void onWorkoutDeleted();
        void onError(String message);
    }

    interface Presenter {
        void loadWorkouts();
        void saveWorkout(Workout workout);
        void updateWorkout(Workout workout);
        void deleteWorkout(Workout workout);
    }
}
