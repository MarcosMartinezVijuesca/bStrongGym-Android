package com.svalero.bstronggym.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.bstronggym.R;
import com.svalero.bstronggym.adapter.MonitorAdapter;
import com.svalero.bstronggym.contract.MonitorContract;
import com.svalero.bstronggym.domain.Monitor;
import com.svalero.bstronggym.presenter.MonitorPresenter;

import java.util.ArrayList;
import java.util.List;

public class MonitorsActivity extends AppCompatActivity implements MonitorContract.View {

    private RecyclerView rvMonitors;
    private MonitorAdapter adapter;
    private MonitorPresenter presenter;
    private EditText etSearch;
    private List<Monitor> monitorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitors);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.monitors));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MonitorPresenter(this);

        rvMonitors = findViewById(R.id.rv_monitors);
        rvMonitors.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.et_search);

        adapter = new MonitorAdapter(monitorList, this, monitor -> {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete))
                    .setMessage(getString(R.string.confirm_delete) + " " + monitor.getName() + "?")
                    .setPositiveButton(getString(R.string.delete), (dialog, which) -> presenter.deleteMonitor(monitor.getId()))
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
        });

        rvMonitors.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    presenter.loadMonitors();
                } else {
                    presenter.loadMonitorsByName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_monitor);
        fab.setOnClickListener(v -> startActivity(new Intent(this, MonitorFormActivity.class)));

        presenter.loadMonitors();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onMonitorsLoaded(List<Monitor> monitors) {
        monitorList = monitors;
        adapter.updateList(monitors);
    }

    @Override
    public void onMonitorSaved() {
        presenter.loadMonitors();
    }

    @Override
    public void onMonitorDeleted() {
        presenter.loadMonitors();
    }

    @Override
    public void onError(String message) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.error_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadMonitors();
    }
}