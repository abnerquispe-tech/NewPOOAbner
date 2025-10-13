package pe.edu.upeu.sysventas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.UnidadMedida;
import pe.edu.upeu.sysventas.repository.ICrudGenericRepository;
import pe.edu.upeu.sysventas.repository.UnidadMedidaRepository;
import pe.edu.upeu.sysventas.service.IUnidadMedidaService;

@RequiredArgsConstructor
@Service
public class UnidadMedidaServiceImp extends CrudGenericServiceImp<UnidadMedida, Long> implements IUnidadMedidaService {
    private final UnidadMedidaRepository unidadMedidaRepository;
    @Override
    protected ICrudGenericRepository<UnidadMedida, Long> getRepo() {
        return unidadMedidaRepository;
    }
}
