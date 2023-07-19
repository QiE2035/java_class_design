package moe.qie2035.market.ui.model;

import moe.qie2035.market.data.Majors;
import moe.qie2035.market.data.Subjects;

import java.util.List;

public class MajorsModel extends AbsModel<Majors> {
    public MajorsModel(List<Majors> dataList) {
        super(dataList);
        header = new String[]{"ID", "专业名称", "学位类型"};
    }

    @Override
    protected Object getValueByCol(Majors oneRow, int col) {
        return switch (col) {
            case 0 -> oneRow.getMajorId();
            case 1 -> oneRow.getMajorName();
            case 2 -> oneRow.getDegreeType();
            default -> null;
        };
    }
}
