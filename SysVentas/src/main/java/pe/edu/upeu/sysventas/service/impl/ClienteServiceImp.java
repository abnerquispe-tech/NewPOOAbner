package pe.edu.upeu.sysventas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.Cliente;
import pe.edu.upeu.sysventas.repository.ClienteRepository;
import pe.edu.upeu.sysventas.repository.ICrudGenericRepository;
import pe.edu.upeu.sysventas.service.IClienteService;

@RequiredArgsConstructor
@Service
public class ClienteServiceImp extends CrudGenericServiceImp<Cliente,String> implements IClienteService {
    private final ClienteRepository clienteRepository;

    @Override
    protected ICrudGenericRepository<Cliente, String> getRepo() {
        return clienteRepository;
    }
}
