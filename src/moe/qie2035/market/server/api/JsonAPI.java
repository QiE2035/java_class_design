package moe.qie2035.market.server.api;

import com.alibaba.fastjson.JSON;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Response.IStatus;
import fi.iki.elonen.router.RouterNanoHTTPD.UriResource;
import fi.iki.elonen.router.RouterNanoHTTPD.UriResponder;
import lombok.SneakyThrows;
import moe.qie2035.market.Const;
import moe.qie2035.market.data.Msg;
import moe.qie2035.market.server.CNStatus;
import moe.qie2035.market.utils.Crypto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonAPI implements UriResponder {

    public static Response response(IStatus status, Object obj) {
        if (obj instanceof String) {
            obj = new Msg((String) obj);
        }
        final String jsonStr = JSON.toJSONString(obj);
        return NanoHTTPD.newFixedLengthResponse(
                status, Const.MIME, Crypto.enc(jsonStr));
    }

    public static Response response(CNStatus status) {
        return response(status, status.getMsg());
    }

    @SneakyThrows
    public String getPostBody(IHTTPSession session) {
        HashMap<String, String> hashMap = new HashMap<>();
        session.parseBody(hashMap);
        return Crypto.dec(hashMap.get("postData"));
    }

    @SneakyThrows
    public String getPutBody(IHTTPSession session) {
        HashMap<String, String> hashMap = new HashMap<>();
        session.parseBody(hashMap);
        return Crypto.dec(new String(
                Files.readAllBytes(
                        Path.of(hashMap.get("content")))));
    }

    @Override
    public Response get(UriResource uriResource,
                        Map<String, String> urlParams,
                        IHTTPSession session) {
        return response(CNStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public Response put(UriResource uriResource,
                        Map<String, String> urlParams,
                        IHTTPSession session) {
        return get(uriResource, urlParams, session);
    }

    @Override
    public Response post(UriResource uriResource,
                         Map<String, String> urlParams,
                         IHTTPSession session) {
        return get(uriResource, urlParams, session);
    }

    @Override
    public Response delete(UriResource uriResource,
                           Map<String, String> urlParams,
                           IHTTPSession session) {
        return get(uriResource, urlParams, session);
    }

    @Override
    public Response other(String s,
                          UriResource uriResource,
                          Map<String, String> urlParams,
                          NanoHTTPD.IHTTPSession session) {
        return get(uriResource, urlParams, session);
    }
}
