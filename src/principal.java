import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class principal {
    public static void main(String[] args) {
        List<funcionario> funcionarios = new ArrayList<>();

        // 3.1 - Inserir todos os funcionários
        funcionarios.add(new funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover o funcionário “João” da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários com todas suas informações
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));

        for (funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome() +
                    ", Data de Nascimento: " + funcionario.getDataNascimento().format(formatter) +
                    ", Salário: " + numberFormat.format(funcionario.getSalario()) +
                    ", Função: " + funcionario.getFuncao());
        }

        // 3.4 - Aumentar salário em 10%
        for (funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.10"));
            funcionario.setSalario(novoSalario);
        }

        // 3.5 - Agrupar os funcionários por função em um MAP
        Map<String, List<funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(funcionario::getFuncao));

        // 3.6 - Imprimir os funcionários, agrupados por função
        for (Map.Entry<String, List<funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (funcionario funcionario : entry.getValue()) {
                System.out.println("  - " + funcionario.getNome());
            }
        }

        // 3.8 - Imprimir os funcionários que fazem aniversário no mês 10 e 12
        for (funcionario funcionario : funcionarios) {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();
            if (mesAniversario == 10 || mesAniversario == 12) {
                System.out.println("Aniversariante: " + funcionario.getNome() + ", Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));
            }
        }

        // 3.9 - Imprimir o funcionário com a maior idade
        funcionario funcionarioMaisVelho = funcionarios.stream()
                .min(Comparator.comparing(funcionario::getDataNascimento))
                .orElse(null);
        if (funcionarioMaisVelho != null) {
            int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("Funcionário mais velho: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade);
        }

        // 3.10 - Imprimir a lista de funcionários por ordem alfabética
        List<funcionario> funcionariosOrdenados = funcionarios.stream()
                .sorted(Comparator.comparing(funcionario::getNome))
                .collect(Collectors.toList());
        for (funcionario funcionario : funcionariosOrdenados) {
            System.out.println("Funcionário: " + funcionario.getNome());
        }

        // 3.11 - Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + numberFormat.format(totalSalarios));

        // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        for (funcionario funcionario : funcionarios) {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.DOWN);
            System.out.println("Funcionário: " + funcionario.getNome() +
                    ", Salários mínimos: " + quantidadeSalariosMinimos);
        }
    }
}