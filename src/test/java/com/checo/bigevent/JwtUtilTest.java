package com.checo.bigevent;


import com.checo.bigevent.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtUtilTest {
    @Test
    public void jwtUtilTest() {
        HashMap<String, Object> claims = new HashMap<String, Object>() {{
            put("id", 2);
            put("username", "zhangsan");
        }};
        System.out.println(JwtUtil.genToken(claims));
        System.out.println(JwtUtil.genToken(claims));
        System.out.println(JwtUtil.genToken(claims));
        System.out.println(JwtUtil.genToken(claims));
    }
}
