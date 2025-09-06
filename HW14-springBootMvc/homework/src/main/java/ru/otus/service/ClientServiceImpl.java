package ru.otus.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.dto.ClientDto;
import ru.otus.mapper.ClientMapper;
import ru.otus.persistence.data.Address;
import ru.otus.persistence.data.Client;
import ru.otus.persistence.data.Phone;
import ru.otus.persistence.repository.AddressRepository;
import ru.otus.persistence.repository.ClientRepository;
import ru.otus.persistence.repository.PhoneRepository;
import ru.otus.sessionmanager.TransactionManager;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings({"java:S1186", "java:S1612"})
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;
    private final TransactionManager transactionManager;

    @Override
    public Optional<ClientDto> getClient(Long id) {
        return Optional.of(getClientWithAddress(id)).map(ClientMapper::toDto);
    }

    public List<ClientDto> findAll() {

        var list = clientRepository.findAll();

        return list.stream()
                .map(this::loadAddress) // Загружаем адреса
                .map(ClientMapper::toDto)
                .toList();
    }

    public void save(ClientDto client) {

        if (client.getId() == null) {
            createClient(client);
        } else {
            updateClient(client);
        }
    }

    private Client getClientWithAddress(Long id) {
        return clientRepository
                .findById(id)
                .map(this::loadAddress)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    private Client loadAddress(Client client) {
        // Загружаем адрес
        if (client.getAddressId() != null) {
            addressRepository.findById(client.getAddressId()).ifPresent(client::setAddress);
        }

        return client;
    }

    private void createClient(ClientDto clientDto) {

        transactionManager.doInTransaction(() -> {
            var address = new Address(null, clientDto.getAddress());
            var savedAddress = addressRepository.save(address);

            var phones = createPhoneList(clientDto.getPhones(), null);

            var client = new Client(clientDto.getName(), savedAddress.id(), savedAddress, phones);
            var savedClient = clientRepository.save(client);

            log.info("createClient. saved Client: {}", savedClient);
            return savedClient;
        });
    }

    private void updateClient(ClientDto clientDto) {

        transactionManager.doInTransaction(() -> {
            var client = getClientWithAddress(clientDto.getId());

            Address address = null;
            if (client.getAddressId() != null) {
                address = updateAddress(clientDto, client);
                addressRepository.save(address); // обновляем адрес
            }

            var phoneList = updatePhones(clientDto, client);
            var addressId = Optional.ofNullable(address).map(Address::id).orElse(null);
            client.setAddress(address);
            client.setPhones(phoneList);
            client.setAddressId(addressId);
            client.setName(clientDto.getName());

            var savedClient = clientRepository.save(client);

            log.info("updateClient. saved Client: {}", savedClient);
            return savedClient;
        });
    }

    private List<Phone> updatePhones(ClientDto clientDto, Client client) {

        if (clientDto.getPhones() == null || clientDto.getPhones().isEmpty()) {
            return client.getPhones();
        }

        var phoneDbStr = client.getPhones().stream().map(Phone::phone).collect(Collectors.joining(", "));

        if (phoneDbStr.equals(clientDto.getPhones())) {
            return client.getPhones();
        }
        // удаляем сначала
        phoneRepository.deleteAllByClientId(client.getId());

        return createPhoneList(clientDto.getPhones(), client.getId());
    }

    private Address updateAddress(ClientDto clientDto, Client client) {

        if (clientDto.getAddress().isEmpty()) {
            return client.getAddress();
        }

        return new Address(client.getAddressId(), clientDto.getAddress());
    }

    private static List<Phone> createPhoneList(String phones, Long clientId) {

        var order = new AtomicInteger(1);

        return Arrays.stream(phones.split(", "))
                .map(s -> new Phone(null, s, clientId, order.getAndIncrement()))
                .toList();
    }

    public void delete(Long id) {
        transactionManager.doInTransaction(() -> {
            var client = clientRepository.findById(id).orElseThrow();
            clientRepository.deleteById(id);
            addressRepository.deleteById(client.getAddressId());
            return null;
        });
    }
}
