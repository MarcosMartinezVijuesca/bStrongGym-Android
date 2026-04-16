package com.svalero.bstronggym.contract;

import com.svalero.bstronggym.model.User;

public interface LoginContract {

    interface View {
        void onLoginSuccess(User user);
        void onLoginError(String message);
        void onRegisterSuccess();
        void onRegisterError(String message);
    }

    interface Presenter {
        void login(String username, String password);
        void register(String username, String password, String role);
    }
}