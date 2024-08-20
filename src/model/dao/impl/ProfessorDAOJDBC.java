package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ProfessorDAO;
import model.entities.Cursos;
import model.entities.Professores;

public class ProfessorDAOJDBC implements ProfessorDAO {

    private Connection conn;

    public ProfessorDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Professores obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Professores " +
                            "(nome, data_contratacao, departamento, telefone, email) " +
                            "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setDate(2, Date.valueOf(obj.getDataContratacao()));
            st.setString(3, obj.getDepartamento());
            st.setString(4, obj.getTelefone());
            st.setString(5, obj.getEmail());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setIdProfessor(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha foi afetada!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Professores obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE Professores " +
                            "SET nome = ?, data_contratacao = ?, departamento = ?, telefone = ?, email = ? " +
                            "WHERE id_professor = ?");

            st.setString(1, obj.getNome());
            st.setDate(2, Date.valueOf(obj.getDataContratacao()));
            st.setString(3, obj.getDepartamento());
            st.setString(4, obj.getTelefone());
            st.setString(5, obj.getEmail());
            st.setInt(6, obj.getIdProfessor());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void excluirPorId(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Professores WHERE id_professor = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Professores buscarPorId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Professores WHERE id_professor = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Professores obj = new Professores();
                obj.setIdProfessor(rs.getInt("id_professor"));
                obj.setNome(rs.getString("nome"));
                obj.setDataContratacao(rs.getDate("data_contratacao").toLocalDate());
                obj.setDepartamento(rs.getString("departamento"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setEmail(rs.getString("email"));
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Professores> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Professores ORDER BY nome");
            rs = st.executeQuery();

            List<Professores> list = new ArrayList<>();

            while (rs.next()) {
                Professores obj = new Professores();
                obj.setIdProfessor(rs.getInt("id_professor"));
                obj.setNome(rs.getString("nome"));
                obj.setDataContratacao(rs.getDate("data_contratacao").toLocalDate());
                obj.setDepartamento(rs.getString("departamento"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setEmail(rs.getString("email"));

                list.add(obj);

                // Formatar e imprimir os dados no console
                String dadosProfessor = String.format(
                        "ID: %d \t Nome: %s \t Data de Contratação: %s \t Departamento: %s \t Telefone: %s \t Email: %s",
                        rs.getInt("id_professor"),
                        rs.getString("nome"),
                        rs.getDate("data_contratacao").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        rs.getString("departamento"),
                        rs.getString("telefone"),
                        rs.getString("email")
                );
                System.out.println(dadosProfessor);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Professores> buscarProfessoresPorCurso(Integer idCurso) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT p.* " +
                            "FROM Professores p " +
                            "JOIN Cursos c ON p.id_professor = c.id_professor " +
                            "WHERE c.id_curso = ?");
            st.setInt(1, idCurso);
            rs = st.executeQuery();

            List<Professores> list = new ArrayList<>();

            while (rs.next()) {
                Professores obj = new Professores();
                obj.setIdProfessor(rs.getInt("id_professor"));
                obj.setNome(rs.getString("nome"));
                obj.setDataContratacao(rs.getDate("data_contratacao").toLocalDate());
                obj.setDepartamento(rs.getString("departamento"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setEmail(rs.getString("email"));

                list.add(obj);

                // Formatar e imprimir os dados no console
                String dadosProfessor = String.format(
                        "ID: %d \t Nome: %s \t Data de Contratação: %s \t Departamento: %s \t Telefone: %s \t Email: %s",
                        rs.getInt("id_professor"),
                        rs.getString("nome"),
                        rs.getDate("data_contratacao").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        rs.getString("departamento"),
                        rs.getString("telefone"),
                        rs.getString("email")
                );
                System.out.println(dadosProfessor);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
