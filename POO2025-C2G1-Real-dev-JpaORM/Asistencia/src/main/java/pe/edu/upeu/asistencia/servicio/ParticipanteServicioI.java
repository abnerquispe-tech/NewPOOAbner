package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Boleto;

import java.util.List;

public interface ParticipanteServicioI {
    void save(Boleto participante); //C
    List<Boleto> findAll(); //R
    Boleto update(Boleto participante); //U
    void delete(String dni); //D

    Boleto findById(String dni); //B

}
