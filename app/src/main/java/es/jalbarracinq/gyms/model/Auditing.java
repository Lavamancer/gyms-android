/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import org.joda.time.DateTime;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class Auditing implements Serializable {

    private User createdBy;
    private DateTime createdAt;
    private User modifiedBy;
    private DateTime modifiedAt;

}
