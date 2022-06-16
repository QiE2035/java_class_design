package moe.qie2035.market.ui.frame;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class AbsFrame
        extends JFrame implements ActionListener {
    public AbsFrame() {
        init();
        bind();
        pack();
        setVisible(true);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    protected abstract void init();

    protected abstract void bind();
}
