package ru.otus.crm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
@SuppressWarnings("java:S2975")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;

        this.address = getAddressCopy(address);
        this.phones = getPhonesCopy(phones);
        setClientForPhones();
    }

    private void setClientForPhones() {
        if (phones != null) {
            phones.forEach(phone -> phone.setClient(this));
        }
    }

    @Override
    public Client clone() {

        var addressCopy = getAddressCopy(this.address);
        var phonesCopy = getPhonesCopy(this.phones);

        return new Client(this.id, this.name, addressCopy, phonesCopy);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    private List<Phone> getPhonesCopy(List<Phone> phones) {
        return phones != null ? phones.stream().map(Phone::clone).toList() : null;
    }

    private Address getAddressCopy(Address address) {
        return address != null ? address.clone() : null;
    }
}
