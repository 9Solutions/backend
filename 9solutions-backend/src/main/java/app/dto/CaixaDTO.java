package app.dto;

import app.enums.OpcoesDoacao;
import app.model.Item;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class CaixaDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_caixa;
    @NotNull(message = "O campo fkUsuario não pode ser nulo")
    private Integer fkUsuario;

    @NotNull(message = "O campo valor não pode ser nulo")
    @PositiveOrZero(message = "O valor deve ser maior ou igual a zero")
    private Double valor;

    @NotNull(message = "O campo escolhaGenero não pode ser nulo")
    private OpcoesDoacao.Genero escolhaGenero;

    @NotNull(message = "O campo escolhaIdade não pode ser nulo")
    private OpcoesDoacao.Idade escolhaIdade;

    @NotNull(message = "O campo escolhaBrinquedo não pode ser nulo")
    private OpcoesDoacao.Brinquedo escolhaBrinquedo;

    @NotNull(message = "O campo escolhaMaterial não pode ser nulo")
    private OpcoesDoacao.MaterialEscolar escolhaMaterial;

    @NotNull(message = "O campo escolhaHigiene não pode ser nulo")
    private OpcoesDoacao.Higiene escolhaHigiene;

    @NotBlank(message = "O campo foto não pode estar em branco")
    private String foto;

    @NotBlank(message = "O campo cartinha não pode estar em branco")
    @Size(max = 1000, message = "O campo cartinha deve ter no máximo 1000 caracteres")
    private String cartinha;

    public int getId_caixa() {
        return id_caixa;
    }

    public void setId_caixa(int id_caixa) {
        this.id_caixa = id_caixa;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public OpcoesDoacao.Genero getEscolhaGenero() {
        return escolhaGenero;
    }

    public void setEscolhaGenero(OpcoesDoacao.Genero escolhaGenero) {
        this.escolhaGenero = escolhaGenero;
    }

    public OpcoesDoacao.Idade getEscolhaIdade() {
        return escolhaIdade;
    }

    public void setEscolhaIdade(OpcoesDoacao.Idade escolhaIdade) {
        this.escolhaIdade = escolhaIdade;
    }

    public OpcoesDoacao.Brinquedo getEscolhaBrinquedo() {
        return escolhaBrinquedo;
    }

    public void setEscolhaBrinquedo(OpcoesDoacao.Brinquedo escolhaBrinquedo) {
        this.escolhaBrinquedo = escolhaBrinquedo;
    }

    public OpcoesDoacao.MaterialEscolar getEscolhaMaterial() {
        return escolhaMaterial;
    }

    public void setEscolhaMaterial(OpcoesDoacao.MaterialEscolar escolhaMaterial) {
        this.escolhaMaterial = escolhaMaterial;
    }

    public OpcoesDoacao.Higiene getEscolhaHigiene() {
        return escolhaHigiene;
    }

    public void setEscolhaHigiene(OpcoesDoacao.Higiene escolhaHigiene) {
        this.escolhaHigiene = escolhaHigiene;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCartinha() {
        return cartinha;
    }

    public void setCartinha(String cartinha) {
        this.cartinha = cartinha;
    }

    public void adicionarItemCaixa(Item item) {
    }

    // Getters e Setters
}
