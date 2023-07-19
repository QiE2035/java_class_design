package moe.qie2035.market.client;

import com.alibaba.fastjson.JSON;
import com.itgowo.httpclient.httpclient.HttpMethod;
import moe.qie2035.market.Const.API;
import moe.qie2035.market.data.Msg;
import moe.qie2035.market.data.Subjects;
import moe.qie2035.market.data.User;

import java.util.List;

public class SubjectsClient extends AbsClient<Subjects, Integer> {

    public SubjectsClient(User user) {
        super(user);
    }

    @Override
    public List<Subjects> get(String search, int page) {
        return JSON.parseArray(request(
                genPath(API.SUBJECTS.getPath(), search, page),
                HttpMethod.GET, null), Subjects.class);
    }

    @Override
    public Msg post(Subjects goods) {
        return requestMsg(
                genPath(API.SUBJECTS.getPath(), goods.getSubjectId()),
                HttpMethod.POST, goods);
    }

    @Override
    public Msg put(Subjects goods, Integer id) {
        return requestMsg(
                genPath(API.SUBJECTS.getPath(), id),
                HttpMethod.PUT, goods);
    }

    @Override
    public Msg delete(Integer id) {
        return requestMsg(
                genPath(API.SUBJECTS.getPath(), id),
                HttpMethod.DELETE, null);
    }
}
