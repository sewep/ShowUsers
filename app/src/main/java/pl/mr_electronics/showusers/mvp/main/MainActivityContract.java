package pl.mr_electronics.showusers.mvp.main;

import java.util.List;

import pl.mr_electronics.showusers.model.UserObj;

public interface MainActivityContract {

    interface Presenter {
        void downloadBase();
        void showDetailsUser();
    }

    interface View {
        void showLoadingStatus();
        void showList(List<UserObj> users);
    }
}
