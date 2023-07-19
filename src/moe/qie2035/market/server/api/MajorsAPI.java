package moe.qie2035.market.server.api;

import com.alibaba.fastjson.JSON;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.router.RouterNanoHTTPD.UriResource;
import moe.qie2035.market.Const;
import moe.qie2035.market.data.Majors;
import moe.qie2035.market.data.User;
import moe.qie2035.market.server.CNStatus;
import moe.qie2035.market.utils.SQL;

import java.util.Map;

public class MajorsAPI extends JsonAPI {
    private static final User.Type TYPE = User.Type.NORMAL;

    @Override
    public Response get(UriResource uriResource,
                        Map<String, String> urlParams,
                        IHTTPSession session) {
        try {


            String id = urlParams.get(Const.ID);
            if (id == null) {
                return response(CNStatus.OK,
                        SQL.get().select(new Majors(),
                                SQL.getCondition(session)));

            }
            Majors goods = new Majors().findOne(Integer.parseInt(id));
            if (goods == null) {
                return response(CNStatus.NOT_FOUND);
            }
            return response(CNStatus.OK, goods);
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
            String id = urlParams.get(Const.ID);
            if (id == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            new Majors().delOne(Integer.parseInt(id));
            return response(CNStatus.OK);
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
            String id = urlParams.get(Const.ID);
            if (body == null || id == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            Majors oldMajors = new Majors().findOne(Integer.parseInt(id));
            if (oldMajors == null) {
                return response(CNStatus.NOT_FOUND);
            }
            Majors newMajors = JSON.parseObject(body, Majors.class);
            newMajors.setMajorId(null);
            SQL.get().update(oldMajors, newMajors);
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
            final String body = getPostBody(session);
            if (body == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            Majors newMajors = JSON.parseObject(body, Majors.class);
            SQL.get().insert(newMajors);
            return response(CNStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return response(CNStatus.INTERNAL_ERROR);
        }
    }
}
