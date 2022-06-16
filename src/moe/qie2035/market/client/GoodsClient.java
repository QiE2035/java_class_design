package moe.qie2035.market.client;

import com.alibaba.fastjson.JSON;
import com.itgowo.httpclient.httpclient.HttpMethod;
import moe.qie2035.market.Const;
import moe.qie2035.market.data.Goods;
import moe.qie2035.market.data.Msg;
import moe.qie2035.market.data.User;

import java.util.List;

public class GoodsClient extends AbsClient<Goods, Integer> {

    public GoodsClient(User user) {
        super(user);
    }

    @Override
    public List<Goods> get(String search, int page) {
        return JSON.parseArray(request(
                genPath(Const.GOODS, search, page),
                HttpMethod.GET, null), Goods.class);
    }

    @Override
    public Msg post(Goods goods) {
        return requestMsg(
                genPath(Const.GOODS, goods.getId()),
                HttpMethod.POST, goods);
    }

    @Override
    public Msg put(Goods goods, Integer id) {
        return requestMsg(
                genPath(Const.GOODS, id),
                HttpMethod.PUT, goods);
    }

    @Override
    public Msg delete(Integer id) {
        return requestMsg(
                genPath(Const.GOODS, id),
                HttpMethod.DELETE, null);
    }
}
