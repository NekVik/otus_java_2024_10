package ru.otus.webserver.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";

    private final transient DBServiceClient dbServiceClient;
    private final transient TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        var clients = dbServiceClient.findAll();

        var clientsDto = getClientDtos(clients);

        paramsMap.put(TEMPLATE_ATTR_CLIENTS, clientsDto);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    private static List<ClientDto> getClientDtos(List<Client> clients) {
        return clients.stream().map(ClientsServlet::getClientDto).toList();
    }

    private static ClientDto getClientDto(Client client) {
        if (client != null) {

            var address = Optional.ofNullable(client.getAddress())
                    .map(Address::getStreet)
                    .orElse("");

            var phones = Optional.ofNullable(client.getPhones())
                    .map(ph -> ph.stream().map(Phone::getPhone).collect(Collectors.joining(", ")))
                    .orElse("");

            return new ClientDto(client.getId(), client.getName(), address, phones);
        } else {
            return null;
        }
    }
}
