package com.cloud.client.imp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public class TransactionListenerImpl implements TransactionListener {
    
    private AtomicInteger transactionIndex = new AtomicInteger(0);
    
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();
    
    /**
     * send()
     * @param msg send中的message对象，
     * @param arg send方法中回调函数之后的传入的参数
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.println("executeLocalTransaction" + new String(msg.getBody()));
        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        localTrans.put(msg.getTransactionId(), status);
        //提交本地事物
        return LocalTransactionState.COMMIT_MESSAGE;
    }
    
    //更新本地事物的最终状态
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("checkLocalTransaction" + new String(msg.getBody()));
        Integer status = localTrans.get(msg.getTransactionId());
        if (null != status) {
            switch (status) {
            case 0:
                return LocalTransactionState.UNKNOW;
            case 1:
                return LocalTransactionState.COMMIT_MESSAGE;
            case 2:
                return LocalTransactionState.ROLLBACK_MESSAGE;
            default:
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
    
}
