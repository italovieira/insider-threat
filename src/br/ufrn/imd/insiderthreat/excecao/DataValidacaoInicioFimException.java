package br.ufrn.imd.insiderthreat.excecao;

public class DataValidacaoInicioFimException extends Exception{
    public String getMessage(){
        return "Erro: A data final informada é anterior a data inicial";
    }
}
