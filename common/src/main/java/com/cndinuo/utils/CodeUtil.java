package com.cndinuo.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 代码生成工具类
 * @author dgb
 *
 */
public class CodeUtil {
	
	static String newLineThreeTab = "\n\t\t\t";
	static String newLineFourTab = "\n\t\t\t\t";
	static String newLineTwoTab = "\n\t\t";
	static String newLineOneTab = "\n\t";
	
	//输出地址
	static String output = "";
	//模块名称
	static String moduleName = "";
	//表名
	static String tableName = "";
	//类名
	static String classSimpleName = "";
	//主键
	static String keyCol = "";
	static String escapePros = "";
	static String daoPackeageName = "";
	static String servicePackageName = "";
	static String serviceImplPackageName = "";
	static String className = "";
	static String smallClassName = "";
	
	
	public static void generateCode(String p_output,String p_moduleName,String p_tableName,String p_classSimpleName,String p_keyCol,String p_ignore){
		try{
			output = p_output;
			moduleName = p_moduleName;
			tableName = p_tableName;
			classSimpleName = p_classSimpleName;
			keyCol = p_keyCol;
			escapePros = p_ignore;
			
			daoPackeageName = "com.cndinuo.dao";
			servicePackageName = "com.cndinuo.service";
			serviceImplPackageName = "com.cndinuo.service.impl";
			className = "com.cndinuo.domain."+classSimpleName;
			smallClassName = classSimpleName.replaceFirst(classSimpleName.substring(0, 1),classSimpleName.substring(0, 1).toLowerCase());
			/**
			 * 创建dao文件
			 */
			createDaoCode();
			
			/**
			 * 创建serivce代码
			 */
			createServiceCode();
			
			/**
			 * 创建serivce实现类代码
			 */
			createServiceImplCode();
			
			/**
			 * 创建XML文件
			 */
			createMapperXmlFile();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void createDaoCode(){
		StringBuffer sb = new StringBuffer();
		//包名
		sb.append("package "+daoPackeageName+";");
		sb.append("\n\n");
		sb.append("import "+className+";\n");
		sb.append("import com.cndinuo.base.BaseDao;\n");
		sb.append("\n\n");
		sb.append("/**\n");
		sb.append("* @date "+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"\n");
		sb.append("* @author dgb\n");
		sb.append("* \n");
		sb.append("*/\n");
		sb.append("public interface "+classSimpleName+"Dao extends BaseDao<"+classSimpleName+", Integer>{");
		sb.append("\n\n");
		sb.append("}");
		System.out.println(sb.toString());
		
		if(!new File(output).exists()){
        	new File(output).mkdir();
        }
        String dirName = output+"/";
		
		createFile(sb.toString(),dirName+classSimpleName+"Dao.java");
		
	}
	/**
	 * service代码
	 */
	public static void createServiceCode(){
		StringBuffer sb = new StringBuffer();
		//包名
		sb.append("package "+servicePackageName+";");
		sb.append("\n\n");
		sb.append("import "+className+";\n");
		sb.append("import com.cndinuo.base.BaseService;\n");
		sb.append("\n\n");
		sb.append("/**\n");
		sb.append("* @date "+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"\n");
		sb.append("* @author dgb\n");
		sb.append("* \n");
		sb.append("*/\n");
		sb.append("public interface "+classSimpleName+"Service extends BaseService<"+classSimpleName+", Integer>{");
		sb.append("\n\n");
		sb.append("}");
		System.out.println(sb.toString());
		
		if(!new File(output).exists()){
        	new File(output).mkdir();
        }
        String dirName = output+"/";
		
		createFile(sb.toString(),dirName+classSimpleName+"Service.java");
		
	}
	/**
	 * service实现代码
	 */
	public static void createServiceImplCode(){
		StringBuffer sb = new StringBuffer();
		//包名
		sb.append("package "+serviceImplPackageName+";");
		sb.append("\n");
		sb.append("import "+className+";\n");
		sb.append("\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		sb.append("import org.springframework.stereotype.Service;\n");
		sb.append("\n");
		sb.append("import "+daoPackeageName+"."+classSimpleName+"Dao;\n");
		sb.append("import "+servicePackageName+"."+classSimpleName+"Service;\n");
		sb.append("import com.cndinuo.base.AbstractService;\n");
		sb.append("import javax.annotation.PostConstruct;\n");
		
		
		sb.append("\n\n");
		sb.append("/**\n");
		sb.append("* @date "+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"\n");
		sb.append("* @author dgb\n");
		sb.append("* \n");
		sb.append("*/\n");
		
		sb.append("@Service(\""+smallClassName+"Service\")\n");
		sb.append("public class "+classSimpleName+"ServiceImpl extends AbstractService<"+classSimpleName+", Integer> implements "+classSimpleName+"Service {");
		sb.append("\n\n");
		sb.append("\t@Autowired\n");
		sb.append("\tprivate "+classSimpleName+"Dao "+smallClassName+"Dao;");
		sb.append("\n\n");

		sb.append("\t@PostConstruct\n");
		sb.append("\tpublic void setBaseDao() {");
		sb.append("\n\t\tsuper.setBaseDao("+smallClassName+"Dao);");
		sb.append("\n\t}");
		
		sb.append("\n}");
		System.out.println(sb.toString());
		
		if(!new File(output).exists()){
			new File(output).mkdir();
		}
		String dirName = output+"/";
		
		createFile(sb.toString(),dirName+classSimpleName+"ServiceImpl.java");
		
	}
	
	public static void createMapperXmlFile() throws Exception{
		Class cc = Class.forName(className);
		Package pk = cc.getPackage();
		Field[] ffArr = cc.getDeclaredFields();
		
		Class supercc=cc.getSuperclass();  
		Field[] ffArr2 = supercc.getDeclaredFields();
		List<Field> ff = new ArrayList<Field>();
		for(Field item:ffArr){
			ff.add(item);
		}
		for(Field item:ffArr2){
			ff.add(item);
		}
		ffArr = (Field[]) ff.toArray(new Field[ff.size()]);
		
		String name = cc.getSimpleName();
		/**小写字母*/
		name = name.replaceFirst(name.substring(0, 1),name.substring(0, 1).toLowerCase());
		/**大写字母*/
		String uname = name.replaceFirst(name.substring(0, 1),name.substring(0, 1).toUpperCase());
		String alias = name;
		Document document = DocumentHelper.createDocument();
		
		// 创建根节点  
		Element root = document.addElement("mapper").addAttribute("namespace", daoPackeageName+"."+uname+"Dao");   
		document.addDocType("mapper", null, "-//mybatis.org//DTD Mapper 3.0//EN\" \"/WEB-INF/dtd/mybatis-3-mapper.dtd");
		
		//创建ResultMap
		getResultMapElement(root,name,ffArr);
		
		//创建cols列
		createSqlCol(root,ffArr);
		
		//创建查询语句
		createSelect(root, name);
		
		//创建查询数量
		createSelectCount(root,name);
		
		//新增语句
		insertElement(root,ffArr,uname);
		
		//修改语句
		updateElement(root, ffArr, name, uname);
		
		deleteElement(root,name);
		// 把文档转换字符串  
        String docXmlText = document.asXML();  
        System.out.println(docXmlText);  
	    writer(document, uname); 
	}
	public static void writer(Document document,String entityName) throws Exception {  
		OutputFormat xmlFormat = new OutputFormat(); 
        xmlFormat.setEncoding("UTF-8");
        // 设置换行
        xmlFormat.setNewlines(true);
        // 生成缩进
        xmlFormat.setIndent(true);
        // 使用4个空格进行缩进, 可以兼容文本编辑器
        xmlFormat.setIndent("    ");
         
//        String dirName = projectProviderLoc+baseLoc+"/"+daoPackeageName.replace(".", "/")+"/";
        if(!new File(output).exists()){
        	new File(output).mkdir();
        }
        String dirName = output+"/";
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(  
                new FileOutputStream(new File(dirName+entityName+"Mapper.xml")), "UTF-8"), xmlFormat);  
        writer.setEscapeText(false);
        // 写入  
        writer.write(document);  
        // 立即写入  
        writer.flush();  
        // 关闭操作  
        writer.close();  
    }  
	public static Element getResultMapElement(Element root,String name,Field[] ffArr){
		//添加resultMapper
		Element rmElement = root.addElement("resultMap").addAttribute("id", name+"Map").addAttribute("type", className);
		for(Field f:ffArr){
			if(!f.getName().equals("serialVersionUID")&&escapePros.indexOf(f.getName())<0){
				Element re = rmElement.addElement("result").addAttribute("property", f.getName())
						.addAttribute("column", entityFiledToDataFiled(f.getName()));
				if(f.getType().getName().equals("java.lang.String")){
					re.addAttribute("javaType","java.lang.String")
					.addAttribute("jdbcType", "VARCHAR");
				}
				if(f.getType().getName().equals("java.lang.Integer")||f.getType().getName().equals("lang")||f.getType().getName().equals("int")){
					re.addAttribute("javaType","java.lang.Integer")
					.addAttribute("jdbcType", "INTEGER");
				}
				if(f.getType().getName().equals("java.util.Date")){
					re.addAttribute("javaType","java.util.Date")
					.addAttribute("jdbcType", "TIMESTAMP");
				}
				if(f.getType().getName().equals("java.lang.Double")||f.getType().getName().equals("double")){
					re.addAttribute("javaType","java.lang.Double")
					.addAttribute("jdbcType", "NUMERIC");
				}
				if (f.getType().getName().equals("java.lang.Byte") || f.getType().getName().equals("byte")) {
					re.addAttribute("javaType", "java.lang.Byte")
					.addAttribute("jdbcType","TINYINT");
				}
				if (f.getType().getName().equals("java.math.BigDecimal")) {
					re.addAttribute("javaType", "java.math.BigDecimal")
					.addAttribute("jdbcType","DECIMAL");
				}
			}
		}
		return rmElement;
	}
	public static Element createSqlCol(Element root,Field[] ffArr){
		//col列
		Element sqlEle = root.addElement("sql").addAttribute("id", "cols");
		StringBuffer sb = new StringBuffer();
		for(Field ff:ffArr){
			
			if(!ff.getName().equals("serialVersionUID")&&escapePros.indexOf(ff.getName())<0){
				if(!StringUtil.isEmpty(sb.toString())){
					sb.append(",");
				}
				sb.append(newLineTwoTab);
				sb.append(entityFiledToDataFiled(ff.getName()));
			}
		}
		sb.append(newLineOneTab);
		sqlEle.addText(sb.toString());
		return sqlEle;
	}
	public static void createSelect(Element root,String name){
		/**
		 * selectById
		 */
		Element selectEle= root.addElement("select").addAttribute("id", "getById")
			.addAttribute("resultMap", name+"Map")
			.addAttribute("parameterType", "java.lang.Integer");
		selectEle.addText(newLineTwoTab+"select <include refid=\"cols\"/> from "+tableName+"  "+name+newLineTwoTab+" where "+keyCol+" = #{id}"+newLineOneTab);
		
		/**
		 * selectByMap
		 */
		Element selectByMap= root.addElement("select").addAttribute("id", "getByMap")
				.addAttribute("resultMap", name+"Map")
				.addAttribute("parameterType", "java.util.Map");
		selectByMap.addText(newLineTwoTab+"select <include refid=\"cols\"/> from "+tableName+"  "+name+
				newLineTwoTab+"<if test=\"whereSql != null and whereSql != ''\" >"
				+newLineThreeTab+"<![CDATA[${whereSql}]]>"
				+newLineTwoTab+ "</if>"
				+newLineTwoTab+ "<!-- 添加查询条件属性 -->"+newLineOneTab);
			
		/**
		 * selectByEntity
		 */
		Element selectByEntity= root.addElement("select").addAttribute("id", "getByEntity")
				.addAttribute("resultMap", name+"Map")
				.addAttribute("parameterType", className);
		selectByEntity.addText(newLineTwoTab+"select <include refid=\"cols\"/> from "+tableName+"  "+name+
					newLineTwoTab+"<!-- 添加查询条件属性 -->"+newLineOneTab);
		
		/**
		 * selectByPage
		 */
		Element selectByPage= root.addElement("select").addAttribute("id", "getByPage")
				.addAttribute("resultMap", name+"Map")
				.addAttribute("parameterType", "com.cndinuo.page.Page");
		selectByPage.addText(newLineTwoTab+"select <include refid=\"cols\"/> from "+tableName+"  "+name+
				newLineTwoTab+"<if test=\"p != null and p.whereSql != null and p.whereSql != ''\" >"
					+newLineThreeTab+ "<![CDATA[${p.whereSql}]]>"
					+newLineTwoTab+"</if>"
					+newLineTwoTab+"<!-- 添加查询条件属性 -->"
					+newLineOneTab);
		
	}
	public static void createSelectCount(Element root,String name){
		/**
		 * selectCount
		 */
		Element countEle= root.addElement("select").addAttribute("id", "getCount")
			.addAttribute("resultType", "java.lang.Integer");
		countEle.addText(newLineTwoTab+"select count(1) from "+tableName+"  "+name+newLineOneTab );
		/**
		 * selectCountByMap
		 */
		Element selectCountByMap= root.addElement("select").addAttribute("id", "getCountByMap")
				.addAttribute("resultType", "java.lang.Integer");
		selectCountByMap.addText(newLineTwoTab+"select count(1) from "+tableName+"  "+name+
				newLineTwoTab+"<if test=\"whereSql != null and whereSql != ''\" >"
				+newLineThreeTab+"<![CDATA[${whereSql}]]>"
				+newLineTwoTab+ "</if>"
				+newLineTwoTab+ "<!-- 添加查询条件属性 -->"+newLineOneTab);
		
		/**
		 * selectCountByEntity
		 */
		Element selectCountByEntity= root.addElement("select").addAttribute("id", "getCountByEntity")
				.addAttribute("resultType", "java.lang.Integer");
		selectCountByEntity.addText(newLineTwoTab+"select count(1) from "+tableName+"  "+name+
				newLineTwoTab+"<!--添加if语句添加过滤条件-->"
				+newLineOneTab);
		
	}
	public static void insertElement(Element root,Field[] ffArr,String uname){
		/**
		 * insert
		 */
		Element insertEle= root.addElement("insert").addAttribute("id", "insert")
			.addAttribute("parameterType", className)
			.addAttribute("useGeneratedKeys", "true")
			.addAttribute("keyProperty", keyCol);
		StringBuffer insertBf = new StringBuffer();
		insertBf.append(newLineTwoTab+"insert into "+tableName);
		insertBf.append(newLineTwoTab+"<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" > ");
		int idx = 0;
		for(Field ff:ffArr){
			idx ++;
			if(!ff.getName().equals(keyCol) && !ff.getName().equals("serialVersionUID") && escapePros.indexOf(ff.getName())<0){
				insertBf.append(newLineThreeTab);
				insertBf.append("<if test=\" "+ff.getName()+" != null and "+ff.getName()+" != ''\">");
				insertBf.append(newLineFourTab);
				insertBf.append(entityFiledToDataFiled(ff.getName()));
				if(idx != ffArr.length){
					insertBf.append(",");
				}
				insertBf.append(newLineThreeTab);
				insertBf.append("</if>");
			}
		}
		insertBf.append(newLineTwoTab);
		insertBf.append("</trim>");
		insertBf.append(newLineTwoTab+"<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
		int idx2 = 0;
		for(Field ff:ffArr){
			idx2 ++;
			if(!ff.getName().equals(keyCol)&&!ff.getName().equals("serialVersionUID")&&escapePros.indexOf(ff.getName())<0){
				insertBf.append(newLineThreeTab);
				insertBf.append("<if test=\" "+ff.getName()+" != null and "+ff.getName()+" != ''\">");
				insertBf.append(newLineFourTab);
				insertBf.append("#{"+ff.getName()+",jdbcType=");
				if(ff.getType().getName().equals("java.lang.String")){
					  insertBf.append("VARCHAR");
				}
				if(ff.getType().getName().equals("java.lang.Integer")||ff.getType().getName().equals("lang")||ff.getType().getName().equals("int")){
					insertBf.append("INTEGER");
				}
				if(ff.getType().getName().equals("java.util.Date")){
					insertBf.append("TIMESTAMP");
				}
				if(ff.getType().getName().equals("java.lang.Double")||ff.getType().getName().equals("double")){
					insertBf.append("NUMERIC");
				}
				if (ff.getType().getName().equals("java.lang.Byte") || ff.getType().getName().equals("byte")) {
					insertBf.append("TINYINT");
				}
				if (ff.getType().getName().equals("java.math.BigDecimal") || ff.getType().getName().equals("byte")) {
					insertBf.append("DECIMAL");
				}
				insertBf.append("}");
				if(idx2 != ffArr.length){
					insertBf.append(",");
				}
				insertBf.append(newLineThreeTab);
				insertBf.append("</if>");
			}
		}
		insertBf.append(newLineTwoTab);
		insertBf.append("</trim>");
		insertBf.append(newLineOneTab);
		insertEle.addText(insertBf.toString());
	}
	public static void updateElement(Element root,Field[] ffArr,String name,String uname){
		
		/**
		 * updateById
		 */
		Element updateEle = root.addElement("update")
				.addAttribute("id", "updateById")
				.addAttribute("parameterType", className);
		StringBuffer updateBf = new StringBuffer();
		updateBf.append(newLineTwoTab+"update "+tableName+" "+name);
		updateBf.append(newLineTwoTab+"<set>");
		int idx3 = 0;
		for(Field ff:ffArr){
			idx3 ++;
			if(!ff.getName().equals(keyCol) && !ff.getName().equals("serialVersionUID") && escapePros.indexOf(ff.getName())<0){

				updateBf.append(newLineThreeTab);
				updateBf.append("<if test=\" "+ff.getName()+" != null and "+ff.getName()+" != ''\">");
				updateBf.append(newLineFourTab);
				updateBf.append(entityFiledToDataFiled(ff.getName())+" = ");
				updateBf.append("#{"+ff.getName()+",jdbcType=");
				if(ff.getType().getName().equals("java.lang.String")){
					updateBf.append("VARCHAR");
				}
				if(ff.getType().getName().equals("java.lang.Integer")||ff.getType().getName().equals("lang")||ff.getType().getName().equals("int")){
					updateBf.append("INTEGER");
				}
				if(ff.getType().getName().equals("java.util.Date")){
					updateBf.append("TIMESTAMP");
				}
				if(ff.getType().getName().equals("java.lang.Double")||ff.getType().getName().equals("double")){
					updateBf.append("NUMERIC");
				}
				if (ff.getType().getName().equals("java.lang.Byte") || ff.getType().getName().equals("byte")) {
					updateBf.append("TINYINT");
				}
				if (ff.getType().getName().equals("java.math.BigDecimal") || ff.getType().getName().equals("byte")) {
					updateBf.append("DECIMAL");
				}
				updateBf.append("}");
				if(idx3 != ffArr.length){
					updateBf.append(",");
				}
				updateBf.append(newLineThreeTab);
				updateBf.append("</if>");
			}
		}
		updateBf.append(newLineTwoTab);
		updateBf.append("</set>");
		updateBf.append(newLineTwoTab);
		updateBf.append(" where "+keyCol+"= #{"+keyCol+"} ");
		updateBf.append(newLineOneTab);
		updateEle.addText(updateBf.toString());
		
		/**
		 * updateByMap
		 */
		Element updateByMap = root.addElement("update").addAttribute("id", "updateByMap");
		updateByMap.addText(newLineTwoTab+"update "+tableName +"  "+name+" set "+
				newLineTwoTab+"<if test=\"whereSql != null and whereSql != ''\" >"
				+newLineThreeTab+"<![CDATA[${whereSql}]]>"
				+newLineTwoTab+"</if>"
				+newLineTwoTab+ "<!-- 添加查询条件属性 -->"+newLineOneTab);
		
	}
	public static void deleteElement(Element root,String name){
		/**
		 * deleteById
		 */
		Element delEle = root.addElement("update").addAttribute("id", "deleteById")
				.addAttribute("parameterType", "java.lang.Integer");
		delEle.addText(newLineTwoTab+"update "+tableName+" "+name+" set deleted=1"+
				newLineTwoTab+"where "+keyCol+"=#{"+keyCol+"}"+newLineOneTab);

		
		/**
		 * deleteByMap
		 */
		Element deleteByMap = root.addElement("update").addAttribute("id", "deleteByMap")
				.addAttribute("parameterType","java.util.Map");
		deleteByMap.addText(newLineTwoTab+"update "+tableName+" "+name+" set deleted=1 "+
				newLineTwoTab+"<if test=\"whereSql != null and whereSql != ''\" >"
				+newLineThreeTab+"<![CDATA[${whereSql}]]>"
				+newLineTwoTab+"</if>"
				+newLineTwoTab+ "<!-- 添加条件属性 -->"+newLineOneTab);
		
		
		/**
		 * deleteByEntity
		 */
		Element deleteByEntity = root.addElement("update").addAttribute("id", "deleteByEntity")
				.addAttribute("parameterType",className);
		deleteByEntity.addText(newLineTwoTab+"update "+tableName+" "+name+" set deleted=1 "+
				newLineTwoTab+"where "+keyCol+"=#{"+keyCol+"}"+newLineOneTab);
	}

	public static void createFile(String data,String filePath){
		 byte[] buff=new byte[]{};  
         buff=data.getBytes();  
		try {
			FileOutputStream out= new FileOutputStream(filePath);
			out.write(buff,0,buff.length); 
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}

	/**
	 * 将实体类字段转换成数据库字段
	 * @param str
	 * @return
	 */
	private static String entityFiledToDataFiled(String str) {
		String[] ss = new String [5];
		int count = 0;
		ss[count] = "";
		for( int i = 0 ; i<str.length(); i++ ){
			char a = str.charAt(i);
			String b = ""+a;
			if( a>64 && a<91 ){ //大写字母的ASCLL码取值范围
				count++;
				ss[count]=b;
			}else{
				ss[count]=ss[count].concat(b);
			}
		}
		String sb = "";
		for (String s : ss) {
			if (StringUtil.isNotEmpty(s)) {
				sb += s.toLowerCase() + "_";
			}
		}
		sb = sb.substring(0, sb.length() - 1);
		System.out.println(sb);
		return sb;
	}
}
