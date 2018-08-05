/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import org.joda.time.DateTime;

import lombok.Data;

@Data
public class Day {

    private DateTime date;
    private Boolean hasEvent = false;

}
