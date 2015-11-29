'use strict';

angular.module('Dashboard', ['ngCookies'])
.controller('DashboardController',
    ['$scope', '$rootScope', '$location', '$cookies', '$window', '$http', '$timeout',
    function ($scope, $rootScope, $location, $cookies, $window, $http, $timeout) {
        $scope.dashboardHTMLActive = 'projects';
        $scope.dashboardPartActive = 'projects';
        $scope.Math = window.Math;
        $scope.USER_ROLES = ['ADMIN', 'PM', 'DEVELOPER', 'TESTER'];

        $scope.error = "";
        $scope.fadeOut = false;

        $scope.selTask = {};
        $scope.selProject = {};

        $scope.projectStatusLayout = {
             'CLOSED':'btn-success',
             'IN_PROGRESS': 'btn-info',
             'RESOLVED': 'btn-success',
             'OPEN':'btn-info',
             'REOPENED': 'btn-warning'
        };

        $scope.taskStatusLayout = {
             'CLOSED':'btn-success',
             'IN_PROGRESS': 'btn-info',
             'REVIEW': 'btn-warning',
             'VERIFYING': 'btn-warning',
             'OPEN':'btn-info',
             'TESTING': 'btn-warning'
        };

        $scope.projects = [];
        $scope.tasks = [];
        $scope.users = [];

        $scope.showError = function(error)  {
            $scope.error = error;
            $('#errorShow').css("display", "block");
            $scope.fadeout = true;
            $timeout(function(){
                $scope.hidden = true;
                $('#errorShow').css("display", "none");
                $scope.fadeout = true;
            }, 2000);
        }

        $scope.loginUser = {};
        $http.get('/api/users/me').then(function successCallback(response){
            $scope.loginUser = response.data;
        },
        function errorCallback(response){
            alert(response.data.message);
        });

        $scope.switchActivePart = function(part, html) {
            $scope.dashboardPartActive = part;
            $scope.dashboardHTMLActive = html;
        }
        $scope.logout = function () {
             console.log("Logging out ...");
             $window.location.assign("/#/login");
        };

        // Get specific task
        $scope.getTask = function(task){
            $http.get('/api/tasks/'+task.id).then(function successCallback(response){
                $scope.selTask = response.data;
            },
            function errorCallback(response){
                console.log("Error on getting task");
            });
        };

        $scope.getTaskView = function (task) {
            $scope.getTask(task);
            $scope.switchActivePart("tasks", "task");
        };

        // Get specific project
        $scope.getProject = function(project){
            $http.get('/api/projects/'+project.id).then(function successCallback(response){
                $scope.selProject = response.data;
            },
            function errorCallback(response){
                console.log("Error on getting project");
            });
        };

        $scope.getProjectView = function (project) {
            $scope.getProject(project);
            $scope.switchActivePart("projects", "project");
        };

        // Get all users
        $scope.getUsers = function () {
            $http.get('/api/users').then(function successCallback(response){
                $scope.users = response.data;
            },
            function errorCallback(response){
                alert(response.data.message);
            });
        };

        // Get all projects
        $scope.getProjects = function() {
            $http.get('/api/projects').then(function successCallback(response){
                $scope.projects = response.data;
            },
            function errorCallback(response){
                alert(response.data.message);
            })
        };

        // Get all tasks
        $scope.getTasks = function() {
            $http.get('/api/tasks').then(function successCallback(response){
                $scope.tasks = response.data;
            },
            function errorCallback(response){
                alert(response.data.message);
            })
        }
    }
]);