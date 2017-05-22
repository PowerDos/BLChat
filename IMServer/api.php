<?php 
require_once 'model.php'; //引入数据库操作类
/**
* Author：Gavin
* Mail：543541941@qq.com
* 集成API类
*/
class API{
	private $url = "https://a1.easemob.com/1159170514178932/blchat/";
	private $clientID = "YXA6-t5PQDhNEeejpDVYs_flbg";
	private $clientSecret = "YXA6-UZJjP_h7-pFuL6zgBBtIcKOl-0";
	/**
	 * Get 请求
	 */
	private function getUrl($url, $header){
		$header = $header ? $header :0;
		$curl = curl_init();
		// 设置URL
		curl_setopt($curl, CURLOPT_URL, $url);
		// 关闭对认证证书的检测
		curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE);
		// 关闭从证书中检查SSL加密算法是否存在
		curl_setopt ($curl, CURLOPT_SSL_VERIFYHOST, FALSE);
		//设置HTTP头
		curl_setopt ($curl, CURLOPT_HTTPHEADER, $header);
		//获取信息以文件流的形式返回
		curl_setopt ($curl, CURLOPT_RETURNTRANSFER, 1);
		//执行操作
		$result = curl_exec($curl);
		// 关闭会话
		curl_close($curl);
		return $result;
	}
	/**
	 * Post 请求
	 */
	private function postUrl($url, $data){
		$curl = curl_init();
		// 设置URL
		curl_setopt($curl, CURLOPT_URL, $url);
		// 关闭对认证证书的检测
		curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, FALSE);
		// 关闭从证书中检查SSL加密算法是否存在
		curl_setopt ($curl, CURLOPT_SSL_VERIFYHOST, FALSE);
		//获取信息以文件流的形式返回
		curl_setopt ($curl, CURLOPT_RETURNTRANSFER, 1);
		//设置以POST模式访问
		curl_setopt ($curl, CURLOPT_CUSTOMREQUEST, POST);
		//设置数据
		$sendData = json_encode($data);
		curl_setopt ($curl, CURLOPT_POSTFIELDS, $sendData); 
		//执行操作
		$result = curl_exec($curl);
		// 关闭会话
		curl_close($curl);
		return $result;
	}
	/**
	 * 获取Token
	 */
	private function getToken(){
		$data['grant_type'] = "client_credentials";
        $data['client_id'] = $this->clientID;
        $data['client_secret'] = $this->clientSecret;
        $url = $this->url . "token";
        //设置缓存
        $fp = fopen ("easeToken.txt", 'r+');
        if ($fp) {
        	//如果文件存在就读取文件
            $arr = unserialize (fgets($fp));
            if ($arr['expires_in'] < time()) {
            	//Token过期，就重新获取
                $result = $this->postUrl($url,$data);
                //解析返回数据
                $result = (array)json_decode($result);
                //设置存活时间
                $result['expires_in'] = $result['expires_in'] + time();
                fwrite ($fp, serialize($result));
                $token = $result['access_token'];
                fclose($fp);
                return  $token;
            }
         	fclose ( $fp );
            return $arr ['access_token']; 
        }
        $result = $this->postUrl ($url,$data);
        $result = (array)json_decode($result);
        //设置存活时间
        $result['expires_in'] = $result['expires_in'] + time();
        //写入缓存
        $fp = fopen ("easeToken.txt", 'w');
        var_dump($fp);
        fwrite ($fp, serialize($result));
        fclose ($fp);
        return $result['access_token'];   
	}

	/**
	 * 获取朋友列表
	 */
	public function getFriendList($username){
		$url = $this->url."user/".$username."/contanct/users";
		$access_token = $this->getToken();
		$header[] = 'Authorization: Bearer ' . $access_token;
		$result = $this -> getUrl($url, $header);
		echo $result;
	}
	/**
	 * 注册用户
	 */
	public function singUp($data){
		$url = $this->url."users";
		$sendData['username'] = $data['username'];
		$sendData['password'] = $data['password'];
		$sendData['nickname'] = $data['nickname'];
		// 发起注册请求
		$result = $this->postUrl($url,$sendData);
		$result = (array)json_decode($result);;
		if(!$result['entities']){
			if ($result['error'] == "duplicate_unique_property_exists") {
				$reData['msg'] = "用户名已存在";
			}
			$reData['error'] = 101;
		}else{
			$model = new Model();
			if($model->addUser($data)){
				$reData['error'] = 0;
				$reData['msg'] = "注册成功";
			}else{
				$reData['error'] = 102;
				$reData['msg'] = "注册失败";
			}
		}
		$reData = json_encode($reData);
		echo $reData;
	}
	/**
	 * 获取用户信息
	 */
	public function getUserInfo($username){
		$model = new Model();
		$data = $model->queryUserInfo($username);
		if ($data) {
			unset($data['status']);
			unset($data['addtime']);
		}else{
			$data["error"] = 103;
			$data["msg"] = "用户不存在";
		}
		$data = json_encode($data);
		echo $data;
	}
}