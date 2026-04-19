package com.svalero.bstronggym.presenter;

import android.content.Context;

import com.svalero.bstronggym.contract.WorkoutContract;
import com.svalero.bstronggym.model.AppDatabase;
import com.svalero.bstronggym.model.Workout;
import com.svalero.bstronggym.model.WorkoutDao;

import java.util.List;

public class WorkoutPresenter implements WorkoutContract.Presenter {

    private WorkoutContract.View view;
    private WorkoutDao workoutDao;

    public WorkoutPresenter(WorkoutContract.View view, Context context) {
        this.view = view;
        this.workoutDao = AppDatabase.getInstance(context).workoutDao();
    }

    @Override
    public void loadWorkouts() {
        try {
            List<Workout> workouts = workoutDao.getAllWorkouts();
            view.onWorkoutsLoaded(workouts);
        } catch (Exception e) {
            view.onError("Error al cargar los entrenamientos");
        }
    }

    @Override
    public void saveWorkout(Workout workout) {
        try {
            workoutDao.insert(workout);
            view.onWorkoutSaved();
        } catch (Exception e) {
            view.onError("Error al guardar el entrenamiento");
        }
    }

    @Override
    public void updateWorkout(Workout workout) {
        try {
            workoutDao.update(workout);
            view.onWorkoutSaved();
        } catch (Exception e) {
            view.onError("Error al actualizar el entrenamiento");
        }
    }

    @Override
    public void deleteWorkout(Workout workout) {
        try {
            workoutDao.delete(workout);
            view.onWorkoutDeleted();
        } catch (Exception e) {
            view.onError("Error al eliminar el entrenamiento");
        }
    }
}
