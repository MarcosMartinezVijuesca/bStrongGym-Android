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
import com.svalero.bstronggym.domain.Member;
import com.svalero.bstronggym.view.MemberFormActivity;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<Member> members;
    private Context context;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Member member);
    }

    public MemberAdapter(List<Member> members, Context context, OnDeleteClickListener deleteListener) {
        this.members = members;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member member = members.get(position);

        holder.tvName.setText(member.getFirstName() + " " + member.getLastName());
        holder.tvDate.setText(member.getRegistrationDate());
        holder.cbActive.setChecked(member.isActive());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MemberFormActivity.class);
            intent.putExtra("member_id", member.getId());
            intent.putExtra("member_firstName", member.getFirstName());
            intent.putExtra("member_lastName", member.getLastName());
            intent.putExtra("member_active", member.isActive());
            context.startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(v -> deleteListener.onDeleteClick(member));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void updateList(List<Member> newList) {
        this.members = newList;
        notifyDataSetChanged();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate;
        CheckBox cbActive;
        ImageView ivDelete;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_member_name);
            tvDate = itemView.findViewById(R.id.tv_member_date);
            cbActive = itemView.findViewById(R.id.cb_member_active);
            ivDelete = itemView.findViewById(R.id.iv_member_delete);
        }
    }
}

