[TOC]

### 阅读说明

#### 请求格式
```
接口分为需要鉴权的和不需要鉴权两类，除了登录接口只要都需要鉴权，鉴权规则如下，请求头中必须包含如下值：
FISE-UA：platform|system|udid|userid|version
//使用|分隔的字符串其中userid为登录成功之后得到的用户id,其他字段酌情填写不可不填,目前阶段强校验userid字段
FISE-AccessToken:dfdd2a47b176443ba7d062307248e25a
//登录成功之后返回的accessToken值，该值和UA中的userid 唯一对应且强关系校验
```
#### 回包格式
```
数据传输采用json协议。回包通用格式如下,后续接口说明仅提供默认正常处理情况下返回的数据格式
{
    "code":INTEGER,      //错误码
    "msg":"STRING", //错误提示
    "data":JsonObject,   //返回的数据,一下所有接口回复说明里面就是该data字段内容,其他code 和code_msg未列出
}
```
#### 请求主机
```
http://boss.fise-wi.com
```

###开放接口
####管理员登录
|   接口地址    |   boss/admin/login         |
|   ---         |   ---                   |

```
//请求
{
    "account":"#userphone#",
    "password":"#md5#"
}
//回复
{
      "access_token": "5282b69acfdf495aa07db874473a6659",
      "account": "Administrator",
      "company_id": 1,
      "depart_id": 0,
      "user_id": 2,
      "nick_name": "Administrator",
      "phone": "",
      "home": "index.html"
}
```

####管理员退出
|   接口地址    |   boss/admin/logout         |
|   ---         |   ---                   |

```
//请求
{
    "user_id":x    // 这个值是登陆中返回的user_id获字段值
}

//回复
看code是否成功
```


###账号管理




####实现单点登录
|   接口地址     |   boss/admin/islogin         |
|   ---    |   ---                   |

```
//请求
{
    "accessToken":""     //必填-管理员的accessToken
}
  
//回复
看code是否成功
```

####新增管理员
|   接口地址    |   boss/admin/insert         |
|   ---         |   ---                   |

```
//请求
{
    "admin_id":X,       //必填-这个值是登陆中返回的id获字段值-调用者id
    "creator_id":X,       //必填-创建者id
    "account":"",       //必填-新增管理员账号
    "password":"",      //必填-密码
    "nick_name":"",     //昵称
    "role_id":x,        //必填-角色
    "phone":"",
    "email":"",
    "company_id":x      //必填-组织id
    "depart_id":x       //选填-部门ID
}

//回复
null 没有数据返回 看code是否成功
```

####管理员查询
|   接口地址    |   boss/admin/query         |
|   ---         |   ---                   |

```
//请求
{
    "admin_id":X,       //必填-这个值是登陆中返回的id获字段值-调用者id
    "account":"",       //管理员账号
    "role_id":x,        //角色
    "company_id":x,     //公司组织id
}

//回复
[
    {
        "id": 1,
        "account": "18601735176",
        "salt": "3678",
        "password": "",
        "nickName": "廖国顺",
        "roleId": 3,
        "companyId": 1,
        "departId":x,
        "phone": "",
        "email": "",
        "accessToken": "",
        "status": 1,
        "lastLogin": 1498122594,
        "created": 1225404661,
        "updated": 1498547576
    },
    {
        "id": 3,
        "account": "chenzhongchao",
        "salt": "1540",
        "password": "",
        "nickName": "陈钟超",
        "roleId": 3,
        "departId":x,
        "companyId": 1,
        "phone": "",
        "email": "",
        "accessToken": null,
        "status": 1,
        "lastLogin": null,
        "created": 1496315408,
        "updated": 1496396165
    }
]
```


####查询当前用户
|   接口地址    |   boss/admin/queryself         |
|   ---         |   ---                   |

```
//请求
{
}

//回复
[    
    {
        "id": 3,
        "account": "chenzhongchao",
        "salt": "1540",
        "password": "",
        "nickName": "陈钟超",
        "roleId": 3,
        "departId":x,
        "companyId": 1,
        "phone": "",
        "email": "",
        "accessToken": null,
        "status": 1,
        "lastLogin": null,
        "created": 1496315408,
        "updated": 1496396165
    }
]
```


####管理员修改
|   接口地址    |   boss/admin/update         |
|   ---         |   ---                   |

```
//请求
{
    "login_id":x,       //必填-登录者id
    "admin_id":X,       //必填-需要修改者id
    "account":"",       //增管理员账号
    "password":"",      //密码
    "nick_name":"",     //昵称
    "status":1,         //0-不可用，1-可用  2-删除 
    "role_id":x,        //角色
    "organization_id":x,//公司组织id
    "phone":"",
    "email":""
}

//回复
null 没有数据返回 看code是否成功
```

###角色权限管理
####新增角色
|   接口地址    |   boss/role/insert        |
|   ---         |   ---                   |

```
//请求
{
 "role_level":x,                        //必填-角色权值
 "role_name":"",                        //必填-角色名称
 "company_id":x                         //必填-公司id
 "desc":"",                             //选填-角色描述
 "depart_id":X                          //选填-角色部门
 "creator_id":X                         //必填-创建者id
}

//回复
无内容，直接查看返回码
``` 

####查询角色
|   接口地址    |   boss/role/query        |
|   ---         |   ---                   |

```
//请求
{
 "role_id":x,                 //必填-自己角色
 "company_id":x               //必填-自己公司
 "creator_id":x               //必填-创建者id
}

//回复
[
      {
         "id": 1,
         "authLevel": 999,
         "name": "BOSS",
         "description": "沸石智能管理员账号",
         "organizationId": 1
      },
      {
         "id": 2,
         "authLevel": 800,
         "name": "超级管理员",
         "description": "合作公司管理员账号",
         "organizationId": 1
      },
      {
         "id": 3,
         "authLevel": 700,
         "name": "管理员",
         "description": "管理员账号",
         "organizationId": 1
      }
]
``` 

####删除角色
|   接口地址    |   boss/role/delete         |
|   ---         |   ---                   |

```
//请求
{

    "role_id":x       //角色id

}

//回复
null 没有数据返回 看code是否成功
```

####修改角色
|   接口地址    |   boss/role/update        |
|   ---         |   ---                   |

```
//请求
{
 "id":x                                 //必填-从role/query返回数据中的id字段值
 "role_level":x,                        //选填-角色权值
 "role_name":"",                        //选填-角色名称
 "company_id":x                         //选填-公司id
 "desc":"",                             //选填-角色描述
 "depart_id":X                          //选填-角色部门
}

//回复
无内容，直接查看返回码
``` 


####查询角色权限
|   接口地址    |   boss/role/queryAuth        |
|   ---         |   ---                   |

```
//请求
{
 "role_id":x,       //必填-自己角色
 "company_id":x     //必填-自己公司
 "include_all":x    //选填-0或者不传:自己的权限 1:所有权限/用于上级查询管理下级权限
}

//回复
[
      {
         "module_id": 2,
         "module_name": "Broadcast",
         "url": "manage/main/main.html",
         "module_type": 0,
         "priority": 2300,
         "parent_id": 0,
         "status": 0,
         "permiss_id": 16,
         "insert_auth": 1,
         "update_auth": 1,
         "query_auth": 1
      },
      {
         "module_id": 3,
         "module_name": "设备管理",
         "url": "",
         "module_type": 0,
         "priority": 1900,
         "parent_id": 0,
         "status": 1,
         "permiss_id": 88,
         "insert_auth": 1,
         "update_auth": 1,
         "query_auth": 1
      }
]
``` 

####删除角色
|   接口地址    |   boss/role/delete         |
|   ---         |   ---                   |

```
//请求
{

    "role_id":x       //角色id

}

//回复
null 没有数据返回 看code是否成功
```

####修改角色权限
|   接口地址    |   boss/role/updateAuth        |
|   ---         |   ---                   |

```
//请求
{
    "key_id":x,                    //必填 角色权限ID 从接口queryAuth返回中的permiss_id字段值
    "status":x,                    //选填-1-可见 0-不可见
    "insert_auth":x,               //选填-新增权限
    "update_auth":x,               //选填-更新权限
    "query_auth":x                 //选填-查询可见权限
}

//回复
无回复 看结果
```

####新增角色权限
|   接口地址    |   boss/role/insertAuth        |
|   ---         |   ---                   |

```
//请求
{
    "role_id":x,                   //必填 权限ID 
    "module_id":x,                 //必填-模块ID
    "company_id":x,                //必填-公司ID
    "status":x,                    //必填-1-可见 0-不可见
    "insert_auth":x,               //必填-权限
    "update_auth":x,               //必填-权限
    "query_auth":x                 //必填-权限
}

//回复
无回复 看结果
```

###菜单管理
####获取菜单列表
|   接口地址    |   boss/module/query         |
|   ---         |   ---                   |

```
//请求
{
    "company_id":x -必填
}
以上值从登陆中获取

//回复
 [
      {
         "id": 2,
         "name": "设备管理",
         "moduleType": 0,
         "belongCompany": 1,
         "description": "",
         "priority": 1900,
         "status": 1,
         "sn": "shebeiguanli",
         "url": "",
         "parentId": 0
      },
      {
         "id": 4,
         "name": "系统管理",
         "moduleType": 0,
         "belongCompany": 1,
         "description": "",
         "priority": 1700,
         "status": 1,
         "sn": "xitongguanli",
         "url": "",
         "parentId": 0
      }
]
```

####新增菜单
|   接口地址    |   boss/module/insert         |
|   ---         |   ---                   |

```
//请求
{
    "name":"title",        //必填-菜单名称
    "description":"",      //选填-菜单描述说明
    "priority":x,          //必填-菜单权限决定排序位置
    "sn":"desc",           //必填-菜单名称的全拼字符串
    "url":"",              //选填-是否需要配置跳转的url
    "company_id":x         //必填-公司ID
    "parent_id":x          //必填-菜单父节点，如果是顶级菜单填写0
}

//回复
无内容，直接查看返回码
```

####修改菜单
|   接口地址    |   boss/module/update         |
|   ---         |   ---                   |

```
//请求
{
    "module_id":x,         //必填-module主键id
    "name":"title",        //选填-菜单名称
    "description":"",      //选填-菜单描述说明
    "priority":x,          //选填-菜单权限决定排序位置
    "sn":"desc",           //选填-菜单名称的全拼字符串
    "status":x,            //选填-0或者1,1=可见 0=不可见
    "url":"",              //选填-是否需要配置跳转的url
    "parent_id":x          //选填-菜单父节点，如果是顶级菜单填写0
} 

//回复
无内容，直接查看返回码
```

####删除菜单
|   接口地址    |   boss/module/delete         |
|   ---         |   ---                   |

```
//请求
{
 "module_id":x,         //必填-module主键id
} 

//回复
无内容，直接查看返回码
```

###系统设置
####新增系统设置
|   接口地址    |   boss/systemconf/addsystemconf         |
|   ---         |   ---                   |

```
//请求
{
    "type":"x",            //必填-配置类型
    "name":"",             //必填-配置名称 
    "value":"",            //选填-配置具体值
    "action":"",           //选填-配置目标action
    "status":1,            //选填-配置状态   0-弃用  1-启用  
    "parent_id":0          //选填-菜单父节点，如果是顶级菜单填写0
}

//回复
无内容，直接查看返回码
```

####查询系统设置
|   接口地址    |   boss/systemconf/querysystemconf         |
|   ---         |   ---                   |

```
//请求
type和name都是选填，如果都不填，则查询所有信息
{
    "type":"",           //选填-配置类型 
    "name":""            //选填-配置名称  
}

//回复
返回一个系统设置信息
{
    "config_id":x，
    "type":"ios",
    "name":"version",
    "value":"1.0.3",
    "action":"",
    "status":1,
    "parent_id":0,
    "updated":0,
    "created":0
}
```

####删除系统设置
|   接口地址    |   boss/systemconf/delsystemconf         |
|   ---         |   ---                   |

```
//请求
{
    "config_id":x           //必填-配置id 
}

//回复
无内容，直接查看返回码
```

####修改系统设置
|   接口地址    |   boss/systemconf/updatesystemconf         |
|   ---         |   ---                   |

```
//请求
{
    "config_id":x,            //必填-配置id 
    "type":"",                //选填-配置类型  
    "name":"",                //选填-配置名称
    "value":"",               //选填-配置具体值
    "action":"",              //选填-配置目标action
    "status":1,               //选填-配置状态  0-弃用 1-启用
    "parent_id":x             //选填-菜单父节点，如果是顶级菜单填写0
}

//回复
无内容，直接查看返回码
```

###设备信息
####新增设备信息
|   接口地址    |   boss/fisedevice/addfisedevice         |
|   ---         |   ---                   |

```
//请求
{
    "ime":"x"，                                                      //必填-设备IME号
    "account":"",               //必填-小位号-账号
    "depart_id":x,              //必填-公司/团体ID
    "type":x,                   //必填-设备类型 
    "mobile":"",                //选填-手机号
    "mark":""                   //选填-备注信息  
}

//回复
无内容，直接查看返回码
```

####查询设备信息
|   接口地址    |   boss/fisedevice/queryfisedevice         |
|   ---         |   ---                   |

```
//请求
{
    "page_no":x,                            //选填-当前页, 默认为第1页
    "page_size":x,                          //选填-每页记录数，默认20
    "param":
    {
        "ime":"",                      //选填-设备IME号
        "account":"",                 //选填-小位号-账号 
        "depart_id":x,                 //必填-公司/团体ID
        "status":x                     //选填-设备状态   0-出厂 1-激活  
    }
}

//回复
{
    "page_no": 1,
    "page_size": 20,
    "total_count": 89,
    "total_page_count": 5,
    "param": null,
    "extra_param": null,
    "result": [
        {
            "ime": "135790246811229",
            "mac": "49:04:22:f8:62:66",
            "code": "E5217ED8D7D4B40AF34FE02905CC39EC",
            "status": true,
            "account": "test_dev8",
            "type": 19,
            "mobile": "test_dev8",
            "mark": "",
            "updated": 1480406086,
            "created": 1480406086,
            "fise_id": 9,
            "depart_id": 1
        },
        {
            "ime": "135790246811230",
            "mac": "",
            "code": "",
            "status": true,
            "account": "test_dev9",
            "type": 20,
            "mobile": "test_dev9",
            "mark": "",
            "updated": 0,
            "created": 0,
            "fise_id": 15,
            "depart_id": 1
        },
        {
            "ime": "135790246811231",
            "mac": "",
            "code": "",
            "status": true,
            "account": "test_dev10",
            "type": 20,
            "mobile": "test_dev10",
            "mark": "",
            "updated": 0,
            "created": 0,
            "fise_id": 16,
            "depart_id": 1
        }
    ]
}
```

####删除设备信息
|   接口地址    |   boss/fisedevice/delfisedevice         |
|   ---         |   ---                   |

```
//请求
{
    "fise_id":x    //必填-设备ID
}

//回复
无内容，直接查看返回码
```  

####修改设备信息
|   接口地址    |   boss/fisedevice/updatefisedevice         |
|   ---         |   ---                   |

```
//请求
{
    "id":x,                     //必填-设备ID
    "ime":"x"，                 //选填-设备IME号
    "status":0,                 //选填-设备状态  0-出厂 1-激活 2-删除
    "account":"",               //选填-小位号-账号
    "depart_id":x,              //选填-部门ID
    "company_id":x,             //选填-公司/团体ID
    "type":x,                   //选填-设备类型 
    "mobile":"",                //选填-手机号
    "mark":""                   //选填-备注信息   	
}

//回复
无内容，直接查看返回码
```  

###设备配置
####查询设备配置
|   接口地址    |   boss/querydevice        |
|   ---         |   ---                   |

```
//请求
{
    "type":x,                   //必填-设备类型
    "company_id":x              //必填-公司ID
    "depart_id":x               //选填-部门ID
    "account":"",               //选填-设备账号
    "phone":"",                 //选填-设备号码
    "device_id":x               //选填-设备ID
}

//回复
//返回的信息会根据查询的设备的类型不同而不同  
//如果设备类型是25  返回儿童手表
{
    "base_info":
    {
        "device_id":105369,
        "master_id":105391,
        "group_id":187,             //家庭群组ID
        "mobile":"13510730625",     //管理员号码
        "alarm_takeoff":1,
        "alarm_battery":1,
        "alarm_poweroff":0,
        "step_mode":1,
        "alarm_call":1,
        "electricize":0,
        "mode":1,
        "bell_mode":1,
        "light_time":3,
        "updated":1493289555,
        "created":1490017302
    },
    "electri_fence":
    [
        {
        "fence_id":x, //ID
        "device_id":x,
        "status":x,   //0-关闭 1-开启
        "lng":"",
        "lat":"",
        "radius":x    //电子围栏半径
        "mark":"",    //名称#地址
        "updated":1493289555,
        "created":1490017302
        },
    ],
    "control":
    [
        {
        "id":x,
        "device_id":x,
        "auth_type":x //权限类型:0-管理员 1-亲情 2-白名单 3-紧急号码,
        "mobile":"",  //管理员此处是ID 其他情况是联系号码
        "status":x,   //2-关闭 1-开启
        "name":"",    //名称
        "updated":x,
        "created":x
        }
    ],
    "crontab":
    [
        {
          "task_id":x,
          "device_id":x,
          "task_type":x,    //任务类型 0-上课记录 1-关爱记录
          "task_name":"",
          "task_param":"",
          "begin_time":"",
          "end_time":"",
          "status":"",     //2-关闭 1-开启
          "repeat_mode":"",
          "repeat_value":"",
          "updated":"",
          "created":""
        }
    ]
}
//如果设备类型是19   返回卡片机
//以下结构是base_info对应信息，其他同25但是无crontab
{
    "device_id":105369,
    "master_id":105391,
    "group_id":187,
    "mobile":"13510730625",
    "alarm_pen":1,
    "alarm_battery":1,
    "alarm_poweroff":0,
    "listen_silent":1,
    "alarm_call":1,
    "electricize":0,
    "mode":1,
    "bell_mode":1,
    "updated":1493289555,
    "created":1490017302
}
//如果设备类型是23   返回电动车
//以下结构是base_info对应信息，其他同25但是无crontab
{
    "device_id":105369,
    "master_id":105391,
    "mobile":"13510730625",
    "alarm_pen":1,
    "alarm_battery":1,
    "alarm_poweroff":0,
    "listen_silent":1,
    "alarm_call":1,
    "mode":1,
    "bell_mode":1,
    "speed":x,
    "speed_limit",x,
    "updated":x,
    "created":x
}
//如果设备类型是21   返回网络摄像头
//以下结构是base_info对应信息，其他同25但是无crontab
{
    "device_id":105392,
    "master_id":105366,
    "group_id":301,
    "mobile":"13714738507",
    "alarm_power":0,
    "alarm_battery":1,
    "alarm_off":1,
    "alarm_move":0, 
    "electricize":0,
    "mode":1,
    "updated":x,
    "created":x
}    
```

###设备统计
####设备激活情况统计
|   接口地址    |   boss/devicecount        |
|   ---         |   ---                   |

```
//请求
{
    "depart_id":x  //选填-部门ID
    "company_id":x //必填-公司ID
}

//回复
{
    "onlineDevice":8,                //在线的设备数
    "activeDevice":38,               //激活的设备数
    "onlineXM":2,                    //在线的小位
    "activeXM":70                    //激活的小位
}
```

###公司设备头像
####新增配置
|   接口地址    |   /boss/departconf/addimdepartconfig        |
|   ---         |   ---                   |

```
//请求
{
    "company_id":x,                  //必填-公司/团体ID  
    "client_type":x,                 //必填-设备类型
    "avatar":""                      //选填-默认头像
}

//回复
无内容，直接查看返回码
```  

####查询配置 
|   接口地址    |   /boss/departconf/queryimdepartconfig        |
|   ---         |   ---                   |

```
//请求
{
    "company_id":x,                  //必填-公司/团体ID  
    "client_type":x,                 //选填-设备类型
}

//回复
[
    {
      "config_id":x,                   
      "company_id":x,   
      "client_type":x,
      "avatar":"",
      "created":x
    },
    {
      "config_id":x,                   
      "company_id":x,
      "client_type":x,
      "avatar":"",
      "created":x
    }
] 
```  

####删除配置 
|   接口地址    |   /boss/departconf/delimdepartconfig        |
|   ---         |   ---                   |

```
//请求
{
    "config_id":x    //必填-公司设备配置ID 
}

//回复
无内容，直接查看返回码
```

####修改配置 
|   接口地址    |   /boss/departconf/updateimdepartconfig        |
|   ---         |   ---                   |

```
//请求
{
    "config_id":x                    //必填-公司设备配置ID
    "avatar":""                      //选填-默认头像
} 

//回复
无内容，直接查看返回码
```

###设备类型
####添加类型
|   接口地址    |   boss/clienttype/addclienttype        |
|   ---         |   ---                   |

```
//请求
{
 "client_type":x,                   //必填-设备类型
 "client_name":""                   //必填-设备类型名称
}

//回复
无内容，直接查看返回码
```

####查询配置
|   接口地址    |   boss/clienttype/queryclienttype        |
|   ---         |   ---                   |


```
//请求
都不填则全部查询
{
 "client_type":x,                   //选填-设备类型
 "client_name":""                   //选填-设备类型名称
}

//回复
[
    {
        "type_id":x,
        "client_type":x,
        "client_name":"",
        "created":x
    },
    {
        "type_id":x,
        "client_type":x,
        "client_name":"",
        "created":x
    }
]
```   

####删除配置
|   接口地址    |   boss/clienttype/delclienttype        |
|   ---         |   ---                   |

```
//请求
{
    "type_id":x    //必填-配置ID
}

//回复
无内容，直接查看返回码
```

####修改配置
|   接口地址    |   boss/clienttype/updateclienttype        |
|   ---         |   ---                   |

```
//请求
{
 "type_id":x,                     //必填-配置ID
 "client_type":x,                 //选填-设备类型
 "client_name":""                 //选填-设备类型名称
}

//回复
无内容，直接查看返回码
``` 

###报表统计
####设备事件查询(分页查询)
|   接口地址    |   boss/event/query        |
|   ---         |   ---                   |

```
//请求
{
    "page_no":x,                            //选填-当前页, 默认为第1页
    "page_size":x,                          //选填-每页记录数，默认20 
    "param":
    {
        "admin_id":x,                  //必填-管理员ID
        "account":""	                 //必填-设备拼音
    }
}

//回复
{
    "page_no": 3,
    "page_size": 20,
    "total_count": 388,
    "total_page_count": 20,
    "param": null,
    "extra_param": null,
    "result": [
        {
        "id": 352,
        "userId": 105392,
        "eventKey": 0,
        "locationX": "22.6850304676964",
        "locationY": "113.985455450724",
        "locationFrom": 2,
        "battery": 1,
        "sq": 4,
        "eventLevel": 2,
        "param": "",
        "updated": 1496476310,
        "created": 1496476310
        },
        {
        "id": 351,
        "userId": 105392,
        "eventKey": 9,
        "locationX": "",
        "locationY": "",
        "locationFrom": 0,
        "battery": 0,
        "sq": 0,
        "eventLevel": 1,
        "param": "",
        "updated": 1496472540,
        "created": 1496472540
        }
    ]
}
```
		
####群消息查询
|   接口地址    |   boss/groupmessage/query        |
|   ---         |   ---                   | 

```
//请求
{
    "admin_id"x,
    "name":""                     //必填-管理员id,群的名称
}

//回复
[
    {
        "id": 29,
        "groupid": 9,
        "userid": 105325,
        "msgid": 1,
        "content": "xHiMKYHeH28J8UhORPMlAnWXrZFfV+UBo37vcxL/NUY=",
        "type": 17,
        "status": 0,
        "updated": 1480581461,
        "created": 1480581461
    },
    {
        "id": 29,
        "groupid": 9,
        "userid": 105325,
        "msgid": 1,
        "content": "xHiMKYHeH28J8UhORPMlAnWXrZFfV+UBo37vcxL/NUY=",
        "type": 17,
        "status": 0,
        "updated": 1480581461,
        "created": 1480581461
    }
]
```

####回话消息查询
|   接口地址    |   boss/sessionmessage/query        |
|   ---         |   ---                   |

```
//请求
{
    "small_id"x,
    "big_id":x                   //必填-回话用户的ID 
}

//回复
[
    {
        "id": 1,
        "relateid": 2,
        "fromid": 105321,
        "toid": 105322,
        "msgid": 1,
        "content": "bFkYRtErdXd/IxbUWaRKpnWXrZFfV+UBo37vcxL/NUY=",
        "audiotype": 0,
        "type": 1,
        "status": false,
        "updated": 1480389148,
        "created": 1480389148
    },
    {
        "id": 2,
        "relateid": 2,
        "fromid": 105321,
        "toid": 105322,
        "msgid": 2,
        "content": "bFkYRtErdXd/IxbUWaRKpnWXrZFfV+UBo37vcxL/NUY=",
        "audiotype": 0,
        "type": 1,
        "status": false,
        "updated": 1480389329,
        "created": 1480389329
    }
]
```

###版本控制
####添加设备新版本
|   接口地址    |   boss/deviceversion/add        |
|   ---         |   ---                   |

```
//请求
{
 "company_id"x,                    //必填-公司ID
 "dev_type":x,                     //必填-设备类型
 "dev_version":"",                 //必填-最新设备固件版本号
 "update_url":"",                  //选填-更新下载地址
 "version_info":""                 //选填-版本信息
}

//回复
无内容，直接查看返回码
``` 

####查询设备新版本
|   接口地址    |   boss/deviceversion/query        |
|   ---         |   ---                   |

```
//请求
{
 "company_id":x,                    //必填-公司ID
 "dev_type":x                       //选填-设备类型
}

//回复
[
    {
    "version_id":x,
    "depart_id":x,
    "dev_type":x,
    "dev_version":"",
    "status":0,
    "version_info":"",
    "update_url":""
    },
    {
    "version_id":x,
    "depart_id":x,
    "dev_type":x,
    "dev_version":"",
    "status":0,
    "version_info":"",
    "update_url":""
    }
]
```

####删除设备新版本
|   接口地址    |   boss/deviceversion/del        |
|   ---         |   ---                   |

```
//请求
{
    "version_id":x                  //必填-设备版本ID
}

//回复
无内容，直接查看返回码
``` 

####修改设备新版本
|   接口地址    |   boss/deviceversion/update        |
|   ---         |   ---                   |  

```
//请求
{
 "version_id":x,                  //必填-设备版本ID
 "dev_version":"",				  //选填-最新设备固件版本号	
 "status":0,                      //选填-0 不可用    1 可用
 "version_info":"",               //选填-版本信息
 "update_url":""                  //选填-更新下载地址
} 

//回复
无内容，直接查看返回码
```

###用户意见 
####新增用户意见
|   接口地址    |   boss/suggest/add        |
|   ---         |   ---                   |

```
//请求
{
 "user_id":x,                     //必填-用户id
 "uname":"",                      //必填-用户名
 "suggestion":"",                 //选填-意见内容 
 "contact":""                     //选填-联系方式
}

//回复
无内容，直接查看返回码
``` 

####查询用户意见
|   接口地址    |   boss/suggest/query        |
|   ---         |   ---                   |

```
//请求
不填，则查询所有
{
    "param":
    {
        "uname":""               //选填-用户名
    }
}

//回复
[
    {
    "suggest_id":x,
    "user_id":x,
    "uname":"",
    "status":0,
    "suggestion":"",
    "contact":"",
    "created":x,
    "updated":x
    },
    {
    "suggest_id":x,
    "user_id":x,
    "uname":"",
    "status":0,
    "suggestion":"",
    "contact":"",
    "created":x,
    "updated":x
    }
]
```

####删除用户意见
|   接口地址    |   boss/suggest/del        |
|   ---         |   ---                   |

```
//请求
{
    "suggest_id":x                  //必填-ID
}

//回复
无内容，直接查看返回码
``` 

####修改用户意见
|   接口地址    |   boss/suggest/update        |
|   ---         |   ---                   |

```
//请求
{
 "suggest_id":x,                  //必填-ID
 "user_id":x,                     //选填-用户id
 "uname":"",                      //选填-用户名
 "status":0,                      //选填-记录状态 0 :初始正常 1:已经回复
 "suggestion":"",                 //选填-意见内容   
 "contact":""                     //选填-联系方式 
 }

//回复
无内容，直接查看返回码
```

###用户查询
|   接口地址    |   boss/user/query        |
|   ---         |   ---                   |

```
//请求
不填，则查询所有
{
 "page_no":x,                             //选填-当前页, 默认为第1页
 "page_size":x,                           //选填-每页记录数，默认20
 "param":{
         "domain":"",                    //选填-用户小位号
         "phone":"",                       //选填-手机
         "user_id":x,                     //选填-用户ID
         "online_status":x                //选填-用户在线状态   1-在线 0-离线
         }
}

//回复
{
    "page_no": 1,
    "page_size": 20,
    "total_count": 107,
    "total_page_count": 6,
    "param": null,
    "extra_param": null,
    "result": 
    [
        {
        "id": 105319,
        "sex": 1,
        "name": "13714738507",
        "domain": "0",
        "nick": "铭记在心",
        "password": "c2cb6c5c0f2bfc2af2a479fdf9f07b5b",
        "salt": "8551",
        "province": "",
        "city": "",
        "country": "",
        "phone": "13714738507",
        "email": "123@123.com",
        "avatar": "g0/000/000/1484449958650018_139730919216.jpg",
        "height": 0,
        "weight": 0,
        "birthday": "0",
        "type": 18,
        "departid": 3,
        "status": 0,
        "created": 1480383837,
        "updated": 1487580230,
        "pushShieldStatus": 0,
        "signInfo": "",
        "lng": "113.990849",
        "lat": "22.681539",
        "battery": 90,
        "sq": 0,
        "friendNeedAuth": 1,
        "loginSafeSwitch": 0,
        "searchAllow": 1,
        "onlineStatus": 0,
        "lastOnlineTime": 0
        },
        {
        "id": 105320,
        "sex": 1,
        "name": "18565806571",
        "domain": "18565806571",
        "nick": "18",
        "password": "10cf1d003cf153547d6aa7905e8547fa",
        "salt": "4502",
        "province": "广东省",
        "city": "深圳市",
        "country": "中国",
        "phone": "18565806571",
        "email": "704834364@qq.com",
        "avatar": "g0/000/000/1493197615818373_140172801484.jpg",
        "height": 0,
        "weight": 0,
        "birthday": "0",
        "type": 1,
        "departid": 3,
        "status": 0,
        "created": 1480384505,
        "updated": 1495877091,
        "pushShieldStatus": 0,
        "signInfo": "hhjjj",
        "lng": "113.990832",
        "lat": "22.681959",
        "battery": 37,
        "sq": 4,
        "friendNeedAuth": 1,
        "loginSafeSwitch": 0,
        "searchAllow": 1,
        "onlineStatus": 0,
        "lastOnlineTime": 1495883709
        }
    ]
}

``` 
###组织/公司管理
####公司新增
|   接口地址    |   boss/organization/insert        |
|   ---         |   ---                   |

```
//请求
{
 "name":"",                    //必填-公司名称
 "address":"",                 //选填-公司地址
 "contact":"",                 //选填-公司联系方式
 "email":"",                   //选填-公司email
 "describtion":"",             //选填-公司简介   
 "home":""                     //选填-公司主页 默认为index.html        
}

//回复
无内容，直接查看返回码
```

####公司查询
|   接口地址    |   boss/organization/query        |
|   ---         |   ---                   |

```
//请求
{
    "name":""                    //选填-不填，则查询所有[名称支持模糊查询]
}

//回复
[
    {
        "id": 1,
        "name": "沸石智能有限公司",
        "address": "沸石智能有限公司",
        "contact": "100",
        "email": "0",
        "describtion": "",
        "status": 1,
        "created": 0,
        "updated": 0
    }
]
```

####公司删除
|   接口地址    |   boss/organization/del        |
|   ---         |   ---                   |


```
//请求
{
    "id":x                        //必填-公司id
}

//回复
无内容，直接查看返回码
```

####公司修改
|   接口地址    |   boss/organization/update        |
|   ---         |   ---                   |

```
//请求
{
 "id":x,                       //必填-公司id
 "name":"",                    //选填-公司名称
 "address":"",                 //选填-公司地址
 "contact":"",                 //选填-公司联系方式
 "email":"",                   //选填-公司email
 "describtion":"",             //选填-公司简介 
 "home":"",                    //选填-公司主页
}

//回复
无内容，直接查看返回码
```

###小位号管理 
####新增登记
|   接口地址    |   boss/accountmanage/add        |
|   ---         |   ---                   |

```
//请求
{
 "company_id":x,                //必填-公司iD 
 "description":"",             //选填-描述
 "begin_account":"",           //选填-设备起始编号
 "end_account":"",             //选填-设备结束编号
 "status":1                    //选填-0-初始 1-已经出厂    
}

//回复
无内容，直接查看返回码
```

####查询记录
|   接口地址    |   boss/accountmanage/query        |
|   ---         |   ---                   |


```
//请求
{
    "company_id":x                 //必填-公司id
}

//回复
[
    {
        "id": 1,
        "description": "试产100小位号",
        "status": 1,
        "created": 0,
        "depart_id": 1,
        "begin_account": "W0101000001",
        "end_account": "W0101000099"
    },
    {
        "id": 2,
        "description": "正式产小位号段",
        "status": 0,
        "created": 0,
        "depart_id": 1,
        "begin_account": "W0101003000",
        "end_account": "W0101006100"
    }
]
```

####删除记录
|   接口地址    |   boss/accountmanage/del        |
|   ---         |   ---                   |

```
//请求
{
    "id":x                    //必填-id
}

//回复
无内容，直接查看返回码
```

####修改更新
|   接口地址    |   boss/accountmanage/update        |
|   ---         |   ---                   |

```
//请求
{
 "id":2,                                //必填-id
 "description": "",                     //选填-描述
 "status": 0,                           //选填-设备状态
 "depart_id": 1,                        //选填-公司id
 "begin_account": "W0101003000",        //选填-设备起始编号  
 "end_account": "W0101006100"           //选填-设备结束编号 
} 

//回复
无内容，直接查看返回码
```

###报表统计
####每天注册/激活数
|   接口地址    |   boss/report/activate        |
|   ---         |   ---                   |

```
//请求
{
    "company_id":1,           //所属公司
    "begin_date":"20160706",//开始查询日期
    "end_date":"20170630"   //结束查询日期
}

//回复
{
    "20161129": 8,
    "20170306": 2,
    "20170320": 1,
    "20170328": 2,
    "20170329": 2,
    "20170405": 1,
    "20170406": 1,
    "20170413": 1,
    "20170414": 1,
    "20170419": 2,
    "20170421": 1,
    "20170425": 1,
    "20170512": 1,
    "20170515": 2,
    "20170517": 1,
    "20170520": 5,
    "20170523": 1,
    "20170524": 4
}
```

####统计概况
|   接口地址    |   boss/report/dashboard        |
|   ---         |   ---                   |

```
//请求
{
    "company_id":1,           //所属公司
}

//回复
{
    "province": 
    {
        "未知": 37
    },
    "sex": 
    {
        "男": 28,
        "未知": 9
    },
    "type": 
    {
        "19": 15,
        "20": 2,
        "21": 5,
        "25": 15
    }
}
```

####统计日消息总数
|   接口地址    |   boss/report/messagecount        |
|   ---         |   ---                   |

```
//请求
{
    "daytime":""              //必填-日期字符串  例如 2017-07-20
}    

//回复
{
    "count":x            
}
```

####统计消息类型分布
|   接口地址    |   boss/report/messagetypecount        |
|   ---         |   ---                   |  

```
//请求
{}               //不需要请求参数

//回复
{
    "code": 0,
    "msg": "ok",
    "data": 
        [
            {
                "keyName": 17,
                "keyValue": 31
            },
            {
         		"keyName": 1,
         		"keyValue": 138
            },
            {
         		"keyName": 23,
         		"keyValue": 30
            },
            {
         		"keyName": 24,
         		"keyValue": 3
      		},
            {
         		"keyName": 25,
        		"keyValue": 42
      		}    
       ]
}
```

###短信模板
####添加短信模板
|   接口地址    |   boss/sms/add        |
|   ---         |   ---                   |

```
//请求
{
 "platfrom_id":x,                   //必填-短信平台ID
 "action":x,                        //选填-对应场景号
 "action_name":"",                  //选填-场景名称
 "template_name":""                 //选填-短信模板
 }

//回复
无内容，直接查看返回码
```

####查询短信模板
|   接口地址    |   boss/sms/query        |
|   ---         |   ---                   |

```
//请求
不填，则查询所有
{
    "action":x                       //选填-对应场景号
}

//回复
[
    {
        "id": 5,
        "action": 2,
        "updated": 1499130741,
        "created": 1499130741,
        "platfrom_id": 1,
        "action_name": "ww",
        "template_name": "123"
    }
]
```

####修改短信模板
|   接口地址    |   boss/sms/update        |
|   ---         |   ---                   |

```
//请求
{
 "id":x,                           //必填-短信模板ID
 "platfrom_id":x,                  //必填-短信平台ID
 "action":x,                       //选填-对应场景号
 "action_name":"",                 //选填-场景名称
 "template_name":""                //选填-短信模板
 }

//回复
无内容，直接查看返回码
```

####删除短信模板
|   接口地址    |   boss/sms/del        |
|   ---         |   ---                   |

```
//请求
{
    "id":x                          //必填-短信模板ID
}

//回复
无内容，直接查看返回码
```

###短信平台
####新增短信平台
|   接口地址    |   boss/smsplatfrom/add        |
|   ---         |   ---                   |

```
//请求
{
 "platfrom_name":"",               //必填-短信平台名称
 "status":1,                       //选填-0-弃用 1-使用  默认为1
 "config":""                       //选填-短信平台配置 
 }

//回复
无内容，直接查看返回码
```

####查询短信平台
|   接口地址    |   boss/smsplatfrom/query        |
|   ---         |   ---                   |

```
//请求
不填则查询所有
{
    "platfrom_name":""                //选填-短信平台名称
}

//回复
[
    {
        "status": false,
        "config": "{\"liuzhaoxin\":\"lihai\"}\n",
        "updated": 0,
        "created": 0,
        "smsplatfrom_id": 8,
        "platfrom_name": "1112"
    },
    {
        "status": true,
        "config": "{}\n",
        "updated": 0,
        "created": 0,
        "smsplatfrom_id": 9,
        "platfrom_name": "afds"
    }
]
```

####修改短信平台
|   接口地址    |   boss/smsplatfrom/update        |
|   ---         |   ---                   |

```
//请求
{
 "smsplatfrom_id":x,                //必填-短信平台ID
 "platfrom_name":"",                //必填-短信平台名称
 "status":1,                        //选填--0-弃用 1-使用
 "config":""                        //选填-短信平台配置
 }   

//回复
无内容，直接查看返回码
```

####删除短信平台
|   接口地址    |   boss/smsplatfrom/del        |
|   ---         |   ---                   |

```
//请求
{
    "smsplatfrom_id":x               //必填-短信平台ID
}

//回复
无内容，直接查看返回码
```

###设备通讯录
####添加通讯号码
|   接口地址    |   boss/devicecontrol/add        |
|   ---         |   ---                   |

```
//请求
{
 "device_id":x,                   //必填-设备号       
 "auth_type":x,                   //必填-权限类型:0-管理员 1-亲情 2-白名单 3-紧急号码
 "mobile":"",                     //必填-电话号码
 "status":x,                      //选填-1  可用    0  不可用
 "name":""                        //选填-称呼
 }              

//回复
无内容，直接查看返回码
```

####查询通讯号码
|   接口地址    |   boss/devicecontrol/query        |
|   ---         |   ---                   |

```
//请求
{
 "name":"",                      //必填-用户名
 "mobile":""                     //选填-电话号码
}

//回复
[
    {
        "id": 402,
        "mobile": "123",
        "status": 1,
        "name": "哥哥",
        "updated": 1499651248,
        "created": 1499651248,
        "device_id": 1,
        "auth_type": 1
    }
]
``` 

####查询通讯号码
|   接口地址    |   boss/devicecontrol/update        |
|   ---         |   ---                   |

```
///请求
{
 "id":x,                        //必填-设备通讯ID
 "device_id":x,                 //必填-设备号
 "auth_type":x,                 //必填-权限类型:0-管理员 1-亲情 2-白名单 3-紧急号码
 "mobile":"",                   //必填-电话号码
 "status":x,                    //选填-1  可用    0  不可用
 "name":""                      //选填-称呼
 }

//回复
无内容，直接查看返回码
```

####查询通讯号码
|   接口地址    |   boss/devicecontrol/del        |
|   ---         |   ---                   |

```
//请求
{
    "id":x                        //必填-设备通讯ID 
}

//回复
无内容，直接查看返回码
```

###设备闹钟
####添加闹钟
|   接口地址    |   boss/devicecrontab/add        |
|   ---         |   ---                   |

```
//请求
{
 "device_id":x,                  //必填-设备号       
 "task_type":1,                  //选填-任务类型 0-上课记录 1-关爱记录
 "task_name":"",                 //选填-任务标题
 "task_param":"",                //选填-任务参数
 "begin_time":"",                //选填-开始时间
 "end_time":"",                  //选填-结束时间
 "status":1,                     //选填-响铃模式 0-关闭 1-开启 3-删除
 "repeat_mode":1,                //选填-重复模式0-关闭 1-开启  
 "repeat_value":""               //选填-重复时间字符串
 }

//回复
无内容，直接查看返回码
```

####查询闹钟
|   接口地址    |   boss/devicecrontab/query        |
|   ---         |   ---                   |

```
//请求
{
    "name":""                      //必填-用户名
}

//回复
[
    {
        "status": 3,
        "updated": 1492067209,
        "created": 1491788486,
        "task_id": 14,
        "device_id": 105370,
        "task_type": 1,
        "task_name": "吃饭",
        "task_param": "14:28",
        "begin_time": "18:59,19:00",
        "end_time": "18:59,19:00",
        "repeat_mode": 0,
        "repeat_value": ""
    }
]
```

####修改闹钟
|   接口地址    |   boss/devicecrontab/update        |
|   ---         |   ---                   |

```
//请求
{
 "task_id":x,                    //必填-闹钟ID
 "task_type":1,                  //选填-任务类型 0-上课记录 1-关爱记录
 "task_name":"",                 //选填-任务标题
 "task_param":"",                //选填-任务参数
 "begin_time":"",                //选填-开始时间
 "end_time":"",                  //选填-结束时间
 "status":1,                     //选填-响铃模式 0-关闭 1-开启 3-删除
 "repeat_mode":1,                //选填-重复模式0-关闭 1-开启  
 "repeat_value":""               //选填-重复时间字符串
 }

//回复
无内容，直接查看返回码
```

####删除闹钟
|   接口地址    |   boss/devicecrontab/del        |
|   ---         |   ---                   |

```
//请求
{
    "task_id":x                         //必填-闹钟ID
}

//回复
无内容，直接查看返回码
```

###电子围栏
####新增电子围栏
|   接口地址    |   boss/electricfence/add        |
|   ---         |   ---                   |

```
//请求
{
 "device_id":x,                   //必填-设备ID
 "status":1,                      //选填-状态 1-开启 2-关闭
 "lng":"",                        //选填-经度
 "lat":"",                        //选填-纬度
 "radius":10,                     //选填-半径
 "mark":""                        //选填-地址
}

//回复
无内容，直接查看返回码
```

####查询电子围栏
|   接口地址    |   boss/electricfence/query        |
|   ---         |   ---                   |

```
//请求
{
    "name":"watch_dev0"              //必填-用户名
}

//回复
[
    {
        "status": 3,
        "lng": "113.993240",
        "lat": "22.683294",
        "radius": 300,
        "mark": " 工业园路375号##广东省深圳市宝安区大浪街道工业园路375号",
        "updated": 1492067209,
        "created": 1491822495,
        "fence_id": 84,
        "device_id": 105370
    }
]
```

####修改电子围栏
|   接口地址    |   boss/electricfence/update        |
|   ---         |   ---                   |

```
//请求
{
 "fence_id":x,                      //必填-围栏ID
 "device_id":x,                     //必填-设备ID
 "status":1,                        //选填-状态 1-开启 2-关闭
 "lng":"",                          //选填-经度
 "lat":"",                          //选填-纬度
 "radius":x,                        //选填-半径
 "mark":""                          //选填-地址
 }

//回复
无内容，直接查看返回码
```

####删除电子围栏
|   接口地址    |   boss/electricfence/del        |
|   ---         |   ---                   |

```
//请求
{
    "fence_id":255                     //必填-围栏ID
}

//回复
无内容，直接查看返回码
```

###消息类型
####消息类型查询
|   接口地址    |   boss/messagetype/query        |
|   ---         |   ---                   |

```
//请求
不填，则查询所有
{
    "type":x               //选填-消息类型对应ID
}    

//回复
{
   "code": 0,
   "msg": "ok",
   "data": [
      {
         "msg_type": 1,
         "msg_name": "文字-单"
      },
      {
         "msg_type": 2,
         "msg_name": "语音-单"
      },
      {
         "msg_type": 4,
         "msg_name": "小视频-单"
      },
      {
         "msg_type": 5,
         "msg_name": "通知-单"
      },
      {
         "msg_type": 6,
         "msg_name": "通知-群"
      },
      {
         "msg_type": 7,
         "msg_name": "抓拍-单"
      },
      {
         "msg_type": 8,
         "msg_name": "抓拍-群"
      },
      {
         "msg_type": 9,
         "msg_name": "录音-单"
      },
      {
         "msg_type": 16,
         "msg_name": "录音-群"
      },
      {
         "msg_type": 17,
         "msg_name": "文字-群"
      },
      {
         "msg_type": 18,
         "msg_name": "语音-群"
      }
   ]
}
```

### 公司部门管理
####查询部门
|   接口地址    |   boss/depart/query        |
|   ---         |   ---                   |

```
//请求
{
    "creator_id":x,   //当前用户Id
    "depart_name":"应急指挥中心", //部门名称
}   

//回复
[
    {
        "depart_id": 3,  //部门Id
        "company_id":1,  //所属公司Id
	    "depart_name":"应急指挥中心", //部门名称
	    "parent_id":1,    //0-顶级部门 x-上级部门ID
	    "status":x,       //状态
	    "created": 0,     //创建时间
        "updated": 0      //修改时间
    }
]
```

####新增部门
|   接口地址    |   boss/depart/insert        |
|   ---         |   ---                   |

```
//请求
{
        "creator_id": 3,  //创建者Id
        "company_id":1,  //所属公司Id
	    "depart_name":"应急指挥中心", //部门名称
	    "parent_id":1,    //0-顶级部门 x-上级部门ID	   
}

//回复
无内容，直接查看返回码
``` 


####修改部门
|   接口地址    |   boss/depart/update        |
|   ---         |   ---                   |

```
//请求
{
 		"depart_id": 3,  //部门Id
	    "depart_name":"应急指挥中心", //部门名称
	    "status":x,       //状态
}

//回复
无内容，直接查看返回码
``` 


####删除部门
|   接口地址    |   boss/depart/delete         |
|   ---         |   ---                   |

```
//请求
{
    "depart_id":x       //部门id
}

//回复
null 没有数据返回 看code是否成功









###我的应用
  ++++++++++++++++++++我的应用的主页++++++++++++++++++++
  1.加载APP栏

   |   接口地址    |   boss/store/appAll        |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                        |

   ####请求
    {
     "page_no":x
    } 
  
  ####返回   
      
   {
   "code": 0,
   "msg": "ok",
   "data": {
      "page_no": 1,
      "page_size": 1,
      "total_count": 5,
      "total_page_count": 5,
      "param": null,
      "extra_param": null,
      "result": [
         {
            "appId": 1,
            "appName": "安卓商店",
            "download": "http://shouji.360tpcdn.com/170809/b1db53b15738fa3b400745049d2015c1/com.juying.androidmarket_20000001.apk",
            "description": "安卓市场",
            "version": "1.2.3",
            "versionCode": "123",
            "category": "系统工具",
			"orientation": 0,
            "star": "4",
            "icon": "http://p17.qhimg.com/t0128b0b333e05c2db2.png",
            "iconType": 1,
            "size": "2.07M"
         }
      ],
      "hasMore": true
   }
}

2.加载广告栏
   |   接口地址    |   boss/store/advertAll        |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                        |
####请求
{

}
####返回 

{
   "code": 0,
   "msg": "ok",
   "data": [
      {
         "id": 1,
         "name": "天天秀秀",
         "type": "app",
         "typeId": 1,
         "image": "http://ww.baidu.com?asdja=wwwhj",
         "delayTime": 11
      },
      {
         "id": 2,
         "name": "王尼玛",
         "type": "app",
         "typeId": 1,
         "image": "http://www.wagnniamm.com?dajshf=wangnima ",
         "delayTime": 11
      }
   ]
}



3.加载频道栏
   |   接口地址    |   boss/store/channelAll      |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                        |
####请求
{

}

####返回 
{
   "code": 0,
   "msg": "ok",
   "data": [
      {
         "name": "精品推荐",
         "textColor": "#ffffff",
         "color": "image:http://pic.58pic.com/5",
         "image": "http://192.168.2.120:8080/c1.png",
         "app_list": null
      },
      {
         "name": "学习教育",
         "textColor": "#ffffff",
         "color": "color:06090b",
         "image": "http://192.168.2.120:8080/c1.png",
         "app_list": null
      },
      {
         "name": "女生频道",
         "textColor": "#ffffff",
         "color": "color:f758c1",
         "image": "http://192.168.2.120:8080/c1.png",
         "app_list": null
      },
      {
         "name": "男生频道",
         "textColor": "#ffffff",
         "color": "color:09c9b5",
         "image": "http://192.168.2.120:8080/c1.png",
         "app_list": null
      },
      {
         "name": "建议安装",
         "textColor": "#ffffff",
         "color": "#ffffff",
         "image": "http://192.168.2.120:8080/c1.png",
         "app_list": null
      }
   ]
}
4.搜索App
   （1）加载两条数据（

   |   接口地址    |   boss/store/simpleSearch   |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                        |
####请求
{
   "app_name":"超级课程表"    
}

####返回 
{
   "code": 0,
   "msg": "ok",
   "data": [
      {
         "appName": "超级课程表",
         "download": "http://shouji.360tpcdn.com/170831/4fda99a5a2e9a414708babeb308b93bc/com.xtuone.android.syllabus_107.apk",
         "description": "课程学习",
         "version": "9.1.2",
         "versionCode": "111",
         "category": "日常工具",
         "star": "4.8",
         "icon": "http://p18.qhimg.com/t0165cf86f621865736.png",
         "iconType": 1,
         "size": "19.32M",
         "packageName": "com.fise.app",
         "appId": 3
      }
   ]
}

（2）点击进去，加载多条数据（分页）

  |   接口地址    |   boss/store/allSearch   |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON        
  ####请求
{
  "page_no":1,
  "param":{
             "app_name":"超级课程表"
          }
 }

####返回 
{
   "code": 0,
   "msg": "ok",
   "data": {
      "page_no": 1,
      "page_size": 10,
      "total_count": 1,
      "total_page_count": 1,
      "param": null,
      "extra_param": null,
      "hasMore": false,
      "result": [
         {
            "appName": "超级课程表",
            "download": "http://shouji.360tpcdn.com/170831/4fda99a5a2e9a414708babeb308b93bc/com.xtuone.android.syllabus_107.apk",
            "description": "课程学习",
            "version": "9.1.2",
            "versionCode": "111",
            "category": "日常工具",
            "star": "4.8",
            "icon": "http://p18.qhimg.com/t0165cf86f621865736.png",
            "iconType": 1,
            "size": "19.32M",
            "packageName": "com.fise.app",
            "appId": 3
         }
      ]
   }
}
++++++++++++++热门搜索++++++++

   |   接口地址    |   boss/store/hotSearch  |
   |   ---    |   ---                |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON        
  ####请求
{
 
}

####返回 

{
   "code": 0,
   "msg": "ok",
   "data": [
      "自拍神器",
      "超级课程表",
      "超级课程",
      "超级"
   ]
}




+++++++++++++++在频道的分类页面+++++++++++++++++++
1.加载该频道下的app列表
  
   |   接口地址    |   boss/store/channel    |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                        |
####请求
{
  "page_no":X,
  "param":{
          "channel_id":X
           }
 }
####返回 
{
   "code": 0,
   "msg": "ok",
   "data": {
      "page_no": 1,
      "page_size": 10,
      "total_count": 3,
      "total_page_count": 1,
      "param": null,
      "extra_param": null,
      "result": [
         {
            "appId": 1,
            "appName": "安卓商店",
            "download": "http://shouji.360tpcdn.com/170809/b1db53b15738fa3b400745049d2015c1/com.juying.androidmarket_20000001.apk",
            "description": "安卓市场",
            "version": "1.2.3",
            "versionCode": "123",
            "category": "系统工具",
            "star": "4.6",
            "icon": "http://p17.qhimg.com/t0128b0b333e05c2db2.png",
            "iconType": 1,
            "size": "2.07M",
            "orientation": 0
         },
         {
            "appId": 2,
            "appName": "自拍神器",
            "download": "http://shouji.360tpcdn.com/170726/0a4b5a3e1e9eec332b7bffd9b2064404/com.thundersoft.hz.selfportrait_61.apk",
            "description": "自拍美颜",
            "version": "2.3.4",
            "versionCode": "223",
            "category": "自拍",
            "star": "4.7",
            "icon": "http://p19.qhimg.com/t01ef636aaf1b0e4cbd.png",
            "iconType": 2,
            "size": "13.50M",
            "orientation": 0
         },
         {
            "appId": 3,
            "appName": "超级课程表",
            "download": "http://shouji.360tpcdn.com/170831/4fda99a5a2e9a414708babeb308b93bc/com.xtuone.android.syllabus_107.apk",
            "description": "课程学习",
            "version": "9.1.2",
            "versionCode": "111",
            "category": "日常工具",
            "star": "4.8",
            "icon": "http://p18.qhimg.com/t0165cf86f621865736.png",
            "iconType": 1,
            "size": "19.32M",
            "orientation": 0
         }
      ],
      "hasMore": false
   }
}

++++++++++++++++++应用简介+++++++++++++++++++++

   |   接口地址    |   boss/store/appinfo    |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                        |
####请求
{
"app_id":"X"
}
####返回


{
   "code": 0,
   "msg": "ok",
   "data": {
      "category": "日常工具",
      "description": "课程学习",
      "version": "9.1.2",
      "versionCode": "853",
      "icon": "http://p18.qhimg.com/t0165cf86f621865736.png",
      "iconType": 1,
      "download": "http://shouji.360tpcdn.com/170831/4fda99a5a2e9a414708babeb308b93bc/com.xtuone.android.syllabus_107.apk",
      "size": "19.32M",
      "updated": 0,
      "created": 0,
      "remarks": "你是",
      "label": "asdas",
      "star": "4",
	  "orientation": 0,
      "iamges": [
         "http://p17.qhimg.com/dm/180_300_/t0175b5305e91387007.jpg",
         "http://p16.qhimg.com/dm/180_300_/t01b32d85a47074f32a.jpg",
         "http://p16.qhimg.com/dm/180_300_/t018ec0d9633156b02b.jpg"
      ],
      "appId": 5,
      "appName": "超级",
      "devId": 0,
      "devName": "广州超级周末科技有限公司",
      "topCategory": "软件"
   }
}



+++++++++++++++++++应用市场的开发者模式+++++++++++++++++++++
①开发者模式下的新增APP
   |   接口地址    |   boss/store/appInsert    |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                  |
####请求                              
{
"app_name":"X",             #应用名称：    app_name        (小雨)
"app_spell":"X",            #应用名称拼音：app_spell       (xiaoyu)
"creator_id":X,             #创建者ID：    creator_id      (2)
"top_category":"X",         #应用大类型：  top_category    (应用 软件)
"category":"X",             #应用小类型：  category        (系统工具 日常工具 自拍神器)
"icon":"X",                 #应用图标：    icon        
"icon_type":X,              #应用图标类型：icon_type        (1  2   3)
"description":"X",          #应用描述：    description     (这是一个游戏app) 
"version":"X",              #版本：        version         (1.2.3)
"versioncode":"X",          #版本代码：    versioncode     (400 500)
"images":[                  #图片：        images
], 
"app":"X",                  #应用:         app
"label":"X",                #应用的标签：  label           (游戏 益智 儿童)
"orientation":X             #图片的摆放：  orientation     (1-横放  0-竖放)

}

####返回
无内容，直接查看返回码


②开发者模式下的修改APP
   |   接口地址    |   boss/store/appModify    |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                  |
####请求                              
{
"app_name":"X",             #应用名称：    app_name        (小雨)
"app_spell":"X",            #应用名称拼音：app_spell       (xiaoyu)
"creator_id":X,             #创建者ID：    creator_id      (2)
"top_category":"X",         #应用大类型：  top_category    (应用 软件)
"category":"X",             #应用小类型：  category        (系统工具 日常工具 自拍神器)
"icon":"X",                 #应用图标：    icon        
"icon_type":X,              #应用图标类型：icon_type        (1  2   3)
"description":"X",          #应用描述：    description     (这是一个游戏app) 
"version":"X",              #版本：        version         (1.2.3)
"versioncode":"X",          #版本代码：    versioncode     (400 500)
"images":[                  #图片：        images
], 
"app":"X",                  #应用:         app
"label":"X",                #应用的标签：  label           (游戏 益智 儿童)
"orientation":X             #图片的摆放：  orientation     (1-横放  0-竖放)
}

####返回
无内容，直接查看返回码



③开发者模式下的删除APP
   |   接口地址    |   boss/store/appDelete    |
   |   ---         |   ---                   |
   |   请求方式    |   HTTP POST             |
   |   参数格式    |   JSON                  |
####请求                              
{
"app_id":x
}
####返回
无内容，直接查看返回码



###角色权限
####修改权限
|   接口地址    |   boss/role/updateAuth        |
|   ---         |   ---                   |

```
//请求
{
    "key_id":x,                    //必填 角色权限ID 从接口queryAuth返回中的permiss_id字段值
    "status":x,                    //选填-1-可见 0-不可见
    "insert_auth":x,               //选填-新增权限
    "update_auth":x,               //选填-更新权限
    "query_auth":x                 //选填-查询可见权限
}

//回复
无回复 看结果
```

####新增权限
|   接口地址    |   boss/role/insertAuth        |
|   ---         |   ---                   |

```
//请求
{
    "role_id":x,                   //必填 权限ID 
    "module_id":x,                 //必填-模块ID
    "company_id":x,                //必填-公司ID
    "status":x,                    //必填-1-可见 0-不可见
    "insert_auth":x,               //必填-权限
    "update_auth":x,               //必填-权限
    "query_auth":x                 //必填-权限
}

//回复
无回复 看结果
```


####新增角色和权限
|   接口地址    |   boss/role/insertRoleAndAuths        |
|   ---         |   ---                   |

```
//请求
{
    "role":
		{
			 "role_level":x,                        //必填-角色权值
			 "role_name":"",                        //必填-角色名称
			 "company_id":x                         //必填-公司id
			 "desc":"",                             //选填-角色描述
			 "depart_id":X ,                        //选填-角色部门
			 "creator_id":X                         //必填-创建者id
		},
	
	"auths":
	    [  
	        {

			    "module_id":x,                 //必填-模块ID
			    "status":x,                    //必填-1-可见 0-不可见
			    "insert_auth":x,               //必填-权限
			    "update_auth":x,               //必填-权限
			    "query_auth":x                 //必填-权限
		    },
		    {
			    "module_id":x,                 //必填-模块ID
			    "status":x,                    //必填-1-可见 0-不可见
			    "insert_auth":x,               //必填-权限
			    "update_auth":x,               //必填-权限
			    "query_auth":x                 //必填-权限
		    }
	    ]
}

//回复
无回复 看结果
```


####修改角色和权限
|   接口地址    |   boss/role/updateRoleAndAuths        |
|   ---         |   ---                   |

```
//请求
{
    "role":
		{
			 "id":x                                 //必填-roleId
			 "role_level":x,                        //选填-角色权值
			 "role_name":"",                        //选填-角色名称
			 "company_id":x                         //选填-公司id
			 "desc":"",                             //选填-角色描述
			 "depart_id":X                          //选填-角色部门
		},
	
	"auths":
	    [  
	        {
			  
			    "module_id":x,                 //必填-模块ID
			    "status":x,                    //选填-1-可见 0-不可见
			    "insert_auth":x,               //选填-新增权限
			    "update_auth":x,               //选填-更新权限
			    "query_auth":x                 //选填-查询可见权限
		    },
		    {
			  
			    "module_id":x,                 //必填-模块ID
			    "status":x,                    //选填-1-可见 0-不可见
			    "insert_auth":x,               //选填-新增权限
			    "update_auth":x,               //选填-更新权限
			    "query_auth":x                 //选填-查询可见权限
		    }
	    ]
}

//回复
无回复 看结果
```

####删除角色和权限
|   接口地址    |   boss/role/deleteRoleAndAuths        |
|   ---         |   ---                   |

```
//请求
{
 
    "roleId":x                             //必填-从roleId

}

//回复
无回复 看结果
```




###设备多媒体信息
####查询设备多媒体信息
|   接口地址    |   boss/videorecord/query         |
|   ---         |   ---                   |

```
//请求
{
    "page_no":x,                            //选填-当前页, 默认为第1页
    "page_size":x,                          //选填-每页记录数，默认20
    "param":
    {
        "dev_id":x                     //设备Id
    }
}

//回复
{
    "page_no": 1,
    "page_size": 20,
    "total_count": 89,
    "total_page_count": 5,
    "param": null,
    "extra_param": null,
    "result": [
        {
            "id": 1,
            "devId": 205601,
            "fileName": "007",
            "duration": 120,
            "imageUrl": "g0/00/00/0.png",
            "vedioUrl": "",
            "status": 1,
            "type": 0,
            "created": 1495882204
        },
        {
            "id": 2,
            "devId": 205601,
            "fileName": "009",
            "duration": 122,
            "imageUrl": "g0/00/00/0.png",
            "vedioUrl": "",
            "status": 0,
            "type": 0,
            "created": 1495882775
        }
    ]
}
```


####修改权限
|   接口地址    |   boss/role/updateAuth        |
|   ---         |   ---                   |

```
//请求
{
    "key_id":x,                    //必填 角色权限ID 从接口queryAuth返回中的permiss_id字段值
    "status":x,                    //选填-1-可见 0-不可见
    "insert_auth":x,               //选填-新增权限
    "update_auth":x,               //选填-更新权限
    "query_auth":x                 //选填-查询可见权限
}

//回复
无回复 看结果
```

####新增权限
|   接口地址    |   boss/role/insertAuth        |
|   ---         |   ---                   |

```
//请求
{
    "role_id":x,                   //必填 权限ID 
    "module_id":x,                 //必填-模块ID
    "company_id":x,                //必填-公司ID
    "status":x,                    //必填-1-可见 0-不可见
    "insert_auth":x,               //必填-权限
    "update_auth":x,               //必填-权限
    "query_auth":x                 //必填-权限
}

//回复
无回复 看结果
```


####新增角色和权限
|   接口地址    |   boss/role/insertRoleAndAuths        |
|   ---         |   ---                   |

```
//请求
{
    "role":
		{
			 "role_level":x,                        //必填-角色权值
			 "role_name":"",                        //必填-角色名称
			 "company_id":x                         //必填-公司id
			 "desc":"",                             //选填-角色描述
			 "depart_id":X ,                        //选填-角色部门
			 "creator_id":X                         //必填-创建者id
		},
	
	"auths":
	    [  
	        {

			    "module_id":x,                 //必填-模块ID
			    "status":x,                    //必填-1-可见 0-不可见
			    "insert_auth":x,               //必填-权限
			    "update_auth":x,               //必填-权限
			    "query_auth":x                 //必填-权限
		    },
		    {
			    "module_id":x,                 //必填-模块ID
			    "status":x,                    //必填-1-可见 0-不可见
			    "insert_auth":x,               //必填-权限
			    "update_auth":x,               //必填-权限
			    "query_auth":x                 //必填-权限
		    }
	    ]
}

//回复
无回复 看结果
```


####修改角色和权限
|   接口地址    |   boss/role/updateRoleAndAuths        |
|   ---         |   ---                   |

```
//请求
{
    "role":
		{
			 "id":x                                 //必填-roleId
			 "role_level":x,                        //选填-角色权值
			 "role_name":"",                        //选填-角色名称
			 "company_id":x                         //选填-公司id
			 "desc":"",                             //选填-角色描述
			 "depart_id":X                          //选填-角色部门
		},
	
	"auths":
	    [  
	        {
			  
			    "module_id":x,                 //必填-模块ID
			    "status":x,                    //选填-1-可见 0-不可见
			    "insert_auth":x,               //选填-新增权限
			    "update_auth":x,               //选填-更新权限
			    "query_auth":x                 //选填-查询可见权限
		    },
		    {
			  
			    "module_id":x,                 //必填-模块ID
			    "status":x,                    //选填-1-可见 0-不可见
			    "insert_auth":x,               //选填-新增权限
			    "update_auth":x,               //选填-更新权限
			    "query_auth":x                 //选填-查询可见权限
		    }
	    ]
}

//回复
无回复 看结果
```

####删除角色和权限
|   接口地址    |   boss/role/deleteRoleAndAuths        |
|   ---         |   ---                   |

```
//请求
{
 
    "roleId":x                             //必填-从roleId

}

//回复
无回复 看结果
```




###设备多媒体信息
####查询设备多媒体信息
|   接口地址    |   boss/videorecord/query    |
|   ---         |   ---                   |

```
//请求
{
    "page_no":x,                            //选填-当前页, 默认为第1页
    "page_size":x,                          //选填-每页记录数，默认20
    "param":
    {
        "account":x,                     //小位号
        "company_id":x,                  //用户公司Id
        "query_date":x                   //日期
    }
}

//回复
{
    "page_no": 1,
    "page_size": 20,
    "total_count": 89,
    "total_page_count": 5,
    "param": null,
    "extra_param": null,
    "result": [
        {
            "id": 1,
            "devId": 205601,
            "fileName": "007",
            "duration": 120,
            "imageUrl": "http://192.168.2.250:8700/g0/00/00/0.png",
            "account": "testcamera2",
            "vedioUrl": "",
            "status": 1,
            "type": 0,
            "created": 1495882204
        },
        {
             "id": 2,
            "devId": 205601,
            "fileName": "009",
            "duration": 122,
            "imageUrl": "http://192.168.2.250:8700/g0/00/00/0.png",
            "account": "testcamera2",
            "vedioUrl": "",
            "status": 0,
            "type": 0,
            "created": 1495882775
        }
    ]
}
```

####查询公司设备多媒体信息数量
|   接口地址    |   boss/videorecord/queryCount    |
|   ---         |   ---                   |

```
//请求
{  
    "company_id":x,                  //用户公司Id
}

//回复
{
     "companyName": "沸石智能有限公司",  //公司名称
      "devCount": 71,                   //设备数量
      "imgCount": 1,                    //图片数量
      "vedioCount": 847                 //视频数量
}
```



