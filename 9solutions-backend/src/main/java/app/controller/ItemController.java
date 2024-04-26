package app.controller;

import app.dto.ItemDTO;
import app.model.Caixa;
import app.model.Item;
import app.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/i tems")
public class ItemController {
    List<ItemDTO> itens = new ArrayList<>();


    // Criando item
    @PostMapping
    public ResponseEntity<ItemDTO> cadastrarItem(@RequestBody ItemDTO item){
        if(item==null){
            return ResponseEntity.status(400).build();
        }
        itens.add(item);
        return ResponseEntity.status(200).body(item);
    }


    // Deletando item
    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> deletarItem(@PathVariable Integer indice){
        if(indice != null){
            itens.remove(indice);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }



}
