package pl.mr_electronics.showusers.model.tools;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pl.mr_electronics.showusers.model.UserObj;
import pl.mr_electronics.showusers.model.ado.GitHubADO;

public class UserGithubGetter extends RestClient {
    ResponseListener respListener;

    public UserGithubGetter(ResponseListener respListener) {
        if (respListener == null)
            throw new IllegalArgumentException("respListener can't be null.");
        this.respListener = respListener;
    }

    public void downloadList() {
        sendGet("https://api.github.com/repositories");
    }

    private List<UserObj> parseResponse(String msg) {
        Gson gson = new Gson();
        GitHubADO[] obj = gson.fromJson(msg, GitHubADO[].class);

        List<UserObj> list = new ArrayList<>();
        for(GitHubADO o : obj) {
            //if (isListContains(list, o.owner.login)) continue;

            UserObj u = new UserObj();
            u.isHighlighted = false;
            u.reposytory = o.name;
            u.name = o.owner.login;
            u.avatar_url = o.owner.avatar_url;
            u.info = o.html_url;
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
