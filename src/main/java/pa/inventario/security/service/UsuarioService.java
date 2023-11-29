package pa.inventario.security.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pa.inventario.security.dto.JwtDto;
import pa.inventario.security.dto.LoginUsuario;
import pa.inventario.security.dto.Mensaje;
import pa.inventario.security.dto.NuevoUsuario;
import pa.inventario.security.entity.Rol;
import pa.inventario.security.entity.Usuario;
import pa.inventario.security.enums.RolNombre;
import pa.inventario.security.jwt.JwtProvider;
import pa.inventario.security.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    public boolean existByNombreUsuario(String nombreUsuario){

        return usuarioRepository.existByNombreUsuario(nombreUsuario);
    }
    public boolean existByEmail(String email){

        return usuarioRepository.existByEmail(email);
    }
    public JwtDto login(LoginUsuario loginUsuario){  Authentication authentication =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(),loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());}
    public Mensaje save(NuevoUsuario nuevoUsuario){

        if (usuarioRepository.existByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new Mensaje("nombre usuario"+getByNombreUsuario(nuevoUsuario.getNombreUsuario()));
        if (usuarioRepository.existByEmail(nuevoUsuario.getEmail()))
            return new Mensaje("Email"+existByEmail(nuevoUsuario.getEmail()));
        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), passwordEncoder.encode((nuevoUsuario.getPassword())));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if (nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
        return new Mensaje(usuario.getNombreUsuario()+ "ha sido creado");


    }

}
