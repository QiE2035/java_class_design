package moe.qie2035.market.ui.model;

import lombok.Getter;
import lombok.Setter;
import moe.qie2035.market.Const;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public abstract class AbsModel<T> extends AbstractTableModel {
    @Getter
    protected String[] header;
    @Setter
    protected List<T> dataList;

    public AbsModel(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getRowCount() {
        return Math.min(Const.PAGE_SIZE, dataList.size());
    }

    @Override
    public String getColumnName(int col) {
        return getHeader()[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        T oneRow = getOneRow(row);
        if (oneRow == null) {
            return null;
        }
        return getValueByCol(oneRow, col);
    }

    public T getOneRow(int row) {
        if (dataList == null) {
            return null;
        }
        return dataList.get(row);
    }

    protected abstract Object getValueByCol(T oneRow, int col);


    @Override
    public int getColumnCount() {
        return getHeader().length;
    }
}
