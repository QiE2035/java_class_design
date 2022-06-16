package moe.qie2035.market.ui.model;

import moe.qie2035.market.data.Goods;

import java.util.List;

public class GoodsModel extends AbsModel<Goods> {
    private static final String[] header =
            {"ID", "商品名", "价格", "数量", "备注"};
    private List<Goods> goodsList;

    public GoodsModel(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Goods goods = getOne(row);
        if (goods == null) {
            return null;
        }
        return switch (col) {
            case 0 -> goods.getId();
            case 1 -> goods.getName();
            case 2 -> goods.getPrince();
            case 3 -> goods.getCount();
            case 4 -> goods.getRemark();
            default -> null;
        };
    }

    @Override
    public String[] getHeader() {
        return header;
    }

    @Override
    public void setData(List<Goods> data) {
        goodsList = data;
    }

    @Override
    public Goods getOne(int row) {
        if (row + 1 > goodsList.size()) {
            return null;
        }
        return goodsList.get(row);
    }
}
