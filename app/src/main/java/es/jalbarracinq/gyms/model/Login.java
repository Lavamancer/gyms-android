/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Login implements Serializable {

    private String username;
    private String password;

}
