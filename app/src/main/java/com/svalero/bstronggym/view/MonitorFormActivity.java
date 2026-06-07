package com.svalero.bstronggym.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.contract.MonitorContract;
import com.svalero.bstronggym.domain.Monitor;
import com.svalero.bstronggym.presenter.MonitorPresenter;

import java.util.List;

public class MonitorFormActivity extends AppCompatActivity implements MonitorContract.View {

    private EditText etName, etDni, etSpecialty, etSalary, etHireDate;
    private CheckBox cbAvailable;
    private Button btnSave;
    private MonitorPresenter presenter;
    private long monitorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MonitorPresenter(this);

        etName = findViewById(R.id.et_name);
        etDni = findViewById(R.id.et_dni);
        etSpecialty = findViewById(R.id.et_specialty);
        etSalary = findViewById(R.id.et_salary);
        etHireDate = findViewById(R.id.et_hireDate);
        cbAvailable = findViewById(R.id.cb_available);
        btnSave = findViewById(R.id.btn_save);

        if (getIntent().hasExtra("monitor_id")) {
            monitorId = getIntent().getLongExtra("monitor_id", -1);
            etName.setText(getIntent().getStringExtra("monitor_name"));
            etDni.setText(getIntent().getStringExtra("monitor_dni"));
            etSpecialty.setText(getIntent().getStringExtra("monitor_specialty"));
            etSalary.setText(String.valueOf(getIntent().getFloatExtra("monitor_salary", 0)));
            etHireDate.setText(getIntent().getStringExtra("monitor_hireDate"));
            cbAvailable.setChecked(getIntent().getBooleanExtra("monitor_available", true));
            getSupportActionBar().setTitle(getString(R.string.edit_monitor));
        } else {
            getSupportActionBar().setTitle(getString(R.string.new_monitor));
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String dni = etDni.getText().toString().trim();
            String hireDate = etHireDate.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError(getString(R.string.error_first_name));
                etName.requestFocus();
                return;
            }

            if (dni.isEmpty() || dni.length() != 9) {
                etDni.setError(getString(R.string.error_dni));
                etDni.requestFocus();
                return;
            }

            if (!hireDate.isEmpty() && !hireDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                etHireDate.setError(getString(R.string.error_date_format));
                etHireDate.requestFocus();
                return;
            }

            Monitor monitor = new Monitor();
            monitor.setName(name);
            monitor.setDni(dni);
            monitor.setSpecialty(etSpecialty.getText().toString().trim());
            monitor.setAvailable(cbAvailable.isChecked());
            monitor.setHireDate(hireDate);

            String salaryStr = etSalary.getText().toString().trim();
            if (!salaryStr.isEmpty())
                monitor.setSalary(Float.parseFloat(salaryStr));

            if (monitorId == -1) {
                presenter.saveMonitor(monitor);
            } else {
                presenter.updateMonitor(monitorId, monitor);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onMonitorsLoaded(List<Monitor> monitors) {}

    @Override
    public void onMonitorSaved() {
        Toast.makeText(this, getString(R.string.monitor_saved), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onMonitorDeleted() {}

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}