package moe.qie2035.market.ui.model;

import moe.qie2035.market.data.User;

import java.util.List;

public class UserModel extends AbsModel<User> {
    private static final String[] header =
            {"帐号", "密码 (加密)", "类型"};
    private List<User> userList;

    public UserModel(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public Object getValueAt(int row, int col) {
        User user = getOne(row);
        if (user == null) {
            return null;
        }
        return switch (col) {
            case 0 -> user.getName();
            case 1 -> user.getPassword();
            case 2 -> user.getUserType().getName();
            default -> null;
        };
    }


    @Override
    public String[] getHeader() {
        return header;
    }

    @Override
    public void setData(List<User> data) {
        userList = data;
    }

    @Override
    public User getOne(int row) {
        if (row + 1 > userList.size()) {
            return null;
        }
        return userList.get(row);
    }
}
