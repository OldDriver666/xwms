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
	"code":INTEGER,			//错误码
	"code_msg":"STRING",	//错误提示
	"data":JsonObject,		//返回的数据,一下所有接口回复说明里面就是该data字段内容,其他code 和code_msg未列出
}
```
#### 请求主机
```
http://boss.fise-wi.com
```

###账号管理

####管理员登录
|   接口地址    |   boss/admin/login         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON  				  |

- **请求**
```
{"account":"#userphone#","password":"#md5#"}
```
- **回复**
```
{
      "id": 1,
      "account": "18601735176",
      "salt": "",
      "password": "",
      "nickName": "廖国顺",
      "roleId": 3,
      "companyId": 1,
      "phone": "",
      "email": "",
      "accessToken": "6d2c021da6524976a8d037cd2b66a891",
      "status": 1,
      "lastLogin": 1496891165,
      "created": 1225404661,
      "updated": 1496806101
}
```

####管理员退出
|   接口地址    |   boss/admin/logout         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

- **请求**
```
{"admin_id":x} 这个值是登陆中返回的id获字段值
```
- **回复**
```
null 没有数据返回 看code是否成功
```

####管理员新增
|   接口地址    |   boss/admin/insert         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

- **请求**
```
{"admin_id":X,       //必填-这个值是登陆中返回的id获字段值-调用者id
 "account":"",       //必填-新增管理员账号
 "password":"",      //必填-密码
 "nick_name":"",     //昵称
 "role_id":x,        //必填-角色
 "phone":"",
 "email":"",
 "organization_id":x //必填-组织id
} 

```
- **回复**
```
null 没有数据返回 看code是否成功
```

####管理员查询
|   接口地址    |   boss/admin/query         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

- **请求**
```
{"admin_id":X,       //必填-这个值是登陆中返回的id获字段值-调用者id
 "account":"",       //管理员账号
 "role_id":x,        //角色
 "company_id":x,     //公司组织id
} 

```
- **回复**
```
null 没有数据返回 看code是否成功
```

####管理员修改
|   接口地址    |   boss/admin/update         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

- **请求**
```
{
 "login_id":x,       //必填-登录者id
 "admin_id":X,       //必填-需要修改者id
 "account":"",       //增管理员账号
 "password":"",      //密码
 "nick_name":"",     //昵称
 "role_id":x,        //角色
 "organization_id":x,//公司组织id
 "phone":"",
 "email":""
} 

```
- **回复**
```
null 没有数据返回 看code是否成功
```


###菜单管理
####获取菜单权限
|   接口地址    |   boss/module/query         |
|   ---         |   ---                   |
|   管理获取    |   boss/module/queryall         |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

- **请求**
```
{"admin_id":x, "role_id":} 以上两个值从登陆中获取
```
- **回复**
```
返回一个json数组
[
      {
         "moduleId": 1,
         "moduleName": "整体概述",
         "priority": 2000,
         "sn": "zhengtigaisu",
         "url": "",
         "insertAuth": 1,
         "updateAuth": 1,
         "queryAuth": 1,
         "parent_id": 0
      },
      {
         "moduleId": 2,
         "moduleName": "设备管理",
         "priority": 1900,
         "sn": "shebeiguanli",
         "url": "",
         "insertAuth": 1,
         "updateAuth": 1,
         "queryAuth": 1,
         "parent_id": 0
      },
      {
         "moduleId": 3,
         "moduleName": "报表统计",
         "priority": 1800,
         "sn": "baobiaotongji",
         "url": "",
         "insertAuth": 1,
         "updateAuth": 1,
         "queryAuth": 1,
         "parent_id": 0
      }
]
```

####新增菜单
|   接口地址    |   boss/module/insert         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

- **请求**
```
{"admin_id":x,          //必填-登陆管理员ID 
 "name":"title",        //必填-菜单名称
 "description":"",      //选填-菜单描述说明
 "priority":x,          //必填-菜单权限决定排序位置
 "sn":"desc",           //必填-菜单名称的全拼字符串
 "status":x,            //必填-0或者1,1表示新增后可可见 0-不可见
 "url":"",              //选填-是否需要配置跳转的url
 "parent_id":x          //必填-菜单父节点，如果是顶级菜单填写0
} 
```
- **回复**
```
无内容，直接查看返回码
```

####修改菜单
|   接口地址    |   boss/module/update         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

- **请求**
```
{"admin_id":x,          //必填-登陆管理员ID 
 "module_id":x,         //必填-module主键id
 "name":"title",        //选填-菜单名称
 "description":"",      //选填-菜单描述说明
 "priority":x,          //选填-菜单权限决定排序位置
 "sn":"desc",           //选填-菜单名称的全拼字符串
 "status":x,            //选填-0或者1,1表示新增后可可见 0-不可见
 "url":"",              //选填-是否需要配置跳转的url
 "parent_id":x          //选填-菜单父节点，如果是顶级菜单填写0
} 
```
- **回复**
```
无内容，直接查看返回码
```

####删除菜单
|   接口地址    |   boss/module/delete         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

- **请求**
```
{
 "module_id":x,         //必填-module主键id
} 
```
- **回复**
```
无内容，直接查看返回码
```

###系统设置
####新增系统设置
|   接口地址    |   boss/systemconf/addsystemconf         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"type":"x",            //必填-配置类型
 "name":"",             //必填-配置名称 
 "value":"",            //选填-配置具体值
 "action":"",           //选填-配置目标action
 "status":1,            //选填-配置状态   0-弃用  1-启用  
 "parent_id":0          //选填-菜单父节点，如果是顶级菜单填写0
}
```
- **回复**
```
无内容，直接查看返回码
```     

####查询系统设置
|   接口地址    |   boss/systemconf/querysystemconf         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
type和name都是选填，如果都不填，则查询所有信息
{            
 "type":"",           //选填-配置类型 
 "name":""            //选填-配置名称  
}
```
- **回复**
```
返回一个系统设置信息
{"config_id":x，
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
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |   

-**请求**
```
{            
 "config_id":x           //必填-配置id 
}
```
- **回复**
```
无内容，直接查看返回码
```

####修改系统设置
|   接口地址    |   boss/systemconf/updatesystemconf         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 
  
-**请求**
```
{"config_id":x,            //必填-配置id 
 "type":"",                //选填-配置类型  
 "name":"",                //选填-配置名称
 "value":"",               //选填-配置具体值
 "action":"",              //选填-配置目标action
 "status":1,               //选填-配置状态  0-弃用 1-启用
 "parent_id":x             //选填-菜单父节点，如果是顶级菜单填写0
}
```
- **回复**
```
无内容，直接查看返回码
```

###设备信息
####新增设备信息
|   接口地址    |   boss/fisedevice/addfisedevice         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"ime":"x"，                                                      //必填-设备IME号
 "account":"",               //必填-小位号-账号
 "depart_id":x,              //必填-公司/团体ID
 "type":x,                   //必填-设备类型 
 "mobile":"",                //选填-手机号
 "mark":""                   //选填-备注信息  
}
```
- **回复**
```
无内容，直接查看返回码
```  

####查询设备信息(分页查询)
|   接口地址    |   boss/fisedevice/queryfisedevice         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"page_no":x,                            //选填-当前页, 默认为第1页
 "page_size":x,                          //选填-每页记录数，默认20
 "param":{"ime":"",                      //选填-设备IME号
           "account":"",                 //选填-小位号-账号 
           "depart_id":x                 //必填-公司/团体ID
           }
}
```
- **回复**
```
{
   "code": 0,
   "msg": "ok",
   "data": [
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
      }
        ]
}        
```

####删除设备信息
|   接口地址    |   boss/fisedevice/delfisedevice         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |  

-**请求**
```
{"fise_id":x}                  //必填-设备ID
```
- **回复**
```
无内容，直接查看返回码
```  

####修改设备信息
|   接口地址    |   boss/fisedevice/updatefisedevice         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"fise_id":x,                //必填-设备ID
 "ime":"x"，                                                     //选填-设备IME号
 "status":0,                 //选填-设备状态  0-出厂 1-激活 2-删除
 "account":"",               //选填-小位号-账号
 "depart_id":x,              //选填-公司/团体ID
 "type":x,                   //选填-设备类型 
 "mobile":"",                //选填-手机号
 "mark":""                   //选填-备注信息   	
}
```
- **回复**
```
无内容，直接查看返回码
```  

###设备配置
####查询设备配置
|   接口地址    |   boss/querydevice        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"account":"",               //选填-设备账号
 "type":x,                   //必填-设备类型
 "phone":"",                 //选填-设备号码
 "depart_id":x               //必填-公司/团体ID
}                  
```
- **回复**
```
返回的信息会根据查询的设备的类型不同而不同  
如果设备类型是25  返回儿童手表
{"device_id":105369,
 "master_id":105391,
 "group_id":187,
 "mobile":"13510730625",
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
}

如果设备类型是19   返回卡片机
{"device_id":105369,
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

如果设备类型是23   返回电动车
{"device_id":105369,
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
```  

###设备统计
####设备激活情况统计
|   接口地址    |   boss/devicecount        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"depart_id":x}                   //必填-公司/团体ID              
```
- **回复**
```
{"onlineDevice":8,                //在线的设备数
 "activeDevice":38,               //激活的设备数
 "onlineXM":2,                    //在线的小位
 "activeXM":70                    //激活的小位
}
```

###公司ID与设备类型配置
####新增配置
|   接口地址    |   /boss/departconf/addimdepartconfig        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"depart_id":x,                   //必填-公司/团体ID  
 "client_type":x,                 //必填-设备类型
 "avatar":""                      //选填-默认头像
}
```
- **回复**
```
无内容，直接查看返回码
```  

####查询配置 
|   接口地址    |   /boss/departconf/queryimdepartconfig        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
都不填则全部查询
{"depart_id":x,                   //选填-公司/团体ID  
 "client_type":x,                 //选填-设备类型
}
```
- **回复**
```
[{"config_id":x,                   
  "depart_id":x,
  "client_type":x,
  "avatar":"",
  "created":x
},
 {"config_id":x,                   
  "depart_id":x,
  "client_type":x,
  "avatar":"",
  "created":x
}
] 
```  

####删除配置 
|   接口地址    |   /boss/departconf/delimdepartconfig        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"config_id":x}                   //必填-公司设备配置ID 
```
- **回复**
```
无内容，直接查看返回码
```

####修改配置 
|   接口地址    |   /boss/departconf/updateimdepartconfig        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"config_id":x                    //必填-公司设备配置ID
 "depart_id":x,                   //选填-公司/团体ID  
 "client_type":x,                 //选填-设备类型
 "avatar":""                      //选填-默认头像
} 
```
- **回复**
```
无内容，直接查看返回码
```

###设备类型与设备名称配置
####添加配置
|   接口地址    |   boss/clienttype/addclienttype        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"client_type":x,                   //必填-设备类型
 "client_name":""                   //必填-设备类型名称
}
```
- **回复**
```
无内容，直接查看返回码
```

####查询配置
|   接口地址    |   boss/clienttype/queryclienttype        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
都不填则全部查询
{"client_type":x,                   //选填-设备类型
 "client_name":""                   //选填-设备类型名称
}
```
- **回复**
```
[{"type_id":x,
  "client_type":x,
  "client_name":"",
  "created":x
  },
 {"type_id":x,
  "client_type":x,
  "client_name":"",
  "created":x
  }
]
```   

####删除配置
|   接口地址    |   boss/clienttype/delclienttype        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"type_id":x}                     //必填-配置ID
```
- **回复**
```
无内容，直接查看返回码
```

####修改配置
|   接口地址    |   boss/clienttype/updateclienttype        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"type_id":x,                     //必填-配置ID
 "client_type":x,                 //选填-设备类型
 "client_name":""                 //选填-设备类型名称
}
```
- **回复**
```
无内容，直接查看返回码
``` 

###权限管理
####查询用户角色
|   接口地址    |   boss/role/query        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"role_id":x,                 //必填-自己角色
 "organ_id":x                 //必填-自己公司
}
```
- **回复**
```
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

####查询角色权限
|   接口地址    |   boss/role/queryAuth        |
|   ---         |   ---                   |
|   包括不可见  |   boss/role/allAuth           |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"role_id":x,                 //必填-自己角色
 "organ_id":x                 //必填-自己公司
}
```
- **回复**
```
[
      {
         "role_id": 3,
         "role_name": "管理员",
         "auth_list": [
            {
               "permissId":1,
               "moduleId": 1,
               "moduleName": "整体概述",
               "priority": 2300,
               "sn": "zhengtigaisu",
               "url": "https://www.baidu.com",
               "insertAuth": 1,
               "updateAuth": 1,
               "queryAuth": 1,
               "parent_id": 0
            },
            {
               "permissId":2,
               "moduleId": 3,
               "moduleName": "报表统计",
               "priority": 1800,
               "sn": "baobiaotongji",
               "url": "",
               "insertAuth": 1,
               "updateAuth": 1,
               "queryAuth": 1,
               "parent_id": 0
            }
         ]
      }
]
``` 

####修改角色权限
|   接口地址    |   boss/role/updateAuth        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{
   "role_id":x,
   "permis_list":
   [
      {"permission_id":x,             //如果是修改必填，如果是新增不用填-权限id 从上面查询中获取id
       "module_id":x,                 //选填-自己菜单id
       "status":x,                    //选填-1-可见 0-不可见
       "insert_auth":x,               //选填-新增权限
       "update_auth":x,               //选填-更新权限
       "query_auth":x                 //选填-查询可见权限
      },
      {
       "module_id":x,                 //选填-自己菜单id
       "status":1,                    //选填-1-可见 0-不可见
       "insert_auth":1,               //选填-新增权限
       "update_auth":1,               //选填-更新权限
       "query_auth":1                 //选填-查询可见权限
      },
   ]
}

```
- **回复**
```
无回复 看结果
```

###报表统计
####设备事件查询(分页查询)
|   接口地址    |   boss/event/query        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |  

-**请求**
```
{"page_no":x,                            //选填-当前页, 默认为第1页
 "page_size":x,                          //选填-每页记录数，默认20 
 "param":{
 		  "admin_id":x,                  //必填-管理员ID
 		  "account":""	                 //必填-设备拼音
 		}
}
``` 
- **回复**
```
{
   "code": 0,
   "msg": "ok",
   "data": {
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
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |  

-**请求**
```
{"admin_id"x,"name":""}                //必填-管理员id,群的名称
```
- **回复**
```
{ [
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
}   
```

####回话消息查询
|   接口地址    |   boss/sessionmessage/query        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |  

-**请求**
```
{"small_id"x,"big_id":x}                //必填-回话用户的ID
```
- **回复**
```
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
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"depart_id"x,                     //必填-公司ID
 "dev_type":x,                     //必填-设备类型
 "dev_version":"",                 //必填-最新设备固件版本号
 "update_url":"",                  //必填-更新下载地址
 "version_info":""                 //选填-版本信息
}                
```
- **回复**
```
无内容，直接查看返回码
``` 

####查询设备新版本
|   接口地址    |   boss/deviceversion/query        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"depart_id":x,                    //必填-公司ID
 "dev_type":x                      //选填-设备类型
}
```
- **回复**
```
[{"version_id":x,
  "depart_id":x,
  "dev_type":x,
  "dev_version":"",
  "status":0,
  "version_info":"",
  "update_url":""
  },
  {"version_id":x,
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
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"version_id":x}                  //必填-设备版本ID
```
- **回复**
```
无内容，直接查看返回码
``` 

####修改设备新版本
|   接口地址    |   boss/deviceversion/update        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |  

-**请求**
```
{"version_id":x,                  //必填-设备版本ID
 "dev_version":"",				  //选填-最新设备固件版本号	
 "status":0,                      //选填-0 可用    1不可用
 "version_info":"",               //选填-版本信息
 "update_url":""                  //选填-更新下载地址
} 
```
- **回复**
```
无内容，直接查看返回码
```

###用户意见 
####新增用户意见
|   接口地址    |   boss/suggest/add        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |  

-**请求**
```
{"user_id":x,                     //必填-用户id
 "uname":"",                      //必填-用户名
 "suggestion":"",                 //选填-意见内容 
 "contact":""                     //选填-联系方式
}
```
- **回复**
```
无内容，直接查看返回码
``` 

####查询用户意见
|   接口地址    |   boss/suggest/query        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
不填，则查询所有
{"uname":""}                      //选填-用户名
```
- **回复**
```
[{"suggest_id":x,
 "user_id":x,
 "uname":"",
 "status":0,
 "suggestion":"",
 "contact":"",
 "created":x,
 "updated":x
 },
 {"suggest_id":x,
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
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |  

-**请求**
```
{"suggest_id":x}                  //必填-ID
```
- **回复**
```
无内容，直接查看返回码
``` 

####修改用户意见
|   接口地址    |   boss/suggest/update        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |  

-**请求**
```
{"suggest_id":x,                  //必填-ID
 "user_id":x,                     //选填-用户id
 "uname":"",                      //选填-用户名
 "status":0,                      //选填-记录状态 0 :初始正常 1:已经回复
 "suggestion":"",                 //选填-意见内容   
 "contact":""                     //选填-联系方式 
 }
```
- **回复**
```
无内容，直接查看返回码
```

###设备用户查询
####查询
|   接口地址    |   boss/user/query        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
不填，则查询所有
{"domain":"",                    //选填-用户小位号
 "nick":""                       //选填-用户昵称
}
```
- **回复**
```
{
   "code": 0,
   "msg": "ok",
   "data": [
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

###公司管理
####新增公司
|   接口地址    |   boss/organization/insert        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"name":"",                    //必填-公司名称
 "address":"",                 //选填-公司地址
 "contact":"",                 //选填-公司联系方式
 "email":"",                   //选填-公司email
 "describtion":""              //选填-公司简介                   
}
```
- **回复**
```
无内容，直接查看返回码
```

####公司查询
|   接口地址    |   boss/organization/query        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
不填，则查询所有
{"name":""}                    //选填-公司名称
```
- **回复**
```
{
   "code": 0,
   "msg": "ok",
   "data": [
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
}
```

####公司删除
|   接口地址    |   boss/organization/del        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
不填，则查询所有
{"id":x}                        //必填-公司id
```
- **回复**
```
无内容，直接查看返回码
```

####公司修改
|   接口地址    |   boss/organization/update        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"id":x,                       //必填-公司id
 "name":"",                    //选填-公司名称
 "address":"",                 //选填-公司地址
 "contact":"",                 //选填-公司联系方式
 "email":"",                   //选填-公司email
 "describtion":"",             //选填-公司简介 
 "status":x                    //选填-0-删除，1-正常
}
```
- **回复**
```
无内容，直接查看返回码
```

###设备出厂 
####新增设备
|   接口地址    |   boss/accountmanage/add        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"depart_id":x,                //必填-公司iD 
 "description":"",             //选填-描述
 "begin_account":"",           //选填-设备起始编号
 "end_account":"",             //选填-设备结束编号
 "status":1                    //选填-0-初始 1-已经出厂    
}
```
- **回复**
```
无内容，直接查看返回码
```

####查询设备
|   接口地址    |   boss/accountmanage/query        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
没有，则查询所有
{"depart_id":x}                 //选填-公司id
```
- **回复**
```
{
   "code": 0,
   "msg": "ok",
   "data": [
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
}
```

####删除设备
|   接口地址    |   boss/accountmanage/del        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"id":x}                    //必填-id
```
- **回复**
```
无内容，直接查看返回码
```

####修改设备
|   接口地址    |   boss/accountmanage/update        |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        | 

-**请求**
```
{"id":2,                                //必填-id
 "description": "",                     //选填-描述
 "status": 0,                           //选填-设备状态
 "depart_id": 1,                        //选填-公司id
 "begin_account": "W0101003000",        //选填-设备起始编号  
 "end_account": "W0101006100"           //选填-设备结束编号 
} 
- **回复**
```
无内容，直接查看返回码
```