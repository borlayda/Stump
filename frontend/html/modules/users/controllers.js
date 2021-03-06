'use strict';

angular.module('User', ['ngCookies'])
.controller('UserController',
    ['$scope', '$rootScope', '$location', '$http',
    function ($scope, $rootScope, $location, $http) {
        $scope.selUser = {};

        $http.get('/api/users').then(function successCallback(response){
            $scope.users = response.data;
        },
        function errorCallback(response){
            alert(response.data.message);
        });

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
                $scope.name = "";
                $scope.password = "";
                $scope.email = "";
                $scope.role = "";
            },
            function errorCallback(response){
                alert("Can't add new user!\n"+response.data);
                alert(response.data.message);
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
                alert(response.data.message);
            });
        }

        // Change password
        $scope.changePassword = function(id, oldPassword, newPassword) {
            $http.post('/api/users/change-password', {
                'userId':id,
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
                'userId':id,
                'newRole': newRole
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
        $scope.addUserWindow = function (){
            $('#registerUser').modal('show');
        }
        $scope.addChangePasswordWindow = function (user){
            $scope.selUser = user;
            $('#changePassword').modal('show');
        }
        $scope.addChangeRoleWindow = function (user){
            $scope.selUser = user;
            $('#changeRole').modal('show');
        }
    }
]);