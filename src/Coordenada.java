class Coordenada {
    int x, y;

    Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void say(){
        System.out.println("minhas coords são x: " + this.x + " y: " + this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordenada)) return false;
        Coordenada outra = (Coordenada) obj;
        return x == outra.x && y == outra.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
