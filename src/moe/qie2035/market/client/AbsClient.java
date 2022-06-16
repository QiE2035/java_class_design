package moe.qie2035.market.client;

import com.alibaba.fastjson.JSON;
import com.itgowo.httpclient.httpclient.HttpClient;
import com.itgowo.httpclient.httpclient.HttpMethod;
import com.itgowo.httpclient.httpclient.HttpResponse;
import lombok.SneakyThrows;
import moe.qie2035.market.Config;
import moe.qie2035.market.Const;
import moe.qie2035.market.data.Msg;
import moe.qie2035.market.data.User;
import moe.qie2035.market.utils.Crypto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbsClient<T, P> {

    protected final User user;

    protected AbsClient(User user) {
        this.user = user;
    }

    @SneakyThrows
    protected String request(String path, HttpMethod method, T data) {
        StringBuilder builder = new StringBuilder("http://");
        Config.Client client = Config.get().getClient();
        builder.append(client.getHost());
        builder.append(':');
        builder.append(client.getPort());
        builder.append(path);
        String url = builder.toString();

        Map<String, String> header = new HashMap<>();
        header.put(Const.AUTH, Crypto.enc(JSON.toJSONString(user)));
        header.put("Content-type", Const.MIME);


        String enc = null;
        if (data != null) {
            enc = Crypto.enc(JSON.toJSONString(data));
        }

        HttpResponse response;
        try {
            response = HttpClient.RequestSync(
                    url, method, header, null,
                    enc);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("服务器连接" + Const.FAILED);
        }
        String bodyStr = Crypto.dec(response.getBodyStr());
        if (!response.isSuccess()) {
            throw new Exception(JSON.parseObject(
                    bodyStr, Msg.class).getMsg());
        }

        return bodyStr;
    }

    protected Msg requestMsg(String path, HttpMethod method, T data) {
        return JSON.parseObject(request(path, method, data), Msg.class);
    }

    protected String genPath(String path, P param, String search, int page) {
        StringBuilder builder = new StringBuilder(path);
        if (param != null) {
            builder.append('/');
            builder.append(param);
        }
        builder.append('?');
        if (search != null) {
            builder.append("s=");
            builder.append(search);
        }
        builder.append('&');
        if (page > 1) {
            builder.append("p=");
            builder.append(page);
        }
        return builder.toString();
    }

    protected String genPath(String path, P param) {
        return genPath(path, param, null, 0);
    }

    protected String genPath(String path, String search, int page) {
        return genPath(path, null, search, page);
    }

    @SneakyThrows
    public List<T> get(int page) {
        return get(null, page);
    }

//    protected abstract T getCondition(P param);


    public abstract List<T> get(String search, int page);


    public abstract Msg post(T data);

    public abstract Msg put(T data, P param);

    public abstract Msg delete(P param);
}
