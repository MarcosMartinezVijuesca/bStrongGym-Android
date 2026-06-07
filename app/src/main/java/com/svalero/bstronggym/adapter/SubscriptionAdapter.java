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
import com.svalero.bstronggym.domain.Subscription;
import com.svalero.bstronggym.view.SubscriptionFormActivity;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder> {

    private List<Subscription> subscriptions;
    private Context context;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Subscription subscription);
    }

    public SubscriptionAdapter(List<Subscription> subscriptions, Context context, OnDeleteClickListener deleteListener) {
        this.subscriptions = subscriptions;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subscription, parent, false);
        return new SubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        Subscription subscription = subscriptions.get(position);

        holder.tvMember.setText(subscription.getMemberName());
        holder.tvType.setText(subscription.getType());
        holder.tvDates.setText(subscription.getStartDate() + " → " + subscription.getEndDate());
        holder.tvPrice.setText(subscription.getPrice() + " €");
        holder.cbActive.setChecked(subscription.isActive());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SubscriptionFormActivity.class);
            intent.putExtra("subscription_id", subscription.getId());
            intent.putExtra("subscription_type", subscription.getType());
            intent.putExtra("subscription_startDate", subscription.getStartDate());
            intent.putExtra("subscription_endDate", subscription.getEndDate());
            intent.putExtra("subscription_price", subscription.getPrice());
            intent.putExtra("subscription_active", subscription.isActive());
            intent.putExtra("subscription_autoRenewal", subscription.isAutoRenewal());
            intent.putExtra("subscription_memberId", subscription.getMemberId());
            context.startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(v -> deleteListener.onDeleteClick(subscription));
    }

    @Override
    public int getItemCount() {
        return subscriptions.size();
    }

    public void updateList(List<Subscription> newList) {
        this.subscriptions = newList;
        notifyDataSetChanged();
    }

    public static class SubscriptionViewHolder extends RecyclerView.ViewHolder {
        TextView tvMember, tvType, tvDates, tvPrice;
        CheckBox cbActive;
        ImageView ivDelete;

        public SubscriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMember = itemView.findViewById(R.id.tv_subscription_member);
            tvType = itemView.findViewById(R.id.tv_subscription_type);
            tvDates = itemView.findViewById(R.id.tv_subscription_dates);
            tvPrice = itemView.findViewById(R.id.tv_subscription_price);
            cbActive = itemView.findViewById(R.id.cb_subscription_active);
            ivDelete = itemView.findViewById(R.id.iv_subscription_delete);
        }
    }
}
