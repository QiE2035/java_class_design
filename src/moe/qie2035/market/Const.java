package moe.qie2035.market;

public class Const {
    public static final String SUCCESS = "成功";
    public static final String FAILED = "失败";
    public static final int GAP = 10;
    public static final int GAP2 = 15;
    public static final int COL = 10;
    public static final String SEARCH = "s";
    public static final String PAGE = "p";
    public static final String AUTH = "authentication";
    public static final int PAGE_SIZE = 30;
    public static final String MIME = "application/json";
    public static final String OK = "确定";
    public static final String CANCEL = "取消";
    public static final String ID = "id";

    private Const() {
    }

    public enum API {
        USER, EMPLOYER, LOGIN, GOODS, MAJORS,SUBJECTS;
        public static final String API = "/api/";


        public String getPath() {
            return API + name().toLowerCase();
        }
    }
}
