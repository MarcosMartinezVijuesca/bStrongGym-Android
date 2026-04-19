package com.svalero.bstronggym.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.contract.BookingContract;
import com.svalero.bstronggym.domain.Booking;
import com.svalero.bstronggym.presenter.BookingPresenter;

import java.util.List;

public class BookingFormActivity extends AppCompatActivity implements BookingContract.View {

    private EditText etBookingDate, etMemberId, etActivityId, etPricePaid, etReviewNote, etReviewText;
    private CheckBox cbAttended;
    private Button btnSave;
    private BookingPresenter presenter;
    private long bookingId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new BookingPresenter(this);

        etBookingDate = findViewById(R.id.et_bookingDate);
        etMemberId = findViewById(R.id.et_memberId);
        etActivityId = findViewById(R.id.et_activityId);
        etPricePaid = findViewById(R.id.et_pricePaid);
        etReviewNote = findViewById(R.id.et_reviewNote);
        etReviewText = findViewById(R.id.et_reviewText);
        cbAttended = findViewById(R.id.cb_attended);
        btnSave = findViewById(R.id.btn_save);

        if (getIntent().hasExtra("booking_id")) {
            bookingId = getIntent().getLongExtra("booking_id", -1);
            etBookingDate.setText(getIntent().getStringExtra("booking_date"));
            etMemberId.setText(String.valueOf(getIntent().getLongExtra("booking_memberId", 0)));
            etActivityId.setText(String.valueOf(getIntent().getLongExtra("booking_activityId", 0)));
            etPricePaid.setText(String.valueOf(getIntent().getFloatExtra("booking_pricePaid", 0)));
            etReviewNote.setText(String.valueOf(getIntent().getIntExtra("booking_reviewNote", 0)));
            etReviewText.setText(getIntent().getStringExtra("booking_reviewText"));
            cbAttended.setChecked(getIntent().getBooleanExtra("booking_attended", false));
            getSupportActionBar().setTitle("Editar reserva");
        } else {
            getSupportActionBar().setTitle("Nueva reserva");
        }

        btnSave.setOnClickListener(v -> {
            String bookingDate = etBookingDate.getText().toString().trim();
            String memberIdStr = etMemberId.getText().toString().trim();
            String activityIdStr = etActivityId.getText().toString().trim();

            if (bookingDate.isEmpty() || !bookingDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                etBookingDate.setError("Fecha obligatoria con formato YYYY-MM-DD");
                etBookingDate.requestFocus();
                return;
            }

            if (memberIdStr.isEmpty() || Long.parseLong(memberIdStr) < 1) {
                etMemberId.setError("El ID del socio es obligatorio");
                etMemberId.requestFocus();
                return;
            }

            if (activityIdStr.isEmpty() || Long.parseLong(activityIdStr) < 1) {
                etActivityId.setError("El ID de la actividad es obligatorio");
                etActivityId.requestFocus();
                return;
            }

            String reviewNoteStr = etReviewNote.getText().toString().trim();
            if (!reviewNoteStr.isEmpty()) {
                int note = Integer.parseInt(reviewNoteStr);
                if (note < 1 || note > 5) {
                    etReviewNote.setError("La valoración debe estar entre 1 y 5");
                    etReviewNote.requestFocus();
                    return;
                }
            }

            Booking booking = new Booking();
            booking.setBookingDate(bookingDate);
            booking.setMemberId(Long.parseLong(memberIdStr));
            booking.setActivityId(Long.parseLong(activityIdStr));
            booking.setAttended(cbAttended.isChecked());

            String pricePaidStr = etPricePaid.getText().toString().trim();
            if (!pricePaidStr.isEmpty())
                booking.setPricePaid(Float.parseFloat(pricePaidStr));

            if (!reviewNoteStr.isEmpty())
                booking.setReviewNote(Integer.parseInt(reviewNoteStr));

            String reviewText = etReviewText.getText().toString().trim();
            if (!reviewText.isEmpty())
                booking.setReviewText(reviewText);

            if (bookingId == -1) {
                presenter.saveBooking(booking);
            } else {
                presenter.updateBooking(bookingId, booking);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBookingsLoaded(List<Booking> bookings) {}

    @Override
    public void onBookingSaved() {
        Toast.makeText(this, "Reserva guardada correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBookingDeleted() {}

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}