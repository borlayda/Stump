'use strict';
  
angular.module('Authentication')
  
.controller('LoginController',
    ['$scope', '$rootScope', '$location', '$http',
    function ($scope, $rootScope, $location, $http) {
        $scope.login = function () {
            var authdata = btoa($scope.username +":"+ $scope.password)
            $http.get('/api', {
                headers: {
                    'Authorization': 'Basic '+authdata
                }
            }).then(function successCallback(response){
                console.log(response);
                $location.path('/')
            },
            function errorCallback(response){
                console.error(response);
            }
            );
        };
    }]);