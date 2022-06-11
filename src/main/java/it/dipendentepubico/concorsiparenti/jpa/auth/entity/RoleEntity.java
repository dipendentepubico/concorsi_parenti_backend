package it.dipendentepubico.concorsiparenti.jpa.auth.entity;

import javax.persistence.*;
@Entity
@Table(name = "ROLES")
public class RoleEntity {
    @Id
    @SequenceGenerator(name="ROLE_ID_GENERATOR", sequenceName="ROLE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ROLE_ID_GENERATOR")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleEnumEntity name;
    public RoleEntity() {
    }
    public RoleEntity(RoleEnumEntity name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public RoleEnumEntity getName() {
        return name;
    }
    public void setName(RoleEnumEntity name) {
        this.name = name;
    }
}