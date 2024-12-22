package com.appointment.his.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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

import com.appointment.his.controller.AppointmentController;
import com.appointment.his.model.Appointment;
import com.appointment.his.model.Doctor;
import com.appointment.his.model.Patient;
import com.toedter.calendar.JDateChooser;

public class AppointmentFormDialog extends JDialog {
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
	JPanel mainPanel;


	public AppointmentFormDialog(Appointment appointment) {
		setTitle("Add Appointment");
		setModal(true);
		setSize(700, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBackground(Color.WHITE);

		setLocationRelativeTo(null);

		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(new BorderLayout(10, 10));


		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(0, 153, 153));
		JLabel headerLabel = new JLabel("Reschedule Appointment");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
		headerPanel.add(headerLabel);
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		JPanel formPanel = new JPanel(new GridLayout(12, 2, 10, 10));
		formPanel.setBackground(Color.WHITE);

		mainPanel.add(formPanel, BorderLayout.CENTER);

		patients = AppointmentController.loadPatients();
		doctors = AppointmentController.loadDoctors();


		formPanel.add(new JLabel("MRD Number:"));
		JPanel mriIdPanel = new JPanel(new BorderLayout());
		mriIdTextField = new JTextField();
		mriIdPanel.add(mriIdTextField, BorderLayout.CENTER);
		searchButton = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\2021617\\Downloads\\image2.png").getImage()
				.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		searchButton.setPreferredSize(new java.awt.Dimension(25, 25));
		searchButton.addActionListener(e -> searchPatient());
		mriIdPanel.add(searchButton, BorderLayout.EAST);
		formPanel.add(mriIdPanel);


		formPanel.add(new JLabel("Patient Name:"));
		patientNameField = new JTextField();
		patientNameField.setEditable(false);

		formPanel.add(patientNameField);

		formPanel.add(new JLabel("Patient Phone:"));
		patientPhoneField = new JTextField();
		patientPhoneField.setEditable(false);
		formPanel.add(patientPhoneField);

		formPanel.add(new JLabel("Patient Email:"));
		patientEmailField = new JTextField();
		patientEmailField.setEditable(false);
		formPanel.add(patientEmailField);

		formPanel.add(new JSeparator());
		formPanel.add(new JLabel());


		formPanel.add(new JLabel("Department:"));
		for (Doctor doctor : doctors) {
			specializationArray.add(doctor.getDepartment());

		}
		departmentComboBox = new JComboBox<>(specializationArray.toArray(new String[0]));
		formPanel.add(departmentComboBox);

		formPanel.add(new JLabel("Specialization:"));
		specializationComboBox = new JComboBox<>();
		formPanel.add(specializationComboBox);

		formPanel.add(new JLabel("Doctor Name:"));
		doctorNameComboBox = new JComboBox<>();
		formPanel.add(doctorNameComboBox);


		formPanel.add(new JLabel("Appointment Date:"));
		appointmentDateChooser = new JDateChooser();
		formPanel.add(appointmentDateChooser);

		formPanel.add(new JLabel("Appointment Time:"));
		appointmentTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(appointmentTimeSpinner, "hh:mm a");
		appointmentTimeSpinner.setEditor(timeEditor);
		appointmentTimeSpinner.setValue(new Date());
		formPanel.add(appointmentTimeSpinner);
		formPanel.add(new JLabel());


		buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		scheduleAssistantButton = new JButton("Schedule Assistant");
		scheduleAssistantButton.addActionListener(e -> openScheduleAssistantWindow());
		scheduleAssistantButton.setBackground(new Color(0, 150, 139));
		scheduleAssistantButton.setForeground(Color.WHITE);
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setBackground(Color.white);
		centerPanel.add(scheduleAssistantButton);


		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		secondPanel.setBackground(Color.white);

		submitButton = new JButton("Submit");
		submitButton.setBackground(new Color(0, 150, 139));
		submitButton.setForeground(Color.WHITE);
		cancelButton = new JButton("Cancel");
		cancelButton.setBackground(new Color(0, 150, 139));
		cancelButton.setForeground(Color.WHITE);

		secondPanel.add(submitButton);
		secondPanel.add(cancelButton);

		buttonPanel.add(centerPanel);
		formPanel.add(new JSeparator());
		buttonPanel.add(secondPanel);
		buttonPanel.setBackground(Color.WHITE);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		add(mainPanel);

		submitButton.addActionListener(e -> submit());
		cancelButton.addActionListener(e -> cancel());

		departmentComboBox.addActionListener(e -> updateSpecializationComboBox());
		specializationComboBox.addActionListener(e -> updateDoctorComboBox());


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

			mriIdTextField.setEnabled(false);
			searchButton.setEnabled(false);
		}
		doctorNameComboBox.addActionListener((e)->{
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

		JDialog scheduleAssistantDialog = new JDialog(this, "Schedule Assistant", true);
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
		slotsPanel.setBackground(Color.WHITE);
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
		closePanel.setBackground(new Color(0,150,139));



		JButton closeButton = new JButton("Close");
		closePanel.setBackground(new Color(0,150,139));
		closeButton.addActionListener(e -> scheduleAssistantDialog.dispose());
		closeButton.setBackground(new Color(9,150,139));
		closeButton.setForeground(Color.white);

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

			AppointmentController.addScheduleList(selectedDoctor,localDate.format(formatter));
			AppointmentController.removeScheduleList(selectedDoctor, localDate.format(formatter),time);
			//AppointmentController.getAppointments().add(appointment);
			AppointmentController.saveAppointments();
			AppointmentController.writeDoctorSchedule();
			dispose();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancel() {
		appointment = null;
		dispose();
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
