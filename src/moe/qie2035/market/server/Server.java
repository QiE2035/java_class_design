package moe.qie2035.market.server;

import fi.iki.elonen.router.RouterNanoHTTPD;
import moe.qie2035.market.Config;
import moe.qie2035.market.Const.API;
import moe.qie2035.market.server.api.*;

public class Server extends RouterNanoHTTPD {
    public static final String ID = "/:id";
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

    /*public static Server refresh() {
        get().stop();
        server = null;
        return get();
    }*/

    @Override
    public void addMappings() {
        super.addMappings();
        removeRoute("/");
        removeRoute("/index.html");

        addRoute(API.LOGIN.getPath(), LoginAPI.class);

        addRoute(API.USER.getPath(), UserAPI.class);
        addRoute(API.USER.getPath() + "/:name", UserAPI.class);

//        addRoute(API.GOODS.getPath(), GoodsAPI.class);
//        addRoute(API.GOODS.getPath() + ID, GoodsAPI.class);

        addRoute(API.EMPLOYER.getPath(), EmployerAPI.class);
        addRoute(API.EMPLOYER.getPath() + ID, EmployerAPI.class);

        addRoute(API.MAJORS.getPath(), MajorsAPI.class);
        addRoute(API.MAJORS.getPath() + ID, MajorsAPI.class);

        addRoute(API.SUBJECTS.getPath(), SubjectsAPI.class);
        addRoute(API.SUBJECTS.getPath() + ID, SubjectsAPI.class);
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            final String uri = session.getUri();
            if (!uri.contains(API.API)) {
                return super.serve(session);
            }
            if (uri.equals(API.LOGIN.getPath())
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