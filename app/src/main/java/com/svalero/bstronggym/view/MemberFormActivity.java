package com.svalero.bstronggym.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.contract.MemberContract;
import com.svalero.bstronggym.domain.Member;
import com.svalero.bstronggym.presenter.MemberPresenter;

public class MemberFormActivity extends AppCompatActivity implements MemberContract.View {

    private EditText etFirstName, etLastName, etEmail, etBirthDate, etWeight;
    private CheckBox cbActive;
    private Button btnSave;
    private MemberPresenter presenter;
    private long memberId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MemberPresenter(this);

        etFirstName = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        etEmail = findViewById(R.id.et_email);
        etBirthDate = findViewById(R.id.et_birthDate);
        etWeight = findViewById(R.id.et_weight);
        cbActive = findViewById(R.id.cb_active);
        btnSave = findViewById(R.id.btn_save);

        // Si viene con datos es edición, si no es creación
        if (getIntent().hasExtra("member_id")) {
            memberId = getIntent().getLongExtra("member_id", -1);
            etFirstName.setText(getIntent().getStringExtra("member_firstName"));
            etLastName.setText(getIntent().getStringExtra("member_lastName"));
            cbActive.setChecked(getIntent().getBooleanExtra("member_active", true));
            getSupportActionBar().setTitle("Editar socio");
        } else {
            getSupportActionBar().setTitle("Nuevo socio");
        }

        btnSave.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String birthDate = etBirthDate.getText().toString().trim();

            if (firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, "Nombre y apellidos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar formato fecha YYYY-MM-DD
            if (!birthDate.isEmpty() && !birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                Toast.makeText(this, "Fecha debe tener formato YYYY-MM-DD (ej: 2000-05-05)", Toast.LENGTH_SHORT).show();
                return;
            }

            Member member = new Member();
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setEmail(etEmail.getText().toString().trim());
            member.setBirthDate(birthDate);
            member.setActive(cbActive.isChecked());

            String weightStr = etWeight.getText().toString().trim();
            if (!weightStr.isEmpty()) {
                member.setWeight(Float.parseFloat(weightStr));
            }

            if (memberId == -1) {
                presenter.saveMember(member);
            } else {
                presenter.updateMember(memberId, member);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onMembersLoaded(java.util.List<Member> members) {}

    @Override
    public void onMemberSaved() {
        Toast.makeText(this, "Socio guardado correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onMemberDeleted() {}

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}