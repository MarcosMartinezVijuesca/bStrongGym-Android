package com.svalero.bstronggym.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.util.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private Button btnMembers, btnActivities, btnBookings, btnMonitors, btnWorkouts, btnSubscriptions, btnMap;
    private TextView tvWelcome, tvRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        sessionManager = new SessionManager(this);

        tvWelcome = findViewById(R.id.tv_welcome);
        tvRole = findViewById(R.id.tv_role);
        btnMembers = findViewById(R.id.btn_members);
        btnActivities = findViewById(R.id.btn_activities);
        btnBookings = findViewById(R.id.btn_bookings);
        btnMonitors = findViewById(R.id.btn_monitors);
        btnWorkouts = findViewById(R.id.btn_workouts);
        btnSubscriptions = findViewById(R.id.btn_subscriptions);
        btnMap = findViewById(R.id.btn_map);

        tvWelcome.setText(getString(R.string.welcome) + sessionManager.getUsername());
        tvRole.setText(getString(R.string.role) + sessionManager.getRole());

        // Solo el admin ve el botón de socios
        if (!sessionManager.isAdmin()) {
            btnMembers.setVisibility(android.view.View.GONE);
        }

        btnMembers.setOnClickListener(v ->
                startActivity(new Intent(this, MembersActivity.class)));

        btnActivities.setOnClickListener(v ->
                startActivity(new Intent(this, ActivitiesActivity.class)));

        btnBookings.setOnClickListener(v ->
                startActivity(new Intent(this, BookingsActivity.class)));

        btnMonitors.setOnClickListener(v ->
                startActivity(new Intent(this, MonitorsActivity.class)));

        btnWorkouts.setOnClickListener(v ->
                startActivity(new Intent(this, WorkoutsActivity.class)));

        btnSubscriptions.setOnClickListener(v ->
                startActivity(new Intent(this, SubscriptionsActivity.class)));

        btnMap.setOnClickListener(v ->
                startActivity(new Intent(this, MapActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            sessionManager.closeSession();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}