package com.appointment.his.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.lang.ModuleLayer.Controller;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.TitledBorder;

import com.appointment.his.controller.AppointmentController;
import com.appointment.his.model.Appointment;
import com.appointment.his.model.Doctor;
import com.appointment.his.model.Patient;
import com.toedter.calendar.JDateChooser;

public class AppointmentFormPanel extends JPanel {
	private JTextField mriIdTextField;
	private JButton searchButton;
	private JTextField patientNameField, patientPhoneField, patientEmailField;
	private JComboBox<String> departmentComboBox, specializationComboBox, doctorNameComboBox;
	private JDateChooser appointmentDateChooser;
	private JSpinner appointmentTimeSpinner;
	private JButton submitButton, cancelButton, scheduleAssistantButton;
	private Appointment appointment;
	private Patient selectedPatient;
	private Doctor selectedDoctor;
	private JPanel buttonPanel;

	private ArrayList<Doctor> doctors;
	private ArrayList<Patient> patients;
	private Set<String> specializationArray = new HashSet<>();
	private Set<String> doctorsSet = new HashSet<>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
	List<String> timeSlots;

	public AppointmentFormPanel(Appointment appointment) {

		setLayout(new BorderLayout(10, 10));
		setBackground(Color.decode("#E3F2FD"));
		

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(0, 153, 153));
		JLabel headerLabel = new JLabel("Add Appointment");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
		headerPanel.add(headerLabel);
		add(headerPanel, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(Color.WHITE);
		add(mainPanel, BorderLayout.CENTER);

		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setPreferredSize(new Dimension(800, 600));
		formPanel.getPreferredSize();
		formPanel.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		patients = AppointmentController.loadPatients();
		doctors = AppointmentController.loadDoctors();

		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(new JLabel("MRD Number:"), gbc);
		mriIdTextField = new JTextField(15);
		gbc.gridx = 1;
		formPanel.add(mriIdTextField, gbc);
		searchButton = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\2021617\\Downloads\\image2.png").getImage()
				.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		searchButton.setPreferredSize(new Dimension(32, 32));
		searchButton.addActionListener(e -> searchPatient());
		gbc.gridx = 2;
		formPanel.add(searchButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(new JLabel("Patient Name:"), gbc);
		patientNameField = new JTextField(15);
		patientNameField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		formPanel.add(patientNameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		formPanel.add(new JLabel("Patient Phone:"), gbc);
		patientPhoneField = new JTextField(15);
		patientPhoneField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		formPanel.add(patientPhoneField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		formPanel.add(new JLabel("Patient Email:"), gbc);
		patientEmailField = new JTextField(15);
		patientEmailField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		formPanel.add(patientEmailField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		formPanel.add(new JSeparator(), gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		formPanel.add(new JLabel("Department:"), gbc);
		for (Doctor doctor : doctors) {
			specializationArray.add(doctor.getDepartment());
		}
		departmentComboBox = new JComboBox<>(specializationArray.toArray(new String[0]));
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		formPanel.add(departmentComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		formPanel.add(new JLabel("Specialization:"), gbc);
		specializationComboBox = new JComboBox<>();
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		formPanel.add(specializationComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		formPanel.add(new JLabel("Doctor Name:"), gbc);
		doctorNameComboBox = new JComboBox<>();
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		formPanel.add(doctorNameComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		formPanel.add(new JLabel("Appointment Date:"), gbc);
		appointmentDateChooser = new JDateChooser();
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		formPanel.add(appointmentDateChooser, gbc);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		formPanel.add(new JLabel("Appointment Time:"), gbc);
		appointmentTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(appointmentTimeSpinner, "hh:mm a");
		appointmentTimeSpinner.setEditor(timeEditor);
		appointmentTimeSpinner.setValue(new Date());
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		formPanel.add(appointmentTimeSpinner, gbc);

		JPanel schedulePanel = new JPanel();
		schedulePanel.setBackground(Color.white);
		scheduleAssistantButton = new JButton("Schedule Assistant");
		scheduleAssistantButton.addActionListener(e -> openScheduleAssistantWindow());
		scheduleAssistantButton.setBackground(new Color(0, 150, 139));
		scheduleAssistantButton.setForeground(Color.WHITE);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		schedulePanel.add(scheduleAssistantButton);

		formPanel.add(schedulePanel, gbc);

		buttonPanel = new JPanel();

		buttonPanel.setBackground(Color.WHITE);

		submitButton = new JButton("Submit");
		submitButton.setBackground(new Color(0, 150, 139));
		submitButton.setForeground(Color.WHITE);
//		rescheduleSubmit = new JButton("Reshedule");
//		rescheduleSubmit.setBackground(new Color(0, 150, 139));
//		rescheduleSubmit.setForeground(Color.WHITE);
//		rescheduleSubmit.setVisible(false);
		cancelButton = new JButton("Clear");
		cancelButton.setBackground(new Color(0, 150, 139));
		cancelButton.setForeground(Color.WHITE);

//		buttonPanel.add(rescheduleSubmit);
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);

		submitButton.addActionListener(e -> submit());
		cancelButton.addActionListener(e -> cancel());

		departmentComboBox.addActionListener(e -> updateSpecializationComboBox());
		specializationComboBox.addActionListener(e -> updateDoctorComboBox());

		gbc.gridy = 11;
		formPanel.add(new JSeparator(), gbc);

		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		formPanel.add(buttonPanel, gbc);

		mainPanel.add(formPanel);

		if (appointment != null) {
			this.appointment = appointment;
			selectedPatient = appointment.getPatient();
			selectedDoctor = appointment.getDoctor();
			mriIdTextField.setText(String.valueOf(selectedPatient.getMrdID()));
			patientNameField.setText(selectedPatient.getFirstName());
			patientPhoneField.setText(String.valueOf(selectedPatient.getPhoneNumber()));
			patientEmailField.setText(selectedPatient.getEmail());
			departmentComboBox.setSelectedItem(selectedDoctor.getDepartment());
			specializationComboBox.setSelectedItem(selectedDoctor.getSpecialization());
			doctorNameComboBox.setSelectedItem(selectedDoctor.getName());
			try {
				Date date = new SimpleDateFormat("dd LLLL yyyy").parse(appointment.getAppointmentDate());
				appointmentDateChooser.setDate(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Date time = new SimpleDateFormat("hh:mm a").parse(appointment.getAppointmentTime());
				appointmentTimeSpinner.setValue(time);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Disable MRI ID field and search button when rescheduling
			mriIdTextField.setEnabled(false);
			searchButton.setEnabled(false);
		}
		doctorNameComboBox.addActionListener((e) -> {
			String department = (String) departmentComboBox.getSelectedItem();
			String specialization = (String) specializationComboBox.getSelectedItem();
			String doctorName = (String) doctorNameComboBox.getSelectedItem();
			for (Doctor doctor : doctors) {
				if (doctor.getName().equals(doctorName) && doctor.getDepartment().equals(department)
						&& doctor.getSpecialization().equals(specialization)) {
					selectedDoctor = doctor;
					break;
				}
			}
		});
	}

	private void openScheduleAssistantWindow() {

		JDialog scheduleAssistantDialog = new JDialog(new JDialog(), "Schedule Assistant");
		scheduleAssistantDialog.setBackground(Color.WHITE);

		scheduleAssistantDialog.setSize(600, 500);
		scheduleAssistantDialog.setLayout(new BorderLayout());
		scheduleAssistantDialog.setLocationRelativeTo(this);

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(0, 153, 153));
		JLabel headerLabel = new JLabel("Schedule Assistant");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
		headerPanel.add(headerLabel);
		scheduleAssistantDialog.add(headerPanel, BorderLayout.NORTH);

		JPanel slotsPanel = new JPanel();
		slotsPanel.setBackground(Color.white);
		slotsPanel.setLayout(new GridLayout(0, 4, 10, 10));
		slotsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		timeSlots = AppointmentController.addScheduleList(selectedDoctor,
				appointmentDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).format(formatter));
		for (String slot : timeSlots) {
			JButton slotButton = new JButton(slot);
			slotButton.addActionListener(e -> {
				appointmentTimeSpinner.setValue(ParseTime(slot));
				scheduleAssistantDialog.dispose();
			});
			slotsPanel.add(slotButton);
		}
		scheduleAssistantDialog.add(slotsPanel, BorderLayout.CENTER);

		JPanel closePanel = new JPanel();
		closePanel.setBackground(new Color(0, 150, 139));

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(e -> scheduleAssistantDialog.dispose());
		closeButton.setBackground(new Color(0, 150, 139));

		closePanel.add(closeButton);
		scheduleAssistantDialog.add(closePanel, BorderLayout.SOUTH);
		scheduleAssistantDialog.setVisible(true);

	}

	private Date ParseTime(String time) {
		try {
			return new SimpleDateFormat("hh:mm a").parse(time);
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}
	}

	private void searchPatient() {
		try {
			int mrdID = Integer.parseInt(mriIdTextField.getText());
			for (Patient patient : patients) {
				if (patient.getMrdID() == mrdID) {
					patientNameField.setText(patient.getFirstName());
					patientPhoneField.setText(String.valueOf(patient.getPhoneNumber()));
					patientEmailField.setText(patient.getEmail());
					selectedPatient = patient;
					break;
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid MRD ID", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateSpecializationComboBox() {
		if (departmentComboBox.getSelectedItem() != null) {
			specializationArray.clear();
			specializationComboBox.removeAllItems();
			for (Doctor doctor : doctors) {
				if (doctor.getDepartment().equals(departmentComboBox.getSelectedItem())) {
					specializationArray.add(doctor.getSpecialization());
				}
			}
			for (String specialization : specializationArray) {
				specializationComboBox.addItem(specialization);
			}
		}
	}

	private void updateDoctorComboBox() {
		if (specializationComboBox.getSelectedItem() != null) {
			doctorsSet.clear();
			doctorNameComboBox.removeAllItems();
			for (Doctor doctor : doctors) {
				if (doctor.getSpecialization().equals(specializationComboBox.getSelectedItem())) {
					doctorsSet.add(doctor.getName());
				}
			}
			for (String doctorName : doctorsSet) {
				doctorNameComboBox.addItem(doctorName);
			}
		}
	}

	public void submit() {
		try {
			String mriId = mriIdTextField.getText();
			String patientName = patientNameField.getText();
			String patientPhone = patientPhoneField.getText();
			String patientEmail = patientEmailField.getText();
			String department = (String) departmentComboBox.getSelectedItem();
			String specialization = (String) specializationComboBox.getSelectedItem();
			String doctorName = (String) doctorNameComboBox.getSelectedItem();
			Date appointmentDate = appointmentDateChooser.getDate();
			Date appointmentTime = (Date) appointmentTimeSpinner.getValue();

			for (Doctor doctor : doctors) {
				if (doctor.getName().equals(doctorName) && doctor.getDepartment().equals(department)
						&& doctor.getSpecialization().equals(specialization)) {
					selectedDoctor = doctor;
					break;
				}
			}

			if (selectedPatient == null || selectedDoctor == null || department.isEmpty() || specialization.isEmpty()
					|| doctorName.isEmpty() || appointmentDate == null || appointmentTime == null) {
				JOptionPane.showMessageDialog(this, "Please fill all the required fields", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			LocalDate localDate = appointmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			String time = new SimpleDateFormat("hh:mm a").format(appointmentTime);
			appointment = new Appointment(selectedPatient, selectedDoctor, localDate.format(formatter), time);
			// System.out.println(appointment);

			AppointmentController.writeDoctorSchedule();
			AppointmentController.addScheduleList(selectedDoctor, localDate.format(formatter));
			AppointmentController.saveAppointments();
			AppointmentController.removeScheduleList(selectedDoctor, localDate.format(formatter), time);
			AppointmentController.writeDoctorSchedule();
			AppointmentController.getAppointments().remove(appointment);
			AppointmentController.getAppointments().add(appointment);
			AppointmentController.saveAppointments();
			JOptionPane.showMessageDialog(this, "Appointment Confirmed!", "Confirm",
					JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
			try {
				cancel();
			} catch (Exception e) {
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancel() {
		mriIdTextField.setText("");
		patientNameField.setText("");
		patientPhoneField.setText("");
		patientEmailField.setText("");
		departmentComboBox.setSelectedItem(null);
		specializationComboBox.setSelectedItem(null);
		doctorNameComboBox.setSelectedItem(null);
		appointmentDateChooser.setDate(null);
		appointmentTimeSpinner.setValue(null);
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public JTextField getMriIdTextField() {
		return mriIdTextField;
	}

	public JButton getSearchButton() {
		return searchButton;
	}



	
}
