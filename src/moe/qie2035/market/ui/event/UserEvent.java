package moe.qie2035.market.ui.event;

import moe.qie2035.market.client.UserClient;
import moe.qie2035.market.data.User;
import moe.qie2035.market.ui.dialog.UserDialog;
import moe.qie2035.market.ui.model.UserModel;

public class UserEvent extends AbsTableEvent<User, UserClient, UserModel> {
    public UserEvent(UserClient client, UserModel model) {
        super(client, model);
    }

    @Override
    public void newClick() {
        new UserDialog(new User(),
                model.getHeader(), true, client);
    }

    @Override
    public void modClick(int index) {
        new UserDialog(check(index),
                model.getHeader(), false, client);
    }

    @Override
    public void delClick(int index) {
        User user = check(index);
        client.delete(user.getName());
        update();
    }
}
