package moe.qie2035.market.client;

import com.alibaba.fastjson.JSON;
import com.itgowo.httpclient.httpclient.HttpMethod;
import moe.qie2035.market.Const.API;
import moe.qie2035.market.data.Majors;
import moe.qie2035.market.data.Msg;
import moe.qie2035.market.data.User;

import java.util.List;

public class MajorsClient extends AbsClient<Majors, Integer> {

    public MajorsClient(User user) {
        super(user);
    }

    @Override
    public List<Majors> get(String search, int page) {
        return JSON.parseArray(request(
                genPath(API.MAJORS.getPath(), search, page),
                HttpMethod.GET, null), Majors.class);
    }

    @Override
    public Msg post(Majors goods) {
        return requestMsg(
                genPath(API.MAJORS.getPath(), goods.getMajorId()),
                HttpMethod.POST, goods);
    }

    @Override
    public Msg put(Majors goods, Integer id) {
        return requestMsg(
                genPath(API.MAJORS.getPath(), id),
                HttpMethod.PUT, goods);
    }

    @Override
    public Msg delete(Integer id) {
        return requestMsg(
                genPath(API.MAJORS.getPath(), id),
                HttpMethod.DELETE, null);
    }
}
