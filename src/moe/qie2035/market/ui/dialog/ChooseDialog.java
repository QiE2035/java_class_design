package moe.qie2035.market.ui.dialog;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.EmployerClient;
import moe.qie2035.market.client.MajorsClient;
import moe.qie2035.market.client.SubjectsClient;
import moe.qie2035.market.client.UserClient;
import moe.qie2035.market.data.User;
import moe.qie2035.market.ui.Tables;
import moe.qie2035.market.ui.event.EmployerEvent;
import moe.qie2035.market.ui.event.MajorsEvent;
import moe.qie2035.market.ui.event.SubjectsEvent;
import moe.qie2035.market.ui.event.UserEvent;
import moe.qie2035.market.ui.frame.TableFrame;
import moe.qie2035.market.ui.model.EmployerModel;
import moe.qie2035.market.ui.model.MajorsModel;
import moe.qie2035.market.ui.model.SubjectsModel;
import moe.qie2035.market.ui.model.UserModel;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChooseDialog extends AbsDialog<Object, Object> {
    private static ChooseDialog dialog;

    private JList<Tables> list;
    private JButton okBtn;

    private User user;

    private ChooseDialog() {
    }

    public static void create(User user) {
        if (dialog != null) {
            dialog.dispose();
        }
        dialog = new ChooseDialog();
        dialog.user = user;
    }

    @Override
    protected void init() {
        setTitle("选择");

        JPanel tipPanel = new JPanel();
        add(tipPanel, BorderLayout.NORTH);

        tipPanel.add(new JLabel("请选择要查看的内容"));

        list = new JList<>();
        add(list);

        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.SOUTH);

        okBtn = new JButton(Const.OK);
        btnPanel.add(okBtn);
    }

    @Override
    protected void bind() {
        okBtn.addActionListener(this);

        final DefaultListModel<Tables> listModel = new DefaultListModel<>();

        for (Tables table : Tables.values()) {
            listModel.addElement(table);
        }

        list.setModel(listModel);
        list.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == okBtn) {
            try {
                final Tables selected = list.getSelectedValue();
                final String title = selected.toString();
                switch (selected) {
                    case USER -> {
                        final UserClient client = new UserClient(user);
                        final UserModel model = new UserModel(client.get(0));
                        new TableFrame(title, model, new UserEvent(client, model));
                    }
                    case EMPLOYER -> {
                        final EmployerClient client = new EmployerClient(user);
                        final EmployerModel model = new EmployerModel(client.get(0));
                        new TableFrame(title, model, new EmployerEvent(client, model));
                    }
                    case MAJORS -> {
                        final MajorsClient client = new MajorsClient(user);
                        final MajorsModel model = new MajorsModel(client.get(0));
                        new TableFrame(title, model, new MajorsEvent(client, model));

                    }
                    case SUBJECTS -> {
                        final SubjectsClient client = new SubjectsClient(user);
                        final SubjectsModel model = new SubjectsModel(client.get(0));
                        new TableFrame(title, model, new SubjectsEvent(client, model));
                    }
                    default -> throw new Exception("施工中...");

                }
                dispose();
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.failed(e);
            }
        }
    }

}
