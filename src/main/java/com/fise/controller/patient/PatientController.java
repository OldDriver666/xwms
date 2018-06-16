package com.fise.controller.patient;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.model.entity.WiPatient;
import com.fise.server.auth.IAuthService;
import com.fise.server.patient.IPatientService;
import com.fise.utils.JsonUtil;
import com.fise.utils.StringUtil;

@RestController
@RequestMapping("/boss/patient")
public class PatientController {

    private Logger logger = Logger.getLogger(getClass());

    @Resource
    IPatientService patientSvr;

    @Resource
    IAuthService authService;

    /* 查询病人信息 */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Response queryOrgan(@RequestBody @Valid WiPatient param) {

        Response response = new Response();
        response = patientSvr.queryPatient(param);
        logger.info("查询病人信息:"+JsonUtil.toJson(param)+" 结果:"+response.getMsg());
        return response;
    }

    /* 插入病人信息 */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insertOrgan(@RequestBody @Valid WiPatient param) {

        Response response = new Response();

        if (authService.inserAuth()) {
            if (!StringUtil.isEmpty(param.getDomain())) {
                response = patientSvr.insertPatient(param);
            } 
            else {
                response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
            }
        } 
        else {
            response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }

        logger.info("新增病人信息:"+JsonUtil.toJson(param) + "结果:" + response.getMsg());
        return response;
    }

    /* 修改病人信息 */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response updateOrgan(@RequestBody @Valid WiPatient param) {

        Response response = new Response();

        if (authService.updateAuth()) {
            if(param.getDomain() != null){
                response = patientSvr.updatePatient(param);
            }
            else{
                response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
            }
        }
        else{
            response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }

        logger.info("更新病人信息:"+JsonUtil.toJson(param)+" 结果:"+response.getMsg());
        return response;
    }

    /* 删除病人信息 */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Response delOrgan(@RequestBody @Valid WiPatient param) {

        Response response = new Response();

        if (!authService.updateAuth()) {
            response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }

        if (param.getDomain() != null){
            response = patientSvr.delPatient(param);
        }
        else{
            response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }

        logger.info("删除病人信息:"+JsonUtil.toJson(param)+" 结果:"+response.getMsg());
        return response;
    }
}
