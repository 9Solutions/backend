package CRUD.solutionsbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    public List<Usuario> usuarios = new ArrayList<>();

    // CADASTRO
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        if(contemArroba(usuario.getEmail())){
            return ResponseEntity.status(400).build();
        }

        if(emailExiste(usuario.getEmail())){
            return ResponseEntity.status(409).build();
        }

            usuario.setEmail(usuario.getEmail());
            usuario.setSenha(usuario.getSenha());
            usuario.setTelefone(usuario.getTelefone());
            usuario.setNomeUser(usuario.getNomeUser());

            usuarios.add(usuario);

        return ResponseEntity.status(200).body(usuario);
    }


    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String email, @RequestParam String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email) && usuario.getSenha().equals(senha)) {
                return ResponseEntity.status(200).body(usuario);
            }
        }
        return ResponseEntity.status(403).build();
    }

    // MÉTODO GET ->  READ
    @GetMapping
    public ResponseEntity<List<Usuario>> Getusuarios(){
        if(usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    // MÉTODO  PUT ->  UPDATE
    @PutMapping("/{indice}")
    public ResponseEntity<Usuario> PutUsuarios(@RequestBody Usuario usuario,@PathVariable int indice){
        if(existeUsuario(indice)){
            usuarios.set(indice,usuario);
            return ResponseEntity.status(200).body(usuarios.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // MÉTODO DELETE -> DELETE
    @DeleteMapping("/{indice}")
    public ResponseEntity<Usuario> DeleteUsuarios(@PathVariable int indice){
        if(existeUsuario(indice)){
            usuarios.remove(indice);
        }
        return ResponseEntity.status(204).build();
    }

    public boolean existeUsuario(int indice){
        return usuarios.size() > indice && indice >= 0;
    }

    public Boolean emailExiste (String email){
        for (Usuario usuario: usuarios){
            if(usuario.getEmail().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }

    public Boolean contemArroba (String email){
        for (Usuario usuario: usuarios){
            if(usuario.getEmail().contains("@")){
                return true;
            }
        }
        return false;
    }


}
