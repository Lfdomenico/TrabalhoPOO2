package controller;

import model.Acorde;
import model.ElementoMusical;
import model.Nota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.*; // Importando todos os nossos serviços

import javax.sound.midi.MidiDevice;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControladorInteracaoTest {

    // 1. Mock para TODAS as dependências do controller
    @Mock private ServicoFavoritos servicoFavoritosMock;
    @Mock private ServicoAnotacao servicoAnotacaoMock;
    @Mock private ServicoAudio servicoAudioMock;
    @Mock private ServicoMidi servicoMidiMock;
    @Mock private ServicoBusca servicoBuscaMock;

    // 2. Injeta todos os mocks acima na instância real do controller
    @InjectMocks
    private ControladorInteracao controladorInteracao;

    private Acorde acordeExemplo;

    @BeforeEach
    void setUp() {
        // Criando um objeto de exemplo para ser usado nos testes
        acordeExemplo = new Acorde("Maior", new Nota("C", null, 4), "C", Collections.emptyList());
        acordeExemplo.setId(1);
    }

    @Test
    @DisplayName("Deve adicionar um favorito quando o item ainda não é favorito")
    void deveAdicionarFavorito() {
        // Dado (Given)
        // 1. Quando o serviço de busca procurar pelo ID 1, ele deve retornar nosso acorde de exemplo.
        when(servicoBuscaMock.buscarEntidadeMusical(1)).thenReturn(acordeExemplo);
        // 2. Quando o serviço de favoritos verificar se o acorde é favorito, ele deve retornar 'false'.
        when(servicoFavoritosMock.ehFavorito(acordeExemplo)).thenReturn(false);

        // Quando (When)
        controladorInteracao.lidarComToggleFavorito(1);

        // Então (Then)
        // Verificamos se o método 'adicionarFavorito' foi chamado no serviço correto.
        verify(servicoFavoritosMock, times(1)).adicionarFavorito(acordeExemplo);
        // E garantimos que o método 'removerFavorito' NÃO foi chamado.
        verify(servicoFavoritosMock, never()).removerFavorito(any());
    }

    @Test
    @DisplayName("Deve remover um favorito quando o item já é favorito")
    void deveRemoverFavorito() {
        // Dado (Given)
        when(servicoBuscaMock.buscarEntidadeMusical(1)).thenReturn(acordeExemplo);
        // Agora, simulamos que o item JÁ É um favorito.
        when(servicoFavoritosMock.ehFavorito(acordeExemplo)).thenReturn(true);

        // Quando
        controladorInteracao.lidarComToggleFavorito(1);

        // Então
        verify(servicoFavoritosMock, times(1)).removerFavorito(acordeExemplo);
        verify(servicoFavoritosMock, never()).adicionarFavorito(any());
    }

    @Test
    @DisplayName("Deve chamar o serviço de áudio para tocar um elemento")
    void deveTocarElemento() {
        // Dado
        when(servicoBuscaMock.buscarEntidadeMusical(1)).thenReturn(acordeExemplo);

        // Quando
        controladorInteracao.lidarComTocarElemento(1);

        // Então
        // Verificamos se o método 'reproduzir' do serviço de áudio foi chamado
        // com o objeto Acorde correto.
        verify(servicoAudioMock, times(1)).reproduzir(acordeExemplo);
    }

    @Test
    @DisplayName("Deve chamar o serviço MIDI para iniciar a escuta")
    void deveIniciarEscutaMidi() {
        // Dado: Simulamos que existe um dispositivo MIDI disponível
        // Criamos um 'dublê' de MidiDevice.Info para o teste
        MidiDevice.Info dispositivoInfoMock = mock(MidiDevice.Info.class);
        when(servicoMidiMock.listarDispositivosDeEntrada()).thenReturn(Map.of("Teclado Mock", dispositivoInfoMock));
        when(servicoMidiMock.conectarDispositivo(dispositivoInfoMock)).thenReturn(true);

        // Quando
        controladorInteracao.iniciarEscutaMidi();

        // Então
        // Verificamos se o controller tentou se conectar ao dispositivo encontrado
        verify(servicoMidiMock, times(1)).conectarDispositivo(dispositivoInfoMock);
        // E se ele se inscreveu para receber notas
        verify(servicoMidiMock, times(1)).onNotaRecebida(any());
    }
}