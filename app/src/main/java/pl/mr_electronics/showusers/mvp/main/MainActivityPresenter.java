package pl.mr_electronics.showusers.mvp.main;

import android.content.Intent;
import android.os.Bundle;

import pl.mr_electronics.showusers.Globals;
import pl.mr_electronics.showusers.model.UserList;
import pl.mr_electronics.showusers.model.UserListCom;
import pl.mr_electronics.showusers.mvp.details.DetailsActivityView;

class MainActivityPresenter implements MainActivityContract.Presenter, UserListCom {

    MainActivityContract.View view;
    UserList users;
    int selectedSortMethodId = 0;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;

        users = UserList.getInstance();
        users.setUserListCom(this);
        users.downloadUsersLists();
    }

    @Override
    public void downloadBase() {

    }

    @Override
    public void showDetailsUser(int position) {
        if (users != null) {
            Intent intent = new Intent(Globals.context, DetailsActivityView.class);
            Bundle b = new Bundle();
            b.putInt("position", position);
            intent.putExtras(b);
            Globals.context.startActivity(intent);
        }
    }

    @Override
    public void selectSortMethod(int sort_id) {
        selectedSortMethodId = sort_id;
        if (users != null) {
            switch (selectedSortMethodId) {
                case 0:
                    users.sortByRepositoryName();
                    break;
                case 1:
                    users.sortByUserName();
                    break;
            }

            view.showList(users.getUsers());
        }
    }

    @Override
    public void loadListReady() {
        selectSortMethod(selectedSortMethodId);
        view.showList(users.getUsers());
    }
}
