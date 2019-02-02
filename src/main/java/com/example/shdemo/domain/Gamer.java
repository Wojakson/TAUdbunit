package com.example.shdemo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "gamer.all", query = "Select g from Gamer g"),
        @NamedQuery(name = "gamer.byPass", query = "Select g from Gamer g where p.pass = :pass")
})
public class Gamer {

    private Long id;

    private String nick;// = "unknown";
    private String pass;// = "";
    private Date registrationDate;// = new Date();

    private List<Undead> undeads;// = new ArrayList<Undead>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }

    @Column(unique = true, nullable = false)
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    @Temporal(TemporalType.DATE)
    public Date getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@OneToMany(orphanRemoval=true)
    //@JoinColumn(name="OWNER")
    public List<Undead> getUndeads() {
        return undeads;
    }
    public void setUndeads(List<Undead> undeads) {
        this.undeads = undeads;
    }
}