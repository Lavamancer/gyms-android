/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import es.jalbarracinq.gyms.model.Card;
import es.jalbarracinq.gyms.model.Configuration;
import es.jalbarracinq.gyms.model.Facility;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.Login;
import es.jalbarracinq.gyms.model.Token;
import es.jalbarracinq.gyms.model.User;
import es.jalbarracinq.gyms.service.tool.StorageTool;

public class Session implements Serializable {

    private static final String SESSION_KEY = "session";

    public Login login;
    public Login autoLogin;
    public Token token;
    public List<Facility> facilities;
    public Facility facility;
    public List<LessonType> lessonTypes;
    public User user;
    public Long evolutionId;
    public List<Card> cards;
    public HashMap<Configuration.Name, String> configurations;
    public HashMap<String, Boolean> permissions;
    public boolean mock = true;


    public static Session getInstance() {
        return Application.application.session;
    }

    public static void init(Application application) {
        Session session = StorageTool.load(application, SESSION_KEY);
        if (session == null) {
            application.session = new Session();
        } else {
            application.session = session;
        }
    }

    public static void store(Application application, Session session) {
        StorageTool.store(application, session, SESSION_KEY);
    }

}
