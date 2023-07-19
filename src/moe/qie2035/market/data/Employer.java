package moe.qie2035.market.data;

import lombok.Data;
import moe.qie2035.market.utils.SQL;
import org.teasoft.bee.osql.annotation.PrimaryKey;
import org.teasoft.bee.osql.annotation.Table;

@Data
@Table("employer")
public class Employer implements IDataOne<Employer, Integer> {
    @PrimaryKey
    private Integer employerId;
    private String employerName;
    private String employerAddress;
    private String employerContact;

    @Override
    public Employer findOne(Integer id) {
        try {
            setEmployerId(id);
            return SQL.get().selectOne(this);//.get(0);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delOne(Integer id) {
        setEmployerId(id);
        SQL.get().delete(this);
    }

}