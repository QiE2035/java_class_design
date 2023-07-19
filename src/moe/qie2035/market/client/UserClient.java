package moe.qie2035.market.client;

import com.alibaba.fastjson.JSON;
import com.itgowo.httpclient.httpclient.HttpMethod;
import moe.qie2035.market.Const;
import moe.qie2035.market.data.Msg;
import moe.qie2035.market.data.User;

import java.util.List;

public class UserClient extends AbsClient<User, String> {

    public UserClient(User user) {
        super(user);
    }

    @Override
    public List<User> get(String search, int page) {
        return JSON.parseArray(request(
                genPath(Const.API.USER.getPath(), search, page),
                HttpMethod.GET, null), User.class);
    }

    @Override
    public Msg post(User user) {
        return requestMsg(
                genPath(Const.API.USER.getPath(), user.getName()),
                HttpMethod.POST, user);

    }

    @Override
    public Msg put(User user, String name) {
        return requestMsg(genPath(Const.API.USER.getPath(), name),
                HttpMethod.PUT, user);
    }

    @Override
    public Msg delete(String name) {
        return requestMsg(genPath(Const.API.USER.getPath(), name),
                HttpMethod.DELETE, null);
    }
}
