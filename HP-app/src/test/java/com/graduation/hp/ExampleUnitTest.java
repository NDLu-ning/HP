package com.graduation.hp;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
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