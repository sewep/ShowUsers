package pl.mr_electronics.showusers.model.tools;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pl.mr_electronics.showusers.model.UserObj;
import pl.mr_electronics.showusers.model.ado.BitbucketADO;

public class UserBitbucketGetter extends RestClient {
    ResponseListener respListener;

    public UserBitbucketGetter(ResponseListener respListener) {
        if (respListener == null)
            throw new IllegalArgumentException("respListener can't be null.");
        this.respListener = respListener;
    }

    public void downloadList() {
        sendGet("https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description");
    }

    private List<UserObj> parseResponse(String msg) {
        Gson gson = new Gson();
        BitbucketADO obj = gson.fromJson(msg, BitbucketADO.class);

        List<UserObj> list = new ArrayList<>();
        for(BitbucketADO.Value v : obj.values) {
            if (isListContains(list, v.owner.display_name)) continue;

            UserObj u = new UserObj();
            u.reposytory = "Bitbucket";
            u.name = v.owner.display_name;
            u.avatar_url = v.owner.links.avatar.href;
            list.add(u);
        }
        return list;
    }

    boolean isListContains(List<UserObj> list, String name) {
        for (UserObj o : list) {
            if (o.name.equals(name)) return true;
        }
        return false;
    }



    @Override
    public void responseMessage(String msg) {
        List<UserObj> list = parseResponse(msg);
        if (respListener != null)
            respListener.ReceiveNewUserList(list);
    }

    @Override
    public void responseError(String log) {
        if (respListener != null)
            respListener.ReceviedNewUserError(log);
    }
}
