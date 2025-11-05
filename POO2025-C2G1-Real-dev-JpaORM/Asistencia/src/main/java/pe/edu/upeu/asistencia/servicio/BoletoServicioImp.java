package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Boleto;
import pe.edu.upeu.asistencia.repositorio.BoletoRepository;

import java.util.List;

@Service
public class BoletoServicioImp implements ParticipanteServicioI {

    @Autowired
    private BoletoRepository boletoRepository;

    @Override
    public void save(Boleto boleto) {
        boletoRepository.save(boleto);
    }

    @Override
    public Boleto update(Boleto boleto) {
        return boletoRepository.save(boleto);
    }

    @Override
    public void delete(String dni) {
        boletoRepository.deleteById(dni);
    }

    @Override
    public Boleto findById(String dni) {
        return boletoRepository.findById(dni).orElse(null);
    }

    @Override
    public List<Boleto> findAll() {
        return boletoRepository.findAll();
    }
}
