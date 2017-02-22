package com.sk89q.questpages.util.swing;

import lombok.NonNull;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import static com.sk89q.questpages.util.SharedLocale.tr;

@Log
public final class SwingHelper {

    private SwingHelper() {
    }

    public static void showErrorDialog(Component parentComponent, @NonNull String message,
                                       @NonNull String title) {
        showErrorDialog(parentComponent, message, title, null);
    }

    public static void showErrorDialog(Component parentComponent, @NonNull String message,
                                       @NonNull String title, Throwable throwable) {
        String detailsText = null;

        // Get a string version of the exception and use that for
        // the extra details text
        if (throwable != null) {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            detailsText = sw.toString();
        }

        showMessageDialog(parentComponent,
                message, title,
                detailsText, JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessageDialog(final Component parentComponent,
                                         @NonNull final String message,
                                         @NonNull final String title,
                                         final String detailsText,
                                         final int messageType) {

        if (SwingUtilities.isEventDispatchThread()) {
            // To force the label to wrap, convert the message to broken HTML
            String htmlMessage = "<html><div style=\"width: 250px\">" + htmlEscape(message);

            JPanel panel = new JPanel(new BorderLayout(0, detailsText != null ? 20 : 0));

            // Add the main message
            panel.add(new JLabel(htmlMessage), BorderLayout.NORTH);

            // Add the extra details
            if (detailsText != null) {
                JTextArea textArea = new JTextArea(tr("errors.reportErrorPreface") + detailsText);
                JLabel tempLabel = new JLabel();
                textArea.setFont(tempLabel.getFont());
                textArea.setBackground(tempLabel.getBackground());
                textArea.setTabSize(2);
                textArea.setEditable(false);
                textArea.setComponentPopupMenu(TextFieldPopupMenu.INSTANCE);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(350, 120));
                panel.add(scrollPane, BorderLayout.CENTER);
            }

            JOptionPane.showMessageDialog(
                    parentComponent, panel, title, messageType);
        } else {
            // Call method again from the Event Dispatch Thread
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        showMessageDialog(
                                parentComponent, message, title,
                                detailsText, messageType);
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean confirmDialog(final Component parentComponent,
                                        @NonNull final String message,
                                        @NonNull final String title) {
        if (SwingUtilities.isEventDispatchThread()) {
            return JOptionPane.showConfirmDialog(
                    parentComponent, message, title, JOptionPane.YES_NO_OPTION) ==
                    JOptionPane.YES_OPTION;
        } else {
            // Use an AtomicBoolean to pass the result back from the
            // Event Dispatcher Thread
            final AtomicBoolean yesSelected = new AtomicBoolean();

            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        yesSelected.set(confirmDialog(parentComponent, title, message));
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            return yesSelected.get();
        }
    }

    public static String htmlEscape(String str) {
        return str.replace(">", "&gt;")
                .replace("<", "&lt;")
                .replace("&", "&amp;");
    }

    public static String hexCode(Color color) {
        return String.format("%06x", color.getRGB() & 0xFFFFFF);
    }

    public static Image readImage(Class<?> clazz, String name) {
        URL resource = clazz.getResource(name);
        if (resource != null) {
            Image image = Toolkit.getDefaultToolkit().createImage(resource);
            image.getSource().startProduction(DummyConsumer.INSTANCE);
            return image;
        } else {
            log.log(Level.WARNING, "Failed to read image from resource: " + name);
            return null;
        }
    }

    public static ImageIcon readImageIcon(Class<?> clazz, String name) {
        Image image = readImage(clazz, name);
        if (image != null) {
            return new ImageIcon(image);
        } else {
            return null;
        }
    }
    public static void setIconImage(Window frame, Class<?> clazz, String path) {
        Image image = readImage(clazz, path);
        if (image != null) {
            frame.setIconImage(image);
        }
    }

    public static void removeOpaqueness(Component ... components) {
        for (Component component : components) {
            if (component instanceof JComponent) {
                JComponent jComponent = (JComponent) component;
                jComponent.setOpaque(false);
                removeOpaqueness(jComponent.getComponents());
            }
        }
    }

    public static JScrollPane wrapScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    public static void setEscapeButton(JRootPane rootPane, final AbstractButton button) {
        rootPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public static void focusLater(Window window, final Component component) {
        window.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                component.requestFocus();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

}
