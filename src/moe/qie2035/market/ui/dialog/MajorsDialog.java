package moe.qie2035.market.ui.dialog;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.MajorsClient;
import moe.qie2035.market.data.Majors;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MajorsDialog extends AbsDialog<Majors, Integer> {
    // TODO: 2023/7/18
    private Integer oldId;
    private JTextField nameField;
    private JTextField degreeTypeField;
    private JSpinner idSpinner;
//    private JTextField contactField;

    public MajorsDialog(Majors goods,
                        String[] label,
                        boolean isNew,
                        MajorsClient client) {
        super(goods, label, isNew, client);
    }

    @Override
    protected void init() {
        setDataTitle(data.getMajorName());

        dataInit();

        idSpinner = new JSpinner();
        fieldBox.add(idSpinner);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        nameField = new JTextField(Const.COL);
        fieldBox.add(nameField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        degreeTypeField = new JTextField(Const.COL);
        fieldBox.add(degreeTypeField);

//        fieldBox.add(Box.createVerticalStrut(Const.GAP));

//        contactField = new JTextField(Const.COL);
//        fieldBox.add(contactField);
    }

    @Override
    protected void bind() {
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        idSpinner.setEnabled(false);

        if (isNew) return;
        oldId = data.getMajorId();
        idSpinner.setValue(data.getMajorId());
        nameField.setText(data.getMajorName());
        degreeTypeField.setText(data.getDegreeType());
//        contactField.setText(data.getMajorsContact());
    }

    private void onOk() {
        try {
            data.setMajorName(nameField.getText().trim());
            data.setDegreeType(degreeTypeField.getText().trim());
//            data.setMajorsContact(contactField.getText().trim());

            if (isNew) {
                client.post(data);
            } else {
                data.setMajorId((Integer) idSpinner.getValue());
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
