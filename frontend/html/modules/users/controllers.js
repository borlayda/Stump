'use strict';

angular.module('User', ['ngCookies'])
.controller('UserController',
    ['$scope', '$rootScope', '$location', '$http',
    function ($scope, $rootScope, $location, $http) {
        $scope.users = [];
        $http.get('/api/users').then(function successCallback(response){
            $scope.users = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

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
        $scope.deleteUser = function(user) {
            $http.delete('/api/users/'+user.id, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                console.log(response);
                console.log($scope.users);
                for (var i=0; i < $scope.users.length; i++){
                    if ($scope.users[i].id = user.id){
                        delete $scope.users[i];
                    }
                }
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