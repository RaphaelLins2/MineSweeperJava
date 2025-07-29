import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui {

    public static void setScreen(int campoTamanhoX, int campoTamanhoY, java.util.List<Cell> campo, java.util.List<Coordenada> listaBomba){
        //criando o frame da tela
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        JPanel containerPanel = new JPanel();

        //criando a grid
        buttonPanel.setLayout(new GridLayout(campoTamanhoX,campoTamanhoY));
        for (int i=0; i < (campoTamanhoX*campoTamanhoY); i++) {
            JButton acao = new JButton("");
            buttonPanel.add(acao);
            acao.setBackground(Color.BLACK);
            //escutando por inputs
            int finalI = i;
            acao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    campo.get(finalI).revelar(campo, listaBomba, campoTamanhoX, campoTamanhoY);
                    String numeroDeBombas = Integer.toString(campo.get(finalI).numBombasmet());
                    System.out.println("A quantidade de bombas perto desta célula é de: " + numeroDeBombas);
                    acao.setText(numeroDeBombas);
                    acao.setBackground(Color.GRAY);
                    switch (Integer.parseInt(numeroDeBombas)){
                        case 0:
                            acao.setForeground(Color.GRAY);
                        case 1:
                            acao.setForeground(Color.cyan);
                        case 2:
                            acao.setForeground(Color.GREEN);
                        case 3:
                            acao.setForeground(Color.RED);
                        case 4:
                            acao.setForeground(Color.blue);
                        case 5:
                            acao.setForeground(Color.red);
                        case 6:
                            acao.setForeground(Color.cyan);
                        case 7:
                            acao.setForeground(Color.DARK_GRAY);
                        case 8:
                            acao.setForeground(Color.BLACK);

                    }
                    if (campo.get(finalI).bomba){
                        acao.setBackground(Color.RED);
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


