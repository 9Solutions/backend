package com.caixadesapato.service.itemCaixa;

import com.caixadesapato.api.model.Caixa;
import com.caixadesapato.api.model.ItemCaixa;
import com.caixadesapato.api.model.Produto;
import com.caixadesapato.api.repository.ItemCaixaRepository;
import com.caixadesapato.api.service.ItemCaixaService;
import com.caixadesapato.api.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemCaixaServiceTest {

    @Mock
    private ItemCaixaRepository itemCaixaRepository;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ItemCaixaService itemCaixaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insertItems_DeveInserirItensCorretamente() {
        // Arrange
        Caixa caixa = new Caixa();
        int[] itemsCaixa = {1, 2, 3};

        List<ItemCaixa> expectedItems = new ArrayList<>();
        for (int idProduto : itemsCaixa) {
            Produto produto = new Produto();
            produto.setId(idProduto);

            ItemCaixa novoItem = new ItemCaixa();
            novoItem.setCaixa(caixa);
            novoItem.setProduto(produto);
            expectedItems.add(novoItem);

            when(produtoService.findById(idProduto)).thenReturn(produto);
        }

        when(itemCaixaRepository.saveAll(anyList())).thenReturn(expectedItems);

        // Act
        List<ItemCaixa> actualItems = itemCaixaService.insertItems(caixa, itemsCaixa);

        // Assert
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);

        ArgumentCaptor<List<ItemCaixa>> captor = ArgumentCaptor.forClass(List.class);
        verify(itemCaixaRepository).saveAll(captor.capture());
        List<ItemCaixa> savedItems = captor.getValue();

        assertEquals(expectedItems.size(), savedItems.size());
        for (int i = 0; i < savedItems.size(); i++) {
            assertEquals(expectedItems.get(i).getCaixa(), savedItems.get(i).getCaixa());
            assertEquals(expectedItems.get(i).getProduto(), savedItems.get(i).getProduto());
        }
    }

    @Test
    void insertItems_DeveChamarFindByIdParaCadaProduto() {
        // Arrange
        Caixa caixa = new Caixa();
        int[] itemsCaixa = {1, 2, 3};

        for (int idProduto : itemsCaixa) {
            Produto produto = new Produto();
            produto.setId(idProduto);

            when(produtoService.findById(idProduto)).thenReturn(produto);
        }

        // Act
        itemCaixaService.insertItems(caixa, itemsCaixa);

        // Assert
        for (int idProduto : itemsCaixa) {
            verify(produtoService).findById(idProduto);
        }
    }
}

