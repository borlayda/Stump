angular.module('login', []).controller('loginController', function($scope) {
	$scope.username = "";
	$scope.password = "";
    $scope.authorize = function (){
    	window.location.href = "/portal.html";
    }
});