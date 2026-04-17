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
import com.svalero.bstronggym.domain.Activity;
import com.svalero.bstronggym.view.ActivityFormActivity;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<Activity> activities;
    private Context context;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Activity activity);
    }

    public ActivityAdapter(List<Activity> activities, Context context, OnDeleteClickListener deleteListener) {
        this.activities = activities;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity activity = activities.get(position);

        holder.tvName.setText(activity.getName());
        holder.tvDuration.setText(activity.getDurationMinutes() + " min");
        holder.tvPrice.setText(activity.getPricePerSession() + " €/sesión");
        holder.cbActive.setChecked(activity.isActive());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityFormActivity.class);
            intent.putExtra("activity_id", activity.getId());
            intent.putExtra("activity_name", activity.getName());
            intent.putExtra("activity_description", activity.getDescription());
            intent.putExtra("activity_capacity", activity.getCapacity());
            intent.putExtra("activity_duration", activity.getDurationMinutes());
            intent.putExtra("activity_price", activity.getPricePerSession());
            intent.putExtra("activity_active", activity.isActive());
            intent.putExtra("activity_monitorId", activity.getMonitorId());
            context.startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(v -> deleteListener.onDeleteClick(activity));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public void updateList(List<Activity> newList) {
        this.activities = newList;
        notifyDataSetChanged();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDuration, tvPrice;
        CheckBox cbActive;
        ImageView ivDelete;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_activity_name);
            tvDuration = itemView.findViewById(R.id.tv_activity_duration);
            tvPrice = itemView.findViewById(R.id.tv_activity_price);
            cbActive = itemView.findViewById(R.id.cb_activity_active);
            ivDelete = itemView.findViewById(R.id.iv_activity_delete);
        }
    }
}
