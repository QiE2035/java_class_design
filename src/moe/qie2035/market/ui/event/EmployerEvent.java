package moe.qie2035.market.ui.event;

import moe.qie2035.market.client.EmployerClient;
import moe.qie2035.market.data.Employer;
import moe.qie2035.market.ui.dialog.EmployerDialog;
import moe.qie2035.market.ui.model.EmployerModel;

public class EmployerEvent extends AbsTableEvent<Employer, EmployerClient, EmployerModel> {
    public EmployerEvent(EmployerClient client, EmployerModel model) {
        super(client, model);
    }

    @Override
    public void newClick() {
        new EmployerDialog(new Employer(),
                model.getHeader(), true, client);
    }

    @Override
    public void modClick(int index) {
        new EmployerDialog(check(index),
                model.getHeader(), false, client);
    }

    @Override
    public void delClick(int index) {
        Employer goods = check(index);
        client.delete(goods.getEmployerId());
        update();
    }
}
