/*
 * Created by Juan Albarracin on 4/08/18 16:08
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 16:08
 */

package es.jalbarracinq.gyms.service.tool;

import com.github.javafaker.Faker;

public class FakerTool {

    Faker faker = Faker.instance();

    private static FakerTool instance;

    private FakerTool() { }

    public static Faker getInstance() {
        if (instance == null) {
            instance = new FakerTool();
        } else {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return instance.faker;
    }

}
