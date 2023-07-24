package moe.qie2035.market;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.Getter;
import moe.qie2035.market.utils.Crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

@Data
public class Config {

    private static final File FILE =
            new File("config.json");
    private static Config config;
    private Server server = new Server();
    private Client client = new Client();
    private Database database = new Database();

    private Config() {
    }

    public static Config get() {
        if (config == null) {
            config = new Config();
            load();
        }
        return config;
    }

    private static void load() {
        try {
            if (FILE.exists()) {
                new JSONReader(new FileReader(FILE))
                        .readObject(get());
            } else {
                get().save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            JSON.writeJSONString(
                    new FileOutputStream(FILE), this,
                    SerializerFeature.PrettyFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class Server {
        private int port = 2035;
        private int threads = 10;

        private Server() {
        }
    }

    @Data
    public static class Client {
        private String host = "localhost";
        private int port = 2035;

        private Client() {
        }
    }

    @Data
    public static class Database {
        private Type type = Type.MSSQL;
        private String name = "Graduate";
        private String host = "localhost";
        private int port = 1433;
        private String username = "sa";
        private String password = "";

        private Database() {
        }

        public String decPassword() {
            return Crypto.dec(password);
        }

        public void encPassword(String password) {
            this.password = Crypto.enc(password);
        }

        public enum Type {
//            H2("H2", "org.h2.Driver"),
//            MYSQL("MySQL", "com.mysql.cj.jdbc.Driver"),
            MSSQL("SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver");

            @Getter
            private final String name;
            private final String drvName;

            Type(String name, String drvName) {
                this.name = name;
                this.drvName = drvName;
            }

            public String getDrvName() {
                return drvName;
            }
        }
    }

}
