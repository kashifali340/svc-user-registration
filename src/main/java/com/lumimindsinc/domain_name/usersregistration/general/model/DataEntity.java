package com.lumimindsinc.domain_name.usersregistration.general.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lumimindsinc.domain_name.usersregistration.user.registration.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@MappedSuperclass
@Getter
@Setter
@JsonIgnoreProperties(value = {"disabledBy", "dateDisabled"})

public class DataEntity extends BaseEntity {

    private static final long serialVersionUID = 2814244235550115484L;

    @Column(nullable = false)
    protected Boolean isActive = Boolean.TRUE;

    @Column(nullable = false, updatable = false)
    protected Timestamp dateCreated;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", updatable = false)
    protected Employee createdBy;

    @Column(nullable = false, updatable = false)
    protected Timestamp dateUpdated;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    protected Employee updatedBy;

    @Column(updatable = false)
    protected Timestamp dateDisabled;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "disabled_by")
    protected Employee disabledBy;

    public DataEntity() {
        super();
        this.isActive = Boolean.TRUE;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
       // initGson();
    }

}
