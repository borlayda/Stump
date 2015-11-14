'use strict';

angular.module('Dashboard', ['ngCookies'])
.controller('DashboardController',
    ['$scope', '$rootScope', '$location', '$cookies', '$window', '$http',
    function ($scope, $rootScope, $location, $cookies, $window, $http) {
        $scope.dashboardPartActive = 'projects';
        $scope.user = JSON.parse($cookies.get("user"));

        $scope.switchActivePart = function($scope, partName) {
            console.log("Dashboard changed: Part = " + partName);

            $scope.dashboardPartActive = patName;
        }
        $scope.getUsers = function () {
            $http.get('/api/users').then(function successCallback(response){
                $scope.users = response.data;
            },
            function errorCallback(response){
                console.error(response);
            });
            console.log($scope.users);
        }
        $scope.addUser = function(name, password, email, role) {
            console.log("User added "+name+" "+password+" "+email+" "+role);
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
                $scope.users = response.data[0];
            },
            function errorCallback(response){
                console.error(response);
            });
        }
        $scope.logout = function () {
             console.log($scope.user.name);
             console.log("Logging out ...");
             $window.location.assign("/#/login");
        }
    }
]);