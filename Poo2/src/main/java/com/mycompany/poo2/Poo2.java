package com.mycompany.poo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import model.*;
import repository.*;
import controller.*;
import service.*;

import repository.EscalaRepositorioMySQL;
import repository.AcordeRepositorioMySQL;
import repository.AnotacaoRepositorioMySQL;
import repository.ProgressaoRepositorioMySQL;
import repository.MusicaRepositorioMySQL; // Importar MusicaRepositorioMySQL

import controller.ConexaoBD; // Certifique-se de que esta classe existe e configura a conexão

public class Poo2 {

	public static void main(String[] args) {

		// Repositories
		// Recomenda-se que todos os repositórios sejam MySQL se você está usando um BD real
		ElementoMusicalRepositorio elementoRepo = new InMemoryElementoMusicalRepositorio(); // Mude para MySQL se ElementoMusical for para BD
		AcordeRepositorio acordeRepo = new AcordeRepositorioMySQL();
		MusicaRepositorio musicaRepo = new MusicaRepositorioMySQL(); // MUDANÇA: Usar MusicaRepositorioMySQL
		NotaRepositorio notaRepo = new InMemoryNotaRepositorio(); // Mude para MySQL se Nota for para BD

		EscalaRepositorio escalaRepo = new EscalaRepositorioMySQL();
		// AnotacaoRepositorioMySQL pode precisar de um ElementoMusicalRepositorioMySQL se depender de IDs reais do BD
		AnotacaoRepositorio anotacaoRepo = new AnotacaoRepositorioMySQL(elementoRepo);
		ProgressaoRepositorio progressaoRepo = new ProgressaoRepositorioMySQL(acordeRepo); 
																									
		// Services (ServicoBusca agora faz tudo para Música)
		ServicoBusca servicoBusca = new ServicoBusca(elementoRepo, acordeRepo, escalaRepo, musicaRepo, notaRepo); // Passar musicaRepo
		ServicoAnotacao servicoAnotacao = new ServicoAnotacao(anotacaoRepo, elementoRepo);
		ServicoProgressao servicoProgressao = new ServicoProgressao(progressaoRepo, acordeRepo); 
		// ServicoMusica servicoMusica = new ServicoMusica(musicaRepo); // LINHA REMOVIDA: ServicoMusica não existe mais
		
		// Controllers
		ControladorBusca controlador = new ControladorBusca(servicoBusca); // controlador é usado para buscar

		Scanner scanner = new Scanner(System.in);

		int opcaoMenuPrincipal = -1;
		while (opcaoMenuPrincipal != 0) {
			System.out.println("\n           MENU PRINCIPAL        ");
			System.out.println("---------------------------------------");
			System.out.println("|1. Gerenciar Escalas                 |");
			System.out.println("|2. Gerenciar Acordes                 |");
			System.out.println("|3. Gerenciar Anotações               |");
			System.out.println("|4. Gerenciar Progressões Harmônicas  |"); 
			System.out.println("|5. Gerenciar Músicas                 |"); // NOVO MENU
			System.out.println("|0. Sair da Aplicação                 |");
			System.out.println("---------------------------------------");
			System.out.print("Escolha uma opção:");

			try {
				opcaoMenuPrincipal = scanner.nextInt();
				scanner.nextLine(); // Consumir a nova linha

				switch (opcaoMenuPrincipal) {
				case 1:
					gerenciarEscalas(scanner, escalaRepo, controlador);
					break;
				case 2:
					gerenciarAcordes(scanner, acordeRepo, controlador);
					break;
				case 3:
					gerenciarAnotacoes(scanner, servicoAnotacao, servicoBusca);
					break;
				case 4: 
					gerenciarProgressoes(scanner, servicoProgressao);
					break;
				case 5: // NOVO CASE
					// MUDANÇA AQUI: passa servicoBusca, já que ServicoMusica foi incorporado
					gerenciarMusicas(scanner, servicoBusca, controlador); 
					break;
				case 0:
					System.out.println("Saindo da aplicação. Tchau!");
					break;
				default:
					System.out.println("Opção inválida. Por favor, tente novamente.");
				}
			} catch (java.util.InputMismatchException e) {
				System.err.println("Entrada inválida. Por favor, digite um número para a opção.");
				scanner.nextLine(); // Consumir a entrada inválida
			} catch (Exception e) {
				System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
				e.printStackTrace();
			}
		}
		scanner.close();
		System.out.println("\n--- APLICAÇÃO FINALIZADA ---");
	}

	// ==============================================================================
	// COLOQUE AQUI OS MÉTODOS gerenciarEscalas, gerenciarAcordes,
	// gerenciarAnotacoes, gerenciarProgressoes DO SEU CÓDIGO ORIGINAL.
	// E logo abaixo, o novo método gerenciarMusicas.
	// ==============================================================================

	// Seu método gerenciarEscalas original
	private static void gerenciarEscalas(Scanner scanner, EscalaRepositorio escalaRepo, ControladorBusca controlador) {
        int opcaoEscalas = -1;
        while (opcaoEscalas != 0) {
            System.out.println("\n--- MENU DE ESCALAS ---");
            System.out.println("1. Buscar Estrutura da Escala por Cifra Exata (ex: C, D)"); 
            System.out.println("2. Criar Nova Escala");
            System.out.println("3. Listar Todas as Escalas");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção para Escalas: ");

            try {
                opcaoEscalas = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcaoEscalas) {
                    case 1: 
                        System.out.print("Digite a cifra EXATA da escala (campo 'Nome' no BD) para ver sua estrutura: ");
                        String cifraExataBusca = scanner.nextLine();

                        // O método findEstruturaByNome no seu EscalaRepositorio só retorna Optional<String>
                        // Se você quiser buscar por nome (como em Acordes e Músicas), EscalaRepositorio precisa de um findByNameContaining
                        // ou um método que retorne Escala.
                        // Por ora, mantive o que você tinha.
                        Optional<String> estruturaOpt = escalaRepo.findEstruturaByNome(cifraExataBusca);
                        
                        if (estruturaOpt.isPresent()) {
                            System.out.println("\nEstrutura da escala '" + cifraExataBusca + "':");
                            System.out.println(estruturaOpt.get());
                        } else {
                            System.out.println("Nenhuma escala encontrada com o nome exato '" + cifraExataBusca + "'.");
                        }
                        break;
                    
                    case 2: 
                        System.out.println("\n--- Criar Nova Escala ---");
                        System.out.print("Informe a Estrutura da nova escala (ex: C-D-E-F-G-A-B): ");
                        String novaEstrutura = scanner.nextLine();
                        System.out.print("Informe o Tipo da nova escala (ex: Maior, Menor): ");
                        String novoTipo = scanner.nextLine();
                        System.out.print("Informe o Nome da nova escala (ex: C Maior): ");
                        String novoNome = scanner.nextLine();
                        
                        Escala novaEscala = new Escala(new ArrayList<>(), novaEstrutura, novoTipo, novoNome); 
                        
                        Escala escalaSalva = escalaRepo.save(novaEscala);
                        System.out.println("Nova escala criada e salva com sucesso! " + escalaSalva);
                        break;
                    
                    case 3: 
                        System.out.println("\n--- Todas as Escalas no Banco de Dados ---");
                        List<Escala> todasEscalas = escalaRepo.findAll();
                        if (todasEscalas.isEmpty()) {
                            System.out.println("Nenhuma escala encontrada no banco de dados.");
                        } else {
                            for (Escala e : todasEscalas) {
                                System.out.println(e);
                            }
                        }
                        break;

                    case 0:
                        System.out.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida para Escalas. Por favor, tente novamente.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado no menu de Escalas: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    

	// Seu método gerenciarAcordes original
	private static void gerenciarAcordes(Scanner scanner, AcordeRepositorio acordeRepo, ControladorBusca controlador) {
		int opcaoAcordes = -1;
		while (opcaoAcordes != 0) {
			System.out.println("\n--- MENU DE ACORDES ---");
			System.out.println("1. Buscar Acorde por Cifra e Tipo (ex: C Maior)");
			System.out.println("2. Listar Todos os Acordes");
			System.out.println("3. Criar Novo Acorde");
			System.out.println("0. Voltar ao Menu Principal");
			System.out.print("Escolha uma opção para Acordes: ");

			try {
				opcaoAcordes = scanner.nextInt();
				scanner.nextLine();

				switch (opcaoAcordes) {
				case 1:
					System.out.print("Digite a cifra do acorde (ex: C, Am, G#): ");
					String cifraAcorde = scanner.nextLine();
					System.out.print("Digite o tipo do acorde (ex: Maior, Menor, Diminuto): ");
					String tipoAcorde = scanner.nextLine();

					// Se o ServicoBusca tem um método específico, é melhor usar ele
					Optional<Acorde> acordeEncontradoOpt = ((ServicoBusca) controlador.servicoBusca).buscarAcordePorNomeETipo(cifraAcorde, tipoAcorde);
					// A linha abaixo pode ser removida se a busca específica for usada
					// Map<String, String> filtrosAcorde = Map.of("tipo", "acorde", "nome", cifraAcorde, "tipoAcorde",tipoAcorde);
					// List<?> resultadoAcorde = controlador.realizarBusca(filtrosAcorde);


					if (acordeEncontradoOpt.isPresent()) { // !resultadoAcorde.isEmpty() && resultadoAcorde.get(0) instanceof Acorde
						Acorde acordeEncontrado = acordeEncontradoOpt.get(); // (Acorde) resultadoAcorde.get(0);
						System.out.println("\nAcorde encontrado:");
						System.out.println(acordeEncontrado);
						System.out.println("Notas do acorde: " + acordeEncontrado.getInfoComplementar());
					} else {
						System.out.println("Acorde '" + cifraAcorde + " " + tipoAcorde + "' não encontrado.");
					}
					break;

				case 2:
					System.out.println("\n--- Todos os Acordes no Banco de Dados ---");
					List<Acorde> todosAcordes = acordeRepo.findAll();
					if (todosAcordes.isEmpty()) {
						System.out.println("Nenhum acorde encontrado no banco de dados.");
					} else {
						for (Acorde a : todosAcordes) {
							System.out.println(a);
							System.out.println("  " + a.getInfoComplementar());
						}
					}
					break;
				case 3:
					System.out.println("\n--- Criar Novo Acorde ---");
					System.out.print("Informe a Cifra do novo acorde (ex: C, G, Am): ");
					String novoAcordeCifra = scanner.nextLine();
					System.out.print("Informe o Tipo do novo acorde (ex: Maior, Menor, Diminuto): ");
					String novoAcordeTipo = scanner.nextLine();
					System.out.print("Informe as notas do acorde separadas por hífen (ex: C-E-G): ");
					String novasNotasStr = scanner.nextLine();

					Nota tonicaAcorde = null;
					List<Nota> notasAcorde = new ArrayList<>();
					if (novasNotasStr != null && !novasNotasStr.isEmpty()) {
						String[] notasArray = novasNotasStr.split("-");
						if (notasArray.length > 0) {
							tonicaAcorde = new Nota(notasArray[0], null, 0);
						}
						for (String notaStr : notasArray) {
							notasAcorde.add(new Nota(notaStr, null, 0));
						}
					}

					Acorde novoAcorde = new Acorde(novoAcordeTipo, tonicaAcorde, novoAcordeCifra, notasAcorde);

					Acorde acordeSalvo = acordeRepo.save(novoAcorde);
					System.out.println("Novo acorde criado e salvo com sucesso! " + acordeSalvo);
					System.out.println("  " + acordeSalvo.getInfoComplementar());
					break;

				case 0:
					System.out.println("Voltando ao Menu Principal.");
					break;
				default:
					System.out.println("Opção inválida para Acordes. Por favor, tente novamente.");
				}
			} catch (java.util.InputMismatchException e) {
				System.err.println("Entrada inválida. Por favor, digite um número.");
				scanner.nextLine();
			} catch (Exception e) {
				System.err.println("Ocorreu um erro inesperado no menu de Acordes: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// Seu método gerenciarAnotacoes original
	private static void gerenciarAnotacoes(Scanner scanner, ServicoAnotacao servicoAnotacao,
			ServicoBusca servicoBusca) {
		int opcaoAnotacoes = -1;
		while (opcaoAnotacoes != 0) {
			System.out.println("\n--- MENU DE ANOTAÇÕES ---");
			System.out.println("1. Criar Nova Anotação");
			System.out.println("2. Associar Anotação a um Elemento Musical");
			System.out.println("3. Buscar Anotações por ID de Elemento Musical");
			System.out.println("4. Listar Todas as Anotações");
			System.out.println("5. Editar Anotação");
			System.out.println("6. Excluir Anotação");
			System.out.println("0. Voltar ao Menu Principal");
			System.out.print("Escolha uma opção para Anotações: ");

			try {
				opcaoAnotacoes = scanner.nextInt();
				scanner.nextLine();

				switch (opcaoAnotacoes) {
				case 1:
					System.out.print("Digite o texto da nova anotação: ");
					String textoAnotacao = scanner.nextLine();
					Anotacao novaAnotacao = servicoAnotacao.criarAnotacao(textoAnotacao);
					System.out.println("Anotação criada: " + novaAnotacao);
					break;
				case 2:
					System.out.print("Digite o ID da anotação a ser associada: ");
					int idAnotacao = scanner.nextInt();
					System.out.print("Digite o ID do Elemento Musical (ex: ID da escala ou acorde): ");
					int idElemento = scanner.nextInt();
					scanner.nextLine();
					servicoAnotacao.associarAnotacao(idAnotacao, idElemento);
					System.out.println(
							"Anotação " + idAnotacao + " associada ao Elemento " + idElemento + " com sucesso!");
					break;
				case 3:
					System.out.print("Digite o ID do Elemento Musical para buscar anotações: ");
					int idElementoBusca = scanner.nextInt();
					scanner.nextLine();
					List<Anotacao> anotacoesEncontradas = servicoAnotacao.buscarAnotacoesPorEntidade(idElementoBusca);
					if (anotacoesEncontradas.isEmpty()) {
						System.out.println(
								"Nenhuma anotação encontrada para o Elemento Musical de ID " + idElementoBusca + ".");
					} else {
						System.out.println("Anotações para o Elemento Musical de ID " + idElementoBusca + ":");
						for (Anotacao a : anotacoesEncontradas) {
							System.out.println(a);
						}
					}
					break;
				case 4:
					System.out.println("\n--- Todas as Anotações no Banco de Dados ---");
					List<Anotacao> todasAnotacoes = servicoAnotacao.buscarTodasAnotacoes();
					if (todasAnotacoes.isEmpty()) {
						System.out.println("Nenhuma anotação encontrada.");
					} else {
						for (Anotacao a : todasAnotacoes) {
							System.out.println(a);
						}
					}
					break;
				case 5:
					System.out.print("Digite o ID da anotação para editar: ");
					int idEditar = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Digite o novo texto para a anotação: ");
					String novoTexto = scanner.nextLine();
					Anotacao anotacaoEditada = servicoAnotacao.editarTextoAnotacao(idEditar, novoTexto);
					System.out.println("Anotação atualizada: " + anotacaoEditada);
					break;
				case 6:
					System.out.print("Digite o ID da anotação para excluir: ");
					int idExcluir = scanner.nextInt();
					scanner.nextLine();
					servicoAnotacao.excluirAnotacao(idExcluir);
					System.out.println("Anotação " + idExcluir + " excluída com sucesso!");
					break;
				case 0:
					System.out.println("Voltando ao Menu Principal.");
					break;
				default:
					System.out.println("Opção inválida para Anotações. Por favor, tente novamente.");
				}
			} catch (java.util.InputMismatchException e) {
				System.err.println("Entrada inválida. Por favor, digite um número.");
				scanner.nextLine();
			} catch (Exception e) {
				System.err.println("Ocorreu um erro inesperado no menu de Anotações: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// Seu método gerenciarProgressoes original
	private static void gerenciarProgressoes(Scanner scanner, ServicoProgressao servicoProgressao) {
		int opcaoProgressoes = -1;
		while (opcaoProgressoes != 0) {
			System.out.println("\n--- MENU DE PROGRESSÕES HARMÔNICAS ---");
			System.out.println("1. Criar Nova Progressão");
			System.out.println("2. Adicionar Acorde à Progressão (por Cifra e Tipo)");
			System.out.println("3. Listar Todas as Progressões");
			System.out.println("4. Ver Detalhes de Progressão (por ID)");
			System.out.println("5. Excluir Progressão por ID");
			System.out.println("0. Voltar ao Menu Principal");
			System.out.print("Escolha uma opção para Progressões: ");

			try {
				opcaoProgressoes = scanner.nextInt();
				scanner.nextLine();

				switch (opcaoProgressoes) {
				case 1: 
					System.out.print("Informe o nome da nova progressão: ");
					String nomeProgressao = scanner.nextLine();
					Progressao novaProgressao = servicoProgressao.criarProgressao(nomeProgressao);
					System.out.println("Progressão criada: " + novaProgressao);
					break;
				case 2: 
					System.out.print("Digite o ID da progressão para adicionar o acorde: ");
					int idProgressaoAdd = scanner.nextInt();
					scanner.nextLine(); 
					System.out.print("Digite a cifra do acorde (ex: C, Am): ");
					String cifraAdd = scanner.nextLine();
					System.out.print("Digite o tipo do acorde (ex: Maior, Menor): ");
					String tipoAdd = scanner.nextLine();

					servicoProgressao.adicionarAcordePorCifraETipo(idProgressaoAdd, cifraAdd, tipoAdd);
					System.out.println("Acorde '" + cifraAdd + " " + tipoAdd + "' adicionado à progressão "
							+ idProgressaoAdd + ".");
					break;
				case 3: 
					System.out.println("\n--- Todas as Progressões no Banco de Dados ---");
					List<Progressao> todasProgressoes = servicoProgressao.listarProgressoes();
					if (todasProgressoes.isEmpty()) {
						System.out.println("Nenhuma progressão encontrada.");
					} else {
						for (Progressao p : todasProgressoes) {
							System.out.println(p);
						}
					}
					break;
				case 4: 
					System.out.print("Digite o ID da progressão para ver detalhes: ");
					int idProgressaoVer = scanner.nextInt();
					scanner.nextLine();
					Progressao progressaoDetalhes = servicoProgressao.carregarProgressao(idProgressaoVer);
					System.out.println("\nDetalhes da Progressão:");
					System.out.println(progressaoDetalhes);
					System.out.println(progressaoDetalhes.getInfoComplementar()); 
					break;
				case 5: 
					System.out.print("Digite o ID da progressão para excluir: ");
					int idExcluirProgressao = scanner.nextInt();
					scanner.nextLine();
					servicoProgressao.excluirProgressao(idExcluirProgressao);
					System.out.println("Progressão " + idExcluirProgressao + " excluída com sucesso!");
					break;
				case 0:
					System.out.println("Voltando ao Menu Principal.");
					break;
				default:
					System.out.println("Opção inválida para Progressões. Por favor, tente novamente.");
				}
			} catch (java.util.InputMismatchException e) {
				System.err.println("Entrada inválita. Por favor, digite um número.");
				scanner.nextLine();
			} catch (Exception e) {
				System.err.println("Ocorreu um erro inesperado no menu de Progressões: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// NOVO MÉTODO: gerenciarMusicas (este deve ser adicionado)
	private static void gerenciarMusicas(Scanner scanner, ServicoBusca servicoBusca, ControladorBusca controlador) {
		int opcaoMusicas = -1;
		while (opcaoMusicas != 0) {
			System.out.println("\n--- MENU DE MÚSICAS ---");
			System.out.println("1. Buscar Música por Título ou Artista");
			System.out.println("2. Inserir Nova Música");
			System.out.println("3. Atualizar Música Existente");
			System.out.println("4. Remover Música");
			System.out.println("5. Listar Todas as Músicas");
			System.out.println("0. Voltar ao Menu Principal");
			System.out.print("Escolha uma opção para Músicas: ");

			try {
				opcaoMusicas = scanner.nextInt();
				scanner.nextLine(); // Consumir a nova linha

				switch (opcaoMusicas) {
				case 1: // Buscar Música
					System.out.print("Digite o título ou artista para buscar: ");
					String queryBusca = scanner.nextLine();
					List<Musica> musicasEncontradas = servicoBusca.buscarMusicasPorTituloOuArtista(queryBusca);
					if (musicasEncontradas.isEmpty()) {
						System.out.println("Nenhuma música encontrada com '" + queryBusca + "'.");
					} else {
						System.out.println("\nMúsicas encontradas:");
						for (Musica m : musicasEncontradas) {
							System.out.println(m);
						}
					}
					break;
				case 2: // Inserir Nova Música
					System.out.println("\n--- Inserir Nova Música ---");
					System.out.print("Título: ");
					String novoTitulo = scanner.nextLine();
					System.out.print("Artista: ");
					String novoArtista = scanner.nextLine();
					System.out.print("Ano: ");
					int novoAno = scanner.nextInt();
					scanner.nextLine(); // Consumir a nova linha
					System.out.print("Tônica: ");
					String novaTonica = scanner.nextLine();

					Musica musicaCriada = servicoBusca.criarMusica(novoTitulo, novoArtista, novoAno, novaTonica);
					if (musicaCriada != null) {
						System.out.println("Música criada com sucesso: " + musicaCriada);
					} else {
						System.out.println("Falha ao criar música.");
					}
					break;
				case 3: // Atualizar Música Existente
					System.out.println("\n--- Atualizar Música ---");
					System.out.print("Digite o ID da música para atualizar: ");
					int idAtualizar = scanner.nextInt();
					scanner.nextLine(); // Consumir a nova linha

					Optional<Musica> musicaParaAtualizarOpt = servicoBusca.buscarMusicaPorId(idAtualizar);
					if (musicaParaAtualizarOpt.isPresent()) {
						Musica musicaAtual = musicaParaAtualizarOpt.get();
						System.out.println("Música atual: " + musicaAtual);

						System.out.print("Novo Título (deixe em branco para manter '" + musicaAtual.getTitulo() + "'): ");
						String updateTitulo = scanner.nextLine();
						if (updateTitulo.isEmpty()) {
							updateTitulo = musicaAtual.getTitulo();
						}

						System.out.print("Novo Artista (deixe em branco para manter '" + musicaAtual.getArtista() + "'): ");
						String updateArtista = scanner.nextLine();
						if (updateArtista.isEmpty()) {
							updateArtista = musicaAtual.getArtista();
						}

						System.out.print("Novo Ano (digite 0 para manter '" + musicaAtual.getAno() + "'): ");
						int updateAno = scanner.nextInt();
						scanner.nextLine(); // Consumir a nova linha
						if (updateAno == 0) {
							updateAno = musicaAtual.getAno();
						}

						System.out.print("Nova Tônica (deixe em branco para manter '" + musicaAtual.getTonica() + "'): ");
						String updateTonica = scanner.nextLine();
						if (updateTonica.isEmpty()) {
							updateTonica = musicaAtual.getTonica();
						}

						Musica musicaAtualizada = servicoBusca.atualizarMusica(idAtualizar, updateTitulo, updateArtista, updateAno, updateTonica);
						if (musicaAtualizada != null) {
							System.out.println("Música atualizada com sucesso: " + musicaAtualizada);
						}
					} else {
						System.out.println("Música com ID " + idAtualizar + " não encontrada.");
					}
					break;
				case 4: // Remover Música
					System.out.println("\n--- Remover Música ---");
					System.out.print("Digite o ID da música para remover: ");
					int idRemover = scanner.nextInt();
					scanner.nextLine(); // Consumir a nova linha
					servicoBusca.removerMusica(idRemover);
					break;
				case 5: // Listar Todas as Músicas
					System.out.println("\n--- Todas as Músicas no Banco de Dados ---");
					List<Musica> todasMusicas = servicoBusca.listarTodasMusicas();
					if (todasMusicas.isEmpty()) {
						System.out.println("Nenhuma música encontrada no banco de dados.");
					} else {
						for (Musica m : todasMusicas) {
							System.out.println(m);
						}
					}
					break;
				case 0:
					System.out.println("Voltando ao Menu Principal.");
					break;
				default:
					System.out.println("Opção inválida para Músicas. Por favor, tente novamente.");
				}
			} catch (java.util.InputMismatchException e) {
				System.err.println("Entrada inválida. Por favor, digite um número.");
				scanner.nextLine();
			} catch (Exception e) {
				System.err.println("Ocorreu um erro inesperado no menu de Músicas: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}