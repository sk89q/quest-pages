package com.sk89q.questpages.ui.renderer;

import com.sk89q.questpages.model.task.Task;
import com.sk89q.questpages.util.swing.SwingHelper;

import javax.swing.*;
import java.awt.*;

import static com.sk89q.questpages.util.SharedLocale.tr;

public class TaskCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Task task = (Task) value;

        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        if (!task.getDisplayName().isEmpty()) {
            builder.append("<strong>").append(task.getDisplayName()).append("</strong>");
        } else {
            builder.append("<em>").append(SwingHelper.htmlEscape(tr("taskList.untitledTask"))).append("</em>");
        }
        builder.append("<br>");
        builder.append(task.getDisplayDesc(isSelected));

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setBorder(BorderFactory.createCompoundBorder(label.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        label.setText(builder.toString());
        label.setIcon(task.getDisplayIcon());
        label.setToolTipText(task.getTypeName());
        return label;
    }

}
