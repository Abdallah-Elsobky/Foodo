package iti.student.foodo.features.auth.register.presenter;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;

public interface RegisterContract {
    interface View extends BaseView {
        void onRegisterSuccess();

        void onRegisterFailure(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void register(String email, String password);
    }
}
