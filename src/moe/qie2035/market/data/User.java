package moe.qie2035.market.data;

import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import moe.qie2035.market.utils.SQL;
import org.teasoft.bee.osql.annotation.PrimaryKey;
import org.teasoft.bee.osql.annotation.Table;

@Data
@Table("users")
public class User implements IDataOne<User, String> {
    @PrimaryKey
    private String name;
    private String password;
    private String userType;

    @Override
    public User findOne(String name) {
        try {
            setName(name);
            return SQL.get().selectOne(this);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delOne(String name) {
        setName(name);
        SQL.get().delete(this);
    }

    public Type getUserType() {
        try {
            return Type.valueOf(userType);
        } catch (Exception e) {
            return null;
        }
    }

    public void setUserType(Type userType) {
        this.userType = userType.name();
    }

    @Getter
    public enum Type {
        GUEST("游客"),
        NORMAL("普通"),
        ADMIN("管理员");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @SneakyThrows
        public static Type from(String name) {
            for (Type type : Type.values()) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
            throw new Exception("未选择用户类型");
        }
    }

}
