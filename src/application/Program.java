package application;

import db.DB;
import model.dao.AlunoDAO;
import model.dao.DaoFactory;
import model.entities.Alunos;

import java.time.LocalDate;

public class Program {
    public static <AlunosDAO> void main(String[] args) {
        AlunoDAO alunosDAO = DaoFactory.criarAlunoDAO();

        // Cria um novo objeto Alunos
       // Alunos novoAluno = new Alunos(null, "Pedro Lustosa", LocalDate.of(2000, 5, 15), "Rua A, 123", "11987654321", "joao.silva@example.com");

        // Insere o novo aluno no banco de dados
        //alunosDAO.inserir(novoAluno);

        // Exibe o ID gerado
        //System.out.println("Inserção realizada com sucesso! ID do novo aluno: " + novoAluno.getIdAluno());

        alunosDAO.buscarTodos();
        alunosDAO.buscarAlunosPorCurso(5);

        alunosDAO.buscarPorId(5);
        DB.closeConnection(DB.getConnection());
    }
}