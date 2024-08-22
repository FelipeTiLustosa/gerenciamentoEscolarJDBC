package application;

import db.DB;
import model.dao.*;
import model.services.*;

import java.sql.Connection;
import java.util.Scanner;

public class Program2 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Connection conn = DB.getConnection(); // Substitua pela sua conexão com o banco de dados
        AuthDAO authDAO = new AuthDAO(conn);
        AuthService authService = new AuthService(authDAO);

        while (true) {
            exibirMenuPrincipal(authService);
        }
    }

    private static void exibirMenuPrincipal(AuthService authService) {
        System.out.println(
                "\033[0m╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(
                "║   \033[1;31mO\033[0m \033[1;32m■ ■                    \033[0m║                                                                             ║");
        System.out.println(
                "\033[0m║   \033[1;32m■ ■\033[0m                      ║                                                                             ║");
        System.out.println(
                "║   \033[1;32m■ ■ ■\033[0m INSTITUTO FEDERAL  ║      SISTEMA DE GERENCIAMENTO DE DISCIPLINAS  DO IFPI                       ║");
        System.out.println(
                "║   \033[1;32m■ ■\033[0m   Piauí              ║                                                                             ║");
        System.out.println(
                "╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println(
                "║                                                                                                          ║");
        System.out.println(
                "║                                        Escolha o tipo de login abaixo:                                   ║");
        System.out.println(
                "║                                                                                                          ║");
        System.out.println(
                "\033[0m║\033[1;32m                         1. Coordenador                                                                   \033[0m║");
        System.out.println(
                "\033[0m║\033[1;32m                         2. Professor                                                                     \033[0m║");
        System.out.println(
                "\033[0m║\033[1;32m                         3. Aluno                                                                         \033[0m║");
        System.out.println(
                "\033[0m║\033[1;32m                         0. Sair do Sistema                                                               \033[0m║\033[0m");
        System.out.println(
                "║                                                                                                          \033[0m║");
        System.out.println(
                "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println(
                "\033[1;36m╠----------------------------------------------------------------------------------------------------------╣\033[0m");
        System.out.print("\033[0m Digite a opção escolhida: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        System.out.println(
                "\033[1;36m╠----------------------------------------------------------------------------------------------------------╣\033[0m");

        switch (escolha) {
            case 1:
                loginCoordenador(authService);
                break;
            case 2:
                loginProfessor(authService);
                break;
            case 3:
                loginAluno(authService);
                break;
            case 0:
                System.out.println("Saindo do Sistema. Até logo!");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void loginAluno(AuthService authService) {
        boolean autenticado = false;

        do {
            try {
                System.out.print("Digite o ID do Aluno: ");
                int idAluno = scanner.nextInt();
                scanner.nextLine(); // Consome a quebra de linha

                System.out.print("Digite a senha do Aluno: ");
                String senha = scanner.nextLine();

                autenticado = authService.loginAluno(idAluno, senha);

                if (autenticado) {
                    System.out.println("Login do Aluno bem-sucedido!");
                    exibirMenuAluno(idAluno);
                } else {
                    System.out.println("ID ou senha incorretos.");
                    System.out.print("Deseja tentar novamente? (S/N): ");
                    String tentarNovamente = scanner.nextLine();
                    if (!tentarNovamente.equalsIgnoreCase("S")) {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao tentar realizar login: " + e.getMessage());
                scanner.nextLine();
                System.out.print("Deseja tentar novamente? (S/N): ");
                String tentarNovamente = scanner.nextLine();
                if (!tentarNovamente.equalsIgnoreCase("S")) {
                    return;
                }
            }
        } while (!autenticado);
    }

    private static void exibirMenuAluno(int idAluno) {
        while (true) {
            System.out.println(
                    "\033[0m╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(
                    "║                                         MENU DO ALUNO                                                     ║");
            System.out.println(
                    "╠══════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println(
                    "║                                                                                                          ║");
            System.out.println(
                    "\033[0m║\033[1;32m                         1. Verificar Matrículas                                                           \033[0m║");
            System.out.println(
                    "\033[0m║\033[1;32m                         2. Consultar Notas                                                                \033[0m║");
            System.out.println(
                    "\033[0m║\033[1;32m                         3. Ver Cursos Disponíveis                                                         \033[0m║");
            System.out.println(
                    "\033[0m║\033[1;32m                         4. Ver Professores dos Cursos                                                     \033[0m║");
            System.out.println(
                    "\033[0m║\033[1;32m                         0. Sair                                                                          \033[0m║\033[0m");
            System.out.println(
                    "║                                                                                                          \033[0m║");
            System.out.println(
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.println(
                    "\033[1;36m╠----------------------------------------------------------------------------------------------------------╣\033[0m");
            System.out.print("\033[0m Digite a opção escolhida: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            System.out.println(
                    "\033[1;36m╠----------------------------------------------------------------------------------------------------------╣\033[0m");

            switch (escolha) {
                case 1:
                    verificarMatriculas(idAluno);
                    break;
                case 2:
                    consultarNotas(idAluno);
                    break;
                case 3:
                    verCursosDisponiveis();
                    break;
                case 4:
                    verProfessoresDosCursos(idAluno);
                    break;
                case 0:
                    System.out.println("Saindo do menu do aluno.");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void verificarMatriculas(int idAluno) {
        MatriculaDAO alunoMatricular = DaoFactory.criarMatriculaDAO();
        alunoMatricular.buscarMatriculasPorAluno(idAluno);
    }

    private static void consultarNotas(int idAluno) {
        NotaDAO alunoNotas = DaoFactory.criarNotaDAO();
        alunoNotas.buscarNotasPorMatricula(idAluno);
    }

    private static void verCursosDisponiveis() {
        CursoDAO alunoCursos = DaoFactory.criarCursoDAO();
        alunoCursos.buscarTodos();
    }

    private static void verProfessoresDosCursos(int idAluno) {
        // Implementar a lógica para exibir os professores dos cursos do aluno
        System.out.println("Exibindo os professores dos cursos do aluno " + idAluno);
        // Aqui você chamaria o método buscarProfessoresPorCurso e exibiria os resultados
    }

    // Métodos para login de Coordenador e Professor de forma similar ao loginAluno
    private static void loginCoordenador(AuthService authService) {
        // Implementação semelhante ao loginAluno
    }

    private static void loginProfessor(AuthService authService) {
        // Implementação semelhante ao loginAluno
    }
}
