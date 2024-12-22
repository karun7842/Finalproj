package com.appointment.his.model;

import java.util.List;
import java.util.Objects;

import com.appointment.his.controller.AppointmentController;

public class DoctorSchedule {
	private Doctor doctor;
	private String date;
	private List<String> availableSlots;

	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<String> getAvailableSlots() {
		return availableSlots;
	}
	public void setAvailableSlots(List<String> availableSlots) {
		this.availableSlots = availableSlots;
	}
	public DoctorSchedule(Doctor doctor, String date) {
		super();
		this.doctor = doctor;
		this.date = date;
		availableSlots = AppointmentController.generateTimeSlots(Integer.parseInt(doctor.getStartTime().substring(0, 2)),Integer.parseInt(doctor.getEndTime().substring(0, 2)));

	}
	@Override
	public int hashCode() {
		return Objects.hash(date, doctor);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoctorSchedule other = (DoctorSchedule) obj;
		return Objects.equals(date, other.date) && Objects.equals(doctor, other.doctor);
	}
	public DoctorSchedule() {
		super();

	}
	@Override
	public String toString() {
		return "DoctorSchedule [doctor=" + doctor + ", date=" + date + ", availableSlots=" + availableSlots + "]";
	}



}
