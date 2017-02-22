package com.sk89q.questpages.item;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.util.swing.SwingHelper;
import com.sk89q.questpages.model.ItemStack;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Log
public class ItemImageLoader {

    private static final Image TEMP_ICON = SwingHelper.readImage(QuestPages.class, "TEMP.png"); // TODO: Remove this
    public static final ItemImageLoader INSTANCE = new ItemImageLoader(ItemDatabase.INSTANCE);

    @Getter private final ItemDatabase itemDatabase;
    @Getter @Setter private File imageDir;
    private Map<ItemId, Image> imageCache = new HashMap<>();

    private ItemImageLoader(ItemDatabase itemDatabase) {
        this.itemDatabase = itemDatabase;
    }

    public Image getImage(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        return getImage(itemStack.toItemId());
    }

    public synchronized Image getImage(ItemId id) {
        /*if (true) {
            return TEMP_ICON;
        }*/

        Image image = imageCache.get(id);
        if (image != null) {
            return image;
        } else {
            image = loadImage(id);
            imageCache.put(id, image);
            return image;
        }
    }

    private Image loadImage(ItemId id) {
        File imageDir = this.imageDir;
        if (imageDir == null) {
            return null;
        }

        Item info = itemDatabase.getItemInfo(id);
        if (info != null) {
            File file = new File(imageDir, info.getDisplayName() + ".png");
            Image image = Toolkit.getDefaultToolkit().createImage(file.getAbsolutePath()).getScaledInstance(32, 32, Image.SCALE_FAST);
            image = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            return image;
        }

        return null;
    }

}
