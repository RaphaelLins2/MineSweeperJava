import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {

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
    }

    public static void setScreen(int campoTamanhoX, int campoTamanhoY, java.util.List<Cell> campo, java.util.List<Coordenada> listaBomba){
        //criando o frame da tela
        JFrame frame = new JFrame("Campo minado menos broxa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        JPanel containerPanel = new JPanel();
        frame.setSize(55*campoTamanhoX, 55*campoTamanhoX);

        //criando uma lista para salvar todas as células
        java.util.List<JButton> botoes = new java.util.ArrayList<>();

        //criando a grid
        buttonPanel.setLayout(new GridLayout(campoTamanhoX,campoTamanhoY));
        for (int i=0; i < (campoTamanhoX*campoTamanhoY); i++) {

            JButton acao = new JButton("");
            //adicionando todos os botões de ação em botões
            botoes.add(acao);
            buttonPanel.add(acao);
            acao.setBackground(Color.BLACK);


            //escutando por inputs
            int finalI = i;

            acao.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)){
                        campo.get(finalI).revelar(campo, listaBomba, campoTamanhoX, campoTamanhoY);
                        campo.get(finalI).revelada = true;

                        updateCells(campo, finalI, acao);
                        for (int botoesAtualizar = 0; botoesAtualizar <campo.size(); botoesAtualizar++){
                            updateCells(campo, botoesAtualizar, botoes.get(botoesAtualizar));
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)){
                        if (!campo.get(finalI).revelada) {
                            acao.setBackground(Color.GREEN);
                            acao.setForeground(Color.GREEN);
                            campo.get(finalI).bandeira = true;
                        }
                    }
                }
            });
        }
        buttonPanel.setPreferredSize(new Dimension(55*campoTamanhoX, 55*campoTamanhoY));
        containerPanel.add(buttonPanel);



        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
        campo asd = new campo();
        setScreen(campo.tamanhoCampoX, campo.tamanhoCampoY, campo.campoLista, campo.coordenadasBombas);
    }
}


