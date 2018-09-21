package com.almightee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.almightee.domain.enumeration.CommandStatus;

/**
 * A Command.
 */
@Entity
@Table(name = "command")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "command")
public class Command implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommandStatus status;

    @Column(name = "total")
    private Long total;

    @OneToMany(mappedBy = "command")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommandItem> carts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("commands")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Command date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public Command status(CommandStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public Long getTotal() {
        return total;
    }

    public Command total(Long total) {
        this.total = total;
        return this;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Set<CommandItem> getCarts() {
        return carts;
    }

    public Command carts(Set<CommandItem> commandItems) {
        this.carts = commandItems;
        return this;
    }

    public Command addCart(CommandItem commandItem) {
        this.carts.add(commandItem);
        commandItem.setCommand(this);
        return this;
    }

    public Command removeCart(CommandItem commandItem) {
        this.carts.remove(commandItem);
        commandItem.setCommand(null);
        return this;
    }

    public void setCarts(Set<CommandItem> commandItems) {
        this.carts = commandItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Command customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public Command user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Command command = (Command) o;
        if (command.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), command.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Command{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
