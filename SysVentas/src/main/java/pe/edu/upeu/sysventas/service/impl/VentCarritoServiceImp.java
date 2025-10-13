package pe.edu.upeu.sysventas.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.VentCarrito;
import pe.edu.upeu.sysventas.repository.ICrudGenericRepository;
import pe.edu.upeu.sysventas.repository.VentCarritoRepository;
import pe.edu.upeu.sysventas.service.IVentCarritoService;
@RequiredArgsConstructor
@Service
public class VentCarritoServiceImp extends CrudGenericServiceImp<VentCarrito, Long> implements IVentCarritoService {

    private final VentCarritoRepository carritoRepository;

    @Override
    protected ICrudGenericRepository<VentCarrito, Long> getRepo() {
        return carritoRepository;
    }
}
