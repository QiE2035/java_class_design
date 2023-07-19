package moe.qie2035.market.ui.dialog;

import moe.qie2035.market.Const;
import moe.qie2035.market.client.EmployerClient;
import moe.qie2035.market.data.Employer;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EmployerDialog extends AbsDialog<Employer, Integer> {
    // TODO: 2023/7/18
    private Integer oldId;
    private JTextField nameField;
    private JTextField addressField;
    private JSpinner idSpinner;
    private JTextField contactField;

    public EmployerDialog(Employer goods,
                          String[] label,
                          boolean isNew,
                          EmployerClient client) {
        super(goods, label, isNew, client);
    }

    @Override
    protected void init() {
        setDataTitle(data.getEmployerName());

        dataInit();

        idSpinner = new JSpinner();
        fieldBox.add(idSpinner);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        nameField = new JTextField(Const.COL);
        fieldBox.add(nameField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        addressField = new JTextField(Const.COL);
        fieldBox.add(addressField);

        fieldBox.add(Box.createVerticalStrut(Const.GAP));

        contactField = new JTextField(Const.COL);
        fieldBox.add(contactField);
    }

    @Override
    protected void bind() {
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        idSpinner.setEnabled(false);

        if (isNew) return;
        oldId = data.getEmployerId();
        idSpinner.setValue(data.getEmployerId());
        nameField.setText(data.getEmployerName());
        addressField.setText(data.getEmployerAddress());
        contactField.setText(data.getEmployerContact());
    }

    private void onOk() {
        try {
            data.setEmployerName(nameField.getText().trim());
            data.setEmployerAddress(addressField.getText().trim());
            data.setEmployerContact(contactField.getText().trim());

            if (isNew) {
                client.post(data);
            } else {
                data.setEmployerId((Integer) idSpinner.getValue());
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
