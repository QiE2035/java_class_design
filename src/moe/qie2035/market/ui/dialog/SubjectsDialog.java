package moe.qie2035.market.ui.dialog;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.SubjectsClient;
import moe.qie2035.market.data.Subjects;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SubjectsDialog extends AbsDialog<Subjects, Integer> {
    private Integer oldId;
    private JTextField nameField;
    private JSpinner idSpinner;

    public SubjectsDialog(Subjects goods,
                          String[] label,
                          boolean isNew,
                          SubjectsClient client) {
        super(goods, label, isNew, client);
    }

    @Override
    protected void init() {
        setDataTitle(data.getSubjectName());

        dataInit();

        idSpinner = new JSpinner();
        fieldBox.add(idSpinner);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        nameField = new JTextField(Const.COL);
        fieldBox.add(nameField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));
    }

    @Override
    protected void bind() {
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        idSpinner.setEnabled(false);

        if (isNew) return;
        oldId = data.getSubjectId();
        idSpinner.setValue(data.getSubjectId());
        nameField.setText(data.getSubjectName());
    }

    private void onOk() {
        try {
            data.setSubjectName(nameField.getText().trim());

            if (isNew) {
                client.post(data);
            } else {
                data.setSubjectId((Integer) idSpinner.getValue());
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
