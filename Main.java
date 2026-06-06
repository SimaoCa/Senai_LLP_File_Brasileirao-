import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Time> tabela = new ArrayList<>();
        try {
            File arquivo = new File("src/jogos.Txt");
            Scanner ler = new Scanner(arquivo);

            while (ler.hasNextLine()) {
                String linha = ler.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(",");


                if (dados.length < 4) continue;

                String resultadoRaw = dados[3].trim();
                if (!resultadoRaw.contains("x") || resultadoRaw.startsWith("x")) continue;

                String timeA = dados[1].trim().replace("_", " ");
                String timeB = dados[2].trim().replace("_", " ");
                String resultado = resultadoRaw;

                String[] gols = resultado.split("x");
                if (gols.length < 2) continue;

                int golsA, golsB;
                try {
                    golsA = Integer.parseInt(gols[0].trim());
                    golsB = Integer.parseInt(gols[1].trim());
                } catch (NumberFormatException e) {
                    continue; // pula linha com resultado inválido
                }

                Time tA = procurarTime(tabela, timeA);
                Time tB = procurarTime(tabela, timeB);

                tA.golsfeitos    += golsA;
                tA.golsSofridos  += golsB;
                tB.golsfeitos    += golsB;
                tB.golsSofridos  += golsA;

                if (golsA > golsB) {
                    tA.pontos += 3;
                } else if (golsB > golsA) {
                    tB.pontos += 3;
                } else {
                    tA.pontos += 1;
                    tB.pontos += 1;
                }
            }
            ler.close();

            tabela.sort((a, b) -> {
                if (b.pontos != a.pontos) return b.pontos - a.pontos;
                return b.getSaldo() - a.getSaldo();
            });

            System.out.println("+-----+---------------------------+--------+-------+");
            System.out.println("|  #  | Time                      | Pontos | Saldo |");
            System.out.println("+-----+---------------------------+--------+-------+");
            for (int i = 0; i < tabela.size(); i++) {
                Time t = tabela.get(i);
                System.out.printf("| %2dº | %-25s | %6d | %5d |%n",
                        i + 1, t.nome, t.pontos, t.getSaldo());
            }
            System.out.println("+-----+---------------------------+--------+-------+");

        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    public static Time procurarTime(ArrayList<Time> tabela, String nome) {
        for (Time t : tabela) {
            if (t.nome.equals(nome)) return t;
        }
        Time novo = new Time(nome);
        tabela.add(novo);
        return novo;
    }
}