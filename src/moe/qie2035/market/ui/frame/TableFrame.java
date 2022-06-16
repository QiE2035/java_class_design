package moe.qie2035.market.ui.frame;

import moe.qie2035.market.ui.event.ITableEvent;
import moe.qie2035.market.ui.model.AbsModel;
import moe.qie2035.market.utils.MsgBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TableFrame extends AbsFrame {

    private final ITableEvent tableEvent;
    private JButton preBtn, nextBtn, newBtn,
            modBtn, delBtn, searchButton;
    private JTextField searchField;
    private JTable table;

    public TableFrame(String title, AbsModel<?> model,
                      ITableEvent tableEvent) {
        setTitle(title);
        table.setModel(model);
        this.tableEvent = tableEvent;
    }

    @Override
    protected void init() {
        JPanel searchPanel = new JPanel();
        add(searchPanel, BorderLayout.NORTH);

        searchField = new JTextField(20);
        searchPanel.add(searchField);

        searchButton = new JButton("搜索/刷新");
        searchPanel.add(searchButton);

        table = new JTable();

        JScrollPane scrollPanel = new JScrollPane(table);
        scrollPanel.setPreferredSize(
                new Dimension(0, 300));
        add(scrollPanel);

        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.SOUTH);

        preBtn = new JButton("上一页");
        btnPanel.add(preBtn);

        nextBtn = new JButton("下一页");
        btnPanel.add(nextBtn);

        newBtn = new JButton("新建");
        btnPanel.add(newBtn);

        modBtn = new JButton("修改");
        btnPanel.add(modBtn);

        delBtn = new JButton("删除");
        btnPanel.add(delBtn);
    }

    @Override
    protected void bind() {
        preBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        newBtn.addActionListener(this);
        modBtn.addActionListener(this);
        delBtn.addActionListener(this);
        searchButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (tableEvent == null) {
            return;
        }
        final int index = table.getSelectedRow();
        final Object source = event.getSource();
        try {

            if (source == preBtn) {
                tableEvent.preClick();
            } else if (source == nextBtn) {
                tableEvent.nextClick();
            } else if (source == newBtn) {
                tableEvent.newClick();
            } else if (source == searchButton) {
                String search = searchField.getText().trim();
                if (search.contains(" ")) {
                    throw new Exception("搜索内容不能包含空格");
                }
                tableEvent.searchClick(search);
            } else if (index != -1) {
                if (source == modBtn) {
                    tableEvent.modClick(index);
                } else if (source == delBtn) {
                    tableEvent.delClick(index);
                    MsgBox.success("删除成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.failed(e);
        }
    }
}
