package app.controller;

import app.dto.CaixaDTO;
import app.model.Caixa;
import app.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

caixa{
    idBrinqueod

            @Quey("diajiodjasod jpsapofop k")
}

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    List<CaixaDTO> caixas = new ArrayList<>();

    // Listar todas as caixas
    @GetMapping
    public ResponseEntity<List<CaixaDTO>> listarCaixas(){
        if(caixas.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(caixas);
    }

    // Listar dados de uma caixa, buscando por indice
    @GetMapping("/{indice}")
    public ResponseEntity<CaixaDTO> buscarPorIndice(@PathVariable int indice) {
        if(existeCaixa(indice)) {
            return ResponseEntity.status(200).body(caixas.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // Ordena a lista pelo valor (do menor para o maior)
    @GetMapping("/order-by-valor")
    public ResponseEntity<List<CaixaDTO>> orderByValor() {
        if(!caixas.isEmpty()) {
            selectionSortOtimizado(caixas);
            return ResponseEntity.status(200).body(caixas);
        }
        return ResponseEntity.status(204).build();
    }

    // Busca uma caixa pelo seu valor
    @GetMapping("/search")
    public ResponseEntity<CaixaDTO> searchByValor(
            @RequestParam double valor
    ) {
        if(!caixas.isEmpty()) {

            selectionSortOtimizado(caixas);
            int indice = binarySearch(caixas, valor, 0, caixas.size()-1);
            return ResponseEntity.status(200).body(caixas.get(indice));

        }
        return ResponseEntity.status(404).build();
    }

    // Cadastrar uma caixa
    @PostMapping
    public ResponseEntity<CaixaDTO> cadastrarCaixa(@RequestBody CaixaDTO caixa) {
        if(!Objects.isNull(caixa)) {
            caixas.add(caixa);
            return ResponseEntity.status(200).body(caixa);
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/item/{indice}")
    public ResponseEntity<CaixaDTO> adicionarItemCaixa(@RequestBody Item item, @PathVariable int indice){
        if(caixaExiste(indice)){
            caixas.get(indice).adicionarItemCaixa(item);
            return ResponseEntity.status(200).body(caixas.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // Atualizar dados de uma caixa
    @PutMapping("/{indice}")
    public ResponseEntity<CaixaDTO> atualizarCaixa(
            @PathVariable int indice,
            @RequestBody CaixaDTO caixa
    ) {
        if(existeCaixa(indice)) {
            caixas.set(indice,caixa);
            return ResponseEntity.status(200).body(caixas.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    // Deletar uam caixa por indice
    @DeleteMapping("/{indice}")
    public ResponseEntity<CaixaDTO> deletarCaixa(@PathVariable int indice) {
        if(existeCaixa(indice)) {
            caixas.remove(indice);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    public boolean existeCaixa(int indice){
        return caixas.size() > indice && indice >= 0;
    }

    public static void selectionSortOtimizado(List<CaixaDTO> caixas) {

        Integer n = caixas.size();

        for (int i = 0; i < n - 1; i++) {

            int minimo = i;

            for (int j = i + 1; j < n; j++) {
                if (caixas.get(j).getValor() < caixas.get(minimo).getValor()) {
                    minimo = j;
                }
            }

            var caixa = caixas.get(minimo);
            caixas.set(minimo, caixas.get(i));
            caixas.set(i, caixa);

        }

    }

    public static int binarySearch(List<CaixaDTO> caixas, double valor, int inicio, int fim) {

        int meio = (inicio + fim) / 2;

        if(fim >= inicio) {

            if(caixas.get(meio).getValor() == valor) {
                return meio;

            } else if(caixas.get(meio).getValor() > valor) {
                return binarySearch(caixas, valor, inicio, meio - 1);

            }
            return binarySearch(caixas, valor, meio + 1, fim );

        }
        return -1;

    }


    public Boolean  caixaExiste(int indice){
        return indice >= 0 && indice < caixas.size();
    }

}
