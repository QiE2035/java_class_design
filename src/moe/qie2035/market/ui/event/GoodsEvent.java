package moe.qie2035.market.ui.event;

import moe.qie2035.market.client.GoodsClient;
import moe.qie2035.market.data.Goods;
import moe.qie2035.market.ui.dialog.GoodsDialog;
import moe.qie2035.market.ui.model.GoodsModel;

public class GoodsEvent extends AbsTableEvent<Goods, GoodsClient, GoodsModel> {
    public GoodsEvent(GoodsClient client, GoodsModel model) {
        super(client, model);
    }

    @Override
    public void newClick() {
        new GoodsDialog(new Goods(),
                model.getHeader(), true, client);
    }

    @Override
    public void modClick(int index) {
        new GoodsDialog(check(index),
                model.getHeader(), false, client);
    }

    @Override
    public void delClick(int index) {
        Goods goods = check(index);
        client.delete(goods.getId());
        update();
    }
}
