package pa.inventario.security.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.inventario.security.entity.Rol;
import pa.inventario.security.enums.RolNombre;
import pa.inventario.security.repository.RolRepository;

import java.util.Optional;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepository;
    public Optional<Rol> getByRolNombre (RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }
    public void  save (Rol rol){
        rolRepository.save(rol);
    }
}
