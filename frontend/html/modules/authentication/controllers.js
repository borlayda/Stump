'use strict';
  
angular.module('Authentication')
  
.controller('LoginController',
    ['$scope', '$rootScope', '$location', '$http', '$window', '$cookieStore',
    function ($scope, $rootScope, $location, $http, $window, $cookieStore) {
        $scope.login = function () {
            var authdata = btoa($scope.username +":"+ $scope.password)
            $http.get('/api/users', {
                headers: {
                    'Authorization': 'Basic '+authdata
                }
            }).then(function successCallback(response){
                $cookieStore.put("user", response.data[0]);
                $window.location.assign("/");
                console.log(response.data[0].name + " logged in");
            },
            function errorCallback(response){
                console.error(response);
            }
            );
        };
    }]);
