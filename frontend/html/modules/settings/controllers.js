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
            alert(response.data.message);
        });

        // Change password
        $scope.changePassword = function(id, oldPassword, newPassword) {
            $http.post('/api/users/change-password', {
                'id':id,
                'oldPassword': oldPassword,
                'newPassword': newPassword
            },{
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                console.log("Password changed!");
            },
            function errorCallback(response){
                alert(response.data.message);
            });
        }

        // Change role
        $scope.changeRole = function(id, newRole) {
            $http.post('/api/users/change-role', {
                'id':id,
                'role': newRole
            },{
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                console.log("Role changed!");
            },
            function errorCallback(response){
                alert(response.data.message);
            });
        }

        // Modal Windows
        $scope.addChangePasswordWindow = function (){
            $('#changePassword').modal('show');
        }
        $scope.addChangeRoleWindow = function (){
            $('#changeRole').modal('show');
        }
    }
]);