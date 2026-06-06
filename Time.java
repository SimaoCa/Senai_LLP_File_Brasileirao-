public class Time {
    String nome;
    int pontos;
    int golsfeitos, golsSofridos;
    public Time(String nome){
        this.nome = nome;
        this.pontos = 0;
        this.golsfeitos =0;
        this.golsSofridos =0;
    }public int getSaldo(){
        return golsfeitos - golsSofridos;
    }
}
