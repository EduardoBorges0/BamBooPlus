package com.app.bamboo.appointments

import com.app.bamboo.data.models.appointments.AppointmentEntities
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object FakeAppointments {
    val fakeData = listOf(
        AppointmentEntities(
            1,
            appointmentType = "Consulta A",
            hospitalName = "Caxias DOr",
            doctorName = "Eduardo Borges",
            onlineOrOnSite = "Online",
            appointmentDate = "10/02/2025",
            appointmentTime = "16:30",
            accomplish = true
        ),
        AppointmentEntities(
            2,
            appointmentType = "Consulta B",
            hospitalName = "Hospital Albert Einstein",
            doctorName = "Alex Pacheco",
            onlineOrOnSite = "On site",
            appointmentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            appointmentTime = LocalTime.now().minusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm")),
            accomplish = false
        ),
        AppointmentEntities(
            3,
            appointmentType = "Consulta C",
            hospitalName = "Hospital Albert Einstein",
            doctorName = "Doctor Slamp",
            onlineOrOnSite = "On site",
            appointmentDate = "10/06/2025",
            appointmentTime = "12:30",
            accomplish = null
        )
    )
}