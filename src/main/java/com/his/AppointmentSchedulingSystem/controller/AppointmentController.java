package com.appointment.his.controller;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.appointment.his.model.Appointment;
import com.appointment.his.model.Doctor;
import com.appointment.his.model.DoctorSchedule;
import com.appointment.his.model.Patient;
import com.appointment.his.model.Status;
import com.appointment.his.view.AppointmentFormDialog;
import com.appointment.his.view.AppointmentFormPanel;
import com.appointment.his.view.AppointmentView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class AppointmentController {
	private static List<Appointment> appointments;
	public static List<Appointment> getAppointments() {
		return appointments;
	}

	public static HashSet<DoctorSchedule> getDoctorSchedules() {
		return doctorSchedules;
	}
	private AppointmentView view;

	private List<Appointment> filteredAppointments;
	static String filePath = "appointments.json";
	int mrdFilter = 0;
	public static HashSet<DoctorSchedule> doctorSchedules;
	static ObjectMapper mapper = new ObjectMapper();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
	static DoctorSchedule doctorSchedule;
	static {
		mapper.registerModule(new JavaTimeModule());
		loadAppointments();
	}

	public AppointmentController(AppointmentView view) {
		this.view = view;

		loadAppointments();

		view.getScheduleButton().addActionListener(e -> scheduleAppointment());
		view.getRescheduleButton().addActionListener(e -> rescheduleAppointment());
		view.getCancelButton().addActionListener(e -> cancelAppointment());
		view.getResetButton().addActionListener(e -> resetFilters());

		view.getFilterMrdField().addActionListener(e -> applyFilters());
		view.getFilterDoctorField().addActionListener(e -> applyFilters());
		view.getFilterSpecialityField().addActionListener(e -> applyFilters());

	}

	public void viewAppointments() {
		loadAppointments();
		updateTable(appointments);
	}

	private static void loadAppointments() {
		File file = new File(filePath);
		if (file.exists()) {
			try {
				appointments = mapper.readValue(file, new TypeReference<List<Appointment>>() {
				});
			} catch (IOException e) {
				appointments = new ArrayList<Appointment>();
			}
		}
	}

	public static void saveAppointments() {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), appointments);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateTable(List<Appointment> listToDisplay) {
		DefaultTableModel model = view.getTableModel();
		model.setRowCount(0);

		for (Appointment a : listToDisplay) {
			Doctor doctor = a.getDoctor();
			String doctorName = (doctor != null) ? doctor.getName() : "Unknown";
			double consultationFee = (doctor != null) ? doctor.getConsultationFee() : 0.0;
			String department = (doctor != null) ? doctor.getDepartment() : "Unknown";
			String specialization = (doctor != null) ? doctor.getSpecialization() : "Unknown";

			model.addRow(new Object[]{
					a.getPatient().getFirstName(),
					a.getPatient().getMrdID(),
					a.getPatient().getPhoneNumber(),
					doctorName,
					consultationFee,
					department,
					specialization,
					a.getAppointmentDate(),
					a.getAppointmentTime(),
					a.getStatus()
			});
		}
		model.fireTableDataChanged();
	}


	private void applyFilters() {
		if (!view.getFilterMrdField().getText().isEmpty()) {
			mrdFilter = Integer.parseInt(view.getFilterMrdField().getText());
		}

		String doctorFilter = view.getFilterDoctorField().getText();
		String specialityFilter = view.getFilterSpecialityField().getText();

		filteredAppointments = appointments.stream()
				.filter(a -> (mrdFilter == 0 || a.getPatient().getMrdID() == mrdFilter)
						&& (doctorFilter.isEmpty() || a.getDoctor().getName().equalsIgnoreCase(doctorFilter))
						&& (specialityFilter.isEmpty()
								|| a.getDoctor().getSpecialization().equalsIgnoreCase(specialityFilter)))
				.collect(Collectors.toList());

		updateTable(filteredAppointments);
	}

	private void resetFilters() {
		view.getFilterMrdField().setText("");
		view.getFilterDoctorField().setText("");
		view.getFilterSpecialityField().setText("");
		updateTable(appointments);
	}

	public void scheduleAppointment() {
		AppointmentFormDialog formDialog = new AppointmentFormDialog(null);
		formDialog.setVisible(true);



		Appointment newAppointment = formDialog.getAppointment();
		if (newAppointment != null) {
			appointments.add(newAppointment);
			for (DoctorSchedule doctorScheduleTemp : doctorSchedules) {
				if(doctorScheduleTemp.equals(doctorSchedule)) {
					doctorScheduleTemp.getAvailableSlots().remove(newAppointment.getAppointmentTime());
				}
			}
			saveAppointments();
			updateTable(appointments);

		}
	}

	private void rescheduleAppointment() {
		int selectedRow = view.getAppointmentTable().getSelectedRow();
		if (selectedRow != -1) {
			Appointment appointmentToReschedule = appointments.get(selectedRow);
			AppointmentFormDialog formDialog = new AppointmentFormDialog(appointmentToReschedule);
			formDialog.getMriIdTextField().setEnabled(false);
			formDialog.getSearchButton().setEnabled(false);
			formDialog.setVisible(true);
//			AppointmentFormPanel.submitButton.setVisible(false);
//			AppointmentFormPanel.rescheduleSubmit.setVisible(true);
			Appointment rescheduledAppointment = formDialog.getAppointment();
			if (rescheduledAppointment != null) {
				appointments.set(selectedRow, rescheduledAppointment);
				saveAppointments();
				updateTable(appointments);
			}
		} else {
			JOptionPane.showMessageDialog(view.getAppointmentTable(), "Please select an appointment to reschedule.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	private void cancelAppointment() {
		int selectedRow = view.getAppointmentTable().getSelectedRow();
		if (selectedRow != -1) {
			Appointment appointment = appointments.get(selectedRow);
			appointment.setStatus(Status.CANCELED);
			saveAppointments();
			updateTable(appointments);
		}
	}

	public static ArrayList<Patient> loadPatients() {
		File file = new File("patientsData.json");
		if (file.exists()) {
			try {
				return mapper.readValue(file, new TypeReference<ArrayList<Patient>>() {
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<Patient>();
	}

	public static ArrayList<Doctor> loadDoctors() {
		File file = new File("DoctorsProfile.json");
		if (file.exists()) {
			try {
				return mapper.readValue(file, new TypeReference<ArrayList<Doctor>>() {
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<Doctor>();
	}
	public static  ArrayList<String> generateTimeSlots(int startHour,int endHour){
		int interval=15;
		ArrayList<String> slots = new ArrayList<String>();
		for(int hour=startHour;hour<=endHour;hour++) {
			for(int minute=0;minute<60;minute+=interval) {
				String time=String.format("%02d:%02d %s",(hour>12?hour-12:hour),minute,(hour>=12?"PM":"AM"));
				slots.add(time);
			}
		}
		return slots;
	}

	public static List<String> addScheduleList(Doctor doctor,String date){
		readDoctorSchedule();
		if(doctorSchedules==null) {
			doctorSchedules = new HashSet<DoctorSchedule>();
		}
		for (DoctorSchedule doctorScheduleTemp : doctorSchedules) {
			if(doctorScheduleTemp.getDoctor().equals(doctor)&&date.equals(doctorScheduleTemp.getDate())) {
				return doctorScheduleTemp.getAvailableSlots();
			}
		}

		doctorSchedule = new DoctorSchedule(doctor, date);
		doctorSchedules.add(doctorSchedule);
		return doctorSchedule.getAvailableSlots();
	}
	public static void removeScheduleList(Doctor doctor,String date, String time){
		for (DoctorSchedule doctorScheduleTemp : doctorSchedules) {
			if(doctorScheduleTemp.getDoctor()!=null&&doctorScheduleTemp.getDoctor().equals(doctor)&&doctorScheduleTemp.getDate().equals(date)) {
				doctorScheduleTemp.getAvailableSlots().remove(time);
			}
		}
	}
	public static void writeDoctorSchedule(){
		try {
			System.out.println(doctorSchedule);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("doctorSchedule.json"),doctorSchedules);

		} catch (IOException e) {

		}
	}
	public static void readDoctorSchedule() {
		try {
			doctorSchedules = mapper.readValue(new File("doctorSchedule.json"), new TypeReference<HashSet<DoctorSchedule>>() {});
			System.out.println("Readed");
		} catch (IOException e) {

			System.out.println(e.getMessage());
			doctorSchedules = new HashSet<DoctorSchedule>();
		}
	}

}
