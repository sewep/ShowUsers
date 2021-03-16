package pl.mr_electronics.showusers.ui.main;

import android.content.Context;

import java.util.List;

import pl.mr_electronics.showusers.model.UserObj;

public interface MainActivityContract {

    interface Presenter {
        void showDetailsUser(int position);
        void selectSortMethod(int sort_id);
    }

    interface View {
        void showList(List<UserObj> users);
    }
}
