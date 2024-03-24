package app.controller;

import app.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    public List<Usuario> usuarios = new ArrayList<>();

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        if(usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    // Listar dados do usuários por indice
    @GetMapping("/{indice}")
    public ResponseEntity<Usuario> buscarPorIndice(@PathVariable int indice) {
        if(existeUsuario(indice)){
            return ResponseEntity.status(200).body(usuarios.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // Listar os nomes, ordenando pelo nome
    @GetMapping("/order-by-nome")
    public ResponseEntity<List<Usuario>> orderByName(){
        if(!usuarios.isEmpty()){
            bubbleSort(usuarios);
            return ResponseEntity.status(200).body(usuarios);
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/search")
    public ResponseEntity<Usuario> binarySearchName(
            @RequestParam String nome
    ){
        bubbleSort(usuarios);
        int indice = binarySearch(usuarios, nome, 0, usuarios.size()-1);
         if(indice != -1){
             return ResponseEntity.status(200).body(usuarios.get(indice));
         }
        return ResponseEntity.status(404).build();
    }


    // Cadastrar usuario
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        if(!contemArroba(usuario.getEmail())){
            return ResponseEntity.status(400).build();
        }

        if(emailExiste(usuario.getEmail())){
            return ResponseEntity.status(409).build();
        }

        usuarios.add(usuario);
        return ResponseEntity.status(200).body(usuario);
    }

    // Login do usuario
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
        String emailLogin = usuario.getEmail();
        String senhaLogin = usuario.getSenha();

        for (Usuario userAtual : usuarios) {
            if( userAtual.getEmail().equalsIgnoreCase(emailLogin)
                && userAtual.getSenha().equalsIgnoreCase(senhaLogin)
            ) {
                return ResponseEntity.status(200).body(userAtual);
            }
        }
        return ResponseEntity.status(403).build();
    }

    // Atualizar usuario
    @PutMapping("/{indice}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable int indice,
            @RequestBody Usuario usuario
    ) {
        if(existeUsuario(indice)){
            usuarios.set(indice,usuario);
            return ResponseEntity.status(200).body(usuarios.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // Deltar usuario
    @DeleteMapping("/{indice}")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable int indice){
        if(existeUsuario(indice)){
            usuarios.remove(indice);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
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
        if(email.contains("@")){
            return true;
        }
        return false;
    }

    public static void bubbleSort(List<Usuario> usuarios) {
        for (int i = 0; i < usuarios.size() - 1; i++) {
            for (int j = 0; j < usuarios.size() - 1; j++) {

                String nome1 = usuarios.get(j).getNomeUser();
                String nome2 = usuarios.get(j + 1).getNomeUser();
                int comparacaoNome = nome1.compareTo(nome2);

                if(comparacaoNome > 0){
                    var usuarioMenor = usuarios.get(j + 1);
                    var usuarioMaior = usuarios.get(j);

                    usuarios.set(j+1, usuarioMaior);
                    usuarios.set(j, usuarioMenor);
                }

            }
        }
    }

    public static int binarySearch(List<Usuario> usuarios, String nome, int inicio, int fim) {

        int meio = (inicio + fim) / 2;

        int comparacaoNomes = usuarios.get(meio).getNomeUser().compareTo(nome);

        if(fim >= inicio) {

            if(comparacaoNomes == 0) {
                return meio;

            } else if(comparacaoNomes > 0) {
                return binarySearch(usuarios, nome, inicio, meio - 1);

            }
            return binarySearch(usuarios, nome, meio + 1, fim );

        }
        return -1;

    }



}
