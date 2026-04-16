package com.svalero.bstronggym.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.svalero.bstronggym.R;
import com.svalero.bstronggym.contract.LoginContract;
import com.svalero.bstronggym.model.User;
import com.svalero.bstronggym.presenter.LoginPresenter;
import com.svalero.bstronggym.util.SessionManager;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private LoginPresenter presenter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        // Si ya hay sesión activa, ir directamente al MainActivity
        if (sessionManager.isLoggedIn()) {
            goToMain();
            return;
        }

        presenter = new LoginPresenter(this, this);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            presenter.login(username, password);
        });

        btnRegister.setOnClickListener(v -> showRegisterDialog());
    }

    private void showRegisterDialog() {
        String[] roles = {"MEMBER", "ADMIN"};
        final String[] selectedRole = {"MEMBER"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registro");

        android.view.View dialogView = getLayoutInflater().inflate(R.layout.dialog_register, null);
        builder.setView(dialogView);

        EditText etDialogUsername = dialogView.findViewById(R.id.et_dialog_username);
        EditText etDialogPassword = dialogView.findViewById(R.id.et_dialog_password);

        builder.setSingleChoiceItems(roles, 0, (dialog, which) -> selectedRole[0] = roles[which]);

        builder.setPositiveButton("Registrar", (dialog, which) -> {
            String username = etDialogUsername.getText().toString().trim();
            String password = etDialogPassword.getText().toString().trim();
            presenter.register(username, password, selectedRole[0]);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    @Override
    public void onLoginSuccess(User user) {
        sessionManager.saveSession(user.getId(), user.getUsername(), user.getRole());
        goToMain();
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "Usuario registrado. Ya puedes iniciar sesión", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}