package moe.qie2035.market.ui.frame;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.LoginClient;
import moe.qie2035.market.data.User;
import moe.qie2035.market.ui.OptMenu;
import moe.qie2035.market.ui.dialog.ChooseDialog;
import moe.qie2035.market.utils.Crypto;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends AbsFrame {
    private static LoginFrame frame;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, exitBtn;
    private Box labelBox, fieldBox, btnBox;

    private LoginFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void create() {
        if (frame != null) {
            frame.dispose();
        }
        frame = new LoginFrame();
    }

    @Override
    protected void init() {
        setTitle("登录");
        initLayout();
        initComs();
        setJMenuBar(OptMenu.getMenuBar());
    }

    private void initComs() {
        labelBox.add(new JLabel("帐号"));

        labelBox.add(Box.createVerticalStrut(Const.GAP2));

        labelBox.add(new JLabel("密码"));

        usernameField = new JTextField(Const.COL);
        fieldBox.add(usernameField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        passwordField = new JPasswordField(Const.COL);
        fieldBox.add(passwordField);

        loginBtn = new JButton("登录");
        btnBox.add(loginBtn);

        btnBox.add(Box.createHorizontalStrut(Const.GAP));

        exitBtn = new JButton("退出");
        btnBox.add(exitBtn);
    }

    private void initLayout() {
        Box frameBox = Box.createVerticalBox();
        add(frameBox);

        frameBox.add(Box.createVerticalGlue());

        frameBox.add(Box.createVerticalStrut(Const.GAP));

        JPanel panel = new JPanel();
        frameBox.add(panel);

        frameBox.add(Box.createVerticalStrut(Const.GAP));

        frameBox.add(Box.createVerticalGlue());

        Box panelBox = Box.createVerticalBox();
        panel.add(panelBox);

        Box inputBox = Box.createHorizontalBox();
        panelBox.add(inputBox);

        panelBox.add(Box.createVerticalStrut(Const.GAP));

        btnBox = Box.createHorizontalBox();
        panelBox.add(btnBox);

        labelBox = Box.createVerticalBox();
        inputBox.add(labelBox);

        inputBox.add(Box.createHorizontalStrut(Const.GAP));

        fieldBox = Box.createVerticalBox();
        inputBox.add(fieldBox);
    }

    @Override
    protected void bind() {
        exitBtn.addActionListener(this);
        loginBtn.addActionListener(this);
    }

    private void onLogin() {
        try {
            User user = new User();
            user.setName(usernameField.getText().trim());
            String password = passwordField.getText();
            user.setPassword(Crypto.enc(password, password));
            new LoginClient().post(user);
            ChooseDialog.create(user);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.failed(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final Object source = event.getSource();
        if (source == loginBtn) {
            onLogin();
        } else if (source == exitBtn) {
            dispose();
            System.exit(0);
        }
    }
}
