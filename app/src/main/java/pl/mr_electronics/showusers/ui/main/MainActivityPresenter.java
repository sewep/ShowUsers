package pl.mr_electronics.showusers.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AlertDialog;

import pl.mr_electronics.showusers.Globals;
import pl.mr_electronics.showusers.model.UserList;
import pl.mr_electronics.showusers.model.UserListCom;
import pl.mr_electronics.showusers.ui.details.DetailsActivityView;

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

            if (users.getUsers() != null && users.getUsers().size() > 0)
                view.showList(users.getUsers());
        }
    }

    @Override
    public void loadListReady() {
        selectSortMethod(selectedSortMethodId);
        view.showList(users.getUsers());
    }

    @Override
    public void loadError(String msg) {
        new Handler(Looper.getMainLooper()).post(() -> new AlertDialog.Builder(Globals.context)
                .setTitle("Error")
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // Continue with delete operation
                    System.exit(0);
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
    }
}
