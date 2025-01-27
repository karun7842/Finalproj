
package com.appointment.his.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.appointment.his.controller.AppointmentController;
import com.appointment.his.model.Doctor;

public class AppointmentView extends JPanel {
	private JTable appointmentTable;
	private DefaultTableModel tableModel;
	private JButton viewButton, scheduleButton, rescheduleButton, cancelButton, resetButton;
	private JTextField filterMrdField;
	private JTextField filterDoctorField, filterSpecialityField;

	public AppointmentView(boolean isReschedule) {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		JLabel titleLabel = new JLabel("Appointment Scheduling System", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		add(titleLabel, BorderLayout.NORTH);

		setupTable();
		setupFilters();
		setupButtons(isReschedule);
	}

	private void setupTable() {
		String[] columns = { "Patient Name", "MRD ID", "Contact Info", "Doctor Name", "Consultation fee", "Department",
				"Speciality", "Appointment Date", "Appointment Time", "Status" };
		tableModel = new DefaultTableModel(columns, 0);
		appointmentTable = new JTable(tableModel);
		JScrollPane tableScrollPane = new JScrollPane(appointmentTable);

		appointmentTable.setOpaque(false);
		tableScrollPane.getViewport().setBackground(Color.WHITE);
		add(tableScrollPane, BorderLayout.CENTER);
	}

	private void setupFilters() {
		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filterPanel.setBackground(new Color(0, 150, 139));

		filterMrdField = new JTextField(10);
		filterMrdField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					applyFilters();
				}
			}
		});

		List<String> doctors = new ArrayList<>();
		for (Doctor doctor : AppointmentController.loadDoctors()) {
			if (!doctors.contains(doctor.getName())) {
				doctors.add(doctor.getName());
			}
		}
		filterDoctorField = new JTextField(10);
		AutoCompleteDecorator.decorate(filterDoctorField, doctors, false);

		List<String> specialities = new ArrayList<>();
		for (Doctor doctor : AppointmentController.loadDoctors()) {
			if (!specialities.contains(doctor.getSpecialization())) {
				specialities.add(doctor.getSpecialization());
			}
		}
		filterSpecialityField = new JTextField(10);
		AutoCompleteDecorator.decorate(filterSpecialityField, specialities, false);

		filterPanel.add(new JLabel("MRD Number:") {
			{
				setForeground(Color.white);
			}
		});
		filterPanel.add(filterMrdField);
		filterPanel.add(new JLabel("Doctor:") {
			{
				setForeground(Color.white);
			}
		});
		filterPanel.add(filterDoctorField);
		filterPanel.add(new JLabel("Speciality:") {
			{
				setForeground(Color.white);
			}
		});
		filterPanel.add(filterSpecialityField);

		add(filterPanel, BorderLayout.NORTH);
	}

	private void setupButtons(boolean isReschedule) {
		JPanel buttonPanel = new JPanel();

		scheduleButton = new JButton("Add appointment");
		scheduleButton.setBackground(new Color(0, 150, 139));
		scheduleButton.setForeground(Color.WHITE);

		rescheduleButton = new JButton("Reschedule");
		rescheduleButton.setBackground(new Color(0, 150, 139));
		rescheduleButton.setForeground(Color.WHITE);

		cancelButton = new JButton("Cancel");
		cancelButton.setBackground(new Color(0, 150, 139));
		cancelButton.setForeground(Color.WHITE);

		resetButton = new JButton("Reset");
		resetButton.setBackground(new Color(0, 150, 139));
		resetButton.setForeground(Color.WHITE);

		buttonPanel.setBackground(new Color(0, 153, 139));

		if (!isReschedule)
			buttonPanel.add(scheduleButton);
		else
			buttonPanel.add(rescheduleButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(resetButton);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public JTable getAppointmentTable() {
		return appointmentTable;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public JButton getViewButton() {
		return viewButton;
	}

	public JButton getScheduleButton() {
		return scheduleButton;
	}

	public JButton getRescheduleButton() {
		return rescheduleButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public JTextField getFilterMrdField() {
		return filterMrdField;
	}

	public JTextField getFilterDoctorField() {
		return filterDoctorField;
	}

	public JTextField getFilterSpecialityField() {
		return filterSpecialityField;
	}

	private void applyFilters() {

	}
}
