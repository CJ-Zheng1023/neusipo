-- 当存储过程`p1`存在时，删除。
drop procedure if exists p1;$$

-- 创建存储过程`p1`，项目启动时插入admin角色和admin用户
create procedure p1()
begin
  declare row_num1 int;
  declare row_num2 int;
  select count(*) into row_num1 from `ns_role` where id='admin';
  if row_num1 = 0 then
    INSERT INTO `ns_role`(`id`, `name`, `description`) VALUES ('admin', 'admin', 'admin');
  end if;
  select count(*) into row_num2 from `ns_user` where id='admin';
  if row_num2 = 0 then
    INSERT INTO `ns_user`(`id`, `user_name`, `password`, `role_id`) VALUES ('admin', 'admin', 'admin', 'admin');
  end if;
end;$$

-- 调用存储过程`p1`
call p1();$$
drop procedure if exists p1;$$