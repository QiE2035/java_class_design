package moe.qie2035.market.ui.model;

import moe.qie2035.market.data.Employer;
import moe.qie2035.market.data.Majors;

import java.util.List;

public class EmployerModel extends AbsModel<Employer> {
    public EmployerModel(List<Employer> dataList) {
        super(dataList);
        header = new String[]{"ID", "就业单位", "地址", "联系方式"};
    }

    @Override
    protected Object getValueByCol(Employer oneRow, int col) {
        return switch (col) {
            case 0 -> oneRow.getEmployerId();
            case 1 -> oneRow.getEmployerName();
            case 2 -> oneRow.getEmployerAddress();
            case 3 -> oneRow.getEmployerContact();
            default -> null;
        };
    }
}
