package pl.mr_electronics.showusers.mvp.main;

import pl.mr_electronics.showusers.model.UserList;
import pl.mr_electronics.showusers.model.UserListCom;

class MainActivityPresenter implements MainActivityContract.Presenter, UserListCom {

    MainActivityContract.View view;
    UserList users;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;

        users = new UserList();
        users.setUserListCom(this);
        users.downloadUsersLists();
    }

    @Override
    public void downloadBase() {

    }

    @Override
    public void showDetailsUser() {

    }

    @Override
    public void loadListReady() {
        view.showList(users.getUsers());
    }
}
