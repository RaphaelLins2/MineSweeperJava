public class Cell {
    //flags
    public boolean bomba;
    public boolean revelada = false;
    public boolean bandeira = false;
    //numeros uououow
    public int cordX;
    public int cordY;
    public int numBombas;

    public void cellInnit(int x, int y, java.util.List<int[]> coordsBombas){
        this.bomba = descobrirSeBomba(coordsBombas);
        this.cordX = x;
        this.cordY = y;

    }

    public boolean eBomba(){
        return bomba;
    }

    public boolean descobrirSeBomba(java.util.List<int[]> coordsBombas){
        for (int[] coord : coordsBombas){
            //indo em cada coordenada de bomba para encontrar se é bomba ou não
            if (coord[0] == this.cordX && coord[1] == this.cordY){
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

    public void main(String[] args){
    }
}