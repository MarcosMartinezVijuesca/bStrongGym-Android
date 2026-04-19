package com.svalero.bstronggym.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.contract.WorkoutContract;
import com.svalero.bstronggym.model.Workout;
import com.svalero.bstronggym.presenter.WorkoutPresenter;

import java.util.List;

public class WorkoutFormActivity extends AppCompatActivity implements WorkoutContract.View {

    private EditText etName, etDescription, etDuration, etDate, etCalories;
    private Button btnSave;
    private WorkoutPresenter presenter;
    private long workoutId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new WorkoutPresenter(this, this);

        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        etDuration = findViewById(R.id.et_duration);
        etDate = findViewById(R.id.et_date);
        etCalories = findViewById(R.id.et_calories);
        btnSave = findViewById(R.id.btn_save);

        if (getIntent().hasExtra("workout_id")) {
            workoutId = getIntent().getLongExtra("workout_id", -1);
            etName.setText(getIntent().getStringExtra("workout_name"));
            etDescription.setText(getIntent().getStringExtra("workout_description"));
            etDuration.setText(String.valueOf(getIntent().getIntExtra("workout_duration", 0)));
            etDate.setText(getIntent().getStringExtra("workout_date"));
            etCalories.setText(String.valueOf(getIntent().getIntExtra("workout_calories", 0)));
            getSupportActionBar().setTitle("Editar entrenamiento");
        } else {
            getSupportActionBar().setTitle("Nuevo entrenamiento");
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("El nombre es obligatorio");
                etName.requestFocus();
                return;
            }

            if (!date.isEmpty() && !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                etDate.setError("Formato incorrecto, usa YYYY-MM-DD");
                etDate.requestFocus();
                return;
            }

            Workout workout = new Workout();
            workout.setName(name);
            workout.setDescription(etDescription.getText().toString().trim());
            workout.setDate(date);

            String durationStr = etDuration.getText().toString().trim();
            if (!durationStr.isEmpty())
                workout.setDurationMinutes(Integer.parseInt(durationStr));

            String caloriesStr = etCalories.getText().toString().trim();
            if (!caloriesStr.isEmpty())
                workout.setCalories(Integer.parseInt(caloriesStr));

            if (workoutId == -1) {
                presenter.saveWorkout(workout);
            } else {
                workout.setId(workoutId);
                presenter.updateWorkout(workout);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onWorkoutsLoaded(List<Workout> workouts) {}

    @Override
    public void onWorkoutSaved() {
        Toast.makeText(this, "Entrenamiento guardado correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onWorkoutDeleted() {}

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
