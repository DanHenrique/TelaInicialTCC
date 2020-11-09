package com.example.telainicialtcc.agents;

import android.widget.TextView;

import com.example.telainicialtcc.MainActivity;
import com.example.telainicialtcc.messages.Instrucao;
import com.example.telainicialtcc.messages.StatusEquipamento;
import com.fasterxml.jackson.core.JsonProcessingException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Morador extends Agent implements MoradorInterface {

    @Override
    protected void setup() {

        registerO2AInterface(MoradorInterface.class, this);
    }

    public void enviaMensagem (String entidade, String dominio, String servico){

       final Instrucao dado = new Instrucao(entidade, dominio, servico);

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    //msg.setContentObject(dado);
                    msg.setContent(dado.toJSON());
                    AID receiver = new AID("Comandante", AID.ISLOCALNAME);
                    msg.addReceiver(receiver);
                    send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void receberMensagem(final TextView luminosLamp, final TextView tempLamp, final TextView severityLamp, final TextView luminosTom, final TextView tempTom , final TextView severityTom){
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                try {
                    if (msg != null) {
                        String json = msg.getContent();

                        StatusEquipamento status = objectMapper.readValue(json, StatusEquipamento.class);
                        MainActivity ma = MainActivity.getInstance();
                        ma.atualizarStatus(status);

                    } else {
                        block();
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void mataMorador() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                   doDelete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
