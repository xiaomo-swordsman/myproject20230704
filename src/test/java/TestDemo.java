import com.xiaomo.aop.*;
import com.xiaomo.dao.UserDao;
import com.xiaomo.domain.User;
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

    @Test
    public void testMyBatis() throws IOException {
        //加载核心配置文件
        InputStream stream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 创建sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);
        // 创建sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 保存用户
//        saveUser(sqlSession);

        // 更新用户
//        updateUser(sqlSession);

        // 删除用户
//        deleteUser(sqlSession);

        // 查询用户
        List<User> list = findAll(sqlSession);

        sqlSession.commit();
        // 打印结果
        System.out.println("list == " + list);
        // 释放资源
        sqlSession.close();
    }

    private void saveUser(SqlSession sqlSession){
        User user = new User();
        user.setUsername("mybatis save user");
        user.setPassword("123");
        user.setName("mybatis save user");
        user.setCreateTime(new Date());

        // 通过加载xml文件的写法
        //sqlSession.insert("userMapper.saveUser",user);

        // 通过注解的写法
        sqlSession.getMapper(UserMapper.class).saveUser(user);
    }

    private void deleteUser(SqlSession sqlSession){
        User user = new User();
        user.setId(22);

        // 通过加载xml文件的写法
        sqlSession.delete("userMapper.deleteUser",user.getId());

        // 通过注解的写法
//      sqlSession.getMapper(UserMapper.class).deleteUser(user.getId());
    }

    private void updateUser(SqlSession sqlSession){
        User user = new User();
        user.setId(13);
        user.setPassword("12345678");
        // 通过加载xml文件的写法
        sqlSession.update("userMapper.updateUser",user);

        // 通过注解的写法
//        sqlSession.getMapper(UserMapper.class).updateUser(user);
    }

    private List<User> findAll(SqlSession sqlSession){
        // 通过加载xml文件的写法
        List<User> list = sqlSession.selectList("userMapper.findAll");


        // 通过注解的写法
        // List<User> list = sqlSession.getMapper(UserMapper.class).findAll();

        return list;
    }
}
