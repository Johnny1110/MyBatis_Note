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