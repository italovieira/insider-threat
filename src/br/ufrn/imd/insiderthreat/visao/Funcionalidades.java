package br.ufrn.imd.insiderthreat.visao;
import br.ufrn.imd.insiderthreat.controle.ArvoreDao;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Funcionalidades {

    private ArvoreDao arvoreConfiguracoes;

    public Funcionalidades(){
        this.arvoreConfiguracoes = new ArvoreDao();
    }

    public void iniciarAplicacao(){
        int opcao;
        do {
            System.out.println("________________________________________________________________________________");
            System.out.println("INÍCIO DA APLICAÇÃO");
            System.out.println("[1] - Buscar usuário em determinado periodo");
            System.out.println("[2] - Verificar dados de um usuário específico");
            System.out.println("[0] - Sair");
            System.out.print("Informe escolha: ");
            Scanner entradaDeDados = new Scanner(System.in);
            opcao = entradaDeDados.nextInt();

            switch (opcao){
                case 1:
                    this.buscarUsuariosPorPeriodo();
                    break;
                case 0:
                default: ;
                    break;
            }
        }while (opcao != 0);

    }

    public void buscarUsuariosPorPeriodo(){
        Scanner entradaDeDados = new Scanner(System.in);
        System.out.println("________________________________________________________________________________");
        System.out.println("FILTRAR USUÁRIOS EM DETERMINADO PERÍODO");
        System.out.print("Informe a data de inicio (##/##/####): ");
        String dataInicial = entradaDeDados.nextLine();

        System.out.print("Informe a data de Fim: (##/##/####): ");
        String dataFinal = entradaDeDados.nextLine();

        //TODO adicionar um try catch para validação das datas
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataIni = LocalDate.parse(dataInicial, formato);
        LocalDate dataFin = LocalDate.parse(dataFinal, formato);

        System.out.println("Processando...");

        this.arvoreConfiguracoes.criarArvoreUsuariosComFiltro(dataIni, dataFin);

        this.listarUsuarios();

    }
    
    public void buscarUsuariosPorPapel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("________________________________________________________________________________");
        System.out.println("FILTRAR USUÁRIOS POR PAPEL");
        System.out.print("Informe o papel: ");
        String papel = scanner.nextLine();

        //TODO adicionar um try catch para validação das datas
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // TODO: Lembrete: essas datas devem ser definidas pelo operador
        LocalDate dataIni = LocalDate.parse("01/01/2002", formato);
        LocalDate dataFin = LocalDate.parse("01/01/2012", formato);

        System.out.println("Processando...");

        this.arvoreConfiguracoes.criarArvoreUsuariosComFiltro(dataIni, dataFin);

    	List<ArvoreModelo> arvores = new ArrayList<>();

    	for (ArvoreModelo arvore : this.arvoreConfiguracoes.getUsuariosArvore()) {
    		Usuario usuario = (Usuario) arvore.getValor();
    		if (papel.equals(usuario.getPapel())) {
    			arvores.add(arvore);
    		}
    	}
    	
    	listarUsuarios(arvores);
    }


    private void listarUsuarios(List<ArvoreModelo> arvores){
        System.out.println("________________________________________________________________________________");
        if(arvores.isEmpty()){
            System.out.println("Não foram encontrados usuários que tenha atividades nesse periodo de tempo.");
        }else{
            for(ArvoreModelo arvoreUsuario : arvores){
                Usuario usuario = ((Usuario)arvoreUsuario.getValor());
                System.out.printf("Id: %s | Nome: %s | Papel: %s \n", usuario.getId(), usuario.getNome(), usuario.getPapel());

            }
        }

    }
    
    public void listarUsuarios() {
    	listarUsuarios(this.arvoreConfiguracoes.getUsuariosArvore());
    }
}
