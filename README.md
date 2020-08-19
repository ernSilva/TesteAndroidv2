# Bank Test App

## Run project

To execute the project open in Android Studio, the path /Bank that contains the application.
> Sync gradle and run on device

## Architecture

The project is develop using Clean + MVVM
- It's used LiveData for notify the views with updates
- The unit test use Mockito (I used mockito because it's so easy to mock objects) and JUnit to make a test isolade.

## Packages
The project is separated by three packages: 
- Presentation: Fragments, Activity etc. Android Framework
- Data: UseCase and Models, with the bussiness rules
- Domain: Repository, DataSource and Services, with local and remote data consumers
