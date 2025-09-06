package ru.otus.persistence.data;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "client")
@SuppressWarnings("java:S125")
public class Client implements Persistable<Long> {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("address_id")
    private Long addressId;

    @Transient
    private Address address;

    @ToString.Exclude
    @MappedCollection(idColumn = "client_id", keyColumn = "ord")
    private List<Phone> phones;

    @PersistenceCreator
    private Client(Long id, String name, Long addressId, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.phones = phones;
    }

    public Client(String name, Long addressId, Address address, List<Phone> phones) {
        this.id = null;
        this.name = name;
        this.addressId = addressId;
        this.address = address;
        this.phones = phones;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "Client{" + "id="
                + id + ", name='"
                + name + '\'' + ", addressId="
                + addressId + ", address="
                + address + ", phones="
                + phones + '}';
    }
}
