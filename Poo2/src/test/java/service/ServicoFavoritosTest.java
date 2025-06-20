package service;

import model.Acorde;
import model.ElementoMusical;
import model.Escala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ElementoMusicalRepositorio;
import repository.InMemoryElementoMusicalRepositorio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicoFavoritosTest {

    private ServicoFavoritos servicoFavoritos;
    private ElementoMusicalRepositorio elementoMusicalRepository;
    
    // Elementos que usaremos para testar, carregados do repositório
    private ElementoMusical acordeC;
    private ElementoMusical escalaG;

    @BeforeEach
    void setUp() {
        // 1. Criamos o repositório polimórfico, que já vem com dados de exemplo.
        elementoMusicalRepository = new InMemoryElementoMusicalRepositorio();
        
        // 2. Injetamos o repositório no serviço que vamos testar.
        servicoFavoritos = new ServicoFavoritos(elementoMusicalRepository);
        
        // 3. Carregamos alguns elementos para usar nos testes.
        acordeC = elementoMusicalRepository.findById(1).orElseThrow(); // Acorde "C"
        escalaG = elementoMusicalRepository.findById(2).orElseThrow(); // Escala "G Maior"
    }

    @Test
    @DisplayName("Deve adicionar um favorito e verificar o estado")
    void deveAdicionarEVerificarFavorito() {
        // Estado inicial: o acorde C não deve ser favorito
        assertFalse(servicoFavoritos.ehFavorito(acordeC), "Inicialmente, o acorde C não deve ser favorito.");
        
        // Quando
        servicoFavoritos.adicionarFavorito(acordeC);
        
        // Então
        assertTrue(servicoFavoritos.ehFavorito(acordeC), "Após adicionar, o acorde C deve ser favorito.");
        assertFalse(servicoFavoritos.ehFavorito(escalaG), "A escala G não foi adicionada, então não deve ser favorita.");
    }
    
    @Test
    @DisplayName("Deve remover um favorito corretamente")
    void deveRemoverFavorito() {
        // Dado: adicionamos um item como favorito
        servicoFavoritos.adicionarFavorito(acordeC);
        assertTrue(servicoFavoritos.ehFavorito(acordeC), "Garante que o acorde é favorito antes de remover.");

        // Quando
        servicoFavoritos.removerFavorito(acordeC);

        // Então
        assertFalse(servicoFavoritos.ehFavorito(acordeC), "Após remover, o acorde C não deve mais ser favorito.");
    }
    
    @Test
    @DisplayName("Deve listar todos os itens favoritados")
    void deveListarFavoritos() {
        // Dado: adicionamos dois itens diferentes aos favoritos
        servicoFavoritos.adicionarFavorito(acordeC);

        // Quando adicionamos o segundo item
        servicoFavoritos.adicionarFavorito(escalaG);
        
        // Então
        List<ElementoMusical> favoritos = servicoFavoritos.listarFavoritos();
        
        assertAll("Verifica a lista de favoritos",
            () -> assertNotNull(favoritos),
            () -> assertEquals(2, favoritos.size(), "A lista de favoritos deve conter 2 itens"),
            () -> assertTrue(favoritos.contains(acordeC), "A lista deve conter o Acorde C"),
            () -> assertTrue(favoritos.contains(escalaG), "A lista deve conter a Escala G")
        );
    }
    
    @Test
    @DisplayName("Não deve adicionar o mesmo favorito duas vezes")
    void naoDeveAdicionarDuplicatas() {
        // Quando
        servicoFavoritos.adicionarFavorito(acordeC);
        servicoFavoritos.adicionarFavorito(acordeC); // Tenta adicionar o mesmo item de novo
        
        // Então
        assertEquals(1, servicoFavoritos.listarFavoritos().size(), "Adicionar o mesmo item duas vezes deve resultar em apenas uma entrada.");
    }
    
    @Test
    @DisplayName("Deve lidar corretamente com itens nulos")
    void deveLidarComNulos() {
        // Testa se os métodos são robustos e não quebram ao receber null.
        assertDoesNotThrow(() -> servicoFavoritos.adicionarFavorito(null));
        assertDoesNotThrow(() -> servicoFavoritos.removerFavorito(null));
        assertFalse(servicoFavoritos.ehFavorito(null), "Verificar um item nulo deve sempre retornar falso.");
    }
}