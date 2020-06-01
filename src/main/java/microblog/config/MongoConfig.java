//package microblog.config;
//
//import com.mongodb.MongoClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoClientFactoryBean;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//public class MongoConfig extends AbstractMongoConfiguration {
//
//    @Value("${mongo.dbname}")
//    private String databaseName;
//
//    @Value("${mongo.username}")
//    private String username;
//
//    @Value("${mongo.password}")
//    private String password;
//
//    @Value("${mongo.host}")
//    private String host;
//
//    @Value("${mongo.port}")
//    private int port;
//
//    @Bean
//    public MongoDbFactory mongoDbFactory() throws Exception {
//        UserCredentials userCredentials = new UserCredentials(username, password);
//        return new SimpleMongoDbFactory(mongo(), databaseName, userCredentials);
//    }
//
//    @Bean
//    public Mongo mongo() throws UnknownHostException {
//        return new MongoClient(host, port);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        return new MongoTemplate(mongoDbFactory());
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return databaseName;
//    }
