package model.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.NotaDAO;
import model.entities.Matriculas;
import model.entities.Notas;

public class NotaDAOJDBC implements NotaDAO {

    private Connection conn;

    public NotaDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Notas obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Notas " +
                            "(id_matricula, nota, data_avaliacao) " +
                            "VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getMatricula().getIdMatricula());
            st.setBigDecimal(2, obj.getNota());
            st.setDate(3, Date.valueOf(obj.getDataAvaliacao()));

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setIdNota(id);
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
    public void atualizar(Notas obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE Notas " +
                            "SET id_matricula = ?, nota = ?, data_avaliacao = ? " +
                            "WHERE id_nota = ?");

            st.setInt(1, obj.getMatricula().getIdMatricula());
            st.setBigDecimal(2, obj.getNota());
            st.setDate(3, Date.valueOf(obj.getDataAvaliacao()));
            st.setInt(4, obj.getIdNota());

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
            st = conn.prepareStatement("DELETE FROM Notas WHERE id_nota = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Notas buscarPorId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Notas WHERE id_nota = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Notas obj = new Notas();
                obj.setIdNota(rs.getInt("id_nota"));
                obj.setNota(rs.getBigDecimal("nota"));
                obj.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());

                // Buscar matrícula associada
                Matriculas matricula = new Matriculas();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                obj.setMatricula(matricula);

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
    public List<Notas> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Notas ORDER BY data_avaliacao");
            rs = st.executeQuery();

            List<Notas> list = new ArrayList<>();

            while (rs.next()) {
                Notas obj = new Notas();
                obj.setIdNota(rs.getInt("id_nota"));
                obj.setNota(rs.getBigDecimal("nota"));
                obj.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());

                // Buscar matrícula associada
                Matriculas matricula = new Matriculas();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                obj.setMatricula(matricula);

                list.add(obj);

                // Formatar e imprimir os dados no console
                String dadosNota = String.format(
                        "ID: %d \t MATRÍCULA: %d \t NOTA: %.2f \t DATA: %s",
                        rs.getInt("id_nota"),
                        rs.getInt("id_matricula"),
                        rs.getBigDecimal("nota"),
                        rs.getDate("data_avaliacao").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                );
                System.out.println(dadosNota);
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
    public List<Notas> buscarNotasPorMatricula(Integer idMatricula) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Notas WHERE id_matricula = ?");
            st.setInt(1, idMatricula);
            rs = st.executeQuery();

            List<Notas> list = new ArrayList<>();

            while (rs.next()) {
                Notas obj = new Notas();
                obj.setIdNota(rs.getInt("id_nota"));
                obj.setNota(rs.getBigDecimal("nota"));
                obj.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());

                // Buscar matrícula associada
                Matriculas matricula = new Matriculas();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                obj.setMatricula(matricula);

                list.add(obj);

                // Formatar e imprimir os dados no console
                String dadosNota = String.format(
                        "ID: %d \t MATRÍCULA: %d \t NOTA: %.2f \t DATA: %s",
                        rs.getInt("id_nota"),
                        rs.getInt("id_matricula"),
                        rs.getBigDecimal("nota"),
                        rs.getDate("data_avaliacao").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                );
                System.out.println(dadosNota);
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
