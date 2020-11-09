package com.example.telainicialtcc.messages;

import jade.util.leap.Serializable;

public class Instrucao implements Serializable {
    // Atributos
    public String entity_id;
    public String domain;
    public String service;

    // Métodos
    public Instrucao(String entity, String domain, String service){
        this.setEntity(entity);
        this.setDomain(domain);
        this.setService(service);
    }

    public String getEntity() {
        return this.entity_id;
    }

    public void setEntity(String entity) {
        this.entity_id = entity;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String toJSON(){
        String json = String.format("{\n\t\"entity_id\": \"%s\",\n\t\"domain\": \"%s\",\n\t\"service\": \"%s\"\n}", this.getEntity(), this.getDomain(), this.getService());
        return json;
    }
}
