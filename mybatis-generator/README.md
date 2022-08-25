# Mybatis 代碼生成工具 __MGB__

<br>

---

<br>

__MGB__ 可以通過映射 DB 自動生成基本的 Mapper interface、Mapper XML、Entity。這些包含基本的單表曾刪改查功能。

<br>


__MGB__ 官網：https://mybatis.org/generator/

<br>

__這裡使用 MGB 版本為 1.3.3__

<br>
<br>
<br>
<br>

## XML 配置

<br>

src/main/resource/generator/generatorConfig.xml

<br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<!--generatorConfiguration是節點-->
<!--properties classPathEntry javaModelGenerator 配置必須照順序來-->
<generatorConfiguration>

    <!--properties 用來指定外部屬性元素，最多可以配置 1 個，也可以忽略不配-->
    <!--properties 可以指定一個需要在配置中解析使用的外部屬性文件，引入屬性文件後，可以在配置中使用 ${property} 形式引用-->
    <!--properties 標籤包含 resource 與 url 兩種屬性，只能使用其中一個來指定，同時出現會噴錯 -->
    <!--resource 指定 classpath 下的屬性文件: com/myproject/generatorConfig.properties-->
    <!--url 指定文件系統特定位址: file:///C:/myfolder/generatorConfig.properties-->
    <properties resource="generatorConfig.properties"/>



    <!--classPathEntry 可以配置多個，也可以不配-->
    <!--classPathEntry 通過屬性 localtion 指定驅動路徑，像是 location="E:\mysql\mysql-connector-java-5.1.29.jar" -->
    <!--    <classPathEntry location=""/>-->

    <!--context 至少配置一個，可以配多個-->
    <!--context 標籤用於指定生成一組對象環境，例如要連接的DB，要生成對象的類型，和要處理的表，運行 MBG 要指定用哪個 context-->
    <!--context id 是必填屬性，MBG 運行時要指定唯一ID -->
    <!--defaultModelType 定義 MBG 如何生成 Entity 有 3 種可選-->
        <!-- * conditional 預設是這個，當表 PK 只有一個欄位，就不為該欄位設立單獨 Entity，直接與其他欄位併在一起成為一個 Entity -->
        <!-- * flat 為每張表生成一個 Entity，簡單易懂，推薦使用-->
        <!-- * hierarchical 如果表有 PK，會產生一個單獨的 PK Entity，如果表有 BLOB 欄位，則為表生成一個包含所有 BLOB 的單獨 Entity-->
        <!--   然後為其他欄位生成一個單獨的 Entity，MBG 在所有生成的 Entity 之間維護繼承關係-->
    <!--targetRuntime 指定生成代碼的運行時環境-->
        <!--MyBatis3: 默認值-->
        <!--MyBatis3Simple: 不生成與 Example 相關方法（就是不幫你弄基本增刪改查）-->
    <!--introspectedColumnImpl 指定 Column 類的實現類-->
    <context id="MySqlContext" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/> <!--設定前分隔號-->
        <property name="endingDelimiter" value="`"/> <!--設定後分隔號-->
        <property name="javaFileEncoding" value="UTF-8"/>

        <!-- 生成註釋信息 -->
        <commentGenerator>
            <property name="suppressAllComments" value="false"/> <!--生成註釋-->
            <property name="suppressDate" value="true"/> <!--生成註釋包含時間戳-->
            <property name="addRemarkComments" value="true"/> <!--生成表的 comment 資訊-->
        </commentGenerator>

        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC"
                        userId="root"
                        password="Jarvan1110">
            <property name="nullCatalogMeansCurrent" value="true"/> <!-- MySQL 不加這一段會把不相干 table 一起建出來 -->
        </jdbcConnection>

        <!--是否強制將 DECIMAL 和 NUMERIC 類型的 JDBC 資料轉成 Java.math.BigDecimal-->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- Entity 生成配置 -->
        <javaModelGenerator targetPackage="com.frizo.lab.mybatis.model" targetProject="src\main\java">
            <property name="trimStrings" value="true" /> <!-- 是否在 setter 加入自動 trim 功能 -->
            <property name="rootClass" value="com.frizo.lab.mybatis.model.BaseEntity" /> <!-- Entity 基本類 -->
            <property name="enableSubPackages" value="false"/> <!-- 根據 catalog 與 schema 生成子包，如果為 false 就直接用 targetPackage -->
        </javaModelGenerator>

        <!-- Mapper.xml 生成配置 -->
        <sqlMapGenerator targetPackage="com.frizo.lab.mybatis.mapper"  targetProject="src\main\resources">
            <property name="enableSubPackages" value="false"/> <!-- 根據 catalog 與 schema 生成子包，如果為 false 就直接用 targetPackage -->
        </sqlMapGenerator>

        <!-- Mapper interface 生成配置 -->
        <!-- type 分為三種 -->
            <!-- 1. ANNOTATEDMAPPER: 基於註解的 Mapper 介面，不產出 XML (不建議)-->
            <!-- 2. MIXEDMAPPER: XML 與註解混用的形式 (不建議)-->
            <!-- 3. XMLMAPPER: 基於 XML (推薦)-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.frizo.lab.mybatis.mapper"  targetProject="src\main\java"/>

        <!-- table 配置 -->
        <table tableName="%"> <!-- "%" 指生成全部表 -->
            <generatedKey column="id" sqlStatement="MySql"/> <!-- 指定自動生成PK的屬性，指定這個標籤，將在自動生成的 insert 語句插入 selectKey 標籤  -->
        </table>
    </context>

</generatorConfiguration>
```

<br>
<br>
<br>
<br>

## 使用 Java 編寫 MGB 產文件

<br>

加入 pom.xml 依賴

<br>

```xml
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.3.3</version>
</dependency>
```

<br>

編寫 MBG：

<br>

```java
package com.frizo.lab.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MBGenerator {

    public static void main(String[] args) throws Exception {
        // MBG 執行過程中的警告
        List<String> warnings = new ArrayList<>();
        // 覆蓋原本文件
        boolean overwrite = true;
        // 讀取配置
        InputStream is = MBGenerator.class.getResourceAsStream("/generator/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        // 建立 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        // 開始生成文件
        myBatisGenerator.generate(null);
        //输出警告信息
        for(String warning : warnings){
            System.out.println(warning);
        }
    }

}
```

<br>

運行 MBGenerator 就可以生成 MyBatis 文件了。

<br>
<br>
<br>
<br>

## 使用 Maven Plugin 運行

<br>

pom.xml 

<br>

```xml
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.3</version>
    <configuration>
        <configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
        <overwrite>true</overwrite>
        <verbose>true</verbose>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>com.firzo.lab</groupId>
            <artifactId>mybatis-generator</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
</plugin>
```

<br>

執行：

```
mvn install
```

<br>

```
D:\Note\MyBatis_Note\mybatis-generator>mvn install
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------< com.firzo.lab:mybatis-generator >-------------------
[INFO] Building mybatis-generator 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.847 s
[INFO] Finished at: 2022-08-26T00:25:26+08:00
[INFO] ------------------------------------------------------------------------

```

<br>

產出文件

<br>

```
mvn mybatis-generator:generate
```

<br>

```
D:\Note\MyBatis_Note\mybatis-generator>mvn mybatis-generator:generate
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------< com.firzo.lab:mybatis-generator >-------------------
[INFO] Building mybatis-generator 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- mybatis-generator-maven-plugin:1.3.3:generate (default-cli) @ mybatis-generator ---
[INFO] Connecting to the Database
[INFO] Introspecting table %
[INFO] Generating Record class for table sys_role_privilege
[INFO] Generating Mapper Interface for table sys_role_privilege
[INFO] Generating SQL Map for table sys_role_privilege
[INFO] Generating Record class for table sys_role
[INFO] Generating Mapper Interface for table sys_role
[INFO] Generating SQL Map for table sys_role
[INFO] Generating Record class for table country
[INFO] Generating Mapper Interface for table country
[INFO] Generating SQL Map for table country
[INFO] Generating Record class for table sys_privilege
[INFO] Generating Mapper Interface for table sys_privilege
[INFO] Generating SQL Map for table sys_privilege
[INFO] Generating Record class for table sys_user_role
[INFO] Generating Mapper Interface for table sys_user_role
[INFO] Generating SQL Map for table sys_user_role
[INFO] Generating Record class for table user info
[INFO] Generating Mapper Interface for table user info
[INFO] Generating SQL Map for table user info
[INFO] Generating Record class for table sys_user
[INFO] Generating Mapper Interface for table sys_user
[INFO] Generating SQL Map for table sys_user
[INFO] Generating Record class for table sys_dict
[INFO] Generating Mapper Interface for table sys_dict
[INFO] Generating SQL Map for table sys_dict
[INFO] Saving file SysRolePrivilegeMapper.xml
[INFO] Saving file SysRoleMapper.xml
[INFO] Saving file CountryMapper.xml
[INFO] Saving file SysPrivilegeMapper.xml
[INFO] Saving file SysUserRoleMapper.xml
[INFO] Saving file UserInfoMapper.xml
[INFO] Saving file SysUserMapper.xml
[INFO] Saving file SysDictMapper.xml
[INFO] Saving file SysRolePrivilege.java
[INFO] Saving file SysRolePrivilegeMapper.java
[INFO] Saving file SysRole.java
[INFO] Saving file SysRoleMapper.java
[INFO] Saving file Country.java
[INFO] Saving file CountryMapper.java
[INFO] Saving file SysPrivilege.java
[INFO] Saving file SysPrivilegeMapper.java
[INFO] Saving file SysUserRole.java
[INFO] Saving file SysUserRoleMapper.java
[INFO] Saving file UserInfo.java
[INFO] Saving file UserInfoMapper.java
[INFO] Saving file SysUser.java
[INFO] Saving file SysUserMapper.java
[INFO] Saving file SysDict.java
[INFO] Saving file SysDictMapper.java
[WARNING] Column id, specified as a generated key column in table sys_role_privilege, does not exist in the table.
[WARNING] Column id, specified as a generated key column in table sys_user_role, does not exist in the table.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.337 s
[INFO] Finished at: 2022-08-26T00:35:40+08:00
[INFO] ------------------------------------------------------------------------
```