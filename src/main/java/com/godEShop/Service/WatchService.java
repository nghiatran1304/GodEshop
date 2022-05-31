package com.godEShop.Service;

import com.godEShop.Entity.Watch;

public interface WatchService {

    Watch update(Watch w);

    Watch create(Watch watch);

    Watch getById(Integer watchId);

}
