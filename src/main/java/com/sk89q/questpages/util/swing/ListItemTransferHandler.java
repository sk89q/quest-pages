package com.sk89q.questpages.util.swing;

import lombok.extern.java.Log;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;

@Log
public class ListItemTransferHandler extends TransferHandler {

    private final ResortListener resortListener;

    private final DataFlavor localObjectFlavor;
    private Object[] transferredObjects = null;

    private int[] indices = null;
    private int addIndex = -1; //Location where items were added
    private int addCount = 0;  //Number of items added.

    private ResortHandler handler;

    public ListItemTransferHandler(ResortListener resortListener) {
        this.resortListener = resortListener;
        localObjectFlavor = new ActivationDataFlavor(Object[].class, DataFlavor.javaJVMLocalObjectMimeType, "Array of items");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Transferable createTransferable(JComponent c) {
        JList list = (JList) c;
        indices = list.getSelectedIndices();
        transferredObjects = list.getSelectedValues();
        return new DataHandler(transferredObjects, localObjectFlavor.getMimeType());
    }

    @Override
    public boolean canImport(TransferSupport info) {
        if (!info.isDrop() || !info.isDataFlavorSupported(localObjectFlavor)) {
            return false;
        }
        return true;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE; //TransferHandler.COPY_OR_MOVE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean importData(TransferSupport info) {
        if (!canImport(info)) {
            return false;
        }
        handler = resortListener.createResortHandler();
        JList target = (JList) info.getComponent();
        JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
        ModifiableListModel listModel = (ModifiableListModel) target.getModel();
        int index = dl.getIndex();
        int max = listModel.getSize();
        if (index < 0 || index > max) {
            index = max;
        }
        addIndex = index;
        try {
            Object[] values = (Object[]) info.getTransferable().getTransferData(localObjectFlavor);
            addCount = values.length;
            for (Object value : values) {
                int idx = index++;
                handler.addElement(idx, value);
                target.setSelectedIndex(idx);
            }
            return true;
        } catch (UnsupportedFlavorException | IOException e) {
            log.log(Level.WARNING, "Transfer error", e);
        }
        return false;
    }

    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
        cleanup(c, action == MOVE);
    }

    private void cleanup(JComponent c, boolean remove) {
        if (remove && indices != null) {
            JList source = (JList) c;
            ModifiableListModel<?> model = (ModifiableListModel<?>) source.getModel();
            if (addCount > 0) {
                //http://java-swing-tips.googlecode.com/svn/trunk/DnDReorderList/src/java/example/MainPanel.java
                for (int i = 0; i < indices.length; i++) {
                    if (indices[i] >= addIndex) {
                        indices[i] += addCount;
                    }
                }
            }
            for (int i = indices.length - 1; i >= 0; i--) {
                if (handler != null) {
                    handler.removeElement(indices[i]);
                }
                if (indices[i] < source.getSelectedIndex()) {
                    source.setSelectedIndex(source.getSelectedIndex() - 1);
                }
            }
        }
        indices = null;
        addCount = 0;
        addIndex = -1;
    }

    public interface ResortListener {
        ResortHandler createResortHandler();
    }

    public interface ResortHandler {
        void addElement(int index, Object value);

        void removeElement(int index);
    }

}
