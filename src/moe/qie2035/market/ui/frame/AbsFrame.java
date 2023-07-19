package moe.qie2035.market.ui.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AbsFrame
        extends JFrame implements ActionListener {
    public AbsFrame(Dimension minSize) {
        init();
        bind();
        pack();
        setVisible(true);
        if (minSize == null) {
            setMinimumSize(getSize());
        } else {
            setMinimumSize(minSize);
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public AbsFrame() {
        this(null);
    }

    protected abstract void init();

    protected abstract void bind();
}
