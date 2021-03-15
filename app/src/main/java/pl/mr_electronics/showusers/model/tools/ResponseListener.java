package pl.mr_electronics.showusers.model.tools;

import java.util.List;

import pl.mr_electronics.showusers.model.UserObj;

public interface ResponseListener {

    void ReceiveNewUserList(List<UserObj> list);

    void ReceviedNewUserError(String str);
}
