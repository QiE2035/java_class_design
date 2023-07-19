package moe.qie2035.market.data;

import lombok.Data;
import moe.qie2035.market.utils.SQL;
import org.teasoft.bee.osql.annotation.PrimaryKey;
import org.teasoft.bee.osql.annotation.Table;

@Data
@Table("subjects")
public class Subjects implements IDataOne<Subjects, Integer> {
    @PrimaryKey
    private Integer subjectId;
    private String subjectName;

    @Override
    public Subjects findOne(Integer id) {
        try {
            setSubjectId(id);
            return SQL.get().selectOne(this);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delOne(Integer id) {
        setSubjectId(id);
        SQL.get().delete(this);
    }

}