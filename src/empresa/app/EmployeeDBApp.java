package empresa.app;

import empresa.model.Employee;
import empresa.model.ExamResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDBApp {

    private static final List<Employee> employeeDatabase = new ArrayList<>();

    public static void main(String[] args) {
        // 1. Cadastrar Pessoas (Simulação de Inserção no DB)
        System.out.println("--- 1. CADASTRANDO FUNCIONÁRIOS ---");

        Employee maria = new Employee(
                "123.456.789-00",
                "MG-10.100.100",
                "Rua das Flores, 100 - Centro",
                "Desenvolvedora Sênior",
                40.0,
                12000.00
        );

        maria.addObservation("Funcionária com excelente desempenho no último trimestre.");
        maria.addAdditionalData("Setor", "TI - Desenvolvimento");
        maria.addAdditionalData("Escolaridade", "Pós-graduação");
        maria.setPendingReceivables(800.00); // Bônus de produtividade

        // Adicionando resultados de exames de Maria (Glicemia: valor menor é melhor)
        maria.addExamResult(new ExamResult(LocalDate.of(2023, 5, 15), "Glicemia", 105.0, "Acima do ideal."));
        maria.addExamResult(new ExamResult(LocalDate.of(2024, 5, 20), "Glicemia", 92.0, "Dentro do ideal."));


        Employee joao = new Employee(
                "987.654.321-99",
                "SP-99.999.999",
                "Av. Principal, 500 - Bairro Novo",
                "Analista Financeiro",
                35.0,
                7500.00
        );

        joao.addObservation("Licença médica tirada em Janeiro/2024.");
        joao.addAdditionalData("Setor", "Financeiro");
        joao.setPendingReceivables(250.50); // Reembolso de despesas

        // Adicionando resultados de exames de João (Resistência: valor maior é melhor)
        joao.addExamResult(new ExamResult(LocalDate.of(2023, 6, 10), "Teste de Resistência", 75.0, "Satisfatório."));
        joao.addExamResult(new ExamResult(new ExamResult(LocalDate.of(2024, 6, 5), "Teste de Resistência", 85.5, "Melhora significativa.")));


        employeeDatabase.add(maria);
        employeeDatabase.add(joao);

        // 2. Listar Todos os Funcionários
        System.out.println("\n--- 2. LISTAGEM COMPLETA ---");
        employeeDatabase.forEach(System.out::println);

        // 3. Simular Consulta e Avaliação de Melhoria (Requisito de Saúde Ocupacional)
        System.out.println("\n--- 3. AVALIAÇÃO DE EXAMES E MELHORIA ---");

        // Avaliação de Maria (Glicemia - Baixo é melhor)
        Optional<Employee> mariaOpt = findEmployeeByCpf("123.456.789-00");
        if (mariaOpt.isPresent()) {
            Employee emp = mariaOpt.get();
            System.out.println("-> Avaliação de " + emp.getJobTitle() + " (CPF: " + emp.getCpf() + "):");
            String assessmentMaria = emp.checkImprovement("Glicemia", true);
            System.out.println("   Glicemia: " + assessmentMaria);
        }

        // Avaliação de João (Teste de Resistência - Alto é melhor)
        Optional<Employee> joaoOpt = findEmployeeByCpf("987.654.321-99");
        if (joaoOpt.isPresent()) {
            Employee emp = joaoOpt.get();
            System.out.println("-> Avaliação de " + emp.getJobTitle() + " (CPF: " + emp.getCpf() + "):");
            String assessmentJoao = emp.checkImprovement("Teste de Resistência", false);
            System.out.println("   Resistência: " + assessmentJoao);
        }

        // 4. Exibir Valores a Receber (Requisito Financeiro)
        System.out.println("\n--- 4. RELATÓRIO FINANCEIRO SIMPLIFICADO ---");
        employeeDatabase.forEach(emp -> {
            System.out.printf("CPF %s | Função: %s | Salário Base: R$%.2f | Valores Extras a Receber: R$%.2f\n",
                    emp.getCpf(),
                    emp.getJobTitle(),
                    emp.getGrossSalary(),
                    emp.getPendingReceivables());
        });
    }

    /**
     * Simula a busca de um funcionário no "banco de dados" pelo CPF.
     */
    public static Optional<Employee> findEmployeeByCpf(String cpf) {
        return employeeDatabase.stream()
                .filter(e -> e.getCpf().equals(cpf))
                .findFirst();
    }
}
