package com.sk89q.questpages.ui.common;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class XList<E> extends JList<E> {

    {
        addMouseListener(new MouseAdapter() {
            private void selectAtMouse(MouseEvent e) {
                int index = XList.this.locationToIndex(e.getPoint());
                if (index >= 0) {
                    XList.this.setSelectedIndex(index);
                } else {
                    XList.this.setSelectedValue(null, false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectAtMouse(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectAtMouse(e);
            }
        });
    }

    public XList(ListModel<E> dataModel) {
        super(dataModel);
    }

    public XList(E[] listData) {
        super(listData);
    }

    public XList(Vector<E> listData) {
        super(listData);
    }

    public XList() {
    }

}
