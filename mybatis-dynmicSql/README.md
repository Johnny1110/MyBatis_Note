# Mybatis 動態 SQL

<br>

---

<br>

## MyBatis 動態 SQL 在 XML 中支持的 tag

<br>

* if

* choose (when, otherwise)

* trim (where, set)

* foreach

* bind

<br>
<br>
<br>
<br>

## if 使用範例

<br>

### SELECT：

```xml
<select id="selectByUser" resultType="com.frizo.lab.mybatis.model.SysUser">
	select id,
		user_name,
		user_password,
		user_email,
		user_info,
		head_img,
		create_time
	from sys_user
	where 1 = 1
	<if test="userName != null and userName != ''">
		and user_name like concat('%', #{userName}, '%')
	</if>
	<if test="userEmail != null and userEmail != ''">
		and user_email = #{userEmail}
	</if>
</select>
```

<br>

### UPDATE：

<br>

```xml
<update id="updateByIdSelective">
	update sys_user
	set
		<if test="userName != null and userName != ''">
			user_name = #{userName},
		</if>
		<if test="userPassword != null and userPassword != ''">
				user_password = #{userPassword},
		</if>
		<if test="userEmail != null and userEmail != ''">
				user_email = #{userEmail},
		</if>
		<if test="userInfo != null and userInfo != ''">
			user_info = #{userInfo},
		</if>
		<if test="headImg != null and headImg != ''">
			head_img = #{headImg, jdbcType=BLOB},
		</if>
		<if test="createTime != null and createTime != ''">
		    create_time = #{createTime, jdbcType=TIMESTAMP},
		</if>
		id = #{id}
	where
		id = #{id}
</update>
```

<br>

### INSERT：

<br>

```xml
<insert id="insertReturnKey" useGeneratedKeys="true" keyProperty="id">
	insert into sys_user(
				user_name, user_password,
				 <if test="userEmail != null and userEmail != ''">
					 user_email,
				 </if>
				 user_info, head_img, create_time

	)
	values(#{userName}, #{userPassword},
	<if test="userEmail != null and userEmail != ''">
		#{userEmail},
	</if>
		#{userInfo}, #{headImg, jdbcType=BLOB}, #{createTime, jdbcType=TIMESTAMP})
</insert>
```

<br>
<br>
<br>
<br>

## choose 使用範例

<br>

```java
	<select id="selectByIdOrUserName" resultType="com.frizo.lab.mybatis.model.SysUser">
		select id,
			user_name,
			user_password,
			user_email,
			user_info,
			head_img,
			create_time
		from sys_user
		where 1 = 1
		<choose>
			<when test="id != null">
				and id = #{id}
			</when>
			<when test="userName != null and userName != ''">
				and user_name = #{userName}
			</when>
			<otherwise>
				and 1 = 2
			</otherwise>
		</choose>
	</select>
```

<br>
<br>
<br>
<br>

## where set trim 用法

<br>

### where 範例

<br>

```xml
	<select id="selectByUser" resultType="com.frizo.lab.mybatis.model.SysUser">
		select id,
			user_name,
			user_password,
			user_email,
			user_info,
			head_img,
			create_time
		from sys_user
		<where>
			<if test="userName != null and userName != ''">
				and user_name like concat('%', #{userName}, '%')
			</if>
			<if test="userEmail != null and userEmail != ''">
				and user_email like concat('%', #{userEmail}, '%')
			</if>
		</where>
	</select>
```

<br><br>

### set 範例

<br>

```xml
	<update id="updateByIdSelective">
		update sys_user
		<set>
			<if test="userName != null and userName != ''">
				user_name = #{userName},
			</if>
			<if test="userPassword != null and userPassword != ''">
				user_password = #{userPassword},
			</if>
			<if test="userEmail != null and userEmail != ''">
				user_email = #{userEmail},
			</if>
			<if test="userInfo != null and userInfo != ''">
				user_info = #{userInfo},
			</if>
			<if test="headImg != null and headImg != ''">
				head_img = #{headImg, jdbcType=BLOB},
			</if>
			<if test="createTime != null and createTime != ''">
				create_time = #{createTime, jdbcType=TIMESTAMP},
			</if>
-- 			必須留 id 是因為 set 裡面不能為空，不然會出錯，以防上面 if 都沒值
			id = #{id},
		</set>

		where
			id = #{id}
	</update>
```

<br>
<br>

### trim 範例

<br>

trim 替代 where 標籤

```xml
<trim prefix="WHERE" prefixOverrides = "AND |OR ">
	<if test="userName != null and userName != ''">
		AND user_name like concat('%', #{userName}, '%')
	</if>
	<if test="userEmail != null and userEmail != ''">
		AND user_email like concat('%', #{userEmail}, '%')
	</if>
</trim>
```

trim 會把第一個通過 if 的值，前面的 `AND ` 拔掉。

<br>

trim 替代 set 標籤

```xml
<trim prefix="SET" suffixOverrides = ",">
	<if test="userName != null and userName != ''">
		user_name = #{userName},
	</if>
	<if test="userPassword != null and userPassword != ''">
		user_password = #{userPassword},
	</if>
	id = #{id},
</trim>
```
trim 會把最後結尾 `id = #{id},` 後面的 `,` 拔掉。

<br>

* prefix：當 trim 元素內包含內容時，會給內容增加 prefix 指定的前綴

* prefixOverrides：當 trim 元素內包含內容時，會去掉內容中匹配的前綴字符去掉

* suffix：當 trim 元素內包含內容時，會給內容增加 prefix 指定的後綴

* suffixOverrides：當 trim 元素內包含內容時，會去掉內容中匹配的後綴字符去掉

<br>
<br>
<br>
<br>

## foreach 用法

<br>

### foreach 實現 in 集合

<br>

UserMapper.java
```java
List<SysUser> selectByIdList(@Param("idList") List<Long> idList);
```

<br>

UserMapper.xml

```xml
	<select id="selectByIdList" resultType="com.frizo.lab.mybatis.model.SysUser">
		select id,
			user_name userName,
			user_password userPassword,
			user_email userEmail,
			user_info userInfo,
			head_img headImg,
			create_time createTime
		from sys_user
		where id in
		<foreach collection="idList" open="(" close=")" separator="," item="id" index="i">
			#{id}
		</foreach>
	</select>
```

<br>
<br>

### foreach 實現批量插入

<br>

如果要返回 id 就不支援使用 `@Param`，且只有 MySQL 可以批量回傳 ID，還必須是自動 key 生成。

```java
int insertList(List<SysUser> userList);
```

<br>

```xml
	<insert id="insertList" useGeneratedKeys="true" keyProperty="id">
		insert into sys_user(
			user_name, user_password, user_email, user_info, head_img, create_time
		) values
		<foreach collection="list" item="user" separator=",">
			(
				#{user.userName}, #{user.userPassword},
				 #{user.userEmail}, #{user.userInfo},
				  #{user.headImg, jdbcType=BLOB}, #{user.createTime, jdbcType=TIMESTAMP}
			)
		</foreach>
	</insert>
```

<br>
<br>

### foreach 實現動態 update

<br>

```java
int updateByMap(Map<String, Object> map);
```

<br>

map 預設使用 `_parameter` 代表，也可以自訂使用 `@Param`

<br>

```xml
	<update id="updateByMap">
		update sys_user
		set
			<foreach collection="_parameter" item="val" index="key" separator=",">
				${key} = #{val}
			</foreach>
		where id = #{id}
	</update>
```

<br>
<br>
<br>
<br>

## bind 用法

<br>

先前的 `selectByUser()` 方法，從 MySQL 換其他 DB 也許就不能 `concat('%', #{userName}, '%')`，像 Oracle 就不支援。

可以改用 bind 標籤完成：

```xml
<if test="userName != null and userName != ''">
	<bind name="userNameLike" value="'%' + userName + '%'"/>
	and user_name like #{userNameLike}
</if>
```

<br>
<br>
<br>
<br>

## OGNL 用法

<br>

1. `e1 or e2`

2. `e1 and e2`

3. `e1 == e2` 或 `e1 eq e2`

4. `e1 != e2` 或 `e1 neq e2`

5. `e1 lt e2` : 小於

6. `e1 lte e2` : 小於等於，`gt`（大於） `gte`（大於等於）

7. `e1 + e2`丶 `e1 * e2`丶 `e1/e2`丶 `e1 - e2`丶 `e1%e2`

8. `!e` 或 `not e`: 非 取反

9. `e.mathod(args)`: 調用對象方法

10. `e.property`: 對象屬性值

11. `e1[ e2 ]`: 按索引取值（List Array 和 Map）

12. `@class@method(args)`: 調用類別靜態方法

13. `@class@field`: 調用類別靜態屬性值

<br>

判斷集合是否為空時，可以這樣做：

<br>

```xml
<if test="list != null ande list.size() > 0">
	...
</if>
```

<br>

示範一下 `@class@method(args)` 怎麼用：

<br>

```xml
<if test="com.frizo.lab.mybatis.util.StringUtil@isNotEmpty(userName)">
	and user_name like concat('%', #{userName}, '%')
</if>
```

<br>

其中 StringUtil 類如下：

<br>

```java
public class StringUtil {
	public static boolean isEmpty(String str){
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
}
```

<br>

如果想知道映射 XML 中方法執行的參數，可以先在 Java 類中添加：

<br>

```java
public static void print(Object param) {
	System.out.println(param);
}
```

<br>

然後在映射文件中的方法標籤添加：

<br>

```xml
<bind name="print" value="com.frizo.lab.mybatis.util.StringUtil@print(_parameter)"/>
```



