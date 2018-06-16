package com.fise.server.patient.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.WiPatientMapper;
import com.fise.model.entity.WiPatient;
import com.fise.model.entity.WiPatientExample;
import com.fise.model.entity.WiPatientExample.Criteria;
import com.fise.server.patient.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private WiPatientMapper patientDao;
    
    @Override
    public Response queryPatient(WiPatient param) {
        Response resp =new Response();
        WiPatientExample example = new WiPatientExample();
        Criteria criteria=example.createCriteria();
        criteria.andDomainEqualTo(param.getDomain());
        List<WiPatient> patientList = patientDao.selectByExample(example);
        resp.success(patientList);
        return resp;
    }

    @Override
    public Response insertPatient(WiPatient param) {
        Response resp =new Response();
        
        patientDao.insertSelective(param);
        return resp;
    }

    @Override
    public Response updatePatient(WiPatient param) {
        Response resp =new Response();
        
        WiPatientExample example = new WiPatientExample();
        Criteria criteria=example.createCriteria();
        criteria.andDomainEqualTo(param.getDomain());
        
        patientDao.updateByExampleSelective(param, example);
        return resp;
    }

    @Override
    public Response delPatient(WiPatient param) {
        
        Response response=new Response();
        
        WiPatientExample example = new WiPatientExample();
        Criteria criteria=example.createCriteria();
        criteria.andDomainEqualTo(param.getDomain());
        patientDao.deleteByExample(example);
        
        return response.success();
    }

}
