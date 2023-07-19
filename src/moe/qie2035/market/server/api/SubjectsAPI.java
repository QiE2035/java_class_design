package moe.qie2035.market.server.api;

import com.alibaba.fastjson.JSON;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.router.RouterNanoHTTPD.UriResource;
import moe.qie2035.market.Const;
import moe.qie2035.market.data.Subjects;
import moe.qie2035.market.data.User;
import moe.qie2035.market.server.CNStatus;
import moe.qie2035.market.utils.SQL;

import java.util.Map;

public class SubjectsAPI extends JsonAPI {
    private static final User.Type TYPE = User.Type.NORMAL;

    @Override
    public Response get(UriResource uriResource,
                        Map<String, String> urlParams,
                        IHTTPSession session) {
        try {
            String id = urlParams.get(Const.ID);
            if (id == null) {
                return response(CNStatus.OK,
                        SQL.get().select(new Subjects(),
                                SQL.getCondition(session)));

            }
            Subjects subjects = new Subjects().findOne(Integer.parseInt(id));
            if (subjects == null) {
                return response(CNStatus.NOT_FOUND);
            }
            return response(CNStatus.OK, subjects);
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
            new Subjects().delOne(Integer.parseInt(id));
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
            Subjects oldSubjects = new Subjects().findOne(Integer.parseInt(id));
            if (oldSubjects == null) {
                return response(CNStatus.NOT_FOUND);
            }
            Subjects newSubjects = JSON.parseObject(body, Subjects.class);
            newSubjects.setSubjectId(null);
            SQL.get().update(oldSubjects, newSubjects);
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
            Subjects newSubjects = JSON.parseObject(body, Subjects.class);
            SQL.get().insert(newSubjects);
            return response(CNStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return response(CNStatus.INTERNAL_ERROR);
        }
    }
}
