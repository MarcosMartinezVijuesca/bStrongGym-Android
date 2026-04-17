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
import com.svalero.bstronggym.adapter.ActivityAdapter;
import com.svalero.bstronggym.contract.ActivityContract;
import com.svalero.bstronggym.domain.Activity;
import com.svalero.bstronggym.presenter.ActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesActivity extends AppCompatActivity implements ActivityContract.View {

    private RecyclerView rvActivities;
    private ActivityAdapter adapter;
    private ActivityPresenter presenter;
    private EditText etSearch;
    private List<Activity> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Actividades");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ActivityPresenter(this);

        rvActivities = findViewById(R.id.rv_activities);
        rvActivities.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.et_search);

        adapter = new ActivityAdapter(activityList, this, activity -> {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar actividad")
                    .setMessage("¿Estás seguro de que quieres eliminar " + activity.getName() + "?")
                    .setPositiveButton("Eliminar", (dialog, which) -> presenter.deleteActivity(activity.getId()))
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        rvActivities.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    presenter.loadActivities();
                } else {
                    presenter.loadActivitiesByName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_activity);
        fab.setOnClickListener(v -> startActivity(new Intent(this, ActivityFormActivity.class)));

        presenter.loadActivities();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onActivitiesLoaded(List<Activity> activities) {
        activityList = activities;
        adapter.updateList(activities);
    }

    @Override
    public void onActivitySaved() {
        presenter.loadActivities();
    }

    @Override
    public void onActivityDeleted() {
        presenter.loadActivities();
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
        presenter.loadActivities();
    }
}