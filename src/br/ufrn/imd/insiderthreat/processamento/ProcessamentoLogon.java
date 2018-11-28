package br.ufrn.imd.insiderthreat.processamento;

import br.ufrn.imd.insiderthreat.model.Logon;

import java.util.HashMap;
import java.util.Map;

public class ProcessamentoLogon extends Processamento<Logon> {
    private static final String FILE = "log/logon.csv";
    private static final String DELIMITADOR = ",";

    public ProcessamentoLogon() {
        this(FILE);
    }

    private ProcessamentoLogon(String file) {
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
    protected Logon converter(Map<String, String> map) {
        Logon logon = new Logon();
        logon.setId(map.get("id"));
        logon.setData(map.get("data"));
        logon.setUsuario(map.get("usuario"));
        logon.setPc(map.get("pc"));
        logon.setAtividade(map.get("atividade"));

        return logon;
    }
}
