package com.ocean.core.commons.huTools;

import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.Cloneable;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.swing.clipboard.ClipboardUtil;
import cn.hutool.core.util.*;
import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.util.Arrays;

@Builder
@Data
public class ToolDemo {
    private String name;
    /*浅克隆*/
    class Cat implements Cloneable<Cat>{


        @Override
        public Cat clone() {
            try {
                return (Cat)super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                throw new CloneRuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {
        //转换类
        String resourcesPath = System.getProperty("user.dir") + "/core/src/main/resources/file";
        System.out.println(resourcesPath);
        int a = 1;
        System.out.println(Convert.toStr(a));
        long[] b = {1,2,3,4,5};
        System.out.println(Convert.toStr(b));
        String dateStr = "2017-05-06";
        System.out.println(Convert.toDate(dateStr).toString()) ;
        // 日期格式自动转换 自动识别常见格式的
        System.out.println(DateUtil.parse(dateStr));
        // 金钱转中文大写
        double money = 67556.32;
        System.out.println( Convert.digitToChinese(money));
        // 文件工具类 自动判断文件类型
        System.out.println( FileTypeUtil.getType(FileUtil.file(resourcesPath+"/back.jpg")));
        //StrUtil
        System.out.println(StrUtil.format("{}爱{}，就像老鼠爱大米", "我", "你"));
        // 剪贴板工具-ClipboardUtil    获取设置剪切板的内容
        System.out.println(ClipboardUtil.getStr());
        ClipboardUtil.setStr("666");
        // 随机工具类 RandomUtil
        System.out.println(RandomUtil.randomInt(1,10));
        System.out.println(RandomUtil.weightRandom(
                Arrays.asList(new WeightRandom.WeightObj<>(1,0.10),new WeightRandom.WeightObj<>(2,0.90) )));

        //ZipUtil.zip("d:/aaa", "d:/bbb/ccc.zip");
        //执行终端命令
        String ipconfigStr = RuntimeUtil.execForStr("ipconfig");
        System.out.println(ipconfigStr);


        //等比缩放图片
        ImgUtil.scale(
                FileUtil.file(resourcesPath+"/back.jpg"),
                FileUtil.file(resourcesPath+"/back_scale0.5.jpg"),
                0.5f//缩放比例
        );
        //剪裁图片
        ImgUtil.cut(
                FileUtil.file("d:/face.jpg"),
                FileUtil.file("d:/face_result.jpg"),
                new Rectangle(200, 200, 100, 100)//裁剪的矩形区域
        );



    }
}
