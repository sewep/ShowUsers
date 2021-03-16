package pl.mr_electronics.showusers.ui.details;

import pl.mr_electronics.showusers.model.UserList;

class DetailsActivityPresenter implements DetailsActivityContract.Presenter {

    DetailsActivityContract.View view;

    public DetailsActivityPresenter(DetailsActivityContract.View view) {
        this.view = view;
    }


    @Override
    public void loadUserByIndex(int idx) {
        if (idx >= 0) {
            UserList userList = UserList.getInstance();
            view.showUserDetails(userList.getUsers().get(idx));
        }
    }
}
