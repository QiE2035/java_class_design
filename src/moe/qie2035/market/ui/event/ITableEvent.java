package moe.qie2035.market.ui.event;

public interface ITableEvent {
    void preClick();

    void nextClick();

    void newClick();

    void modClick(int index);

    void delClick(int index);

    void searchClick(String search);
}
