package com.sk89q.questpages.ui;

import com.sk89q.questpages.ui.common.XTextArea;
import com.sk89q.questpages.ui.common.XTextField;
import com.sk89q.questpages.ui.component.task.TaskPanel;
import com.sk89q.questpages.model.task.Task;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static com.sk89q.questpages.util.SharedLocale.tr;

public class TaskDialog extends JDialog {

    @Getter private final XTextField nameText = new XTextField();
    @Getter private final XTextArea descArea = new XTextArea();
    private final JButton okButton = new JButton(tr("button.ok"));
    private final JButton cancelButton = new JButton(tr("button.cancel"));
    private final TaskPanel taskPanel;

    @Getter
    private int value = JOptionPane.DEFAULT_OPTION;

    public TaskDialog(Window owner, TaskPanel taskPanel) {
        super(owner, tr("taskDialog.title"), ModalityType.DOCUMENT_MODAL);

        this.taskPanel = taskPanel;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(owner);

        okButton.addActionListener(e -> {
            value = JOptionPane.OK_OPTION;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            value = JOptionPane.CANCEL_OPTION;
            setVisible(false);
        });

        getRootPane().setDefaultButton(okButton);
        SwingHelper.setEscapeButton(getRootPane(), cancelButton);
        SwingHelper.focusLater(this, nameText);
    }

    private void initComponents() {
        JPanel container = new JPanel();
        container.setLayout(new MigLayout("fill, insets dialog"));

        container.add(createInfoPanel(), "aligny top, growx 1");
        container.add(createTaskPanel(), "aligny top, growx 1, span");
        container.add(okButton, "tag ok, span, split 2, sizegroup bttn, gaptop unrel");
        container.add(cancelButton, "tag cancel, sizegroup bttn");

        add(container, BorderLayout.CENTER);
    }

    private JPanel createInfoPanel() {
        JPanel container = new JPanel();
        SwingHelper.removeOpaqueness(container);

        container.setLayout(new MigLayout("fillx, insets panel, insets 0 0 0 n"));

        container.add(new JLabel(tr("field.name")));
        container.add(nameText, "growx, span");

        container.add(new JLabel(tr("field.description")));
        container.add(SwingHelper.wrapScrollPane(descArea), "w 200, h 200, growx, span");

        return container;
    }

    private JPanel createTaskPanel() {
        JPanel container = new JPanel();
        SwingHelper.removeOpaqueness(container);

        container.setLayout(new MigLayout("fill, insets panel, insets 0"));

        container.add(taskPanel, "aligny top, w 300");

        return container;
    }

    public static Task showTaskDialog(Window parent, Task task) {
        TaskPanel panel = task.createEditPanel();
        TaskDialog dialog = new TaskDialog(parent, panel);
        dialog.getNameText().setText(task.getName() != null ? task.getName() : "");
        dialog.getDescArea().setText(task.getDescription() != null ? task.getDescription() : "");
        dialog.setVisible(true);
        if (dialog.getValue() == JOptionPane.OK_OPTION) {
            Task newTask = panel.getChangedTask();
            newTask.setName(dialog.getNameText().getText());
            newTask.setDescription(dialog.getDescArea().getText());
            return newTask;
        } else {
            return null;
        }
    }

}
