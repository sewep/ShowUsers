package pl.mr_electronics.showusers.ui.details;

import pl.mr_electronics.showusers.model.UserObj;

interface DetailsActivityContract {

    interface View {
        void showUserDetails(UserObj userObj);
    }

    interface Presenter {
        void loadUserByIndex(int idx);
    }
}
