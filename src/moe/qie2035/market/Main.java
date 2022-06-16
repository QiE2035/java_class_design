package moe.qie2035.market;

import com.formdev.flatlaf.FlatLightLaf;
import com.itgowo.httpclient.httpclient.HttpClient;
import moe.qie2035.market.ui.frame.LoginFrame;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        HttpClient.setTimeout(5000);
        LoginFrame.create();
    }
}