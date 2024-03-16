package CRUD.solutionsbackend;

public class Caixa {
    private Usuario usuario;
    private Double valor;
    private Genero escolhaGenero;
    private Idade escolhaIdade;
    private Brinquedo escolhaBrinquedo;
    private MaterialEscolar escolhaMaterial;
    private Higiene escolhaHigiene;
    private ItensDiversos[] itensDiversos = new ItensDiversos[3];
    private ItensUsoPessoal escolhaItensUso;
    private DocesChocolates[] docesChocolates = new DocesChocolates[3];
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

    public Genero getEscolhaGenero() {
        return escolhaGenero;
    }

    public void setEscolhaGenero(Genero escolhaGenero) {
        this.escolhaGenero = escolhaGenero;
    }

    public Idade getEscolhaIdade() {
        return escolhaIdade;
    }

    public void setEscolhaIdade(Idade escolhaIdade) {
        this.escolhaIdade = escolhaIdade;
    }

    public Brinquedo getEscolhaBrinquedo() {
        return escolhaBrinquedo;
    }

    public void setEscolhaBrinquedo(Brinquedo escolhaBrinquedo) {
        this.escolhaBrinquedo = escolhaBrinquedo;
    }

    public MaterialEscolar getEscolhaMaterial() {
        return escolhaMaterial;
    }

    public void setEscolhaMaterial(MaterialEscolar escolhaMaterial) {
        this.escolhaMaterial = escolhaMaterial;
    }

    public Higiene getEscolhaHigiene() {
        return escolhaHigiene;
    }

    public void setEscolhaHigiene(Higiene escolhaHigiene) {
        this.escolhaHigiene = escolhaHigiene;
    }

    public ItensDiversos[] getItensDiversos() {
        return itensDiversos;
    }

    public void setItensDiversos(ItensDiversos[] itensDiversos) {
        this.itensDiversos = itensDiversos;
    }

    public ItensUsoPessoal getEscolhaItensUso() {
        return escolhaItensUso;
    }

    public void setEscolhaItensUso(ItensUsoPessoal escolhaItensUso) {
        this.escolhaItensUso = escolhaItensUso;
    }

    public DocesChocolates[] getDocesChocolates() {
        return docesChocolates;
    }

    public void setDocesChocolates(DocesChocolates[] docesChocolates) {
        this.docesChocolates = docesChocolates;
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
