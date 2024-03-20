package CRUD.solutionsbackend;

import CRUD.solutionsbackend.enums.OpcoesDoacao;

public class Caixa {
    private Usuario usuario;
    private Double valor;

    private OpcoesDoacao.Genero escolhaGenero;
    private OpcoesDoacao.Idade escolhaIdade;
    private OpcoesDoacao.Brinquedo escolhaBrinquedo;
    private OpcoesDoacao.MaterialEscolar escolhaMaterial;
    private OpcoesDoacao.Higiene escolhaHigiene;
    private OpcoesDoacao.ItensDiversos[] itensDiversos = new OpcoesDoacao.ItensDiversos[3];
    private OpcoesDoacao.ItensUsoPessoal escolhaItensUso;
    private OpcoesDoacao.DocesChocolates[] itensDoces = new OpcoesDoacao.DocesChocolates[3];
    private String foto;
    private String cartinha;


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public OpcoesDoacao.ItensDiversos[] getItensDiversos() {
        return itensDiversos;
    }

    public void setItensDiversos(OpcoesDoacao.ItensDiversos[] itensDiversos) {
        this.itensDiversos = itensDiversos;
    }

    public OpcoesDoacao.ItensUsoPessoal getEscolhaItensUso() {
        return escolhaItensUso;
    }

    public void setEscolhaItensUso(OpcoesDoacao.ItensUsoPessoal escolhaItensUso) {
        this.escolhaItensUso = escolhaItensUso;
    }

    public OpcoesDoacao.DocesChocolates[] getItensDoces() {
        return itensDoces;
    }

    public void setItensDoces(OpcoesDoacao.DocesChocolates[] itensDoces) {
        this.itensDoces = itensDoces;
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
}
