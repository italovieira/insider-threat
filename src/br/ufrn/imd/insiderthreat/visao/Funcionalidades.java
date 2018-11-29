package br.ufrn.imd.insiderthreat.visao;
import br.ufrn.imd.insiderthreat.controle.ArvoreDao;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Funcionalidades {
    public void iniciarAplicacao(){
        System.out.println("INÍCIO DA APLICAÇÃO");
        System.out.println("1 - Buscar usuário em determinado periodo");
        System.out.println("2 - Verificar dados de um usuário específico");
        System.out.println("0 - Sair");

        Scanner entradaDeDados = new Scanner(System.in);
        int opcao = entradaDeDados.nextInt();

        switch (opcao){
            case 1:
               this.buscarUsuariosPorPeriodo();
               break;
            case 0:
                default: ;
                break;
        }
    }

    public void buscarUsuariosPorPeriodo(){
        Scanner entradaDeDados = new Scanner(System.in);

        System.out.println("FILTRAGEM DOS LOGS");
        System.out.print("Informe a data de inicio (##/##/####): ");
        String dataInicial = entradaDeDados.nextLine();

        System.out.print("Informe a data de Fim: (##/##/####): ");
        String dataFinal = entradaDeDados.nextLine();

        //adicionar um try catch para validação das datas
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataIni = LocalDate.parse(dataInicial, formato);
        LocalDate dataFin = LocalDate.parse(dataFinal, formato);

        ArvoreDao arvoreConfiguracoes = new ArvoreDao();
        arvoreConfiguracoes.criarArvoreUsuariosComFiltro(dataIni, dataFin);

        System.out.println("Processando...");

    }
}
