
ALTER TABLE `wi_module`
ADD COLUMN `module_type`  int(5) DEFAULT '0' COMMENT '0-管理 1-查询' AFTER `name`;,
ADD COLUMN `belong_company` int(11) DEFAULT '0' COMMENT '所属公司0为公有菜单' AFTER `module_type`;


/*详细地址 用户/设备所属公司 用户手机/身份证*/
ALTER TABLE `IMUser`
ADD COLUMN `address`  varchar(64) NULL DEFAULT '' AFTER `city`,
ADD COLUMN `companyId`  int(11) NULL DEFAULT 1 AFTER `type`;
MODIFY COLUMN `phone`  varchar(19)  NOT NULL DEFAULT '' COMMENT '手机号码' AFTER `country`;

/*管理员分部门*/
ALTER TABLE `wi_admin`
ADD COLUMN `depart_id`  int(11) NULL DEFAULT 0 AFTER `company_id`;
ALTER TABLE `wi_organization_role`
ADD COLUMN `depart_id`  int(11) NULL DEFAULT 0 AFTER `organization_id`;

/*设备属于某公司 可以细分到部门 默认0为为分配部门*/
ALTER TABLE `FiseDevice`
MODIFY COLUMN `departId`  int(11) NOT NULL DEFAULT 0 COMMENT '公司/团体ID' AFTER `account`,
ADD COLUMN `companyId`  int(11) NULL DEFAULT 1 AFTER `account`;

ALTER TABLE `wi_organization`
ADD COLUMN `home`  varchar(32) NULL DEFAULT "index.html" AFTER `email`;

ALTER TABLE `wi_permission`
ADD COLUMN `company_id`  int(11) NULL DEFAULT 0 COMMENT '0-公有角色 x-指定公司角色' AFTER `id`;


ALTER TABLE `wi_organization_role`
DROP INDEX `unique_name` ,
ADD UNIQUE INDEX `unique_name` (`name`, `organization_id`) USING BTREE ;

ALTER TABLE `wi_module`
DROP INDEX `unique_sn` ,
ADD UNIQUE INDEX `unique_sn` (`belong_company`, `name`, `module_type`) USING BTREE ;


DROP TABLE IF EXSITS `wi_department`;
CREATE TABLE `wi_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `depart_name` varchar(255) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `updated` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;