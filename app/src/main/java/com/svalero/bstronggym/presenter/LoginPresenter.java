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
    public void login(String username, String password, String errorMessage) {
        User user = userDao.login(username, password);
        if (user != null) {
            view.onLoginSuccess(user);
        } else {
            view.onLoginError(errorMessage);
        }
    }

    @Override
    public void register(String username, String password, String role, String errorMessage) {
        User existing = userDao.findByUsername(username);
        if (existing != null) {
            view.onRegisterError(errorMessage);
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