package pe.edu.upeu.sysventas.service.impl;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.sysventas.model.Marca;
import pe.edu.upeu.sysventas.repository.ICrudGenericRepository;
import pe.edu.upeu.sysventas.repository.MarcaRepository;
import pe.edu.upeu.sysventas.service.IMarcaService;

@RequiredArgsConstructor
public class MarcaServiceImp extends CrudGenericServiceImp<Marca, Long> implements IMarcaService {
    private final MarcaRepository marcaRepository;

    @Override
    protected ICrudGenericRepository<Marca, Long> getRepo() {
        return marcaRepository;
    }
}
