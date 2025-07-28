public class Cell {
    //flags
    public boolean bomba;
    public boolean revelada = false;
    public boolean bandeira = false;
    //numeros uououow
    public int cordX;
    public int cordY;
    public int numBombas;

    Cell(int x, int y, java.util.List<Coordenada> coordsBombas){
        this.bomba = descobrirSeBomba(coordsBombas);
        this.cordX = x;
        this.cordY = y;

    }

    public boolean eBomba(){
        //eu odeio que eu fiz esta função, porém por algum motivo ela funciona melhor
        //do que se eu pegasse Cell.bomba
        return bomba;
    }

    public boolean descobrirSeBomba(java.util.List<Coordenada> coordsBombas){
        System.out.println("descobrindo se eu X:"+ this.cordX + " Y:" + this.cordY+ " sou uma bomba");
        for (int cords = 0; cords<coordsBombas.size(); cords++){
            //indo em cada coordenada de bomba para encontrar se é bomba ou não
            if (coordsBombas.get(cords).equals(this.cordX) && coordsBombas.get(cords).y == this.cordY){
                //caso seja seu valor é mudada para verdadeiro
                System.out.println("bomba em: X:" + this.cordX + " Y:" + this.cordY);
                return true;
            }
        }
        return false;
    }

    public void perguntarSeBomba(int tamanhoCampoX, int tamanhoCampoY, java.util.List<Cell> campo){
        if (this.bomba){
            System.out.println("Eu sou uma bomba:3333! Você perdeu XD ");
        }

        //perguntar pela existência de bombas em células vizinhas
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = 0; dy <= 1; dy++) {
                if (dx == 0 && dy == 0){ //não se auto checa pois não é uma bomba
                    continue;
                }
                //após ser reservada uma área de raio 1 ao redor da célula
                //começa a se fazer realmente a checagem
                int nx = this.cordX + dx;
                int ny = this.cordY + dy;

                //checando os vizinhos na área reservada
                if (0 <= nx && nx <tamanhoCampoX  && 0 <=ny && ny < tamanhoCampoY){
                    int id_vizinho = nx + ny * tamanhoCampoX;
                    if (campo.get(id_vizinho).eBomba()){
                        System.out.println("Bomba encontrada por perto! :o");
                        this.numBombas ++;

                    }
                }
            }

        }

    }

    public void revelar(java.util.List<Cell> campoLista, java.util.List<Coordenada> bombasLista, int tamanhoCampoX, int tamanhoCampoY){
        if (this.revelada || this.bandeira){//chechando se tem bandeira ou se já foi revelada
            return;
        }
        //atualizando a flag de revelada para verdadeiro
        this.revelada = true;
    }

    public static void main(String[] args){
    }
}