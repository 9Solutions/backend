package CRUD.solutionsbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/caixas")

public class CaixaController {
    List<Caixa> caixas = new ArrayList<>();

    // CADASTRO
    @PostMapping("/cadastrar")
    public ResponseEntity<Caixa> cadastrar(@RequestBody Caixa caixa) {
        if(caixa!= null){
            caixas.add(caixa);
            return ResponseEntity.status(200).body(caixa);
        }
        return ResponseEntity.status(404).build();
    }


    // MÉTODO GET ->  READ
    @GetMapping
    public ResponseEntity<List<Caixa>> GetCaixas(){
        if(caixas.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(caixas);
    }

    // MÉTODO  PUT ->  UPDATE
    @PutMapping("/{indice}")
    public ResponseEntity<Caixa> PutCaixas(@RequestBody Caixa caixa,@PathVariable int indice){
        if(existeCaixa(indice)){
            caixas.set(indice,caixa);
            return ResponseEntity.status(200).body(caixas.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // MÉTODO DELETE -> DELETE
    @DeleteMapping("/{indice}")
    public ResponseEntity<Caixa> DeleteCaixa(@PathVariable int indice){
        if(existeCaixa(indice)){
            caixas.remove(indice);
        }
        return ResponseEntity.status(204).build();
    }

    public boolean existeCaixa(int indice){
        return caixas.size() > indice && indice >= 0;
    }


}
