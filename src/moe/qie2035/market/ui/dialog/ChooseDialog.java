package moe.qie2035.market.ui.dialog;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.GoodsClient;
import moe.qie2035.market.client.UserClient;
import moe.qie2035.market.data.User;
import moe.qie2035.market.ui.event.GoodsEvent;
import moe.qie2035.market.ui.event.UserEvent;
import moe.qie2035.market.ui.frame.TableFrame;
import moe.qie2035.market.ui.model.GoodsModel;
import moe.qie2035.market.ui.model.UserModel;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChooseDialog extends AbsDialog<Object, Object> {

    public static final String USER = "用户";
    public static final String GOODS = "商品";
    private static ChooseDialog dialog;

    private JList<String> list;
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

        final DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement(USER);
        listModel.addElement(GOODS);
        list.setModel(listModel);
        list.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == okBtn) {
            try {
                switch (list.getSelectedValue()) {
                    case USER -> {
                        UserClient client = new UserClient(user);
                        UserModel model = new UserModel(client.get(0));
                        new TableFrame(USER, model, new UserEvent(client, model));
                    }

                    case GOODS -> {
                        GoodsClient client = new GoodsClient(user);
                        GoodsModel model = new GoodsModel(client.get(0));
                        new TableFrame(GOODS, model, new GoodsEvent(client, model));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.failed(e);
            }
            dispose();
        }
    }
}
