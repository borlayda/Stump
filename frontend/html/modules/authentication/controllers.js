'use strict';
  
angular.module('Authentication')
  
.controller('LoginController',
    ['$scope', '$rootScope', '$location', '$http',
    function ($scope, $rootScope, $location, $http) {
        $scope.login = function () {
            $http.post('http://localhost:8080',
                  { username: $scope.username, password: $scope.password })
            .then(function successCallback(response){
                console.log(response);
                $location.path('/')
            },
            function errorCallback(response){
                console.error(response);
            }
            );
        };
    }]);