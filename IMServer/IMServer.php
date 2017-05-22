<?php 
require_once 'api.php';
/**
* Author：Gavin
* Mail：543541941@qq.com
* 入口文件
*/
//实例化API
$objApi = new API();
switch ($_POST['type']) {
	case 1:
		$objApi->getUserInfo($_POST['username']);
		break;
	case 2:
		$objApi->singUp($_POST);
		break;
	case 3:
		$objApi->getFriendList($_POST['username']);
		break;
	default:
		echo "error";
		break;
}
	
	
