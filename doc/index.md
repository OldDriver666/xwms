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
 "email":""
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

####查询设备信息
|   接口地址    |   boss/fisedevice/queryfisedevice         |
|   ---         |   ---                   |
|   请求方式    |   HTTP POST             |
|   参数格式    |   JSON                        |

-**请求**
```
{"ime":"",                    //选填-设备IME号
 "account":"",                //选填-小位号-账号 
 "depart_id":x                //必填-公司/团体ID
}
```
- **回复**
```
返回一个json数组
[
      {
         "fise_id": 1,
         "ime": "135790246811221",
         "mac": "49:04:22:f8:62:66",
         "code": "E5217ED8D7D4B40AF34FE02905CC39EC",
         "status": 1,
         "account": "test_dev0",
         "depart_id": 29,
         "type": 19,
         "mobile": "test_dev0",
         "mark":"",
	     "updated":1480406017,
	     "created":1480406017 
      },
      {
	     "fise_id": 2,
         "ime": "135790246811223",
         "mac": "49:04:22:f8:62:66",
         "code": "E5217ED8D7D4B40AF34FE02905CC39EC",
         "status": 1,
         "account": "test_dev2",
         "depart_id": 29,
         "type": 19,
         "mobile": "test_dev2",
         "mark":"",
	     "updated":1480406017,
	     "created":1480406017
	}
]
```

####删除设备信息
|   接口地址    |   boss/fisedevice//delfisedevice         |
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