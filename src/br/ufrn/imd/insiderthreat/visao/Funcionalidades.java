package br.ufrn.imd.insiderthreat.visao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.ufrn.imd.insiderthreat.controle.ArvoreDao;
import br.ufrn.imd.insiderthreat.controle.Histograma;
import br.ufrn.imd.insiderthreat.model.Atributos;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

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

        System.out.println("Processando...");
        List<ArvoreModelo> arvoresFiltradas = new ArrayList(this.arvoreConfiguracoes.filtrarPorPapel(papel).values());

    	listarUsuarios(arvoresFiltradas);
    }

    private void listarUsuarios(List<ArvoreModelo> arvores){
        System.out.println("________________________________________________________________________________");
        if(arvores.isEmpty()){
            System.out.println("Não foram encontrados usuários que tenha atividades nesse periodo de tempo.");
        }else{
            for(ArvoreModelo arvoreUsuario : arvores){
                Usuario usuario = ((Usuario)arvoreUsuario.getValor());
                int qtdAtividades = 0;
                for(Arvore<Modelo> arvorePc : arvoreUsuario.getFilhos()){
                    qtdAtividades += arvorePc.getFilhos().size();
                }
                System.out.printf("Id: %s | Nome: %s | Papel: %s | Quantidade Atividades: %s \n", usuario.getId(), usuario.getNome(), usuario.getPapel(), qtdAtividades);

            }
        }

    }
    
    public void listarUsuarios() {
        List<ArvoreModelo> arvores = new ArrayList<ArvoreModelo>(this.arvoreConfiguracoes.getUsuariosArvore().values());
    	listarUsuarios(arvores);
    }
    
    

    // TODO: remover: apenas para testes
    public void test1() {
        System.out.println("________________________________________________________________________________");
        System.out.println("TESTE IMPRIMIR 2 HISTOGRAMAS");
        System.out.println("Processando...");

        List<ArvoreModelo> arvoresFiltradas = new ArrayList<>();
        arvoresFiltradas.add(this.arvoreConfiguracoes.getUsuariosArvore().get("DTAA/RES0962"));
        arvoresFiltradas.add(this.arvoreConfiguracoes.getUsuariosArvore().get("DTAA/BJC0569"));

    	listarUsuarios(arvoresFiltradas);

    	ArvoreModelo arvoreTest1 = arvoresFiltradas.get(0);
    	ArvoreModelo arvoreTest2 = arvoresFiltradas.get(1);
        imprimirArvore(arvoreTest1);
        //imprimirArvore(arvoreTest2);
        
		try {
			Histograma h1 = new Histograma(arvoreTest1);
			Histograma h2 = new Histograma(arvoreTest2);
			Histograma h3 = new Histograma(arvoresFiltradas);
			
			System.out.println("Histograma dos perfis dos 2 usuários:");
			h1.imprimir();
			h2.imprimir();

			System.out.println("Histograma da média do perfil dos 2 usuários:");
			h3.imprimir();
			System.out.println("Distância " + h1.calcularDistancia(h3));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // TODO: remover: apenas para testes
    public void imprimirArvore(Arvore<Modelo> arvore) {
    	 Modelo valor = arvore.getValor();
    	 if (valor instanceof Atributos) {
    		 Atributos atributo = (Atributos) valor;
    		 System.out.println(atributo.getId() + " | " + atributo.getData() + " | " + atributo.getPc());
    	 }

    	 if (arvore.getFilhos().isEmpty()) {
    		 return;
    	 }
    	 
    	 for (Arvore<Modelo> arvoreTemp : arvore.getFilhos()) {
    		 imprimirArvore(arvoreTemp);
    	 }
    }

}
