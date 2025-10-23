package empresa.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Employee {

    private String cpf;
    private String rg;
    private String address;
    private String observations;

    // Campos para dados adicionais (flexível)
    private Map<String, String> additionalData;

    // Campos para exames anuais
    private List<ExamResult> annualExams;

    // Campos de função e trabalho
    private String jobTitle;
    private double workHoursPerWeek;

    // Campos financeiros (Valores a Receber)
    private double grossSalary;
    private double pendingReceivables; // Outros valores a receber (bônus, reembolsos)

    public Employee(String cpf, String rg, String address, String jobTitle, double workHoursPerWeek, double grossSalary) {
        this.cpf = cpf;
        this.rg = rg;
        this.address = address;
        this.jobTitle = jobTitle;
        this.workHoursPerWeek = workHoursPerWeek;
        this.grossSalary = grossSalary;
        this.observations = "";
        this.additionalData = new HashMap<>();
        this.annualExams = new ArrayList<>();
    }

    // --- Métodos de Controle ---

    public void addObservation(String observation) {
        this.observations += observation + " | ";
    }

    public void addAdditionalData(String key, String value) {
        this.additionalData.put(key, value);
    }

    public void addExamResult(ExamResult result) {
        this.annualExams.add(result);
    }

    /**
     * Verifica se houve melhora nos exames anuais (simulação simples: valor mais recente é melhor que o anterior).
     * @param examType O tipo de exame a ser verificado.
     * @param isLowerBetter Indica se um valor mais baixo é considerado uma melhora (ex: Glicemia).
     * @return String com a avaliação.
     */
    public String checkImprovement(String examType, boolean isLowerBetter) {
        List<ExamResult> filteredExams = this.annualExams.stream()
                .filter(e -> e.getExamType().equalsIgnoreCase(examType))
                .sorted((e1, e2) -> e2.getExamDate().compareTo(e1.getExamDate())) // Mais recente primeiro
                .toList();

        if (filteredExams.size() < 2) {
            return "Necessita de pelo menos 2 resultados para avaliar melhora.";
        }

        ExamResult latest = filteredExams.get(0);
        ExamResult previous = filteredExams.get(1);

        if (latest.getResultValue() == previous.getResultValue()) {
            return "Sem mudança no exame de " + examType + " (" + latest.getResultValue() + ").";
        }

        boolean improved;
        if (isLowerBetter) {
            // Glicemia, Pressão (valores menores são melhores)
            improved = latest.getResultValue() < previous.getResultValue();
        } else {
            // Condição física, Nível de Ferro (valores maiores/maior resistência são melhores)
            improved = latest.getResultValue() > previous.getResultValue();
        }

        String comparison = latest.getResultValue() > previous.getResultValue() ? "aumento" : "redução";

        if (improved) {
            return "Houve MELHORIA no exame de " + examType + ". Valor atual: " + latest.getResultValue() + " (" + comparison + ").";
        } else {
            return "Houve PIORA no exame de " + examType + ". Valor atual: " + latest.getResultValue() + " (" + comparison + ").";
        }
    }

    // --- Getters e Setters (Omissos para brevidade, mas necessários em um DTO completo) ---
    public String getCpf() { return cpf; }
    public String getRg() { return rg; }
    public String getAddress() { return address; }
    public String getObservations() { return observations; }
    public Map<String, String> getAdditionalData() { return additionalData; }
    public List<ExamResult> getAnnualExams() { return annualExams; }
    public String getJobTitle() { return jobTitle; }
    public double getWorkHoursPerWeek() { return workHoursPerWeek; }
    public double getGrossSalary() { return grossSalary; }
    public double getPendingReceivables() { return pendingReceivables; }

    public void setPendingReceivables(double pendingReceivables) {
        this.pendingReceivables = pendingReceivables;
    }

    // --- Exibição ---

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------------------\n");
        sb.append("CADASTRO DE FUNCIONÁRIO: CPF ").append(cpf).append("\n");
        sb.append("-------------------------------------------------------------------\n");
        sb.append("DADOS PESSOAIS:\n");
        sb.append("  RG: ").append(rg).append("\n");
        sb.append("  Endereço: ").append(address).append("\n");
        sb.append("  Observações: ").append(observations.isEmpty() ? "Nenhuma" : observations).append("\n");

        sb.append("\nDADOS OCUPACIONAIS E FINANCEIROS:\n");
        sb.append("  Função: ").append(jobTitle).append("\n");
        sb.append("  Horas Semanais: ").append(workHoursPerWeek).append("\n");
        sb.append("  Salário Bruto (a Receber): R$ ").append(String.format("%.2f", grossSalary)).append("\n");
        sb.append("  Outros Valores a Receber: R$ ").append(String.format("%.2f", pendingReceivables)).append("\n");

        sb.append("\nDADOS ADICIONAIS:\n");
        if (additionalData.isEmpty()) {
            sb.append("  Nenhum campo adicional cadastrado.\n");
        } else {
            additionalData.forEach((k, v) -> sb.append("  ").append(k).append(": ").append(v).append("\n"));
        }

        sb.append("\nHISTÓRICO DE EXAMES ANUAIS:\n");
        if (annualExams.isEmpty()) {
            sb.append("  Nenhum exame cadastrado.\n");
        } else {
            annualExams.forEach(exam -> sb.append(exam).append("\n"));
        }

        return sb.toString();
    }


}
