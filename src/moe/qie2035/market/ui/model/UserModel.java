package moe.qie2035.market.ui.model;

import moe.qie2035.market.data.Majors;
import moe.qie2035.market.data.User;

import java.util.List;

public class UserModel extends AbsModel<User> {

    public UserModel(List<User> dataList) {
        super(dataList);
        header = new String[]{"帐号", "密码 (加密)", "类型"};
    }

    @Override
    protected Object getValueByCol(User oneRow, int col) {
        return switch (col) {
            case 0 -> oneRow.getName();
            case 1 -> oneRow.getPassword();
            case 2 -> oneRow.getUserType();
            default -> null;
        };
    }
}
