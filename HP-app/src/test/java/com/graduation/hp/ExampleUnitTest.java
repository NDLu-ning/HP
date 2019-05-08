package com.graduation.hp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
// 测试类中的所有测试方法都按照方法名的字母顺序执行，可以指定3个值。分别是 DEFAULT,JVM,NAME_ASCENDING
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Ignore("耗时")
    public void ignoreMethod() {
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass 这个方法在所有测试开始之前执行一次，用于做一些耗时的初始化工作(如: 连接数据库)");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("beforeClass 这个方法在所有测试结束之后执行一次，用于清理数据(如: 断开数据连接)，方法必须是static");
    }

    @Before()
    public void beforeEachMethod() {
        System.out.println("beforeEachMethod 每个测试@Test方法之前执行，用于准备测试环境");
    }

    @After()
    public void afterEachMethod() {
        System.out.println("afterEachMethod 每个测试@Test方法之前执行，用于清理测试环境");
    }

    @Test(timeout = 100)
    public void testExecution() {
        // 方法超过100毫秒 失败
        System.out.println("testExecution 控制测试时长，不可超过100毫秒");
    }

    @Test
    public void regex() {
        String content = "您的体质是阴虚型。特点是:经常感到手脚心发热，面颊潮红或偏红，眼睛干涩，皮肤干燥。性情急躁，外向好动。耐冻不耐夏。容易患虚劳、失眠。常便秘或大便干结。调养方式：多吃干凉滋润的食物，如绿豆、冬瓜、芝麻、百合、地瓜；多吃长在水里的东西，如藕、竹笋。中午保持一定的午休时间。避免熬夜、剧烈运动，运动是应该控制出汗量，即时补充水分。";
        String constitutionRegex = "您的体质是(.*?)。特点是:(.*?)。调养方式：(.*?)$";
        Pattern pattern = Pattern.compile(constitutionRegex);
        Matcher matcher = pattern.matcher(content);
        System.out.println(matcher.matches());
        System.out.println(matcher.groupCount());
        System.out.println(matcher.group());
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));
        System.out.println(matcher.group(3));
    }
}