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
import com.svalero.bstronggym.adapter.SubscriptionAdapter;
import com.svalero.bstronggym.contract.SubscriptionContract;
import com.svalero.bstronggym.domain.Subscription;
import com.svalero.bstronggym.presenter.SubscriptionPresenter;
import com.svalero.bstronggym.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsActivity extends AppCompatActivity implements SubscriptionContract.View {

    private RecyclerView rvSubscriptions;
    private SubscriptionAdapter adapter;
    private SubscriptionPresenter presenter;
    private SessionManager sessionManager;
    private List<Subscription> subscriptionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Suscripciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);
        presenter = new SubscriptionPresenter(this);

        rvSubscriptions = findViewById(R.id.rv_subscriptions);
        rvSubscriptions.setLayoutManager(new LinearLayoutManager(this));

        // Solo el admin puede eliminar suscripciones
        adapter = new SubscriptionAdapter(subscriptionList, this, subscription -> {
            if (sessionManager.isAdmin()) {
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar suscripción")
                        .setMessage("¿Estás seguro de que quieres eliminar esta suscripción?")
                        .setPositiveButton("Eliminar", (dialog, which) -> presenter.deleteSubscription(subscription.getId()))
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        rvSubscriptions.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_subscription);

        // Solo el admin puede crear suscripciones
        if (sessionManager.isAdmin()) {
            fab.setOnClickListener(v -> startActivity(new Intent(this, SubscriptionFormActivity.class)));
        } else {
            fab.setVisibility(android.view.View.GONE);
        }

        loadData();
    }

    private void loadData() {
        if (sessionManager.isAdmin()) {
            presenter.loadSubscriptions();
        } else {
            presenter.loadSubscriptionsByMember(sessionManager.getUserId());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onSubscriptionsLoaded(List<Subscription> subscriptions) {
        subscriptionList = subscriptions;
        adapter.updateList(subscriptions);
    }

    @Override
    public void onSubscriptionSaved() {
        loadData();
    }

    @Override
    public void onSubscriptionDeleted() {
        loadData();
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
        loadData();
    }
}