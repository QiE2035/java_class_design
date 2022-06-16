package moe.qie2035.market.ui;

import moe.qie2035.market.Const;
import moe.qie2035.market.server.Server;
import moe.qie2035.market.ui.dialog.CfgDialog;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptMenu extends JMenu implements ActionListener {
    public static final String START = "开启服务器";
    public static final String ABOUT = "关于";
    private static OptMenu optMenu;
    private static JMenuBar menuBar;
    private JMenuItem srvItem, cfgItem, aboutItem;

    private OptMenu() {
        setText("选项");
    }

    public static JMenuBar getMenuBar() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
        }
        if (optMenu == null) {
            optMenu = new OptMenu();
        }
        menuBar.add(optMenu);
        optMenu.init();
        optMenu.bind();
        return menuBar;
    }

    private void init() {
        srvItem = new JMenuItem(START);
        add(srvItem);

        cfgItem = new JMenuItem("设置");
        add(cfgItem);

        aboutItem = new JMenuItem(ABOUT);
        add(aboutItem);
    }

    private void bind() {
        srvItem.addActionListener(this);
        cfgItem.addActionListener(this);
        aboutItem.addActionListener(this);
    }

    private void onSrv() {
        final Server server = Server.get();
        if (server.isAlive()) {
            server.stop();
            MsgBox.success();
            srvItem.setText(START);
        } else {
            try {
                server.start();
                MsgBox.success();
                srvItem.setText("关闭服务器");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.failed("启动"
                        + Const.FAILED +
                        ", 可能是端口已被占用");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == srvItem) {
            onSrv();
        } else if (source == cfgItem) {
            CfgDialog.create();
        } else if (source == aboutItem) {
            JOptionPane.showMessageDialog(null,
                    "由 企鹅2035 制作", ABOUT,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
