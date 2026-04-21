package com.svalero.bstronggym.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.contract.SubscriptionContract;
import com.svalero.bstronggym.domain.Subscription;
import com.svalero.bstronggym.presenter.SubscriptionPresenter;

import java.util.List;

public class SubscriptionFormActivity extends AppCompatActivity implements SubscriptionContract.View {

    private RadioGroup rgType;
    private EditText etStartDate, etEndDate, etPrice, etMemberId;
    private CheckBox cbActive, cbAutoRenewal;
    private Button btnSave;
    private SubscriptionPresenter presenter;
    private long subscriptionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new SubscriptionPresenter(this);

        rgType = findViewById(R.id.rg_type);
        etStartDate = findViewById(R.id.et_startDate);
        etEndDate = findViewById(R.id.et_endDate);
        etPrice = findViewById(R.id.et_price);
        etMemberId = findViewById(R.id.et_memberId);
        cbActive = findViewById(R.id.cb_active);
        cbAutoRenewal = findViewById(R.id.cb_autoRenewal);
        btnSave = findViewById(R.id.btn_save);

        if (getIntent().hasExtra("subscription_id")) {
            subscriptionId = getIntent().getLongExtra("subscription_id", -1);
            etStartDate.setText(getIntent().getStringExtra("subscription_startDate"));
            etEndDate.setText(getIntent().getStringExtra("subscription_endDate"));
            etPrice.setText(String.valueOf(getIntent().getFloatExtra("subscription_price", 0)));
            etMemberId.setText(String.valueOf(getIntent().getLongExtra("subscription_memberId", 0)));
            cbActive.setChecked(getIntent().getBooleanExtra("subscription_active", true));
            cbAutoRenewal.setChecked(getIntent().getBooleanExtra("subscription_autoRenewal", false));

            String type = getIntent().getStringExtra("subscription_type");
            if ("QUARTERLY".equals(type)) rgType.check(R.id.rb_quarterly);
            else if ("ANNUAL".equals(type)) rgType.check(R.id.rb_annual);
            else rgType.check(R.id.rb_monthly);

            getSupportActionBar().setTitle(getString(R.string.edit_subscription));
        } else {
            getSupportActionBar().setTitle(getString(R.string.new_subscription));
        }

        btnSave.setOnClickListener(v -> {
            String startDate = etStartDate.getText().toString().trim();
            String endDate = etEndDate.getText().toString().trim();
            String memberIdStr = etMemberId.getText().toString().trim();

            if (startDate.isEmpty() || !startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                etStartDate.setError(getString(R.string.error_start_date));
                etStartDate.requestFocus();
                return;
            }

            if (endDate.isEmpty() || !endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                etEndDate.setError(getString(R.string.error_end_date));
                etEndDate.requestFocus();
                return;
            }

            if (memberIdStr.isEmpty() || Long.parseLong(memberIdStr) < 1) {
                etMemberId.setError(getString(R.string.error_member_id));
                etMemberId.requestFocus();
                return;
            }

            String type;
            int selectedId = rgType.getCheckedRadioButtonId();
            if (selectedId == R.id.rb_quarterly) type = "QUARTERLY";
            else if (selectedId == R.id.rb_annual) type = "ANNUAL";
            else type = "MONTHLY";

            Subscription subscription = new Subscription();
            subscription.setType(type);
            subscription.setStartDate(startDate);
            subscription.setEndDate(endDate);
            subscription.setMemberId(Long.parseLong(memberIdStr));
            subscription.setActive(cbActive.isChecked());
            subscription.setAutoRenewal(cbAutoRenewal.isChecked());

            String priceStr = etPrice.getText().toString().trim();
            if (!priceStr.isEmpty())
                subscription.setPrice(Float.parseFloat(priceStr));

            if (subscriptionId == -1) {
                presenter.saveSubscription(subscription);
            } else {
                presenter.updateSubscription(subscriptionId, subscription);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onSubscriptionsLoaded(List<Subscription> subscriptions) {}

    @Override
    public void onSubscriptionSaved() {
        Toast.makeText(this, getString(R.string.subscription_saved), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onSubscriptionDeleted() {}

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}