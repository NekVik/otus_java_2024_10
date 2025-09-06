package ru.otus.mapper;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import ru.otus.dto.ClientDto;
import ru.otus.persistence.data.Address;
import ru.otus.persistence.data.Client;
import ru.otus.persistence.data.Phone;

@UtilityClass
public class ClientMapper {

    public ClientDto toDto(Client client) {
        if (client != null) {

            String address = Optional.ofNullable(client.getAddress())
                    .map(Address::street)
                    .orElse("");

            var phonesDto = client.getPhones().stream().map(Phone::phone).collect(Collectors.joining(", "));

            return new ClientDto(client.getId(), client.getName(), address, phonesDto);
        } else {
            return null;
        }
    }
}
