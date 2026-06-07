package com.svalero.bstronggym.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.domain.Booking;
import com.svalero.bstronggym.view.BookingFormActivity;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookings;
    private Context context;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Booking booking);
    }

    public BookingAdapter(List<Booking> bookings, Context context, OnDeleteClickListener deleteListener) {
        this.bookings = bookings;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);

        holder.tvMember.setText(booking.getMemberName());
        holder.tvActivity.setText(booking.getActivityName());
        holder.tvDate.setText(booking.getBookingDate());
        holder.cbAttended.setChecked(booking.isAttended());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingFormActivity.class);
            intent.putExtra("booking_id", booking.getId());
            intent.putExtra("booking_date", booking.getBookingDate());
            intent.putExtra("booking_attended", booking.isAttended());
            intent.putExtra("booking_reviewNote", booking.getReviewNote());
            intent.putExtra("booking_reviewText", booking.getReviewText());
            intent.putExtra("booking_pricePaid", booking.getPricePaid());
            intent.putExtra("booking_memberId", booking.getMemberId());
            intent.putExtra("booking_activityId", booking.getActivityId());
            context.startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(v -> deleteListener.onDeleteClick(booking));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public void updateList(List<Booking> newList) {
        this.bookings = newList;
        notifyDataSetChanged();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvMember, tvActivity, tvDate;
        CheckBox cbAttended;
        ImageView ivDelete;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMember = itemView.findViewById(R.id.tv_booking_member);
            tvActivity = itemView.findViewById(R.id.tv_booking_activity);
            tvDate = itemView.findViewById(R.id.tv_booking_date);
            cbAttended = itemView.findViewById(R.id.cb_booking_attended);
            ivDelete = itemView.findViewById(R.id.iv_booking_delete);
        }
    }
}
