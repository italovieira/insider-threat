package br.ufrn.imd.insiderthreat.excecao;

/**
 * @author claudio
 *
 * Exception para ser lançada quando a data final do intervalo de tempo é menor que a inicial
 */
public class DataValidacaoInicioFimException extends Exception{
    public String getMessage(){
        return "Erro: A data final informada é anterior a data inicial";
    }
}
