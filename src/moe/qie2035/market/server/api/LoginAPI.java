package moe.qie2035.market.server.api;

import com.alibaba.fastjson.JSON;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.router.RouterNanoHTTPD.UriResource;
import lombok.SneakyThrows;
import moe.qie2035.market.Const;
import moe.qie2035.market.data.User;
import moe.qie2035.market.server.CNStatus;
import moe.qie2035.market.utils.Crypto;

import java.util.Map;

public class LoginAPI extends JsonAPI {

    @SneakyThrows
    public static boolean check(String data, User.Type type) {
        final User loginUser =
                JSON.parseObject(data, User.class);
        final User findUser =
                new User().findOne(loginUser.getName());
        if (findUser == null) {
            return false;
        }
        if (type == null) {
            return findUser.getPassword()
                    .equals(loginUser.getPassword());
        } else return type.ordinal()
                <= findUser.getUserType().ordinal();
    }

    public static boolean check(
            IHTTPSession session, User.Type type) {
        return check(Crypto.dec(session.getHeaders().get(Const.AUTH)), type);
    }

    @Override
    public Response post(UriResource uriResource,
                         Map<String, String> urlParams,
                         IHTTPSession session) {
        String body = getPostBody(session);
        if (check(body, null)) {
            return response(CNStatus.OK);
        }
        return response(CNStatus.UNAUTHORIZED, Const.FAILED);
    }
}
