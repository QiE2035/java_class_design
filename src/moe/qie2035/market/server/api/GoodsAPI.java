package moe.qie2035.market.server.api;

import com.alibaba.fastjson.JSON;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.router.RouterNanoHTTPD.UriResource;
import moe.qie2035.market.data.Goods;
import moe.qie2035.market.data.User;
import moe.qie2035.market.server.CNStatus;
import moe.qie2035.market.utils.SQL;

import java.util.Map;

public class GoodsAPI extends JsonAPI {
    private static final User.Type TYPE = User.Type.NORMAL;
    private static final String ID = "id";

    @Override
    public Response get(UriResource uriResource,
                        Map<String, String> urlParams,
                        IHTTPSession session) {
        try {


            String id = urlParams.get(ID);
            if (id == null) {
                return response(CNStatus.OK,
                        SQL.get().select(new Goods(),
                                SQL.getCondition(session)));

            }
            Goods goods = new Goods().findOne(Integer.parseInt(id));
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
            String id = urlParams.get(ID);
            if (id == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            new Goods().delOne(Integer.parseInt(id));
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
            String id = urlParams.get(ID);
            if (body == null || id == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            Goods oldGoods = new Goods().findOne(Integer.parseInt(id));
            if (oldGoods == null) {
                return response(CNStatus.NOT_FOUND);
            }
            Goods newGoods = JSON.parseObject(body, Goods.class);
            SQL.get().update(oldGoods, newGoods);
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
            String name = urlParams.get(ID);
            if (body == null || name == null) {
                return response(CNStatus.BAD_REQUEST);
            }
            Goods oldGoods = new Goods().findOne(Integer.parseInt(name));
            if (oldGoods == null) {
                Goods newGoods = JSON.parseObject(body, Goods.class);
                SQL.get().insert(newGoods);
                return response(CNStatus.CREATED);
            }
            return response(CNStatus.FORBIDDEN, "ID冲突");
        } catch (Exception e) {
            e.printStackTrace();
            return response(CNStatus.INTERNAL_ERROR);
        }
    }
}
