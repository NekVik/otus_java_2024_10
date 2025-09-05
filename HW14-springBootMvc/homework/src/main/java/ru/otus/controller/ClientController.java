package ru.otus.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dto.ClientDto;
import ru.otus.service.ClientService;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/clients")
    public String clientsListView(Model model) {
        List<ClientDto> clients = clientService.findAll();
        model.addAttribute("clients", clients);

        return "clients";
    }

    @GetMapping("/clients/add")
    public String clientCreateView(Model model) {
        model.addAttribute("client", new ClientDto());
        model.addAttribute("isNew", true);
        return "edit_client";
    }

    @GetMapping("/clients/edit/{id}")
    public String clientCreateView(@PathVariable Long id, Model model) {
        var clintOpt = clientService.getClient(id);

        if (clintOpt.isEmpty()) {
            return "redirect:/clients";
        }

        var client = clintOpt.get();
        model.addAttribute("client", client);
        model.addAttribute("isNew", false);

        return "edit_client";
    }

    @GetMapping("/clients/delete/{id}")
    public RedirectView clientCreateView(@PathVariable Long id) {
        clientService.delete(id);
        return new RedirectView("/clients", true);
    }

    @PostMapping("/clients/save")
    public RedirectView clientSave(ClientDto client) {
        clientService.save(client);
        return new RedirectView("/clients", true);
    }
}
