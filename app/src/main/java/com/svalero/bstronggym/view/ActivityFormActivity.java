package com.svalero.bstronggym.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.contract.ActivityContract;
import com.svalero.bstronggym.domain.Activity;
import com.svalero.bstronggym.presenter.ActivityPresenter;

import java.util.List;

public class ActivityFormActivity extends AppCompatActivity implements ActivityContract.View {

    private EditText etName, etDescription, etCapacity, etDuration, etPrice, etMonitorId;
    private TextView tvCurrentMonitor;
    private CheckBox cbActive;
    private Button btnSave;
    private ActivityPresenter presenter;
    private long activityId = -1;

    @Override
    public void onActivityLoaded(Activity activity) {
        etName.setText(activity.getName());
        etDescription.setText(activity.getDescription());
        etCapacity.setText(String.valueOf(activity.getCapacity()));
        etDuration.setText(String.valueOf(activity.getDurationMinutes()));
        etPrice.setText(String.valueOf(activity.getPricePerSession()));
        etMonitorId.setText(String.valueOf(activity.getMonitorId()));
        cbActive.setChecked(activity.isActive());
        tvCurrentMonitor.setText("Monitor actual: " + activity.getMonitorName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ActivityPresenter(this);

        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        etCapacity = findViewById(R.id.et_capacity);
        etDuration = findViewById(R.id.et_duration);
        etPrice = findViewById(R.id.et_price);
        etMonitorId = findViewById(R.id.et_monitorId);
        tvCurrentMonitor = findViewById(R.id.tv_current_monitor);
        cbActive = findViewById(R.id.cb_active);
        btnSave = findViewById(R.id.btn_save);

        if (getIntent().hasExtra("activity_id")) {
            activityId = getIntent().getLongExtra("activity_id", -1);
            getSupportActionBar().setTitle("Editar actividad");
            presenter.loadActivity(activityId);
        } else {
            tvCurrentMonitor.setVisibility(android.view.View.GONE);
            getSupportActionBar().setTitle("Nueva actividad");
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String capacityStr = etCapacity.getText().toString().trim();
            String durationStr = etDuration.getText().toString().trim();
            String monitorIdStr = etMonitorId.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("El nombre es obligatorio");
                etName.requestFocus();
                return;
            }

            if (capacityStr.isEmpty() || Integer.parseInt(capacityStr) < 1) {
                etCapacity.setError("La capacidad mínima es 1");
                etCapacity.requestFocus();
                return;
            }

            if (durationStr.isEmpty() || Integer.parseInt(durationStr) < 15) {
                etDuration.setError("La duración mínima es 15 minutos");
                etDuration.requestFocus();
                return;
            }

            if (monitorIdStr.isEmpty() || Long.parseLong(monitorIdStr) < 1) {
                etMonitorId.setError("El ID del monitor es obligatorio");
                etMonitorId.requestFocus();
                return;
            }

            Activity activity = new Activity();
            activity.setName(name);
            activity.setDescription(etDescription.getText().toString().trim());
            activity.setActive(cbActive.isChecked());
            activity.setCapacity(Integer.parseInt(capacityStr));
            activity.setDurationMinutes(Integer.parseInt(durationStr));
            activity.setMonitorId(Long.parseLong(monitorIdStr));

            String priceStr = etPrice.getText().toString().trim();
            if (!priceStr.isEmpty())
                activity.setPricePerSession(Float.parseFloat(priceStr));

            if (activityId == -1) {
                presenter.saveActivity(activity);
            } else {
                presenter.updateActivity(activityId, activity);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onActivitiesLoaded(List<Activity> activities) {}

    @Override
    public void onActivitySaved() {
        Toast.makeText(this, "Actividad guardada correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onActivityDeleted() {}

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
