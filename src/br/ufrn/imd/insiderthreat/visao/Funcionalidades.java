package br.ufrn.imd.insiderthreat.visao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import br.ufrn.imd.insiderthreat.controle.ArvoreDao;
import br.ufrn.imd.insiderthreat.controle.AtividadeDao;
import br.ufrn.imd.insiderthreat.controle.HistogramaDao;
import br.ufrn.imd.insiderthreat.controle.RankingDao;
import br.ufrn.imd.insiderthreat.excecao.DataValidacaoInicioFimException;
import br.ufrn.imd.insiderthreat.model.Atividade;
import br.ufrn.imd.insiderthreat.model.Modelo;
import br.ufrn.imd.insiderthreat.model.Usuario;
import br.ufrn.imd.insiderthreat.util.Align;
import br.ufrn.imd.insiderthreat.util.Arvore;
import br.ufrn.imd.insiderthreat.util.ArvoreModelo;

/**
 * @author claudio
 *
 * Responsável por exibir a interação com o operador do sistema
 * Faz uso das outras classes para realizar os comandos principais do sistema
 */
public class Funcionalidades {

    private ArvoreDao arvoreConfiguracoes;

    public Funcionalidades(){
        this.arvoreConfiguracoes = new ArvoreDao();
    }

    /**
     * Exibe o menu inicial do sistema, pelo qual o operador irá "navegar" pelas opções 
     */
    public void iniciarAplicacao() throws InputMismatchException  {
        Scanner entradaDeDados = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("________________________________________________________________________________");
            System.out.println("<<FUNCIONALIDADES>>");
            System.out.println("<<USUÁRIOS, SUAS ATIVIDADES E HISTOGRAMAS>>");
            System.out.println("[1] - Buscar usuário em determinado periodo \n");
            System.out.println("<<USUÁRIOS E PAPEIS>>");
            System.out.println("[2] - Verificar perfil de um usuário");
            System.out.println("[3] - Buscar usuários de determinado papel \n");
            System.out.println("<<OUTRAS OPÇÕES>>");
            System.out.println("[0] - Sair");
            System.out.print("Informe escolha: ");

            opcao = entradaDeDados.nextInt();
            switch (opcao) {
                case 1:
                    try{
                        this.buscarUsuariosPorPeriodo();
                    }catch (DateTimeParseException e){
                        System.err.println("Erro: Data inválida.");
                    }catch (DataValidacaoInicioFimException ex){
                        System.err.println(ex.getMessage());
                    }
                    break;
                case 2:
                    this.buscarUsuariosPorIdPerfil();
                    break;
                case 3:
                    this.buscarUsuariosPorPapelPerfil();
                    break;
                case 0:
                default:
                    System.exit(0);
                    break;
            }
        } while (opcao != 0);

    }

    /**
     * Submenu acionado com algumas opções de navegação do menu principal
     */
    public void subMenuFuncionalidades() throws InputMismatchException{
        int opcao;
        do {
            System.out.println("________________________________________________________________________________");
            System.out.println("<<USUÁRIOS SUAS ATIVIDADES E HISTOGRAMAS>>");
            System.out.println("[1] - Buscar usuário da listagem e exibir média de atividades de acordo com o papel");
            System.out.println("[2] - Buscar usuário da listagem e exibir histograma de acordo com o papel");
            System.out.println("[3] - Buscar usuários de determinado papel");
            System.out.println("[4] - Apresentar lista de usuários novamente \n");
            System.out.println("<<OUTRAS OPÇÕES>>");
            System.out.println("[5] - Voltar ao menu inicial");
            System.out.println("[0] - Sair");
            System.out.print("Informe escolha: ");
            Scanner entradaDeDados = new Scanner(System.in);
            opcao = entradaDeDados.nextInt();

            switch (opcao){
                case 1:
                    this.mostrarMediaAtividadeUsuarioPapel();
                    break;
                case 2:
                    this.gerarHistogramaUsuarioPapel();
                    break;
                case 3:
                    this.buscarUsuariosPorPapel();
                    break;
                case 4:
                    this.listarUsuarios();
                    break;
                case 0:
                    System.exit(0);
                    break;
                case 5:
                    default:
                    break;
            }
        }while (opcao != 0 && opcao != 5);

    }

    /**
     * Mostra o perfil do usuário indicado
     */
    public void buscarUsuariosPorIdPerfil(){
        Scanner entradaDeDados = new Scanner(System.in);
        System.out.println("________________________________________________________________________________");
        System.out.println("BUSCAR DETERMINADO USUÁRIO:");
        System.out.print("Informe o id do usuário:");
        String id = entradaDeDados.nextLine();

        System.out.println("Processando...");

        this.arvoreConfiguracoes.criarArvorePerfisUsuarios("id", id);
        List<ArvoreModelo> arvores = new ArrayList<ArvoreModelo>(this.arvoreConfiguracoes.getUsuariosArvore().values());
        this.mostrarPerfilUsuario(arvores);
    }

    /**
     * Mostra os perfis dos usuários que desempenham o papel indicado
     */
    public void buscarUsuariosPorPapelPerfil(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("________________________________________________________________________________");
        System.out.println("BUSCAR USUÁRIOS POR PAPEL");
        System.out.print("Informe o papel: ");
        String papel = scanner.nextLine();

        System.out.println("Processando...");
        this.arvoreConfiguracoes.criarArvorePerfisUsuarios("papel", papel);
        List<ArvoreModelo> arvores = new ArrayList<ArvoreModelo>(this.arvoreConfiguracoes.getUsuariosArvore().values());
        this.mostrarPerfilUsuario(arvores);
    }

    /**
     * Mostra os perfis dos usuários da floresta de árvores
     * @param arvores floresta de árvores dos usuários
     */
    private void mostrarPerfilUsuario(List<ArvoreModelo> arvores){
        System.out.println("________________________________________________________________________________");
        if(arvores.isEmpty()){
            System.out.println("Não foram encontrados usuários.");
        }else{
            System.out.println("LISTAGEM DE USUÁRIOS");
            Align align = new Align();
            // header
            align.addLine("Id", "Nome", "Dominio", "Email", "Papel");
            for(ArvoreModelo arvoreUsuario : arvores){
                Usuario usuario = ((Usuario)arvoreUsuario.getValor());
                // data
                align.addLine(usuario.getId(), usuario.getNome(), usuario.getDominio(), usuario.getEmail(), usuario.getPapel());
            }
            align.output((String s) -> System.out.println(s));
        }

    }

    /**
     * Exibe os usuários que possuem atividades no intervalo de tempo indicado pelo operador e faz uma chamada ao submenu para dar continuidade aos comandos do operador 
     */
    public void buscarUsuariosPorPeriodo() throws DateTimeParseException, DataValidacaoInicioFimException {
        try {
            Scanner entradaDeDados = new Scanner(System.in);
            System.out.println("________________________________________________________________________________");
            System.out.println("FILTRAR USUÁRIOS EM DETERMINADO PERÍODO");
            System.out.print("Informe a data de inicio (DD/MM/AAAA): ");
            String dataInicial = entradaDeDados.nextLine();

            System.out.print("Informe a data de Fim: (DD/MM/AAAA): ");
            String dataFinal = entradaDeDados.nextLine();

            //TODO adicionar um try catch para validação das datas
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataIni = LocalDate.parse(dataInicial, formato);
            LocalDate dataFin = LocalDate.parse(dataFinal, formato);

            //verifica se a data fim é maior que a data inicio
            if(dataIni.compareTo(dataFin) == 1){
                throw new DataValidacaoInicioFimException();
            }

            System.out.println("Processando...");

            this.arvoreConfiguracoes.criarArvoreUsuariosComFiltro(dataIni, dataFin);

            this.listarUsuarios();
            if(!this.arvoreConfiguracoes.getUsuariosArvore().isEmpty()) {
                this.subMenuFuncionalidades();
            }
        }catch (InputMismatchException e){
            System.err.println("Erro: O número digitado não corresponde a um inteiro.");
            System.exit(0);
        }
    }

    /**
     * Mostra alguns quantitativos relacionados ao usuário escolhido e aos usuários do mesmo papel no geral 
     */
    public void mostrarMediaAtividadeUsuarioPapel(){
        Scanner entradaDeDados = new Scanner(System.in);
        System.out.println("________________________________________________________________________________");
        System.out.println("BUSCAR DETERMINADO USUÁRIO DA LISTAGEM ACIMA:");
        System.out.print("Informe o id do usuário:");
        String id = entradaDeDados.nextLine();

        System.out.println("Processando...");

        AtividadeDao atividadesUtilitarios = new AtividadeDao();
        atividadesUtilitarios.mediaTarefasUsuarioPapel(this.arvoreConfiguracoes.getUsuariosArvore(), id);
        if(atividadesUtilitarios.getUsuario() != null) {
            Align align = new Align();
            align.addLine("Papel:", atividadesUtilitarios.getUsuario().getPapel());
            align.output((String s) -> System.out.println(s));
            Align align1 = new Align();
            align1.addLine("Usuário Analisado:", atividadesUtilitarios.getUsuario().getNome(), "Quantidade de atividades:", Integer.toString(atividadesUtilitarios.getQuantidadeTarefasUsuario()));
            align1.output((String s) -> System.out.println(s));
            Align align2 = new Align();
            align2.addLine("Quantidade de Usuários do papel: ", Integer.toString(atividadesUtilitarios.getTotalUsuariosPapel()), "Quantidade de atividades: ", Integer.toString(atividadesUtilitarios.getQuantidadetarefasUsuariosPapel()), "Média de atividades por usuário: ", Integer.toString(atividadesUtilitarios.getQuantidadetarefasUsuariosPapel() / atividadesUtilitarios.getTotalUsuariosPapel()));
            align2.output((String s) -> System.out.println(s));
        }else{
            System.out.println("________________________________________________________________________________");
            System.out.println("Não foi encontrado nenhum usuário com o Id informado.");
        }
    }
    
    /**
     * Filtra os usuários que desempenham o papel indicado e os exibe
     */
    public void buscarUsuariosPorPapel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("________________________________________________________________________________");
        System.out.println("FILTRAR USUÁRIOS DA LISTAGEM POR PAPEL");
        System.out.print("Informe o papel: ");
        String papel = scanner.nextLine();

        System.out.println("Processando...");
        List<ArvoreModelo> arvoresFiltradas = this.arvoreConfiguracoes.filtrarPorPapel(papel);

        listarUsuarios(arvoresFiltradas);
    }

    /**
     * Lista os usuários da floresta de árvores indicada
     * @param arvores floresta de árvores representada como uma lista
     */
    private void listarUsuarios(List<ArvoreModelo> arvores){
        System.out.println("________________________________________________________________________________");
        if(arvores.isEmpty()){
            System.out.println("Não foram encontrados usuários que tenha atividades nesse periodo de tempo.");
        }else{
            Align align = new Align();
            // header
            align.addLine("Id", "Nome", "Papel", "Quantidade Atividades");
            for(ArvoreModelo arvoreUsuario : arvores){
                Usuario usuario = ((Usuario)arvoreUsuario.getValor());
                int qtdAtividades = 0;
                for(Arvore<Modelo> arvorePc : arvoreUsuario.getFilhos()){
                    qtdAtividades += arvorePc.getFilhos().size();
                }
                align.addLine(usuario.getId(), usuario.getNome(), usuario.getPapel(), Integer.toString(qtdAtividades));

            }

            align.output((String s) -> System.out.println(s));
        }

    }
    
    /**
     * Lista os usuários a partir da floresta de árvores
     */
    public void listarUsuarios() {
        List<ArvoreModelo> arvores = new ArrayList<ArvoreModelo>(this.arvoreConfiguracoes.getUsuariosArvore().values());
    	listarUsuarios(arvores);
    }

    /**
     * A partir da floresta de árvores mostra um comparativo do histograma do usuário escolhido e dos usuários que desempenham o mesmo papel
     */
    public void gerarHistogramaUsuarioPapel(){
        Scanner entradaDeDados = new Scanner(System.in);
        System.out.println("________________________________________________________________________________");
        System.out.println("BUSCAR DETERMINADO USUÁRIO DA LISTAGEM ACIMA:");
        System.out.print("Informe o id do usuário:");
        String id = entradaDeDados.nextLine();

        System.out.println("Processando...\n");

        List<ArvoreModelo> arvoresFiltradas = new ArrayList<>();
        arvoresFiltradas.add(this.arvoreConfiguracoes.getUsuariosArvore().get("DTAA/" + id));

        if(arvoresFiltradas.get(0) == null){
            System.out.println("________________________________________________________________________________");
            System.out.println("Não foram encontrados usuários com o id informado");
        }else {
            System.out.println("HISTOGRAMA RESULTADO:\n");
            ArvoreModelo arvoreTest1 = arvoresFiltradas.get(0);
            try {
                //Map<Usuario, Double> rankingMap = new HashMap<Usuario, Double>();
                RankingDao rankingUsuarios = new RankingDao();

                HistogramaDao histogramaUsuario = new HistogramaDao(arvoreTest1);
                System.out.print("Histograma do usuário: ");
                System.out.println(((Usuario)arvoreTest1.getValor()).getNome());
                histogramaUsuario.imprimir();

                System.out.println("");
                System.out.println("histograma dos usuários pertentences ao papel: " + ((Usuario)arvoreTest1.getValor()).getPapel());
                List<ArvoreModelo> arvoreUsuariosPapel = this.arvoreConfiguracoes.filtrarPorPapel(((Usuario)arvoreTest1.getValor()).getPapel());
                HistogramaDao histogramaUsuariosPapel = new HistogramaDao(arvoreUsuariosPapel);
                for (ArvoreModelo arvore : arvoreUsuariosPapel) {
                    try {
                        HistogramaDao histogramaUsuarioDoPerfil = new HistogramaDao(arvore);
                        System.out.print("ID: " + ((Usuario)arvore.getValor()).getId() + " | ");
                        System.out.println("NOME: " + ((Usuario)arvore.getValor()).getNome());
                        histogramaUsuarioDoPerfil.imprimir();

                        //adiciona os usuários ao ranking
                        rankingUsuarios.rankingAdd(((Usuario)arvore.getValor()), histogramaUsuarioDoPerfil.calcularDistancia(histogramaUsuariosPapel));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("");
                System.out.println("Histograma do perfil médio dos usuários de mesmo papel: ");
                histogramaUsuariosPapel.imprimir();

                System.out.println("");
                System.out.println("Distância do usuário "+ ((Usuario)arvoreTest1.getValor()).getNome() + " para o perfil médio: " + histogramaUsuario.calcularDistancia(histogramaUsuariosPapel));

                System.out.println("");
                System.out.println("Ranking de usuários de acordo com a distancia:");
                rankingUsuarios.imprimirRankingOrdenado();

            } catch (Exception e) {
                e.printStackTrace();
            }



        }
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
			HistogramaDao h1 = new HistogramaDao(arvoreTest1);
			HistogramaDao h2 = new HistogramaDao(arvoreTest2);
			HistogramaDao h3 = new HistogramaDao(arvoresFiltradas);
			
			System.out.println("HistogramaDao dos perfis dos 2 usuários:");
			h1.imprimir();
			h2.imprimir();

			System.out.println("HistogramaDao da média do perfil dos 2 usuários:");
			h3.imprimir();
			System.out.println("Distância " + h1.calcularDistancia(h3));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void imprimirArvore(Arvore<Modelo> arvore) {
    	 Modelo valor = arvore.getValor();
    	 if (valor instanceof Atividade) {
    		 Atividade atributo = (Atividade) valor;
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
