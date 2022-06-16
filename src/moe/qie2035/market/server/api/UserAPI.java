package moe.qie2035.market.server.api;

import com.alibaba.fastjson.JSON;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.router.RouterNanoHTTPD.UriResource;
import moe.qie2035.market.data.User;
import moe.qie2035.market.server.CNStatus;
import moe.qie2035.market.utils.SQL;

import java.util.Map;

public class UserAPI extends JsonAPI {

    private static final User.Type TYPE = User.Type.ADMIN;
    private static final String NAME = "name";

    @Override
    public Response get(UriResource uriResource,
                        Map<String, String> urlParams,
                        IHTTPSession session) {
        try {
            if (!LoginAPI.check(session, TYPE)) {
                return response(CNStatus.FORBIDDEN);
            }
            String name = urlParams.get(NAME);
            if (name == null) {

                return response(CNStatus.OK, SQL.get().select(
                        new User(), SQL.getCondition(session)));

            }
            User user = new User().findOne(name);
            if (user == null) {
                return response(CNStatus.NOT_FOUND);
            }
            return response(CNStatus.OK, user);
        } catch (Exception e) {
            e.printStackTrace();
            return response(CNStatus.INTERNAL_ERROR);
        }
    }

    @Override
    public Response put(UriResource uriResource,
                        Map<String, String> urlParams,
                        IHTTPSession session) {
        try {
            if (!LoginAPI.check(session, TYPE)) {
                return response(CNStatus.FORBIDDEN);
            }
            String body = getPutBody(session);
            String name = urlParams.get(NAME);
            if (body == null || name == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            User oldUser = new User().findOne(name);
            if (oldUser == null) {
                return response(CNStatus.NOT_FOUND);
            }
            User newUser = JSON.parseObject(body, User.class);
            SQL.get().update(oldUser, newUser);
            return response(CNStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return response(CNStatus.INTERNAL_ERROR);
        }
    }

    @Override
    public Response post(UriResource uriResource,
                         Map<String, String> urlParams,
                         IHTTPSession session) {
        try {
            if (!LoginAPI.check(session, TYPE)) {
                return response(CNStatus.FORBIDDEN);
            }
            String body = getPostBody(session);
            String name = urlParams.get(NAME);
            if (body == null || name == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            User oldUser = new User().findOne(name);
            if (oldUser == null) {
                User newUser = JSON.parseObject(body, User.class);
                SQL.get().insert(newUser);
                return response(CNStatus.CREATED);
            }
            return response(CNStatus.FORBIDDEN, "用户已存在");
        } catch (Exception e) {
            e.printStackTrace();
            return response(CNStatus.INTERNAL_ERROR);
        }
    }

    @Override
    public Response delete(UriResource uriResource,
                           Map<String, String> urlParams,
                           IHTTPSession session) {
        try {
            if (!LoginAPI.check(session, TYPE)) {
                return response(CNStatus.FORBIDDEN);
            }
            String name = urlParams.get(NAME);
            if (name == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            new User().delOne(name);
            return response(CNStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return response(CNStatus.INTERNAL_ERROR);
        }
    }
}
