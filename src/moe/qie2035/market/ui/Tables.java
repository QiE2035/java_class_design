package moe.qie2035.market.ui;

public enum Tables {
    EMPLOYER("就业单位"),
    EMPLOYMENT("就业"),
    SUBJECTS("科目"),
    GRADES("成绩"),
    GRADUATES("毕业生"),
    MAJORS("专业"),
    USER("用户");

    private final String title;

    Tables(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
