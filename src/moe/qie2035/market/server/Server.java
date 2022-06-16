package moe.qie2035.market.server;

import fi.iki.elonen.router.RouterNanoHTTPD;
import moe.qie2035.market.Config;
import moe.qie2035.market.Const;
import moe.qie2035.market.server.api.GoodsAPI;
import moe.qie2035.market.server.api.JsonAPI;
import moe.qie2035.market.server.api.LoginAPI;
import moe.qie2035.market.server.api.UserAPI;

public class Server extends RouterNanoHTTPD {
    private static Server server;

    private Server() {
        super(Config.get().getServer().getPort());
        setAsyncRunner(new BoundRunner(
                Config.get().getServer().getThreads()));
        addMappings();
    }

    public static Server get() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public static Server refresh() {
        get().stop();
        server = null;
        return get();
    }

    @Override
    public void addMappings() {
        super.addMappings();
        removeRoute("/");
        removeRoute("/index.html");
        addRoute(Const.LOGIN, LoginAPI.class);
        addRoute(Const.USER, UserAPI.class);
        addRoute(Const.USER + "/:name", UserAPI.class);
        addRoute(Const.GOODS, GoodsAPI.class);
        addRoute(Const.GOODS + "/:id", GoodsAPI.class);
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            final String uri = session.getUri();
            if (!uri.contains("/api/")) {
                return super.serve(session);
            }
            if (uri.equals(Const.LOGIN)
                    || LoginAPI.check(session, null)) {
                return super.serve(session);
            } else {
                return JsonAPI.response(CNStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonAPI.response(CNStatus.INTERNAL_ERROR);
        }
    }
}