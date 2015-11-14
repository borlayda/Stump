'use strict';

angular.module('User', ['ngCookies'])
.controller('UserController',
    ['$scope', '$rootScope', '$location', '$http',
    function ($scope, $rootScope, $location, $http) {

        // Get all users
        $scope.getUsers = function () {
            $http.get('/api/users').then(function successCallback(response){
                $scope.users = response.data;
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Create user
        $scope.addUser = function(name, password, email, role) {
            $http.post('/api/users', {
                "name": name,
                "password": password,
                "email": email,
                "role": role
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.users.push({
                    "name": name,
                    "password": password,
                    "email": email,
                    "role": role
                });
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Delete user
        $scope.deleteUser = function(name) {
            $http.delete('/api/users', {
                "name": name
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.users.foreach(function (data, value){
                    if (data['name'] === name) {
                        $scope.users.splice(index, 1);
                    }
                });
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Modal Windows
        $scope.addUserWindow = function (){
            $('#registerUser').modal('show');
        }
    }
]);