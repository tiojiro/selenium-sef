# Selenium SEF - Renovação Automática

### Descrição:
Simples programa que faz o login e verifica disponibilidade da Renovação Automática junto ao SEF de 2 em 2 horas.

### Informações técnicas:
Ao executar a classe Main é necessário passar 3 variáveis de ambiente:
USER, PASS, DOCUMENT (título de residência)

Verificar no console cada uma das tentativas, abaixo exemplo:
<br>[2024-12-04 18:00:02] Início
<br>[2024-12-04 18:00:07] Pagina carregada: Serviço de Estrangeiros e Fronteiras
<br>[2024-12-04 18:00:11] Login OK
<br>[2024-12-04 18:00:15] O título de residência que possui não permite...
<br>[2024-12-04 18:00:15] Fim

### Resultado:
Se encontrar alguma diferença na mensagem de erro, será exibido alguns alertas:
<br>PopUp: Com mensagem "VERIFICAR PÁGINA DO SEF!"
<br>Console: Com a mensagem "[2024-12-04 18:00:02] VERIFICAR PÁGINA DO SEF!"
<br>Sonoro: Se executado no Windows o audio "exclamation" será tocado.