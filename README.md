# Trabalho POO 2
## Visão Geral do Sistema
O sistema tem por objetivo auxiliar no ensino de teoria musical abordando assuntos técnicos de diferentes tipos de instrumentos, como: Acordes, campos harmônicos e escalas. O sistema tem como público-alvo pessoas interessadas em aprender teoria musical e professores que usariam o sistema como um instrumento de auxílio.

## Requisitos Funcionais
| Identificador | Descrição |Prioridade |
|-----------|-------|------------|
| RF01 | O sistema deve permitir a visualização das diferentes escalas, campos harmônicos e acordes.| Alta |
| RF02 | O sistema deve ter filtros para facilitar a busca.| Alta |
| RF03 | O sistema deve permitir a inclusão de progressões que o usuário criou.| Alta |
| RF04 | O sistema deve permitir a importação de progressões.| Alta |
| RF05 | O sistema deve permitir a exportação de progressões.| Alta |
| RF06 | O usuário pode buscar por diferentes acordes e escalas.| Alta |
| RF07 | O usuário pode favoritar um acorde ou escala.| Média |
| RF08 | O sistema pode exibir informações complementares sobre acordes e escala.| Média |
| RF09 | O usuário pode transpor notas.| Média |
| RF10 | O usuário pode inverter acordes.| Média |
| RF11 | O sistema pode reproduzir sonoramente as escalas, os acordes, as notas e as progressões.| Baixa |
| RF12 | O sistema deve reconhecer os dispositivos MIDI conectados.| Baixa |
| RF13 | O sistema pode se conectar com dispositivos MIDI.| Baixa |
| RF14 | O sistema pode reconhecer as notas tocadas em um teclado/piano conectado por USB.| Baixa |
| RF15 | O usuário pode fazer anotações.| Baixa | <!-- Alterar essa descrição?? -->
| RF16 | O usuário pode buscar informações sobre músicas já existentes.| Baixa |

## Requisitos Não Funcionais
| Identificador | Descrição                                                                                   | Tipo         |
|---------------|----------------------------------------------------------------------------------|--------------|
| RNF01         | O sistema deve ter um design responsivo, garantindo uma resposta em no máximo 3 segundos    | Desempenho   |
| RNF02         | O sistema deve ser compatível com Windows, macOS e Linux                                    | Compatibilidade |
| RNF03         | O sistema deve ter uma interface intuitiva                                                 | Produto      |
| RNF04         | O sistema deve oferecer suporte para múltiplos idiomas, como Português, Espanhol, Inglês e Mandarim | Produto      |
| RNF05         | O sistema deve estar disponível 24 horas por dia                                            | Disponibilidade |
| RNF06         | O sistema deve fazer backup das progressões do usuário a cada 3 dias                       | Confiabilidade |
| RNF07         | O sistema deve oferecer recursos de acessibilidade, como contraste ajustável               | Acessibilidade |
| RNF08         | O sistema deve permitir o uso offline do aplicativo                                         | Desempenho   |
| RNF09         | O sistema deve implementar as atualizações automaticamente                                 | Manutenção   |
| RNF10         | O sistema deve registrar e reportar erros em um log automaticamente                        | Manutenção   |
