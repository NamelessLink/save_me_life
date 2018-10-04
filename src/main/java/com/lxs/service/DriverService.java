package com.lxs.service;

import com.lxs.dao.DriverMapper;
import com.lxs.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lxs.util.MD5.md5;

@Service
public class DriverService {

    @Autowired
    private DriverMapper driverMapper;

    //验证账号是否存在
    public boolean isSellerExist(String account){
        if(driverMapper.findDriverByAccount(account) == null){
            return false;
        }else{
            return true;
        }
    }

    //验证是否合法,1合法，0账号或密码错误，-1账号或密码为空，
    public int isVaildSeller(String account, String pwd){
        Driver driver = new Driver();
        if (account != null && !"".equals(account)){
            driver = driverMapper.findDriverByAccount(account);
            if (driver == null){
                return 0;
            }else{
                if (pwd != null && !"".equals(pwd)){
                    if (driver.getPwd().equals(md5(pwd))){
                        return 1;
                    }else{
                        return 0;
                    }
                }else{
                    return -1;
                }
            }
        }else{
            return -1;
        }
    }

    //骑手登录登录
    public Driver driverLogin(String account, String pwd)throws Exception{
        return driverMapper.findDriverByLogin(account, md5(pwd));
    }


}
