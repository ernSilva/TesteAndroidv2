## Architecture

O projeto desenvolvido utilizando Clean + MVVM
- É usado LiveData para notificar as visualizações com todas atualizações 
- O teste de unidade usa ustilizei o mockito porque é muito fácil simular objetos e JUnit para fazer um isolamento de teste.

## Packages
O projeto é separado por três pacotes:
- Presentation: Fragmentos, Atividade etc. Frameworks do Android
- Data: UseCase e Models, com as regras de negócio
- Domain: Repository, DataSource and Services, com consumidores de dados locais e remotos