package com.cndinuo.generator;

import com.cndinuo.utils.CodeUtil;

public class Code {
    public static void main(String[] args) {
        //输出地址
        String output = "D://workspaces/code";
        //模块名称
        String moduleName = "";
        //表名
        String tableName = "alipay_flow";
        //类名
        String classSimpleName = "AlipayFlow";
        //主键列
        String keyCol = "id";

        //生成XML时忽略的列
        String ignoreCols = "";

        CodeUtil.generateCode(output, moduleName, tableName, classSimpleName, keyCol,ignoreCols);
    }
}
