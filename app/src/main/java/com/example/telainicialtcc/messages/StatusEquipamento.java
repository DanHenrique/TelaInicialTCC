package com.example.telainicialtcc.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class StatusEquipamento implements Serializable {

    // Atributos
    @JsonProperty
    public String severity;
    @JsonProperty("entity_id")
    public String entityID;
    @JsonProperty
    public int brightness;
    @JsonProperty
    public int temperature;

    // Métodos
    public StatusEquipamento(){
    }

    public StatusEquipamento(String entity, int temperature, int brightness) {
        this.setEntityID(entity);
        this.setBrightness(brightness);
        this.setTemperature(temperature);
    }

    public String getSeverity() {
        return this.severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entity) {
        this.entityID = entity;
    }

    public int getBrightness() {
        return this.brightness;
    }

    public void setBrightness(int domain) {
        this.brightness = domain;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int service) {
        this.temperature = service;
    }
}