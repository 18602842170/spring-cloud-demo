//package com.cloud.client.controller;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController // 配置为controller
//@RequestMapping(value = "/atomic_reference_field_updater")
//public class AtomicReferenceFieldUpdaterCon {
//    
//    class User {
//        public String name;
//        
//        public String getName() {
//            return name;
//        }
//        
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
//    
//    //使用newUpdater方法构建实例，第一个参数为需要操作对象，第二个参数为需要操作的属性的数据类型字节码文件，第三个为属性名字
//    AtomicReferenceFieldUpdater<User, String> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");
//    
//    User user = new User();
//    
//    /**
//     * @return
//     * @throws ExecutionException 
//     * @throws InterruptedException 
//     */
//    @RequestMapping(value = "/test")
//    public Object AtomicReferenceFieldUpdaterTest() throws InterruptedException, ExecutionException {
//        
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    printName("张三");
//                }
//            }
//        }).start();
//        
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    printName("李四");
//                }
//            }
//        }).start();
//        
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    printName("网五");
//                }
//            }
//        }).start();
//        
//        return "test over";
//    }
//    
//    public void printName(String name) {
//        atomicReferenceFieldUpdater.set(user, name);
//        System.out.println(user.getName());
//    }
//    
//}
