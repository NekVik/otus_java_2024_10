package ru.otus.webserver.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.otus.webserver.crm.model.Address;
import ru.otus.webserver.crm.model.Client;
import ru.otus.webserver.crm.model.Phone;
import ru.otus.webserver.crm.service.DBServiceClient;
import ru.otus.webserver.web.model.ClientDto;
import ru.otus.webserver.web.services.TemplateProcessor;

@SuppressWarnings({"java:S1989"})
public class ClientsAddServlet extends HttpServlet {

    private static final String ADD_CLIENT_HTML = "add_client.html";
    private static final String TEMPLATE_ATTR_CLIENT = "client";

    private final transient DBServiceClient dbServiceClient;
    private final transient TemplateProcessor templateProcessor;

    public ClientsAddServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADD_CLIENT_HTML, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var params = req.getParameterMap();

        var client = new Client();
        client.setName(params.get("name")[0]);

        var address = new Address();
        address.setStreet(params.get("street")[0]);
        client.setAddress(address);

        var phones = params.get("phones")[0].split(",");
        var phoneList = Arrays.stream(phones).map(s -> {
           var phone = new Phone();
           phone.setPhone(s);
           return phone;
        }).toList();
        client.setPhones(phoneList);

        dbServiceClient.saveClient(client);

        resp.sendRedirect("/clients");
    }

    private static List<ClientDto> getClientDtos(List<Client> clients) {
        return clients.stream().map(ClientsAddServlet::getClientDto).toList();
    }

    private static ClientDto getClientDto(Client client) {
        if (client != null) {

            var address = Optional.ofNullable(client.getAddress()).map(Address::getStreet).orElse("");

            var phones = Optional.ofNullable(client.getPhones()).map(
                ph -> ph.stream().map(Phone::getPhone).collect(Collectors.joining(", "))
            ).orElse("");

            return new ClientDto(client.getId(), client.getName(), address, phones);
        } else {
            return null;
        }
    }

}
