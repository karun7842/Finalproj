
package his.welcome;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
            frame.setLocationRelativeTo(null); // Center the window
 
            // Create a panel for the welcome screen content
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
 
            // Create and add a hospital-related image
            JLabel imageLabel = new JLabel(new ImageIcon("src\\main\\java\\assets\\welcome.jpg")); // Use your hospital image here
            panel.add(imageLabel, BorderLayout.CENTER);
 
            // Create a menu bar
            JMenuBar menuBar = new JMenuBar();
 
            // Create the "Registration" menu
            
            JMenu registrationMenu = new JMenu("Registration");

            JMenuItem patientRegistration = new JMenuItem("Patient Registration");
            patientRegistration.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Patient Registration clicked.");
                    // Here you can add the actual registration functionality
                }
            });
            registrationMenu.add(patientRegistration);

            JMenuItem doctorRegistration = new JMenuItem("Doctor Registration");
            doctorRegistration.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Doctor Registration clicked.");
                    // Here you can add the actual registration functionality
                }
            });
            registrationMenu.add(doctorRegistration);

            // Create the "Appointments" menu
            JMenu appointmentsMenu = new JMenu("Appointments");

            JMenuItem createAppointment = new JMenuItem("Create Appointment");
            createAppointment.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //JOptionPane.showMessageDialog(frame, "Create Appointment clicked.");
                    SwingUtilities.invokeLater(() -> {
                        AppointmentView view = new AppointmentView(false);
                        AppointmentController controller = new AppointmentController(view);
                        controller.viewAppointments();
                        view.display();
                    });
                    // Add create appointment functionality here
                }
            });
            appointmentsMenu.add(createAppointment);

            JMenuItem rescheduleAppointment = new JMenuItem("Reschedule Appointment");
            appointmentsMenu.add(rescheduleAppointment);
            
            rescheduleAppointment.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   // JOptionPane.showMessageDialog(frame, "Reschedule Appointment clicked.");
                    SwingUtilities.invokeLater(() -> {
                        AppointmentView view = new AppointmentView(true);
                        AppointmentController controller = new AppointmentController(view);
                        controller.viewAppointments();
                        view.display();
                    });
                    // Add reschedule appointment functionality here
                }
            });
           

            // Create the "Browser" menu
            JMenu browserMenu = new JMenu("Browser");

            JMenuItem searchPatient = new JMenuItem("Search Patient");
            searchPatient.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Search Patient clicked.");
                    // Add search patient functionality here
                }
            });
            browserMenu.add(searchPatient);

            JMenuItem searchDoctor = new JMenuItem("Search Doctor");
            searchDoctor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Search Doctor clicked.");
                    // Add search doctor functionality here
                }
            });
            browserMenu.add(searchDoctor);

            // Create the "Consultation" menu
            JMenu consultationMenu = new JMenu("Consultation");

            // Create the "Same Consultation" menu item
            JMenuItem consultation = new JMenuItem(" Consultation");
            consultation.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Same Consultation clicked.");
                    // Add same consultation functionality here
                }
            });
            consultationMenu.add(consultation);

            // Add the menus to the menu bar
            menuBar.add(registrationMenu);
            menuBar.add(appointmentsMenu);
            menuBar.add(browserMenu);
            menuBar.add(consultationMenu); // Add Consultation menu here

            // Set the menu bar for the frame
            frame.setJMenuBar(menuBar);

            // Add the main panel to the frame
            frame.add(panel);

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


