'use strict';

angular.module('Settings', ['ngCookies'])
.controller('SettingsController',
    ['$scope', '$rootScope', '$location', '$http',
    function ($scope, $rootScope, $location, $http) {
        $scope.loginUser = {};
        $http.get('/api/users/me').then(function successCallback(response){
            $scope.loginUser = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        // Modal Windows
        $scope.addChangePasswordWindow = function (){
            $('#changePassword').modal('show');
        }
        $scope.addChangeRoleWindow = function (){
            $('#changeRole').modal('show');
        }
    }
]);