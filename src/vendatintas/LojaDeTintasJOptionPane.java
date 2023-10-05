package vendatintas;

import javax.swing.JOptionPane;
import java.text.DecimalFormat;

public class LojaDeTintasJOptionPane {

    public static void main(String[] args) {
        String areaStr = JOptionPane.showInputDialog("Informe o tamanho da área a ser pintada (m²):");

        try {
            double area = Double.parseDouble(areaStr);
            calcularTintas(area);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um valor válido para a área.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void calcularTintas(double area) {
        double litrosNecessarios = area * 1.1 / 6.0;
        int latas18L = (int) Math.ceil(litrosNecessarios / 18.0);
        int galoes36L = (int) Math.ceil(litrosNecessarios / 3.6);
        int[] misturaLatasGaloes = calcularMisturaOptima(litrosNecessarios);

        DecimalFormat df = new DecimalFormat("0.##");

        StringBuilder resultado = new StringBuilder();
        resultado.append("Comprar apenas latas de 18 litros: ");
        resultado.append(latas18L);
        resultado.append(" lata(s) por R$ ");
        resultado.append(latas18L * 80);
        resultado.append("\n");

        resultado.append("Comprar apenas galões de 3,6 litros: ");
        resultado.append(galoes36L);
        resultado.append(" galão(ões) por R$ ");
        resultado.append(galoes36L * 25);
        resultado.append("\n");

        resultado.append("Misturar latas e galões (");
        resultado.append(misturaLatasGaloes[0]);
        resultado.append(" lata(s) e ");
        resultado.append(misturaLatasGaloes[1]);
        resultado.append(" galão(ões)): R$ ");
        resultado.append((misturaLatasGaloes[0] * 80 + misturaLatasGaloes[1] * 25));
        resultado.append("\n");

        JOptionPane.showMessageDialog(null, resultado.toString(), "Resultados", JOptionPane.INFORMATION_MESSAGE);
    }

    private static int[] calcularMisturaOptima(double litrosNecessarios) {
        int[] resultado = new int[2];
        double litrosFolga = litrosNecessarios * 0.1; // 10% de folga

        // Tente comprar apenas latas
        int latas = (int) Math.ceil(litrosNecessarios / 18.0);
        double sobraLatas = (latas * 18) - litrosNecessarios;

        // Tente comprar apenas galões
        int galoes = (int) Math.ceil(litrosNecessarios / 3.6);
        double sobraGaloes = (galoes * 3.6) - litrosNecessarios;

        // Tente misturar latas e galões
        int latasMistura = (int) Math.floor(litrosNecessarios / 18.0);
        int galoesMistura = (int) Math.ceil((litrosNecessarios - (latasMistura * 18)) / 3.6);
        double sobraMistura = (latasMistura * 18 + galoesMistura * 3.6) - litrosNecessarios;

        if (sobraLatas < sobraGaloes && sobraLatas < sobraMistura) {
            resultado[0] = latas;
            resultado[1] = 0;
        } else if (sobraGaloes < sobraLatas && sobraGaloes < sobraMistura) {
            resultado[0] = 0;
            resultado[1] = galoes;
        } else {
            resultado[0] = latasMistura;
            resultado[1] = galoesMistura;
        }
        return resultado;
    }
}
