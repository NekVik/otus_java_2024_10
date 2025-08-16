package ru.otus.webserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.webserver.core.repository.DataTemplateHibernate;
import ru.otus.webserver.core.repository.HibernateUtils;
import ru.otus.webserver.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.webserver.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.webserver.crm.model.Address;
import ru.otus.webserver.crm.model.Client;
import ru.otus.webserver.crm.model.Phone;
import ru.otus.webserver.crm.service.DBServiceClient;
import ru.otus.webserver.crm.service.DbServiceClientImpl;
import ru.otus.webserver.web.dao.InMemoryUserDao;
import ru.otus.webserver.web.dao.UserDao;
import ru.otus.webserver.web.server.ClientsWebServer;
import ru.otus.webserver.web.server.ClientsWebServerWithFilterBasedSecurity;
import ru.otus.webserver.web.services.TemplateProcessor;
import ru.otus.webserver.web.services.TemplateProcessorImpl;
import ru.otus.webserver.web.services.UserAuthService;
import ru.otus.webserver.web.services.UserAuthServiceImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {

        var serviceClient = initDbAndGetClientService();

        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        ClientsWebServer usersWebServer = new ClientsWebServerWithFilterBasedSecurity(
                WEB_SERVER_PORT, authService, serviceClient, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static DBServiceClient initDbAndGetClientService() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations(true);

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        ///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        ///
        return new DbServiceClientImpl(transactionManager, clientTemplate);
    }
}
