package moe.qie2035.market.ui.dialog;

import lombok.SneakyThrows;
import moe.qie2035.market.Config;
import moe.qie2035.market.Const;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CfgDialog extends AbsDialog<Object, Object> {
    private static CfgDialog dialog;
    private JButton saveBtn, cancelBth;
    private JTabbedPane tabPanel;
    private JTextField dbNameField, dbHostField,
            dbUsernameField, clientHostField;
    private JComboBox<Config.Database.Type> dbTypeComboBox;
    private JSpinner dbPortSpinner, srvPortSpinner,
            srvThreadsSpinner, clientPortSpinner;
    private JPasswordField dbPasswordField;

    private CfgDialog() {
    }

    public static void create() {
        if (dialog != null) {
            dialog.dispose();
        }
        dialog = new CfgDialog();
    }

    @SneakyThrows
    private static int check(Object obj) {
        int in = (int) obj;
        if (1 <= in && in <= 65535) {
            return in;
        }
        throw new Exception(
                "数字不在有效范围, 请检查端口或线程设置");
    }

    private static JSpinner.NumberEditor getEditor(JSpinner spinner) {
        return new JSpinner.NumberEditor(
                spinner, "#");
    }

    @Override
    protected void init() {
        setTitle("设置");

        tabPanel = new JTabbedPane();
        add(tabPanel);

        initClient();
        initSrv();
        initDb();

        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.SOUTH);

        saveBtn = new JButton("保存");
        btnPanel.add(saveBtn);

        cancelBth = new JButton(Const.CANCEL);
        btnPanel.add(cancelBth);
    }

    private void initDb() {
        JPanel dbPanel = new JPanel();
        tabPanel.addTab("数据库", dbPanel);

        Box dbBox = Box.createHorizontalBox();
        dbPanel.add(dbBox);

        Box dbLabelBox = Box.createVerticalBox();
        dbBox.add(dbLabelBox);

        dbLabelBox.add(Box.createVerticalStrut(Const.GAP));

        dbLabelBox.add(new JLabel("库名"));
        dbLabelBox.add(Box.createVerticalStrut(Const.GAP2));
        dbLabelBox.add(new JLabel("类型"));
        dbLabelBox.add(Box.createVerticalStrut(Const.GAP2));
        dbLabelBox.add(new JLabel("主机"));
        dbLabelBox.add(Box.createVerticalStrut(Const.GAP2));
        dbLabelBox.add(new JLabel("端口"));
        dbLabelBox.add(Box.createVerticalStrut(Const.GAP2));
        dbLabelBox.add(new JLabel("帐号"));
        dbLabelBox.add(Box.createVerticalStrut(Const.GAP2));
        dbLabelBox.add(new JLabel("密码"));

        dbBox.add(Box.createHorizontalStrut(Const.GAP));

        Box dbFieldBox = Box.createVerticalBox();
        dbBox.add(dbFieldBox);

        dbFieldBox.add(Box.createVerticalStrut(Const.GAP));

        dbNameField = new JTextField(Const.COL);
        dbFieldBox.add(dbNameField);

        dbFieldBox.add(Box.createVerticalStrut(Const.GAP));

        dbTypeComboBox = new JComboBox<>();
        dbFieldBox.add(dbTypeComboBox);

        dbFieldBox.add(Box.createVerticalStrut(Const.GAP));

        dbHostField = new JTextField(Const.COL);
        dbFieldBox.add(dbHostField);

        dbFieldBox.add(Box.createVerticalStrut(Const.GAP));

        dbPortSpinner = new JSpinner();
        dbFieldBox.add(dbPortSpinner);

        dbFieldBox.add(Box.createVerticalStrut(Const.GAP));

        dbUsernameField = new JTextField(Const.COL);
        dbFieldBox.add(dbUsernameField);

        dbFieldBox.add(Box.createVerticalStrut(Const.GAP));

        dbPasswordField = new JPasswordField(Const.COL);
        dbFieldBox.add(dbPasswordField);
    }

    private void initSrv() {
        JPanel srvPanel = new JPanel();
        tabPanel.addTab("服务器", srvPanel);

        Box srvBox = Box.createHorizontalBox();
        srvPanel.add(srvBox);


        Box srvLabelBox = Box.createVerticalBox();
        srvBox.add(srvLabelBox);

        srvLabelBox.add(Box.createVerticalStrut(Const.GAP));

        srvLabelBox.add(new JLabel("端口"));
        srvLabelBox.add(Box.createVerticalStrut(Const.GAP2));
        srvLabelBox.add(new JLabel("线程"));

        srvBox.add(Box.createHorizontalStrut(Const.GAP));

        Box srvFieldBox = Box.createVerticalBox();
        srvBox.add(srvFieldBox);

        srvFieldBox.add(Box.createVerticalStrut(Const.GAP));

        srvPortSpinner = new JSpinner();
        srvFieldBox.add(srvPortSpinner);

        srvFieldBox.add(Box.createVerticalStrut(Const.GAP));

        srvThreadsSpinner = new JSpinner();
        srvFieldBox.add(srvThreadsSpinner);
    }

    private void initClient() {
        JPanel clientPanel = new JPanel();
        tabPanel.addTab("客户端", clientPanel);

        Box clientBox = Box.createHorizontalBox();
        clientPanel.add(clientBox);

        Box clientLabelBox = Box.createVerticalBox();
        clientBox.add(clientLabelBox);

        clientLabelBox.add(Box.createVerticalStrut(Const.GAP));

        clientLabelBox.add(new JLabel("主机"));
        clientLabelBox.add(Box.createVerticalStrut(Const.GAP2));
        clientLabelBox.add(new JLabel("端口"));

        clientBox.add(Box.createHorizontalStrut(Const.GAP));

        Box clientFieldBox = Box.createVerticalBox();
        clientBox.add(clientFieldBox);

        clientFieldBox.add(Box.createVerticalStrut(Const.GAP));

        clientHostField = new JTextField(Const.COL);
        clientFieldBox.add(clientHostField);

        clientFieldBox.add(Box.createVerticalStrut(Const.GAP));

        clientPortSpinner = new JSpinner();
        clientFieldBox.add(clientPortSpinner);
    }

    @Override
    protected void bind() {
        saveBtn.addActionListener(this);
        cancelBth.addActionListener(this);

        Config.Client client = Config.get().getClient();
        clientHostField.setText(client.getHost());
        clientPortSpinner.setEditor(getEditor(clientPortSpinner));
        clientPortSpinner.setValue(client.getPort());

        Config.Server server = Config.get().getServer();
        srvPortSpinner.setEditor(getEditor(srvPortSpinner));
        srvPortSpinner.setValue(server.getPort());
        srvThreadsSpinner.setEditor(getEditor(srvThreadsSpinner));
        srvThreadsSpinner.setValue(server.getThreads());

        Config.Database database = Config.get().getDatabase();
        dbHostField.setText(database.getHost());
        dbNameField.setText(database.getName());
        dbUsernameField.setText(database.getUsername());
        dbPortSpinner.setEditor(getEditor(dbPortSpinner));
        dbPortSpinner.setValue(database.getPort());
        dbPasswordField.setText(database.decPassword());
        for (Config.Database.Type type
                : Config.Database.Type.values()) {
            dbTypeComboBox.addItem(type);
        }
        dbTypeComboBox.setSelectedItem(database.getType());
    }

    private void onSave() {
        try {
            Config.Client client = Config.get().getClient();
            client.setHost(clientHostField.getText().trim());
            client.setPort(check(clientPortSpinner.getValue()));

            Config.Server server = Config.get().getServer();
            server.setPort(check(srvPortSpinner.getValue()));
            server.setThreads(check(srvThreadsSpinner.getValue()));

            Config.Database database = Config.get().getDatabase();
            database.setHost(dbHostField.getText().trim());
            database.setName(dbNameField.getText().trim());
            database.setUsername(dbUsernameField.getText().trim());
            database.setPort((int) dbPortSpinner.getValue());
            database.encPassword(dbPasswordField.getText());
            database.setType((Config.Database.Type)
                    dbTypeComboBox.getSelectedItem());

            Config.get().save();

            MsgBox.success();
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.failed(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == saveBtn) {
            onSave();
        } else if (source == cancelBth) {
            dispose();
        }
    }
}
