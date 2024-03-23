package CRUD.solutionsbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    List<Caixa> caixas = new ArrayList<>();

    // Listar todas as caixas
    @GetMapping
    public ResponseEntity<List<Caixa>> listarCaixas(){
        if(caixas.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(caixas);
    }

    // Listar dados de uma caixa, buscando por indice
    @GetMapping("/{indice}")
    public ResponseEntity<Caixa> buscarPorIndice(@PathVariable int indice) {
        if(existeCaixa(indice)) {
            return ResponseEntity.status(200).body(caixas.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // Cadastrar uma caixa
    @PostMapping
    public ResponseEntity<Caixa> cadastrarCaixa(@RequestBody Caixa caixa) {
        if(!Objects.isNull(caixa)) {
            caixas.add(caixa);
            return ResponseEntity.status(200).body(caixa);
        }
        return ResponseEntity.status(404).build();
    }

    // Atualizar dados de uma caixa
    @PutMapping("/{indice}")
    public ResponseEntity<Caixa> atualizarCaixa(
            @PathVariable int indice,
            @RequestBody Caixa caixa
    ) {
        if(existeCaixa(indice)) {
            caixas.set(indice,caixa);
            return ResponseEntity.status(200).body(caixas.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // Deletar uam caixa por indice
    @DeleteMapping("/{indice}")
    public ResponseEntity<Caixa> deletarCaixa(@PathVariable int indice) {
        if(existeCaixa(indice)) {
            caixas.remove(indice);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    public boolean existeCaixa(int indice){
        return caixas.size() > indice && indice >= 0;
    }


}
