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
import com.svalero.bstronggym.adapter.MemberAdapter;
import com.svalero.bstronggym.contract.MemberContract;
import com.svalero.bstronggym.domain.Member;
import com.svalero.bstronggym.presenter.MemberPresenter;

import java.util.ArrayList;
import java.util.List;

public class MembersActivity extends AppCompatActivity implements MemberContract.View {

    private RecyclerView rvMembers;
    private MemberAdapter adapter;
    private MemberPresenter presenter;
    private EditText etSearch;
    private List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.members));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MemberPresenter(this);

        rvMembers = findViewById(R.id.rv_members);
        rvMembers.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.et_search);

        adapter = new MemberAdapter(memberList, this, member -> {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete))
                    .setMessage(getString(R.string.confirm_delete) + " " + member.getFirstName() + "?")
                    .setPositiveButton(getString(R.string.delete), (dialog, which) -> presenter.deleteMember(member.getId()))
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
        });

        rvMembers.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    presenter.loadMembers();
                } else {
                    presenter.loadMembersByName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_member);
        fab.setOnClickListener(v -> startActivity(new Intent(this, MemberFormActivity.class)));

        presenter.loadMembers();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onMembersLoaded(List<Member> members) {
        memberList = members;
        adapter.updateList(members);
    }

    @Override
    public void onMemberSaved() {
        presenter.loadMembers();
    }

    @Override
    public void onMemberDeleted() {
        presenter.loadMembers();
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
        presenter.loadMembers();
    }
}
