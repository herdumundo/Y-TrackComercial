package com.example.y_trackcomercial.model.models


data class HorariosUsuarioResponse(
    val id: Int,
    val idHorario: Int,
    val idUsuario: Int,
    val updatedAt: String?,
    val createdAt: String?,
    val yemsys_horarios: List<YemsysHorario>
)

data class YemsysHorario(
    val id: Int,
    val descripcion: String,
    val idEstado: Int,
    val updatedAt: String,
    val createdAt: String,
    val yemsys_turnos: List<YemsysTurno>
)

data class YemsysTurno(
    val id: Int,
    val idHorario: Int,
    val descripcion: String,
    val horaEnt: String,
    val horaSal: String,
    val tolEnt: Int,
    val tolSal: Int,
    val inicioEnt: String,
    val finEnt: String,
    val inicioSal: String,
    val finSal: String,
    val updatedAt: String,
    val createdAt: String
)
