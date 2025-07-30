import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {
    public static int qntFlags;
    public static boolean ganhou = false;
    public static boolean derrota = false;
    public static String estadoPartida = ":P";
    static ImageIcon ChikawaStatus = new ImageIcon("C:\\Users\\Public\\Downloads\\chikawaHappy.png");
    static ImageIcon ChikawaNeutral = new ImageIcon("C:\\Users\\Public\\Downloads\\chikawaHappy.png");
    static ImageIcon ChikawaSad = new ImageIcon("C:\\Users\\Public\\Downloads\\chikawaHappy.png");


    public static void updateCells(java.util.List<Cell> campo, int indexCellAtual, JButton botaoAcao){
        if (campo.get(indexCellAtual).revelada){
            String numeroDeBombas = Integer.toString(campo.get(indexCellAtual).numBombas);
            botaoAcao.setText(numeroDeBombas);
            botaoAcao.setBackground(Color.GRAY);
            switch (Integer.parseInt(numeroDeBombas)){
                case 1:
                    botaoAcao.setForeground(Color.CYAN);
                    break;
                case 2:
                    botaoAcao.setForeground(Color.GREEN);
                    break;
                case 3:
                    botaoAcao.setForeground(Color.RED);
                    break;
                case 4:
                    botaoAcao.setForeground(Color.BLUE);
                    break;
                case 5:
                    botaoAcao.setForeground(Color.PINK);
                    break;
                case 6:
                    botaoAcao.setForeground(Color.ORANGE);
                    break;
                case 7:
                    botaoAcao.setForeground(Color.DARK_GRAY);
                    break;
                case 8:
                    botaoAcao.setForeground(Color.MAGENTA);
                    break;
                case 0:
                    botaoAcao.setText("");
                    break;

            }
            if (campo.get(indexCellAtual).bomba){
                botaoAcao.setBackground(Color.RED);
            }
        }
        if (!campo.get(indexCellAtual).revelada && !campo.get(indexCellAtual).bandeira){
            botaoAcao.setBackground(Color.BLACK);
            botaoAcao.setForeground(Color.BLACK);
        }
    }

    public static void setScreen(int campoTamanhoX, int campoTamanhoY, java.util.List<Cell> campo, java.util.List<Coordenada> listaBomba, campo camp){
        //criando o frame da tela
        JFrame frame = new JFrame("Campo minado menos broxa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        JPanel containerPanel = new JPanel();

        JLabel numeroBombas = new JLabel(Integer.toString(camp.qntBombas));
        numeroBombas.setForeground(Color.RED);

        JLabel estadoChikawa = new JLabel(ChikawaStatus);
        estadoChikawa.setSize(50, 50);

        frame.setSize(55*campoTamanhoX, 55*campoTamanhoX);

        JLabel status = new JLabel(estadoPartida);

        //adicionando um botão para resetar o campo
        JButton reset = new JButton("reset");


        //criando uma lista para salvar todas as células
        java.util.List<JButton> botoes = new java.util.ArrayList<>();


        //criando a grid
        buttonPanel.setLayout(new GridLayout(campoTamanhoX,campoTamanhoY));
        for (int i=0; i < (campoTamanhoX*campoTamanhoY); i++) {

            int finalI = i;


            JButton acao = new JButton("");
            //adicionando todos os botões de ação em botões
            botoes.add(acao);
            buttonPanel.add(acao);
            acao.setBackground(Color.BLACK);

            //fazendo a lógica do botão de reset
            reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    camp.reiniciar(ganhou, derrota);
                    ganhou = false;
                    derrota = false;
                    for (int botoesAtualizar = 0; botoesAtualizar <campo.size(); botoesAtualizar++){
                        updateCells(campo, botoesAtualizar, botoes.get(botoesAtualizar));
                    }
                }
            });

            //escutando por inputs

            acao.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    status.setText(":P");
                    if (ganhou){
                        status.setText(":3");
                        estadoChikawa.setIcon(ChikawaStatus);

                    }else if(derrota){
                        status.setText("D:");
                        estadoChikawa.setIcon(ChikawaSad);

                    }
                }
            });

            acao.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    if (SwingUtilities.isLeftMouseButton(e) && !ganhou && !derrota){
                        boolean perdeu= campo.get(finalI).revelar(campo, listaBomba, campoTamanhoX, campoTamanhoY);

                        //colocando a derrota no jogo
                        if (perdeu){
                            derrota = true;
                            System.out.println("perdeu");
                        }

                        updateCells(campo, finalI, acao);
                        for (int botoesAtualizar = 0; botoesAtualizar <campo.size(); botoesAtualizar++){
                            updateCells(campo, botoesAtualizar, botoes.get(botoesAtualizar));
                        }
                    } else if (SwingUtilities.isRightMouseButton(e) && !ganhou && !derrota){
                        if (!campo.get(finalI).revelada && !campo.get(finalI).bandeira) {
                            acao.setBackground(Color.GREEN);
                            acao.setForeground(Color.GREEN);
                            campo.get(finalI).bandeira = true;
                            qntFlags++;
                        } else if (campo.get(finalI).bandeira){
                            campo.get(finalI).bandeira = false;
                            acao.setBackground(Color.BLACK);
                            acao.setForeground(Color.BLACK);
                            qntFlags--;
                        }
                    }
                    ganhou = camp.checarVitoria();
                    if (ganhou){
                        status.setText(":3");
                        estadoChikawa.setIcon(ChikawaStatus);
                    }else if(derrota){
                        status.setText("D:");
                        estadoChikawa.setIcon(ChikawaSad);
                    }else{
                        status.setText(":O");
                        estadoChikawa.setIcon(ChikawaNeutral);

                    }
                    numeroBombas.setText(Integer.toString(camp.qntBombas - qntFlags));
                }
            });
        }
        buttonPanel.setPreferredSize(new Dimension(55*campoTamanhoX, 55*campoTamanhoY));


        status.setText((estadoPartida));
        containerPanel.add(status);


        containerPanel.add(buttonPanel);

        containerPanel.add(numeroBombas);
        containerPanel.add(reset);

        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
        campo asd = new campo();
        setScreen(campo.tamanhoCampoX, campo.tamanhoCampoY, campo.campoLista, campo.coordenadasBombas, asd);
    }
}


