import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class campo {
    public static int tamanhoCampoX;
    public static int tamanhoCampoY;
    public static int qntBombas;
    public static List<Coordenada> coordenadasBombas= new ArrayList<>();
    public static List<Cell> campoLista= new ArrayList<>();


    campo(){
        java.util.Scanner scan = new Scanner(System.in);

        //pegando os valores do campo
        System.out.println("Qual o tamanho do campo em X? ");
        tamanhoCampoX = scan.nextInt();

        System.out.println("Qual o tamanho do campo em y? ");
        tamanhoCampoY = scan.nextInt();

        //pegando a quantidade de bombas
        System.out.println("Com quantas bombas você quer jogar? ");
        qntBombas = scan.nextInt();
        geracaoBombas();
        gerarCampo();
    }

    public void geracaoBombas(){
        for (int i =0; i < qntBombas; i++){
            //gerando as coordenadas das bombas

            int xBomba = (int) (Math.random() * tamanhoCampoX-1);
            int yBomba = (int) (Math.random() * tamanhoCampoY-1);

            Coordenada cord = new Coordenada(xBomba, yBomba);


            //checagem de coordenadas repitidas
            while(coordenadasBombas.contains(cord)){
                System.out.println("coordenadas duplicadas! Gerando novas");
                xBomba = (int) (Math.random() * tamanhoCampoX-1);
                yBomba = (int) (Math.random() * tamanhoCampoY-1);
                cord = new Coordenada(xBomba, yBomba);
            }
            coordenadasBombas.add(cord);
        }
        System.out.println(coordenadasBombas);
        System.out.println("o tamanho da lista de bombas deu: " + coordenadasBombas.size());
    }

    public void gerarCampo(){
        //gerando um campo para cada célula que tenha no campo,
        //e sim, em teoria poderia ser utilizado a área do campo
        //no loop de for, porém como isso é apenas um port e isso
        //funcionou melhor desse jeito no python, eu decidi deixar assim no java também
        for (int ycamp=0; ycamp <tamanhoCampoY; ycamp++){
            for (int xcamp=0; xcamp <tamanhoCampoX; xcamp++){
                System.out.println("criando uma celula novas nas coordenadas X/Y " + xcamp  +"/"+ ycamp);
                Cell celula = new Cell(xcamp, ycamp, coordenadasBombas, tamanhoCampoX, tamanhoCampoY, campoLista);
                campoLista.add(celula);
            }
        }
        System.out.println(campoLista);
        System.out.println("quantidade de celulas no campo: " + campoLista.size());
    }

    public void reiniciar(boolean vitoria, boolean derrota){
        vitoria = false;
        derrota = false;
        coordenadasBombas.clear();
        campoLista.clear();
        geracaoBombas();
        gerarCampo();
    }

    public boolean checarVitoria(){
        for (int i = 0; i< campoLista.size(); i++){
            Cell celula = campoLista.get(i);
            if (!celula.revelada && !celula.bomba && !celula.bandeira){
                return false;
            }

        }
        System.out.println("ganhou");
        return true;
    }

    public static void main(String[] args){

    }
}
