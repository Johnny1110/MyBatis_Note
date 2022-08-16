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

```

