package com.lumimindsinc.domain_name.usersregistration.user.registration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lumimindsinc.domain_name.usersregistration.general.model.DataEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Getter
@Setter
public class Employee extends DataEntity implements Serializable {

    public static final byte HASH_ROUNDS = 6;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(length = 50)
    private String username;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String designation;
    @Column
    private String contactNumber;
    @Column
    @JsonIgnore
    @ToString.Exclude
    private String password;
    @Column
    private String locationDetail;
    @Column
    private String gender;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Roles role;

    public Employee(String username, String name, String email, String designation, String contactNumber, String password, String locationDetail, String gender, Roles role) {
        super(Boolean.TRUE,  new Timestamp(System.currentTimeMillis()), null,  new Timestamp(System.currentTimeMillis()), null, null, null);
        this.username = username;
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.contactNumber = contactNumber;
        this.password = password;
        this.locationDetail = locationDetail;
        this.gender = gender;
        this.role = role;
    }
}
