import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {
    public static long timerInicio;
    public static JLabel timerLabel = new JLabel("000");
    public static Timer timer;
    public static boolean timerComecou = false;

    public static int qntFlags;
    public static boolean ganhou = false;
    public static boolean derrota = false;
    public static String estadoPartida = ":P";

    public static void iniciarTimer(){
        System.out.println("iniciando o timer!");
        timerInicio= System.currentTimeMillis();
        timerComecou = true;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ganhou || derrota){
                    interromperTimer();
                }else {
                    long tempoPassado = System.currentTimeMillis() - timerInicio;
                    long tempoPassadoSegundos = tempoPassado / 1000;
                    timerLabel.setText(Long.toString(tempoPassadoSegundos));
                    System.out.println("atualizando o timer");
                }

            }
        });
        timer.start();

    }

    public static void interromperTimer(){
        if (timer != null && timer.isRunning()){
            timer.stop();
            System.out.println("timer interrompido");
        }
    }

    public static void updateCells(java.util.List<Cell> campo, int indexCellAtual, JButton botaoAcao, campo campor){
        //atualiza a célula para que ela respeite o seu estado atual
        if (campo.get(indexCellAtual).revelada){
            //celula revelada mostrando seu número
            String numeroDeBombas = Integer.toString(campo.get(indexCellAtual).numBombas);
            if (campor.showNumbers){
                botaoAcao.setText(numeroDeBombas);
            }else{
                botaoAcao.setText("⏺");
            }

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
                //celula revelada com bomba sendo vermelho
                botaoAcao.setBackground(Color.RED);
                botaoAcao.setText("δ");
            }
        }
        if (!campo.get(indexCellAtual).revelada && !campo.get(indexCellAtual).bandeira){
            //celula não revelada é completamente preta
            botaoAcao.setBackground(Color.BLACK);
            botaoAcao.setForeground(Color.BLACK);
        }
    }

    public static void setScreen(int campoTamanhoX, int campoTamanhoY, java.util.List<Cell> campo, java.util.List<Coordenada> listaBomba, campo camp) {
        timerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        //criando o frame da tela
        JFrame frame = new JFrame("Campo minado menos broxa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(55*campoTamanhoX, 55*campoTamanhoY);
        //criando os paineis do jogo
        JPanel buttonPanel = new JPanel();
        JPanel containerPanel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel();
        containerPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBackground(Color.lightGray);

        //deixando um gap da borda da janela para o jogo mesmo
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //criando a caixa de texto que guarda o número das bombas
        JLabel numeroBombas = new JLabel(Integer.toString(camp.qntBombas));
        numeroBombas.setFont(new Font("Arial", Font.BOLD, 30));
        numeroBombas.setForeground(Color.RED);

        //adicionando um botão para resetar o campo que mostra também o estado da partida
        JButton reset = new JButton(estadoPartida);
        reset.setBorder(BorderFactory.createEmptyBorder(0, 50,0,50));
        reset.setBackground(Color.lightGray);
        reset.setForeground(Color.GREEN);
        reset.setFont(new Font("Arial", Font.BOLD, 30));

        //criando uma lista para salvar todas as células
        java.util.List<JButton> botoes = new java.util.ArrayList<>();

        //criando a grid

        buttonPanel.setLayout(new GridLayout(campoTamanhoY,campoTamanhoX));
        for (int i=0; i < (campoTamanhoX*campoTamanhoY); i++) {
            int finalI = i;
            //criando cada botão individualmente na grid
            JButton acao = new JButton("");
            acao.setMargin(new Insets(0,0,0,0));
            if (camp.showNumbers){
                acao.setFont(new Font("Arial", Font.BOLD, 30));
            }else{
                acao.setFont(new Font("Arial", Font.BOLD, 50));
            }


            //adicionando todos os botões de ação em botões
            botoes.add(acao);
            buttonPanel.add(acao);
            acao.setBackground(Color.BLACK);


            acao.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //esse código inteiro é só pra que a carinha se mexa enquanto o player faz ações, e reaga baseada na vitória/derrota
                    reset.setText(":P");
                    if (ganhou){
                        reset.setText(":3");
                    }else if(derrota){
                        reset.setText("D:");
                    }
                }
            });

            acao.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!timerComecou){
                         //caso o timer não tenha começado, começar o timer
                        iniciarTimer();
                    }


                    if (SwingUtilities.isLeftMouseButton(e) && !ganhou && !derrota){
                        //checando se o player perdeu, pois a função revelar retorna true/false baseada
                        //na célula ser uma bomba ou não
                        boolean perdeu= campo.get(finalI).revelar(campo, listaBomba, campoTamanhoX, campoTamanhoY);
                        //colocando a derrota no jogo
                        if (perdeu){
                            derrota = true;
                            System.out.println("perdeu");
                        }

                        //atualizando o campo inteiro, para suportar casos de floodFill
                        for (int botoesAtualizar = 0; botoesAtualizar <campo.size(); botoesAtualizar++){
                            updateCells(campo, botoesAtualizar, botoes.get(botoesAtualizar), camp);
                        }
                    } else if (SwingUtilities.isRightMouseButton(e) && !ganhou && !derrota){
                        //basicamente um T flip flop pra bandeira
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
                    //sempre checando a vitória após uma ação
                    ganhou = camp.checarVitoria();
                    //definindo a carinha com base na vitória/derrota
                    if (ganhou){
                        reset.setText(":3");
                    }else if(derrota){
                        reset.setText("D:");

                    }else{
                        reset.setText(":o");
                    }
                    //atualizando o número de bombas do painel superior
                    numeroBombas.setText(Integer.toString(camp.qntBombas - qntFlags));
                }
            });
        }
        //fazendo a lógica do botão de reset
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\n\nbotão de reset foi acionado\n\n");
                //reiniciando o campo junto com todas as variáveis
                camp.reiniciar(ganhou, derrota);
                interromperTimer();
                timerLabel.setText("000");
                timerComecou = false;
                ganhou = false;
                derrota = false;
                qntFlags = 0;
                //atualizando o campo inteiro
                for (int botoesAtualizar = 0; botoesAtualizar <campo.size(); botoesAtualizar++){
                    updateCells(campo, botoesAtualizar, botoes.get(botoesAtualizar), camp);
                }
                reset.setText(":P");
            }
        });
        //colocando as dimensões da grid do campo
        buttonPanel.setPreferredSize(new Dimension(35*campoTamanhoX, 35*campoTamanhoY));


        //adicionando cada painel em seu respectivo lugar
        containerPanel.add(buttonPanel);
        topPanel.add(timerLabel);
        topPanel.add(reset);
        topPanel.add(numeroBombas);

        //adicionando o top panel acima do campo
        containerPanel.add(topPanel, BorderLayout.NORTH);

        //adicionando toda a GUI do jogo na tela do jogo
        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);
    }
}


