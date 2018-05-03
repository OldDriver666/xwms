
import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.fise.base.Response;
import com.fise.model.param.AdminQuery;
import com.fise.server.administrator.IAdministratorService;
import com.fise.task.ReportTask;

public class UserAssignServiceTest extends BaseJunit4Test {

	@Autowired
    private ReportTask reportTask;
	
	@Test // 标明是测试方法
	@Transactional // 标明此方法需使用事务
	@Rollback(false) // 标明使用完此方法后事务不回滚,true时为回滚
	public void insert() {
		reportTask.show();
        
	}

}