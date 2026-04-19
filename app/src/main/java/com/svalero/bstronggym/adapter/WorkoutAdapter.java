package com.svalero.bstronggym.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.model.Workout;
import com.svalero.bstronggym.view.WorkoutFormActivity;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private List<Workout> workouts;
    private Context context;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Workout workout);
    }

    public WorkoutAdapter(List<Workout> workouts, Context context, OnDeleteClickListener deleteListener) {
        this.workouts = workouts;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workouts.get(position);

        holder.tvName.setText(workout.getName());
        holder.tvDate.setText(workout.getDate());
        holder.tvDuration.setText(workout.getDurationMinutes() + " min");
        holder.tvCalories.setText(workout.getCalories() + " kcal");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WorkoutFormActivity.class);
            intent.putExtra("workout_id", workout.getId());
            intent.putExtra("workout_name", workout.getName());
            intent.putExtra("workout_description", workout.getDescription());
            intent.putExtra("workout_duration", workout.getDurationMinutes());
            intent.putExtra("workout_date", workout.getDate());
            intent.putExtra("workout_calories", workout.getCalories());
            context.startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(v -> deleteListener.onDeleteClick(workout));
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public void updateList(List<Workout> newList) {
        this.workouts = newList;
        notifyDataSetChanged();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvDuration, tvCalories;
        ImageView ivDelete;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_workout_name);
            tvDate = itemView.findViewById(R.id.tv_workout_date);
            tvDuration = itemView.findViewById(R.id.tv_workout_duration);
            tvCalories = itemView.findViewById(R.id.tv_workout_calories);
            ivDelete = itemView.findViewById(R.id.iv_workout_delete);
        }
    }
}
