package pl.mr_electronics.showusers.model;

import java.util.ArrayList;
import java.util.List;

import pl.mr_electronics.showusers.model.tools.ResponseListener;

public class UserList implements ResponseListener {

    List<UserObj> users = new ArrayList<>();

    public void downloadUsersLists() {
        // Clear current list before update
        users.clear();
        // Download user list from Bitbucket
        UserBitbucketGetter u = new UserBitbucketGetter(this);
        u.downloadList();
        //TODO: Download users from GitHub
    }


    @Override
    public void ReceiveNewUserList(List<UserObj> list) {
        users.addAll(list);
        System.out.println("Receve new " + list + " users.");
    }

    @Override
    public void ReceviedNewUserError(String str) {

    }
}
