package moe.qie2035.market.ui.event;

import lombok.SneakyThrows;
import moe.qie2035.market.client.AbsClient;
import moe.qie2035.market.ui.model.AbsModel;

public abstract class AbsTableEvent
        <T, C extends AbsClient<T, ?>,
                M extends AbsModel<T>>
        implements ITableEvent {
    protected int page = 1;
    C client;
    M model;
    private String search;

    public AbsTableEvent(C client, M model) {
        this.client = client;
        this.model = model;
    }

    @Override
    public void preClick() {
        if (page > 1) {
            page--;
            update();
        }
    }

    @Override
    public void searchClick(String search) {
        this.search = search;
        update();
    }

    protected void update() {
        model.setDataList(client.get(search, page));
        model.fireTableDataChanged();
    }

    @SneakyThrows
    protected T check(int index) {
        T data = model.getOneRow(index);
        if (data == null) {
            throw new Exception("选择了空行");
        }
        return data;
    }

    @Override
    public void nextClick() {
        update();
        /*if (model.getOneRow(0) == null) {
            preClick();
        }*/
    }
}
