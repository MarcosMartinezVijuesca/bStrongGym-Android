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
        getSupportActionBar().setTitle(getString(R.string.subscriptions));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);
        presenter = new SubscriptionPresenter(this);

        rvSubscriptions = findViewById(R.id.rv_subscriptions);
        rvSubscriptions.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SubscriptionAdapter(subscriptionList, this, subscription -> {
            if (sessionManager.isAdmin()) {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.delete))
                        .setMessage(getString(R.string.confirm_delete) + "?")
                        .setPositiveButton(getString(R.string.delete), (dialog, which) -> presenter.deleteSubscription(subscription.getId()))
                        .setNegativeButton(getString(R.string.cancel), null)
                        .show();
            }
        });

        rvSubscriptions.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_subscription);

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
                .setTitle(getString(R.string.error_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}