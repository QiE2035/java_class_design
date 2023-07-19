package moe.qie2035.market.ui.event;

import moe.qie2035.market.client.MajorsClient;
import moe.qie2035.market.data.Majors;
import moe.qie2035.market.ui.dialog.MajorsDialog;
import moe.qie2035.market.ui.model.MajorsModel;

public class MajorsEvent extends AbsTableEvent<Majors, MajorsClient, MajorsModel> {
    public MajorsEvent(MajorsClient client, MajorsModel model) {
        super(client, model);
    }

    @Override
    public void newClick() {
        new MajorsDialog(new Majors(),
                model.getHeader(), true, client);
    }

    @Override
    public void modClick(int index) {
        new MajorsDialog(check(index),
                model.getHeader(), false, client);
    }

    @Override
    public void delClick(int index) {
        Majors goods = check(index);
        client.delete(goods.getMajorId());
        update();
    }
}
