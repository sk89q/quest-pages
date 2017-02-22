package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.extern.java.Log;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import javax.swing.border.Border;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@Log
public class TipPanel extends JPanel {

    private static final ImageIcon TIP_ICON = SwingHelper.readImageIcon(QuestPages.class, "tip.png");

    private final JXLabel tipLabel = new JXLabel();
    private List<String> tips = new ArrayList<>();
    private int index = 0;

    public TipPanel(Class<?> clazz, String resourceName) {
        setLayout(new MigLayout("insets panel", "[][grow]"));

        Border macBorder = UIManager.getDefaults().getBorder("InsetBorder.aquaVariant");
        setBorder(macBorder != null ? macBorder : BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10), BorderFactory.createTitledBorder("Tip of the Day")));

        tipLabel.setLineWrap(true);

        add(new JLabel(TIP_ICON), "spany 2, aligny top");
        add(tipLabel, "w 300, growx, span");

        Thread thread = new Thread(new TipLoader(clazz, resourceName), "Tip of the Day Loader");
        thread.setDaemon(true);
        thread.start();
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
        if (!tips.isEmpty()) {
            index = 0;
            tipLabel.setText(tips.get(index));
        } else {
            tipLabel.setText("No tip of the day could be loaded.");
        }
    }

    public void nextTip() {
        index++;
        if (index >= tips.size()) {
            index = 0;
        }
        if (!tips.isEmpty()) {
            tipLabel.setText(tips.get(index));
        }
    }

    private class TipLoader implements Runnable {
        private final Class<?> clazz;
        private final String resourceName;

        private TipLoader(Class<?> clazz, String resourceName) {
            this.clazz = clazz;
            this.resourceName = resourceName;
        }

        @Override
        public void run() {
            final List<String> tips = new ArrayList<>();
            InputStream inputStream = clazz.getResourceAsStream(resourceName);
            if (inputStream != null) {
                try (InputStream is = inputStream;
                        InputStreamReader isr = new InputStreamReader(is, "utf-8");
                        BufferedReader br = new BufferedReader(isr)) {
                    String line;

                    while ((line = br.readLine()) != null) {
                        line = line.trim();
                        if (!line.isEmpty()) {
                            tips.add(line);
                        }
                    }

                    Collections.shuffle(tips);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            setTips(tips);
                        }
                    });
                } catch (IOException e) {
                    log.log(Level.WARNING, "Failed to read tips of the day from " + clazz.getName() + ":" + resourceName, e);
                }
            }
        }
    }

}
