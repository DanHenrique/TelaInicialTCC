package com.example.telainicialtcc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.telainicialtcc.agents.Morador;
import com.example.telainicialtcc.agents.MoradorInterface;
import com.example.telainicialtcc.messages.StatusEquipamento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.logging.Level;

import jade.android.AndroidHelper;
import jade.android.MicroRuntimeService;
import jade.android.MicroRuntimeServiceBinder;
import jade.android.RuntimeCallback;
import jade.core.MicroRuntime;
import jade.core.Profile;
import jade.util.Logger;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.O2AException;
import jade.wrapper.StaleProxyException;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    private MicroRuntimeServiceBinder microRuntimeServiceBinder;
    private ServiceConnection serviceConnection;
    private Logger logger = Logger.getJADELogger(this.getClass().getName());
    private String nickname;
    private TextView infoTextView;
    private MoradorInterface moradorInterface;
    LinearLayout expandableview;
    LinearLayout expandableviewtom;
    LinearLayout expandableviewarcond;
    TextView textview;
    CardView cardView;
    CardView cardViewTom;
    CardView cardViewArcond;
    TextView luminoslamp;
    TextView templamp;
    TextView severitylamp;
    TextView luminostom;
    TextView temptom;
    TextView severitytom;
    TextView temperaturaarcond;
    String host = "10.0.2.2";
    String port = "1099";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        instance = this;

        setSupportActionBar(toolbar);
        nickname = "Morador";

        expandableview = findViewById(R.id.expandable_view);
        expandableviewtom = findViewById(R.id.expandable_view_tom);
        expandableviewarcond = findViewById(R.id.expandable_view_arcond);
        textview = findViewById(R.id.showmore);
        cardView = findViewById(R.id.card_expansivo);
        cardViewTom = findViewById(R.id.card_expansivo_tom);
        cardViewArcond = findViewById(R.id.card_expansivo_arcond);
        luminoslamp = findViewById(R.id.luminoslamp);
        templamp = findViewById(R.id.templamp);
        severitylamp = findViewById(R.id.severitylamp);
        luminostom = findViewById(R.id.luminostom);
        temptom = findViewById(R.id.temptom);
        severitytom = findViewById(R.id.severitytom);
        temperaturaarcond = findViewById(R.id.temperaturaarcond);

        Button liglamp = findViewById(R.id.liglamp);
        Button deslamp = findViewById(R.id.deslamp);
        Button ligtom = findViewById(R.id.ligtom);
        Button destom = findViewById(R.id.destom);



        try {
            SharedPreferences settings = getSharedPreferences(
                    "jadeChatPrefsFile", 0);

            MainActivity.this.startChat(nickname, host, port, agentStartupCallback);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected exception creating chat agent!");
        }

        liglamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AgentController ac = MicroRuntime.getAgent(nickname);

                    moradorInterface = ac.getO2AInterface(MoradorInterface.class);
                    moradorInterface.enviaMensagem("light.lampada", "homeassistant", "turn_on");
                } catch (O2AException e) {
                    logger.log(Level.SEVERE, "Unexpected exception creating chat agent -O2AExcp!");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unexpected exception creating chat agent!");
                    infoTextView.setText("Unexpected error");
                }
            }
        });
        ligtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AgentController ac = MicroRuntime.getAgent(nickname);

                    moradorInterface = ac.getO2AInterface(MoradorInterface.class);
                    moradorInterface.enviaMensagem("switch.tomada", "homeassistant", "turn_on");
                } catch (O2AException e) {
                    logger.log(Level.SEVERE, "Unexpected exception creating chat agent -O2AExcp!");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unexpected exception creating chat agent!");
                    infoTextView.setText("Unexpected error");
                }
            }
        });
        deslamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AgentController ac = MicroRuntime.getAgent(nickname);

                    moradorInterface = ac.getO2AInterface(MoradorInterface.class);
                    moradorInterface.enviaMensagem("light.lampada", "homeassistant", "turn_off");
                } catch (O2AException e) {
                    logger.log(Level.SEVERE, "Unexpected exception creating chat agent -O2AExcp!");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unexpected exception creating chat agent!");
                    infoTextView.setText("Unexpected error");
                }
            }
        });
        destom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AgentController ac = MicroRuntime.getAgent(nickname);

                    moradorInterface = ac.getO2AInterface(MoradorInterface.class);
                    moradorInterface.enviaMensagem("switch.tomada", "homeassistant", "turn_off");
                } catch (O2AException e) {
                    logger.log(Level.SEVERE, "Unexpected exception creating chat agent -O2AExcp!");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unexpected exception creating chat agent!");
                    infoTextView.setText("Unexpected error");
                }
            }
        });
    }

    private RuntimeCallback<AgentController> agentStartupCallback = new RuntimeCallback<AgentController>() {
        @Override
        public void onSuccess(AgentController agent) {
        }

        @Override
        public void onFailure(Throwable throwable) {
            logger.log(Level.INFO, "Nickname already in use!");
        }
    };

    private RuntimeCallback<Void> containerStartupCallback = new RuntimeCallback<Void>() {
        @Override
        public void onSuccess(Void aVoid) {

        }

        @Override
        public void onFailure(Throwable throwable) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startChat(final String nickname, final String host,
                          final String port,
                          final RuntimeCallback<AgentController> agentStartupCallback) {

        final Properties profile = new Properties();
        profile.setProperty(Profile.MAIN_HOST, host);
        profile.setProperty(Profile.MAIN_PORT, port);
        profile.setProperty(Profile.MAIN, Boolean.FALSE.toString());
        profile.setProperty(Profile.JVM, Profile.ANDROID);

        if (AndroidHelper.isEmulator()) {
            // Emulator: this is needed to work with emulated devices
            profile.setProperty(Profile.LOCAL_HOST, AndroidHelper.LOOPBACK);
        } else {
            profile.setProperty(Profile.LOCAL_HOST,
                    AndroidHelper.getLocalIPAddress());
        }
        // Emulator: this is not really needed on a real device
        profile.setProperty(Profile.LOCAL_PORT, "2000");
        logger.log(Level.INFO, "Oi Felpinho");
        if (microRuntimeServiceBinder == null) {
            serviceConnection = new ServiceConnection() {
                public void onServiceConnected(ComponentName className,
                                               IBinder service) {
                    microRuntimeServiceBinder = (MicroRuntimeServiceBinder) service;
                    logger.log(Level.INFO, "Gateway successfully bound to MicroRuntimeService");
                    startContainer(nickname, profile, agentStartupCallback);
                }

                public void onServiceDisconnected(ComponentName className) {
                    microRuntimeServiceBinder = null;
                    logger.log(Level.INFO, "Gateway unbound from MicroRuntimeService");
                }
            };
            logger.log(Level.INFO, "Binding Gateway to MicroRuntimeService...");
            boolean teste = bindService(new Intent(getApplicationContext(),
                            MicroRuntimeService.class), serviceConnection,
                    Context.BIND_AUTO_CREATE);
            logger.log(Level.INFO, "Teste -> " + teste);
        } else {
            logger.log(Level.INFO, "MicroRumtimeGateway already binded to service");
            startContainer(nickname, profile, agentStartupCallback);
        }
    }

    private void startContainer(final String nickname, Properties profile,
                                final RuntimeCallback<AgentController> agentStartupCallback) {
        if (!MicroRuntime.isRunning()) {
            microRuntimeServiceBinder.startAgentContainer(profile,
                    new RuntimeCallback<Void>() {
                        @Override
                        public void onSuccess(Void thisIsNull) {
                            logger.log(Level.INFO, "Successfully start of the container...");
                            startAgent(nickname, agentStartupCallback);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            logger.log(Level.SEVERE, "Failed to start the container...");
                        }
                    });
        } else {
            startAgent(nickname, agentStartupCallback);
        }
    }

    private void startAgent(final String nickname,
                            final RuntimeCallback<AgentController> agentStartupCallback) {
        String teste = Morador.class.getName();
        microRuntimeServiceBinder.startAgent(nickname,
                Morador.class.getName(),
                new Object[]{getApplicationContext()},
                new RuntimeCallback<Void>() {
                    @Override
                    public void onSuccess(Void thisIsNull) {
                        logger.log(Level.INFO, "Successfully start of the "
                                + Morador.class.getName() + "...");
                        try {
                            agentStartupCallback.onSuccess(MicroRuntime
                                    .getAgent(nickname));

                            AgentController ac = MicroRuntime.getAgent(nickname);

                            moradorInterface = ac.getO2AInterface(MoradorInterface.class);

                            moradorInterface.receberMensagem(luminoslamp, templamp, severitylamp, luminostom, temptom, severitytom);
                        } catch (ControllerException e) {
                            // Should never happen
                            agentStartupCallback.onFailure(e);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        logger.log(Level.SEVERE, "Failed to start the "
                                + "MainActivity" + "...");
                        agentStartupCallback.onFailure(throwable);
                    }
                });
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void atualizarStatus(final StatusEquipamento status) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (status.entityID.equals("light.lampada")){
                    luminoslamp.setText("Luminosidade: "+  status.getBrightness());
                    templamp.setText("Temperatura: " + status.getTemperature());
                    severitylamp.setText("Criticitade: "+ status.getSeverity());

                }else{
                    luminostom.setText("Luminosidade: "+  status.getBrightness());
                    temptom.setText("Temperatura: " + status.getTemperature());
                    severitytom.setText("Criticitade: "+ status.getSeverity());
                }
            }
        });


    }

    public void showmore(View view) {

        if (expandableview.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableview.setVisibility(View.VISIBLE);
        } else {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableview.setVisibility(View.GONE);
        }


    }

    public void showmoreTom(View view) {
        if (expandableviewtom.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardViewTom, new AutoTransition());
            expandableviewtom.setVisibility(View.VISIBLE);
        } else {
            TransitionManager.beginDelayedTransition(cardViewTom, new AutoTransition());
            expandableviewtom.setVisibility(View.GONE);
        }
    }
    public void showmorearcond(View view) {
        if (expandableviewarcond.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardViewArcond, new AutoTransition());
            expandableviewarcond.setVisibility(View.VISIBLE);
        } else {
            TransitionManager.beginDelayedTransition(cardViewArcond, new AutoTransition());
            expandableviewarcond.setVisibility(View.GONE);
        }
    }
    public void abaixatemp(View view) {
        int temperatura;

       temperatura = Integer.parseInt(temperaturaarcond.getText().toString());
       temperatura--;
       temperaturaarcond.setText(String.valueOf(temperatura));
    }
    public void aumentatemp(View view) {
        int temperatura;
        temperatura = Integer.parseInt(temperaturaarcond.getText().toString());
        temperatura++;
        temperaturaarcond.setText(String.valueOf(temperatura));
    }
    @Override
    protected void onStop () {
        super.onStop();
        try {
            AgentController ac = MicroRuntime.getAgent(nickname);
            moradorInterface = ac.getO2AInterface(MoradorInterface.class);
            moradorInterface.mataMorador();
            microRuntimeServiceBinder.stopAgentContainer(containerStartupCallback);
        } catch (StaleProxyException e) {
            e.printStackTrace();
        } catch (ControllerException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume () {
        super.onResume();
        MainActivity.this.startChat(nickname, host, port, agentStartupCallback);
    }



}