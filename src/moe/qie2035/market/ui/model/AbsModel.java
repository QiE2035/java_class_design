package moe.qie2035.market.ui.model;

import moe.qie2035.market.Const;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public abstract class AbsModel<T> extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return Const.PAGE_SIZE;
    }

    @Override
    public String getColumnName(int col) {
        return getHeader()[col];
    }

    @Override
    public int getColumnCount() {
        return getHeader().length;
    }

    public abstract String[] getHeader();

    public abstract void setData(List<T> data);

    public abstract T getOne(int row);
}
