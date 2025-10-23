package empresa.model;

import java.time.LocalDate;


public class ExamResult {

    private LocalDate examDate;
    private String examType;
    private double resultValue;
    private String remarks; // Observações do médico ou do funcionário

    public ExamResult(LocalDate examDate, String examType, double resultValue, String remarks) {
        this.examDate = examDate;
        this.examType = examType;
        this.resultValue = resultValue;
        this.remarks = remarks;
    }

    public ExamResult(ExamResult testeDeResistência) {
    }

    // Getters
    public LocalDate getExamDate() { return examDate; }
    public double getResultValue() { return resultValue; }
    public String getExamType() { return examType; }

    @Override
    public String toString() {
        return "  [Tipo: " + examType + ", Data: " + examDate + ", Valor: " + resultValue + ", Observação: " + remarks + "]";
    }
}
