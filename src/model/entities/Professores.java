package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Professores implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProfessor;
    private String nome;
    private LocalDate dataContratacao;
    private String departamento;
    private String telefone;
    private String email;
    private String senha; // Novo atributo para senha
    private boolean coordenador;

    // Construtor
    public Professores() {

    }

    public Professores(int idProfessor, String nome, LocalDate dataContratacao, String departamento, String telefone, String email, String senha, boolean coordenador) {
        this.idProfessor = idProfessor;
        this.nome = nome;
        this.dataContratacao = dataContratacao;
        this.departamento = departamento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.coordenador = coordenador;
    }
    // Getters e Setters

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }


    public boolean isCoordenador() {
        return coordenador;
    }

    public void setCoordenador(boolean coordenador) {
        this.coordenador = coordenador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professores that = (Professores) o;
        return Objects.equals(idProfessor, that.idProfessor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idProfessor);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d, Nome: %s, Data de Contratação: %s, Departamento: %s, Telefone: %s, Email: %s, Coordenador: %b",
                getIdProfessor(),
                getNome(),
                getDataContratacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                getDepartamento(),
                getTelefone(),
                getEmail(),
                isCoordenador()
        );
    }

}
