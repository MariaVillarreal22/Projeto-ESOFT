# FIFA World Cup Manager

Projeto Maven Java para um sistema de informacao usado durante o Campeonato do Mundo de Futebol.

## Ecra principal

A primeira pantalla implementada e `FASES`, baseada no Figma `Projeto ES Copo do Mundo`.
A interface foi feita em Swing usando `com.intellij.uiDesigner.core.GridLayoutManager`, como base para as restantes pantallas.
Ela funciona como ponto de partida para as outras areas do sistema:

- Calendario de jogos.
- Classificacoes e estatisticas.
- Equipas, arbitros e estadios.
- Compra e gestao de bilhetes.
- Hoteis e deslocacoes das equipas.

## Estrutura Maven

- `pom.xml`: configuracao Maven do projeto.
- `src/main/java/pt/ipleiria/es/worldcup/Main.java`: ponto de entrada.
- `src/main/java/pt/ipleiria/es/worldcup/ui/MainFrame.java`: janela principal.
- `src/main/java/pt/ipleiria/es/worldcup/ui/MainScreen.java`: pantalla `FASES` com `GridLayoutManager`.
- `src/main/java/pt/ipleiria/es/worldcup/ui/AppTheme.java`: cores e fontes.

## Como executar

No IntelliJ IDEA, abre o projeto como Maven e executa a classe:

```text
pt.ipleiria.es.worldcup.Main
```

Tambem podes executar no Windows com duplo clique em:

```text
run-app.bat
```
