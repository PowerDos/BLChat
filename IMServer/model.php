<?php 
/**
* Author：Gavin
* Mail：543541941@qq.com
* 数据库处理类
*/
class Model{
	private $host = "127.0.0.1"; // 数据库地址
	private	$dbName = "blchat"; // 数据库名
	private	$username = ""; //数据库用户名
	private	$password = ""; //数据库密码	
	/**
	 * 获取数据库连接
	 */
	private function getSqlConn(){
		//连接数据库
		$conn = mysql_connect($this->host, $this->username, $this->password);
		//选择数据库
		mysql_select_db($this->dbName,$conn);
		//设置字符集
		mysql_query("set names 'utf8'");
		return $conn;
	}
	/**
	 * 添加用户数据库操作
	 */
	public function addUser($data){
		$conn = $this->getSqlConn();
		//设置时区
		date_default_timezone_set('PRC');
		//设置时间
		$data['time'] = date("Y-m-d H:i:s",time());
		$sql = "insert into user(username,password,nickname,sex,addtime,location) values('{$data["username"]}','{$data["password"]}','{$data["nickname"]}','{$data["sex"]}','{$data["time"]}','{$data["location"]}')";
		if(!mysql_query($sql,$conn)){
			mysql_close($conn);
			return false;
		}
		mysql_close($conn);
		return true;
	}
	/**
	 * 获取用户信息
	 */
	public function queryUserInfo($username){
		$conn = $this->getSqlConn();
		$sql = "select * from user where username='{$username}'";
		$result = mysql_query($sql);
		$data = mysql_fetch_assoc($result);
		return $data;
	}
}