package pl.mr_electronics.showusers.model;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pl.mr_electronics.showusers.model.ado.BitbucketADO;
import pl.mr_electronics.showusers.model.tools.ResponseListener;
import pl.mr_electronics.showusers.model.tools.RestClient;

public class UserBitbucketGetter extends RestClient {
    List<UserObj> list = null;
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
        //System.out.println(list.toString());

        List<UserObj> list = new ArrayList<>();
        for(BitbucketADO.Value v : obj.values) {
            UserObj u = new UserObj();
            u.reposytory = "Bitbucket";
            u.name = v.owner.display_name;
            u.avatar_url = v.owner.links.avatar.href;
            list.add(u);
        }
        return list;
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
