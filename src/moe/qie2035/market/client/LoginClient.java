package moe.qie2035.market.client;

import com.itgowo.httpclient.httpclient.HttpMethod;
import lombok.SneakyThrows;
import moe.qie2035.market.Const;
import moe.qie2035.market.data.Msg;
import moe.qie2035.market.data.User;

import java.util.List;

public class LoginClient extends AbsClient<User, String> {
    public LoginClient() {
        super(null);
    }

    @Override
    @SneakyThrows
    public List<User> get(String search, int page) {
        throw new Exception("不允许的请求");
    }

    @Override
    public Msg post(User user) {
        return requestMsg(Const.LOGIN,
                HttpMethod.POST, user);
    }

    @Override
    @SneakyThrows
    public Msg put(User data, String param) {
        throw new Exception("不允许的请求");
    }

    @Override
    @SneakyThrows
    public Msg delete(String param) {
        throw new Exception("不允许的请求");
    }
}
