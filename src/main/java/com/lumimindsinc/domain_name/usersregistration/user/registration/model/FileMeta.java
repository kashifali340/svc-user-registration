package com.lumimindsinc.domain_name.usersregistration.user.registration.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "FILE_META")
@Getter @Setter
public class FileMeta {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "VERSION")
    private String version;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Employee employee;

    public FileMeta(String fileName, String filePath, String version, Employee employee) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.version = version;
        this.employee = employee;
    }
}