package com.sk89q.questpages.ui;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.BeanAdapter;
import com.sk89q.questpages.model.ItemPrecision;
import com.sk89q.questpages.model.ItemRequirement;
import com.sk89q.questpages.ui.common.FormPanel;
import com.sk89q.questpages.ui.common.XRadioButton;
import com.sk89q.questpages.ui.common.XTextField;
import com.sk89q.questpages.util.swing.ItemStackField;
import com.sk89q.questpages.util.swing.MoreBindings;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import static com.sk89q.questpages.util.SharedLocale.tr;

public class ItemRequirementPanel extends JPanel {

    @Getter private ItemRequirement itemRequirement;

    private final XRadioButton itemRadio = new XRadioButton(tr("itemRequirement.matchItem"));
    private final XRadioButton fluidRadio = new XRadioButton(tr("itemRequirement.matchFluid"));
    private final JLabel fluidNameLabel = new JLabel(tr("itemRequirement.fluidName"));
    private final XTextField fluidNameText = new XTextField();
    private final JLabel itemStackLabel = new JLabel(tr("itemRequirement.itemStack"));
    private final ItemStackField itemStackField = new ItemStackField();
    private final JLabel precisionLabel = new JLabel(tr("itemRequirement.precision"));
    private final JComboBox<ItemPrecision> precisionCombo = new JComboBox<>(new DefaultComboBoxModel<>(ItemPrecision.values()));
    private final JCheckBox requiredCheck = new JCheckBox(tr("itemRequirement.required"));

    public ItemRequirementPanel(ItemRequirement itemRequirement) {
        this.itemRequirement = itemRequirement;
        initComponents();
        registerListeners();

        if (itemRequirement.getFluid() != null) {
            fluidRadio.setSelected(true);
        } else {
            itemRadio.setSelected(true);
        }
    }

    private void initComponents() {
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(itemRadio);
        typeGroup.add(fluidRadio);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new MigLayout("insets 0, gap 0"));
        optionsPanel.add(itemRadio, "span");
        optionsPanel.add(fluidRadio);

        FormPanel formPanel = new FormPanel("", "", "[][][][]push[]")
                .spanningField(optionsPanel)
                .label(fluidNameLabel, "hidemode 2")
                .field(fluidNameText, "w 200, hidemode 2")
                .label(itemStackLabel, "aligny top, hidemode 2")
                .spanningField(itemStackField, "w 200, hidemode 2")
                .label(precisionLabel, "hidemode 2")
                .field(precisionCombo, "hidemode 2")
                .spanningField(requiredCheck);

        setLayout(new MigLayout("insets 0"));
        add(optionsPanel, "wrap");
        add(formPanel, "wrap");
    }

    private void registerListeners() {
        itemRadio.addChangeListener(e -> updateChoice());
        fluidRadio.addChangeListener(e -> updateChoice());

        BeanAdapter<ItemRequirement> beanAdapter = new BeanAdapter<>(itemRequirement);
        Bindings.bind(fluidNameText, beanAdapter.getValueModel(ItemRequirement.FLUID_FIELD), true);
        MoreBindings.bind(itemStackField, beanAdapter.getValueModel(ItemRequirement.ITEM_FIELD));
        Bindings.bind(requiredCheck, beanAdapter.getValueModel(ItemRequirement.REQUIRED_FIELD));
        Bindings.bind(precisionCombo, new ComboBoxAdapter<>(ItemPrecision.values(), beanAdapter.getValueModel(ItemRequirement.PRECISION_FIELD)));
    }

    private void updateChoice() {
        fluidNameLabel.setVisible(fluidRadio.isSelected());
        fluidNameText.setVisible(fluidRadio.isSelected());
        itemStackLabel.setVisible(itemRadio.isSelected());
        itemStackField.setVisible(itemRadio.isSelected());
        precisionLabel.setVisible(itemRadio.isSelected());
        precisionCombo.setVisible(itemRadio.isSelected());
    }

}
