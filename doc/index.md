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

###菜单管理
####获取菜单权限
|   接口地址    |   boss/module/queru         |
|   ---         |   ---                   |
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