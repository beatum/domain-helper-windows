package beatum.happy.domain.helper.forms;

import beatum.happy.domain.helper.model.Instance;
import beatum.happy.domain.helper.model.Response;
import beatum.happy.domain.helper.service.IinstanceService;
import beatum.happy.domain.helper.utility.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import static beatum.happy.domain.helper.utility.utils.getImage;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/1/2022 3:14 PM
 */
public class Main extends JFrame {

    private String HEADING_TEXT;

    //instance service
    private IinstanceService instanceService = null;

    //Menu bar
    private JMenuBar menu;

    //menu item of file
    private JMenu menuItemFile;

    //menu item of help
    private JMenu menuItemHelp;

    //label of host name
    private JLabel lhostName;

    //Text filed of host name
    private JTextField textHostName;

    //combobox of instance
    private JComboBox comboxOfInstance;

    //Button of start
    private JButton buttonOfStart;


    public Main(String title, IinstanceService instanceService) {
        HEADING_TEXT = title;
        this.instanceService = instanceService;
        initiation();
    }

    /*
     * initiation
     * */
    private void initiation() {

        this.setTitle(HEADING_TEXT);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 260);

        // setIconImage(utils.getImageFromResource("imgs/sketch48.png"));
        setIconImage(getImage("imgs/sketch48.png"));

        //layout
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 3, 6);
        setLayout(layout);

        //Menu bar
        menu = new JMenuBar();
        menu.setFont(new Font("Arial", Font.PLAIN, 12));

        //Menu - file
        menuItemFile = new JMenu("File");
        menuItemFile.setFont(new Font("Arial", Font.PLAIN, 12));

        //Menu - help
        menuItemHelp = new JMenu("Help");
        menuItemHelp.setFont(new Font("Arial", Font.PLAIN, 12));


        //Menu item for join domain
        JMenuItem menuItemOfNetJoindomain = new JMenuItem("Joining domain");
        menuItemOfNetJoindomain.setIcon(new ImageIcon(getImage("imgs/add_16px.png")));
        menuItemFile.add(menuItemOfNetJoindomain);
        menuItemOfNetJoindomain.addActionListener(new ActionListenerForMenuItems());


        //Menu item for undo join domain
        JMenuItem menuItemOfUndoJoindomain = new JMenuItem("UndoJoin domain");
        menuItemOfUndoJoindomain.setIcon(new ImageIcon(getImage("imgs/del_file_16px.png")));
        menuItemFile.add(menuItemOfUndoJoindomain);
        menuItemOfUndoJoindomain.addActionListener(new ActionListenerForMenuItems());

        //Menu item for set computer name
        JMenuItem menuItemOfSetComputerName = new JMenuItem("Set computer name");
        menuItemOfSetComputerName.setIcon(new ImageIcon(getImage("imgs/Company_16_01.png")));
        menuItemFile.add(menuItemOfSetComputerName);
        menuItemOfSetComputerName.addActionListener(new ActionListenerForMenuItems());


        //set menu bar
        menu.add(menuItemFile);
        menu.add(menuItemHelp);
        setJMenuBar(menu);

        //panel of host
        JPanel jPanelHost = new JPanel();
        //Label of hostname
        lhostName = new JLabel("Host name:");
        jPanelHost.add(lhostName);

        //Text box of host name
        textHostName = new JTextField();
        textHostName.setColumns(22);
        jPanelHost.add(textHostName);
        add(jPanelHost);

        //panel of instance
        JPanel panelOfInstance = new JPanel();
        //label of instance
        JLabel lInstance = new JLabel("instance:    ");
        panelOfInstance.add(lInstance);

        //Combobox of instance items
        comboxOfInstance = new JComboBox();
        comboxOfInstance.setSize(new Dimension(100, 22));
        comboxOfInstance.setPreferredSize(new Dimension(260, 27));
        panelOfInstance.add(comboxOfInstance);
        add(panelOfInstance);

        //start
        buttonOfStart = new JButton();
        buttonOfStart.setText("Start");
        buttonOfStart.addMouseListener(new MouseListenerOfStart());
        add(buttonOfStart);

        //load all instance items
        loadAllInstanceItems();
        //load computer name
        loadComputerName();
    }

    /*
     * load all instance items
     * */
    private void loadAllInstanceItems() {
        Response response = instanceService.getAllInstances();
        if (!response.isResult()) {
            JOptionPane.showMessageDialog(new JFrame(), response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Instance> instances = (List<Instance>) response.getObject();
        comboxOfInstance.removeAllItems();
        for (int i = 0; i < instances.size(); i++) {
            Instance instance = instances.get(i);
            comboxOfInstance.addItem(instance.getInstance_name());
        }
    }


    /*
     * load computer name
     * */
    private void loadComputerName() {
        String name = utils.getComputerName();
        if (null != name) {
            textHostName.setText(null);
            textHostName.setText(name);
        }
    }


    /*
     * Mouse listener for start
     * */
    private class MouseListenerOfStart implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            /*
            final File file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String absoluteJarPath = file.getAbsolutePath();
            System.out.println(absoluteJarPath);
             */

            String hostName = textHostName.getText();
            if (null == hostName || hostName.trim().equals("")) {
                JOptionPane.showMessageDialog(new JFrame(), "Enter your host name please!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Object selectedItem = comboxOfInstance.getSelectedItem();
            if (null == selectedItem) {
                JOptionPane.showMessageDialog(new JFrame(), "Choose your executions instance!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String instanceName = selectedItem.toString().trim();
            //get one instance by name
            Response response = instanceService.getInstanceByName(instanceName);
            if (!response.isResult()) {
                JOptionPane.showMessageDialog(new JFrame(), response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Instance instance = (Instance) response.getObject();
            if (null == instance) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid instance", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int result = JOptionPane.showConfirmDialog(new Frame(), "are you sure to continue??", "confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            StringBuffer message = new StringBuffer();

            //0. Join domain
            Response responseOfJoinDomain = utils.joinDomain(null,instance.getDomain(), instance.getAccount(), instance.getPassword());
            message.append(responseOfJoinDomain.getMessage() + "\n");
            //1.Change host name
            Response responseOfChangeHostName = utils.changeHostName(hostName);
            message.append(responseOfChangeHostName.getMessage() + "\n");
            //2.ChangeNetBIOS
            Response responseOfChangeNetBIOS = utils.changeNetBIOSName(hostName);
            message.append(responseOfChangeNetBIOS.getMessage() + "\n");

            //3.Join domain
            //Response responseOfJoinDoamin = utils.joinDomain(hostName,instance.getDomain(), instance.getAccount(), instance.getPassword());
            //message.append(responseOfJoinDoamin.getMessage() + "\n");

            JOptionPane.showMessageDialog(new JFrame(), message.toString(), "Info", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Done...");
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    /*
     *
     * */
    private class ActionListenerForMenuItems implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            int result = JOptionPane.showConfirmDialog(Main.this, "are you sure to continue??", "confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            if (actionCommand.equals("Joining domain")) {
                Object selectedItem = comboxOfInstance.getSelectedItem();
                if (null == selectedItem) {
                    JOptionPane.showMessageDialog(new JFrame(), "Choose your executions instance!!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String instanceName = selectedItem.toString().trim();
                //get one instance by name
                Response response = instanceService.getInstanceByName(instanceName);
                if (!response.isResult()) {
                    JOptionPane.showMessageDialog(new JFrame(), response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Instance instance = (Instance) response.getObject();
                if (null == instance) {
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid instance", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //3.Undo join domain
                Response responseOfJoinDoamin = utils.joinDomain(null,instance.getDomain(), instance.getAccount(), instance.getPassword());
                JOptionPane.showMessageDialog(new JFrame(), responseOfJoinDoamin.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            if (actionCommand.equals("UndoJoin domain")) {
                Response response = utils.NetUnjoinDomain(null, null);
                JOptionPane.showMessageDialog(new JFrame(), response.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            if (actionCommand.equals("Set computer name")) {
                String hostName = textHostName.getText();
                if (null == hostName || hostName.trim().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Enter your host name please!!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuffer message = new StringBuffer();
                // 1. Change host name
                Response responseOfChangeHostName = utils.changeHostName(hostName.trim());
                message.append(responseOfChangeHostName.getMessage() + "\n");
                //2.ChangeNetBIOS
                Response responseOfChangeNetBIOS = utils.changeNetBIOSName(hostName.trim());
                message.append(responseOfChangeNetBIOS.getMessage() + "\n");
                JOptionPane.showMessageDialog(new JFrame(), message.toString(), "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

}
