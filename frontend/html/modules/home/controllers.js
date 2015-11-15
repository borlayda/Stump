'use strict';

angular.module('Dashboard', ['ngCookies'])
.controller('DashboardController',
    ['$scope', '$rootScope', '$location', '$cookies', '$window', '$http',
    function ($scope, $rootScope, $location, $cookies, $window, $http) {
        $scope.dashboardHTMLActive = 'projects';
        $scope.dashboardPartActive = 'projects';
        $scope.USER_ROLES = ['ADMIN', 'PM', 'DEVELOPER', 'TESTER'];

        $scope.selTask = {};
        $scope.selProject = {};

        $scope.projects = [];
        $http.get('/api/projects').then(function successCallback(response){
            $scope.projects = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        $scope.tasks = [];
        $http.get('/api/tasks').then(function successCallback(response){
            $scope.tasks = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        $scope.users = [];
        $http.get('/api/users').then(function successCallback(response){
            $scope.users = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        $scope.loginUser = {};
        $http.get('/api/users/me').then(function successCallback(response){
            $scope.loginUser = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        $scope.switchActivePart = function(part, html) {
            $scope.dashboardPartActive = part;
            $scope.dashboardHTMLActive = html;
        }
        $scope.logout = function () {
             console.log("Logging out ...");
             $window.location.assign("/#/login");
        }

        // Get specific task
        $scope.getTask = function (task) {
            $scope.selTask = task;
            $scope.switchActivePart("tasks", "task");
        }

        // Get specific task
        $scope.getProject = function (project) {
            $scope.selProject = project;
            $scope.switchActivePart("projects", "project");
        }

        // Get all users
        $scope.getUsers = function () {
            $http.get('/api/users').then(function successCallback(response){
                $scope.users = response.data;
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Get all projects
        $scope.getProjects = function() {
            $http.get('/api/projects').then(function successCallback(response){
                $scope.projects = response.data;
            },
            function errorCallback(response){
                console.error(response);
            })
        }

        // Get all tasks
        $scope.getProjects = function() {
            $http.get('/api/tasks').then(function successCallback(response){
                $scope.projects = response.data;
            },
            function errorCallback(response){
                console.error(response);
            })
        }
    }
]);