package pl.mr_electronics.showusers.mvp.main;

import java.util.List;

import pl.mr_electronics.showusers.model.UserList;
import pl.mr_electronics.showusers.model.UserListCom;
import pl.mr_electronics.showusers.model.UserObj;

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
        users.sortByRepositoryName();
        view.showList(users.getUsers());
    }
}
