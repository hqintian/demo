package com.example.demo.controller;

import com.alibaba.csp.sentinel.CtSph;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello1")
    //@SentinelResource(value = "/hello2")
    public String hello1() {
        ContextUtil.enter("context_hello1");
        Entry entry = null;
        try {
            entry = SphU.entry("hello1");
            // 资源中的逻辑. 啊啊啊
            System.out.println(Thread.currentThread().getId() + " Hello Sentinel1");
        } catch (BlockException e1) {
            System.out.println("blocked!");
        } finally {
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }


        return "Hello Sentinel1";
    }

    @GetMapping("/hello2")
    //@SentinelResource(value = "/hello2")
    public String hello2() {
        ContextUtil.enter("context_hello2");
        Entry entry = null;
        try {
            entry = SphU.entry("hello1");
            // 资源中的逻辑.
            System.out.println(Thread.currentThread().getId() + " Hello Sentinel2");
        } catch (BlockException e1) {
            System.out.println("blocked!");
        } finally {
            if (entry != null) {
                entry.exit();
                ContextUtil.exit();
            }
        }

        return "Hello Sentinel2";
    }
}
