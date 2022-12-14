package com.lumimindsinc.domain_name.usersregistration.user.registration.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
@Builder
public class Roles implements Serializable {

    @Id
    @SequenceGenerator(name="roll_sequence",sequenceName="entity_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO,generator="roll_sequence")
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column
    private String type;

}
