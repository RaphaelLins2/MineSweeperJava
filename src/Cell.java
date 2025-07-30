public class Cell {
    //flags
    public boolean bomba;
    public boolean revelada = false;
    public boolean bandeira = false;
    //numeros uououow
    public int cordX;
    public int cordY;
    public int numBombas;

    Cell(int x, int y, java.util.List<Coordenada> coordsBombas, int tamanhoCampoX, int tamanhoCampoY, java.util.List<Cell> campo){
        System.out.println("criando uma celula novas nas coordenadas X/Y: " + x+ "/"+y);
        this.cordX = x;
        this.cordY = y;
        this.bomba = descobrirSeBomba(coordsBombas);

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
            if (coordsBombas.get(cords).x ==this.cordX && coordsBombas.get(cords).y == this.cordY){
                //caso seja seu valor é mudada para verdadeiro
                System.out.println("Sou uma bomba :3");
                return true;
            }
        }
        return false;
    }

    public void perguntarSeBomba(int tamanhoCampoX, int tamanhoCampoY, java.util.List<Cell> campo){
        if (this.bomba){
            System.out.println("Eu sou uma bomba:3333! Você perdeu XD ");
        }else{

            //perguntar pela existência de bombas em células vizinhas
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) { //não se auto checa pois não é uma bomba
                        continue;
                    }
                    //após ser reservada uma área de raio 1 ao redor da célula
                    //começa a se fazer realmente a checagem
                    int nx = this.cordX + dx;
                    int ny = this.cordY + dy;

                    //checando os vizinhos na área reservada
                    if (0 <= nx && nx < tamanhoCampoX && 0 <= ny && ny < tamanhoCampoY) {
                        int id_vizinho = nx + ny * tamanhoCampoX;
                        if (campo.get(id_vizinho).bomba) {
                            System.out.println("Bomba encontrada por perto! :o");
                            this.numBombas++;

                        }
                    }
                }
            }
        }

    }

    public void revelar(java.util.List<Cell> campoLista, java.util.List<Coordenada> bombasLista, int tamanhoCampoX, int tamanhoCampoY){
        if (this.revelada || this.bandeira){//chechando se tem bandeira ou se já foi revelada
            System.out.println("nao faço nada, sou uma bandeira/revelada :33");
        }else {
            //atualizando a flag de revelada para verdadeiro
            revelada = true;

            perguntarSeBomba(tamanhoCampoX, tamanhoCampoY, campoLista);

            if (this.numBombas == 0) {
                System.out.println("Flood Fill Detectado, fazendo isso nas redondezas de: X" + this.cordX + " Y" + this.cordY);
                revelarAdjacente(campoLista, tamanhoCampoX, tamanhoCampoY, bombasLista);
            }
        }
    }

    public void revelarAdjacente(java.util.List<Cell> campoLista, int tamanhoCampoX, int tamanhoCampoY, java.util.List<Coordenada> bombasLista){
        //isso daqui é praticamente a mesma lógica para pegar as
        //células adjacentes que nem o perguntarSeBomba() faz
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) { //não se auto checa pois não precisa ser revelado
                    continue;
                }
                //após ser reservada uma área de raio 1 ao redor da célula
                //começa a se fazer realmente a checagem
                int nx = this.cordX + dx;
                int ny = this.cordY + dy;

                //checando os vizinhos na área reservada
                if (0 <= nx && nx < tamanhoCampoX && 0 <= ny && ny < tamanhoCampoY) {
                    int id_vizinho = nx + ny * tamanhoCampoX;
                    if (!campoLista.get(id_vizinho).revelada) {
                        campoLista.get(id_vizinho).revelar(campoLista, bombasLista, tamanhoCampoX, tamanhoCampoY);
                    }
                }
            }
        }
    }
}