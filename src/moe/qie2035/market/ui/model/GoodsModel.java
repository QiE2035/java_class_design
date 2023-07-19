package moe.qie2035.market.ui.model;

import moe.qie2035.market.data.Goods;

import java.util.List;

public class GoodsModel extends AbsModel<Goods> {
    public GoodsModel(List<Goods> dataList) {
        super(dataList);
        header = new String[]{"ID", "商品名", "价格", "数量", "备注"};
    }

    @Override
    protected Object getValueByCol(Goods oneRow, int col) {
        return null;
    }
}
