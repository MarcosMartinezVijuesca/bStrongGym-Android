package com.svalero.bstronggym.presenter;

import android.content.Context;

import com.svalero.bstronggym.contract.LoginContract;
import com.svalero.bstronggym.model.AppDatabase;
import com.svalero.bstronggym.model.User;
import com.svalero.bstronggym.model.UserDao;

import java.time.LocalDate;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private UserDao userDao;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.userDao = AppDatabase.getInstance(context).userDao();
    }

    @Override
    public void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.onLoginError("Por favor, rellena todos los campos");
            return;
        }

        User user = userDao.login(username, password);
        if (user != null) {
            view.onLoginSuccess(user);
        } else {
            view.onLoginError("Usuario o contraseña incorrectos");
        }
    }

    @Override
    public void register(String username, String password, String role) {
        if (username.isEmpty() || password.isEmpty()) {
            view.onRegisterError("Por favor, rellena todos los campos");
            return;
        }

        User existing = userDao.findByUsername(username);
        if (existing != null) {
            view.onRegisterError("Ese nombre de usuario ya existe");
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setRegistrationDate(LocalDate.now().toString());
        userDao.insert(newUser);
        view.onRegisterSuccess();
    }
}