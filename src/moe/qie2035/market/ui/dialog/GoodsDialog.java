package moe.qie2035.market.ui.dialog;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.GoodsClient;
import moe.qie2035.market.data.Goods;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GoodsDialog extends AbsDialog<Goods, Integer> {
    private Integer oldId;
    private JTextField nameField;
    private JTextField princeField;
    private JSpinner idSpinner;
    private JSpinner countSpinner;
    private JTextField remarkField;

    public GoodsDialog(Goods user,
                       String[] label,
                       boolean isNew,
                       GoodsClient client) {
        super(user, label, isNew, client);
    }

    @Override
    protected void init() {
        setDataTitle(data.getName());

        dataInit();

        idSpinner = new JSpinner();
        fieldBox.add(idSpinner);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        nameField = new JTextField(Const.COL);
        fieldBox.add(nameField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        princeField = new JTextField(Const.COL);
        fieldBox.add(princeField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        countSpinner = new JSpinner();
        fieldBox.add(countSpinner);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        remarkField = new JTextField(Const.COL);
        fieldBox.add(remarkField);
    }

    @Override
    protected void bind() {
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        princeField.setText("0");

        if (isNew) return;
        oldId = data.getId();
        idSpinner.setValue(data.getId());
        idSpinner.setEditor(new JSpinner.NumberEditor(
                idSpinner, "#"));
        nameField.setText(data.getName());
        princeField.setText(String.valueOf(data.getPrince()));
        countSpinner.setValue(data.getCount());
        remarkField.setText(data.getRemark());
    }

    private void onOk() {
        try {
            data.setPrince(Double.valueOf(
                    princeField.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.failed("价格格式不正确");
            return;
        }
        try {
            data.setName(nameField.getText().trim());
            data.setId((Integer) idSpinner.getValue());
            data.setCount((Integer) countSpinner.getValue());
            data.setRemark(remarkField.getText().trim());
            if (isNew) {
                client.post(data);
            } else {
                client.put(data, oldId);
            }
            MsgBox.success();
            dispose();
        } catch (Exception e) {
            MsgBox.failed(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final Object source = event.getSource();
        if (source == cancelBtn) {
            dispose();
        } else if (source == okBtn) {
            onOk();
        }
    }
}
