## Gym Spring Security Application

### How to run
This application consists of two microservices: finaldemo and schedule. To run the finaldemo microservice successfully, follow these steps:

* Configure JDK 17 (Initially, I attempted to develop the application with JDK 21, but encountered issues during runtime).
* Set up a PostgreSQL relational database (Database name: 'finaldemo', Password: #####).
* To test REST API endpoints, access Swagger at: http://localhost:8080/swagger-ui/index.html#/

For schedule Microservice:

* Configure JDK 17.
* Utilize an H2 in-memory database (Username: sa, Password: baraka, database: schedule).
  
### Architecture of Application

#### Domain (Domain is a package which includes 5 entites of our model and couple of enum classes)

  * User
  * Trainee
  * Trainer
  * TrainingType
  * Training
  * Role(Enum)
  * Token
  * TokenType(Enum)

#### DTO (DTO is a package which includes our Request and Response dto inorder to hide our actual models, as we know our dto classes should me immutable class therefore they are implemented in record class type) 

#### Request

* StatusRequest
* ChangeLogin
* Login
* TraineeRegistrationRequest
* TraineeTrainingsRequest
* TrainerRegistrationRequest
* TrainerTrainingsRequest
* TrainingRequest
* UpdateTraineeRequest
* UpdateTrainerRequest

#### Response

* RegistrationResponse
* TraineeList
* TraineeProfileResponse
* TraineeTrainingsResponse
* TrainersList
* TrainerProfile
* TrainerTrainingsResponse
* UpdateTraineeResponse
* UpdateTrainerResponse

#### Repository (Repository is layer which stands between Database and Busnees logic layer. Implemented using Hibernate ORM using SessionFactory interface I executed crud operations on modules)

* UserRepository
* TraineeRepository
* TrainingRepository
* TrainingTypeRepository
* TrainerRepository
* TokenRepository

#### Service (Service layer which is localed between DAO and Controller. The main purpose of this layer is hide complaxity and busines logic of the request from client side. I mean Controller is for only handling request and return responce toward REST APIs. However, service classes is only for busines logic part)

* UserService
* TraineeService
* TrainerService
* TrainingService
* TrainingTypeService
* JwtService
* LogoutHandlerService

#### Utils (This folder basicly responsible for logger messages as well as validation of requests whether they are provided properly or not it checks for that)
* LoggingAspect
* ValidModule

#### Controller (Controller layer is for handling request from client side and return proper Response account condition of the result it provides proper status code and proper logger message about success or failer of operations to depeloper)

*  AuthenticationController
*  TraineeController
*  TrainerController
*  TrainingController
*  TrainingTypeController

#### Configuration (Well, this folder consistce of couple of configurations like swagger configuration with Authentication I mean with tokens, and cuple of beans for example passwordEncoder or AuthenticationManager and ect..., configuration for SecurityFilterChain in order to controler which endpoints permited all or has specific role smth like that, and last but not least filtering token checking for validations proccess.)

* ApplicationConfig
* JwtAuthenticationFilter
* OpenApiConfiguration
* SecurityConfiguration

### Required task for this module
* Implement separate Spring boot Application (Microservice).
* Application should implement REST endpoint to accept trainer's workload with follow 
  contract:
  1. Request
    1. Trainer Username
    2. Trainer First Name
    3. Trainer Last Name
    4. IsActive
    5. Training date
    6. Training duration
    7. Action Type (ADD/DELETE)
  2. Response
    1. 200 OK
* Implement service function corresponding to mentioned below REST endpoint. Service 
  should calculate as in-memory saved structure (in memory DB) trainer's monthly 
  summary of the provided trainings. The model should be the follow;
    1. Trainer Username
    2. Trainer First Name
    3. Trainer Last Name
    4. Trainer Status
    5. Years (List)
      1. Months (List) 
        1. Training summary duration
* Update Existing Main Microservice implementation to call Secondary Microservice 
  every time that new training added or deleted to the system.
* Implement discovery module according to guide Eureka Discovery Service.
* Use circuit breaker design pattern in your implementation.
* Implement Authorization − Bearer token for Microservices integration Use JWT token 
  implementation
* Two levels of logging should be implemented - transactions and each operation 
  transaction level - which endpoint was called, which request came and the service 
  response - 200 or error and response message + at this level, a transactionId is generated, 
  by which you can track all operations for this transaction the same transactionId can later 
  be passed to downstream services.

### Implementations of Required tasks

* Implemented a separate schedule microservice.
* Developed a REST API to store the given DTO into an H2 in-memory database whenever users attempt to create or delete training.
* Implemented a function that calculates a report about the working hours of a requested trainer.
* Updated and added several logics to the main microservice (finaldemo) to enable calling the second
  microservice (schedule) when creating a new training, canceling, or sending a request to read the schedule of     a trainer.
* Implemented a Discovery microservice to register both microservices.
* Implemented a circuit breaker pattern to break communication between the two microservices in the event of an abnormal flow of requests.
* Sent requests to the second microservice's endpoints through a secured Rest API. These endpoints require specific roles for access,
  utilizing JWT tokens as valid credentials. Hence, I deemed it unnecessary to * implement additional security systems for the second microservice.
* Developed logging messages, but encountered difficulty in reading the transactionId and adding it to the log messages, which was a requirement for this module.




### TEST Coverage
Unit tests are implemented except for configuration and reached to 83% linear test coverage which satisfies the condition.

