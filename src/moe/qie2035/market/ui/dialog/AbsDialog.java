package moe.qie2035.market.ui.dialog;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.AbsClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AbsDialog<T, P>
        extends JDialog implements ActionListener {
    protected String[] label;
    protected T data;
    protected boolean isNew;
    protected AbsClient<T, P> client;
    protected JButton okBtn;
    protected JButton cancelBtn;
    protected Box fieldBox;

    public AbsDialog() {
        baseInit();
    }

    public AbsDialog(T data,
                     String[] label,
                     boolean isNew,
                     AbsClient<T, P> client) {
        this.data = data;
        this.label = label;
        this.isNew = isNew;
        this.client = client;
        baseInit();
    }

    private void baseInit() {
        init();
        bind();
        pack();
        setVisible(true);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    protected void setDataTitle(String name) {
        if (isNew) {
            setTitle("新建");
        } else {
            setTitle("修改: " + name);
        }
    }

    protected abstract void init();

    protected abstract void bind();

    protected void dataInit() {
        JPanel panel = new JPanel();
        add(panel);

        Box panelBox = Box.createHorizontalBox();
        panel.add(panelBox);

        Box labelBox = Box.createVerticalBox();
        panelBox.add(labelBox);

        Component tmp = Box.createVerticalStrut(Const.GAP);
        labelBox.add(tmp);
        for (String s : label) {
            labelBox.add(new JLabel(s));
            tmp = Box.createVerticalStrut(Const.GAP2);
            labelBox.add(tmp);
        }
        labelBox.remove(tmp);

        panelBox.add(Box.createHorizontalStrut(Const.GAP));

        fieldBox = Box.createVerticalBox();
        panelBox.add(fieldBox);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.SOUTH);

        Box btnBox = Box.createHorizontalBox();
        btnPanel.add(btnBox);

        okBtn = new JButton(Const.OK);
        btnBox.add(okBtn);

        btnBox.add(Box.createHorizontalStrut(Const.GAP));

        cancelBtn = new JButton(Const.CANCEL);
        btnBox.add(cancelBtn);

    }

}
