package moe.qie2035.market.ui.dialog;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.UserClient;
import moe.qie2035.market.data.User;
import moe.qie2035.market.utils.Crypto;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UserDialog extends AbsDialog<User, String> {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> typeComboBox;
    private String oldName;

    public UserDialog(User user,
                      String[] label,
                      boolean isNew,
                      UserClient client) {
        super(user, label, isNew, client);
    }

    @Override
    protected void init() {
        setDataTitle(data.getName());

        dataInit();

        usernameField = new JTextField(Const.COL);
        fieldBox.add(usernameField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        passwordField = new JPasswordField(Const.COL);
        fieldBox.add(passwordField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        typeComboBox = new JComboBox<>();
        fieldBox.add(typeComboBox);
    }

    @Override
    protected void bind() {
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        for (User.Type type : User.Type.values()) {
            typeComboBox.addItem(type.getName());
        }
        if (isNew) return;
        oldName = data.getName();
        usernameField.setText(data.getName());
        typeComboBox.setSelectedItem(
                data.getUserType().getName());
    }

    private void onOk() {
        try {
            data.setName(usernameField.getText().trim());
            String password = passwordField.getText();
            if (!password.trim().equals("")) {
                data.setPassword(Crypto.enc(password, password));
            }
            data.setUserType(User.Type.
                    from((String) typeComboBox
                            .getSelectedItem()));
            if (isNew) {
                client.post(data);
            } else {
                client.put(data, oldName);
            }
            MsgBox.success();
        } catch (Exception e) {
            MsgBox.failed(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final Object source = event.getSource();
        if (source == okBtn) {
            onOk();
        }
        dispose();
    }
}
