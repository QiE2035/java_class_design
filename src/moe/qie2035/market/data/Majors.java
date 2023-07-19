package moe.qie2035.market.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import moe.qie2035.market.utils.SQL;
import org.teasoft.bee.osql.annotation.PrimaryKey;
import org.teasoft.bee.osql.annotation.Table;

@Data
@Table("majors")
public class Majors implements IDataOne<Majors, Integer> {
    @PrimaryKey
    private Integer majorId;
    private String majorName;
    private String degreeType;

    @Override
    public Majors findOne(Integer id) {
        try {
            setMajorId(id);
            return SQL.get().selectOne(this);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delOne(Integer id) {
        setMajorId(id);
        SQL.get().delete(this);
    }

}