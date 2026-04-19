package com.svalero.bstronggym.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.bstronggym.R;
import com.svalero.bstronggym.adapter.WorkoutAdapter;
import com.svalero.bstronggym.contract.WorkoutContract;
import com.svalero.bstronggym.model.Workout;
import com.svalero.bstronggym.presenter.WorkoutPresenter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsActivity extends AppCompatActivity implements WorkoutContract.View {

    private RecyclerView rvWorkouts;
    private WorkoutAdapter adapter;
    private WorkoutPresenter presenter;
    private List<Workout> workoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mis entrenamientos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new WorkoutPresenter(this, this);

        rvWorkouts = findViewById(R.id.rv_workouts);
        rvWorkouts.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WorkoutAdapter(workoutList, this, workout -> {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar entrenamiento")
                    .setMessage("¿Estás seguro de que quieres eliminar " + workout.getName() + "?")
                    .setPositiveButton("Eliminar", (dialog, which) -> presenter.deleteWorkout(workout))
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        rvWorkouts.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_workout);
        fab.setOnClickListener(v -> startActivity(new Intent(this, WorkoutFormActivity.class)));

        presenter.loadWorkouts();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onWorkoutsLoaded(List<Workout> workouts) {
        workoutList = workouts;
        adapter.updateList(workouts);
    }

    @Override
    public void onWorkoutSaved() {
        presenter.loadWorkouts();
    }

    @Override
    public void onWorkoutDeleted() {
        presenter.loadWorkouts();
    }

    @Override
    public void onError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadWorkouts();
    }
}