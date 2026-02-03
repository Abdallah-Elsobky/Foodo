package iti.student.foodo.features.auth.login;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;

public interface LoginContract {
    interface View extends BaseView {
        void onLoginSuccess();

        void onLoginFailure(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String email, String password);
    }
}
