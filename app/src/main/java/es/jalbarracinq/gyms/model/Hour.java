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
public class Hour implements Serializable {

    private String name;
    private Boolean hasEvent;
    private DateTime start;
    private DateTime end;
    private String color;
    private Lesson lesson;

}
