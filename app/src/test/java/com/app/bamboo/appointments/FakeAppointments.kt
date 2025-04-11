package com.app.bamboo.appointments

import com.app.bamboo.data.models.AppointmentEntities

object FakeAppointments {
    val fakeData = listOf(
        AppointmentEntities(
            1,
            appointmentType = "Consulta A",
            hospitalName = "Caxias DOr",
            doctorName = "Eduardo Borges",
            onlineOrOnSite = "Online",
            appointmentDate = "10/12/2025",
            appointmentTime = "16:30",
            accomplish = true
        ),
        AppointmentEntities(
            2,
            appointmentType = "Consulta B",
            hospitalName = "Hospital Albert Einstein",
            doctorName = "Alex Pacheco",
            onlineOrOnSite = "On site",
            appointmentDate = "20/06/2025",
            appointmentTime = "12:30",
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