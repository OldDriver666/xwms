
alter table wi_module drop  belong_company;
alter table wi_module drop  module_type;

alter table IMUser drop  address;
alter table IMUser drop  companyId;

alter table wi_admin drop  depart_id;
alter table wi_organization_role drop  depart_id;
alter table FiseDevice drop  companyId;


alter table wi_organization drop  home;
alter table wi_permission drop  company_id;
