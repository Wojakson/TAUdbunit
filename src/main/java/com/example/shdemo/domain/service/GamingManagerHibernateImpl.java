package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Undead;
import com.example.shdemo.domain.Gamer;

@Component
@Transactional
public class GamingMangerHibernateImpl implements GamingManager {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addClient(Gamer gamer) {
        gamer.setId(null);
        sessionFactory.getCurrentSession().persist(gamer);
        sessionFactory.getCurrentSession().flush();
    }
    @Override
    public void updateClient(Gamer gamer) {
        sessionFactory.getCurrentSession().update(gamer);
    }

    @Override
    public void deleteClient(Gamer gamer) {
        gamer = (Gamer) sessionFactory.getCurrentSession().get(Gamer.class,
                gamer.getId());

        // lazy loading here
        for (Undead undead : gamer.getUndeads()) {
            undead.setSummoned(false);
            sessionFactory.getCurrentSession().update(undead);
        }


        sessionFactory.getCurrentSession().delete(gamer);
    }

    @Override
    public List<Undead> getOwnedUndeads(Gamer gamer) {
        gamer = (Gamer) sessionFactory.getCurrentSession().get(Gamer.class,
                gamer.getId());
        // lazy loading here - try this code without (shallow) copying
        List<Undead> undeads = new ArrayList<Undead>(gamer.getUndeads());
        return undeads;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Gamer> getAllClients() {
        return sessionFactory.getCurrentSession().getNamedQuery("gamer.all")
                .list();
    }

    @Override
    public Gamer findClientByPass(String pass) {
        return (Gamer) sessionFactory.getCurrentSession().getNamedQuery("gamer.byPass").setString("pass", pass).uniqueResult();
    }


    @Override
    public Long addNewCar(Undead undead) {
        undead.setId(null);
        return (Long) sessionFactory.getCurrentSession().save(undead);
    }

    @Override
    public void sellUndead(Long gamerId, Long undeadId) {
        Undead undead = (Gamer) sessionFactory.getCurrentSession().get(
                Gamer.class, gamerId);
        Undead undead = (Undead) sessionFactory.getCurrentSession()
                .get(Undead.class, undeadId);
        undead.setSummoned(true);
        if (gamer.getUndeads() == null) {
            gamer.setUndeads(new LinkedList<Undead>());
        }
        gamer.getUndeads().add(undead);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Undead> getAvailableUndeads() {
        return sessionFactory.getCurrentSession().getNamedQuery("undead.unsummoned")
                .list();
    }
    @Override
    public void disposeUndead(Gamer gamer, Undead undead) {

        gamer = (Gamer) sessionFactory.getCurrentSession().get(Gamer.class,
                gamer.getId());
        undead = (Undead) sessionFactory.getCurrentSession().get(Undead.class,
                undead.getId());

        Undead toRemove = null;
        // lazy loading here (gamer.getUndeads)
        for (Undead aUndead : gamer.getUndeads())
            if (aUndead.getId().compareTo(undead.getId()) == 0) {
                toRemove = aUndead;
                break;
            }

        if (toRemove != null)
            gamer.getUndeads().remove(toRemove);

        undead.setSummoned(false);
    }

    @Override
    public Undead findUndeadById(Long id) {
        return (Undead) sessionFactory.getCurrentSession().get(Undead.class, id);
    }

}