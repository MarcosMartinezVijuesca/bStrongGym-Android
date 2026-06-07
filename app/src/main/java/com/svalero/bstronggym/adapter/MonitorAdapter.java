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
import com.svalero.bstronggym.domain.Monitor;
import com.svalero.bstronggym.view.MonitorFormActivity;

import java.util.List;

public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.MonitorViewHolder> {

    private List<Monitor> monitors;
    private Context context;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Monitor monitor);
    }

    public MonitorAdapter(List<Monitor> monitors, Context context, OnDeleteClickListener deleteListener) {
        this.monitors = monitors;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public MonitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_monitor, parent, false);
        return new MonitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonitorViewHolder holder, int position) {
        Monitor monitor = monitors.get(position);

        holder.tvName.setText(monitor.getName());
        holder.tvSpecialty.setText(monitor.getSpecialty());
        holder.tvDni.setText(monitor.getDni());
        holder.cbAvailable.setChecked(monitor.isAvailable());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MonitorFormActivity.class);
            intent.putExtra("monitor_id", monitor.getId());
            intent.putExtra("monitor_name", monitor.getName());
            intent.putExtra("monitor_dni", monitor.getDni());
            intent.putExtra("monitor_specialty", monitor.getSpecialty());
            intent.putExtra("monitor_salary", monitor.getSalary());
            intent.putExtra("monitor_hireDate", monitor.getHireDate());
            intent.putExtra("monitor_available", monitor.isAvailable());
            context.startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(v -> deleteListener.onDeleteClick(monitor));
    }

    @Override
    public int getItemCount() {
        return monitors.size();
    }

    public void updateList(List<Monitor> newList) {
        this.monitors = newList;
        notifyDataSetChanged();
    }

    public static class MonitorViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSpecialty, tvDni;
        CheckBox cbAvailable;
        ImageView ivDelete;

        public MonitorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_monitor_name);
            tvSpecialty = itemView.findViewById(R.id.tv_monitor_specialty);
            tvDni = itemView.findViewById(R.id.tv_monitor_dni);
            cbAvailable = itemView.findViewById(R.id.cb_monitor_available);
            ivDelete = itemView.findViewById(R.id.iv_monitor_delete);
        }
    }
}
