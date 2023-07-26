import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xiaomo.aop.Target;
import com.xiaomo.dao.UserDao;
import com.xiaomo.domain.PageVo;
import com.xiaomo.domain.User;
import com.xiaomo.domain.Vo;
import com.xiaomo.mapper.UserMapper;
import com.xiaomo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.TransactionDefinition.ISOLATION_DEFAULT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml") // 使用配置文件xml的写法
//@ContextConfiguration(classes = {SpringConfiguartion.class}) // 纯注解开发的写法
public class SpringTestDemo {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private Target target;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private ComboPooledDataSource dataSource;

    @Autowired
    private UserMapper userMapper;

    Connection connection = null;
    ResultSet resultSet = null;

    public void init() throws SQLException {
        connection = dataSource.getConnection();
        resultSet = connection.prepareStatement("select * from user").executeQuery();
    }

    @Test
    public void test() throws SQLException {
        userDao.save();
        userService.save();

        init();
        while (resultSet.next()){
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getString("name"));
        }
        destroy(connection,resultSet);
    }

    public void destroy(Connection connection,ResultSet resultSet) throws SQLException {
        connection.close();
        resultSet.close();
    }

    @Test
    public void testAOP(){
        target.save();
    }

    @Test
    public void testTranscation(){
        /*
            定义一个新的事务属性，定义了事务的隔离级别、传播行为、超时设置等事务执行的属性
            在 Spring 的声明式事务管理中，开发者可以通过 XML 配置或者注解来定义事务属性
            而 Spring 会根据这些定义自动创建相应的 TransactionDefinition 对象
         */
        TransactionDefinition definitionTransaction =  new DefaultTransactionDefinition();
        int propagationBehavior = definitionTransaction.getPropagationBehavior();// 获取事务的传播行为
        int isolationLevel = definitionTransaction.getIsolationLevel();// 获取事务的隔离级别
        int timeout = definitionTransaction.getTimeout();// 获取事务的超时时间（以秒为单位）。
        boolean readOnly = definitionTransaction.isReadOnly();// 判断事务是否为只读事务。
        String name = definitionTransaction.getName();// 获取事务的名称。
        System.out.println("事务的传播行为 == " + propagationBehavior);
        System.out.println("事务的隔离级别 == " + isolationLevel);
        System.out.println("事务的超时时间 == " + timeout);
        System.out.println("务是否为只读事务 == " + readOnly);
        System.out.println("事务的名称 == " + name);

        System.out.println("事务的隔离级别 == " + ISOLATION_DEFAULT);

        /*
            表示事务的执行状态的接口
            包含了事务的当前状态，如事务是否已开始、是否已提交或回滚等。
            在编程式事务管理中，开发者可以使用 TransactionStatus 对象来手动管理事务的执行
         */
        TransactionStatus transactionStatus = transactionManager.getTransaction(definitionTransaction);
        System.out.println("是否存储回滚点 == " + transactionStatus.hasSavepoint());
        System.out.println("事务是否完成 == " + transactionStatus.isCompleted());
        System.out.println("是否是新事务 == " + transactionStatus.isNewTransaction());
        System.out.println("事务是否回滚 == " + transactionStatus.isRollbackOnly());
        transactionManager.commit(transactionStatus);
        transactionManager.rollback(transactionStatus);
    }

    @Test
    public void testMyBatis(){
        List<User> userList = userMapper.getUserById(1);
        System.out.println("userList == " + userList);

    }

    @Test
    public void testPageHelper(){
        int page = 2;
        int pageSize = 5;
        // 设置分页相关参数
        PageHelper.startPage(page,pageSize);

        List<User> userList = userMapper.getUserByPage();
        System.out.println("userList == " + userList);// 这里直接输出的是page的信息

        List<User> userInfoList = new ArrayList<User>();// 封装实际查询的结果
        for (User user:userList) {
            userInfoList.add(user);
        }

        PageInfo<User> pageInfo = new PageInfo<User>(userList);  // 包含了分页的很多信息，如当前页、每页显示的数量、总记录数、总页数等
        PageVo<User> pageVo = new PageVo<>(pageInfo, userInfoList);
        System.out.println("pageVo == " + pageVo);
    }

    @Test
    public void testForeach(){
        List<String> userNameList = new ArrayList<String>();
        userNameList.add("wanger");
        userNameList.add("xiaohonghong");
        userNameList.add("xiaoxiao");
        List<User> userList = userMapper.getUserByUsernameList(userNameList);
        System.out.println("userList == " + userList);

        User user1 = new User();
        user1.setUsername("xiaomo11");
        user1.setName("xiaomo12");

        User user2 = new User();
        user2.setUsername("xiaomo21");
        user2.setName("xiaomo22");

        List<User> userList2 = new ArrayList<User>();
        userList2.add(user1);
        userList2.add(user2);
        userMapper.addUser(userList2);

    }
}
