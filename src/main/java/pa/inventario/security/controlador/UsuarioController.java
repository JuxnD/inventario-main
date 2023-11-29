package pa.inventario.security.controlador;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pa.inventario.security.dto.JwtDto;
import pa.inventario.security.dto.LoginUsuario;
import pa.inventario.security.dto.Mensaje;
import pa.inventario.security.dto.NuevoUsuario;
import pa.inventario.security.entity.Admin;
import pa.inventario.security.entity.Rol;
import pa.inventario.security.entity.Usuario;
import pa.inventario.security.enums.RolNombre;
import pa.inventario.security.jwt.JwtProvider;
import pa.inventario.security.repository.UsuarioRepository;
import pa.inventario.security.service.RolService;
import pa.inventario.security.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;


    @Autowired
    UsuarioRepository usuarioRepository;



    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<Mensaje> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario) {
        return ResponseEntity.ok(usuarioService.save(nuevoUsuario));

    }


      @PostMapping("/login")
    public ResponseEntity<JwtDto> login (@Valid @RequestBody LoginUsuario loginUsuario){

        return ResponseEntity.ok(usuarioService.login(loginUsuario));
        }


}


