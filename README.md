# ControleDeAcesso

API Spring Boot do Projeto Integrador. Os dados ficam no **Cloudflare D1** (banco `projetointegrador`),
acessado pela API REST do Cloudflare.

## Configuracao do banco

1. Copie `.env.example` para `.env` na raiz do projeto.
2. Preencha `CLOUDFLARE_API_TOKEN` com o token da conta (o `.env` nao e versionado).
3. Suba a aplicacao com `./mvnw spring-boot:run`. Na inicializacao e feito um `SELECT 1` para validar a conexao.

As variaveis tambem podem ser definidas como variaveis de ambiente do sistema.
O schema das tabelas esta em `db/schema.sql`.
