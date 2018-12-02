package br.ufrn.imd.insiderthreat.controle;

import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Align;

import java.util.*;

/**
 * @author claudio
 *
 * Para criar o ranking dos perfis dos usuários utilizando como métrica a distância euclidiana
 */
public class RankingDao {
    private Map<Usuario, Double> rankingMap;

    public RankingDao(){
        rankingMap = new HashMap<Usuario, Double>();
    }

    /**
     * @param usuario
     * @param distancia distância euclidiana do usuário
     */
    public void rankingAdd(Usuario usuario, Double distancia){
        rankingMap.put(usuario, distancia);
    }

    /**
     * Exibe o ranking dos perfis em ordem
     */
    public void imprimirRankingOrdenado(){
        Map<Usuario, Double> sortedMap = sortByValue(rankingMap);
        printMap(sortedMap);
    }

    /**
     * Imprime a distância euclidiana do conjunto de usuários
     * 
     * @param map Registro com a distância dos usuários a serem exibidas 
     */
    public static <K, V> void printMap(Map<K, V> map) {
        Align align = new Align();
        align.addLine("Id usuário", "Nome usuário", "Distância");
        for (Map.Entry<K, V> entry : map.entrySet()) {
            align.addLine(((Usuario)entry.getKey()).getId(), ((Usuario)entry.getKey()).getNome(), Double.toString((Double)entry.getValue()));
        }
        align.output((String s) -> System.out.println(s));
    }

    /**
     * Ordena o map por distância euclidiana que possibilitará ser impresso em ordem
     * 
     * @param unsortMap map a ser ordenado
     * @return map ordenado com base na distância euclidiana
     */
    private static Map<Usuario, Double> sortByValue(Map<Usuario, Double> unsortMap) {
        List<Map.Entry<Usuario, Double>> list =
                new LinkedList<Map.Entry<Usuario, Double>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Usuario, Double>>() {
            public int compare(Map.Entry<Usuario, Double> o1,
                               Map.Entry<Usuario, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<Usuario, Double> sortedMap = new LinkedHashMap<Usuario, Double>();
        for (Map.Entry<Usuario, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
    }

}
