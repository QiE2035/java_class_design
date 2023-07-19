package moe.qie2035.market.client;

import com.alibaba.fastjson.JSON;
import com.itgowo.httpclient.httpclient.HttpMethod;
import moe.qie2035.market.Const.API;
import moe.qie2035.market.data.Employer;
import moe.qie2035.market.data.Msg;
import moe.qie2035.market.data.User;


import java.util.List;

public class EmployerClient extends AbsClient<Employer, Integer> {

    public EmployerClient(User user) {
        super(user);
    }

    @Override
    public List<Employer> get(String search, int page) {
        return JSON.parseArray(request(
                genPath(API.EMPLOYER.getPath(), search, page),
                HttpMethod.GET, null), Employer.class);
    }

    @Override
    public Msg post(Employer goods) {
        return requestMsg(
                genPath(API.EMPLOYER.getPath(), goods.getEmployerId()),
                HttpMethod.POST, goods);
    }

    @Override
    public Msg put(Employer goods, Integer id) {
        return requestMsg(
                genPath(API.EMPLOYER.getPath(), id),
                HttpMethod.PUT, goods);
    }

    @Override
    public Msg delete(Integer id) {
        return requestMsg(
                genPath(API.EMPLOYER.getPath(), id),
                HttpMethod.DELETE, null);
    }
}
