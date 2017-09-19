package com.fise.server.appstore;

import java.util.List;

import com.fise.base.Response;
import com.fise.model.entity.AppSplash;

public interface IAppSplashService {

    Response insertSplash();

    List<AppSplash> querySpalsh();
}
