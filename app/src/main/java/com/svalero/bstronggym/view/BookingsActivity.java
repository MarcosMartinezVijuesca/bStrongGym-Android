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
import com.svalero.bstronggym.adapter.BookingAdapter;
import com.svalero.bstronggym.contract.BookingContract;
import com.svalero.bstronggym.domain.Booking;
import com.svalero.bstronggym.presenter.BookingPresenter;

import java.util.ArrayList;
import java.util.List;

public class BookingsActivity extends AppCompatActivity implements BookingContract.View {

    private RecyclerView rvBookings;
    private BookingAdapter adapter;
    private BookingPresenter presenter;
    private EditText etSearchMemberId;
    private List<Booking> bookingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reservas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new BookingPresenter(this);

        rvBookings = findViewById(R.id.rv_bookings);
        rvBookings.setLayoutManager(new LinearLayoutManager(this));

        etSearchMemberId = findViewById(R.id.et_search_member_id);

        adapter = new BookingAdapter(bookingList, this, booking -> {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar reserva")
                    .setMessage("¿Estás seguro de que quieres eliminar esta reserva?")
                    .setPositiveButton("Eliminar", (dialog, which) -> presenter.deleteBooking(booking.getId()))
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        rvBookings.setAdapter(adapter);

        etSearchMemberId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    presenter.loadBookings();
                } else {
                    try {
                        presenter.loadBookingsByMember(Long.parseLong(s.toString()));
                    } catch (NumberFormatException e) {
                        presenter.loadBookings();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_booking);
        fab.setOnClickListener(v -> startActivity(new Intent(this, BookingFormActivity.class)));

        presenter.loadBookings();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBookingsLoaded(List<Booking> bookings) {
        bookingList = bookings;
        adapter.updateList(bookings);
    }

    @Override
    public void onBookingSaved() {
        presenter.loadBookings();
    }

    @Override
    public void onBookingDeleted() {
        presenter.loadBookings();
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
        presenter.loadBookings();
    }
}
