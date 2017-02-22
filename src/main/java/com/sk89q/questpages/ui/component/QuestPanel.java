package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.ui.common.XCheckBox;
import com.sk89q.questpages.ui.common.XTextArea;
import com.sk89q.questpages.ui.common.XTextField;
import com.sk89q.questpages.history.BeanChange;
import com.sk89q.questpages.history.History;
import com.sk89q.questpages.model.ItemStack;
import com.sk89q.questpages.model.Quest;
import com.sk89q.questpages.model.RepeatType;
import com.sk89q.questpages.model.RequirementType;
import com.sk89q.questpages.model.TriggerType;
import com.sk89q.questpages.util.swing.ItemStackField;
import com.sk89q.questpages.util.swing.SwingHelper;
import com.sk89q.questpages.util.swing.SwingListeners;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class QuestPanel extends JPanel {

    private final History history;

    @Getter private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
    private final XTextField nameText = new XTextField();
    private final XTextArea descArea = new XTextArea();
    private final XCheckBox largeIconCheck = new XCheckBox("Show an enlarged icon for this quest");
    private final ItemStackField iconField = new ItemStackField();
    private final JComboBox<RepeatType> repeatTypeCombo = new JComboBox<>(new DefaultComboBoxModel<>(RepeatType.values()));
    private final JSpinner repeatDaysSpinner = new JSpinner();
    private final JSpinner repeatHoursSpinner = new JSpinner();
    private final JComboBox<TriggerType> visibilityTypeCombo = new JComboBox<>(new DefaultComboBoxModel<>(TriggerType.values()));
    private final JSpinner visibilityTaskCountSpinner = new JSpinner();
    private final JComboBox<RequirementType> requirementTypeCombo = new JComboBox<>(new DefaultComboBoxModel<>(RequirementType.values()));
    private final JSpinner requirementCountSpinner = new JSpinner();
    @Getter private final TaskList taskList;
    private final ItemStackList rewardsPanel = new ItemStackList();
    private final ItemStackList choiceRewardsPanel = new ItemStackList();
    private final ReputationRewardList reputationRewardsTable = new ReputationRewardList();
    private Quest quest = new Quest();

    public QuestPanel(History history) {
        this.history = history;

        taskList = new TaskList(history);

        visibilityTypeCombo.setSelectedItem(null);
        requirementTypeCombo.setSelectedItem(null);
        repeatTypeCombo.setSelectedItem(null);

        tabbedPane.add("Quest", createBasicPanel());
        tabbedPane.add("Options", createOptionsPanel());
        tabbedPane.add("Tasks", createTasksPanel());
        tabbedPane.add("Rewards", createRewardsPanel());

        registerListeners();

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.setSelectedIndex(2); // TODO: Remove debug
    }

    private JPanel createBasicPanel() {
        JPanel panel = new JPanel();
        SwingHelper.removeOpaqueness(panel);

        panel.setLayout(new MigLayout("insets dialog, fillx", "[][230,grow]"));

        panel.add(new JLabel("Name:"));
        panel.add(nameText, "growx, span");

        panel.add(new JLabel("Description:"));
        panel.add(SwingHelper.wrapScrollPane(descArea), "h 200, growx, span");

        panel.add(new JLabel("Icon:"), "aligny top");
        panel.add(iconField, "growx, span, gapbottom unrel");

        panel.add(largeIconCheck, "span, gapbottom unrel");

        return panel;
    }

    private JPanel createOptionsPanel() {
        JPanel panel = new JPanel();
        SwingHelper.removeOpaqueness(panel);

        panel.setLayout(new MigLayout("insets dialog, fillx", "[][230,grow]"));

        panel.add(new JLabel("Visibility:"), "spany 2, top");
        panel.add(visibilityTypeCombo, "growx, span");

        panel.add(new JLabel("after"), "split 3, span");
        panel.add(visibilityTaskCountSpinner, "w 50");
        panel.add(new JLabel("tasks"), "gapbottom unrel");

        panel.add(new JLabel("Availability:"), "spany 2, top");
        panel.add(requirementTypeCombo, "growx, span");

        panel.add(new JLabel("after"), "split 3, span");
        panel.add(requirementCountSpinner, "w 50");
        panel.add(new JLabel("parent requirements"), "gapbottom unrel");

        panel.add(new JLabel("Repeatability:"), "spany 3, top");
        panel.add(repeatTypeCombo, "growx, span");

        panel.add(new JLabel("every"), "split 3, span");
        panel.add(repeatDaysSpinner, "w 50");
        panel.add(new JLabel("Minecraft days"));

        panel.add(new JLabel("every"), "split 3, span");
        panel.add(repeatHoursSpinner, "w 50");
        panel.add(new JLabel("Minecraft hours"), "gapbottom unrel");

        return panel;
    }

    private JPanel createTasksPanel() {
        JPanel panel = new JPanel();
        SwingHelper.removeOpaqueness(panel);

        panel.setLayout(new MigLayout("insets dialog, fill"));

        panel.add(taskList, "w 200, grow, span");

        return panel;
    }

    private JPanel createRewardsPanel() {
        JPanel panel = new JPanel();
        SwingHelper.removeOpaqueness(panel);

        panel.setLayout(new MigLayout("insets dialog, fill", "", "[|fill,grow||fill,grow||fill,grow]"));

        panel.add(new JLabel("'Get All' Rewards:"), "span");
        panel.add(rewardsPanel, "w 200, h 100, grow, span");

        panel.add(new JLabel("'Choose One' Rewards:"), "span");
        panel.add(choiceRewardsPanel, "w 200, h 100, grow, span");

        panel.add(new JLabel("Reputation Rewards:"), "span");
        panel.add(reputationRewardsTable, "w 200, h 100, grow, span");

        return panel;
    }

    private void registerListeners() {
        SwingListeners.addChangeListener(nameText, e -> {
            // Change
            quest.setName(nameText.getText());

            fireChange();
        });

        SwingListeners.addChangeListener(descArea, e -> {
            // Change
            quest.setDescription(descArea.getText());

            fireChange();
        });

        iconField.addPropertyChangeListener("itemStack", evt -> {
            // Change and commit to history
            BeanChange<Quest> change = new BeanChange<>("Change Quest Icon", quest);
            change.getProxy().setIcon((ItemStack) evt.getNewValue());
            history.commit(change);

            fireChange();
        });

        SwingListeners.addChangeListener(largeIconCheck, e -> {
            // Change and commit to history
            BeanChange<Quest> change = new BeanChange<>("Change Icon Size", quest);
            Quest quest = change.getProxy();
            int offset = 5;
            if (quest.isBigIcon() != largeIconCheck.isSelected()) {
                if (largeIconCheck.isSelected()) {
                    quest.setX(quest.getX() - offset);
                    quest.setY(quest.getY() - offset);
                } else {
                    quest.setX(quest.getX() + offset);
                    quest.setY(quest.getY() + offset);
                }
            }
            quest.setBigIcon(largeIconCheck.isSelected());
            history.commit(change);

            fireChange();
        });

        SwingListeners.addChangeListener(visibilityTypeCombo, e -> {
            TriggerType type = ((TriggerType) visibilityTypeCombo.getSelectedItem());
            visibilityTaskCountSpinner.setEnabled(type.usesTaskCount());

            // Change
            quest.setVisibility(type);
            quest.setVisibilityTaskCount((Integer) visibilityTaskCountSpinner.getValue());

            fireChange();
        });

        ChangeListener requirementChangeListener = e -> {
            RequirementType type = ((RequirementType) requirementTypeCombo.getSelectedItem());
            requirementCountSpinner.setEnabled(type.usesRequirementCount());

            // Change
            quest.setRequirementCount(type == RequirementType.REQUIRES_SOME ? (Integer) requirementCountSpinner.getValue() : null);

            fireChange();
        };

        SwingListeners.addChangeListener(requirementTypeCombo, requirementChangeListener);
        SwingListeners.addChangeListener(requirementCountSpinner, requirementChangeListener);

        ChangeListener repeatChangeListener = e -> {
            RepeatType type = ((RepeatType) repeatTypeCombo.getSelectedItem());
            repeatDaysSpinner.setEnabled(type.usesTime());
            repeatHoursSpinner.setEnabled(type.usesTime());

            // Change
            quest.getRepeatability().setType(type);
            quest.getRepeatability().setDays((Integer) repeatDaysSpinner.getValue());
            quest.getRepeatability().setHours((Integer) repeatHoursSpinner.getValue());

            fireChange();
        };

        SwingListeners.addChangeListener(repeatTypeCombo, repeatChangeListener);
        SwingListeners.addChangeListener(repeatDaysSpinner, repeatChangeListener);
        SwingListeners.addChangeListener(repeatHoursSpinner, repeatChangeListener);
    }

    private void fireChange() {
        firePropertyChange("quest", null, quest);
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
        copyFrom();
    }

    public void copyFrom() {
        nameText.setText(quest.getName());
        descArea.setText(quest.getDescription());
        iconField.setItemStack(quest.getIcon());
        largeIconCheck.setSelected(quest.isBigIcon());
        visibilityTypeCombo.setSelectedItem(quest.getVisibility());
        visibilityTaskCountSpinner.setValue(quest.getVisibilityTaskCount());
        requirementTypeCombo.setSelectedItem(quest.getRequirementCount() == null ? RequirementType.REQUIRES_ALL : RequirementType.REQUIRES_SOME);
        requirementCountSpinner.setValue(quest.getRequirementCount() != null ? quest.getRequirementCount() : 0);
        repeatTypeCombo.setSelectedItem(quest.getRepeatability().getType());
        repeatDaysSpinner.setValue(quest.getRepeatability().getDays());
        repeatHoursSpinner.setValue(quest.getRepeatability().getHours());

        taskList.setModel(quest.getTasks());
    }

}
