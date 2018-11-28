package br.ufrn.imd.insiderthreat.processamento;

import br.ufrn.imd.insiderthreat.model.Http;

import java.util.HashMap;
import java.util.Map;

public class processamentoHTTP extends Processamento<Http> {
    private static final String FILE = "log/http.csv";
    private static final String DELIMITADOR = ",";

    public processamentoHTTP() {
        this(FILE);
    }

    private processamentoHTTP(String file) {
        super(file);
    }

    @Override
    protected Map<String, String> processarLinha(String linha) {
        // TODO: Checar `atributos` e verificar se há mais ou menos atributos do que deviam
        // talvez devesse ser lançada exceções nesses casos
        String[] atributos = linha.split(DELIMITADOR);

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", atributos[0]);
        map.put("data", atributos[1]);
        map.put("usuario", atributos[2]);
        map.put("pc", atributos[3]);
        map.put("atividade", atributos[4]);

        return map;
    }

    @Override
    protected Http converter(Map<String, String> map) {
        Http http = new Http();
        http.setId(map.get("id"));
        http.setData(map.get("data"));
        http.setUsuario(map.get("usuario"));
        http.setPc(map.get("pc"));
        http.setAtividade(map.get("atividade"));

        return http;
    }
}
