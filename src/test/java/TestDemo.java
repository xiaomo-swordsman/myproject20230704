import com.xiaomo.aop.*;
import com.xiaomo.dao.UserDao;
import com.xiaomo.domain.Order;
import com.xiaomo.domain.User;
import com.xiaomo.mapper.OrderMapper;
import com.xiaomo.mapper.UserMapper;
import com.xiaomo.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.List;

public class TestDemo {

    @Test
    public void testSpring(){
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User)app.getBean("user");
        System.out.println(user);

         UserDao userDao = (UserDao)app.getBean("userDao");
         userDao.save();

         UserService userService = (UserService)app.getBean("userService");
         userService.save();
    }

    @Test
    public void testJDKProxy(){
        final Target target = new TargetImpl();// 被代理的对象 使用final修饰是为了确保代理过程中对象引用不会发生变化
        final Advice advice = new Advice();// 增强的代码对象
        Target proxy = (Target) getJDKProxy(target,advice);// 通过JDK反射机制，创建代理对象
        proxy.save();

    }

    // JDK动态代理 通过Java标准库的反射机制，生成一个实现目标接口的代理类来实现代理
    private Object getJDKProxy(final Target target,final Advice advice){
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),// 被代理对象的类加载器
                target.getClass().getInterfaces(),// 被代理对象的字节码对象的接口数组
                new InvocationHandler() {
                    // 调用代理对象的任何方法,实质都是调用invoke方法
                    @Override
                    public Object invoke(Object proxy2, Method method, Object[] args) throws Throwable {
                        advice.before();// 前置方法执行
                        Object result = method.invoke(target, args);// 被代理对象的方法的执行
                        advice.after();// 后置方法执行
                        return result;
                    }
                });
        return proxy;
    }

    @Test
    public void testCGLBProxy(){
        final CGLBTarget target = new CGLBTargetSun();// 被代理对象
        final Advice advice = new Advice();// 增强对象
        CGLBTarget proxy = (CGLBTarget) getCGLBProxy(target,advice);// 通过CGLB获取代理对象
        proxy.save();
    }

    // CGLB动态代理 生成目标类的子类来实现代理
    private Object getCGLBProxy(final CGLBTarget target,final Advice advice){
        // 1、创建增强器()
        Enhancer enhancer = new Enhancer();
        // 2、设置父类
        enhancer.setSuperclass(CGLBTarget.class);
        // 3、设置回调
        enhancer.setCallback(new MethodInterceptor() {
            // 调用代理对象的任何方法,实质都是调用invoke方法
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                advice.before();// 前置方法执行
                Object result = method.invoke(target, args);// 执行目标
                advice.after();// 后置方法执行
                return result;
            }
        });
        // 4、创建代理对象
        CGLBTarget proxy = (CGLBTarget)enhancer.create(); // 父子关系,通过多态进行转换

        return proxy;
    }

    InputStream stream = null;
    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;
    private void initMyBatis(){
        //加载核心配置文件
        try {
            stream = Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建sqlSessionFactory工厂对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);
        // 创建sqlSession对象
        sqlSession = sqlSessionFactory.openSession();
    }

    private void destroyMyBatis(){
        sqlSession.commit();
        sqlSession.close();
    }

    // 保存用户
    @Test
    public void saveUser(){
        initMyBatis();
        User user = new User();
        user.setUsername("mybatis save user");
        user.setPassword("123");
        user.setName("mybatis save user");
        user.setCreateTime(new Date());
        sqlSession.getMapper(UserMapper.class).saveUser(user);
        destroyMyBatis();
    }

    // 删除用户
    @Test
    public void deleteUser(){
        initMyBatis();
        User user = new User();
        user.setId(22);
        // UserMapper.xml配置文件中的namespace + id
        sqlSession.delete("userMapper.deleteUser",user.getId());
        destroyMyBatis();
    }

    // 更新用户
    @Test
    public void updateUser(){
        initMyBatis();
        User user = new User();
        user.setId(13);
        user.setPassword("12345678");
        // UserMapper.xml配置文件中的namespace + id
        sqlSession.update("userMapper.updateUser",user);

        // UserMapper.xml配置文件中的namespace为接口的全限定名，id为接口中的方法名
//      sqlSession.getMapper(UserMapper.class).updateUser(user);
        System.out.println("updateUser == " + user);
        destroyMyBatis();
    }

    // 查询所有用户
    @Test
    public void findAll(){
        initMyBatis();
        // UserMapper.xml配置文件中的namespace + id
//      List<User> list = sqlSession.selectList("userMapper.findAll");
        // UserMapper.xml配置文件中的namespace为接口的全限定名，id为接口中的方法名
        List<User> list = sqlSession.getMapper(UserMapper.class).findAll();
        System.out.println("findAll == " + list);
        destroyMyBatis();
    }

    // 根据username查找用户
    @Test
    public void getUserByName(){
        initMyBatis();
        User user = new User();
        user.setUsername("jerry");
        user.setName("xiaoxiao");
        // UserMapper.xml配置文件中的namespace + id
//        User  resultUser =  sqlSession.selectOne("userMapper.getUserByName", user);

        // UserMapper.xml配置文件中的namespace为接口的全限定名，id为接口中的方法名
        User user2 = sqlSession.getMapper(UserMapper.class).getUserByName(user);
        System.out.println("getUserByName == " + user2);
        destroyMyBatis();
    }

    @Test
     public void getOrderById(){
        initMyBatis();
        List<Order> orderList = sqlSession.getMapper(OrderMapper.class).getOrderById(1);
        System.out.println("getOrderById == " + orderList);
         destroyMyBatis();
     }

    @Test
    public void getUserById(){
        initMyBatis();
        List<User>  user = sqlSession.getMapper(UserMapper.class).getUserById(1);
        System.out.println("getUserById == " + user);
        destroyMyBatis();
    }
}
