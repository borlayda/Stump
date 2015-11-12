'use strict';
  
angular.module('Authentication')
  
.controller('LoginController',
    ['$scope', '$rootScope', '$location', '$http', '$window',
    function ($scope, $rootScope, $location, $http, $window) {
        $scope.login = function () {
            var authdata = btoa($scope.username +":"+ $scope.password)
            $http.get('api', {
                headers: {
                    'Authorization': 'Basic '+authdata
                }
            }).then(function successCallback(response){
                console.log(response);
                $window.location.assign("/");
                console.log("Change!");
            },
            function errorCallback(response){
                console.error(response);
            }
            );
        };
    }]);