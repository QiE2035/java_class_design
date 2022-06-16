package moe.qie2035.market.utils;

import moe.qie2035.market.Const;

import javax.swing.*;

public class MsgBox {
    private MsgBox() {
    }

    public static void success(String msg) {
        JOptionPane.showMessageDialog(null,
                msg, Const.SUCCESS,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void success() {
        success(Const.SUCCESS);
    }

    public static void failed(String msg) {
        JOptionPane.showMessageDialog(null,
                msg, Const.FAILED,
                JOptionPane.ERROR_MESSAGE);
    }

    public static void failed() {
        failed(Const.FAILED);
    }

    public static void failed(Exception e) {
        failed(e.getMessage());
    }
}
