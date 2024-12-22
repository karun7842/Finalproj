package his.welcome;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.appointment.his.controller.AppointmentController;
import com.appointment.his.view.AppointmentFormPanel;
import com.appointment.his.view.AppointmentView;
import com.consultation.his.app.EPrescriptionApp;
import com.doctorregistration.his.controller.DoctorTableModel;
import com.doctorregistration.his.view.BrowserPanel;
import com.doctorregistration.his.view.DoctorProfilePanel;
import com.patientregistration.his.controller.PatientRegistrationController;
import com.patientregistration.his.view.PatientRegistrationView;

public class WelcomePage {

	public static JPanel editPanel;
	static JPanel mainPanel;
	static CardLayout cardLayout;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Hospital Information System");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setLocationRelativeTo(null);

			mainPanel = new JPanel(new CardLayout());
			cardLayout = (CardLayout) mainPanel.getLayout();

			JPanel welcomePanel = new JPanel(new BorderLayout());
			JLabel imageLabel = new JLabel(new ImageIcon("src\\main\\java\\assets\\welcome.jpg"));
			welcomePanel.add(imageLabel, BorderLayout.CENTER);

			JPanel registrationPanel = new JPanel();
			registrationPanel.add(new JLabel("Patient & Doctor Registration"));

			JPanel appointmentPanel = new JPanel();
			appointmentPanel.add(new JLabel("Create & Reschedule Appointments"));

			JPanel consultationPanel = new JPanel();
			consultationPanel.add(new JLabel("Consultation Screen"));

			mainPanel.add(welcomePanel, "Welcome");
			mainPanel.add(registrationPanel, "Registration");
			mainPanel.add(appointmentPanel, "Appointments");
			mainPanel.add(consultationPanel, "Consultation");

			JMenuBar menuBar = new JMenuBar();

			JMenu homeMenu = new JMenu("Home");

			homeMenu.addMenuListener(new MenuListener() {

				@Override
				public void menuSelected(MenuEvent e) {
					cardLayout.show(mainPanel, "Welcome");
				}

				@Override
				public void menuDeselected(MenuEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void menuCanceled(MenuEvent e) {
					// TODO Auto-generated method stub

				}
			});

			JMenu registrationMenu = new JMenu("Registration");
			JMenuItem patientRegistration = new JMenuItem("Patient Registration");
			JMenuItem updatePatients = new JMenuItem("Update Patients");

			registrationMenu.add(patientRegistration);
			registrationMenu.add(updatePatients);

			JMenu appointmentsMenu = new JMenu("Appointments");
			JMenuItem createAppointment = new JMenuItem("Create Appointment");

			createAppointment.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AppointmentView view = new AppointmentView(false);
					AppointmentController controller = new AppointmentController(view);
					AppointmentFormPanel formPanel = new AppointmentFormPanel(null);
					mainPanel.add(formPanel, "FormPanel");
					cardLayout.show(mainPanel, "FormPanel");
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

			patientRegistration.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					PatientRegistrationView patientRegistrationView = new PatientRegistrationView();
					PatientRegistrationController controller = new PatientRegistrationController(
							patientRegistrationView);

					patientRegistrationView.registerButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								String firstName = patientRegistrationView.firstNameField.getText();
								String lastName = patientRegistrationView.lastNameField.getText();

								Date dobDate = patientRegistrationView.dobChooser.getDate();
								String sex = (String) patientRegistrationView.sexCombo.getSelectedItem();
								String maritalStatus = (String) patientRegistrationView.maritalStatusCombo
										.getSelectedItem();
								String addressLine1 = patientRegistrationView.addressLine1Field.getText();
								String addressLine2 = patientRegistrationView.addressLine2Field.getText();
								String addressLine3 = patientRegistrationView.addressLine3Field.getText();
								String pincode = patientRegistrationView.pincodeField.getText();
								String state = (String) patientRegistrationView.stateComboBox.getSelectedItem();
								String fullAddress = String
										.join(", ", addressLine1, addressLine2, addressLine3, pincode, state).trim();
								String email = patientRegistrationView.emailField.getText();
								String bloodGroup = (String) patientRegistrationView.bloodGroupCombo.getSelectedItem();
								String contactText = patientRegistrationView.contactField.getText().trim();
								long contactNumber = Long.parseLong(contactText);
								String emergencyContactName = patientRegistrationView.emergencyContactNameField
										.getText();
								String emergencyContactText = patientRegistrationView.emergencyContactNumberField
										.getText().trim();
								long emergencyContactNumber = 0;
								LocalDate dob = dobDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

								try {
									if (firstName.isEmpty() || dobDate == null || addressLine1.isEmpty()
											|| pincode.isEmpty() || state.isEmpty() || email.isEmpty()
											|| contactText.isEmpty() || pincode.isEmpty() || state.isEmpty()) {
										System.out.println("FIrst name empty");
										JOptionPane.showMessageDialog(new JFrame(), "All fields are mandatory!");
										return;
									}

									if (!firstName.matches("^[A-Za-z]+$")) {
										JOptionPane.showMessageDialog(new JFrame(),
												"Name fields must contain letters only");
										return;
									}
									dob = dobDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
									if (dob.isAfter(LocalDate.now())) {
										JOptionPane.showMessageDialog(new JFrame(), "Invalid date of birth");
										return;
									}
									if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
										JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid email!");
										return;
									}
									if (!contactText.matches("^[6-9][0-9]{9}$")) {
										JOptionPane.showMessageDialog(new JFrame(), "Invalid phone number format!");
										return;
									}
									if (!pincode.matches("\\d{6}")) {
										JOptionPane.showMessageDialog(new JFrame(), "Invalid pincode");
										return;
									}
									if (!emergencyContactText.isEmpty()) {
										emergencyContactNumber = Long.parseLong(emergencyContactText);
									}

								} catch (Exception e1) {
									JOptionPane.showMessageDialog(new JFrame(), "All fields are mandatory!");
								}

								LocalDate currentDate = LocalDate.now();
								Period agePeriod = Period.between(dob, currentDate);
								String ageText;
								int years = agePeriod.getYears();
								int months = agePeriod.getMonths();
								if (dob.getYear() == currentDate.getYear()) {

									ageText = months + " Months";
									years = 0; // Set years to 0
								} else {

									ageText = years + " Years, " + months + " Months";
								}

								patientRegistrationView.ageField.setText(ageText);

								com.patientregistration.his.model.Patient patient = new com.patientregistration.his.model.Patient(
										firstName, lastName, dob, years, months, sex, maritalStatus, bloodGroup, email,
										contactNumber, fullAddress, pincode, state, emergencyContactName,
										emergencyContactText.isEmpty() ? 0 : emergencyContactNumber);

								List<com.patientregistration.his.model.Patient> patients = controller.jsonHandler
										.readPatients();
								patients.add(patient);
								controller.jsonHandler.savePatients(patients);
								JOptionPane.showMessageDialog(new JFrame(), "Patient registered successfully!");
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(new JFrame(), "All fields are mandatory");
								return;
							}

							patientRegistrationView.resetFields();
						}

					});

					mainPanel.add(patientRegistrationView, "View");
					cardLayout.show(mainPanel, "View");
				}
			});

			updatePatients.addActionListener(new ActionListener() {
				PatientRegistrationView patientRegistrationView = new PatientRegistrationView();
				PatientRegistrationController controller = new PatientRegistrationController(patientRegistrationView);

				@Override
				public void actionPerformed(ActionEvent e) {

					JPanel viewPatientPanel = controller.viewPatients();

					mainPanel.add(viewPatientPanel, "UpdatePatients");
					cardLayout.show(mainPanel, "UpdatePatients");
				}

			});

			JMenu employeRegistration = new JMenu("Employee Registration");
			JMenuItem doctorRegistration = new JMenuItem("Doctor Registration");
			doctorRegistration.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					JPanel container = new JPanel(cardLayout);
					DoctorTableModel tableModel = new DoctorTableModel();
					final JTable table = new JTable(tableModel);
					tableModel.setupTableButtons(table, cardLayout, container);
					DoctorProfilePanel profilePanel = new DoctorProfilePanel(cardLayout, container, tableModel);
					BrowserPanel browserPanel = new BrowserPanel(cardLayout, container, tableModel, table);

					container.add(new JScrollPane(profilePanel), "Profile");
					container.add(browserPanel, "Browser");
					mainPanel.add(container, "DoctorPanel");
					cardLayout.show(mainPanel, "DoctorPanel");

				}
			});

			employeRegistration.add(doctorRegistration);

			JMenuItem updateDoc = new JMenuItem("Update Doctor");
			updateDoc.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					JPanel container = new JPanel(cardLayout);
					DoctorTableModel tableModel = new DoctorTableModel();
					final JTable table = new JTable(tableModel);
					tableModel.setupTableButtons(table, cardLayout, container);
					DoctorProfilePanel profilePanel = new DoctorProfilePanel(cardLayout, container, tableModel);
					BrowserPanel browserPanel = new BrowserPanel(cardLayout, container, tableModel, table);

					container.add(browserPanel, "Browser");
					mainPanel.add(container, "DoctorPanel");
					cardLayout.show(mainPanel, "DoctorPanel");
				}
			});
			employeRegistration.add(updateDoc);

			// Browser Menu
			JMenu browserMenu = new JMenu("Browser");
			JMenuItem searchPatient = new JMenuItem("Search Patient");
			searchPatient.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					PatientRegistrationView patientView = new PatientRegistrationView();
					PatientRegistrationController registrationController = new PatientRegistrationController(
							patientView);
					JPanel tempPanel = registrationController.viewPatients();

					mainPanel.add(tempPanel, "ViewPatients");
					cardLayout.show(mainPanel, "ViewPatients");
				}
			});
			browserMenu.add(searchPatient);

			JMenuItem searchDoctor = new JMenuItem("Search Doctor");
			searchDoctor.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel container = new JPanel(cardLayout);
					DoctorTableModel tableModel = new DoctorTableModel();
					final JTable table = new JTable(tableModel);
					tableModel.setupTableButtons(table, cardLayout, container);
					DoctorProfilePanel profilePanel = new DoctorProfilePanel(cardLayout, container, tableModel);
					BrowserPanel browserPanel = new BrowserPanel(cardLayout, container, tableModel, table);

					container.add(browserPanel, "Browser");
					mainPanel.add(container, "DoctorPanel");
					cardLayout.show(mainPanel, "DoctorPanel");
				}
			});
			browserMenu.add(searchDoctor);

			JMenu consultationMenu = new JMenu("Consultation");
			JMenuItem sameConsultation = new JMenuItem("Consultation");
			consultationMenu.add(sameConsultation);

			sameConsultation.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					EPrescriptionApp prescriptionPanel = new EPrescriptionApp();
					mainPanel.add(prescriptionPanel, "prescriptionPanel");
					cardLayout.show(mainPanel, "prescriptionPanel");

				}
			});

			menuBar.add(homeMenu);
			menuBar.add(registrationMenu);
			menuBar.add(employeRegistration);
			menuBar.add(appointmentsMenu);
			menuBar.add(browserMenu);
			menuBar.add(consultationMenu);

			frame.setJMenuBar(menuBar);
			frame.add(mainPanel);

			cardLayout.show(mainPanel, "Welcome");

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

	public static void setUpdatePanel(PatientRegistrationView patientRegistrationView) {
		mainPanel.add(patientRegistrationView, "Hii");
		cardLayout.show(mainPanel, "Hii");
	}

}
