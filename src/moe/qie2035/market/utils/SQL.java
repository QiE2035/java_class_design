package moe.qie2035.market.utils;

import fi.iki.elonen.NanoHTTPD;
import lombok.SneakyThrows;
import moe.qie2035.market.Config;
import moe.qie2035.market.Const;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactoryHelper;
import org.teasoft.honey.osql.core.ConditionImpl;
import org.teasoft.honey.osql.core.HoneyConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SQL {
    private static SuidRich suid;

    private SQL() {
    }

    public static SuidRich get() {
        if (suid == null) {
            refresh();
            suid = BeeFactoryHelper.getSuidRich();
        }
        return suid;
    }

    public static void refresh() {
        final HoneyConfig config = HoneyConfig.getHoneyConfig();
        final Config.Database database = Config.get().getDatabase();
        config.setDbName(database.getType().getName());
        config.setDriverName(database.getType().getDrvName());
        config.setUrl(genUrl(database));
        if (database.getType() == Config.Database.Type.MYSQL
                || database.getType() == Config.Database.Type.MSSQL) {
            config.setUsername(database.getUsername());
            config.setPassword(database.decPassword());
        }
    }

    @SneakyThrows
    private static void initH2() {
        String fileName = Config.get().getDatabase().getName() + ".mv.db";
        File file = new File(fileName);
        if (file.exists()) return;
        InputStream is = SQL.class.getResourceAsStream("/demo.mv.db");
        if (is == null) return;
        byte[] bytes = new byte[102400];
        FileOutputStream os = new FileOutputStream(file);
        int index;
        while ((index = is.read(bytes)) != -1) {
            os.write(bytes, 0, index);
        }
        os.flush();
        os.close();
        is.close();
    }

    private static String genUrl(Config.Database database) {
        StringBuilder builder = new StringBuilder("jdbc:");
        final Config.Database.Type dbType = database.getType();
        builder.append(dbType.getName().toLowerCase()
                .replaceAll(" ", ""));
        builder.append(':');
        switch (dbType) {
            case H2 -> {
                initH2();
                builder.append("./");
                builder.append(database.getName());
            }
            case MYSQL -> {
                builder.append("//");
                builder.append(database.getHost());
                builder.append(':');
                builder.append(database.getPort());
                builder.append('/');
                builder.append(database.getName());
            }
            case MSSQL -> {
                builder.append("//");
                builder.append(database.getHost());
                builder.append(':');
                builder.append(database.getPort());
                builder.append(";database=");
                builder.append(database.getName());
                builder.append(";trustServerCertificate=true");
            }
        }
        return builder.toString();
    }

    public static ConditionImpl getCondition(NanoHTTPD.IHTTPSession session) {
        final ConditionImpl condition = new ConditionImpl();
//        String search = session.getParms().get(Const.SEARCH);
//        if (search != null) {
//            condition.op("name", Op.like, "%" + search + "%");
//        }
        final String page = session.getParms().get(Const.PAGE);
        if (page != null) {
            final int pageNum = Integer.parseInt(page);
            condition.start(Math.max((pageNum - 1) * Const.PAGE_SIZE, 0))
                    .size(Const.PAGE_SIZE);
        }
        return condition;
    }
}