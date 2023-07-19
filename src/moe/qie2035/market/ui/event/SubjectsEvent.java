package moe.qie2035.market.ui.event;

import moe.qie2035.market.client.SubjectsClient;
import moe.qie2035.market.data.Subjects;
import moe.qie2035.market.ui.dialog.SubjectsDialog;
import moe.qie2035.market.ui.model.SubjectsModel;

public class SubjectsEvent extends AbsTableEvent<Subjects, SubjectsClient, SubjectsModel> {
    public SubjectsEvent(SubjectsClient client, SubjectsModel model) {
        super(client, model);
    }

    @Override
    public void newClick() {
        new SubjectsDialog(new Subjects(),
                model.getHeader(), true, client);
    }

    @Override
    public void modClick(int index) {
        new SubjectsDialog(check(index),
                model.getHeader(), false, client);
    }

    @Override
    public void delClick(int index) {
        Subjects goods = check(index);
        client.delete(goods.getSubjectId());
        update();
    }
}
