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
import model.dao.MatriculaDAO;
import model.entities.Alunos;
import model.entities.Cursos;
import model.entities.Matriculas;

public class MatriculaDAOJDBC implements MatriculaDAO {

    private Connection conn;

    public MatriculaDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Matriculas obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Matriculas " +
                            "(id_aluno, id_curso, data_matricula) " +
                            "VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getAluno().getIdAluno());
            st.setInt(2, obj.getCurso().getIdCurso());
            st.setDate(3, Date.valueOf(obj.getDataMatricula()));

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setIdMatricula(id);
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
    public void atualizar(Matriculas obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE Matriculas " +
                            "SET id_aluno = ?, id_curso = ?, data_matricula = ? " +
                            "WHERE id_matricula = ?");

            st.setInt(1, obj.getAluno().getIdAluno());
            st.setInt(2, obj.getCurso().getIdCurso());
            st.setDate(3, Date.valueOf(obj.getDataMatricula()));
            st.setInt(4, obj.getIdMatricula());

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
            st = conn.prepareStatement("DELETE FROM Matriculas WHERE id_matricula = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Matriculas buscarPorId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Matriculas WHERE id_matricula = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Matriculas obj = new Matriculas();
                obj.setIdMatricula(rs.getInt("id_matricula"));

                // Assumindo que você já possui métodos para buscar Aluno e Curso por ID
                Alunos aluno = new AlunoDAOJDBC(conn).buscarPorId(rs.getInt("id_aluno"));
                Cursos curso = new CursoDAOJDBC(conn).buscarPorId(rs.getInt("id_curso"));

                obj.setAluno(aluno);
                obj.setCurso(curso);
                obj.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
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
    public List<Matriculas> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Matriculas");
            rs = st.executeQuery();

            List<Matriculas> list = new ArrayList<>();

            while (rs.next()) {
                Matriculas obj = new Matriculas();
                obj.setIdMatricula(rs.getInt("id_matricula"));

                // Assumindo que você já possui métodos para buscar Aluno e Curso por ID
                Alunos aluno = new AlunoDAOJDBC(conn).buscarPorId(rs.getInt("id_aluno"));
                Cursos curso = new CursoDAOJDBC(conn).buscarPorId(rs.getInt("id_curso"));

                obj.setAluno(aluno);
                obj.setCurso(curso);
                obj.setDataMatricula(rs.getDate("data_matricula").toLocalDate());

                list.add(obj);

                // Formatar e imprimir os dados no console
                String dadosMatricula = String.format(
                        "ID: %d \t Aluno: %s \t Curso: %s \t Data da Matrícula: %s",
                        rs.getInt("id_matricula"),
                        aluno.getNome(),
                        curso.getNome(),
                        rs.getDate("data_matricula").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                );
                System.out.println(dadosMatricula);
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
    public List<Matriculas> buscarMatriculasPorAluno(Integer idAluno) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Matriculas WHERE id_aluno = ?");
            st.setInt(1, idAluno);
            rs = st.executeQuery();

            List<Matriculas> list = new ArrayList<>();

            while (rs.next()) {
                Matriculas obj = new Matriculas();
                obj.setIdMatricula(rs.getInt("id_matricula"));

                // Assumindo que você já possui métodos para buscar Curso por ID
                Cursos curso = new CursoDAOJDBC(conn).buscarPorId(rs.getInt("id_curso"));

                obj.setAluno(new AlunoDAOJDBC(conn).buscarPorId(idAluno));
                obj.setCurso(curso);
                obj.setDataMatricula(rs.getDate("data_matricula").toLocalDate());

                list.add(obj);

                // Formatar e imprimir os dados no console
                String dadosMatricula = String.format(
                        "ID: %d \t Aluno: %s \t Curso: %s \t Data da Matrícula: %s",
                        rs.getInt("id_matricula"),
                        obj.getAluno().getNome(),
                        curso.getNome(),
                        rs.getDate("data_matricula").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                );
                System.out.println(dadosMatricula);
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
    public List<Matriculas> buscarMatriculasPorCurso(Integer idCurso) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Matriculas WHERE id_curso = ?");
            st.setInt(1, idCurso);
            rs = st.executeQuery();

            List<Matriculas> list = new ArrayList<>();

            while (rs.next()) {
                Matriculas obj = new Matriculas();
                obj.setIdMatricula(rs.getInt("id_matricula"));

                // Assumindo que você já possui métodos para buscar Aluno por ID
                Alunos aluno = new AlunoDAOJDBC(conn).buscarPorId(rs.getInt("id_aluno"));

                obj.setCurso(new CursoDAOJDBC(conn).buscarPorId(idCurso));
                obj.setAluno(aluno);
                obj.setDataMatricula(rs.getDate("data_matricula").toLocalDate());

                list.add(obj);

                // Formatar e imprimir os dados no console
                String dadosMatricula = String.format(
                        "ID: %d \t Aluno: %s \t Curso: %s \t Data da Matrícula: %s",
                        rs.getInt("id_matricula"),
                        aluno.getNome(),
                        obj.getCurso().getNome(),
                        rs.getDate("data_matricula").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                );
                System.out.println(dadosMatricula);
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
