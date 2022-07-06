package beatum.happy.domain.helper;

import beatum.happy.domain.helper.forms.Main;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;

/**
 * Main
 */
public class App {

    /*
     * LaF
     * */
    static {
        try {
            UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    public static void main(String[] args) {
        /*
         * initiation spring context
         * */
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);

        /*
         * start main form
         * */
        Main mainFrom = (Main) context.getBean("mainFrom");

        EventQueue.invokeLater(() -> {
            try {
                mainFrom.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
