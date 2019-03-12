### Spring Test Layer
[@SpringBootTest vs @WebMvcTest](https://stackoverflow.com/questions/39865596/difference-between-using-mockmvc-with-springboottest-and-using-webmvctest)
[SpringBoot Testing](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications)

Spring provide several test annotation:
- @SpringBootTest: find @SpringBootApplication, load configuration, start with whole context
  - Usually conjunct with @AutoConfigureMockMvc and @AutoConfigureTestEntityManager to get core test bean
- @WebMvcTest: only load @Controller and included filters
  - Do unit test for controllers, like filter checking, parameters checking, exception return
  - You have to add mocked beans manually(with @MockBean) if controller have dependencies
- @DataJpaTest: start in-memory database, scan @Entity and auto transactional
  - @JdbcTest, @DataJdbcTest: only for jdbc
- ...: others, slicing testing by component/feature/layer

### Test Granularity
- Repository: Use @DataJpaTest, mainly check the entity's CURD and its relationships, also examine customized SQL
- Service: Unit test with mock object is ok, focus on logic
- Controller: Using @SpringBootTest to check the complete request/response is preferred. 
These called integration test should cover all business logic. But not all details will return to frontend.
So you can move other tests, like parameter validation/ authorization/ message convert, in to @WebMvcTest.
Like a unit test only for Request->Controller->Response.
- Filter: include in @WebMvcTest
- Entity: testing it with repositories

***Write less but necessary, do simple but meaningful.***

### Test Tools
- Junit & Spring-Boot-Test
- Mock & Stub, Assertion & Verify