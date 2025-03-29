package com.salker.konnect.POJO;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "phone_numbers")
public class UsersContactNumber implements Serializable {

    @Id // Correct annotation from jakarta.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Integer id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", foreignKey = @ForeignKey(name = "FK_ADDRESS_PHONE", value = ConstraintMode.CONSTRAINT))
    private UserAddreses userAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserAddreses getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddreses userAddress) {
        this.userAddress = userAddress;
    }
}
