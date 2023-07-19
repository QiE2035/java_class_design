package moe.qie2035.market.ui.model;

import moe.qie2035.market.data.Subjects;

import java.util.List;

public class SubjectsModel extends AbsModel<Subjects> {
    public SubjectsModel(List<Subjects> dataList) {
        super(dataList);
        header = new String[]{"ID", "科目名称"};
    }

    @Override
    protected Object getValueByCol(Subjects oneRow, int col) {
        return switch (col) {
            case 0 -> oneRow.getSubjectId();
            case 1 -> oneRow.getSubjectName();
            default -> null;
        };
    }
}
