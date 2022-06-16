package moe.qie2035.market.data;

import lombok.Data;
import moe.qie2035.market.utils.SQL;
import org.teasoft.bee.osql.annotation.PrimaryKey;
import org.teasoft.bee.osql.annotation.Table;

@Data
@Table("goods")
public class Goods implements IDataOne<Goods, Integer> {
    @PrimaryKey
    private Integer id;
    private String name;
    private Double prince;
    private Integer count;
    private String remark;

    @Override
    public Goods findOne(Integer id) {
        try {
            setId(id);
            return SQL.get().select(this).get(0);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delOne(Integer id) {
        setId(id);
        SQL.get().delete(this);
    }
}
