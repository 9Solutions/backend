package CRUD.solutionsbackend;

public class Usuario {
    private String nomeCompleto;
    private String email;
    private String numero;
    private String fotoUsuario;
    private String senha;
    private String descricao;

    private boolean empregador;
    private boolean ativo;

    public Usuario() {
    }

    public Usuario(String nomeCompleto, String email, String numero, String fotoUsuario, String senha, String descricao, boolean empregador, boolean ativo) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.numero = numero;
        this.fotoUsuario = fotoUsuario;
        this.senha = senha;
        this.descricao = descricao;
        this.empregador = empregador;
        this.ativo = ativo;
    }

    public boolean isEmpregador() {
        return empregador;
    }

    public void setEmpregador(boolean empregador) {
        this.empregador = empregador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
