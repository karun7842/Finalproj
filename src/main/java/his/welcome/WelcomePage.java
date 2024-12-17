package his.welcome;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.his.AppointmentSchedulingSystem.controller.AppointmentController;
import com.his.AppointmentSchedulingSystem.view.AppointmentView;

public class WelcomePage {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hospital Information System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);

            // Create CardLayout
            JPanel mainPanel = new JPanel(new CardLayout());
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

            // Welcome Screen Panel
            JPanel welcomePanel = new JPanel(new BorderLayout());
            JLabel imageLabel = new JLabel(new ImageIcon("src\\main\\java\\assets\\welcome.jpg"));
            welcomePanel.add(imageLabel, BorderLayout.CENTER);

            // Panels for Menu Options
            JPanel registrationPanel = new JPanel();
            registrationPanel.add(new JLabel("Patient & Doctor Registration"));

            JPanel appointmentPanel = new JPanel();
            appointmentPanel.add(new JLabel("Create & Reschedule Appointments"));

            JPanel consultationPanel = new JPanel();
            consultationPanel.add(new JLabel("Consultation Screen"));

            // Add Panels to CardLayout
            mainPanel.add(welcomePanel, "Welcome");
            mainPanel.add(registrationPanel, "Registration");
            mainPanel.add(appointmentPanel, "Appointments");
            mainPanel.add(consultationPanel, "Consultation");

            // Create Menu Bar
            JMenuBar menuBar = new JMenuBar();

            // Home Menu
            JMenu homeMenu = new JMenu("Home");
            JMenuItem homeItem = new JMenuItem("Home");
            homeItem.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
            homeMenu.add(homeItem);

            // Registration Menu
            JMenu registrationMenu = new JMenu("Registration");
            JMenuItem patientRegistration = new JMenuItem("Patient Registration");
            patientRegistration.addActionListener(e -> cardLayout.show(mainPanel, "Registration"));
            registrationMenu.add(patientRegistration);

            JMenuItem doctorRegistration = new JMenuItem("Doctor Registration");
            doctorRegistration.addActionListener(e -> cardLayout.show(mainPanel, "Registration"));
            registrationMenu.add(doctorRegistration);

            // Appointments Menu
            JMenu appointmentsMenu = new JMenu("Appointments");
            JMenuItem createAppointment = new JMenuItem("Create Appointment");

            createAppointment.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AppointmentView view = new AppointmentView(false);
                    AppointmentController controller = new AppointmentController(view);
                    controller.viewAppointments();
                    mainPanel.add(view, "AppointmentView");
                    cardLayout.show(mainPanel, "AppointmentView");
                }
            });
            appointmentsMenu.add(createAppointment);

            JMenuItem rescheduleAppointment = new JMenuItem("Reschedule Appointment");

            rescheduleAppointment.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        AppointmentView view = new AppointmentView(true);
                        AppointmentController controller = new AppointmentController(view);
                        controller.viewAppointments();
                        mainPanel.add(view, "RescheduleView");
                        cardLayout.show(mainPanel, "RescheduleView");
                    });
                }
            });
            appointmentsMenu.add(rescheduleAppointment);

            // Browser Menu
            JMenu browserMenu = new JMenu("Browser");
            JMenuItem searchPatient = new JMenuItem("Search Patient");
            searchPatient.addActionListener(e -> cardLayout.show(mainPanel, "Registration"));
            browserMenu.add(searchPatient);

            JMenuItem searchDoctor = new JMenuItem("Search Doctor");
            searchDoctor.addActionListener(e -> cardLayout.show(mainPanel, "Registration"));
            browserMenu.add(searchDoctor);

            // Consultation Menu
            JMenu consultationMenu = new JMenu("Consultation");
            JMenuItem sameConsultation = new JMenuItem("Consultation");
            sameConsultation.addActionListener(e -> cardLayout.show(mainPanel, "Consultation"));
            consultationMenu.add(sameConsultation);

            // Add Menus to Menu Bar
            menuBar.add(homeMenu); // Add Home menu
            menuBar.add(registrationMenu);
            menuBar.add(appointmentsMenu);
            menuBar.add(browserMenu);
            menuBar.add(consultationMenu);

            // Set Menu Bar and Main Panel
            frame.setJMenuBar(menuBar);
            frame.add(mainPanel);

            // Show Welcome Panel Initially
            cardLayout.show(mainPanel, "Welcome");

            // Make the frame visible
            frame.setVisible(true);
        });

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Nimbus Look and Feel not applied. Falling back to default.");
        }
    }
}
