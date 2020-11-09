package com.example.telainicialtcc.agents;

import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface MoradorInterface {
    ObjectMapper objectMapper = new ObjectMapper();

    void enviaMensagem(String entidade, String dominio, String servico);

    void receberMensagem(TextView luminosLamp, TextView tempLamp, TextView severityLamp, TextView luminosTom, TextView tempTom, TextView severityTom );
}
