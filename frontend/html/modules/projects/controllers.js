'use strict';

angular.module('Project', ['ngCookies'])
.controller('ProjectController',
    ['$scope', '$rootScope', '$location', '$http', '$cookies',
    function ($scope, $rootScope, $location, $http, $cookies) {
        $scope.TASK_TYPES = ["ISSUE", "TASK"];
        $scope.PROJECT_STATUSES = ["OPEN", "IN_PROGRESS", "RESOLVED", "REOPENED", "CLOSED"];

        $http.get('/api/projects').then(function successCallback(response){
            $scope.projects = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        // Create user
        $scope.createProject = function(title, description) {
            console.log($scope.loginUser);
            console.log($scope.projects);
            $http.post('/api/projects', {
                "title": title,
                "description": description,
                "ownerId": $scope.loginUser.id,
                "status": 'OPEN'
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getProjects();
                $scope.title = "";
                $scope.description = "";
            },
            function errorCallback(response){
                alert("Can't add new project!\n"+response.data);
                console.error(response);
            });
        };

        // Delete user
        $scope.deleteProject = function(project) {
            $http.delete('/api/projects/'+project.id, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getProjects();
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Change project status
        $scope.changeProjectStatus = function(status) {
            console.log($scope.selProject.id + " " + status);
            $http.post('/api/projects/change-status', {
                "id": $scope.selProject.id,
                "status": status
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getProject($scope.selProject);
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Change Project's data
        $scope.changeProject = function(projectId, title, description, ownerId, status, selectedUsers) {
            $http.post('/api/projects/change', {
                "projectId": projectId,
                "title": title,
                "description": description,
                "ownerId": ownerId,
                "status": status,
                "users": selectedUsers.map(function (u) {
                    return u.id;
                })
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getProject($scope.selProject);
            },
            function errorCallback(response){
                console.error(response);
            });
        };

        // Create task
        $scope.createTask = function(title, description, projectId, type) {
            console.log($scope.loginUser);
            console.log($scope.tasks);
            $http.post('/api/tasks', {
                "title": title,
                "description": description,
                "ownerId": $scope.loginUser.id,
                "projectId": projectId,
                "status": 'OPEN',
                "type": type
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getProject($scope.selProject);
                $scope.title = "";
                $scope.description = "";
            },
            function errorCallback(response){
                alert("Can't add new task!\n"+response.data);
                console.error(response);
            });
        };

        // Modal Windows
        $scope.addCreateTaskWindow = function (){
            $('#createTask').modal('show');
        };
        $scope.addCreateProjectWindow = function (){
            $scope.showError('hello World');
            $http.get('/api/projects').then(function successCallback(response){
                $scope.projects = response.data;
            },
            function errorCallback(response){
                console.error(response);
            });
            $('#createProject').modal('show');
        };
        $scope.addChangeProjectWindow = function (){
            $('#changeProject').modal('show');
            $scope.usersNotInProject = $scope.users.filter(function (val) {
                return $scope.selProject.users.map(function (it) {return it.id}).indexOf(val.id) == -1;
            });
        }
    }
]);