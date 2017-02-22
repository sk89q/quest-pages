package com.sk89q.questpages.util.swing;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.ValueModel;

public final class MoreBindings {

    private MoreBindings() {
    }

    public static void bind(ItemStackField component, ValueModel valueModel) {
        Bindings.bind(component, "itemStack", valueModel);
    }

}
