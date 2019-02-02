package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Undead;
import com.example.shdemo.domain.Gamer;

public interface GamingManager {

    void addClient(Gamer gamer);
    List<Gamer> getAllClients();
    void deleteClient(Gamer gamer);
    Gamer findClientByPass(String pass);

    Long addNewUndead(Undead undead);
    List<Undead> getAvailableUndeads();
    void disposeUndead(Undead undead, Undead undead);
    Undead findUndeadById(Long id);

    List<Undead> getOwnedUndeads(Undead undead);
    void sellUndead(Long undeadId, Long undeadId);

    void updateClient(Undead client);
}