package com.sk89q.questpages;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sk89q.questpages.item.Item;
import com.sk89q.questpages.item.ItemDatabase;
import com.sk89q.questpages.item.ItemImageLoader;
import com.sk89q.questpages.model.QuestLine;
import com.sk89q.questpages.ui.QuestEditorFrame;
import com.sk89q.questpages.util.SharedLocale;
import lombok.extern.java.Log;

import javax.swing.*;
import java.io.File;
import java.util.Locale;

@Log
public class QuestPages {

    public static void main(String[] args) throws Exception {
        // This is all for TESTING
        SharedLocale.loadBundle("com.sk89q.questpages.lang.QuestPages", Locale.getDefault());

        File base = new File("example_data");
        File questDataFile = new File(base, "skcraft_steam_age.json");
        File itemPanelFile = new File(base, "itempanel.csv");
        File itemIconsDir = new File(base, "itempanel_icons");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        ObjectNode rootNode = (ObjectNode) mapper.readTree(questDataFile);
        ArrayNode quests = (ArrayNode) rootNode.get("quests");
        int id = 0;
        for (JsonNode node : quests) {
            ObjectNode questNode = (ObjectNode) node;
            questNode.set("@id", new IntNode(id++));
        }
        QuestLine questLine = mapper.readValue(rootNode.toString(), QuestLine.class);

        ItemDatabase.INSTANCE.loadItemPanelCsv(itemPanelFile);
        ItemImageLoader.INSTANCE.setImageDir(itemIconsDir);

        for (Item item : ItemDatabase.INSTANCE.getItems()) {
            ItemImageLoader.INSTANCE.getImage(item.getId());
        }

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "QuestEdit");
        //Application.getApplication().setDockIconBadge("1");
        //Application.getApplication().setDockIconImage(SwingHelper.readImage(QuestEdit.class, "icon.png"));

        SwingUtilities.invokeLater(() -> {
            UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
                e.printStackTrace();
            }

            QuestEditorFrame editor = new QuestEditorFrame(questLine);
            editor.setVisible(true);

            /*DefaultDialog dialog = new DefaultDialog(null, "test", new ItemRequirementPanel(new ItemRequirement()));
            dialog.setVisible(true);*/
        });
    }

}
