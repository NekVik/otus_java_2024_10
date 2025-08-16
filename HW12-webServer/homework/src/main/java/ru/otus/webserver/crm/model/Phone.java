package ru.otus.webserver.crm.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phone")
public class Phone implements Cloneable {

    @Id
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public Phone(Long id, String phone, Client client) {
        this.id = id;
        this.phone = phone;
        this.client = client;
    }


    @Override
    public Phone clone() {
        return new Phone(this.id, this.phone);
    }

    @Override
    public String toString() {
        return "Phone{" + "id=" + id + ", phone='" + phone + '\'' + '}';
    }

}
